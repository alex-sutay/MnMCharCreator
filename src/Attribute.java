import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * A way of storing the different types of Attributes
 * @author Alex Sutay
 */
public enum Attribute{
    STRENGTH, STAMINA, AGILITY, DEXTERITY, FIGHTING, INTELLECT, AWARENESS, PRESENCE;

    /**
     * A way of getting all of the choices as a HashSet
     * @return a HashSet of all possible Attributes
     */
    public static HashSet<Attribute> value_set() {
        return new HashSet<>(Arrays.asList(Attribute.values()));
    }

    /**
     * A way getting a default Attribute to Integer map. Maps each Attribute to 0
     * @return A HashMap of each Attribute to 0
     */
    public static HashMap<Attribute, Integer> default_map() {
        HashMap<Attribute, Integer> map = new HashMap<>();
        for (Attribute attribute : value_set()) {
            map.put(attribute, 0);
        }
        return map;
    }
}
