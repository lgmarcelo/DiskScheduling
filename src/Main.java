import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int currentPosition, trackSize, numReq;
        String choice;
        String repeat;
        String direction;
        boolean validAlgo = false;

        boolean run = true;

        do {
            System.out.print("Input current position: ");
            currentPosition = sc.nextInt();

            System.out.print("Input track size: ");
            trackSize = sc.nextInt();

            System.out.print("Input number of request: ");
            numReq = sc.nextInt();

            while (numReq > 10) {
                System.out.println("\nMaximum of 10 request only");
                System.out.print("Input number of request again: ");
                numReq = sc.nextInt();
            }

            int[] locationArr = new int[numReq];

            for (int i = 0; i < locationArr.length; i++) {
                System.out.print("Loc " + (i + 1) + ": ");
                locationArr[i] = sc.nextInt();
            }

            int[] arr = new int[numReq + 1];
            arr[0] = currentPosition;
            for (int i = 1; i < arr.length; i++) {
                arr[i] = locationArr[i - 1];
            }
            do {
                System.out.print("\n[A] First Come First Serve (FCFS)" +
                        "\n[B] Shortest Seek Time First (SSTF)" +
                        "\n[C] Scan" +
                        "\n[D] Look" +
                        "\n[E] Circular Scan (CSCAN)" +
                        "\n[F] Circular Look (CLOOK)" +
                        "\n[G] Exit");
                System.out.println("\n\nEnter disk scheduling algorithm: ");
                choice = sc.next();

                switch (choice) {
                    case "A":
                    case "a":
                        System.out.println("\nYou have entered: " + choice.toUpperCase());
                        validAlgo = true;
                        FCFS(currentPosition, trackSize, arr);
                        break;
                    case "b":
                    case "B":
                        System.out.println("\nYou have entered: " + choice.toUpperCase());
                        validAlgo = true;
                        SSTF(currentPosition, trackSize, arr);
                        break;
                    case "c":
                    case "C":
                        System.out.println("\nYou have entered: " + choice.toUpperCase());
                        System.out.println("Which direction? L/R: ");
                        direction = sc.next();
                        validAlgo = true;
                        SCAN(currentPosition, trackSize, arr, direction);
                        break;
                    case "d":
                    case "D":
                        System.out.println("\nYou have entered: " + choice.toUpperCase());
                        validAlgo = true;
                        LOOK(currentPosition, trackSize, arr);
                        break;
                    case "e":
                    case "E":
                        System.out.println("\nYou have entered: " + choice.toUpperCase());
                        validAlgo = true;
                        CSCAN(currentPosition, trackSize, arr);
                        break;
                    case "f":
                    case "F":
                        System.out.println("\nYou have entered: " + choice.toUpperCase());
                        validAlgo = true;
                        CLOOK(currentPosition, trackSize, arr);
                        break;
                    case "g":
                    case "G":
                        System.out.println("\nYou have entered: " + choice.toUpperCase());
                        System.out.println("Exited");
                        run = false;
                        break;
                    default:
                        System.out.println("\nYou have entered: " + choice.toUpperCase());
                        System.out.println("Invalid choice");
                }
            } while (!validAlgo);
            System.out.print("Input again? y/n: ");
            repeat = sc.next();
            while (!repeat.equalsIgnoreCase("Y") && !repeat.equalsIgnoreCase("N")) {
                System.out.println("Please input y/n only: ");
                repeat = sc.next();
            }

            if (repeat.equalsIgnoreCase("N")) {
                System.out.println("Program stopped");
                run = false;
            }

        } while (run);
    }

    public static void FCFS(int curPos, int trkSz, int[] arr) {
        FCFS fcfs = new FCFS(curPos, arr);
        FCFS.run();
    }

    public static void SSTF(int curPos, int trkSz, int[] arr) {
        SSTF sstf = new SSTF(curPos, arr);
        SSTF.run();
    }

    public static void SCAN(int curPos, int trkSz, int[] arr, String dir) {
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        ArrayList<Integer> sequence = new ArrayList<>();

        if(dir.equalsIgnoreCase("L")){
            //algo ng left
//            System.out.println("left");
        }else if(dir.equalsIgnoreCase("R")){
            //algo ng right
//            System.out.println("right");
        }else{
            System.out.println("Invalid direction");
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < curPos)
                left.add(arr[i]);
            else
                right.add(arr[i]);
        }

        Collections.sort(left);
        Collections.sort(right);

        for (int i = 0; i < right.size(); i++) {
            sequence.add(right.get(i));
        }

        sequence.add(trkSz - 1);  // The head is now at the end
        sequence.add(0);  // The head is now at the start

        for (int i = left.size() - 1; i >= 0; i--) {
            sequence.add(left.get(i));
        }

        int totalHeadMovement = 0;
        int prevPos = curPos;

        for (int i = 0; i < sequence.size(); i++) {
            totalHeadMovement += Math.abs(sequence.get(i) - prevPos);
            prevPos = sequence.get(i);
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
                "SCAN Disk Scheduling", "", "", dataset);  // Swap the x-axis and y-axis labels

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

    public static void LOOK(int curPos, int trkSz, int[] arr) {

    }

    public static void CSCAN(int curPos, int trkSz, int[] arr) {
        CSCAN cscan = new CSCAN(curPos, trkSz, arr);
        CSCAN.run();
    }

    public static void CLOOK(int curPos, int trkSz, int[] arr) {

    }

}

