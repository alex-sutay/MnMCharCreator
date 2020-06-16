import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Application;


public class BuilderGui extends Application{
    Builder builder;

    public BuilderGui() {
        super();
        System.out.println("yeet constructor");
    }

    @Override
    public void init() throws Exception {
        super.init();
        builder = new Builder(getParameters().getUnnamed().get(0));
        System.out.println( "init called." );
    }

    @Override
    public void start( Stage primaryStage ) throws Exception {
        System.out.println( "start called." );

        try {
            // Create the layout for page 1
            VBox page1 = new VBox();
            BackgroundImage backImage1 = defBackImage("images/charSheet1.jpg");
            Background background1 = new Background(backImage1);
            page1.setBackground(background1);
            page1.setMinSize(850, 1100);

            // The top section of the first page
            VBox charInfo = new VBox();  //Character Info
            charInfo.setPrefSize(850, 200);
            charInfo.setSpacing(5);
            TextField name = new TextField();  // Line 1
            name.setPrefSize(520, 10);
            HBox line1 = new HBox();
            line1.setSpacing(10);
            line1.getChildren().addAll(spaceFiller(350, 25), name);
            TextField playerName = new TextField();  // Line 2
            playerName.setPrefSize(140, 10);
            TextField identity = new TextField();
            identity.setPrefSize(140, 10);
            HBox line2 = new HBox();
            line2.setSpacing(10);
            line2.getChildren().addAll(spaceFiller(335, 25), playerName, spaceFiller(30, 25), identity);
            TextField gender = new TextField();  // Line 3
            gender.setPrefSize(105, 10);
            TextField age = new TextField();
            age.setPrefSize(105, 10);
            TextField height = new TextField();
            height.setPrefSize(105, 10);
            TextField weight = new TextField();
            weight.setPrefSize(105, 10);
            TextField eyes = new TextField();
            eyes.setPrefSize(105, 10);
            TextField hair = new TextField();
            hair.setPrefSize(105, 10);
            HBox line3 = new HBox();
            line3.setSpacing(10);
            line3.getChildren().addAll(spaceFiller(57, 25), gender, spaceFiller(5, 25), age, spaceFiller(15,25), height, spaceFiller(15, 25), weight, spaceFiller(10,25), eyes, spaceFiller(10, 25), hair);
            TextField group = new TextField();  // Line 4
            group.setPrefSize(200, 10);
            TextField base = new TextField();
            base.setPrefSize(200, 10);
            TextField level = new TextField();
            level.setPrefSize(105, 10);
            HBox line4 = new HBox();
            line4.setSpacing(10);
            line4.getChildren().addAll(spaceFiller(115, 25), group, spaceFiller(110, 25), base, spaceFiller(85, 25), level);
            TextField ppAbilities = new TextField();  // Line 5
            ppAbilities.setPrefSize(65, 10);
            TextField ppPowers = new TextField();
            ppPowers.setPrefSize(65, 10);
            TextField ppAdvantages = new TextField();
            ppAdvantages.setPrefSize(65, 10);
            TextField ppSkills = new TextField();
            ppSkills.setPrefSize(65, 10);
            TextField ppDefenses = new TextField();
            ppDefenses.setPrefSize(65, 10);
            TextField ppTotal = new TextField();
            ppTotal.setPrefSize(65, 10);
            HBox line5 = new HBox();
            line5.setSpacing(10);
            line5.getChildren().addAll(spaceFiller(185, 25), ppAbilities, spaceFiller(45, 25), ppPowers, spaceFiller(80, 25), ppAdvantages, spaceFiller(40, 25), ppSkills, spaceFiller(45, 25), ppDefenses, ppTotal);
            charInfo.getChildren().addAll(spaceFiller(850, 30), line1, line2, line3, line4, line5);

            // The middle section of the first page
            HBox combInfo = new HBox();
            combInfo.setPrefSize(850, 420);
            // Left half
            VBox lcenter = new VBox();
            lcenter.setPrefSize(675, 420);
            GridPane attributes = new GridPane();  // Attributes
            attributes.setPrefSize(675, 105);
            attributes.setPadding(new Insets(10, 5, 10, 5));
            attributes.setHgap(5);
            attributes.setVgap(7);
            for (int r = 0; r <= 1; r++) {
                for (int c = 0; c <= 7; c+=2) {
                    attributes.add(spaceFiller(80, 50), c, r);
                }
            }
            TextArea txtStrength = new TextArea();
            txtStrength.setPrefSize(80, 50);
            attributes.add(txtStrength, 1, 0);
            TextArea txtAgility = new TextArea();
            txtAgility.setPrefSize(80, 50);
            attributes.add(txtAgility, 3, 0);
            TextArea txtFighting = new TextArea();
            txtFighting.setPrefSize(80, 50);
            attributes.add(txtFighting, 5, 0);
            TextArea txtAwareness = new TextArea();
            txtAwareness.setPrefSize(80, 50);
            attributes.add(txtAwareness, 7, 0);
            TextArea txtStamina = new TextArea();
            txtStamina.setPrefSize(80, 50);
            attributes.add(txtStamina, 1, 1);
            TextArea txtDexterity = new TextArea();
            txtDexterity.setPrefSize(80, 50);
            attributes.add(txtDexterity, 3, 1);
            TextArea txtIntellect = new TextArea();
            txtIntellect.setPrefSize(80, 50);
            attributes.add(txtIntellect, 5, 1);
            TextArea txtPresence = new TextArea();
            txtPresence.setPrefSize(80, 50);
            attributes.add(txtPresence, 7, 1);
            GridPane offense = new GridPane();  // Offenses
            offense.setMaxSize(675, 170);
            offense.setPadding(new Insets(5));
            offense.setVgap(2);
            offense.setHgap(1);
            TextArea lblInitiative = new TextArea();
            lblInitiative.setPrefSize(42, 40);
            BorderPane initiative = new BorderPane();
            initiative.setRight(lblInitiative);
            offense.add(initiative, 2, 0);
            for (int i = 1; i<5; i++) {
                TextField txtAttack = new TextField();
                txtAttack.setPrefSize(185, 1);
                offense.add(txtAttack, 0,i);
                TextField txtModifier = new TextField();
                txtModifier.setPrefSize(30, 1);
                offense.add(txtModifier, 1,i);
                TextField txtDescription = new TextField();
                txtDescription.setPrefSize(458, 1);
                offense.add(txtDescription, 2, i);
            }
            TextArea conditions = new TextArea();  // Conditions
            conditions.setPrefSize(675, 145);
            lcenter.getChildren().addAll(attributes, offense, conditions);
            // Right half
            VBox rcenter = new VBox();
            BorderPane defenses = new BorderPane();  // Defenses
            VBox defenseText = new VBox();
            defenses.setPrefSize(175, 240);
            TextArea dodge = new TextArea();
            dodge.setPrefSize(42,40);
            TextArea parry = new TextArea();
            parry.setPrefSize(42, 40);
            TextArea fortitude = new TextArea();
            fortitude.setPrefSize(42, 40);
            TextArea toughness = new TextArea();
            toughness.setPrefSize(42, 40);
            TextArea will = new TextArea();
            will.setPrefSize(42, 40);
            defenseText.setPadding(new Insets(3));
            defenseText.getChildren().addAll(spaceFiller(39, 10), dodge, parry, fortitude, toughness, will);
            defenses.setPadding(new Insets(10));
            defenses.setRight(defenseText);
            Button heroPoints = spaceFiller(175, 60);  // Hero points
            Button ppEarned = spaceFiller(175, 60);  // Power Points earned
            Button ppSpent = spaceFiller(175, 60);  // Power Points spent
            rcenter.getChildren().addAll(defenses, heroPoints, ppEarned, ppSpent);
            combInfo.getChildren().addAll(lcenter, rcenter);

            // The bottom section of the first page
            AbilityList powerInfo = new AbilityList(builder.power_opts.keySet(), builder.default_mods.keySet());
            powerInfo.setPrefSize(850, 480);

            page1.getChildren().addAll(charInfo, combInfo, powerInfo);


            // Create the layout for page 2
            BorderPane page2 = new BorderPane();
            BackgroundImage backImage2 = defBackImage("images/charSheet2.jpg");
            Background background2 = new Background(backImage2);
            page2.setBackground(background2);
            page2.setMinSize(850, 1100);
            // Left half
            VBox leftSide = new VBox();
            leftSide.setSpacing(10);
            leftSide.getChildren().add(spaceFiller(450, 120));
            AbilityList advantages = new AbilityList(builder.advantages.keySet());
            advantages.setPrefSize(450, 250);
            leftSide.getChildren().add(advantages);
            TextArea complications = new TextArea();
            complications.setPrefSize(450, 245);
            leftSide.getChildren().add(complications);
            TextArea equipment = new TextArea();
            equipment.setPrefSize(450, 245);
            leftSide.getChildren().add(equipment);
            page2.setLeft(leftSide);
            VBox information = new VBox();
            information.setPrefSize(450, 245);
            information.setPadding(new Insets(10));
            information.setSpacing(25);
            HBox infoLine = new HBox();
            infoLine.setPrefSize(830,25);
            TextField series = new TextField();
            series.setPrefSize(390, 25);
            TextField gamemaster = new TextField();
            gamemaster.setPrefSize(400, 25);
            infoLine.getChildren().addAll(spaceFiller(90, 25), series, spaceFiller(110, 25), gamemaster);
            information.getChildren().add(infoLine);
            TextArea notes = new TextArea();
            notes.setPrefSize(830, 125);
            information.getChildren().add(notes);
            // Bottom
            page2.setBottom(information);
            // Right side
            BorderPane skills = new BorderPane();
            skills.setPrefSize(400, 890);
            skills.setPadding(new Insets(10));
            VBox skillNames = new VBox();
            skillNames.setPrefSize(150, 870);
            skillNames.setSpacing(5);
            skillNames.getChildren().add(spaceFiller(145, 150));
            TextField closeCombat1 = new TextField();
            closeCombat1.setPrefSize(125, 25);
            closeCombat1.setPadding(new Insets(0,0,0,20));
            skillNames.getChildren().add(closeCombat1);
            TextField closeCombat2 = new TextField();
            closeCombat2.setPrefSize(125, 25);
            closeCombat2.setPadding(new Insets(0,0,0,20));
            skillNames.getChildren().add(closeCombat2);
            TextField closeCombat3 = new TextField();
            closeCombat3.setPrefSize(125, 25);
            closeCombat3.setPadding(new Insets(0,0,0,20));
            skillNames.getChildren().addAll(closeCombat3, spaceFiller(150, 45));
            TextField Expertise1 = new TextField();
            Expertise1.setPrefSize(125, 25);
            Expertise1.setPadding(new Insets(0,0,0,20));
            skillNames.getChildren().add(Expertise1);
            TextField Expertise2 = new TextField();
            Expertise2.setPrefSize(125, 25);
            Expertise2.setPadding(new Insets(0,0,0,20));
            skillNames.getChildren().add(Expertise2);
            TextField Expertise3 = new TextField();
            Expertise3.setPrefSize(125, 25);
            Expertise3.setPadding(new Insets(0,0,0,20));
            skillNames.getChildren().add(Expertise3);
            TextField Expertise4 = new TextField();
            Expertise4.setPrefSize(125, 25);
            Expertise4.setPadding(new Insets(0,0,0,20));
            skillNames.getChildren().addAll(Expertise4, spaceFiller(150, 190));
            TextField rangeCombat1 = new TextField();
            rangeCombat1.setPrefSize(125, 25);
            rangeCombat1.setPadding(new Insets(0,0,0,20));
            skillNames.getChildren().add(rangeCombat1);
            TextField rangeCombat2 = new TextField();
            rangeCombat2.setPrefSize(125, 25);
            rangeCombat2.setPadding(new Insets(0,0,0,20));
            skillNames.getChildren().add(rangeCombat2);
            TextField rangeCombat3 = new TextField();
            rangeCombat3.setPrefSize(125, 25);
            rangeCombat3.setPadding(new Insets(0,0,0,20));
            skillNames.getChildren().add(rangeCombat3);
            skills.setLeft(skillNames);
            GridPane skillScores = new GridPane(); // Scores section
            skillScores.setPrefSize(230, 870);
            skillScores.setPadding(new Insets(10)); 
            skillScores.setHgap(10);
            skillScores.setVgap(7);
            for (int r = 0; r < 23; r++) {
                for (int c = 0; c < 4; c++) {
                    TextField entry = new TextField();
                    entry.setPrefSize(50, 15);
                    if (r==0) {
                        entry.setPadding(new Insets(60,0,0,0));
                    }
                    if (r == 2 || r==6 || r==15) {
                        entry.setPadding(new Insets(30,0,0,0));
                    }
                    skillScores.add(entry, c, r);
                }
            }
            skills.setRight(skillScores);
            page2.setRight(skills);

            VBox pagesPane = new VBox();
            pagesPane.getChildren().add(page1);
            pagesPane.getChildren().add(page2);


            // Put them both in the main pane and display
            ScrollPane mainPane = new ScrollPane();
            mainPane.setContent(pagesPane);
            mainPane.setMaxHeight(1000);
            mainPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            mainPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

            primaryStage.setTitle( "New Character" );
            Scene scene = new Scene(mainPane);
            scene.getStylesheets().add("css/textarea.css");
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (NullPointerException e) {
            System.out.println("Image file missing or corrupted");
            System.exit(1);
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println( "stop called. Do termination cleanup." );
    }

    BackgroundImage defBackImage(String filename) {
        return new BackgroundImage(
                new Image(getClass().getResource(filename).toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(850, 1100, false, false, false, false)
        );
    }

    Button spaceFiller(int w, int h) {
        Button rtnBtn = new Button();
        rtnBtn.setPrefSize(w, h);
        rtnBtn.setOpacity(0.3);
        return rtnBtn;
    }

    public static void main( String[] args ) {
        System.out.println( "main launching application..." );
        Application.launch( args );
    }
}
