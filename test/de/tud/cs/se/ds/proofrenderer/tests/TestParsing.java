package de.tud.cs.se.ds.proofrenderer.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Assert;
import org.junit.Test;

import de.tud.cs.se.ds.proofrenderer.model.ProofTree;
import de.tud.cs.se.ds.proofrenderer.parser.ProofLexer;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser;
import de.tud.cs.se.ds.proofrenderer.parser.ProofTreeLoader;

public class TestParsing {

    @Test
    public void testParsing() {
        try {
            final String testFileContent = new String(Files.readAllBytes(Paths.get("test.pt")));
            final String parseResult = parse(testFileContent).toString();
            
            Assert.assertEquals(parseResult, parse(parseResult).toString());
            
        }
        catch (IOException e) {
            Assert.fail("Unable to load test file.");
        }
    }
    
    private ProofTree parse(String input) throws IOException {
        final ProofLexer lexer = new ProofLexer(new ANTLRInputStream(
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))));
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final ProofParser parser = new ProofParser(tokens);

        final ProofTreeLoader loader = new ProofTreeLoader();
        return loader.visitInit(parser.init());
    }
}
