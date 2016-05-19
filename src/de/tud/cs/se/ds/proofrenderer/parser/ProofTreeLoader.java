package de.tud.cs.se.ds.proofrenderer.parser;

import java.util.HashMap;

import de.tud.cs.se.ds.proofrenderer.model.OperatorDefinition;
import de.tud.cs.se.ds.proofrenderer.model.OperatorDefinition.OperatorPositions;
import de.tud.cs.se.ds.proofrenderer.model.ProofTree;
import de.tud.cs.se.ds.proofrenderer.model.ProofTreeModelElement;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.DefopContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.InitContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.LabeledOperatorContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OpdefContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OperatorContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OpidContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OpposContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OpprecContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.ProofContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.SubtreeContext;

public class ProofTreeLoader extends ProofBaseVisitor<ProofTreeModelElement> {
    private HashMap<String, OperatorDefinition> opDefs = new HashMap<String, OperatorDefinition>();
    
    @Override
    public ProofTree visitInit(InitContext ctx) {
        return visitProof(ctx.proof());
    }

    @Override
    public ProofTreeModelElement visitDefop(DefopContext ctx) {
        final String opName = visitOpid(ctx.opid()).getElem();
        if (opDefs.containsKey(opName)) {
            System.err.println("Duplicate definition of operator '" + opName
                    + "'");
        }

        final OperatorDefinition opdef = new OperatorDefinition(opName,
                visitOpdef(ctx.opdef()).getElem(), visitOpprec(ctx.opprec())
                        .getElem(), visitOppos(ctx.oppos()).getElem());

        opDefs.put(opName, opdef);

        return opdef;
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitOpid(OpidContext ctx) {
        return new ProofTreeModelElementWrapper<String>(ctx.getText());
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitOpdef(OpdefContext ctx) {
        return new ProofTreeModelElementWrapper<String>(ctx.getText());
    }

    @Override
    public ProofTreeModelElementWrapper<Integer> visitOpprec(OpprecContext ctx) {
        return new ProofTreeModelElementWrapper<Integer>(Integer.parseInt(ctx.getText()));
    }

    @Override
    public ProofTreeModelElementWrapper<OperatorPositions> visitOppos(
            OpposContext ctx) {
        return new ProofTreeModelElementWrapper<OperatorPositions>(
                ctx == null ? OperatorPositions.INFIX : OperatorPositions
                        .valueOf(ctx.getText().toUpperCase()));
    }

    @Override
    public ProofTree visitProof(ProofContext ctx) {
        return visitSubtree(ctx.subtree());
    }

    @Override
    public ProofTree visitSubtree(SubtreeContext ctx) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProofTreeModelElement visitLabeledOperator(LabeledOperatorContext ctx) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProofTreeModelElement visitOperator(OperatorContext ctx) {
        // TODO Auto-generated method stub
        return null;
    }
    
    private static class ProofTreeModelElementWrapper<T> implements ProofTreeModelElement {
        private T wrapped;
        
        public ProofTreeModelElementWrapper(T wrapped){
            this.wrapped = wrapped;
        }
        
        public T getElem() {
            return wrapped;
        }
        
        @Override
        public String toString() {
            return wrapped.toString();
        }
    }
}