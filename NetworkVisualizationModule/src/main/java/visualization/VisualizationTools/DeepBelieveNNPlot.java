package visualization.VisualizationTools;

import com.main.network.Layers.ILayer;
import com.main.network.Layers.LayerTypes;
import com.main.network.Network.Network;
import com.main.network.Network.NetworkConfiguration;
import com.main.network.Network.NetworkFactory;
import com.main.network.connections.IConnection;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.ISOMsNode;
import com.main.network.neurons.NeuronTypes;
import org.tc33.jheatchart.HeatChart;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by EmilSebastian on 27-03-2016.
 */
public class DeepBelieveNNPlot {
    public DeepBelieveNNPlot(ILayer lay, int numberOfWeightsInEachRow, int neuronsInEachRow){

        ArrayList<INeuron> n = lay.getNeurons();
        int totalNumberOfConnections = lay.getNeurons().get(0).getBackwardsConnections().size();

        double[][] data = new double
                [(n.size() / neuronsInEachRow) * (totalNumberOfConnections / numberOfWeightsInEachRow)
                + ((n.size() / neuronsInEachRow)-1)] // plus margin
                [neuronsInEachRow * numberOfWeightsInEachRow
                + neuronsInEachRow-1]; // plus margin
        int y = 0;
        for(int i =0 ; i < n.size() ; i++){
            // insert one cupic:
            double[] d = new double[totalNumberOfConnections];
            for(int conIndex = 0 ; conIndex < n.get(i).getBackwardsConnections().size(); conIndex++){
                d[conIndex] = n.get(i).getBackwardsConnections().get(conIndex).getWeight();
            }

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

    public static void main(String[] args){
        Network net = new NetworkFactory()
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.RBM, 100)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.RBM, 2)
                .build();

        new DeepBelieveNNPlot(net.getLayer(1), 10, 2);

//        net.unsupervizedDBTraining(null);

//        new DeepBelieveNNPlot(net.getLayer(1), 10, 2);
    }
}
