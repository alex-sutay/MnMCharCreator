import java.util.HashSet;
import java.util.Scanner;

/**
 * This class represents a completed Power
 * @author Alex Sutay
 */
public class Power extends PowerOpt implements Costly{
    private int rank;
    private int cost;
    private String name;
    private HashSet<Modifier> mods = new HashSet<>();
    public enum PowerType {

    }

    /**
     * The Constructor for building a new power from a PowerOpt of unknown cost
     * @param base - the PowerOpt used to build this Power
     * @param rank - the rank of the Power
     * @param name - the given name of the Power
     * @param in - a Scanner to gather extra information
     */
    public Power(PowerOpt base, int rank, String name, Scanner in) {
        super(base.get_title(), base.cost_opt, base.mod_opt);
        this.rank = rank;
        this.name = name;
        if (cost_opt.size() > 1) {
            System.out.print("What is the cost of " + base.get_title() + "?\n? ");
            int input = Integer.parseInt(in.nextLine());
            while (!(cost_opt.contains(input))) {
                System.out.println("Invalid option. For this power, your options are:");
                for (int opt : cost_opt) {
                    System.out.print(opt);
                    System.out.print(", ");}
                System.out.println("only.");
                System.out.print("What is the cost of " + base.get_title() + "?\n> ");
                input = Integer.parseInt(in.nextLine());
            }
            this.cost = input;
        } else {
            for (int cost : cost_opt) {
                this.cost = cost;
            }
        }
    }

    /**
     * A constructor for re-building a Power that already has mods
     * @param base - the PowerOpt this power is based on
     * @param name - the given name of the Power
     * @param rank - the rank of the Power
     * @param cost - the chosen cos per rank
     * @param mods - the set of Mods to be added
     */
    public Power(PowerOpt base, String name, int rank, int cost, HashSet<Modifier> mods) {
        super(base.get_title(), base.cost_opt, base.mod_opt);
        this.rank = rank;
        this.name = name;
        this.cost = cost;
        this.mods = mods;
    }

    /**
     * A constructor for re-building a Power that already without any mods
     * @param base - the PowerOpt this power is based on
     * @param name - the given name of the Power
     * @param rank - the rank of the Power
     * @param cost - the chosen cos per rank
     */
    public Power(PowerOpt base, String name, int rank, int cost) {
        super(base.get_title(), base.cost_opt, base.mod_opt);
        this.rank = rank;
        this.name = name;
        this.cost = cost;
    }

    /**
     * Add a Modifier to this Power
     * @param mod - the Modifier being added
     */
    public void add_mod(Modifier mod) {
        mods.add(mod);
    }

    /**
     * Get a nice readable String version of this Power
     * @return - a nice readable String for printing
     */
    public String printString() {
        StringBuilder pow_str = new StringBuilder(name);
        pow_str.append(" (").append(get_title()).append("): ");
        pow_str.append("rank ").append(rank).append("; ");
        if (mods.size() != 0) {
            pow_str.append("modifiers: ");
            for (Modifier mod : mods) {
                pow_str.append(mod.get_title()).append(", ");
            }
        }
        pow_str.append("total cost: ").append(get_cost());
        return pow_str.toString();
    }

    /**
     * Accessor for the rank
     * @return - the rank
     */
    public int get_rank() {
        return rank;
    }

    /**
     * Method for setting the rank
     * @param rank - the new rank for this power
     */
    public void set_rank(int rank) {
        this.rank = rank;
    }

    /**
     * Method for setting the name
     * @param name - the new name of the Power
     */
    public void set_name(String name) {
        this.name = name;
    }

    /**
     * Gives a string for the save file format, which is:
     * name;title;rank;cost;mod1\mod2\mod3
     * @return String version of the power
     */
    @Override
    public String toString() {
        StringBuilder pow_str = new StringBuilder("Power;");
        pow_str.append(name).append(";").append(get_title()).append(";").append(rank).append(";").append(cost).append(";");
        for (Modifier mod : mods) {
            pow_str.append(mod.toString()).append("\\");
        }
        pow_str.setLength(pow_str.length() - 1);
        return pow_str.toString();
    }

    /**
     * Calculate the total cost for this Power
     * @return - the total cost of this Power
     */
    @Override
    public long get_cost() {
        long cost = this.cost * rank;
        for (Modifier mod : mods) {
            cost += mod.get_cost();
        }
        if (cost < 1) {
            return 1;
        } else {
            return cost;
        }
    }
}
