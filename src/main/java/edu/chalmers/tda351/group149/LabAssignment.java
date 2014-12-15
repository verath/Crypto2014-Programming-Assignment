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


    private void generateKeys(DSA dsa) throws IOException {
        // Parse "n=5"
        String n = reader.readLine();
        int numKeysToGenerate = Integer.parseInt(n.substring(2));

        for(int i = 0; i < numKeysToGenerate; i++) {
            DSAKeyPair keyPair = dsa.generateKey();
            writeOutput("x=" + keyPair.getX());
            writeOutput("y=" + keyPair.getY());
        }
    }

    private void signDigests(DSA dsa) {
        System.out.println("signDigests");

    }

    private void verifyDigests(DSA dsa) {
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

        DSA dsa = new DSA(domainParameter);
        String operation = reader.readLine();

        switch (operation) {
            case "genkey":
                generateKeys(dsa);
                break;
            case "sign":
                signDigests(dsa);
                break;
            case "verify":
                verifyDigests(dsa);
                break;
        }
    }

}
