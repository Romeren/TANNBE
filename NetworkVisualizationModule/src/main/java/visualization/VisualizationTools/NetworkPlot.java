package visualization.VisualizationTools;

import com.main.network.Layers.LayerTypes;
import com.main.network.Network.Network;
import com.main.network.Network.NetworkFactory;
import com.main.network.connections.IConnection;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.NeuronTypes;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.MapTransformer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class NetworkPlot extends JFrame{

    public NetworkPlot(Network net){
        Graph<Integer, String> g = new SparseMultigraph<Integer, String>();
        // Add some vertices. From above we defined these to be type Integer.
        int numberOfLayers = net.getNumberOfLayers();
        int index = 0;
        Map<INeuron, Integer> nameMapping = new HashMap();
        Map<Integer, Double> weightMap = new HashMap();
        int edgeCount = 0;
        for(int x = 0; x < numberOfLayers; x++){
            for(int y = 0 ; y < net.getLayer(x).getNeurons().size(); y++){
                nameMapping.put(net.getLayer(x).getNeurons().get(y), index);
                g.addVertex(index);
                index++;

                for(IConnection con : net.getLayer(x).getNeurons().get(y).getBackwardsConnections()){
                    g.addEdge("Edge-" + nameMapping.get(con.getStart()).intValue() + "-"+nameMapping.get(con.getEnd()).intValue(),
                            nameMapping.get(con.getStart()).intValue(),
                            nameMapping.get(con.getEnd()).intValue(),
                            EdgeType.DIRECTED);
                    //edgeCount++;

                }

            }

        }

        // Let's see what we have. Note the nice output from the
        // SparseMultigraph<V,E> toString() method
        //System.out.println("The graph g = " + g.toString());

        // The Layout<V, E> is parameterized by the vertex and edge types
        Layout<Integer, String> layout =  new DAGLayout(g);
        layout.setSize(new Dimension(300,300)); // sets the initial size of the space
        // The BasicVisualizationServer<V,E> is parameterized by the edge types
        BasicVisualizationServer<Integer,String> vv =
                new BasicVisualizationServer<Integer,String>(layout);
        vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(vv);
        pack();
        setVisible(true);
    }

    public static void main(String[] args){
        Network net = new NetworkFactory().addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.LINEAR, 1)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.LINEAR, 3)
                .addLayer(LayerTypes.ONE_DIMENTIONAL,NeuronTypes.LINEAR,3)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.LINEAR, 1).build();
        new NetworkPlot(net);

    }
}
