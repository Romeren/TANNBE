package visualization.VisualizationTools;


import com.main.network.Layers.LayerTypes;
import com.main.network.Network.Network;
import com.main.network.Network.NetworkConfiguration;
import com.main.network.Network.NetworkFactory;
import com.main.network.neurons.NeuronTypes;
import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapGrayscale;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public class RegressionPlot3D extends AbstractAnalysis {
    private Mapper function;
    public Network net;
    public RegressionPlot3D(Network net){
        this.net = net;
    }
    public RegressionPlot3D(Mapper function){
        this.function = function;
    }
    public RegressionPlot3D(Network net, Mapper function){
        this.function = function;
        this.net = net;
    }


    public void init() {
        // Define a function to plot
        Mapper function2 = null;
        if(function == null) {
            this.function = new Mapper() {
                @Override
                public double f(double v, double v1) {
                    double out = 0;
                    try {
                        out = net.feedInputThroughNetwork(new double[]{v, v1,1})[0];
                    }catch (Exception e){
                        e.printStackTrace();
                        out = 0;
                    }
                    net.resetNetwork();
                    return out;
                }
            };
        }
        else if( net != null){
            function2 = new Mapper() {
                @Override
                public double f(double v, double v1) {
                    double out = 0;
                    try {
                        out = net.feedInputThroughNetwork(new double[]{v, v1,1})[0];
                        net.resetNetwork();
                    }catch (Exception e){
                        e.printStackTrace();
                        out = 0;
                    }
                    net.resetNetwork();
                    return out;
                }
            };
        }

        // Define range and precision for the function to plot
        Range range = new Range(-1, 1);
        int steps = 80;

        // Create the object to represent the function over the given range.
        final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), function);
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);

        // Create a chart
        chart = AWTChartComponentFactory.chart(Quality.Advanced, getCanvasType());
        chart.getScene().getGraph().add(surface);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        if(function2 != null){
            final Shape surface2 =  Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), function2);
            surface2.setFaceDisplayed(true);
            surface2.setWireframeDisplayed(false);
            chart.getScene().getGraph().add(surface2);
            surface2.setColorMapper(new ColorMapper(new ColorMapGrayscale(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        }

    }

    /*
            For testing!
     */
    public static void main(String[] args) throws Exception {
        NetworkConfiguration.maximumNumberOfSupervizedTrainingIterations =1000;
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
        //Example function:
        //AnalysisLauncher.open(new RegressionPlot3D(func));

        Network net = new NetworkFactory()
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.INPUT, 3)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.SIGMA, 10)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.LINEAR, 1)
                .build();

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
        System.out.println("Training started");
        net.supervisedTraining(training, labels);
        System.out.println("Training Done");
        //AnalysisLauncher.open(new RegressionPlot3D(net, func));
        AnalysisLauncher.open(new RegressionPlot3D(net));
    }
}

