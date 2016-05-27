package visualization.VisualizationTools;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.Layers.LayerTypes;
import com.main.network.Network.Network;
import com.main.network.Network.NetworkConfiguration;
import com.main.network.Network.NetworkFactory;
import com.main.network.neurons.NeuronTypes;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import visualization.Utilz.JfreeChartFrame;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public class RegressionPlot2D {

    public RegressionPlot2D(Network net, double xmin, double xmax, double xinterval) {
        XYSeriesCollection dataset = new XYSeriesCollection(); //getDataSet(net,xmin,xmax,xinterval);
        XYSeries serie = getDataSet(net, xmin, xmax, xinterval);
        dataset.addSeries(serie);
        JFreeChart chart = ChartFactory.createXYLineChart("Regression Chart", "X", "Y", dataset, PlotOrientation.VERTICAL, true, true, false);
        new JfreeChartFrame(chart, "Regression Chart");
    }


    public RegressionPlot2D(Network net, double xmin, double xmax, double xinterval, XYSeries origin){
        XYSeriesCollection dataset = new XYSeriesCollection(); //getDataSet(net,xmin,xmax,xinterval);
        dataset.addSeries(origin);
        XYSeries set = getDataSet(net, xmin, xmax, xinterval);
        dataset.addSeries(set);


        JFreeChart chart = ChartFactory.createXYLineChart("Regression Chart", "X", "Y",  dataset, PlotOrientation.VERTICAL,true,true,false);
        new JfreeChartFrame(chart, "Regression Chart");
    }

    public XYSeries getDataSet(Network net, double xmin, double xmax, double xinterval) {
        net.resetNetwork();
        //XYSeriesCollection dataset = new XYSeriesCollection();

        int numberOfInputNeurons = net.getLayer(0).getNeurons().size();
        // Get data for plot:
//        for (int i = 0; i < numberOfInputNeurons; i++) {
        XYSeries serie = new XYSeries("InputNeuron_" + 1);
        for (double current = xmin; current <= xmax; current += xinterval) {
            double[] input = new double[numberOfInputNeurons];
            input[0] = current;
            input[1] = 1;
            try {
                // TODO: how to handle multible output neurons:
                double x = net.feedInputThroughNetwork(input)[0];
//                    System.out.println("Input: " + input[0] + "\tOutput:"+ x);
                //net.resetNetwork();
                serie.add(current, x);
            } catch (InvalidNumberOfInput invalidNumberOfInput) {
                invalidNumberOfInput.printStackTrace();
            }
        }
       // dataset.addSeries(serie);
//        }
        return serie;
    }


    /*
            FOR TESTING
            SAMPLE
     */
    public static void main(String[] args) {
        NetworkConfiguration.maximumNumberOfSupervizedTrainingIterations = 20000;
        NetworkConfiguration.minimumInitializedLinearBias = 0.6;
        NetworkConfiguration.maximumInitializedLinearBias = 1;
        NetworkConfiguration.minimumInitializedConnectionWeight = -1;
        NetworkConfiguration.maximumInitializedConnectionWeight = 1;
        NetworkConfiguration.learningRate= 0.1;

        Network net = new NetworkFactory().addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.INPUT, 2)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.SIGMA, 10)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.LINEAR, 1).build();

        XYSeries serie = new XYSeries("Expected");

        double[][] trainingset = new double[1000][2];
        double[][] testset = new double[1000][1];
        int counter = 0;
        for (double i = -1; counter < trainingset.length; i += 0.002) {
            trainingset[counter][0] = i;
            trainingset[counter][1] = 1;
            testset[counter][0] = Math.sin(2 * Math.PI * i) + Math.sin(5 * Math.PI * i);
            serie.add(i, testset[counter][0]);
            counter++;
        }
        try {
            net.supervisedTraining(trainingset, testset);
        } catch (InvalidNumberOfInput invalidNumberOfInput) {
            invalidNumberOfInput.printStackTrace();
        }
        new RegressionPlot2D(net, -1, 1, 0.002, serie);
    }
}
