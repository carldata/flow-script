// Generated from /home/klangner/workspaces/scala/flow-script/src/main/scala/carldata/sf/compiler/FlowScript.g4 by ANTLR 4.7
package carldata.sf.compiler.gen;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FlowScriptParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, DEF=7, EXTERNAL=8, MODULE=9, 
		TRUE=10, FALSE=11, PLUS=12, MINUS=13, AND=14, OR=15, NEG=16, IF=17, THEN=18, 
		ELSE=19, WS=20, LINE_COMMENT=21, Identifier=22, QuotedString=23, Integer=24, 
		AddOp=25, MultiplyOp=26, RelationOp=27;
	public static final int
		RULE_compilationUnit = 0, RULE_moduleDeclaration = 1, RULE_externalFunDef = 2, 
		RULE_functionDefinition = 3, RULE_paramList = 4, RULE_param = 5, RULE_typeDefinition = 6, 
		RULE_expression = 7, RULE_boolLiteral = 8, RULE_stringLiteral = 9, RULE_numberLiteral = 10, 
		RULE_variableExpr = 11, RULE_funApp = 12, RULE_expressionList = 13;
	public static final String[] ruleNames = {
		"compilationUnit", "moduleDeclaration", "externalFunDef", "functionDefinition", 
		"paramList", "param", "typeDefinition", "expression", "boolLiteral", "stringLiteral", 
		"numberLiteral", "variableExpr", "funApp", "expressionList"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "':'", "'='", "','", "'.'", "'def'", "'external'", 
		"'module'", "'True'", "'False'", "'+'", "'-'", "'&&'", "'||'", "'!'", 
		"'if'", "'then'", "'else'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, "DEF", "EXTERNAL", "MODULE", 
		"TRUE", "FALSE", "PLUS", "MINUS", "AND", "OR", "NEG", "IF", "THEN", "ELSE", 
		"WS", "LINE_COMMENT", "Identifier", "QuotedString", "Integer", "AddOp", 
		"MultiplyOp", "RelationOp"
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

	@Override
	public String getGrammarFileName() { return "FlowScript.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FlowScriptParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class CompilationUnitContext extends ParserRuleContext {
		public ModuleDeclarationContext moduleDeclaration() {
			return getRuleContext(ModuleDeclarationContext.class,0);
		}
		public TerminalNode EOF() { return getToken(FlowScriptParser.EOF, 0); }
		public List<ExternalFunDefContext> externalFunDef() {
			return getRuleContexts(ExternalFunDefContext.class);
		}
		public ExternalFunDefContext externalFunDef(int i) {
			return getRuleContext(ExternalFunDefContext.class,i);
		}
		public List<FunctionDefinitionContext> functionDefinition() {
			return getRuleContexts(FunctionDefinitionContext.class);
		}
		public FunctionDefinitionContext functionDefinition(int i) {
			return getRuleContext(FunctionDefinitionContext.class,i);
		}
		public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilationUnit; }
	}

	public final CompilationUnitContext compilationUnit() throws RecognitionException {
		CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compilationUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			moduleDeclaration();
			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EXTERNAL) {
				{
				{
				setState(29);
				externalFunDef();
				}
				}
				setState(34);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DEF) {
				{
				{
				setState(35);
				functionDefinition();
				}
				}
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(41);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModuleDeclarationContext extends ParserRuleContext {
		public TerminalNode MODULE() { return getToken(FlowScriptParser.MODULE, 0); }
		public TerminalNode Identifier() { return getToken(FlowScriptParser.Identifier, 0); }
		public ModuleDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_moduleDeclaration; }
	}

	public final ModuleDeclarationContext moduleDeclaration() throws RecognitionException {
		ModuleDeclarationContext _localctx = new ModuleDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_moduleDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			match(MODULE);
			setState(44);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExternalFunDefContext extends ParserRuleContext {
		public TerminalNode EXTERNAL() { return getToken(FlowScriptParser.EXTERNAL, 0); }
		public TerminalNode DEF() { return getToken(FlowScriptParser.DEF, 0); }
		public TerminalNode Identifier() { return getToken(FlowScriptParser.Identifier, 0); }
		public TypeDefinitionContext typeDefinition() {
			return getRuleContext(TypeDefinitionContext.class,0);
		}
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public ExternalFunDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_externalFunDef; }
	}

	public final ExternalFunDefContext externalFunDef() throws RecognitionException {
		ExternalFunDefContext _localctx = new ExternalFunDefContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_externalFunDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(EXTERNAL);
			setState(47);
			match(DEF);
			setState(48);
			match(Identifier);
			setState(49);
			match(T__0);
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(50);
				paramList();
				}
			}

			setState(53);
			match(T__1);
			setState(54);
			match(T__2);
			setState(55);
			typeDefinition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public TerminalNode DEF() { return getToken(FlowScriptParser.DEF, 0); }
		public TerminalNode Identifier() { return getToken(FlowScriptParser.Identifier, 0); }
		public TypeDefinitionContext typeDefinition() {
			return getRuleContext(TypeDefinitionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_functionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			match(DEF);
			setState(58);
			match(Identifier);
			setState(59);
			match(T__0);
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(60);
				paramList();
				}
			}

			setState(63);
			match(T__1);
			setState(64);
			match(T__2);
			setState(65);
			typeDefinition();
			setState(66);
			match(T__3);
			setState(67);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamListContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public ParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramList; }
	}

	public final ParamListContext paramList() throws RecognitionException {
		ParamListContext _localctx = new ParamListContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_paramList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			param();
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(70);
				match(T__4);
				setState(71);
				param();
				}
				}
				setState(76);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(FlowScriptParser.Identifier, 0); }
		public TypeDefinitionContext typeDefinition() {
			return getRuleContext(TypeDefinitionContext.class,0);
		}
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(Identifier);
			setState(78);
			match(T__2);
			setState(79);
			typeDefinition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeDefinitionContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(FlowScriptParser.Identifier, 0); }
		public TypeDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDefinition; }
	}

	public final TypeDefinitionContext typeDefinition() throws RecognitionException {
		TypeDefinitionContext _localctx = new TypeDefinitionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_typeDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext ifExpr;
		public Token minusOp;
		public Token negOp;
		public Token addOp;
		public Token boolOp;
		public TerminalNode IF() { return getToken(FlowScriptParser.IF, 0); }
		public TerminalNode THEN() { return getToken(FlowScriptParser.THEN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(FlowScriptParser.ELSE, 0); }
		public TerminalNode MINUS() { return getToken(FlowScriptParser.MINUS, 0); }
		public TerminalNode NEG() { return getToken(FlowScriptParser.NEG, 0); }
		public BoolLiteralContext boolLiteral() {
			return getRuleContext(BoolLiteralContext.class,0);
		}
		public StringLiteralContext stringLiteral() {
			return getRuleContext(StringLiteralContext.class,0);
		}
		public NumberLiteralContext numberLiteral() {
			return getRuleContext(NumberLiteralContext.class,0);
		}
		public VariableExprContext variableExpr() {
			return getRuleContext(VariableExprContext.class,0);
		}
		public FunAppContext funApp() {
			return getRuleContext(FunAppContext.class,0);
		}
		public TerminalNode MultiplyOp() { return getToken(FlowScriptParser.MultiplyOp, 0); }
		public TerminalNode PLUS() { return getToken(FlowScriptParser.PLUS, 0); }
		public TerminalNode AND() { return getToken(FlowScriptParser.AND, 0); }
		public TerminalNode OR() { return getToken(FlowScriptParser.OR, 0); }
		public TerminalNode RelationOp() { return getToken(FlowScriptParser.RelationOp, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(84);
				match(IF);
				setState(85);
				((ExpressionContext)_localctx).ifExpr = expression(0);
				setState(86);
				match(THEN);
				setState(87);
				expression(0);
				setState(88);
				match(ELSE);
				setState(89);
				expression(13);
				}
				break;
			case 2:
				{
				setState(91);
				match(T__0);
				setState(92);
				expression(0);
				setState(93);
				match(T__1);
				}
				break;
			case 3:
				{
				setState(95);
				((ExpressionContext)_localctx).minusOp = match(MINUS);
				setState(96);
				expression(11);
				}
				break;
			case 4:
				{
				setState(97);
				((ExpressionContext)_localctx).negOp = match(NEG);
				setState(98);
				expression(8);
				}
				break;
			case 5:
				{
				setState(99);
				boolLiteral();
				}
				break;
			case 6:
				{
				setState(100);
				stringLiteral();
				}
				break;
			case 7:
				{
				setState(101);
				numberLiteral();
				}
				break;
			case 8:
				{
				setState(102);
				variableExpr();
				}
				break;
			case 9:
				{
				setState(103);
				funApp();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(120);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(118);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(106);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(107);
						match(MultiplyOp);
						setState(108);
						expression(11);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(109);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(110);
						((ExpressionContext)_localctx).addOp = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((ExpressionContext)_localctx).addOp = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(111);
						expression(10);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(112);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(113);
						((ExpressionContext)_localctx).boolOp = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==AND || _la==OR) ) {
							((ExpressionContext)_localctx).boolOp = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(114);
						expression(8);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(115);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(116);
						match(RelationOp);
						setState(117);
						expression(7);
						}
						break;
					}
					} 
				}
				setState(122);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BoolLiteralContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(FlowScriptParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(FlowScriptParser.FALSE, 0); }
		public BoolLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolLiteral; }
	}

	public final BoolLiteralContext boolLiteral() throws RecognitionException {
		BoolLiteralContext _localctx = new BoolLiteralContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_boolLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			_la = _input.LA(1);
			if ( !(_la==TRUE || _la==FALSE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringLiteralContext extends ParserRuleContext {
		public TerminalNode QuotedString() { return getToken(FlowScriptParser.QuotedString, 0); }
		public StringLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringLiteral; }
	}

	public final StringLiteralContext stringLiteral() throws RecognitionException {
		StringLiteralContext _localctx = new StringLiteralContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_stringLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			match(QuotedString);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberLiteralContext extends ParserRuleContext {
		public List<TerminalNode> Integer() { return getTokens(FlowScriptParser.Integer); }
		public TerminalNode Integer(int i) {
			return getToken(FlowScriptParser.Integer, i);
		}
		public NumberLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numberLiteral; }
	}

	public final NumberLiteralContext numberLiteral() throws RecognitionException {
		NumberLiteralContext _localctx = new NumberLiteralContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_numberLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			match(Integer);
			setState(130);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(128);
				match(T__5);
				setState(129);
				match(Integer);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableExprContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(FlowScriptParser.Identifier, 0); }
		public VariableExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableExpr; }
	}

	public final VariableExprContext variableExpr() throws RecognitionException {
		VariableExprContext _localctx = new VariableExprContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_variableExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunAppContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(FlowScriptParser.Identifier, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public FunAppContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funApp; }
	}

	public final FunAppContext funApp() throws RecognitionException {
		FunAppContext _localctx = new FunAppContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_funApp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			match(Identifier);
			setState(135);
			match(T__0);
			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << TRUE) | (1L << FALSE) | (1L << MINUS) | (1L << NEG) | (1L << IF) | (1L << Identifier) | (1L << QuotedString) | (1L << Integer))) != 0)) {
				{
				setState(136);
				expressionList();
				}
			}

			setState(139);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			expression(0);
			setState(146);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(142);
				match(T__4);
				setState(143);
				expression(0);
				}
				}
				setState(148);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 7:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 10);
		case 1:
			return precpred(_ctx, 9);
		case 2:
			return precpred(_ctx, 7);
		case 3:
			return precpred(_ctx, 6);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\35\u0098\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\7\2!\n\2\f\2\16\2$\13"+
		"\2\3\2\7\2\'\n\2\f\2\16\2*\13\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3"+
		"\4\5\4\66\n\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5@\n\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\6\3\6\3\6\7\6K\n\6\f\6\16\6N\13\6\3\7\3\7\3\7\3\7\3\b\3\b\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\5\tk\n\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\7\ty\n\t\f\t\16\t|\13\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\5\f\u0085\n\f\3"+
		"\r\3\r\3\16\3\16\3\16\5\16\u008c\n\16\3\16\3\16\3\17\3\17\3\17\7\17\u0093"+
		"\n\17\f\17\16\17\u0096\13\17\3\17\2\3\20\20\2\4\6\b\n\f\16\20\22\24\26"+
		"\30\32\34\2\5\3\2\16\17\3\2\20\21\3\2\f\r\2\u009d\2\36\3\2\2\2\4-\3\2"+
		"\2\2\6\60\3\2\2\2\b;\3\2\2\2\nG\3\2\2\2\fO\3\2\2\2\16S\3\2\2\2\20j\3\2"+
		"\2\2\22}\3\2\2\2\24\177\3\2\2\2\26\u0081\3\2\2\2\30\u0086\3\2\2\2\32\u0088"+
		"\3\2\2\2\34\u008f\3\2\2\2\36\"\5\4\3\2\37!\5\6\4\2 \37\3\2\2\2!$\3\2\2"+
		"\2\" \3\2\2\2\"#\3\2\2\2#(\3\2\2\2$\"\3\2\2\2%\'\5\b\5\2&%\3\2\2\2\'*"+
		"\3\2\2\2(&\3\2\2\2()\3\2\2\2)+\3\2\2\2*(\3\2\2\2+,\7\2\2\3,\3\3\2\2\2"+
		"-.\7\13\2\2./\7\30\2\2/\5\3\2\2\2\60\61\7\n\2\2\61\62\7\t\2\2\62\63\7"+
		"\30\2\2\63\65\7\3\2\2\64\66\5\n\6\2\65\64\3\2\2\2\65\66\3\2\2\2\66\67"+
		"\3\2\2\2\678\7\4\2\289\7\5\2\29:\5\16\b\2:\7\3\2\2\2;<\7\t\2\2<=\7\30"+
		"\2\2=?\7\3\2\2>@\5\n\6\2?>\3\2\2\2?@\3\2\2\2@A\3\2\2\2AB\7\4\2\2BC\7\5"+
		"\2\2CD\5\16\b\2DE\7\6\2\2EF\5\20\t\2F\t\3\2\2\2GL\5\f\7\2HI\7\7\2\2IK"+
		"\5\f\7\2JH\3\2\2\2KN\3\2\2\2LJ\3\2\2\2LM\3\2\2\2M\13\3\2\2\2NL\3\2\2\2"+
		"OP\7\30\2\2PQ\7\5\2\2QR\5\16\b\2R\r\3\2\2\2ST\7\30\2\2T\17\3\2\2\2UV\b"+
		"\t\1\2VW\7\23\2\2WX\5\20\t\2XY\7\24\2\2YZ\5\20\t\2Z[\7\25\2\2[\\\5\20"+
		"\t\17\\k\3\2\2\2]^\7\3\2\2^_\5\20\t\2_`\7\4\2\2`k\3\2\2\2ab\7\17\2\2b"+
		"k\5\20\t\rcd\7\22\2\2dk\5\20\t\nek\5\22\n\2fk\5\24\13\2gk\5\26\f\2hk\5"+
		"\30\r\2ik\5\32\16\2jU\3\2\2\2j]\3\2\2\2ja\3\2\2\2jc\3\2\2\2je\3\2\2\2"+
		"jf\3\2\2\2jg\3\2\2\2jh\3\2\2\2ji\3\2\2\2kz\3\2\2\2lm\f\f\2\2mn\7\34\2"+
		"\2ny\5\20\t\rop\f\13\2\2pq\t\2\2\2qy\5\20\t\frs\f\t\2\2st\t\3\2\2ty\5"+
		"\20\t\nuv\f\b\2\2vw\7\35\2\2wy\5\20\t\txl\3\2\2\2xo\3\2\2\2xr\3\2\2\2"+
		"xu\3\2\2\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2\2{\21\3\2\2\2|z\3\2\2\2}~\t\4\2"+
		"\2~\23\3\2\2\2\177\u0080\7\31\2\2\u0080\25\3\2\2\2\u0081\u0084\7\32\2"+
		"\2\u0082\u0083\7\b\2\2\u0083\u0085\7\32\2\2\u0084\u0082\3\2\2\2\u0084"+
		"\u0085\3\2\2\2\u0085\27\3\2\2\2\u0086\u0087\7\30\2\2\u0087\31\3\2\2\2"+
		"\u0088\u0089\7\30\2\2\u0089\u008b\7\3\2\2\u008a\u008c\5\34\17\2\u008b"+
		"\u008a\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008e\7\4"+
		"\2\2\u008e\33\3\2\2\2\u008f\u0094\5\20\t\2\u0090\u0091\7\7\2\2\u0091\u0093"+
		"\5\20\t\2\u0092\u0090\3\2\2\2\u0093\u0096\3\2\2\2\u0094\u0092\3\2\2\2"+
		"\u0094\u0095\3\2\2\2\u0095\35\3\2\2\2\u0096\u0094\3\2\2\2\r\"(\65?Ljx"+
		"z\u0084\u008b\u0094";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}