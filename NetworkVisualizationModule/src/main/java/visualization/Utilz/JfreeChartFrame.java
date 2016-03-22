package visualization.Utilz;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;



/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class JfreeChartFrame extends ChartFrame {

    public JfreeChartFrame(JFreeChart c, String chartname){
        super(chartname, c);
        setVisible(true);
        setSize(300, 300);
    }
}
