// Generated from Proof.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ProofLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, OPID=6, LPAREN=7, RPAREN=8, INT=9, 
		STRING=10, WHITESPACE=11, COMMENT=12;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "OPID", "LPAREN", "RPAREN", "INT", 
		"STRING", "WHITESPACE", "COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'defop'", "'infix'", "'prefix'", "'suffix'", "'proof'", null, "'('", 
		"')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, "OPID", "LPAREN", "RPAREN", "INT", 
		"STRING", "WHITESPACE", "COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public ProofLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Proof.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\16e\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\7\6\7=\n\7\r\7\16\7>\3\b\3\b\3\t\3\t\3\n\5\nF\n\n\3\n\6\nI"+
		"\n\n\r\n\16\nJ\3\13\3\13\7\13O\n\13\f\13\16\13R\13\13\3\13\3\13\3\f\6"+
		"\fW\n\f\r\f\16\fX\3\f\3\f\3\r\3\r\7\r_\n\r\f\r\16\rb\13\r\3\r\3\r\2\2"+
		"\16\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\3\2\7\4\2"+
		"C\\c|\3\2\62;\3\2$$\5\2\13\f\17\17\"\"\3\2\f\fj\2\3\3\2\2\2\2\5\3\2\2"+
		"\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\3\33\3\2"+
		"\2\2\5!\3\2\2\2\7\'\3\2\2\2\t.\3\2\2\2\13\65\3\2\2\2\r<\3\2\2\2\17@\3"+
		"\2\2\2\21B\3\2\2\2\23E\3\2\2\2\25L\3\2\2\2\27V\3\2\2\2\31\\\3\2\2\2\33"+
		"\34\7f\2\2\34\35\7g\2\2\35\36\7h\2\2\36\37\7q\2\2\37 \7r\2\2 \4\3\2\2"+
		"\2!\"\7k\2\2\"#\7p\2\2#$\7h\2\2$%\7k\2\2%&\7z\2\2&\6\3\2\2\2\'(\7r\2\2"+
		"()\7t\2\2)*\7g\2\2*+\7h\2\2+,\7k\2\2,-\7z\2\2-\b\3\2\2\2./\7u\2\2/\60"+
		"\7w\2\2\60\61\7h\2\2\61\62\7h\2\2\62\63\7k\2\2\63\64\7z\2\2\64\n\3\2\2"+
		"\2\65\66\7r\2\2\66\67\7t\2\2\678\7q\2\289\7q\2\29:\7h\2\2:\f\3\2\2\2;"+
		"=\t\2\2\2<;\3\2\2\2=>\3\2\2\2><\3\2\2\2>?\3\2\2\2?\16\3\2\2\2@A\7*\2\2"+
		"A\20\3\2\2\2BC\7+\2\2C\22\3\2\2\2DF\7/\2\2ED\3\2\2\2EF\3\2\2\2FH\3\2\2"+
		"\2GI\t\3\2\2HG\3\2\2\2IJ\3\2\2\2JH\3\2\2\2JK\3\2\2\2K\24\3\2\2\2LP\7$"+
		"\2\2MO\n\4\2\2NM\3\2\2\2OR\3\2\2\2PN\3\2\2\2PQ\3\2\2\2QS\3\2\2\2RP\3\2"+
		"\2\2ST\7$\2\2T\26\3\2\2\2UW\t\5\2\2VU\3\2\2\2WX\3\2\2\2XV\3\2\2\2XY\3"+
		"\2\2\2YZ\3\2\2\2Z[\b\f\2\2[\30\3\2\2\2\\`\7=\2\2]_\n\6\2\2^]\3\2\2\2_"+
		"b\3\2\2\2`^\3\2\2\2`a\3\2\2\2ac\3\2\2\2b`\3\2\2\2cd\b\r\2\2d\32\3\2\2"+
		"\2\t\2>EJPX`\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}