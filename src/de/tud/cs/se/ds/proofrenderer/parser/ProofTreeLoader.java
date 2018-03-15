package de.tud.cs.se.ds.proofrenderer.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import de.tud.cs.se.ds.proofrenderer.model.MacroDefinition;
import de.tud.cs.se.ds.proofrenderer.model.OperatorDefinition;
import de.tud.cs.se.ds.proofrenderer.model.OperatorDefinition.OperatorPositions;
import de.tud.cs.se.ds.proofrenderer.model.ProofNodeExpression;
import de.tud.cs.se.ds.proofrenderer.model.ProofNodeStringExpression;
import de.tud.cs.se.ds.proofrenderer.model.ProofTree;
import de.tud.cs.se.ds.proofrenderer.model.ProofTreeModelElement;
import de.tud.cs.se.ds.proofrenderer.model.SubTree;
import de.tud.cs.se.ds.proofrenderer.model.TexInput;
import de.tud.cs.se.ds.proofrenderer.model.Usepackage;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.DefopContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.InitContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.InputContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.LatexfileContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.MacroContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.MacrodefContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.MacroidContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.NumparamsContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OpdefContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OperatorContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OperatorLabelContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OpidContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OpposContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.OpprecContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.PkgargsContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.PkgnameContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.ProofContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.SubtreeContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.UsepkgContext;

public class ProofTreeLoader extends ProofBaseVisitor<ProofTreeModelElement>
        implements IProofTreeLoader {
    private HashMap<String, OperatorDefinition> opDefs = new LinkedHashMap<String, OperatorDefinition>();
    private HashMap<String, MacroDefinition> macroDefs = new LinkedHashMap<String, MacroDefinition>();
    private HashSet<Usepackage> usePackages = new LinkedHashSet<Usepackage>();
    private HashSet<TexInput> latexInputs = new LinkedHashSet<TexInput>();

    @Override
    public ProofTree visitInit(InitContext ctx) {
        for (UsepkgContext usepkg : ctx.usepkg()) {
            visit(usepkg);
        }
        
        for (InputContext latexFileCtx : ctx.input()) {
            visit(latexFileCtx);
        }

        for (MacroContext defmacro : ctx.macro()) {
            visit(defmacro);
        }

        for (DefopContext defop : ctx.defop()) {
            visit(defop);
        }

        final SubTree subtree = visitProof(ctx.proof());

        return new ProofTree(usePackages, latexInputs, macroDefs, opDefs, subtree);
    }

    @Override
    public ProofTreeModelElementWrapper<TexInput> visitInput(InputContext ctx) {
        final TexInput result = new TexInput(
                visitLatexfile(ctx.latexfile()).getElem());
        latexInputs.add(result);
        return new ProofTreeModelElementWrapper<TexInput>(result);
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitLatexfile(
            LatexfileContext ctx) {
        return new ProofTreeModelElementWrapper<String>(
                stripQuotes(ctx.STRING().getText()));
    }

    @Override
    public ProofTreeModelElementWrapper<Usepackage> visitUsepkg(
            UsepkgContext ctx) {
        final Usepackage result = new Usepackage(
                visitPkgname(ctx.pkgname()).getElem(),
                visitPkgargs(ctx.pkgargs()).getElem());
        usePackages.add(result);
        return new ProofTreeModelElementWrapper<Usepackage>(result);
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitPkgname(
            PkgnameContext ctx) {
        return new ProofTreeModelElementWrapper<String>(
                stripQuotes(ctx.STRING().getText()));
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitPkgargs(
            PkgargsContext ctx) {
        final String result = ctx == null ? ""
                : stripQuotes(ctx.STRING().getText());

        return new ProofTreeModelElementWrapper<String>(result);
    }

    @Override
    public ProofTreeModelElement visitMacro(MacroContext ctx) {
        final String opName = visitMacroid(ctx.macroid()).getElem();
        if (opDefs.containsKey(opName)) {
            System.err.println(
                    "Duplicate definition of operator '" + opName + "'");
        }

        final MacroDefinition opdef = new MacroDefinition(opName,
                visitNumparams(ctx.numparams()).getElem(),
                visitMacrodef(ctx.macrodef()).getElem());

        macroDefs.put(opName, opdef);

        return opdef;
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitMacrodef(
            MacrodefContext ctx) {
        return new ProofTreeModelElementWrapper<String>(
                stripQuotes(ctx.STRING().getText()));
    }

    @Override
    public ProofTreeModelElementWrapper<Integer> visitNumparams(
            NumparamsContext ctx) {
        return new ProofTreeModelElementWrapper<Integer>(
                Integer.parseInt(ctx.getText()));
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitMacroid(
            MacroidContext ctx) {
        return new ProofTreeModelElementWrapper<String>(ctx.getText());
    }

    @Override
    public OperatorDefinition visitDefop(DefopContext ctx) {
        final String opName = visitOpid(ctx.opid()).getElem();
        if (opDefs.containsKey(opName)) {
            System.err.println(
                    "Duplicate definition of operator '" + opName + "'");
        }

        final OperatorDefinition opdef = new OperatorDefinition(opName,
                visitOpdef(ctx.opdef()).getElem(),
                visitOpprec(ctx.opprec()).getElem(),
                visitOppos(ctx.oppos()).getElem());

        opDefs.put(opName, opdef);

        return opdef;
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitOpid(OpidContext ctx) {
        return new ProofTreeModelElementWrapper<String>(ctx.getText());
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitOpdef(OpdefContext ctx) {
        return new ProofTreeModelElementWrapper<String>(
                stripQuotes(ctx.STRING().getText()));
    }

    @Override
    public ProofTreeModelElementWrapper<Integer> visitOpprec(
            OpprecContext ctx) {
        return new ProofTreeModelElementWrapper<Integer>(
                Integer.parseInt(ctx.getText()));
    }

    @Override
    public ProofTreeModelElementWrapper<OperatorPositions> visitOppos(
            OpposContext ctx) {
        return new ProofTreeModelElementWrapper<OperatorPositions>(ctx == null
                ? OperatorPositions.INFIX
                : OperatorPositions.valueOf(ctx.getText().toUpperCase()));
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
                    new ProofNodeStringExpression(
                            stripQuotes(ctx.STRING().getText())));

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

            final String leftLabel = ctx.leftLabel() == null ? ""
                    : visitOperatorLabel(ctx.leftLabel().operatorLabel())
                            .getElem();

            final String rightLabel = ctx.rightLabel() == null ? ""
                    : visitOperatorLabel(ctx.rightLabel().operatorLabel())
                            .getElem();

            return new ProofTreeModelElementWrapper<ProofNodeExpression>(
                    new ProofNodeExpression(opdef, children, leftLabel,
                            rightLabel));

        }
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitOperatorLabel(
            OperatorLabelContext ctx) {
        return new ProofTreeModelElementWrapper<String>(
                ctx == null ? "" : stripQuotes(ctx.STRING().getText()));
    }

    private String stripQuotes(String str) {
        return str.replaceAll("\"", ""); // Funny hack part II
    }

    static class ProofTreeModelElementWrapper<T>
            implements ProofTreeModelElement {
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

    @Override
    public void setInputFile(File file) {
        // Nothing to do for this loader
    }
}
