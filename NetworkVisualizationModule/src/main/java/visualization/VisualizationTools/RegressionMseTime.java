package visualization.VisualizationTools;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.Layers.LayerTypes;
import com.main.network.Network.Network;
import com.main.network.Network.NetworkConfiguration;
import com.main.network.Network.NetworkFactory;
import com.main.network.neurons.NeuronTypes;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jzy3d.plot3d.builder.Mapper;
import visualization.Utilz.JfreeChartFrame;

import java.awt.*;

/**
 * Created by EmilSebastian on 25-05-2016.
 */
public class RegressionMseTime {
    public RegressionMseTime(){//Network net, double xmin, double xmax, double xinterval) {
        XYSeriesCollection mse = new XYSeriesCollection(); //getDataSet(net,xmin,xmax,xinterval);
        XYSeriesCollection time = new XYSeriesCollection(); //getDataSet(net,xmin,xmax,xinterval);

        XYSeries MSE1000 = new XYSeries("MSE with 1.000 Epochs");
        XYSeries MSE10000 = new XYSeries("MSE with 10.000 Epochs");
        XYSeries MSE100000 = new XYSeries("MSE with 25.000 Epochs");
        XYSeries time1000 = new XYSeries("Computational time with 1.000 Epochs");
        XYSeries time10000 = new XYSeries("Computational time with 10.000 Epochs");
        XYSeries time100000 = new XYSeries("Computational time with 25.000 Epochs");

        NetworkConfiguration.maximumNumberOfSupervizedTrainingIterations = 25000;
        NetworkConfiguration.minimumInitializedLinearBias = 0.6;
        NetworkConfiguration.maximumInitializedLinearBias = 1;
        NetworkConfiguration.minimumInitializedConnectionWeight = 0.99;
        NetworkConfiguration.maximumInitializedConnectionWeight = 1;
        NetworkConfiguration.learningRate= 0.2;

        Mapper func = new Mapper() {
            @Override
            public double f(double v, double v1) {
                return Math.exp(-((v*v)+(v1*v1))/0.1);
            }
        };

        double[][] training = new double[40*40][3];
        double[][] labels = new double[40*40][1];
        int index = 0;
        for (double x = -1; x < 1; x += 0.05){
            for (double y = -1; y < 1; y+=0.05){
                training[index][0] = x;
                training[index][1] = y;
                training[index][2] = 1;
                labels[index][0] = func.f(x,y);
                index++;
            }
        }

        for(int i = 1; i <= 10; i++){
            Network nets = new NetworkFactory().addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.INPUT, 3)
                    .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.SIGMA, (i))
                    .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.SIGMA, (i))
                    .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.LINEAR, 1).build();

            try {
                double[][] data = nets.meanSquaredWithTime(training,labels);
                MSE1000.add((i),data[0][0]);
                time1000.add((i),data[0][1]/1000);
                MSE10000.add((i),data[1][0]);
                time10000.add((i),data[1][1]/1000);
                MSE100000.add((i),data[2][0]);
                time100000.add((i),data[2][1]/1000);
            } catch (InvalidNumberOfInput invalidNumberOfInput) {
                invalidNumberOfInput.printStackTrace();
            }
        }

        mse.addSeries(MSE1000);
        mse.addSeries(MSE10000);
        mse.addSeries(MSE100000);
        time.addSeries(time1000);
        time.addSeries(time10000);
        time.addSeries(time100000);

        XYPlot plot = new XYPlot();
        plot.setDataset(0, mse);
        plot.setDataset(1, time);

        plot.setRenderer(0, new XYSplineRenderer());//use default fill paint for first series
        XYSplineRenderer splinerenderer = new XYSplineRenderer();
        splinerenderer.setSeriesFillPaint(0, Color.BLUE);
        plot.setRenderer(1, splinerenderer);
        plot.setRangeAxis(0, new NumberAxis("Mean Squared Error"));
        plot.setRangeAxis(1, new NumberAxis("Processing Time (Sec)"));
        plot.setDomainAxis(new NumberAxis("Number of Neurons"));

        //Map the data to the appropriate axis
        plot.mapDatasetToRangeAxis(0, 0);
        plot.mapDatasetToRangeAxis(1, 1);

        JFreeChart chart = new JFreeChart( "Performance with 1.000, 10.000 and 25.000 number of epochs", Font.getFont("Arial") , plot, true);
        new JfreeChartFrame(chart, "Regression Chart");
    }

    public static void main(String[] args){
        new RegressionMseTime();
    }

}
