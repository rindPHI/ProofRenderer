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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, OPID=7, LPAREN=8, RPAREN=9, 
		INT=10, STRING=11, WHITESPACE=12, COMMENT=13;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "OPID", "LPAREN", "RPAREN", 
		"INT", "STRING", "WHITESPACE", "COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'defop'", "'infix'", "'prefix'", "'suffix'", "'proof'", "':'", 
		null, "'('", "')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, "OPID", "LPAREN", "RPAREN", 
		"INT", "STRING", "WHITESPACE", "COMMENT"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\17i\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\7\3\7\3\b\6\bA\n\b\r\b\16\bB\3\t\3\t\3\n\3\n\3\13"+
		"\5\13J\n\13\3\13\6\13M\n\13\r\13\16\13N\3\f\3\f\7\fS\n\f\f\f\16\fV\13"+
		"\f\3\f\3\f\3\r\6\r[\n\r\r\r\16\r\\\3\r\3\r\3\16\3\16\7\16c\n\16\f\16\16"+
		"\16f\13\16\3\16\3\16\2\2\17\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\3\2\7\4\2C\\c|\3\2\62;\3\2$$\5\2\13\f\17\17\"\"\3"+
		"\2\f\fn\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\3\35\3\2\2\2\5#\3\2\2\2\7)\3\2\2\2\t"+
		"\60\3\2\2\2\13\67\3\2\2\2\r=\3\2\2\2\17@\3\2\2\2\21D\3\2\2\2\23F\3\2\2"+
		"\2\25I\3\2\2\2\27P\3\2\2\2\31Z\3\2\2\2\33`\3\2\2\2\35\36\7f\2\2\36\37"+
		"\7g\2\2\37 \7h\2\2 !\7q\2\2!\"\7r\2\2\"\4\3\2\2\2#$\7k\2\2$%\7p\2\2%&"+
		"\7h\2\2&\'\7k\2\2\'(\7z\2\2(\6\3\2\2\2)*\7r\2\2*+\7t\2\2+,\7g\2\2,-\7"+
		"h\2\2-.\7k\2\2./\7z\2\2/\b\3\2\2\2\60\61\7u\2\2\61\62\7w\2\2\62\63\7h"+
		"\2\2\63\64\7h\2\2\64\65\7k\2\2\65\66\7z\2\2\66\n\3\2\2\2\678\7r\2\289"+
		"\7t\2\29:\7q\2\2:;\7q\2\2;<\7h\2\2<\f\3\2\2\2=>\7<\2\2>\16\3\2\2\2?A\t"+
		"\2\2\2@?\3\2\2\2AB\3\2\2\2B@\3\2\2\2BC\3\2\2\2C\20\3\2\2\2DE\7*\2\2E\22"+
		"\3\2\2\2FG\7+\2\2G\24\3\2\2\2HJ\7/\2\2IH\3\2\2\2IJ\3\2\2\2JL\3\2\2\2K"+
		"M\t\3\2\2LK\3\2\2\2MN\3\2\2\2NL\3\2\2\2NO\3\2\2\2O\26\3\2\2\2PT\7$\2\2"+
		"QS\n\4\2\2RQ\3\2\2\2SV\3\2\2\2TR\3\2\2\2TU\3\2\2\2UW\3\2\2\2VT\3\2\2\2"+
		"WX\7$\2\2X\30\3\2\2\2Y[\t\5\2\2ZY\3\2\2\2[\\\3\2\2\2\\Z\3\2\2\2\\]\3\2"+
		"\2\2]^\3\2\2\2^_\b\r\2\2_\32\3\2\2\2`d\7=\2\2ac\n\6\2\2ba\3\2\2\2cf\3"+
		"\2\2\2db\3\2\2\2de\3\2\2\2eg\3\2\2\2fd\3\2\2\2gh\b\16\2\2h\34\3\2\2\2"+
		"\t\2BINT\\d\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}