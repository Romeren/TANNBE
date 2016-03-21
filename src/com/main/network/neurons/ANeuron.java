package com.main.network.neurons;

import com.main.network.connections.IConnection;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public abstract class ANeuron implements INeuron{
    private double input;
    protected double error;
    protected double output;
    protected ArrayList<IConnection> backwardsConnections;
    protected ArrayList<IConnection> forwardsConnections;


    public ANeuron(){
        this.backwardsConnections = new ArrayList<>();
        this.forwardsConnections = new ArrayList<>();
    }

    @Override
    public double getError() {
        return this.error;
    }

    @Override
    public void resetNeuron() {
        this.input = 0;
        this.output = 0;
        this.error = 0;
        for (IConnection con : forwardsConnections){
            con.resetValues();
        }
    }

    @Override
    public void startBackpropagate(double targetValue) {
        this.error = (targetValue -this.getOutput());
    }

    @Override
    public void addBackpropagationError(double propagatedError) {
        this.error+=propagatedError;
    }

    @Override
    public void addInput(double i) {
        this.input += i;
    }


    @Override
    public double getOutput() {
        return this.output;
    }

    @Override
    public void feedForward() {

        this.feedToActivationFunction(); // each child must implement the method

        for(IConnection con : forwardsConnections){
            con.feedForward(this.output);
        }
    }

    @Override
    public ArrayList<IConnection> getForwardConnections() {
        return this.forwardsConnections;
    }

    @Override
    public ArrayList<IConnection> getBackwardsConnections() {
        return  this.backwardsConnections;
    }

    protected double getInput(){
        return this.input;
    }



}
