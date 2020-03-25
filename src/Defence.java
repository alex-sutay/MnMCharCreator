import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This enum stores the different options for Defences
 * @author Alex Sutay
 */
public enum Defence{
    DODGE(Attribute.AGILITY), PARRY(Attribute.FIGHTING), FORTITUDE(Attribute.STAMINA), TOUGHNESS(Attribute.STAMINA), WILL(Attribute.AWARENESS);

    Attribute assoc;

    /**
     * Basic constructor for a defence
     * @param assoc - the associated Attribute
     */
    Defence (Attribute assoc) {
        this.assoc = assoc;
    }

    /**
     * Accessor for the attribute
     * @return - the associated Attribute
     */
    public Attribute get_attribute() {
        return assoc;
    }

    /**
     * A way of getting all the possible values in a HashSet
     * @return a HashSet of all possible entries
     */
    public static HashSet<Defence> value_set() {
        return new HashSet<>(Arrays.asList(Defence.values()));
    }

    /**
     * A way of getting all the possible values mapped to Integers, set to 0
     * @return a HashMap of all possible values, each mapped to 0
     */
    public static HashMap<Defence, Integer> default_map() {
        HashMap<Defence, Integer> map = new HashMap<>();
        for (Defence defence : value_set()) {
            map.put(defence, 0);
        }
        return map;
    }
}
