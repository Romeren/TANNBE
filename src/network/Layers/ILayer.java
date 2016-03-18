package network.Layers;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public interface ILayer {
    void feedForward();
    ArrayList<Double> getOutput();
    void resetLayer();
    void addInputToLayer(ArrayList<Double> input);
    void backpropagate();
}
