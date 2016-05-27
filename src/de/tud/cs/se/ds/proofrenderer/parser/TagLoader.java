package de.tud.cs.se.ds.proofrenderer.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.tud.cs.se.ds.proofrenderer.model.OperatorDefinition;
import de.tud.cs.se.ds.proofrenderer.model.OperatorDefinition.OperatorPositions;
import de.tud.cs.se.ds.proofrenderer.model.ProofTreeModelElement;
import de.tud.cs.se.ds.proofrenderer.model.ProofTreeTag;
import de.tud.cs.se.ds.proofrenderer.model.ProofTreeTags;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.DefopContext;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser.InitContext;
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
import de.tud.cs.se.ds.proofrenderer.parser.ProofTreeLoader.ProofTreeModelElementWrapper;

public class TagLoader extends ProofBaseVisitor<ProofTreeModelElement>
        implements IProofTreeLoader {
    private HashMap<String, OperatorDefinition> opDefs = new HashMap<String, OperatorDefinition>();
    private File inputFile = null;
    private ArrayList<String> fileLines = new ArrayList<String>();
    @Override
    public void setInputFile(File file) {
        this.inputFile = file;

        try {
            final BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
                fileLines.add(line);
            }

            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProofTreeTags visitInit(InitContext ctx) {
        final ArrayList<ProofTreeTag> tags = new ArrayList<ProofTreeTag>();

        for (UsepkgContext usepkg : ctx.usepkg()) {
            tags.add(visitUsepkg(usepkg));
        }

        for (MacroContext defmacro : ctx.macro()) {
            tags.add(visitMacro(defmacro));
        }

        for (DefopContext defop : ctx.defop()) {
            tags.add(visitDefop(defop));
        }
        
        tags.addAll(visitProof(ctx.proof()).getTags());

        return new ProofTreeTags(tags);
    }

    @Override
    public ProofTreeTag visitUsepkg(UsepkgContext ctx) {
        final String pkgName = visitPkgname(ctx.pkgname()).getElem();
        final String pkgArgs = visitPkgargs(ctx.pkgargs()).getElem();
        final int line = ctx.getStart().getLine();

        return new ProofTreeTag(pkgName + " [" + pkgArgs + "]",
                inputFile.getName(), fileLines.get(line - 1), 'u', line,
                new ArrayList<String>());
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitPkgname(PkgnameContext ctx) {
        return new ProofTreeModelElementWrapper<String>(stripQuotes(ctx
                .STRING().getText()));
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitPkgargs(PkgargsContext ctx) {
        final String result = ctx == null ? "" : stripQuotes(ctx.STRING()
                .getText());

        return new ProofTreeModelElementWrapper<String>(result);
    }

    @Override
    public ProofTreeTag visitMacro(MacroContext ctx) {
        final String opName = visitMacroid(ctx.macroid()).getElem();
        final int line = ctx.getStart().getLine();

        return new ProofTreeTag(opName, inputFile.getName(),
                fileLines.get(line - 1), 'm', line, new ArrayList<String>());
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitMacrodef(
            MacrodefContext ctx) {
        return new ProofTreeModelElementWrapper<String>(stripQuotes(ctx
                .STRING().getText()));
    }

    @Override
    public ProofTreeModelElementWrapper<Integer> visitNumparams(
            NumparamsContext ctx) {
        return new ProofTreeModelElementWrapper<Integer>(Integer.parseInt(ctx
                .getText()));
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitMacroid(MacroidContext ctx) {
        return new ProofTreeModelElementWrapper<String>(ctx.getText());
    }

    @Override
    public ProofTreeTag visitDefop(DefopContext ctx) {
        final String opName = visitOpid(ctx.opid()).getElem();
        final int line = ctx.getStart().getLine();

        opDefs.put(opName,
                new OperatorDefinition(opName, visitOpdef(ctx.opdef())
                        .getElem(), visitOpprec(ctx.opprec()).getElem(),
                        visitOppos(ctx.oppos()).getElem()));

        return new ProofTreeTag(opName, inputFile.getName(),
                fileLines.get(line - 1), 'o', line, new ArrayList<String>());
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
    public ProofTreeTags visitProof(ProofContext ctx) {
        return visitSubtree(ctx.subtree());
    }

    private ProofTreeTags visitSubtree(SubtreeContext ctx,
            ArrayList<String> currentScope) {
        final ArrayList<ProofTreeTag> tags = new ArrayList<ProofTreeTag>();

        String lastOpName = "";
        for (OperatorContext operator : ctx.operator()) {
            final ProofTreeTag tag = visitOperator(operator);

            tag.setScope(new ArrayList<String>(currentScope));
            lastOpName = tag.getName();

            tags.add(tag);
        }

        int i = 1;
        for (SubtreeContext subtree : ctx.subtree()) {
            final ArrayList<String> scope = new ArrayList<String>(currentScope);
            final int line = subtree.start.getLine();
            final String newScopeName = lastOpName + " [branch " + i + "]";
            scope.add(newScopeName);
            
            tags.add(new ProofTreeTag(newScopeName, inputFile.getName(), fileLines.get(line - 1), 't', line, currentScope));
            
            tags.addAll(visitSubtree(subtree, scope).getTags());
            i++;
        }

        return new ProofTreeTags(tags);
    }

    @Override
    public ProofTreeTags visitSubtree(SubtreeContext ctx) {
        final ArrayList<String> scope = new ArrayList<String>();
        scope.add("tree:root");

        final ArrayList<ProofTreeTag> result = new ArrayList<ProofTreeTag>();
        final ProofTreeTags subtreeRes = visitSubtree(ctx, scope);
        final int line = ctx.getStart().getLine();

        result.add(new ProofTreeTag("root", inputFile.getName(), fileLines
                .get(line), 't', line, scope));
        result.addAll(subtreeRes.getTags());

        return new ProofTreeTags(result);
    }

    @Override
    public ProofTreeTag visitOperator(OperatorContext ctx) {
        final int line = ctx.start.getLine();
        String opName = "";

        if (ctx.STRING() != null) {

            opName = stripQuotes(ctx.STRING().getText());

        }
        else {

            opName = visitOpid(ctx.opid()).getElem();
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

            if (!leftLabel.isEmpty()) {
                opName = leftLabel;
            }
            else if (!rightLabel.isEmpty()) {
                opName = rightLabel;
            }

        }

        return new ProofTreeTag(opName + " (l:" + line + ")", inputFile.getName(),
                fileLines.get(line - 1), 'n', line, null);
    }

    @Override
    public ProofTreeModelElementWrapper<String> visitOperatorLabel(
            OperatorLabelContext ctx) {
        return new ProofTreeModelElementWrapper<String>(ctx == null ? ""
                : stripQuotes(ctx.STRING().getText()));
    }

    private String stripQuotes(String str) {
        return str.replaceAll("\"", ""); // Funny hack part II
    }
}
