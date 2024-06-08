public class Activation {

    public static double sigmoid(double outputPreActivation) {

        return 1 / (1 + Math.exp(-outputPreActivation));
    }

}
