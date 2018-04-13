package com.pwr.graphical_file_explorer.Controller;

import com.pwr.graphical_file_explorer.ui.ImageViewClass;
import com.pwr.graphical_file_explorer.ui.TreeDirectory;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrameController implements Initializable {

    public StackPane flowPaneDirectory;
    public FlowPane miniaturesFlowPane;
    public ScrollPane scrollPaneImages;
    public ScrollPane scrollPaneDirectory;
    public Slider sliderSize;
    public ImageView rotateLeft;
    public ImageView rotateRight;
    public Button buttonFileModel;
    public TextArea textAreaSelectedFile;
    public TextArea textAreaDescription;
    private TreeView<TreeDirectory> treeView;
    private TreeDirectory selectedDirectory;
    private static ImageViewClass imageViewClass;

    public static ImageViewClass getImageViewClass() {
        return imageViewClass;
    }

    public static void setImageViewClass(ImageViewClass imageViewClass) {
        MainFrameController.imageViewClass = imageViewClass;
    }

    public void buttonChooseDirectory_onClick(ActionEvent actionEvent) {
        File rootDirectory;
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Choose the root directory");
        rootDirectory = dc.showDialog(null);
        if (rootDirectory.isDirectory()) {
            initializeTreeView(rootDirectory);
        }
    }

    private void initializeTreeView(File rootDirectory) {
        flowPaneDirectory.getChildren().clear();
        TreeItem<TreeDirectory> rootTreeItem = new TreeItem<>(new TreeDirectory(rootDirectory));
        initializeTreeItem(rootTreeItem);
        treeView = new TreeView<>(rootTreeItem);
        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            treeViewMouseClicked();
        });
        flowPaneDirectory.getChildren().add(treeView);
    }

    private void treeViewMouseClicked() {

        miniaturesFlowPane.getChildren().clear();
        if (treeView.getSelectionModel().getSelectedItem() != null) {
            selectedDirectory = treeView.getSelectionModel().getSelectedItem().getValue();
            textAreaDescription.setText("Now browsing: "
                    + selectedDirectory.getDirectory().getName()
                    +"\n" +"Images: "
                    + selectedDirectory.getImageViews().size());
            if (selectedDirectory.getImageViews() != null) selectedDirectory.getImageViews().forEach(img -> {
                SwingUtilities.invokeLater(img);
                img.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    this.imageViewClass = img;
                    buttonFileModel.setDisable(false);
                    textAreaSelectedFile.setText(img.getImageSource().getName());
                    event.consume();
                    System.gc();
                });
                miniaturesFlowPane.getChildren().add(img);

            });
        }

        // loadMiniatures();
    }

    private void initializeTreeItem(TreeItem<TreeDirectory> treeItem) {
        TreeDirectory directory = treeItem.getValue();
        if (directory.processDirectory() != null) {
            List<File> subdirectories = directory.processDirectory();
            for (File subdirectory : subdirectories) {
                TreeItem<TreeDirectory> child = new TreeItem<>(new TreeDirectory(subdirectory));
                initializeTreeItem(child);
                treeItem.getChildren().add(child);
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // scrollPaneImages.setFitToWidth(true);
        //scrollPaneImages.setFitToHeight(true);
        //scrollPaneDirectory.setFitToWidth(true);
        // scrollPaneDirectory.setFitToHeight(true);
        miniaturesFlowPane.prefWidthProperty().bind(scrollPaneImages.widthProperty());
        miniaturesFlowPane.prefHeightProperty().bind(scrollPaneImages.heightProperty());
        addSliderListener();
        addRotateListener();

    }

    private void addRotateListener() {
        rotateLeft.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    timerLeft.start();
                } else {
                    timerLeft.stop();
                }
                event.consume();

            }

        });

        rotateRight.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    timerRight.start();
                } else {
                    timerRight.stop();
                }
                event.consume();

            }


        });
    }


    final AnimationTimer timerRight = new AnimationTimer() {

        private long lastUpdate = 0;

        @Override
        public void handle(long time) {
            if (this.lastUpdate > 100) {
                imageViewClass.roteRightPicture(2.0);
            }
            this.lastUpdate = time;
        }
    };

    final AnimationTimer timerLeft = new AnimationTimer() {

        private long lastUpdate = 0;

        @Override
        public void handle(long time) {
            if (this.lastUpdate > 100) {
                imageViewClass.rotateLeftPicture(2.0);
            }
            this.lastUpdate = time;
        }
    };


    private void addSliderListener() {
        sliderSize.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                imageViewClass.scalePicture(new_val.doubleValue() - old_val.doubleValue());

            }
        });
    }


    public void buttonFileModel_onClick(ActionEvent actionEvent) {
        try {


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("fxml/FileModeling.fxml"));
            Parent rooot = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(rooot));

            stage.show();


        } catch (IOException ioEcx) {
            Logger.getLogger(InitApplicationController.class.getName()).log(Level.SEVERE, null, ioEcx);

        }
    }
}
