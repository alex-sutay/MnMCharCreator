/**
 * A class to store Advantages
 * @author Alex Sutay
 */
public class Advantage{
    String title;

    /**
     * The basic constructor for an advantage
     * @param title - the title of the advantage
     */
    public Advantage(String title) {
        this.title = title;
    }

    /**
     * The accessor for the title
     * @return - the title of this advantage
     */
    public String get_title() {
        return title;
    }

    /**
     * Convert the advantage into a String
     * @return - the title of the advantage
     */
    @Override
    public String toString() {
        return title;
    }
}
