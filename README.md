# ProofParser
Transforms proof trees written in a Lisp-like syntax to bussproof LaTeX trees.

Try it with:

    ant
    java -jar dist/ProofRenderer.jar -f test/test.pt
    
You get a help message informing about the possible parameters with

	java -jar dist/ProofRenderer.jar -h
	
## Example Input/Output

Consider the following input file content of the file `test/test.pt` (which is badly formatted on purpose):

```lisp
; asdf
(defop lor " \lor " 20); test)
(defop land " \land " 30)
(defop neg "\neg " 40 prefix)
(defop list ", " 10)
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
(defop seq "\Rightarrow " 0)
```

The command `java -jar dist/ProofRenderer.jar -f test/test.pt` renders this to

	\begin{prooftree}
	\AxiomC{$\neg p$}
	\UnaryInfC{$p \lor \neg q\Rightarrow succedent$}
	\UnaryInfC{$test$}
	\UnaryInfC{$test$}
	\UnaryInfC{$subtree1$}
	\AxiomC{$test$}
	\UnaryInfC{$subtree2$}
	\AxiomC{$\textrm{And a third branch!!!}$}
	\LeftLabel{\scriptsize FancySplitRule 1}
	\TrinaryInfC{$\Rightarrow \neg p, \neg q, p \lor q$}
	\UnaryInfC{$asdf \lor asdf \lor asdf$}
	\RightLabel{\scriptsize That's ``a'' right label!}
	\UnaryInfC{$p \land q \lor q \land p$}
	\UnaryInfC{$(p \lor q) \land (q \lor p)$}
	\UnaryInfC{$\neg (p \lor q)$}
	\UnaryInfC{$\Rightarrow \left[p\right]\phi$}
	\UnaryInfC{$asdf \lor asdf \lor \neg asdf$}
	\end{prooftree}

Using the command `java -jar dist/ProofRenderer.jar -f test/test.pt -r plain` you can clean up the messy proof tree definition in the file `test.pt`:

	(defop neg "\neg " 40 prefix)
	(defop modality "\left[#1\right]#2" 40 param)
	(defop land " \land " 30 infix)
	(defop text "\textrm{#1}" 99999 param)
	(defop list ", " 10 infix)
	(defop lor " \lor " 20 infix)
	(defop seq "\Rightarrow " 0 infix)
	
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
