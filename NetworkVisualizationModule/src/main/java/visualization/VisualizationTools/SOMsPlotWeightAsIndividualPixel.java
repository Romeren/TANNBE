package visualization.VisualizationTools;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.Layers.ILayer;
import com.main.network.Layers.LayerTypes;
import com.main.network.Network.Network;
import com.main.network.Network.NetworkConfiguration;
import com.main.network.Network.NetworkFactory;
import com.main.network.RandomUtilz;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.ISOMsNode;
import com.main.network.neurons.NeuronTypes;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import org.tc33.jheatchart.HeatChart;
import visualization.Utilz.MNISTReader;
import visualization.Utilz.MnistWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by EmilSebastian on 26-03-2016.
 */
public class SOMsPlotWeightAsIndividualPixel {

    public SOMsPlotWeightAsIndividualPixel(ILayer lay, int numberOfWeightsInEachRow, String chartname) {

        ArrayList<INeuron> n = lay.getNeurons();
        System.out.println("Number of neurons: " + n.size());
        int totalNumberOfWeights = n.get(0).getBackwardsConnections().size();
        System.out.println("Number of weights: " + totalNumberOfWeights);

        double[][] data = new double
                [NetworkConfiguration.SOMsGridDimensions[1] * (totalNumberOfWeights / numberOfWeightsInEachRow)
                + (NetworkConfiguration.SOMsGridDimensions[1]-1)] // plus margin
                [NetworkConfiguration.SOMsGridDimensions[0] * numberOfWeightsInEachRow
                + (NetworkConfiguration.SOMsGridDimensions[0]-1)]; // plus margin
        int y = 0;
        for(int i =0 ; i < n.size() ; i++){
            // insert one cupic:
            double[] d = ((ISOMsNode) n.get(i)).getWeights();

            int x = (i*(numberOfWeightsInEachRow+1) %(data[0].length+1));
            if(x == 0 && i > 0){
                y += (d.length/numberOfWeightsInEachRow)+1;
            }
//            System.out.println("X: " + x + "\tY:"+y);

            for(int x1 = 0; x1 < d.length/numberOfWeightsInEachRow; x1++){
                for(int y1 = 0; y1 < d.length/numberOfWeightsInEachRow; y1++) {
//                    System.out.println("X: " + (x+(x1%numberOfWeightsInEachRow)) + "\tdY:"+(y+y1));
                    data[y+y1][x + (x1 % numberOfWeightsInEachRow)] = d[x1+(y1* numberOfWeightsInEachRow)];
                }
            }
        }

        HeatChart map = new HeatChart(data);

        map.setTitle("SOMs Layer");
        map.setXAxisLabel("X");
        map.setYAxisLabel("Y");
        if(chartname != null)
            try {
                map.saveToFile(new File(chartname));
            } catch (IOException e) {
                e.printStackTrace();
            }
        ImageIcon img = new ImageIcon(map.getChartImage());
        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel lbl = new JLabel(img);
        frame.add(lbl);
        frame.show();
    }
    public static void calculateUDistanceMatrix(ILayer layer, String chartname){
        double[][] umatrix = calcUMatrix(layer);
        HeatChart map = new HeatChart(umatrix);

        map.setTitle("SOMs Layer");
        map.setXAxisLabel("X");
        map.setYAxisLabel("Y");
        try {
            map.saveToFile(new File(chartname));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon img = new ImageIcon(map.getChartImage());
        JFrame frame = new JFrame();
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel lbl = new JLabel(img);
        frame.add(lbl);
        frame.show();
    }

    public static double calcGoodness(ILayer layer){
        double[][] umatrix = calcUMatrix(layer);
        double average = 0;
        double counter = 0;
        for(int x = 0; x < umatrix.length; x++)
            for (int y = 0; y < umatrix.length; y++) {
                if (x % 2 == 0 && y % 2 == 1) {
                    average += umatrix[x][y];
                    counter++;
                }else if(x % 2 == 1 && y % 2 ==0){
                    average += umatrix[x][y];
                    counter++;
                }
            }

        return average/counter;
    }

    private static double[][] calcUMatrix(ILayer layer){
        ArrayList<INeuron> neurons = layer.getNeurons();
        ISOMsNode[][] neuronMatrix = new ISOMsNode[NetworkConfiguration.SOMsGridDimensions[0]][NetworkConfiguration.SOMsGridDimensions[1]];
        int neuronCounter = 0;
        for(int x = 0 ; x < neuronMatrix.length; x++){
            for (int y = 0 ; y < neuronMatrix.length; y++){
                neuronMatrix[x][y] = ((ISOMsNode)neurons.get(neuronCounter));
                neuronCounter++;
            }
        }
        double[][] umatrix = new double[(NetworkConfiguration.SOMsGridDimensions[0]*2)-1][(NetworkConfiguration.SOMsGridDimensions[1]*2)-1];
        for (int x = 0; x < umatrix.length; x++){
            for (int y = 0; y < umatrix[0].length; y++){
                if(x % 2 == 0){
                    if (y % 2 == 0){
                        // original neuron:
                        umatrix[x][y] = getAvg(neuronMatrix[x/2][y/2]);
                    }else{
                        //between neurons in a column:
                        umatrix[x][y] = calcEucledianDistance(neuronMatrix[x/2][(y-1)/2],neuronMatrix[x/2][(y+1)/2]);
                    }
                }else{
                    if(y % 2 == 0){
                        // between neurons in a row:
                        umatrix[x][y] = calcEucledianDistance(neuronMatrix[(x-1)/2][y/2],neuronMatrix[(x+1)/2][y/2]);
                    }
                    //else{// Corner between 4 distances://}
                }
            }
        }

        for (int x = 0; x < umatrix.length; x++) {
            for (int y = 0; y < umatrix[0].length; y++) {
                if(x % 2 == 1 && y % 2 == 1){
                    umatrix[x][y] = getAvg(umatrix[x][y-1], umatrix[x-1][y], umatrix[x+1][y],umatrix[x][y+1]);
                }
            }
        }
        return umatrix;
    }

    private static   double calcEucledianDistance(ISOMsNode node1, ISOMsNode node2){
        double dist = 0;
        for(int i = 0; i < node1.getWeights().length; i++){
            double tmp = node1.getWeights()[i] - node2.getWeights()[i];
            dist += tmp * tmp;
        }
        return Math.sqrt(dist);
    }

    private static double getAvg(ISOMsNode node){
        double avg = 0;
        for(double weight : node.getWeights()){
            avg += weight;
        }
        avg = avg/node.getWeights().length;
        return avg;
    }

    private static double getAvg(double d1, double d2,double d3, double d4){
        return (d1+d2+d3+d4)/4;
    }

    public static void main(String[] args) throws Exception {
        int dim = 15;
        int numberOfInputDimensions = 15*15;

        MnistWrapper mnist =  MNISTReader.getDataSet("mnistlabel","mnisttraining");
        // make soms:
        NetworkConfiguration.learningRate = 1;
        NetworkConfiguration.minimumInitializedSOMWeight = 0;
        NetworkConfiguration.maximumInitializedSOMWeight = 1;
        NetworkConfiguration.SOMsGridDimensions[0] = 10;
        NetworkConfiguration.SOMsGridDimensions[1] = 10;
        NetworkConfiguration.SOMsMaxNumberOfIterations = 100000;
        NetworkConfiguration.dimentionalityOfInput = numberOfInputDimensions;
        Network net = new NetworkFactory()
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.INPUT, numberOfInputDimensions)
                .addLayer(LayerTypes.SOMSLAYER, NeuronTypes.CORRELATIONSOMS,
                        NetworkConfiguration.SOMsGridDimensions[0] * NetworkConfiguration.SOMsGridDimensions[1])
                .build();

        //new SOMsPlotWeightAsIndividualPixel(net.getLayer(1), dim, "before.png");
        List<BufferedImage> images = new ArrayList<>();


        double[] goodnessData = new double[NetworkConfiguration.SOMsMaxNumberOfIterations/10];
        try {
            int i = 0;
            Random rand = new Random();
            while(i < NetworkConfiguration.SOMsMaxNumberOfIterations) {
                if(mnist.images.size() < 2){
                    mnist.images = images;
                    images.clear();
                }

                BufferedImage bi = mnist.images.get(rand.nextInt(mnist.images.size()));
                images.add(bi);
                mnist.images.remove(bi);

                double[] tmpData = new double[numberOfInputDimensions];
                int counter = 0;
                for (int x = 0; x < dim; x++){
                    for (int y= 0 ; y < dim; y++)
                        tmpData[counter++] = bi.getRGB(x, y)& 0xFF;
                }
                net.feedInputThroughNetwork(tmpData);
                net.resetNetwork();
                if(i % 10 == 0) {
                    goodnessData[i/10] = SOMsPlotWeightAsIndividualPixel.calcGoodness(net.getLayer(1));
                    System.out.println("Iteration " + i + " have been completed!");
                }
                i++;
            }
        } catch (InvalidNumberOfInput invalidNumberOfInput) {
            invalidNumberOfInput.printStackTrace();
        }
        SOMsPlotWeightAsIndividualPixel spwaip = new SOMsPlotWeightAsIndividualPixel(net.getLayer(1), dim, "after.png");

        calculateUDistanceMatrix(net.getLayer(1), "UMatrix.png");

        LineRenderer lines = new DefaultLineRenderer2D();
        lines.setColor(Color.BLUE);
        DataTable data = new DataTable(Double.class, Double.class);
        for(int i = 0; i < goodnessData.length; i++){
            data.add(i, goodnessData[i]);
        }
        XYPlot plot = new XYPlot(data);
        plot.setLineRenderers(data, lines);

        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new InteractivePanel(plot));
        frame.show();
    }
}