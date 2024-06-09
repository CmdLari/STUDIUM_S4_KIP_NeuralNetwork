import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Network {
    List<Neuron> neuronsInput;
    List<Neuron> neuronsHidden;
    List<Neuron> neuronsOutput;

    List<Neuron> allNeurons;

    int epochCount;

    /**
     * for training
     * @param matrixSize Size of the input image (height*width)
     * @param answers Number of possible answer options
     */
    public Network(int matrixSize, int answers, int epochNr) {

        // HERE NEURONS (INPUT + HIDDEN + OUTPUT
        List<Neuron> neuronsInput = new ArrayList<>();
        List<Neuron> neuronsHidden = new ArrayList<>();
        List<Neuron> neuronsOutput = new ArrayList<>();

        this.allNeurons = new ArrayList<>();

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
        this.epochCount = epochNr;

        this.allNeurons.addAll(neuronsInput);
        this.allNeurons.addAll(neuronsHidden);
        this.allNeurons.addAll(neuronsOutput);
    }

    /**
     * for using a set of pre-trained Neurons
     *
     */
    public Network (int epochCount) throws IOException {
        List <Neuron> neuronsInput ;
        List <Neuron> neuronsHidden;
        List <Neuron> neuronsOutput;

        List<List<Neuron>> trainedNeurons = FileUtil.readFromFile("trainedNeurons.txt");

        neuronsInput = trainedNeurons.get(0);
        neuronsHidden = trainedNeurons.get(1);
        neuronsOutput = trainedNeurons.get(2);

        this.neuronsInput = neuronsInput;
        this.neuronsHidden = neuronsHidden;
        this.neuronsOutput = neuronsOutput;
        this.allNeurons = new ArrayList<>();
        allNeurons.addAll(neuronsInput);
        allNeurons.addAll(neuronsHidden);
        allNeurons.addAll(neuronsOutput);
        this.epochCount =epochCount;
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

    public void train (List<int[]> trainingData, List<int[]> answers){
        //Training with known results

        Double bestEpochLoss = null;


        for (int epoch = 0; epoch < epochCount; epoch++) {
            Neuron epochNeuron = allNeurons.get(epoch % allNeurons.size());
            //Brave attempt to use a method overlooked in the tutorial 1=!=!=!??!??
            epochNeuron.mutate();

            List<double[]> predictions = new ArrayList<>();

            for (int i = 0; i < trainingData.size(); i++) {

                predictions.add(i, this.predict(trainingData.get(i)));
            }

            Double thisEpochLoss = Util.meanSquareLoss(answers, predictions);

            if (bestEpochLoss == null){
                bestEpochLoss = thisEpochLoss;
                epochNeuron.remember();
            }
            else {
                if (thisEpochLoss < bestEpochLoss){
                    bestEpochLoss = thisEpochLoss;
                    epochNeuron.remember();
                }
                else {
                    epochNeuron.forget();
                }
            }
        }

        // saving the trained neurons
        FileUtil.writeToFile(neuronsInput, neuronsHidden, neuronsOutput, "trainedNeurons.txt");


    }

}

