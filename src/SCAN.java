//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartFrame;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
//import java.util.*;
//
//public class SCAN {
//    private static int[] arr;
//    private static int curPos;
//    private static int trkSz;
//    private static String direction;
//
//    public SCAN(int curPos, int trkSz, int[] arr, String direction) {
//        SCAN.curPos = curPos;
//        SCAN.trkSz = trkSz;
//        SCAN.arr = arr;
//        SCAN.direction = direction;
//    }
//
//    public static void run() {
//        int seek_count = 0;
//        int distance, cur_track;
//        Vector<Integer> left = new Vector<Integer>(),
//                right = new Vector<Integer>();
//        Vector<Integer> seek_sequence = new Vector<Integer>();
//
//        if (direction.equalsIgnoreCase("L"))
//            left.add(0);
//        else if (direction.equalsIgnoreCase("R"))
//            right.add(trkSz - 1);
//
//        for (int i = 0; i < arr.length; i++)
//        {
//            if (arr[i] < curPos)
//                left.add(arr[i]);
//            if (arr[i] > curPos)
//                right.add(arr[i]);
//        }
//
//        Collections.sort(left);
//        Collections.sort(right);
//
//        int run = 2;
//        while (run-- > 0)
//        {
//            if (direction.equalsIgnoreCase("L"))
//            {
//                for (int i = left.size() - 1; i >= 0; i--)
//                {
//                    cur_track = left.get(i);
//                    seek_sequence.add(cur_track);
//                    distance = Math.abs(cur_track - curPos);
//                    seek_count += distance;
//                    curPos = cur_track;
//                }
//                direction = "R";
//            }
//            else if (direction.equalsIgnoreCase("R"))
//            {
//                for (int i = 0; i < right.size(); i++)
//                {
//                    cur_track = right.get(i);
//                    seek_sequence.add(cur_track);
//                    distance = Math.abs(cur_track - curPos);
//                    seek_count += distance;
//                    curPos = cur_track;
//                }
//                direction = "L";
//            }
//        }
//
//        System.out.println("Total number of seek operations = " + seek_count);
//        System.out.println("Seek Sequence is");
//
//        // Generate data for line plot
//        XYSeries series = new XYSeries("Disk Head Movement");
//
//        // Set data points
//        for (int i = 0; i < seek_sequence.size(); i++) {
//            series.add(i, seek_sequence.get(i));
//        }
//
//        XYSeriesCollection dataset = new XYSeriesCollection();
//        dataset.addSeries(series);
//
//        // Create line plot
//        JFreeChart chart = ChartFactory.createXYLineChart(
//                "SCAN Disk Scheduling",
//                "Time (relative)",
//                "Disk Head Position",
//                dataset
//        );
//
//        // Display chart
//        ChartFrame frame = new ChartFrame("First", chart);
//        frame.setVisible(true);
//        frame.setSize(450, 500);
//    }
//}