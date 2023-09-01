/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package visitor;

import ast.*;

public interface Visitor {
	public Object visit(Programa programa, Object param);
	public Object visit(DefVariable defVariable, Object param);
	public Object visit(DefStruct defStruct, Object param);
	public Object visit(DefFuncion defFuncion, Object param);
	public Object visit(Campo campo, Object param);
	public Object visit(If _if, Object param);
	public Object visit(Ifelse ifelse, Object param);
	public Object visit(While _while, Object param);
	public Object visit(For _for, Object param);
	public Object visit(DoWhile doWhile, Object param);
	public Object visit(Switch _switch, Object param);
	public Object visit(Read read, Object param);
	public Object visit(Print print, Object param);
	public Object visit(Printsp printsp, Object param);
	public Object visit(Println println, Object param);
	public Object visit(Return _return, Object param);
	public Object visit(Asignacion asignacion, Object param);
	public Object visit(MasIgual masIgual, Object param);
	public Object visit(OperadorMasMas operadorMasMas, Object param);
	public Object visit(LlamadaFuncionS llamadaFuncionS, Object param);
	public Object visit(AsignacionArray asignacionArray, Object param);
	public Object visit(AsignacionE asignacionE, Object param);
	public Object visit(Case _case, Object param);
	public Object visit(ArithmeticExpresion arithmeticExpresion, Object param);
	public Object visit(LogicExpresion logicExpresion, Object param);
	public Object visit(RelacionalExpresion relacionalExpresion, Object param);
	public Object visit(NotExpresion notExpresion, Object param);
	public Object visit(AccesoArray accesoArray, Object param);
	public Object visit(AccesoCampo accesoCampo, Object param);
	public Object visit(LlamadaFuncionE llamadaFuncionE, Object param);
	public Object visit(Cast cast, Object param);
	public Object visit(Variable variable, Object param);
	public Object visit(IntConstant intConstant, Object param);
	public Object visit(RealConstant realConstant, Object param);
	public Object visit(CharConstant charConstant, Object param);
	public Object visit(Truee truee, Object param);
	public Object visit(Falsee falsee, Object param);
	public Object visit(Ternario ternario, Object param);
	public Object visit(IntType intType, Object param);
	public Object visit(FloatType floatType, Object param);
	public Object visit(CharType charType, Object param);
	public Object visit(BooleanType booleanType, Object param);
	public Object visit(VoidType voidType, Object param);
	public Object visit(StringType stringType, Object param);
	public Object visit(VectorType vectorType, Object param);
}
