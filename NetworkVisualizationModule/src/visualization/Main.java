package visualization;

import com.main.network.Layers.LayerTypes;
import com.main.network.Network.Network;
import com.main.network.Network.NetworkFactory;
import com.main.network.neurons.NeuronTypes;


/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class Main {

    public static void main(String[] args){
        Network net = new NetworkFactory().addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.PERCEPTRON, 3)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.PERCEPTRON, 1).build();

        //new NetworkPlot(net);

        //new NeuronVisualization(net.getLayer(1).getNeurons().get(0));

    }

}
