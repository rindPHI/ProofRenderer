# ProofRenderer

Transforms proof trees written in a Lisp-like syntax to bussproof LaTeX trees.

Try it with:

```bash
ant
java -jar dist/ProofRenderer.jar -f test/test.pt
```

You get a help message informing about the possible parameters with

```bash
java -jar dist/ProofRenderer.jar -h
```
	
## Example Input/Output

Consider the following input file content of the file `test/test.pt` (which is badly formatted on purpose):

```lisp
; asdf
(defop lor " \lor " 20); test)
(defop land " \land " 30)
(defop neg "\neg " 40 prefix)
(defop list ", " 10)
(macro implies 0 "\Longrightarrow")
(defop modality "\left[#1\right]#2" 40 param)
(defop text "\textrm{#1}" 99999 param)
(proof
    (
        (lor "asdf" (lor "asdf" (neg "asdf")))
        (seq "" (modality "p" "\phi"))
        (neg (lor "p" "q")) ; should be "\neg (p \lor q)"
        (land (lor "p" "q") (lor "q" "p")) ; should be "(p \lor q) \land (q \lor p)"
        (lor (land "p" "q") (land "q" "p")) ; should be "p \land q \lor q \land p"
        (lor "asdf" (lor "asdf" "asdf")) <- "That's ``a'' right label!"
        "FancySplitRule 1" -> (seq "" (list (neg "p") (neg "q") (lor "p" "q")))
        ("subtree1" "test"
            (
            	"test"
            	(seq (lor "p" (neg "q")) "succedent")
            	(neg "p")));))
        (
            "subtree2"
            "test"
        )
        (text "And a third branch!!!")
    )
)
(defop seq "\implies  " 0)
```

The command `java -jar dist/ProofRenderer.jar -f test/test.pt -r standalone-latex` renders this to

```latex
\documentclass{article}
\usepackage{bussproofs}

\begin{document}

\newcommand{\implies}[0]{\Longrightarrow}
\begin{prooftree}
\AxiomC{$\neg p$}
\UnaryInfC{$p \lor \neg q\implies  succedent$}
\UnaryInfC{$test$}
\UnaryInfC{$test$}
\UnaryInfC{$subtree1$}
\AxiomC{$test$}
\UnaryInfC{$subtree2$}
\AxiomC{$\textrm{And a third branch!!!}$}
\LeftLabel{\scriptsize FancySplitRule 1}
\TrinaryInfC{$\implies  \neg p, \neg q, p \lor q$}
\UnaryInfC{$asdf \lor asdf \lor asdf$}
\RightLabel{\scriptsize That's ``a'' right label!}
\UnaryInfC{$p \land q \lor q \land p$}
\UnaryInfC{$(p \lor q) \land (q \lor p)$}
\UnaryInfC{$\neg (p \lor q)$}
\UnaryInfC{$\implies  \left[p\right]\phi$}
\UnaryInfC{$asdf \lor asdf \lor \neg asdf$}
\end{prooftree}

\end{document}
```

PDFLaTeX compilation result:

![Rendered Output](example-tree.png?raw=true)

Using the command `java -jar dist/ProofRenderer.jar -f test/test.pt -r plain` you can clean up the messy proof tree definition in the file `test.pt`:

```lisp
(macro implies 0 "\Longrightarrow")
(defop neg "\neg " 40 prefix)
(defop modality "\left[#1\right]#2" 40 param)
(defop land " \land " 30 infix)
(defop text "\textrm{#1}" 99999 param)
(defop list ", " 10 infix)
(defop lor " \lor " 20 infix)
(defop seq "\implies  " 0 infix)

(proof (
	(lor  "asdf" (lor  "asdf" (neg  "asdf")))
	(seq  "" (modality  "p" "\phi"))
	(neg  (lor  "p" "q"))
	(land  (lor  "p" "q") (lor  "q" "p"))
	(lor  (land  "p" "q") (land  "q" "p"))
	(lor  "asdf" (lor  "asdf" "asdf")) <- "That's ``a'' right label!"
	"FancySplitRule 1" -> (seq  "" (list  (neg  "p") (neg  "q") (lor  "p" "q")))
	(
		"subtree1"
		"test"
		(
			"test"
			(seq  (lor  "p" (neg  "q")) "succedent")
			(neg  "p")
		)
	)
	(
		"subtree2"
		"test"
	)
	(
		(text  "And a third branch!!!")
	)
))
```

## Input Syntax

Within the `proof` environment, you can, for each node, either use Strings (including LaTeX macros; everything will be put into a math environment) or *operators* that you defined in the file yourself. The syntax for operator definitions is as follows:

```antrl
defop : '(' 'defop' opid opdef opprec oppos? ')';
opid : ID;
opdef : STRING;
opprec : INT;
oppos :	'infix' | 'prefix' | 'suffix' | 'param';
```

`opid` is the name of the operator, e.g. `lor` as in the examples above, `opdef` is the LaTeX definition, e.g. `" \lor "`, `opprec` is the (positive) precedence of the operator, where low numbers bind weaker than higher numbers, and `oppos` is the operator position. For infix, prefix, and suffix operators, an arbitrary number of arguments can be supplied. Parentheses will be put according to the given precedence. The `param` option allows for the definition of operators with LaTeX-like parameters; here, the number of parameters has to match, of course.

Inside a proof, you can simple start a new subtree by enclosing a sequence of nodes (and subtrees) into parentheses.

## Supported Renderers

So far, the following three renderers are supported (supply the name inside the quotes to the `-r` command line parameter):

* "plain"  
  This renderer just generates a cleaned-up representation of the input in the same lisp-like syntax.
* "latex"  
  bussproofs proof tree for including into an existing LaTeX document.
* "standalone-latex"  
  A complete LaTeX document ready for compilation.
  
## Extending ProofRenderer

It's quite easy to extend ProofRenderer with a new renderer. Just create a class implementing `de.tud.cs.se.ds.proofrenderer.renderer.ProofRenderer` providing an annotation according to `de.tud.cs.se.ds.proofrenderer.renderer.RendererInformation`. An easy example is the "standalone-latex" renderer:

```java
@RendererInformation(name = "standalone-latex")
public class StandaloneLatexRenderer implements ProofRenderer {

    @Override
    public String render(ProofTree tree) {
        final StringBuilder sb = new StringBuilder();
        
        sb.append("\\documentclass{article}\n")
            .append("\\usepackage{bussproofs}\n\n")
            .append("\\begin{document}\n\n");
        
        sb.append(new LatexRenderer().render(tree));
        
        sb.append("\n\n\\end{document}");
        
        return sb.toString();
    }

}
```