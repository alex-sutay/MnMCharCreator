import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Builder {
    private Character character;
    HashMap<String, Advantage> advantages;
    HashMap<String, PowerOpt> power_opts;
    HashMap<String, ModifierOpt> default_mods;

    /**
     * The basic constructor for a builder. Doesn't do anything.
     */
    public Builder() {}

    /**
     * This function will instantiate a Builder, load in a datafile, and start the commandline
     * @param args - the commandline arguments. should only contain the name of the datafile
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Builder builder = new Builder();
        try {
            builder.load_data(args[0]);
        } catch (Exception e) {
            System.out.println("Something went wrong while trying to load the data\n" + e);
            e.printStackTrace();
            System.exit(1);
        }
        System.out.print("Would you like to load a character? [y/n]\n> ");
        String resp = in.nextLine();
        while (!resp.equals("y") && !resp.equals("n")) {
            System.out.println("Please select y or n.");
            System.out.print("Would you like to load a character? [y/n]\n> ");
            resp = in.nextLine();
        }
        if (resp.equals("y")) {
            System.out.print("What is the name of the character file?\n> ");
            String filename = in.nextLine();
            builder.load_char(filename);
        } else {
            builder.empty_character();
        }
        builder.cmdline(in);
    }

    /**
     * This method loads all the data into the Builder from the given file
     * @param filename - the name of the data file
     * @throws FileNotFoundException - required for the file operation
     */
    public void load_data(String filename) throws FileNotFoundException {
        filename = System.getProperty("user.dir") + "\\" + filename;
        File file = new File(filename);
        if (file.exists()) {
            ArrayList<String> lines = new ArrayList<>();
            String data;
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                data = reader.nextLine();
                lines.add(data.strip());
            }
            String section;
            while (lines.size() > 0) {
                section = lines.remove(0);
                switch (section) {
                    case "!Advantages":
                        advantages = new HashMap<>();
                        lines = build_advantages(lines);
                        break;
                    case "!Modifiers":
                        default_mods = new HashMap<>();
                        lines = build_modifiers(lines);
                        break;
                    case "!Powers":
                        power_opts = new HashMap<>();
                        lines = build_powers(lines);
                        break;
                }
            }
            System.out.println("Loaded " + power_opts.size() + " powers, and all other data");
        } else {
            System.out.println("Data file not found");
        }
    }

    /**
     * Build advantages from an ArrayList of lines, each one containing one advantage
     * Takes in the rest of the ines from the file, returns the ones it didn't use
     * @param lines- the lines used to build the advantages
     * @return the leftover lines
     */
    public ArrayList<String> build_advantages(ArrayList<String> lines) {
        String line;
        while (true) {
            line = lines.remove(0).strip();
            if (line.equals("done")) {
                break;
            } else {
                advantages.put(line, new Advantage(line));
            }
        }
        return lines;
    }

    /**
     * Build modifiers from an ArrayList of lines, each one containing one modifier
     * Takes in the rest of the ines from the file, returns the ones it didn't use
     * @param lines- the lines used to build the advantages
     * @return the leftover lines
     */
    public ArrayList<String> build_modifiers(ArrayList<String> lines) {
        String line;
        String[] line_array;
        String title;
        while (true) {
            line = lines.remove(0).strip();
            if (line.equals("done")) {
                break;
            } else {
                line_array = line.split(";");
                title = line_array[0];
                default_mods.put(title, build_mod(line_array));
            }
        }
        return lines;
    }

    /**
     * Build a Modifier from a String array, each element a different aspect of the modifier. The format should be:
     * ["title", "cost1,cost2,cost3", "per_rank"]
     * @param mod_array - the String array containing the info needed to build a modifier
     * @return - the Modifier built from the array
     */
    public ModifierOpt build_mod(String[] mod_array) {
        String title;
        HashSet<Integer> cost_opt = new HashSet<>();
        boolean per_rank;
        title = mod_array[0];
        if (mod_array[1].indexOf(',') > -1) {
            String[] cost_array = mod_array[1].split(",");
            for (String str : cost_array) {
                cost_opt.add(Integer.parseInt(str));
            }
        } else {
            cost_opt.add(Integer.parseInt(mod_array[1]));
        }
        per_rank = (mod_array[2].equals("True"));
        return new ModifierOpt(title, cost_opt, per_rank);
    }

    /**
     * Build powers from an ArrayList of lines, each one containing one power
     * Takes in the rest of the ines from the file, returns the ones it didn't use
     * @param lines- the lines used to build the advantages
     * @return the leftover lines
     */
    public ArrayList<String> build_powers(ArrayList<String> lines) {
        String line;
        String[] line_array;
        String title;
        HashSet<Integer> cost_opt;
        HashMap<String, ModifierOpt> mod_opts = default_mods;

        while (true) {
            line = lines.remove(0).strip();
            if (line.equals("done")) {
                break;
            } else {
                line_array = line.split(";");
                title = line_array[0];
                cost_opt = new HashSet<>();
                if (line_array[1].indexOf(',') > -1) {
                    String[] cost_array = line_array[1].split(",");
                    for (String str : cost_array) {
                        cost_opt.add(Integer.parseInt(str));
                    }
                } else {
                    cost_opt.add(Integer.parseInt(line_array[1]));
                }
                if (line_array.length == 3) {
                    String[] mods = line_array[2].split("/");
                    for (String mod : mods) {
                        String[] mod_array = mod.split(":");
                        mod_opts.put(mod_array[0], build_mod(mod_array));
                    }
                }
                power_opts.put(title, new PowerOpt(title, cost_opt, mod_opts));
            }
        }
        return lines;
    }

    /**
     * Load in a character from a save file
     * @param filename - the name of the file with the saved character
     */
    public void load_char(String filename) {
        try {
            String name = "";
            HashMap<Attribute, Integer> attributes = new HashMap<>();
            HashMap<Defence, Integer> defences = new HashMap<>();
            HashMap<Skill, Integer> skills = new HashMap<>();
            HashMap<Advantage, Integer> advantages = new HashMap<>();
            HashMap<String, Power> powers = new HashMap<>();

            File file = new File(filename);
            Scanner input = new Scanner(file);

            String this_line;
            String[] line_array;
            while (input.hasNextLine()) {
                this_line = input.nextLine();
                line_array = this_line.split(";");
                switch (line_array[0]) {
                    case "name":
                        name = line_array[1];
                        break;
                    case "Attribute":
                        attributes.put(Attribute.valueOf(line_array[1]), Integer.valueOf(line_array[2]));
                        break;
                    case "Defence":
                        defences.put(Defence.valueOf(line_array[1]), Integer.valueOf(line_array[2]));
                        break;
                    case "Skill":
                        skills.put(Skill.valueOf(line_array[1]), Integer.valueOf(line_array[2]));
                        break;
                    case "Advantage":
                        advantages.put(new Advantage(line_array[1]), Integer.valueOf(line_array[2]));
                        break;
                    case "Power":
                        Power power = new Power(power_opts.get(line_array[2]), line_array[1], Integer.parseInt(line_array[3]), Integer.parseInt(line_array[4]));
                        if (line_array.length == 5) {
                            powers.put(line_array[1], power);
                        } else {
                            String[] mods_array = line_array[5].split("\\\\");
                            for (String mod_str : mods_array) {
                                String[] mod = mod_str.split(":");
                                Modifier modifier = new Modifier(mod[0], power, Integer.parseInt(mod[1]), Boolean.parseBoolean(mod[2]));
                                power.add_mod(modifier);
                            }
                            powers.put(line_array[1], power);
                        }
                        break;
                }
            }
            this.character = new Character(name, powers, attributes, defences, skills, advantages);
            System.out.println("Character successfully loaded");
        } catch (Exception e) {
            System.out.println("There was an error: " + e);
            e.printStackTrace();
        }
    }

    /**
     * save the current character to a file
     * @param in - the Scanner used to get missing information from the user
     */
    public void save_char(Scanner in) {
        try {
            System.out.print("What is the name of the file you'd like to write to?\n? ");
            String filename = in.nextLine();
            filename = System.getProperty("user.dir") + "\\" + filename;
            FileWriter file = new FileWriter(filename);
            file.write(character.toString());
            file.close();
            System.out.println("Character saved at: " + filename);
        } catch (Exception e) {
            System.out.println("There was an error: " + e);
            e.printStackTrace();
        }
    }

    /**
     * Set the current loaded character to an empty one
     */
    public void empty_character() {
        this.character = new Character();
    }

    /**
     * Start a command line for character editing
     * @param in - the Scanner used for taking commands
     */
    public void cmdline(Scanner in) {
        boolean cont = true;
        String input;
        String[] cmdArray;
        System.out.println("Character Creation Commandline loaded.");
        while (cont) {
            System.out.print("> ");
            input = in.nextLine();
            cmdArray = input.split(" ");
            try {
                switch (cmdArray[0]) {
                    case "help":
                        helpmsg(cmdType.DEFAULT);
                        break;
                    case "power":
                        power_cmd(in);
                        break;
                    case "attribute":
                        update_att(cmdArray);
                        break;
                    case "defence":
                        update_def(cmdArray);
                        break;
                    case "skill":
                        update_skill(cmdArray);
                        break;
                    case "advantage":
                        update_adv(cmdArray);
                        break;
                    case "print":
                        System.out.println(character.printString());
                        break;
                    case "save":
                        save_char(in);
                        break;
                    case "name":
                        String name = "";
                        for(int i = 1; i < cmdArray.length - 1; i++) {
                            name += cmdArray[i] + " ";
                        }
                        name += cmdArray[cmdArray.length - 1];
                        character.set_name(name);
                        break;
                    case "quit":
                        cont = false;
                        break;
                    default:
                        System.out.println("Unknown command. Try \"help\" for a list of commands.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e);
                e.printStackTrace();
            }
        }
    }

    /**
     * The separate command line used for editing powers
     * @param in - the Scanner used to take in commands
     */
    public void power_cmd(Scanner in) {
        boolean cont = true;
        String input;
        String[] cmdArray;
        while (cont) {
            System.out.print("Powers> ");
            input = in.nextLine();
            cmdArray = input.split(" ");
            try {
                switch (cmdArray[0]) {
                    case "help":
                        helpmsg(cmdType.POWER);
                        break;
                    case "quit":
                        cont = false;
                        break;
                    case "add":
                        add_power(cmdArray, in);
                        break;
                    case "mod":
                        mod_power(cmdArray, in);
                        break;
                    case "rename":
                        rename_power(cmdArray, in);
                        break;
                    case "delete":
                        rem_power(cmdArray);
                        break;
                    default:
                        System.out.println("Unknown command. Try \"help\" for a list of commands.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e);
                e.printStackTrace();
            }
        }
    }

    /**
     * Add a power to the current character
     * @param cmdArray - the String array representing the command issued to generate this power
     * @param in - the Scanner needed to collect extra information
     */
    public void add_power(String[] cmdArray, Scanner in) {
        String title = add_spaces(cmdArray);
        if (power_opts.containsKey(title)) {
            System.out.print("What rank is the power?\n? ");
            int rank = Integer.parseInt(in.nextLine());
            System.out.print("What is the name of the power? (Choose any description)\n? ");
            String name = in.nextLine();
            Power power = new Power(power_opts.get(title), rank, name, in);
            character.add_power(name, power);
        } else {
            System.out.println("Power \"" + title + "\" not found.");
        }
    }

    /**
     * Modify a power attached to a character
     * @param cmdArray - a String array representing the command issued
     * @param in - a Scanner needed for collecting extra information
     */
    public void mod_power(String[] cmdArray, Scanner in) {
        String title = add_spaces(cmdArray);
        Power power = character.get_power(title);
        if (power == null) {
            System.out.println("Power \"" + title + "\" not found.");
            return;
        }
        System.out.print("Which mod would you like to add?\n? ");
        String mod_name = in.nextLine();
        if (power.get_mod_opts().get(mod_name) == null) {
            System.out.println("Modifier \"" + mod_name + "\" not found.");
            return;
        }
        Modifier mod = new Modifier(power.get_mod_opts().get(mod_name), power, in);
        power.add_mod(mod);
        character.add_power(title, power);
    }

    /**
     * Rename a power attached to the character
     * @param cmdArray - a String array representing the issued command
     * @param in - a Scanner for obtaining extra information
     */
    public void rename_power(String[] cmdArray, Scanner in) {
        String name = add_spaces(cmdArray);
        Power power = character.get_power(name);
        System.out.print("What is the new name for the power?\n? ");
        String new_name = in.nextLine();
        power.set_name(new_name);
        character.remove_power(name);
        character.add_power(new_name, power);
    }

    /**
     * Remove a power from a character
     * @param cmdArray - a String array representing the command
     */
    public void rem_power(String[] cmdArray) {
        String name = add_spaces(cmdArray);
        character.remove_power(name);
    }

    /**
     * Adds spaces between the Strings in an array, skipping the first String
     * @param array - the String array being put together
     * @return String containing all Strings in the array, separated by spaces
     */
    private String add_spaces(String[] array) {
        StringBuilder string = new StringBuilder();
        if (array.length > 2) {
            for (int i = 1; i < array.length - 1; i++) {
                string.append(array[i]).append(" ");
            }
            string.append(array[array.length - 1]);
        } else {
            string = new StringBuilder(array[1]);
        }
        return string.toString();
    }

    private enum cmdType {DEFAULT, POWER}
    /**
     * sends a help message detailing possible commands
     * @param location - a cmdType dictating which help message to send
     */
    public void helpmsg(cmdType location) {
        switch (location) {
            case DEFAULT:
                System.out.println("help: print this message\n" +
                        "quit: exit the command line\n" +
                        "power: Edit the powers\n" +
                        "attribute name score: set the attribute with the given name to the given score\n" +
                        "defence name score: set the defence bonus with the given name to the given score\n" +
                        "skill name score: set the skill with the given name to the given score\n" +
                        "advantage name score: add the advantage with the given name with the given score\n");
                break;
            case POWER:
                System.out.println("help: print this message\n" +
                        "quit: exit to primary command line\n" +
                        "add title: add a power. title is the name as given in the rules\n" +
                        "mod name: add a modifier to a power. name is the name given to the power when you created it\n" +
                        "rename name: rename a power. name is the current name of the power\n" +
                        "delete name: remove a power with the given name");
                break;
        }
    }

    /**
     * update an attribute of the character
     * @param cmdArray - the String array representing the command
     */
    public void update_att(String[] cmdArray) {
        if (cmdArray.length == 3) {
            int score = Integer.parseInt(cmdArray[2]);
            Attribute att = Attribute.valueOf(cmdArray[1].toUpperCase());
            character.change_att(att, score);
        } else {
            System.out.println("usage: attribute name score");
        }
    }

    /**
     * update a defence of the character
     * @param cmdArray - the String array representing the issued command
     */
    public void update_def(String[] cmdArray) {
        if (cmdArray.length == 3) {
            int score = Integer.parseInt(cmdArray[2]);
            Defence def = Defence.valueOf(cmdArray[1].toUpperCase());
            character.change_def(def, score);
        } else {
            System.out.println("usage: attribute name score");
        }
    }

    /**
     * Update a skill of the character
     * @param cmdArray - a String array representing the issued command
     */
    public void update_skill(String[] cmdArray) {
        if (cmdArray.length == 3) {
            int score = Integer.parseInt(cmdArray[2]);
            Skill skill = Skill.valueOf(cmdArray[1].toUpperCase());
            character.change_skill(skill, score);
        } else {
            System.out.println("usage: skill name score");
        }
    }

    /**
     * Update an advantage of the character
     * @param cmdArray- a String array representing the issued command
     */
    public void update_adv(String[] cmdArray) {
        if (cmdArray.length < 3) {
            System.out.println("usage: advantage name score");
        } else {
            int score = Integer.parseInt(cmdArray[cmdArray.length - 1]);
            StringBuilder title = new StringBuilder();
            for (int i = 1; i < cmdArray.length - 2; i++) {
                title.append(cmdArray[i]).append(" ");
            }
            title.append(cmdArray[cmdArray.length - 2]);
            Advantage adv = advantages.get(title.toString());
            if (adv == null) {
                System.out.println("Advantage \"" + title + "\" not found.");
            } else {
                character.change_adv(adv, score);
            }
        }
    }
}
