package com.main.network.connections;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.SomsNode;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public class SomsConnection implements IConnection<double[], Double> {
    private ArrayList<SomsNode> end;
    private INeuron start;
    private int[] dims = {10,10};

    public SomsConnection(INeuron start, ArrayList<SomsNode> end, int[] dims){
        this.start = start;
        this.end = end;
        this.dims = dims;
    }

    @Override
    public void resetValues() {}

    @Override
    public void feedForward(double[] input) {
        SomsNode candidate = end.get(0);
        double candidateDist = candidate.calculateDistance(input);
        int xCordinat = 0, yCordinat = 0;
        for(int x = 0 ; x < dims[0] ; x++ ) {
            for (int y = 0; y < dims[1]; y++) {
                SomsNode secoundCand =end.get(x + (y* dims[0]));
                double secound = secoundCand.calculateDistance(input);
                if (secound < candidateDist) {
                    candidate = secoundCand;
                    candidateDist = secound;
                }
            }
        }

        int radius = 0;
        int xbegin = Math.max(xCordinat - radius, 0);
        int xend = Math.min( xCordinat + radius, dims[0]);
        int ybegin = Math.max(yCordinat- radius, 0);
        int yend = Math.min(  yCordinat + radius, dims[1]);

        for (int x = xbegin; x < xend; x++) {
            for (int y = ybegin; y < yend; y++) {
                double dist2 = Math.sqrt((x*x) + (y*y));
                double r2 = radius* radius;
                if (dist2 < r2) {
                    double influence = Math.exp(-(dist2)/(2*r2));
                    end.get(x+(y*dims[1])).adjustWeights(input, NetworkConfiguration.SOMsLearningRate, influence);
                }
            }
        }
    }

    @Override
    public void feedBackwards(Double input) {
        start.addInput(input);
    }

    @Override
    public void backpropagate() {

    }

    @Override
    public void unsupervizedRBM() {

    }

    @Override
    public double getWeight() {
        return 0;
    }
}
