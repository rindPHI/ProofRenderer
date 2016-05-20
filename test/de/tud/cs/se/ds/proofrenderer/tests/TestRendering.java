package de.tud.cs.se.ds.proofrenderer.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import de.tud.cs.se.ds.proofrenderer.Renderer;
import de.tud.cs.se.ds.proofrenderer.model.ProofTree;

public class TestRendering {

    @Test
    public void dummyTest() throws IOException {
        final String testFileContent = new String(Files.readAllBytes(Paths.get("test/test.pt")));
        final ProofTree parseResult = TestSuite.parse(testFileContent);
        final Renderer renderer = new Renderer(parseResult);
        System.out.println(renderer.render());
    }
    
}
