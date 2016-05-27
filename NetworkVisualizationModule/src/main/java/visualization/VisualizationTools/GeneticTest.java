package visualization.VisualizationTools;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.GeneticAlgorithm.GeneticAlgorithm;
import com.main.network.Layers.LayerTypes;
import com.main.network.Network.Network;
import com.main.network.Network.NetworkConfiguration;
import com.main.network.Network.NetworkFactory;
import com.main.network.neurons.NeuronTypes;


/**
 * Created by EmilSebastian on 01-04-2016.
 */
public class GeneticTest {
/*
    public static void main(String[] args){
        NetworkConfiguration.maximumNumberOfSupervizedTrainingIterations =2000;
        NetworkConfiguration.learningRate= 0.01;
        NetworkConfiguration.minimumInitializedLinearBias = -5;
        NetworkConfiguration.maximumInitializedLinearBias = 5;
        NetworkConfiguration.minimumInitializedConnectionWeight = 0.6;
        NetworkConfiguration.maximumInitializedConnectionWeight =1;

        Network net = new NetworkFactory().addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.LINEAR, 1)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.LINEAR, 20)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.LINEAR, 1).build();

        XYSeries serie = new XYSeries("Expected");
        double[][] trainingset = new double[30][1];
        double[][] testset = new double[30][1];
        int counter = 0;
        for(double i = -1; counter < trainingset.length; i+=0.1){
            trainingset[counter][0] = i;
            testset[counter][0] =  Math.sin(2 * Math.PI * i) + Math.sin(5 * Math.PI * i) ;
            serie.add(i, testset[counter][0]);
            counter++;
        }

        // Evolve our population until we reach an optimum solution
        try {
            GeneticAlgorithm genetic = new GeneticAlgorithm(200,net, trainingset, testset, 60);
            net = genetic.bestMatch.getNetwork();
        } catch (InvalidNumberOfInput invalidNumberOfInput) {
            invalidNumberOfInput.printStackTrace();
        }
        new RegressionPlot2D(net,-1,2,0.02, serie);
    }
    */
}
