import java.util.List;

public class Util {

    public static double sigmoid(double outputPreActivation) {

        return 1 / (1 + Math.exp(-outputPreActivation));
    }

    public static Double meanSquareLoss(List<int[]> correctAnswers, List<double[]> predictedAnswers) {

        double sumSquares = 0;
        for (int i = 0; i < correctAnswers.size(); i++) {
            for (int j = 0; j < correctAnswers.get(i).length; j++) {
                double error = (double)correctAnswers.get(i)[j] - predictedAnswers.get(i)[j];
                sumSquares += (error*error);
            }
        }
        return sumSquares;
    }

}
