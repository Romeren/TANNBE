package visualization.Utilz;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by EmilSebastian on 09-05-2016.
 */
public class MnistWrapper {
    public List<BufferedImage> images;
    public double[][] labels;
    public MnistWrapper(List<BufferedImage> images, double[][] labels){
        this.images = images;
        this.labels = labels;
    }
}
