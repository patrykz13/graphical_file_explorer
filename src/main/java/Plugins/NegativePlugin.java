package Plugins;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class NegativePlugin {
    public Image negative(Image input)
    {
        ImageConverter ic = new ImageConverter();
        BufferedImage bufferedInput = ic.imageToBufferedImage(input);
        for(int x = 0; x <bufferedInput.getWidth(); x++){
            for(int y = 0; y < bufferedInput.getHeight(); y++){
                int p = bufferedInput.getRGB(x, y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                //subtract RGB from 255
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                //set new RGB value
                p = (a<<24) | (r<<16) | (g<<8) | b;
                //img.setRGB(x, y, p);
                bufferedInput.setRGB(x, y, p);
            }
        }
        return ic.bufferedImageToImage(bufferedInput);
    }
}
