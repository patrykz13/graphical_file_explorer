package com.pwr.graphical_file_explorer.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TreeDirectory {
    private File directory;
    private ConcurrentLinkedQueue<ImageViewClass> imageViews;

    public TreeDirectory(File directory) {
        this.directory = directory;
        imageViews = new ConcurrentLinkedQueue<>();
    }
    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }

    public ConcurrentLinkedQueue<ImageViewClass> getImageViews() {
        return imageViews;
    }

    public void setImageViews(ConcurrentLinkedQueue<ImageViewClass> imageViews) {
        this.imageViews = imageViews;
    }

    public boolean hasImages() {
        return !imageViews.isEmpty();
    }


    @Override
    public String toString() {
        return directory.getName();
    }

    public List<File> processDirectory() {
        List<File> childrenDirectories = new ArrayList<>();
        List<File> children = null;
        if (directory.listFiles() != null) {
            children = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
        } else return null;

        for (File file : children) {
            if (file.isDirectory()) childrenDirectories.add(file);
            else {
                String path = file.getPath();
                if (path.endsWith(".png") || path.endsWith(".jpg")) addImageView(file);
            }
        }
        return childrenDirectories;
    }

    private void addImageView(File file) {
        imageViews.add(new ImageViewClass(file));
    }

}
