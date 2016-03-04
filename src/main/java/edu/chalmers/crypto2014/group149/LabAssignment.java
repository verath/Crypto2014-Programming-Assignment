package edu.chalmers.crypto2014.group149;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Peter on 2014-12-14.
 */
public class LabAssignment {

    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final Random random;

    /**
     * Creates a new LabAssignment instance that uses a provided Random object as source of random. The random passed
     * is directly used in the implementation of DSA and must therefor be secure!
     *
     * @param inputReader  A reader to read input from
     * @param outputWriter A writer to write output to
     * @param random       A source of random used for the DSA implementation.
     */
    public LabAssignment(BufferedReader inputReader, BufferedWriter outputWriter, Random random) {
        this.reader = inputReader;
        this.writer = outputWriter;
        this.random = random;
    }

    /**
     * Creates a new LabAssignment instance that uses a SecureRandom object for providing source of random.
     *
     * @param inputReader  A reader to read input from
     * @param outputWriter A writer to write output to
     */
    public LabAssignment(BufferedReader inputReader, BufferedWriter outputWriter) {
        this(inputReader, outputWriter, new SecureRandom());
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

        for (int i = 0; i < numKeysToGenerate; i++) {
            DSAKeyPair keyPair = dsa.generateKey();
            writeOutput("x=" + keyPair.getX());
            writeOutput("y=" + keyPair.getY());
        }
    }

    private void signDigests(DSA dsa) throws IOException {
        String x = reader.readLine().substring(2);
        String y = reader.readLine().substring(2);

        DSAKeyPair keyPair = new DSAKeyPair(new BigInteger(x), new BigInteger(y));

        String line = "";
        while ((line = reader.readLine()) != null) {
            line = line.substring(2); // removes two first characters
            DSASignaturePair sigPair = dsa.sign(keyPair, new BigInteger(line, 16));
            writeOutput("r=" + sigPair.getR());
            writeOutput("s=" + sigPair.getS());
        }
    }

    private void verifyDigests(DSA dsa) throws IOException {
        String y = reader.readLine().substring(2);

        String D = "";
        while ((D = reader.readLine()) != null) {
            D = D.substring(2);
            String r = reader.readLine().substring(2);
            String s = reader.readLine().substring(2);
            DSASignaturePair sigPair = new DSASignaturePair(new BigInteger(r), new BigInteger(s));

            boolean verified = dsa.verify(new BigInteger(y), new BigInteger(D, 16), sigPair);

            if (verified) {
                writeOutput("signature_valid");
            } else {
                writeOutput("signature_invalid");
            }
        }
    }

    public void doAssignment() throws IOException {
        DSADomainParameter domainParameter = readDSADomainParameter();

        if (domainParameter.isValid()) {
            writeOutput("valid_group");
        } else {
            writeOutput("invalid_group");
            return;
        }

        DSA dsa = new DSA(domainParameter, random);
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
