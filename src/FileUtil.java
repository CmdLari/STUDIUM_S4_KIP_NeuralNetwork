import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FileUtil {

    public static int[] readSingleImage(String imagePath) {
        try {
            // Read the image
            BufferedImage originalImage = ImageIO.read(new File(imagePath));

            // Convert to grayscale
            BufferedImage grayscaleImage = new BufferedImage(
                    originalImage.getWidth(), originalImage.getHeight(),
                    BufferedImage.TYPE_BYTE_GRAY);

            Graphics2D g2d = grayscaleImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, null);
            g2d.dispose();

            // Extract pixel data
            byte[] pixelData = ((DataBufferByte) grayscaleImage.getRaster().getDataBuffer()).getData();

            int[] pixelDataInt = new int[pixelData.length];

            for (int i = 0; i < pixelData.length; i++) {
                pixelDataInt[i] = pixelData[i];
            }

            return pixelDataInt;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
        }
}