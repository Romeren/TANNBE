package com.main.network.Network;

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

    public void backpropagateNetwork(double[] expectedValues) throws InvalidNumberOfInput {
        this.layers.get(layers.size()-1).startBackpropagate(expectedValues);
        for(int i = layers.size()-1; i > 0; i--){
            layers.get(i).backpropagate();
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

}
