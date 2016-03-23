package visualization.VisualizationTools;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.Layers.LayerTypes;
import com.main.network.Network.Network;
import com.main.network.Network.NetworkConfiguration;
import com.main.network.Network.NetworkFactory;
import com.main.network.neurons.NeuronTypes;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import visualization.Utilz.JfreeChartFrame;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public class RegressionPlot2D {

    public RegressionPlot2D(Network net, double xmin, double xmax, double xinterval){
        XYSeriesCollection dataset = getDataSet(net,xmin,xmax,xinterval);
        JFreeChart chart = ChartFactory.createXYLineChart("Regression Chart", "X", "Y", dataset);
        new JfreeChartFrame(chart, "Regression Chart");
    }
    public RegressionPlot2D(Network net, double xmin, double xmax, double xinterval, XYSeries origin){
        XYSeriesCollection dataset = getDataSet(net,xmin,xmax,xinterval);
        dataset.addSeries(origin);
        JFreeChart chart = ChartFactory.createXYLineChart("Regression Chart", "X", "Y", dataset);
        new JfreeChartFrame(chart, "Regression Chart");
    }

    public XYSeriesCollection getDataSet(Network net, double xmin, double xmax, double xinterval){
        net.resetNetwork();
        XYSeriesCollection dataset = new XYSeriesCollection();

        int numberOfInputNeurons = net.getLayer(0).getNeurons().size();
        // Get data for plot:
        for (int i = 0; i < numberOfInputNeurons; i++) {
            XYSeries serie = new XYSeries("InputNeuron_"+i);
            for (double current = xmin; current <= xmax; current += xinterval) {
                double[] input = new double[numberOfInputNeurons];
                input[i] = current;
                try {
                    // TODO: how to handle multible output neurons:
                    double x = net.feedInputThroughNetwork(input)[0];
                    //System.out.println(x);
                    net.resetNetwork();
                    serie.add(current, x);
                } catch (InvalidNumberOfInput invalidNumberOfInput) {
                    invalidNumberOfInput.printStackTrace();
                }
            }
            dataset.addSeries(serie);
        }
        return dataset;
    }


    /*
            FOR TESTING
            SAMPLE
     */
    public static void main(String[] args){
        NetworkConfiguration.maximumNumberOfSupervizedTrainingIterations =2000;
        NetworkConfiguration.learningRate= 0.01;
        NetworkConfiguration.minimumInitializedLinearBias = -5;
        NetworkConfiguration.maximumInitializedLinearBias = 5;
        NetworkConfiguration.minimumInitializedConnectionWeight = 0.6;
        NetworkConfiguration.maximumInitializedConnectionWeight =1;

        Network net = new NetworkFactory().addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.SIGMA, 1)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.SIGMA, 4)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.SIGMA, 5)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.LINEAR, 1).build();

        XYSeries serie = new XYSeries("Expected");

        double[][] trainingset = new double[300][1];
        double[][] testset = new double[300][1];
        int counter = 0;
        for(double i = -1; counter < trainingset.length; i+=0.01){
            trainingset[counter][0] = i;
            testset[counter][0] =  Math.sin(2 * Math.PI * i) + Math.sin(5 * Math.PI * i) ;
            serie.add(i, testset[counter][0]);
            counter++;
        }

        try {
            net.supervisedTraining(trainingset, testset);
        } catch (InvalidNumberOfInput invalidNumberOfInput) {
            invalidNumberOfInput.printStackTrace();
        }
        new RegressionPlot2D(net,-1,2,0.02, serie);
    }
}
