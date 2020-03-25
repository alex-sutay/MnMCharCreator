import java.util.HashSet;

/**
 * A class representing a power option or a modifier option
 * @author Alex Sutay
 */
public abstract class Option {
    private String title;
    protected HashSet<Integer> cost_opt;

    /**
     * Basic constructor
     * @param title - the title of this object
     * @param cost_opt - the options for how much this object costs
     */
    public Option(String title, HashSet<Integer> cost_opt) {
        this.title = title;
        this.cost_opt = cost_opt;
    }

    /**
     * Accessor for the title
     * @return - the title of this object
     */
    public String get_title() {
        return title;
    }
}
