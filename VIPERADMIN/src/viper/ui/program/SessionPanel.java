package viper.ui.program;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import viper.db.DBController;

public class SessionPanel extends JPanel {

	private static JFrame frame = null;
	private XYDataset dataset = null;
	private JFreeChart chart = null;
	private ChartPanel chartPanel = null;
	
	/**
	 * Create the panel.
	 */
	public SessionPanel() {
		super();
		initialize();
	}

	public SessionPanel(JFrame f) {
		super();
		frame = f;
		initialize();
	}

	private void initialize() {
		
		dataset = createDataset();
	    chart = createChart(dataset);
	    chartPanel = new ChartPanel(chart);
	    chartPanel.setBounds(80, 80, 800, 500);
	    
        
		this.setSize(1000, 700);
		this.setLayout(null);
		this.setBackground(Color.black);
		
		this.add(chartPanel);

	}
	
	private XYDataset createDataset() {
        
        XYSeries series1 = new XYSeries("Logins");
        ArrayList<Object[]> arr = DBController.retrieveSession();
        Object[] data;
        for (int i=0; i<arr.size(); i++) {
        	data = arr.get(i);
        	series1.add((int) data[1], (int) data[2]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
                
        return dataset;
        
    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Sessions by Month",  // chart title
            "Month",                      // x axis label
            "Count",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.black);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.black);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        NumberAxis rangeAxis;
        rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        rangeAxis = (NumberAxis) plot.getDomainAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
                
        return chart;
        
    }
}