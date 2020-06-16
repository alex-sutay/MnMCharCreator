import java.util.HashMap;

/**
 * This class store all of the necessary parts of a character
 * @author Alex Sutay
 */
public class Character implements Costly {
    private String name;
    private HashMap<Attribute, Integer> attributes;
    private HashMap<Defence, Integer> defences;
    private HashMap<Skill, Integer> skills;
    private HashMap<Advantage, Integer> advantages;
    private HashMap<String, Power> powers;

    /**
     * The constructor used for building a character if you already have the parts you want to add
     * @param name - The name of the character
     * @param powers - A map of the names of powers to the Power objects themselves
     * @param attributes - A map of Attribute objects to their scores
     * @param defences - A map of Defence objects to their scores
     * @param skills - A map of Skill objects to their scores
     * @param advantages - A map of Advantage objects to their scores
     */
    public Character(String name, HashMap<String, Power> powers, HashMap<Attribute, Integer> attributes, HashMap<Defence, Integer> defences,
                     HashMap<Skill, Integer> skills, HashMap<Advantage, Integer> advantages) {
        this.name = name;
        this.powers = powers;
        this.attributes = attributes;
        this.defences = defences;
        this.skills = skills;
        this.advantages = advantages;
    }

    /**
     * The constructor for a blank character
     */
    public Character() {
        this.name = "";
        this.powers = new HashMap<>();
        this.attributes = Attribute.default_map();
        this.defences = Defence.default_map();
        this.skills = Skill.default_map();
        this.advantages = new HashMap<>();
    }

    /**
     * Change the score associated with an attribute
     * @param attribute - the attribute being changed
     * @param score - the new score for the attribute
     */
    public void change_att(Attribute attribute, int score) {
        attributes.put(attribute, score);
    }

    /**
     * Change the score associated with a defence
     * @param defence - the defence being changed
     * @param score - the new score for the defence
     */
    public void change_def(Defence defence, int score) {
        defences.put(defence, score);
    }

    /**
     * Change the score associated with a skill
     * @param skill - the Skill being changed
     * @param score - the new score for the skill
     */
    public void change_skill(Skill skill, int score) {
        skills.put(skill, score);
    }

    /**
     * Change the score associated with an advantage
     * @param advantage - the advantage being changed
     * @param score - the new score for the advantage
     */
    public void change_adv(Advantage advantage, int score) {
        advantages.put(advantage, score);
    }

    /**
     * Add a power to this character
     * @param title - The name of the power
     * @param power - the Power object being added
     */
    public void add_power(String title, Power power) {
        powers.put(title, power);
    }

    /**
     * Remove a power from this character
     * @param name - the name of the Power being removed
     */
    public void remove_power(String name) {
        powers.remove(name);
    }

    /**
     * Accessor for powers that this character has
     * @param title - the name of the power
     * @return - this character's Power with the given name
     */
    public Power get_power(String title) {
        return powers.get(title);
    }

    /**
     * Accessor for the skills
     * @return - HashMap of the character's skills to scores
     */
    public HashMap<Skill, Integer> get_skills() {
        return skills;
    }

    /**
     * Change the name of this character
     * @param name - the new name for this character
     */
    public void set_name(String name) {
        this.name = name;
    }

    /**
     * Accessor for the name
     * @return the name of this character
     */
    public String get_name() {
        return name;
    }

    /**
     * Accessor for an attribute score
     * @param att - the Attribute of the score
     * @return the score associated with that attribute
     */
    public int get_attribute_score(Attribute att) {
        return attributes.get(att);
    }

    /**
     * Accessor for a defence score
     * @param def - the Defence of the score
     * @return the score associated with that attribute
     */
    public int get_defence_score(Defence def) {
        return defences.get(def);
    }

    /**
     * Get a nice String for this character containing all the information about it
     * @return - a nice String representing this character
     */
    public String printString() {
        StringBuilder data = new StringBuilder("Name: ");
        data.append(name).append("\n\nAttributes:\n");
        for (Attribute att : attributes.keySet()) {
            data.append(att).append(": ").append(attributes.get(att)).append("\n");
        }
        data.append("\nDefence scores:\n");
        for (Defence defence : defences.keySet()) {
            data.append(defence).append(": modifier - ").append(defences.get(defence)).append(", total - ").append(defences.get(defence) + attributes.get(defence.get_attribute())).append("\n");
        }
        data.append("\nSkill scores:\n");
        for (Skill skill : skills.keySet()) {
            if (!(skill.untrained) && skills.get(skill) == 0) {
                data.append(skill).append(":  modifier - ").append(0).append(", total - ").append(0).append("\n");
            } else {
                data.append(skill).append(":  modifier - ").append(skills.get(skill)).append(", total - ").append(skills.get(skill) + attributes.get(skill.get_attribute())).append("\n");
            }
        }
        data.append("\nAdvantages:\n");
        for (Advantage adv : advantages.keySet()) {
            data.append(adv.get_title()).append(", rank ").append(advantages.get(adv)).append("\n");
        }
        data.append("\nPowers:\n");
        for (String power : powers.keySet()) {
            data.append(powers.get(power).printString()).append("\n");
        }
        return data.toString();
    }

    /**
     * Get a nice String breaking down where the total cost comes from
     * @return String containing a readable breakdown of where points are being spent
     */
    public String pointsString() {
        StringBuilder costStr = new StringBuilder("Points spent:\n");
        int cost = 0;
        for (String power : powers.keySet()) {
            cost += powers.get(power).get_cost();
        }
        costStr.append("Powers: ").append(cost).append("\n");
        cost = 0;
        for (Attribute attribute : attributes.keySet()) {
            cost += 2 *attributes.get(attribute);
        }
        costStr.append("Attributes: ").append(cost).append("\n");
        cost = 0;
        for (Defence defence : defences.keySet()) {
            cost += defences.get(defence);
        }
        costStr.append("Defences: ").append(cost).append("\n");
        cost = 0;
        for (Skill skill : skills.keySet()) {
            cost += skills.get(skill);
        }
        cost = cost/2;
        costStr.append("Skills: ").append(cost).append("\n");
        cost = 0;
        for (Advantage advantage : advantages.keySet()) {
            cost += advantages.get(advantage);
        }
        costStr.append("Advantages: ").append(cost).append("\n");
        costStr.append("Total Cost: ").append(get_cost());
        return costStr.toString();
    }

    /**
     * Convert this character into a String for saving to a file
     * @return - crude String representing this character
     */
    @Override
    public String toString() {
        StringBuilder char_str = new StringBuilder();
        if (!(name.equals(""))) {
            char_str.append("name;").append(name);
        }
        char_str.append("\n");
        for (Attribute att : attributes.keySet()) {
            char_str.append("Attribute;").append(att.toString()).append(";").append(attributes.get(att)).append("\n");
        }
        for (Defence def : defences.keySet()) {
            char_str.append("Defence;").append(def.toString()).append(";").append(defences.get(def)).append("\n");
        }
        for (Skill skill : skills.keySet()) {
            char_str.append("Skill;").append(skill.toString()).append(";").append(skills.get(skill)).append("\n");
        }
        for (Advantage advantage : advantages.keySet()) {
            char_str.append("Advantage;").append(advantage.toString()).append(";").append(advantages.get(advantage)).append("\n");
        }
        for (String power : powers.keySet()) {
            char_str.append(powers.get(power)).append("\n");
        }
        return char_str.toString();
    }

    /**
     * Get the total cost for everything in this character
     * @return - the total cost of this character
     */
    @Override
    public long get_cost() {
        int cost = 0;
        for (String power : powers.keySet()) {
            cost += powers.get(power).get_cost();
        }
        for (Attribute attribute : attributes.keySet()) {
            cost += 2 *attributes.get(attribute);
        }
        for (Defence defence : defences.keySet()) {
            cost += defences.get(defence);
        }
        int skillcost = 0;
        for (Skill skill : skills.keySet()) {
            skillcost += skills.get(skill);
        }
        cost += skillcost / 2;
        for (Advantage advantage : advantages.keySet()) {
            cost += advantages.get(advantage);
        }
        return cost;
    }
}
