package com.main.network.Layers;

import com.main.network.CommonInterfaces.IBackpropagationable;
import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.NeuronTypes;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public class OneDimentionalLayer extends ALayer implements IBackpropagationable{
    public OneDimentionalLayer(){}

    @Override
    public void backpropagate() {
        for (INeuron n : neurons){
            n.backpropagate();
        }
    }

    @Override
    public void startBackpropagate(double[] expectedValues) throws InvalidNumberOfInput {
        if(expectedValues.length != neurons.size()){
            throw new InvalidNumberOfInput();
        }
        for (int i = 0; i < neurons.size(); i++){
            neurons.get(i).startBackpropagate(expectedValues[i]);
        }
    }
}
