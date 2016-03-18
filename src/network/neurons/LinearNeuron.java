package network.neurons;

import java.util.Random;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public class LinearNeuron extends ANeuron {
    private  final float bias = new Random().nextFloat();

    @Override
    public void feedToActivationFunction() {
        this.output = getInput() + bias;
    }

    @Override
    public void backpropagate() {

    }
}
