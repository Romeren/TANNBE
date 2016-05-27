package visualization.Utilz;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by EmilSebastian on 09-05-2016.
 */
public class MnisTest {
    public static void main(String[] args) throws Exception {
        MNISTReader.getDataSet("mnistlabel","mnisttraining");
        /*
        BufferedImage img = ImageIO.read(is);
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());

        frame.getContentPane().add(new JLabel(new ImageIcon(img)));
        frame.show();
        */
    }

}
