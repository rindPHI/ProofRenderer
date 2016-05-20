package de.tud.cs.se.ds.proofrenderer.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

public class TestParsing {

    @Test
    public void testParsing() {
        try {
            final String testFileContent = new String(Files.readAllBytes(Paths.get("test/test.pt")));
            final String parseResult = TestSuite.parse(testFileContent).toString();
            
            Assert.assertEquals(parseResult, TestSuite.parse(parseResult).toString());
            
        }
        catch (IOException e) {
            Assert.fail("Unable to load test file.");
        }
    }
}
