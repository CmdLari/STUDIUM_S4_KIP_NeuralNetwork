import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {

    public static void main(String[] args) {


        List<int[]> imageArrays = FileUtil.readImagesFromDirectory("assets/Training");

        List<int[]> answers = new ArrayList<>();
        int[] answer0 = new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] answer1 = new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] answer2 = new int[] {0, 0, 1, 0, 0, 0, 0, 0, 0, 0};
        int[] answer3 = new int[] {0, 0, 0, 1, 0, 0, 0, 0, 0, 0};
        int[] answer4 = new int[] {0, 0, 0, 0, 1, 0, 0, 0, 0, 0};
        int[] answer5 = new int[] {0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
        int[] answer6 = new int[] {0, 0, 0, 0, 0, 0, 1, 0, 0, 0};
        int[] answer7 = new int[] {0, 0, 0, 0, 0, 0, 0, 1, 0, 0};
        int[] answer8 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
        int[] answer9 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 1};

        for (int i = 0; i<10; i++){
             answers.add(answer0);
        }

        for (int i = 0; i<10; i++){
            answers.add(answer1);
        }

        for (int i = 0; i<10; i++){
            answers.add(answer2);
        }

        for (int i = 0; i<10; i++){
            answers.add(answer3);
        }

        for (int i = 0; i<10; i++){
            answers.add(answer4);
        }

        for (int i = 0; i<10; i++){
            answers.add(answer5);
        }

        for (int i = 0; i<10; i++){
            answers.add(answer6);
        }

        for (int i = 0; i<10; i++){
            answers.add(answer7);
        }

        for (int i = 0; i<10; i++){
            answers.add(answer8);
        }

        for (int i = 0; i<10; i++){
            answers.add(answer9);
        }

        int epochCount= 5;

        Network network;
        try {
            network=new Network(epochCount);
        }catch (IOException e){
            network = new Network(imageArrays.getFirst().length, 10, epochCount);
        }


        network.train(imageArrays, answers);

        int[] imageArray = FileUtil.readSingleImage("./assets/974.jpg");

        double[] prediction = network.predict(imageArray);

        System.out.println(prettyPred(prediction));

        for(double d : prediction){
            System.out.println(d);
        }
    }

    public static String prettyPred(double[] prediction){
        String answerEnd;

        double zero = prediction[0];
        double one = prediction[1];
        double two = prediction[2];
        double three = prediction[3];
        double four = prediction[4];
        double five = prediction[5];
        double six = prediction[6];
        double seven = prediction[7];
        double eight = prediction[8];
        double nine = prediction[9];

        double thisIsIt = Arrays.stream(prediction).max().orElseThrow();

        if (thisIsIt==zero){
            answerEnd="Zero";
        }
        else if (thisIsIt==one){
            answerEnd="One";
        }
        else if (thisIsIt==two){
            answerEnd="two";
        }
        else if (thisIsIt==three){
            answerEnd="Three";
        }
        else if (thisIsIt==four){
            answerEnd="Four";
        }
        else if (thisIsIt==five){
            answerEnd="Five";
        }
        else if (thisIsIt==six){
            answerEnd="Six";
        }
        else if (thisIsIt==seven){
            answerEnd="Seven";
        }
        else if (thisIsIt==eight){
            answerEnd="Eight";
        }
        else {
            answerEnd="Nine";
        }

        System.out.println("the number on the image is: " + answerEnd);

        return answerEnd;
    }
}
