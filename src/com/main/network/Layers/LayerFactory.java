package com.main.network.Layers;

import com.main.network.neurons.*;

import java.util.concurrent.ExecutorService;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public class LayerFactory {

    public static ILayer createLayerMultithreaded(LayerTypes type, int numberOfNeurons, NeuronTypes neuronTypes, ExecutorService pool){
        ILayer lay = createLayer(type,numberOfNeurons,neuronTypes);
        lay.setExecutorService(pool);
        return lay;
    }

    public static ILayer createLayer(LayerTypes type, int numberOfNeurons, NeuronTypes neuronType){
        ILayer layer;
        switch (type){
            case ONE_DIMENTIONAL:
                layer = new OneDimentionalLayer();
                //layer.setNeuronType(neuronType);
                break;
            case TWO_DIMENTIONAL:
                return null;
            case SOMSLAYER:
                layer = new SomsLayer();
                break;
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
            case SOMS:
                for(int i = 0; i < numberOfNeurons; i++){
                    layer.addNeuron(new SomsNode());
                }
                break;
        }
    }

}
