import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Set;

public class AbilityList extends VBox {

    private int nextRow = 0;
    private boolean modable = false;
    private final Set<String> choices;
    private Set<String> modifiers;
    private final GridPane abilities = new GridPane();

    public AbilityList(Set<String> abilities, Set<String> modifiers) {
        this(abilities);
        modable = true;
        this.modifiers = modifiers;
    }

    public AbilityList(Set<String> choices) {
        super();
        this.choices = choices;
        Button button = new Button("Add Row");
        button.setPrefWidth(100000);
        button.setOnMouseClicked(event -> addRow());
        this.getChildren().add(abilities);
        this.getChildren().add(button);
    }

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
        }
        nextRow += 1;
    }

    private void removeRow(int row) {
        if (row == 0) {
            abilities.getChildren().removeIf
                    (node -> GridPane.getRowIndex(node) == null || GridPane.getRowIndex(node) == 0);
        } else {
            abilities.getChildren().removeIf(node -> GridPane.getRowIndex(node) == row);
        }
    }

    private void addModifier(int row) {
        // bruh idk figure this out later
    }
}
