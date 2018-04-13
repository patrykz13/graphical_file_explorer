package Plugins;
import javafx.scene.image.Image;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GrayScalePlugin
{
    public Image grayScale(Image input)
    {
        ImageConverter ic = new ImageConverter();
        BufferedImage bufferedInput = ic.imageToBufferedImage(input);
        for (int i = 0; i < bufferedInput.getWidth(); i++)
        {
            for (int j = 0; j < bufferedInput.getHeight(); j++)
            {
                Color color = new Color(bufferedInput.getRGB(i, j));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int gray = (r + g + b) / 3;
                //Color newColor = new Color((int) (0.21 * r), (int) (0.72 * g), (int) (0.07 * b));
                Color newColor = new Color(gray, gray, gray);
                bufferedInput.setRGB(i, j, newColor.getRGB());
            }
        }
        return ic.bufferedImageToImage(bufferedInput);
    }
}
