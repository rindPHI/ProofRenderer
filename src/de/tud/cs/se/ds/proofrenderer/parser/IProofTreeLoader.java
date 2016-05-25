package de.tud.cs.se.ds.proofrenderer.parser;

import java.io.File;

import de.tud.cs.se.ds.proofrenderer.model.ProofTreeModelElement;

public interface IProofTreeLoader extends ProofVisitor<ProofTreeModelElement> {
    
    void setInputFile(File file);
    
}
