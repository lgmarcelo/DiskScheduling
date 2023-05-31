import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

import java.awt.*;
import java.util.*;

public class CLOOK {

    private static int[] arr;
    private static int curPos;
    private static int trkSz;

    public CLOOK(int curPos, int trkSz, int[] arr) {
        this.curPos = curPos;
        this.trkSz = trkSz;
        this.arr = arr;
    }

    public static void run(){
        int seek_count = 0;
        int distance, cur_track;
        Vector<Integer> left = new Vector<>();
        Vector<Integer> right = new Vector<>();
        Vector<Integer> seek_sequence = new Vector<>();
        // Add current position to the start of the sequence
        seek_sequence.add(0, curPos);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < curPos) {
                left.add(arr[i]);
            }
            if (arr[i] > curPos) {
                right.add(arr[i]);
            }
        }

        Collections.sort(left);
        Collections.sort(right);

        for (int i = 0; i < right.size(); i++) {
            cur_track = right.get(i);
            seek_sequence.add(cur_track);
            distance = Math.abs(cur_track - curPos);
            seek_count += distance;
            curPos = cur_track;
        }

        seek_count += Math.abs(curPos - left.get(0));
        curPos = left.get(0);

        for (int i = 0; i < left.size(); i++) {
            cur_track = left.get(i);
            seek_sequence.add(cur_track);
            distance = Math.abs(cur_track - curPos);
            seek_count += distance;
            curPos = cur_track;
        }




        System.out.println("Total Head Movement: " + seek_count);

        System.out.println("Seek Sequence is");

        for (Integer integer : seek_sequence) {
            System.out.println(integer);
        }

        // Generate data for line plot
        double[][] data = new double[2][seek_sequence.size()];

// Set data points
        for (int i = 0; i < seek_sequence.size(); i++) {
            data[0][i] = seek_sequence.get(i);
            data[1][i] = i;
        }

        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("Head Movement", data);

// Create line plot
        JFreeChart chart = ChartFactory.createXYLineChart(
                "CLOOK Disk Scheduling", "", "", dataset);  // Swap the x-axis and y-axis labels

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
