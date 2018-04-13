package com.pwr.graphical_file_explorer.Controller;
import com.pwr.graphical_file_explorer.ClassLoader.ClassLoader;
import com.pwr.graphical_file_explorer.ui.ImageViewClass;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class FileModelingController implements Initializable {
    public ImageView imageViewPicture;
    public ComboBox comboBoxPlugin;
    private Image originalImage;
    private ImageViewClass imageViewClass = MainFrameController.getImageViewClass();
    private ClassLoader classLoader;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classLoader = new ClassLoader(FileModelingController.class.getClassLoader(), "file:C:/Users/Patryk Zdral/Desktop/");

        imageViewPicture.setImage(imageViewClass.getImage());
        originalImage = imageViewClass.getImage();
        comboBoxPlugin.getItems().addAll("Grayscale", "Negative", "Sepia", "Blur", "Rotate","Original");
        addComboBoxListener();
    }

    private void addComboBoxListener() {
        comboBoxPlugin.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("Grayscale")) loadGrayscale();
                if (newValue.equals("Negative")) loadNegative();
                if (newValue.equals("Sepia")) loadSepia();
                if (newValue.equals("Blur")) loadBlur();
                if (newValue.equals("Rotate")) loadRotate();
                if (newValue.equals("Original")) loadOriginal();

            }
        });
    }

    private void loadOriginal() {
        imageViewPicture.setImage(originalImage);
    }


    private void loadGrayscale() {
        if(classLoader ==null){
            classLoader = new ClassLoader(FileModelingController.class.getClassLoader(), "file:C:/Users/Patryk Zdral/Desktop/");
        }
        javafx.scene.image.Image input = originalImage;
        Image output = classLoader.invokeImage("Plugins.GrayScalePlugin", "grayScale", input);
        imageViewPicture.setImage(output);
    }

    private void loadNegative() {
        if(classLoader ==null){
            classLoader = new ClassLoader(FileModelingController.class.getClassLoader(), "file:C:/Users/Patryk Zdral/Desktop/");
        }
        javafx.scene.image.Image input = originalImage;
        Image output = classLoader.invokeImage("Plugins.NegativePlugin", "negative", input);
        imageViewPicture.setImage(output);
    }

    private void loadSepia() {
        if(classLoader ==null){
            classLoader = new ClassLoader(FileModelingController.class.getClassLoader(), "file:C:/Users/Patryk Zdral/Desktop/");
        }
        javafx.scene.image.Image input = originalImage;
        Image output = classLoader.invokeImage("Plugins.SepiaPlugin", "sepia", input);

        imageViewPicture.setImage(output);
    }

    private void loadBlur() {
        if(classLoader ==null){
            classLoader = new ClassLoader(FileModelingController.class.getClassLoader(), "file:C:/Users/Patryk Zdral/Desktop/");
        }
        javafx.scene.image.Image input = originalImage;
        Image output = classLoader.invokeImage("Plugins.BlurPlugin", "blur", input);
        imageViewPicture.setImage(output);
    }
    private void loadRotate() {
        if(classLoader ==null){
            classLoader = new ClassLoader(FileModelingController.class.getClassLoader(), "file:C:/Users/Patryk Zdral/Desktop/");
        }
        javafx.scene.image.Image input = originalImage;
        Image output = classLoader.invokeImage("Plugins.RotatePlugin", "rotate", input);
        imageViewPicture.setImage(output);
    }

    public void buttonUnload_onAction(ActionEvent actionEvent) {
        System.out.println(comboBoxPlugin.getSelectionModel().getSelectedItem().toString());
        classLoader.unLoadObject();
        classLoader =null;
        System.gc();
    }
}
