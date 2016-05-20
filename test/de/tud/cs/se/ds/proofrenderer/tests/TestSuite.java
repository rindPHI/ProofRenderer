package de.tud.cs.se.ds.proofrenderer.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.tud.cs.se.ds.proofrenderer.model.ProofTree;
import de.tud.cs.se.ds.proofrenderer.parser.ProofLexer;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser;
import de.tud.cs.se.ds.proofrenderer.parser.ProofTreeLoader;

@RunWith(Suite.class)
@SuiteClasses({ TestParsing.class })
public class TestSuite {

    public static ProofTree parse(String input) throws IOException {
        final ProofLexer lexer = new ProofLexer(new ANTLRInputStream(
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))));
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final ProofParser parser = new ProofParser(tokens);

        final ProofTreeLoader loader = new ProofTreeLoader();
        return loader.visitInit(parser.init());
    }
    
}
