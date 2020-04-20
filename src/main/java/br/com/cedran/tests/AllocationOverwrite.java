package br.com.cedran.tests;

import java.util.Random;

public class AllocationOverwrite {

    public static void main(String[] args) throws InterruptedException {
        int arraySize = 1_000_000;
        GCMe[] gcMes = new GCMe[arraySize];

        int count = 0;
        Random random = new Random();

        while(true) {
            gcMes[random.nextInt(arraySize)] = new GCMe();
            // Prints a dot every 2kk allocationss

            if(count % 2_000_000 == 0) {
                System.out.print(".");
            }
            count++;
            Thread.sleep(1);
        }
    }
}
