package visualization.Utilz;

/**
 * Created by EmilSebastian on 09-05-2016.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MNISTReader {
    public List<BufferedImage> readImages(String fileName) throws IOException, Exception {
        try(DataInputStream stream = new DataInputStream(new FileInputStream(fileName))) {
            int magicNumber = stream.readInt();
            if (magicNumber != 2051) {
                throw new Exception("Wrong magic number: " + magicNumber + "; expected: 2051");
            }

            int imageCount = stream.readInt();
            int rowCount = stream.readInt();
            int columnCount = stream.readInt();
            imageCount = 10000;
            List<BufferedImage> allImages = new ArrayList<>();

            for (int i = 0; i < imageCount; i++) {
                if (stream.available() <= 0) {
                    throw new Exception("The file contains less than " + imageCount + " images");
                }
                BufferedImage bi = new BufferedImage(columnCount, rowCount, 1);
                for (int column = 0; column < columnCount; column++) {
                    for (int row = 0; row < rowCount; row++) {
                        bi.setRGB(column, row, stream.readUnsignedByte());
                        //allImages[i][column * rowCount + row] = stream.readUnsignedByte();
                    }
                }
                allImages.add(bi);
            }

            return allImages;
        }
    }

    public double[][] readLabels(String fileName) throws IOException, Exception {
        try(DataInputStream stream = new DataInputStream(new FileInputStream(fileName))) {

            int magicNumber = stream.readInt();
            if (magicNumber != 2049) {
                throw new Exception("Wrong magic number: " + magicNumber + "; expected: 2049");
            }

            int labelCount = stream.readInt();

            double[][] allLabels = new double[labelCount][10];

            for (int i = 0; i < labelCount; i++) {
                if (stream.available() <= 0) {
                    throw new Exception("The file contains less than " + labelCount + " images");
                }

                byte label = stream.readByte();
                allLabels[i][label % 10] = 1;
            }

            return allLabels;
        }
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public static MnistWrapper getDataSet(String labelsFileName, String imagesFileName) throws IOException, Exception {
        MNISTReader mnistReader = new MNISTReader();

        List<BufferedImage> images = mnistReader.readImages(imagesFileName);

        for(int i = 0; i < images.size(); i++){
            images.set(i, resize(images.get(i), 15,15));
        }

        double[][] labels = mnistReader.readLabels(labelsFileName);
        return new MnistWrapper(images,labels);
    }
}