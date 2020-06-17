import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Set;

public class AbilityList extends VBox {

    private int nextRow = 0;
    private boolean modable = false;
    private final Set<String> choices;
    private Set<String> modifiers;
    private final GridPane abilities = new GridPane();
    private HashMap<Integer, Integer> modslot = new HashMap<>();

    /**
     * Constructor for a list that has modifiers
     * @param abilities - the Set of ability options
     * @param modifiers - the Set of modifier options
     */
    public AbilityList(Set<String> abilities, Set<String> modifiers) {
        this(abilities);
        modable = true;
        this.modifiers = modifiers;
    }

    /**
     * Constructor for a list without modifiers
     * @param choices - the Set of ability options
     */
    public AbilityList(Set<String> choices) {
        super();
        this.choices = choices;
        Button button = new Button("Add Row");
        button.setPrefWidth(100000);
        button.setOnMouseClicked(event -> addRow());
        this.getChildren().add(abilities);
        this.getChildren().add(button);
    }

    /**
     * The function to add a power
     */
    private void addRow() {
        int thisRow = nextRow;
        TextField name = new TextField("New");
        abilities.add(name, 0, thisRow);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(choices);
        abilities.add(comboBox, 1, thisRow);
        Button delete = new Button("Remove");
        delete.setOnMouseClicked(event -> removeRow(thisRow));
        abilities.add(delete, 2, thisRow);
        if (modable) {
            Button addMod = new Button("Add Modifier");
            addMod.setOnMouseClicked(event -> addModifier(thisRow));
            abilities.add(addMod, 3, thisRow);
            modslot.put(thisRow, 4);
        }
        nextRow += 1;
    }

    /**
     * The function to remove a power
     * @param row - the row number of the power being removed
     */
    private void removeRow(int row) {
        if (row == 0) {
            abilities.getChildren().removeIf
                    (node -> GridPane.getRowIndex(node) == null || GridPane.getRowIndex(node) == 0);
        } else {
            abilities.getChildren().removeIf(node -> GridPane.getRowIndex(node) == row);
        }
    }

    /**
     * The method to add a modifier
     * @param row - the row number of the power being modified
     */
    private void addModifier(int row) {
        int thisSlot = modslot.get(row);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(modifiers);
        abilities.add(comboBox, thisSlot, row);
        modslot.put(row, thisSlot+1);
    }
}
