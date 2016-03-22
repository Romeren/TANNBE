package visualization.VisualizationTools;

import com.main.network.Layers.LayerTypes;
import com.main.network.Network.Network;
import com.main.network.Network.NetworkFactory;
import com.main.network.connections.IConnection;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.NeuronTypes;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import visualization.Utilz.JfreeChartFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class NeuronVisualization{

    public NeuronVisualization(INeuron neuron){
        DefaultCategoryDataset lines = new DefaultCategoryDataset();

        ArrayList<IConnection> compareTo = (ArrayList<IConnection>) neuron.getBackwardsConnections().clone();

        int counter = 0;
        final XYSeriesCollection dataset = new XYSeriesCollection();
        for(IConnection con1 : neuron.getBackwardsConnections()){
            compareTo.remove(con1);
            for (IConnection con2: compareTo){
                double w1 = con1.getWeight();
                double w2 = con2.getWeight();
                XYSeries tmp = new XYSeries(counter);
                tmp.add(0,1/2 * w2);
                tmp.add(2,(-(w1/w2)*2)+(0.5*w2));
                dataset.addSeries(tmp);
                counter++;
            }
        }

        JFreeChart chart = ChartFactory.createXYLineChart("Neuron", "X","Y", dataset, PlotOrientation.VERTICAL, true, true, false);

        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
        //      legend.setDisplaySeriesShapes(true);

        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        new JfreeChartFrame(chart, "Neuron");
    }

    public static void main(String[] args){
        Network net = new NetworkFactory().addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.SIGMA, 4)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.PERCEPTRON, 1).build();

        new NeuronVisualization(net.getLayer(1).getNeurons().get(0));
    }
}
