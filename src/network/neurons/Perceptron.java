package network.neurons;

import network.connections.IConnection;

import java.util.Random;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public class Perceptron extends ANeuron {
    private final float THREDSHOLD = new Random().nextFloat();

    public Perceptron(){
        super();
    }

    @Override
    public void feedToActivationFunction() {
        double tmp = this.getInput();
        this.output = 0;
        while (tmp > this.THREDSHOLD){
            this.output++;
            tmp--;
        }
    }

    @Override
    public void backpropagate() {

    }
}
