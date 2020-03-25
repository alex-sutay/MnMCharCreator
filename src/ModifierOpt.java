import java.util.HashSet;

/**
 * A class representing a power modifier that isn't finished yet
 * @author Alex Sutay
 */
public class ModifierOpt extends Option{
    private boolean per_rank;

    /**
     * Basic constructor
     * @param title - the title of the modifier
     * @param cost_opt - the options for how much this modifier costs
     * @param per_rank - boolean of whether or not the cost is flat or per rank
     */
    public ModifierOpt(String title, HashSet<Integer> cost_opt, boolean per_rank) {
        super(title, cost_opt);
        this.per_rank = per_rank;
    }

    /**
     * Accessor for per_rank
     * @return - boolean of whether or not the cost is flat or per rank
     */
    public boolean isPer_rank() {return per_rank;}
}
