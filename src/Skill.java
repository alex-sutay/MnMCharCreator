import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * An object for storing the different choices for skills
 * @author Alex Sutay
 */
public enum Skill{
    ACROBATICS(Attribute.AGILITY, false),
    ATHLETICS(Attribute.STRENGTH, true),
    CLOSE_COMBAT(Attribute.FIGHTING, true, true),
    DECEPTION(Attribute.PRESENCE, true),
    EXPERTISE(Attribute.INTELLECT, false, true),
    INSIGHT(Attribute.AWARENESS, true),
    INTIMIDATION(Attribute.PRESENCE, true),
    INVESTIGATION(Attribute.INTELLECT, false),
    PERCEPTION(Attribute.AWARENESS, true),
    PERSUATION(Attribute.PRESENCE, true),
    RANGED_COMBAT(Attribute.DEXTERITY, true, true),
    SLEIGHT_OF_HAND(Attribute.DEXTERITY, false),
    STEALTH(Attribute.AGILITY, true),
    TECHNOLOGY(Attribute.INTELLECT, false),
    TREATMENT(Attribute.INTELLECT, false),
    VEHICLES(Attribute.DEXTERITY, false);

    protected Attribute assoc;
    protected boolean untrained;
    protected boolean has_desc;
    protected String desc = "";

    /**
     * The constructor for a skill that can't have a description
     * @param assoc - the Attribute this skill is based on
     * @param untrained - boolean of whether or not it can be used untrained
     */
    Skill (Attribute assoc, boolean untrained) {
        this.assoc = assoc;
        this.untrained = untrained;
        this.has_desc = false;
    }

    /**
     * The constructor for a skill that can have a description
     * @param assoc - the Attribute this skill is based on
     * @param untrained - boolean of whether or not it can be used untrained
     * @param has_desc - the boolean of whether or not this object can have a description
     */
    Skill (Attribute assoc, boolean untrained, boolean has_desc) {
        this.assoc = assoc;
        this.untrained = untrained;
        this.has_desc = has_desc;
    }

    /**
     * Accessor for the associated attribute
     * @return - the attribute associated with this skill
     */
    public Attribute get_attribute() {
        return assoc;
    }

    /**
     * accessor for the description, if it has one
     * @return - the description of this skill
     */
    public String get_description() {
        return desc;
    }

    /**
     * A method to set what the description of this skill is
     * @param desc - the new description of this skill
     */
    public void set_description(String desc) {
        if (has_desc) {
            this.desc = desc;
        }
    }

    /**
     * A method to build only the standard skills
     * @return - A Set of the standard skills
     */
    public static HashSet<Skill> standard() {
        HashSet<Skill> standard = value_set();
        standard.removeIf(skill -> skill.has_desc);
        return standard;
    }

    /**
     * A way to get all of te skills in a HashSet
     * @return - HashSet of all skills
     */
    public static HashSet<Skill> value_set() {
        return new HashSet<>(Arrays.asList(Skill.values()));
    }

    /**
     * A way of building a map of standard skills to integers, all set initially to 0
     * @return a Map of standard skills to zeros
     */
    public static HashMap<Skill, Integer> default_map() {
        HashMap<Skill, Integer> map = new HashMap<>();
        for (Skill skill : standard()) {
            map.put(skill, 0);
        }
        return map;
    }
}
