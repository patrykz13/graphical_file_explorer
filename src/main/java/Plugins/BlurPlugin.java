package Plugins;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class BlurPlugin {
    public Image blur(Image input){
        ImageConverter ic = new ImageConverter();
        BufferedImage bufferedInput = ic.imageToBufferedImage(input);
        int radius = 11;
        int size = radius * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] data = new float[size * size];

        for (int i = 0; i < data.length; i++) {
            data[i] = weight;
        }

        Kernel kernel = new Kernel(size, size, data);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        //tbi is BufferedImage
        bufferedInput = op.filter(bufferedInput, null);
        return ic.bufferedImageToImage(bufferedInput);

    }


}
