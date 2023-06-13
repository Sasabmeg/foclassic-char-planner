package net.fodev.foclassic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import net.fodev.foclassic.model.fochar.FoCharacter;
import net.fodev.foclassic.model.fochar.FoCharacterFactory;

import java.io.IOException;

/**
 * JavaFX App
 */
public class CharPlannerApp extends Application {

    @Getter @Setter private static Scene scene;
    private FoCharacter foCharacter;

    public void createNewCharacter() {
        foCharacter = FoCharacterFactory.createNewCharacter("Raging Newbie", 15, "FEMME FATALE");
    }


    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("App::start(Stage stage)");
        //scene = new Scene(loadFXML("fxml/register"), 1024, 590);
        scene = new Scene(loadFXML("fxml/primary"), 1024, 768);
        System.out.println("App::stage.setScene(scene)");
        stage.setScene(scene);
        stage.getIcons().add(new Image(CharPlannerApp.class.getResource("images/dice-icon.png").openStream()));
        stage.setTitle("FOClassic Character Planner");
        System.out.println("App::stage.show()");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setRoot(Parent fxmlLoader) {
        scene.setRoot(fxmlLoader);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        System.out.println("App::loadFXML(\"" + fxml + "\")");
        FXMLLoader fxmlLoader = new FXMLLoader(CharPlannerApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        System.out.println("App::launch()");
        launch();
    }

}