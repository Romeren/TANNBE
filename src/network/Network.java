package network;

import network.Layers.ILayer;
import network.Layers.LayerFactory;
import network.Layers.LayerTypes;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public class Network {
    private ArrayList<ILayer> layers = new ArrayList<>();

    
    public ArrayList<Double> feedInputThroughNetwork(ArrayList<Double> input){
        addInputToVisibleLayer(input);
        feedForward();
        return getOutputFromOutputLayer();
    }

    public void addInputToVisibleLayer(ArrayList<Double> input){
        layers.get(0).addInputToLayer(input);
    }

    public ArrayList<Double> getOutputFromOutputLayer(){
        return layers.get(layers.size()).getOutput();
    }

    public void feedForward(){
        for (ILayer layer: layers){
            layer.feedForward();
        }
    }

    public void addLayer(ILayer layer){
        layers.add(layer);
    }

}
