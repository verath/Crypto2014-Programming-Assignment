package edu.chalmers.tda351.group149;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by Peter on 2014-12-14.
 */
@RunWith(Parameterized.class)
public class LabAssignmentIT {
    private String inputFilePath;

    public LabAssignmentIT(String fileName) {
        this.inputFilePath = fileName;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> getFiles() {
        return Arrays.asList(new Object[][]{
                {"inputfiles/invalid1"},
                {"inputfiles/invalid2"},
                {"inputfiles/invalid3"},
                {"inputfiles/invalid4"},
                {"inputfiles/verify"},
                {"inputfiles/sign"},
                {"inputfiles/genkey"}
        });
    }

    @Test
    public void testForFile() throws Exception {
        // Simulate system.in, instead feed directly from the edu.chalmers.tda351.group149.inputfiles
        InputStream in = getClass().getResourceAsStream(inputFilePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        // Capture output in a string writer so we can compare it later
        StringWriter output = new StringWriter();

        // Use a fixed seeded Random so output is testable
        Random random = new Random(1);

        LabAssignment labAssignment = new LabAssignment(reader, new BufferedWriter(output), random);
        labAssignment.doAssignment();

        String actual = output.toString();

        in.close();
        reader.close();

        // Read expected file
        Path p = Paths.get(getClass().getResource(inputFilePath + "-expected").toURI());
        String expected = new String(Files.readAllBytes(p));

        assertEquals(expected.replaceAll("\\s+", ""), actual.replaceAll("\\s+", ""));
    }
}
