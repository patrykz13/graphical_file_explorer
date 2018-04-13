package com.pwr.graphical_file_explorer.ui;

import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ImageViewClass extends StackPane implements Runnable {

    private static BackgroundFill backgroundFill = new BackgroundFill(Color.web("#BFBFBF"), CornerRadii.EMPTY, Insets.EMPTY);
    private File imageSource;
    private WeakReference<Image> image;
    private ImageView imageView;

    public ImageViewClass(File file) {
        this.imageSource = file;
        setPrefHeight(80);
        setPrefWidth(80);
        setMaxHeight(80);
        setMaxWidth(80);
        setBackground(new Background(backgroundFill));
        setStyle("-fx-border-color: #0096C9");
        image = new WeakReference<Image>(null);
        imageView = new ImageView();
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        imageView.setPreserveRatio(true);
        imageView.setEffect(new DropShadow(20, Color.BLACK));

        getChildren().add(imageView);
    }

    public File getImageSource() {
        return imageSource;
    }

    public void setImageSource(File imageSource) {
        this.imageSource = imageSource;
    }

    public Image getImage() {
        return image.get();
    }

    public void setImage(Image image) {
        this.image = new WeakReference<Image>(image);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void scalePicture(double value) {
        this.imageView.setFitHeight(imageView.getFitHeight() + value);
        this.imageView.setFitWidth(imageView.getFitWidth() + value);
    }

    public void rotateLeftPicture(Double rotateValue) {
        imageView.setRotate(imageView.getRotate() - rotateValue);
    }

    public void roteRightPicture(Double rotateValue) {
        imageView.setRotate(imageView.getRotate() + rotateValue);
    }


    public ImageViewClass getObject() {
        return this;
    }

    @Override
    public void run() {
        if (image.get() == null) {
            if (imageSource.exists()) {
                image = new WeakReference<>(new Image("file:" + imageSource.getPath()));
            }
        }
        imageView.setImage(image.get());
    }

}
