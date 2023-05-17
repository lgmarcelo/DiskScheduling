import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int currentPosition, trackSize, numReq;
        String choice;
        String repeat;
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
                        validAlgo = true;
                        SCAN(currentPosition, trackSize, arr);
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

    public static void SSTF(int curPos, int trkSz, int[] arr) {
        int[] visited = new int[arr.length];  // Array to store the visited locations
        int totalHeadMovement = 0;

        // Copy the array to avoid modifying the original array
        int[] remainingLocations = Arrays.copyOf(arr, arr.length);

        // Iterate until all locations have been visited
        for (int i = 0; i < arr.length; i++) {
            int minDistance = Integer.MAX_VALUE;
            int nextLocation = -1;

            // Find the location with the shortest seek time
            for (int j = 0; j < remainingLocations.length; j++) {
                int distance = Math.abs(curPos - remainingLocations[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    nextLocation = remainingLocations[j];
                }
            }

            // Update the total head movement and current position
            totalHeadMovement += minDistance;
            curPos = nextLocation;

            // Mark the location as visited
            visited[i] = nextLocation;

            // Remove the visited location from the remainingLocations array
            remainingLocations = removeElement(remainingLocations, nextLocation);
        }

        System.out.println("Total Head Movement: " + totalHeadMovement);

        // Generate data for line plot
        double[][] data = new double[2][arr.length];

        // Set data points
        for (int i = 0; i < visited.length; i++) {
            data[0][i] = visited[i];
            data[1][i] = i;
        }

        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("Head Movement", data);

        // Create line plot
        JFreeChart chart = ChartFactory.createXYLineChart(
                "SSTF Disk Scheduling", "", "", dataset);  // Swap the x-axis and y-axis labels

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

    // Helper method to remove an element from an array
    private static int[] removeElement(int[] arr, int element) {
        int[] result = new int[arr.length - 1];
        int index = 0;
        for (int value : arr) {
            if (value != element) {
                result[index++] = value;
            }
        }
        return result;
    }

    public static void SCAN(int curPos, int trkSz, int[] arr) {

    }

    public static void LOOK(int curPos, int trkSz, int[] arr) {

    }

    public static void CSCAN(int curPos, int trkSz, int[] arr) {
        int totalHeadMovement = 0;
        int maxTrack = trkSz - 1;  // Maximum track number

        // Sort the request locations in ascending order
        Arrays.sort(arr);

        // Find the index where the current position is less than or equal to the location
        int index = 0;
        while (index < arr.length && arr[index] < curPos) {
            index++;
        }

        // Calculate head movement for the right direction
        for (int i = index; i < arr.length; i++) {
            totalHeadMovement += Math.abs(curPos - arr[i]);
            curPos = arr[i];
        }

        // Move the head to the maximum track
        totalHeadMovement += Math.abs(curPos - maxTrack);
        curPos = maxTrack;

        // Move the head to the starting track (track 0)
        totalHeadMovement += Math.abs(curPos - 0);
        curPos = 0;

        // Calculate head movement for the left direction
        for (int i = 0; i < index; i++) {
            totalHeadMovement += Math.abs(curPos - arr[i]);
            curPos = arr[i];
        }

        System.out.println("Total Head Movement: " + totalHeadMovement);

        // Create a new array to represent the correct order of head movement
        int[] headMovement = new int[arr.length];
        headMovement[0] = curPos;
        for (int i = 1; i < headMovement.length; i++) {
            headMovement[i] = arr[i];
        }

// Generate data for line plot
        double[][] data = new double[2][headMovement.length];

// Set data points for head movement
        for (int i = 0; i < headMovement.length; i++) {
            data[0][i] = headMovement[i];
            data[1][i] = i;
        }

        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("Head Movement", data);

        // Create line plot
        JFreeChart chart = ChartFactory.createXYLineChart(
                "C-SCAN Disk Scheduling", "", "", dataset);  // Swap the x-axis and y-axis labels

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

    public static void CLOOK(int curPos, int trkSz, int[] arr) {

    }

}

