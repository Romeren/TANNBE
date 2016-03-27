package com.main.network.Layers;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.neurons.IBackwardsFeed;
import com.main.network.neurons.INeuron;
import com.sun.corba.se.spi.orb.Operation;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public abstract class ALayer implements ILayer {
    protected ArrayList<INeuron> neurons = new ArrayList<>();
    protected ExecutorService pool = null;

    public ALayer(){}

    @Override
    public void setExecutorService(ExecutorService pool) {
        this.pool = pool;
    }

    @Override
    public ArrayList<INeuron> getNeurons() {
        return this.neurons;
    }

    @Override
    public void feedBackward() {
        if(pool != null){
            For(neurons, pParameter -> ((IBackwardsFeed) pParameter).feedBackwards());
        }else {
            for (INeuron n : this.neurons) {
                if(n instanceof IBackwardsFeed) {
                    ((IBackwardsFeed) n).feedBackwards();
                }
            }
        }
    }

    @Override
    public void backpropagateRBM() {
        if(pool != null){
            For(neurons, pParameter -> ((IBackwardsFeed)pParameter).unsupervizedRBM());
        }else {
            for (INeuron n : this.neurons) {
                if(n instanceof IBackwardsFeed) {
                    ((IBackwardsFeed) n).unsupervizedRBM();
                }
            }
        }
    }

    @Override
    public void feedForward() {
        if(pool != null){
            For(neurons, pParameter -> pParameter.feedForward());
        }else {
            for (INeuron n : this.neurons) {
                n.feedForward();
            }
        }
    }

    @Override
    public double[] getOutput() {
        double[] output = new double[this.neurons.size()];
        for (int i = 0; i < neurons.size(); i++){
            output[i] = neurons.get(i).getOutput();
        }

        return output;
    }

    @Override
    public void resetLayer() {
        if(pool != null){
            For(neurons, pParameter -> pParameter.resetNeuron());
        }
        for (INeuron n : neurons){
            n.resetNeuron();
        }
    }

    @Override
    public void addInputToLayer(double[] input) throws InvalidNumberOfInput {
        if(input.length != this.neurons.size()){
            throw new InvalidNumberOfInput();
        }
        for (int i = 0 ; i < neurons.size(); i++){
            neurons.get(i).addInput(input[i]);
        }
    }

    @Override
    public void addNeuron(INeuron neuron) {
        this.neurons.add(neuron);
    }

    protected <T> void For(final Iterable<T> elements, final Operation<T> operation) {
        try {
            // invokeAll blocks for us until all submitted tasks in the call complete
            pool.invokeAll(createCallables(elements, operation));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private <T> Collection<Callable<Void>> createCallables(final Iterable<T> elements, final Operation<T> operation) {
        List<Callable<Void>> callables = new LinkedList();
        for (final T elem : elements) {
            callables.add(new Callable<Void>() {
                @Override
                public Void call() {
                    operation.perform(elem);
                    return null;
                }
            });
        }

        return callables;
    }
    protected interface Operation<T> {
        void perform(T pParameter);
    }
}