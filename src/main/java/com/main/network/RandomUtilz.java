package com.main.network;

import java.util.Random;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class RandomUtilz {
    public static Random rd = new Random();

    public static double getDoubleInRange(double rangeMin, double rangeMax){
        return (rangeMin + (rangeMax - rangeMin) * rd.nextDouble());
    }


}
