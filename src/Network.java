import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Network {
    List<Neuron> neuronsInput;
    List<Neuron> neuronsHidden;
    List<Neuron> neuronsOutput;

    /**
     * for training
     * @param matrixSize Size of the input image (height*width)
     * @param answers Number of possible answer options
     */
    public Network(int matrixSize, int answers) {

        // HERE NEURONS (INPUT + HIDDEN + OUTPUT
        List<Neuron> neuronsInput = new ArrayList<>();
        List<Neuron> neuronsHidden = new ArrayList<>();
        List<Neuron> neuronsOutput = new ArrayList<>();

        //ADD INPUT NEURONS
        for (int i = 0; i < matrixSize; i++) {
            neuronsInput.add(new Neuron(matrixSize));
        }

        //ADD HIDDEN NEURONS
        for (int i = 0; i < matrixSize; i++) {
            neuronsHidden.add(new Neuron(matrixSize));
        }

        //ADD OUTPUT NEURONS
        for (int i = 0; i < answers; i++) {
            neuronsOutput.add(new Neuron(matrixSize));
        }

        this.neuronsInput = neuronsInput;
        this.neuronsHidden = neuronsHidden;
        this.neuronsOutput = neuronsOutput;
    }


    /**
     * @param inputs Array of the size inputMatrixlength * inputMatrixWidth
     */
    public double[] predict(int[] inputs){

        double[] inputComputes = new double[neuronsInput.size()];
        double[] hiddenComputes = new double[neuronsHidden.size()];
        double[] outputComputes = new double[neuronsOutput.size()];


        for (int i = 0; i < neuronsInput.size(); i++) {
            inputComputes[i] = neuronsInput.get(i).compute(Arrays.stream(inputs).asDoubleStream().toArray());
        }

        for (int i = 0; i < neuronsHidden.size(); i++) {
            hiddenComputes[i] = neuronsHidden.get(i).compute(inputComputes);
        }

        for (int i = 0; i < neuronsOutput.size(); i++) {
            outputComputes[i] = neuronsOutput.get(i).compute(hiddenComputes);
        }

        return outputComputes;
    }

}

