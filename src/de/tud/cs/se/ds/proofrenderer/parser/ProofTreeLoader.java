package de.tud.cs.se.ds.proofrenderer.parser;

import java.util.ArrayList;
import java.util.HashMap;

import de.tud.cs.se.ds.proofrenderer.model.OperatorDefinition;
import de.tud.cs.se.ds.proofrenderer.model.OperatorDefinition.OperatorPositions;
import de.tud.cs.se.ds.proofrenderer.model.ProofNodeExpression;
import de.tud.cs.se.ds.proofrenderer.model.ProofNodeStringExpression;
import de.tud.cs.se.ds.proofrenderer.model.ProofTree;
import de.tud.cs.se.ds.proofrenderer.model.SubTree;
import de.tud.cs.se.ds.proofrenderer.model.ProofTreeModelElement;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.DefopContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.InitContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OpdefContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OperatorContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OperatorLabelContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OpidContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OpposContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OpprecContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.ProofContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.SubtreeContext;

public class ProofTreeLoader extends ProofBaseVisitor<ProofTreeModelElement> {
    private HashMap<String, OperatorDefinition> opDefs = new HashMap<String, OperatorDefinition>();

    @Override
    public ProofTree visitInit(InitContext ctx) {
        final ArrayList<OperatorDefinition> opdeflist = new ArrayList<OperatorDefinition>();

        for (DefopContext defop : ctx.defop()) {
            opdeflist.add(visitDefop(defop));
        }

        final SubTree subtree = visitProof(ctx.proof());

        return new ProofTree(opdeflist, subtree);
    }

    @Override
    public OperatorDefinition visitDefop(DefopContext ctx) {
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
        return new ProofTreeModelElementWrapper<String>(stripQuotes(ctx
                .STRING().getText()));
    }

    @Override
    public ProofTreeModelElementWrapper<Integer> visitOpprec(OpprecContext ctx) {
        return new ProofTreeModelElementWrapper<Integer>(Integer.parseInt(ctx
                .getText()));
    }

    @Override
    public ProofTreeModelElementWrapper<OperatorPositions> visitOppos(
            OpposContext ctx) {
        return new ProofTreeModelElementWrapper<OperatorPositions>(
                ctx == null ? OperatorPositions.INFIX : OperatorPositions
                        .valueOf(ctx.getText().toUpperCase()));
    }

    @Override
    public SubTree visitProof(ProofContext ctx) {
        return visitSubtree(ctx.subtree());
    }

    @Override
    public SubTree visitSubtree(SubtreeContext ctx) {
        final ArrayList<ProofNodeExpression> sequentialBlock = new ArrayList<ProofNodeExpression>();
        final ArrayList<SubTree> subtrees = new ArrayList<SubTree>();

        for (OperatorContext operator : ctx.operator()) {
            sequentialBlock.add(visitOperator(operator).getElem());
        }

        for (SubtreeContext operator : ctx.subtree()) {
            subtrees.add(visitSubtree(operator));
        }

        return new SubTree(sequentialBlock, subtrees);
    }

    @Override
    public ProofTreeModelElementWrapper<ProofNodeExpression> visitOperator(
            OperatorContext ctx) {
        if (ctx.STRING() != null) {

            return new ProofTreeModelElementWrapper<ProofNodeExpression>(
                    new ProofNodeStringExpression(stripQuotes(ctx.STRING()
                            .getText())));

        }
        else {

            final ArrayList<ProofNodeExpression> children = new ArrayList<ProofNodeExpression>();
            for (OperatorContext op : ctx.operator()) {
                children.add(visitOperator(op).getElem());
            }

            final String opName = visitOpid(ctx.opid()).getElem();
            final OperatorDefinition opdef = opDefs.get(opName);

            if (opdef == null) {
                System.err.println("Unknown operator '" + opName + "' at line "
                        + ctx.getStart().getLine());
                System.exit(1);
            }

            final String label = visitOperatorLabel(ctx.operatorLabel())
                    .getElem();

            return new ProofTreeModelElementWrapper<ProofNodeExpression>(
                    new ProofNodeExpression(opdef, children, label));

        }
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitOperatorLabel(
            OperatorLabelContext ctx) {
        return new ProofTreeModelElementWrapper<String>(ctx == null ? ""
                : stripQuotes(ctx.STRING().getText()));
    }

    private String stripQuotes(String str) {
        return str.replaceAll("\"", "");
    }

    private static class ProofTreeModelElementWrapper<T> implements
            ProofTreeModelElement {
        private T wrapped;

        public ProofTreeModelElementWrapper(T wrapped) {
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
