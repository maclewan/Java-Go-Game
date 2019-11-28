/**
 *
 * Projekt studencki tworzony przez studentów Politechniki Wrocławskiej,
 * wydziału Podstawowych Problemów Techniki, kierunku: Informatyka.
 * Kurs obowiązkowy: Technologie Programowania
 * Lista 4: "Gra w GO"
 * https://cs.pwr.edu.pl/macyna/TPLab04.pdf
 *
 * 2019-11-28
 *
 */

/**
 * @author Michał Kalina & Maciej Lewandowicz
 *
 */

package client;

import Controllers.MenuFrameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.Mnemonic;
import javafx.stage.Stage;

import java.util.Locale;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(new Locale("pl"));
/*
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI.fxml"));
        root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
*/
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MenuFrame.fxml"));
        MenuFrameController mfc = new MenuFrameController();
        loader.setController(mfc);
        root = loader.load();
        primaryStage.setTitle("Menu");
        Scene s = new Scene(root);
        primaryStage.setScene(s);
        primaryStage.setResizable(false);
        primaryStage.show();
        mfc.setStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


