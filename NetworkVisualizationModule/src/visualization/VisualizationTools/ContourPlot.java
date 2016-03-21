package visualization.VisualizationTools;

import com.main.network.Layers.LayerTypes;
import com.main.network.Network.Network;
import com.main.network.Network.NetworkFactory;
import com.main.network.connections.IConnection;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.NeuronTypes;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ColorBar;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.ColorPalette;
import org.jfree.chart.plot.RainbowPalette;
import org.jfree.data.contour.ContourDataset;
import org.jfree.data.contour.DefaultContourDataset;
import org.jfree.data.contour.NonGridContourDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class ContourPlot extends  ApplicationFrame {
    //User - Settings
    final String title = "Contour Plot";
    final String xAxisLabel = "X Values";
    final String yAxisLabel = "Y Values";
    final String zAxisLabel = "Color Values";

    //axis & colorBar
    private NumberAxis xAxis = null;
    private NumberAxis yAxis = null;
    private ColorBar zColorBar = null;

    //temp data for init Contour Data
    private double[] tmpDoubleY = null;
    private double[] tmpDoubleX = null;
    private double[] tmpDoubleZ = null;
    private INeuron neuron;

    public ContourPlot(String title, INeuron neuron) {
        super(title);

        this.neuron = neuron;

        final JFreeChart chart = createContourPlot();
        final ChartPanel panel = new ChartPanel(chart, true, true, true, true, true);

        panel.setPreferredSize(new java.awt.Dimension(500, 300));
        panel.setMaximumDrawHeight(100000); //stop chartpanel from scaling
        panel.setMaximumDrawWidth(100000); //stop chartpanel from scaling
        panel.setFillZoomRectangle(false);
        setContentPane(panel);
    }

    public static void main(final String[] args) {
        Network net = new NetworkFactory().addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.PERCEPTRON, 100)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.PERCEPTRON, 1).build();

        final ContourPlot demo = new ContourPlot("Neuron", net.getLayer(1).getNeurons().get(0));
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

    private JFreeChart createContourPlot() {
        //plot을 만들기위한 삼대요소인 엑시스와 데이터와 컬러바를 만든다.
        makeAxisNColorBar();
        final ContourDataset data = makeData();

        final org.jfree.chart.plot.ContourPlot plot = new org.jfree.chart.plot.ContourPlot(data, this.xAxis, this.yAxis, this.zColorBar);

        //플롯세팅. 플롯의 두께 조절한다. 클 수록 가로가 좁아진다. (상대적으로 세로가 길어보임)
        //plot.setDataAreaRatio(1);

        //점만 찍는 것.
        //plot.setRenderAsPoints(true);

        final JFreeChart chart = new JFreeChart(title, null, plot, false);
        chart.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 0, 1000, Color.green));
        return chart;
    }

    private void makeAxisNColorBar() {
        //생성
        xAxis = new NumberAxis(xAxisLabel);
        yAxis = new NumberAxis(yAxisLabel);
        zColorBar = new ColorBar(zAxisLabel);

        //설정
        //this.xAxis.setAutoRangeIncludesZero(false);
        //this.xAxis.setInverted(xIsInverted);
        this.xAxis.setLowerMargin(0.0);
        this.xAxis.setUpperMargin(0.0);
        this.yAxis.setLowerMargin(0.0);
        this.yAxis.setUpperMargin(0.0);
        this.xAxis.setAutoRangeIncludesZero(false);
        this.yAxis.setAutoRangeIncludesZero(false);

        //이거 조절하면 끝기준으로 잘라서 보인다.
        this.xAxis.setRange(1, 15.5);
        this.yAxis.setRange(1, 15.5);

        //this.xAxis.setInverted(true);

        //컬러바 세팅
        this.zColorBar.getAxis().setTickMarksVisible(true);


        ColorPalette cp = new RainbowPalette();
        double x[] = {0, 3, 5, 7, 10, 13, 15, 17, 20, 23, 25};
        cp.setTickValues(x);
        //일정 단계로 딱딱 자른다.
        cp.setStepped(true);
        //

        zColorBar.setColorPalette(cp);

        zColorBar.setMinimumValue(0);
        zColorBar.setMaximumValue(25);


    }

    private ContourDataset makeData() {
        ArrayList<IConnection> cons =this.neuron.getBackwardsConnections();

        int dim = 1;
        while (dim*dim < cons.size()){
            dim++;
        }
        if(dim*dim != cons.size()){
            return null;
        }

        //이때 x,y값을 조금이라도 잘못잡아주면 그래프가 완전 뒤틀린다.
        /** Number of x intervals. */
        int numX = dim;
        /** Number of y intervals. */
        int numY = dim;

        //first_GetNumber
        GetNumber(numX,numY);
        //second_giveNumber
        final Double[] oDoubleX = (Double[]) DefaultContourDataset.formObjectArray(this.tmpDoubleX);
        final Double[] oDoubleY = (Double[]) DefaultContourDataset.formObjectArray(this.tmpDoubleY);
        final Double[] oDoubleZ = (Double[]) DefaultContourDataset.formObjectArray(this.tmpDoubleZ);

        ContourDataset data = null;

        //data = new DefaultContourDataset("Contouring", oDoubleX, oDoubleY, oDoubleZ);

        /** Number of y intervals. */
        int power = 10000;
        //data = new NonGridContourDataset("Contouring", oDoubleX, oDoubleY, oDoubleZ);//,  numX, numY, power);
        data = new NonGridContourDataset("Contouring", oDoubleX, oDoubleY, oDoubleZ, numX, numY, power);
        //data = new DefaultContourDataset("Contouring", oDoubleX, oDoubleY, oDoubleZ);//,  numX, numY, power);
        return data;
    }


    private void GetNumber(int dimX, int dimY) {
        double[] tmpDoubleY = new double[dimX * dimY];

        double value = 1;
        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
                tmpDoubleY[dimX * i + j] = value;
            }
            value = value + 0.5;
        }
        this.tmpDoubleY = tmpDoubleY;

        double[] tmpDoubleX = new double[900];
        value = 1;
        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
                tmpDoubleX[dimY * i + j] = value;
                value = value + 0.5;
            }
            value = 1;
        }
        this.tmpDoubleX = tmpDoubleX;

        final double[] tmpDoubleZ =
                {
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6, 6, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 6, 6, 9, 9, 9, 9, 9, 9, 6, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 6, 6, 9, 9, 11, 11, 11, 11, 9, 9, 6, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,

                        1, 1, 1, 6, 6, 9, 9, 11, 11, 19, 19, 11, 11, 9, 9, 6, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 6, 9, 9, 11, 11, 19, 19, 19, 19, 11, 11, 9, 9, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 6, 9, 11, 11, 19, 19, 21, 21, 19, 19, 11, 11, 9, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 6, 9, 11, 19, 19, 21, 25, 25, 21, 19, 19, 11, 9, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 6, 9, 11, 19, 21, 25, 25, 25, 25, 21, 19, 11, 9, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,

                        1, 1, 1, 6, 9, 11, 19, 21, 25, 25, 25, 25, 21, 19, 11, 9, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 6, 9, 11, 19, 19, 21, 25, 25, 21, 19, 19, 11, 9, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 6, 9, 11, 11, 19, 19, 21, 21, 19, 19, 11, 11, 9, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 6, 9, 9, 11, 11, 19, 19, 19, 19, 11, 11, 9, 9, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 6, 6, 9, 9, 11, 11, 19, 19, 11, 11, 9, 9, 6, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,

                        1, 1, 1, 1, 6, 6, 9, 9, 11, 11, 11, 11, 9, 9, 6, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 6, 6, 9, 9, 9, 9, 9, 9, 6, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6, 6, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,

                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,

                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,


                }; //12.0, 0.0};  // add values to fill entire lake surface
        this.tmpDoubleZ = tmpDoubleZ;
    }
}