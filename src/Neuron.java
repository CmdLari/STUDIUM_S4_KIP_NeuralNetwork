import java.util.Random;

public class Neuron {
    public double[] weights;
    public double bias;
    public double[] oldWeights;
    public double oldBias;
    private int matrixsize;

    public Neuron(int matrixSize) {
        Random random = new Random();
        this.bias = random.nextDouble(-1, 1);
        double[] weights = new double[matrixSize];
        this.weights=weights;
        this.matrixsize = matrixSize;

        for (int i = 0; i < matrixSize; i++) {
            double weight = random.nextDouble(-1, 1);
            weights[i] = weight;
        }
    }

    public Neuron(int matrixSize, double[] weights, double bias) {
        if (weights.length != matrixSize) throw new IllegalArgumentException("Matrix size does not match weights array");

        this.bias = bias;
        this.weights=weights;
        this.matrixsize = matrixSize;
    }

    public double compute(double[] input) {

        double outputPreActivation = 0;
        for (int i = 0; i < input.length; i++) {
            outputPreActivation += input[i]*this.weights[i];
        }
        outputPreActivation += this.bias;

        return Activation.sigmoid(outputPreActivation);
    }

    public void mutate(){
        Random random = new Random();
        int propertyToChange = random.nextInt(0, this.matrixsize+1);
        double changeFactor = random.nextDouble(-1, 1);
        if (propertyToChange != 0) {
            this.bias =+ changeFactor;
        }
        else {
            this.weights[propertyToChange]+=changeFactor;
        }
    }

    public void forget(){
        this.bias = this.oldBias;
        this.weights = this.oldWeights;
    }

    public void remember(){
        this.oldBias = this.bias;
        this.oldWeights = this.weights;
    }

}
