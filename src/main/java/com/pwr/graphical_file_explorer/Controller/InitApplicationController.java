package com.pwr.graphical_file_explorer.Controller;
import com.pwr.graphical_file_explorer.GraphicalFileExplorer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitApplicationController {


    public void buttonEnterApplication_onClick(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getClassLoader().getResource("fxml/MainFrame.fxml"));
            loader.load();
            MainFrameController display = loader.getController();
            Parent parent = loader.getRoot();
            Stage stage = GraphicalFileExplorer.getMainStage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException ioEcx) {
            Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ioEcx);
        }
    }
}
