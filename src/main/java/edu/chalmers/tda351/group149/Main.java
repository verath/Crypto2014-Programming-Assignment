package edu.chalmers.tda351.group149;

import java.io.*;

/**
 * Created by Peter on 2014-12-14.
 */
public class Main {

    public static void main(String args[]) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            new LabAssignment(reader, writer).doAssignment();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
