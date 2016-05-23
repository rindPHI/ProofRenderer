package de.tud.cs.se.ds.proofrenderer.renderer;

import java.util.ArrayList;
import java.util.Collections;

import de.tud.cs.se.ds.proofrenderer.exception.RendererException;
import de.tud.cs.se.ds.proofrenderer.model.OperatorDefinition;
import de.tud.cs.se.ds.proofrenderer.model.OperatorDefinition.OperatorPositions;
import de.tud.cs.se.ds.proofrenderer.model.ProofNodeExpression;
import de.tud.cs.se.ds.proofrenderer.model.ProofNodeStringExpression;
import de.tud.cs.se.ds.proofrenderer.model.ProofTree;
import de.tud.cs.se.ds.proofrenderer.model.SubTree;

@RendererInformation(name = "latex", description = "Creates a bussproofs LaTeX proof for including in a container document")
public class LatexRenderer implements ProofRenderer {

    private ProofTree proofTree = null;

    @Override
    public String render(ProofTree tree) {
        this.proofTree = tree;
        return render();
    }

    private String render() {
        final StringBuilder sb = new StringBuilder();

        sb.append("\\begin{prooftree}").append(render(proofTree.getSubtree()))
                .append("\n\\end{prooftree}");

        return sb.toString();
    }

    private String render(SubTree tree) {
        final StringBuilder sb = new StringBuilder();

        final ArrayList<ProofNodeExpression> reversedSeqBlock = tree
                .getSequentialBlock();
        final int numSubtrees = tree.getSubtrees().size();

        for (final SubTree subtree : tree.getSubtrees()) {
            sb.append(render(subtree));
        }

        Collections.reverse(reversedSeqBlock);

        int arity = numSubtrees;
        for (int i = 0; i < reversedSeqBlock.size(); i++) {
            final ProofNodeExpression op = reversedSeqBlock.get(i);

            if (!op.getLeftLabel().isEmpty()) {
                sb.append("\n\\LeftLabel{\\scriptsize ")
                        .append(op.getLeftLabel()).append("}");
            }

            sb.append("\n").append(getInvRule(arity)).append("{$")
                    .append(render(op)).append("$}");

            if (!op.getRightLabel().isEmpty()) {
                sb.append("\n\\RightLabel{\\scriptsize ")
                        .append(op.getRightLabel()).append("}");
            }

            arity = 1;
        }

        return sb.toString();
    }

    private String render(ProofNodeExpression expr) {
        if (expr instanceof ProofNodeStringExpression) {
            return expr.toString().replaceAll("\"", "");
        }
        else {
            final StringBuilder sb = new StringBuilder();
            final OperatorPositions opPos = expr.getOperator().getOpPos();
            final int numChildren = expr.getChildren().size();

            if (opPos == OperatorPositions.INFIX) {
                for (int i = 0; i < numChildren; i++) {
                    final ProofNodeExpression child = expr.getChildren().get(i);

                    sb.append(putParenthesesWithPrecedence(expr.getOperator(),
                            child.getOperator(), render(child)));

                    if (i < numChildren - 1) {
                        sb.append(expr.getOperator().getStrDef());
                    }
                }
            }
            else if (opPos == OperatorPositions.PREFIX
                    || opPos == OperatorPositions.SUFFIX) {
                if (opPos == OperatorPositions.PREFIX) {
                    sb.append(expr.getOperator().getStrDef());
                }

                for (ProofNodeExpression child : expr.getChildren()) {

                    sb.append(putParenthesesWithPrecedence(expr.getOperator(),
                            child.getOperator(), render(child)));
                    
                }

                if (opPos == OperatorPositions.SUFFIX) {
                    sb.append(expr.getOperator().getStrDef());
                }
            }
            else if (opPos == OperatorPositions.PARAM) {
                String res = expr.getOperator().getStrDef();
                for (int i = 0; i < numChildren; i++) {
                    res = res.replaceAll(
                            "#" + (i + 1),
                            render(expr.getChildren().get(i)).replaceAll(
                                    "\\\\", "\\\\\\\\"));
                }
                sb.append(res);
            }
            else {
                throw new RendererException("Unsupported operator position: '"
                        + opPos + "'");
            }
            return sb.toString();
        }
    }

    private String putParenthesesWithPrecedence(OperatorDefinition op1,
            OperatorDefinition op2, String expr) {
        final StringBuilder sb = new StringBuilder();
        final boolean bindsStronger = op1.getPrecedence() > op2.getPrecedence();

        if (bindsStronger) {
            sb.append("(");
        }
        
        sb.append(expr);
        
        if (bindsStronger) {
            sb.append(")");
        }
        
        return sb.toString();
    }

    private String getInvRule(int premises) {
        switch (premises) {
        case 0:
            return "\\AxiomC";
        case 1:
            return "\\UnaryInfC";
        case 2:
            return "\\BinaryInfC";
        case 3:
            return "\\TrinaryInfC";
        case 4:
            return "\\QuaternaryInfC";
        case 5:
            return "\\QuinaryInfC";
        default:
            throw new RendererException("Illegal number of premises: "
                    + premises);
        }
    }

}
