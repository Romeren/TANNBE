package network.neurons;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public abstract class ANeuron implements INeuron{
    private double input;
    private double output;

    public ANeuron(){}

    @Override
    public void resetNeuron() {
        this.input = 0;
        this.output = 0;
    }

    @Override
    public void addInput(double i) {
        this.input += i;
    }

    @Override
    public double getOutput() {
        return this.output;
    }
}
