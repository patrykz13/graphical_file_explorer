package com.pwr.graphical_file_explorer;

import com.pwr.graphical_file_explorer.Controller.InitApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphicalFileExplorer extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setResizable(false);
            setMainStage(primaryStage);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/InitApplicationController.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            primaryStage.setTitle("Graphical explorer");
            primaryStage.setScene(new Scene(root, 600, 150));
            primaryStage.getIcons().add(new Image("/image/icon.png"));


            primaryStage.show();
        } catch (IOException ioEcx) {
            Logger.getLogger(GraphicalFileExplorer.class.getName()).log(Level.SEVERE, null, ioEcx);
        }
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        GraphicalFileExplorer.mainStage = mainStage;
    }


}

