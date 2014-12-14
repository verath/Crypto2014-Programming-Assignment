package edu.chalmers.tda351.group149;

import java.io.*;

/**
 * Created by Peter on 2014-12-14.
 */
public class LabAssignment {

    private BufferedReader reader;
    private BufferedWriter writer;

    public LabAssignment(BufferedReader inputReader, BufferedWriter outputWriter) {
        this.reader = inputReader;
        this.writer = outputWriter;
    }

    private void writeOutput(String message) throws IOException {
        writer.write(message + "\n");
        writer.flush();
    }

    private DSADomainParameter readDSADomainParameter() throws IOException {
        String p = reader.readLine();
        String q = reader.readLine();
        String g = reader.readLine();

        // They all start with p=, trim that part off
        p = p.substring(2);
        q = q.substring(2);
        g = g.substring(2);

        return new DSADomainParameter(p, q, g, 10);
    }


    private void generateKeys() {
        System.out.println("generateKeys");
    }

    private void signDigests() {
        System.out.println("signDigests");

    }

    private void verifyDigests() {
        System.out.println("verifyDigests");
    }

    public void doAssignment() throws IOException {
        DSADomainParameter domainParameter = readDSADomainParameter();

        if(domainParameter.isValid()) {
            writeOutput("valid_group");
        } else {
            writeOutput("invalid_group");
            return;
        }

        String operation = reader.readLine();

        switch (operation) {
            case "genkey":
                generateKeys();
                break;
            case "sign":
                signDigests();
                break;
            case "verify":
                verifyDigests();
                break;
        }
    }

}
