package network.connections;

import network.neurons.INeuron;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public abstract class AConnection implements  IConnection{
    private static final double UNCHANGEDUPDATEDWEIGHT = Double.MIN_VALUE;
    private double weight;
    private double updatedWeight = UNCHANGEDUPDATEDWEIGHT;
    private INeuron start;
    private INeuron end;

    public AConnection(double weight, INeuron start, INeuron end){
        this.weight = weight;
        this.start = start;
        this.end = end;
    }

    @Override
    public void resetValues() {
        if(updatedWeight != UNCHANGEDUPDATEDWEIGHT){
            this.weight = updatedWeight;
            this.updatedWeight = UNCHANGEDUPDATEDWEIGHT;
        }
    }

    @Override
    public void feedForward(double input) {

    }

    @Override
    public void feedBackwards(double input) {

    }
}
