import java.util.Arrays;

public class Client {

    public static void main(String[] args) {

        int[] imageArray = FileUtil.readSingleImage("./assets/998.jpg");

        assert imageArray != null;
        Network network = new Network(imageArray.length, 10);

        double[] prediction = network.predict(imageArray);

        for (int i = 0; i < prediction.length; i++) {
            System.out.println(prediction[i]);
        }
    }

}
