import java.util.HashMap;
import java.util.HashSet;

/**
 * This class represents a Power with some options not yet decided
 * @author Alex Sutay
 */
public class PowerOpt extends Option{
    protected HashMap<String, ModifierOpt> mod_opt;

    /**
     * The constructor for a PowerOpt
     * @param title - the title of the Power
     * @param cost_opt - a set of integers representing the choices for the cost
     * @param mod_opt - a set of possible mods you're allowed to attach to the power
     */
    public PowerOpt(String title, HashSet<Integer> cost_opt, HashMap<String, ModifierOpt> mod_opt) {
        super(title, cost_opt);
        this.mod_opt = mod_opt;
    }

    /**
     * Accessor for the Modifier options
     * @return - a HashSet of possible Modifiers for this power
     */
    public HashMap<String, ModifierOpt> get_mod_opts() {
        return mod_opt;
    }

}
