import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class CSCAN {
    private static int[] arr;
    private static int curPos;
    private static int trkSz;

    public CSCAN(int curPos, int trkSz, int[] arr) {
        CSCAN.curPos = curPos;
        CSCAN.arr = arr;
        CSCAN.trkSz = trkSz;
    }

    public static void run(){
        int totalHeadMovement = 0;
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        ArrayList<Integer> sequence = new ArrayList<>();

        // Divide locations into two parts
        // based on the current head position
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < curPos)
                left.add(arr[i]);
            else
                right.add(arr[i]);
        }

        // Sort the locations
        Collections.sort(left);
        Collections.sort(right);

        // Traverse the right side from current head
        for (int i = 0; i < right.size(); i++) {
            int prevPos = curPos;
            curPos = right.get(i);
            sequence.add(curPos);
            totalHeadMovement += Math.abs(curPos - prevPos);
        }


        // Move to the last track
        int prevPos = curPos;
        curPos = trkSz - 1;
        totalHeadMovement += Math.abs(curPos - prevPos);
        sequence.add(curPos);

        prevPos = curPos;
        curPos = 0;  // The head is now at the start
        totalHeadMovement += Math.abs(curPos - prevPos);
        sequence.add(curPos);

// Traverse the left side from the start
        for (int i = 0; i < left.size(); i++) {
            prevPos = curPos;
            curPos = left.get(i);
            sequence.add(curPos);
            totalHeadMovement += Math.abs(curPos - prevPos);
        }

        System.out.println("Total Head Movement: " + totalHeadMovement);

        // Generate data for line plot
        double[][] data = new double[2][sequence.size()];

        // Set data points
        for (int i = 0; i < sequence.size(); i++) {
            data[0][i] = sequence.get(i);
            data[1][i] = i;
        }

        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("Head Movement", data);

        // Create line plot
        JFreeChart chart = ChartFactory.createXYLineChart(
                "CSCAN Disk Scheduling", "", "", dataset);  // Swap the x-axis and y-axis labels

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
