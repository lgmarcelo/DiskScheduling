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

    }

    public static void LOOK(int curPos, int trkSz, int[] arr) {

    }

    public static void CSCAN(int curPos, int trkSz, int[] arr) {
        CSCAN cscan = new CSCAN(curPos, trkSz, arr);
        CSCAN.run();
    }

    public static void CLOOK(int curPos, int trkSz, int[] arr) {
        CLOOK clook = new CLOOK(curPos, trkSz , arr);
        CLOOK.run();
    }

}

