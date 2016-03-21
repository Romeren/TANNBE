package com.main.network.Layers;

import com.main.network.neurons.*;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public class LayerFactory {

    public static ILayer createLayer(LayerTypes type, int numberOfNeurons, NeuronTypes neuronType){
        ILayer layer;
        switch (type){
            case ONE_DIMENTIONAL:
                layer = new OneDimentionalLayer();
                layer.setNeuronType(neuronType);
                break;
            case TWO_DIMENTIONAL:
                return null;
            default:
                layer=null;
        }
        addNeuronsToLayer(layer, numberOfNeurons, neuronType);
        return layer;
    }

    private static void addNeuronsToLayer(ILayer layer, int numberOfNeurons, NeuronTypes type){
        if(layer ==null){
            return;
        }
        switch (type){
            case PERCEPTRON:
                for(int i = 0; i < numberOfNeurons; i++){
                    layer.addNeuron(new Perceptron());
                }
                break;
            case LINEAR:
                for(int i = 0; i < numberOfNeurons; i++){
                    layer.addNeuron(new LinearNeuron());
                }
                break;
            case SIGMA:
                for(int i = 0; i < numberOfNeurons; i++){
                    layer.addNeuron(new SigmaNeuron());
                }
                break;
            case RBM:
                for(int i = 0; i < numberOfNeurons; i++){
                    layer.addNeuron(new RBMNeuron());
                }
                break;
        }
    }

}
