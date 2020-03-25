import java.util.HashSet;
import java.util.Scanner;

/**
 * This class is used to represent modifiers to a power
 * @author Alex Sutay
 */
public class Modifier extends ModifierOpt implements Costly{
    private int cost;
    private Power attached;

    /**
     * The constructor for building a Modifier from a ModifierOpt
     * @param base - the ModifierOpt that this modifier is based on
     * @param attached - the Power this Modifier is attached to
     * @param in - a Scanner for gathering extra information
     */
    public Modifier(ModifierOpt base, Power attached, Scanner in) {
        super(base.get_title(), base.cost_opt, base.isPer_rank());
        this.attached = attached;
        if (cost_opt.size() > 1) {
            System.out.print("What is the cost of " + base.get_title() + "?\n? ");
            int input = Integer.parseInt(in.nextLine());
            while (!(cost_opt.contains(input))) {
                System.out.println("Invalid option. For this modifier, your options are:");
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
     * The constructor for building a Modifier by providing all of the attributes
     * @param title - the title of the Modifier
     * @param attached - the Power this Modifier is attached to
     * @param cost - the cost of this Modifier
     * @param per_rank - a Boolean of whether the cost is flat or per rank
     */
    public Modifier(String title, Power attached, int cost, boolean per_rank) {
        super(title, new HashSet<>(), per_rank);
        this.attached = attached;
        this.cost = cost;
    }

    /**
     * A method to get the cost of this Modifier
     * @return - the total cost of this modifier
     */
    @Override
    public long get_cost() {
        if (isPer_rank()) {
            return cost * attached.get_rank();
        } else {
            System.out.println(cost);
            return cost;
        }
    }

    /**
     * Gives a string for saving to a file. Formatted as:
     * title:cost:per_rank
     * @return String version of a modifier
     */
    @Override
    public String toString() {
        String mod_str = get_title();
        mod_str += ":" + cost + ":" + isPer_rank();
        return mod_str;
    }
}
