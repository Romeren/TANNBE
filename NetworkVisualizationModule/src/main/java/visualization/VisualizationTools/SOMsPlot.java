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
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public class SOMsPlot {

    public SOMsPlot(ILayer lay){

        ArrayList<INeuron> n = lay.getNeurons();
        double[][] data = new double[NetworkConfiguration.SOMsGridDimensions[0]][NetworkConfiguration.SOMsGridDimensions[1]];
        for (int x = 0; x < NetworkConfiguration.SOMsGridDimensions[0] ; x++){
            for (int y = 0; y < NetworkConfiguration.SOMsGridDimensions[1]; y++){
                double[] d = ((ISOMsNode) n.get(x+ (y*NetworkConfiguration.SOMsGridDimensions[0]))).getWeights();
                double awg = 0;
                for (double tmp : d){
                    awg += tmp;
                }
                data[x][y] = awg/d.length;
            }
        }

        HeatChart map = new HeatChart(data);

        map.setTitle("SOMs Layer");
        map.setXAxisLabel("X");
        map.setYAxisLabel("Y");

        ImageIcon img = new ImageIcon(map.getChartImage());
        JFrame frame = new JFrame();
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel lbl = new JLabel(img);
        frame.add(lbl);
        frame.show();
    }

    public static void main(String[] args){
        NetworkConfiguration.minimumInitializedSOMWeight = 0;
        NetworkConfiguration.maximumInitializedSOMWeight = 255;
        NetworkConfiguration.SOMsMaxNumberOfIterations = 1000;
        Network net = new NetworkFactory()
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.LINEAR, 3)
                .addLayer(LayerTypes.SOMSLAYER, NeuronTypes.SOMS, 100)
                .build();

        new SOMsPlot(net.getLayer(1));

        try {
            for (int i =0 ; i<NetworkConfiguration.SOMsMaxNumberOfIterations ;i++) {
                double r = RandomUtilz.getDoubleInRange(0, 255);
                double g = RandomUtilz.getDoubleInRange(0, 255);
                double b = RandomUtilz.getDoubleInRange(0, 255);
//                System.out.println("R:" + r + "    G:" + g + "    B:" + b);
                net.feedInputThroughNetwork(new double[]{r,g,b});
                net.resetNetwork();
            }
        } catch (InvalidNumberOfInput invalidNumberOfInput) {
            invalidNumberOfInput.printStackTrace();
        }
        new SOMsPlot(net.getLayer(1));
    }
}
