package Plugins;

import javafx.scene.image.Image;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class RotatePlugin {
    public Image rotate(Image input)
    {
        ImageConverter ic = new ImageConverter();
        BufferedImage bufferedInput = ic.imageToBufferedImage(input);
        int width = bufferedInput.getWidth();
        int height = bufferedInput.getHeight();
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.PI / 2, width / 2, height / 2);
        AffineTransformOp transformOp = new AffineTransformOp(transform,
                AffineTransformOp.TYPE_BILINEAR);
        BufferedImage bufferedOutput = transformOp.filter(bufferedInput, null);
        return ic.bufferedImageToImage(bufferedOutput);
    }
}
