grammar Grammar
	;
	
import Lexicon
	;

@parser::header {
    import ast.*;
}

start returns[Programa ast]
	: listOperaciones+=operacion* EOF 														 	{ $ast = new Programa($listOperaciones); }
	;
		
operacion returns[Operacion ast]
	:  defVar 															 			{ $ast = $defVar.ast; }                                                                                               
	| 'struct' IDENT '{' listCampos+=campo* '}' ';' 						 				 			{ $ast = new DefStruct($IDENT, $listCampos); }                                                           
	|  IDENT '(' parametros ')' (':' tipo)? '{' listVar+=defVar* listSent+=sentencia* '}' 	 								{ $ast = new DefFuncion($IDENT, $parametros.list, $ctx.tipo != null ? $tipo.ast : new VoidType(), $listVar, $listSent); }
	;

defVar returns[DefVariable ast]
	: 'var' atributo ';'                                                 					 						{ $ast = $atributo.ast; }
	;

parametros returns [List<DefVariable> list = new ArrayList<DefVariable>()]
	: (atributo { $list.add($atributo.ast); } (',' atributo { $list.add($atributo.ast); })*)?
	;

campo returns[Campo ast]
	: IDENT ':' tipo ';'   																    	{ $ast = new Campo($tipo.ast, $IDENT); }
	;

atributo returns[DefVariable ast]
	: IDENT ':' tipo    								 			    				    		{ $ast = new DefVariable($tipo.ast, $IDENT); }
	;

sentencia returns[Sentencia ast]
	: 'if' '(' expr ')' '{' listSentIfSimple+=sentencia* '}'								   				{ $ast = new If($expr.ast, $listSentIfSimple); }                     
	| 'if' '(' expr ')' '{' listSentIf+=sentencia* '}' 'else' '{' listSentElse+=sentencia* '}' 								{ $ast = new Ifelse($expr.ast, $listSentIf, $listSentElse); }         
	| 'while' '(' expr ')' '{' listSentWhile+=sentencia* '}'							 					{ $ast = new While($expr.ast, $listSentWhile); } 
	| 'for' '(' s=sentencia e=expr ';' expr '++' ')' '{' list+=sentencia* '}'             									{ $ast = new For($s.ast, $e.ast, new OperadorMasMas($expr.ast), $list); } 
	| 'do' '{' list+=sentencia* '}' 'while' '(' expr ')' ';'  												{ $ast = new DoWhile($expr.ast, $list); } 
	| 'switch' '(' expr ')' '{' cases+=casee* ('default' ':' list+=sentencia*)? '}'            								{ $ast = new Switch($expr.ast, $cases, $list); } 
	| 'read' expr ';'													 				{ $ast = new Read($expr.ast); }
	| g='print' expr? ';'												     					{ $ast = new Print($ctx.expr != null ? $expr.ast : null); $ast.setPositions($g);}
	| h='printsp' expr? ';'												 					{ $ast = new Printsp($ctx.expr != null ? $expr.ast : null); $ast.setPositions($h);}
	| i='println' expr? ';'												 					{ $ast = new Println($ctx.expr != null ? $expr.ast : null); $ast.setPositions($i);}
	| p='return' expr? ';'												 					{ $ast = new Return($ctx.expr != null ? $expr.ast : null); $ast.setPositions($p); }
	| a=expr '=' b=expr ';'												 					{ $ast = new Asignacion($a.ast, $b.ast); }
	| aa=expr '+=' bb=expr ';'												 				{ $ast = new MasIgual($aa.ast, $bb.ast); }
	| expr '++' ';'												 						{ $ast = new OperadorMasMas($expr.ast); }
	| IDENT '(' params ')' ';'											 					{ $ast = new LlamadaFuncionS($IDENT, $params.list); }
	| params '=' expr ';'              															{ $ast = new AsignacionArray($expr.ast, $params.list); }
	| asigs '=' c=expr ';'												 					{ $ast = new AsignacionE($asigs.list, $c.ast); }
	;

casee returns[Case ast]
	: 'case' expr ':' list+=sentencia* { $ast = new Case($expr.ast, $list, false); } ('break' ';' { $ast.setHasBreak(true);} )?
	;

asigs returns [List<Expresion> list = new ArrayList<Expresion>()]
	: (expr { $list.add($expr.ast); } ('=' expr { $list.add($expr.ast); })*)
	;
	
expr returns[Expresion ast]
	: e=expr '.' IDENT													 				{ $ast = new AccesoCampo($e.ast, $IDENT); }
	| l=expr '[' r=expr ']'												 					{ $ast = new AccesoArray($l.ast, $r.ast); }
	| '(' expr ')'                                                 		 										{ $ast = $expr.ast; }
	| '<' tipo '>' '(' expr ')'                                    		 										{ $ast = new Cast($tipo.ast, $expr.ast); }
	| l=expr op=('*' | '/' | '%') r=expr                           		 										{ $ast = new ArithmeticExpresion($l.ast, $op, $r.ast); }
	| l=expr op=('+' | '-') r=expr									     							{ $ast = new ArithmeticExpresion($l.ast, $op, $r.ast); }
	| l=expr op=('==' | '!=' | '<' | '>' | '<=' | '>=') r=expr           											{ $ast = new RelacionalExpresion($l.ast, $op, $r.ast); }
	| '!' expr                                                           											{ $ast = new NotExpresion($expr.ast); }
	| l=expr op='&&' r=expr										   		 					{ $ast = new LogicExpresion($l.ast, $op, $r.ast); }
	| l=expr op='||' r=expr                                        		 										{ $ast = new LogicExpresion($l.ast, $op, $r.ast); }
	| l=expr op='**' r=expr                                        		 										{ $ast = new LogicExpresion($l.ast, $op, $r.ast); }
	| IDENT '(' params ')'                                               											{ $ast = new LlamadaFuncionE($IDENT, $params.list); } 
	| 'true'                                                		 										{ $ast = new Truee(); }
	| 'false'                                                		 										{ $ast = new Falsee(); }             
	| IDENT                                                        		 										{ $ast = new Variable($IDENT); }
	| INT_CONSTANT                                                 		 										{ $ast = new IntConstant($INT_CONSTANT); }
	| REAL_CONSTANT                                                		 										{ $ast = new RealConstant($REAL_CONSTANT); }
	| CHAR_CONSTANT                                                		 										{ $ast = new CharConstant($CHAR_CONSTANT); }
	| p=expr '?' s=expr ':' t=expr																{ $ast = new Ternario($p.ast, $s.ast, $t.ast); }
	;	

params returns [List<Expresion> list = new ArrayList<Expresion>()]
	: (expr { $list.add($expr.ast); } (',' expr { $list.add($expr.ast); })*)?
	;
	
tipo returns[Tipo ast]
	: 'int' 															 			{  $ast = new IntType(); }
	| 'float' 															 			{  $ast = new FloatType(); }
	| 'char'															 			{  $ast = new CharType(); }
	| 'boolean'                                                                                 								{  $ast = new BooleanType(); }
	| IDENT                         									 						{  $ast = new StringType($IDENT); }															
	| '[' INT_CONSTANT ']' tipo																{  $ast = new VectorType($INT_CONSTANT, $tipo.ast); }     								    
	;
	

