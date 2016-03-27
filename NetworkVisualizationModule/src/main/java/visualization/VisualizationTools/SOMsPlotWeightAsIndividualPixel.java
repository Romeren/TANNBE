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
import org.tc33.jheatchart.HeatChart;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by EmilSebastian on 26-03-2016.
 */
public class SOMsPlotWeightAsIndividualPixel {

    public SOMsPlotWeightAsIndividualPixel(ILayer lay, int numberOfWeightsInEachRow) {

        ArrayList<INeuron> n = lay.getNeurons();
        int totalNumberOfWeights = lay.getNeurons().get(0).getBackwardsConnections().size();

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

        ImageIcon img = new ImageIcon(map.getChartImage());
        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel lbl = new JLabel(img);
        frame.add(lbl);
        frame.show();
    }

    public static void main(String[] args) {
        int numberOfInputDimensions = 25;
        NetworkConfiguration.minimumInitializedSOMWeight = 0;
        NetworkConfiguration.maximumInitializedSOMWeight = 255;
        NetworkConfiguration.SOMsGridDimensions[0] = 4;
        NetworkConfiguration.SOMsGridDimensions[1] = 4;
        NetworkConfiguration.SOMsMaxNumberOfIterations = 2000;
        NetworkConfiguration.dimentionalityOfInput = numberOfInputDimensions;
        Network net = new NetworkFactory()
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.LINEAR, numberOfInputDimensions)
                .addLayer(LayerTypes.SOMSLAYER, NeuronTypes.SOMS,
                        NetworkConfiguration.SOMsGridDimensions[0] * NetworkConfiguration.SOMsGridDimensions[1])
                .build();
        new SOMsPlotWeightAsIndividualPixel(net.getLayer(1), 5);
        try {
            for (int i = 0; i < NetworkConfiguration.SOMsMaxNumberOfIterations; i++) {
                double tmpData[] = new double[numberOfInputDimensions];
                for(int z = 0; z < numberOfInputDimensions; z++){
                    tmpData[z] = RandomUtilz.getDoubleInRange(0,255);
                }
                net.feedInputThroughNetwork(tmpData);
                net.resetNetwork();
            }
        } catch (InvalidNumberOfInput invalidNumberOfInput) {
            invalidNumberOfInput.printStackTrace();
        }

        new SOMsPlotWeightAsIndividualPixel(net.getLayer(1), 5);
    }
}