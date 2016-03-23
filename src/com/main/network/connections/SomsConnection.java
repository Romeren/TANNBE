package com.main.network.connections;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.ISOMsNode;
import com.main.network.neurons.SomsNode;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public class SomsConnection implements IConnection {
    private ISOMsNode[] end;
    private INeuron start;
    private int iteration = 0;
    private int connectionNumber;

    public SomsConnection(INeuron start, ArrayList<INeuron> end, int connectionNumber){
        this.start = start;
        this.connectionNumber = connectionNumber;
        this.end = new ISOMsNode[end.size()];
        for (int n = 0 ; n < end.size(); n++){
            this.end[n] = (ISOMsNode) end.get(n);
        }
    }

    @Override
    public void resetValues() {}

    @Override
    public void feedForward(double input) {
        for (ISOMsNode n : end){
            n.addInput(input - n.getWeights()[this.connectionNumber] * (input - n.getWeights()[this.connectionNumber]));
        }
    }

    public void adjustWeights(){
        ISOMsNode candidate = end[0];
        double candidateDist = candidate.getDistance();
        int xCordinat = 0, yCordinat = 0;
        for(int x = 0 ; x < NetworkConfiguration.SOMsGridDimensions[0] ; x++ ) {
            for (int y = 0; y < NetworkConfiguration.SOMsGridDimensions[1]; y++) {
                int index = x + (y* NetworkConfiguration.SOMsGridDimensions[0]);
                ISOMsNode secoundCand =end[index];
                double secound = secoundCand.getDistance();
                if (secound < candidateDist) {
                    candidateDist = secound;
                    xCordinat = x;
                    yCordinat = y;
//                    System.out.println("X:" + x + "   Y:" + y + "    DIST:" + candidateDist);
                }
            }
        }

        iteration++;
        double radius = NetworkConfiguration.calculateNeighborhoodRadius(iteration);
        int xbegin = (int) Math.max(xCordinat - radius, 0);
        int xend = (int) Math.min( xCordinat + radius, NetworkConfiguration.SOMsGridDimensions[0]);
        int ybegin = (int) Math.max(yCordinat- radius, 0);
        int yend = (int) Math.min(  yCordinat + radius, NetworkConfiguration.SOMsGridDimensions[1]);

        for (int x = xbegin; x < xend; x++) {
            for (int y = ybegin; y < yend; y++) {
                double dist2 = Math.sqrt((x*x) + (y*y));
                double r2 = radius* radius;
                if (dist2 < r2) {
                    double influence = Math.exp(-(dist2)/(2*r2));
                    end[x+(y*NetworkConfiguration.SOMsGridDimensions[1])].adjustWeights(
                            start.getOutput(),
                            NetworkConfiguration.calculateLearningRate(iteration),
                            influence,
                            this.connectionNumber);
                }
            }
        }
    }


    @Override
    public void feedBackwards(double input) {
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
