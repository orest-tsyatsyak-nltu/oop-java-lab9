package org.orest.tsiatsiak;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

public class Lab9ConsoleApplication {

    private static final int N = 20;

    private static final int M = 2;

    private static final int A = 25;

    private static final int B = 40;

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        System.out.println("Randomly filled streams: ");
        Vector<InputStream> streams = Arrays.stream(getRandomlyFilledMatrix(M, N))
                .peek(bytes -> System.out.println(Arrays.toString(bytes)))
                .map(ByteArrayInputStream::new)
                .collect(Vector::new, Vector::add, Vector::addAll);
        InputStream combinedStreams = new SequenceInputStream(streams.elements());
        System.out.println("Combined stream: ");
        printStream(combinedStreams);
        streams.forEach(Lab9ConsoleApplication::closeStream);
        closeStream(combinedStreams);
    }

    private static byte[][] getRandomlyFilledMatrix(int rows, int cols) {
        byte[][] matrix = new byte[rows][];
        for(int i = 0; i < rows; i++){
            matrix[i] = getRandomlyFilledArray(cols);
        }
        return matrix;
    }

    private static byte[] getRandomlyFilledArray(int size){
        byte[] array = new byte[size];
        for(int i = 0; i < size; i++){
            array[i] = (byte) (RANDOM.nextInt(B - A + 1) + A);
        }
        return array;
    }

    private static void printStream(InputStream inputStream) {
        try {
            System.out.println(Arrays.toString(inputStream.readAllBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeStream(InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
