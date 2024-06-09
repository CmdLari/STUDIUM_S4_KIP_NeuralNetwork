import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
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


    public static void writeToFile(List<Neuron> inputNeurons, List<Neuron> hiddenNeurons, List<Neuron> outputNeurons, String fileName){


        StringBuilder stringBuilder = new StringBuilder();

        //input
        for (Neuron neuron : inputNeurons) {
            stringBuilder.append("INPUTNEURON:")
                    .append(neuron.bias).append(":");
            for (int i = 0; i < neuron.weights.length; i ++) {
                stringBuilder.append(neuron.weights[i]).append(";");
            }
            stringBuilder.append(":MatrixSize:").append(neuron.matrixsize).append(":");
            stringBuilder.append("\n");
        }


        //hidden
        for (Neuron neuron : hiddenNeurons) {
            stringBuilder.append("HIDDENNEURON:")
                    .append(neuron.bias).append(":");
            for (int i = 0; i < neuron.weights.length; i ++) {
                stringBuilder.append(neuron.weights[i]).append(";");
            }
            stringBuilder.append(":MatrixSize:").append(neuron.matrixsize).append(":");
            stringBuilder.append("\n");
        }

        //output
        for (Neuron neuron : outputNeurons) {
            stringBuilder.append("OUTPUTNEURON:")
                    .append(neuron.bias).append(":");
            for (int i = 0; i < neuron.weights.length; i ++) {
                stringBuilder.append(neuron.weights[i]).append(";");
            }
            stringBuilder.append(":MatrixSize:").append(neuron.matrixsize).append(":");
            stringBuilder.append("\n");
        }


        try {
            // Write the content to the file
            Files.write(Paths.get(fileName), stringBuilder.toString().getBytes(), StandardOpenOption.CREATE);
            System.out.println("File written successfully: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<List<Neuron>> readFromFile(String fileName) throws IOException {
        List<Neuron> inputNeurons = new ArrayList<>();
        List<Neuron> hiddenNeurons = new ArrayList<>();
        List<Neuron> outputNeurons = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));

            for (String line : lines) {
                String[] parts = line.split(":");
                String type = parts[0];
                double bias = Double.parseDouble(parts[1]);
                String[] weightStrings = parts[2].split(";");
                double[] weights = new double[weightStrings.length];
                for (int i = 0; i < weightStrings.length; i++) {
                    weights[i] = Double.parseDouble(weightStrings[i]);
                }
                int matrixSize = Integer.parseInt(parts[4]);

                Neuron neuron = new Neuron(matrixSize, weights, bias);

                switch (type) {
                    case "INPUTNEURON":
                        inputNeurons.add(neuron);
                        break;
                    case "HIDDENNEURON":
                        hiddenNeurons.add(neuron);
                        break;
                    case "OUTPUTNEURON":
                        outputNeurons.add(neuron);
                        break;
                }
            }

        } catch (IOException e) {
            //e.printStackTrace();
            throw e;
        }

        List<List<Neuron>> trainedNeurons = new ArrayList<>();
        trainedNeurons.add(inputNeurons);
        trainedNeurons.add(hiddenNeurons);
        trainedNeurons.add(outputNeurons);

        return trainedNeurons;
    }

    public static List<int[]> readImagesFromDirectory(String directoryPath) {
        List<int[]> imageList = new ArrayList<>();

        File directory = new File(directoryPath);
        File[] subDirectories = directory.listFiles(File::isDirectory);

        if (subDirectories != null) {
            for (File subDir : subDirectories) {
                File[] imageFiles = subDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg"));

                if (imageFiles != null) {
                    for (File imageFile : imageFiles) {
                        int[] imageData = readSingleImage(imageFile.getPath());
                        if (imageData != null) {
                            imageList.add(imageData);
                        }
                    }
                }
            }
        }
        return imageList;
    }
}
