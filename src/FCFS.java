import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

import java.awt.*;

public class FCFS {

    private static int[] arr;
    private static int curPos;

    public FCFS(int curPos, int[] arr) {
        FCFS.curPos = curPos;
        FCFS.arr = arr;
    }

    public static void run(){
        int totalHeadMovement = 0;

        // Calculate head movement
        for (int i = 0; i < arr.length; i++) {
            totalHeadMovement += Math.abs(curPos - arr[i]);
            curPos = arr[i];
        }

        System.out.println("Total Head Movement: " + totalHeadMovement);

        // Generate data for line plot
        double[][] data = new double[2][arr.length];

        // Set data points
        for (int i = 0; i < arr.length; i++) {
            data[0][i] = arr[i];
            data[1][i] = i;
        }

        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("Head Movement", data);

        // Create line plot
        JFreeChart chart = ChartFactory.createXYLineChart(
                "FCFS Disk Scheduling", "", "", dataset);  // Swap the x-axis and y-axis labels

        XYPlot plot = chart.getXYPlot();
        plot.getRenderer().setSeriesPaint(0, Color.BLUE);
        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));

        // Customize x-axis to display whole numbers and place it on the top
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        domainAxis.setTickMarkInsideLength(4);  // Adjust the tick mark length
        domainAxis.setAxisLineVisible(false);  // Hide the x-axis line

        // Move the x-axis labels to the top
        plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);

        // Customize y-axis to display whole numbers, reverse the range, and place it on the left side
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setTickMarkInsideLength(4);  // Adjust the tick mark length
        rangeAxis.setAxisLineVisible(false);  // Hide the y-axis line
        rangeAxis.setInverted(true);  // Reverse the range

        // Display line plot
        ChartFrame frame = new ChartFrame("Disk Scheduling Visualization", chart);
        frame.pack();
        frame.setVisible(true);
    }
}
