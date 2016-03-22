package com.main.network.Network;

import com.main.network.CommonInterfaces.IBackpropagationable;
import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.Layers.ILayer;
import com.sun.deploy.util.ArrayUtil;
import jdk.nashorn.internal.ir.LiteralNode;
import sun.plugin.javascript.navig4.Layer;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public class Network {
    protected ArrayList<ILayer> layers = new ArrayList<>();

    protected Network(){

    }

    public void supervisedTraining(double[][] trainingset, double[][] labels) throws InvalidNumberOfInput{
        // TODO: add stop criteria to the training algorithm so it will stop when no more can be learnt!
        for (int i = 0 ; i < NetworkConfiguration.maximumNumberOfSupervizedTrainingIterations; i++){
            for (int x = 0 ; x < trainingset.length; x++){
                feedInputThroughNetwork(trainingset[x]);
                backpropagateNetwork(labels[x]);
            }
        }
    }

    public void backpropagateNetwork(double[] expectedValues) throws InvalidNumberOfInput {
        ILayer lay = this.layers.get(layers.size() - 1);

        if(!(lay instanceof IBackpropagationable)){
            return;
        }

        ((IBackpropagationable)lay).startBackpropagate(expectedValues);
        for(int i = layers.size()-1; i > 0; i--){
            lay = layers.get(i);
            if(!(lay instanceof IBackpropagationable)) return;
            ((IBackpropagationable)lay).backpropagate();
        }
        resetNetwork();
    }

    public double[] feedInputThroughNetwork(double[] input) throws InvalidNumberOfInput{
        addInputToVisibleLayer(input);
        feedForward();
        ArrayList<Double> result = new ArrayList<>();
        return getOutputFromOutputLayer();
    }

    public double[] feedInputThroughNetwork(ArrayList<Double> input) throws InvalidNumberOfInput{
        addInputToVisibleLayer(input);
        feedForward();
        return getOutputFromOutputLayer();
    }


    public void addInputToVisibleLayer(ArrayList<Double> input) throws InvalidNumberOfInput {
        layers.get(0).addInputToLayer(input.stream().mapToDouble(Double::doubleValue).toArray());
    }

    public void addInputToVisibleLayer(double[] input) throws InvalidNumberOfInput {
        layers.get(0).addInputToLayer(input);

    }

    public double[] getOutputFromOutputLayer(){
        return layers.get(layers.size()-1).getOutput();
    }

    public void feedForward(){
        for (ILayer layer: layers){
            layer.feedForward();
        }
    }

    public void resetNetwork(){
        for(ILayer layer: this.layers){
            layer.resetLayer();
        }
    }

    public void addLayer(ILayer layer){
        layers.add(layer);
    }

    public ILayer getLayer(int index){
        return this.layers.get(index);
    }

    public int getNumberOfLayers (){
        return this.layers.size();
    }
}
