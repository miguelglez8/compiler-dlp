/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package visitor;

import ast.*;
import java.util.*;

/*
DefaultVisitor. Implementación base del visitor para ser derivada por nuevos visitor.
	No modificar esta clase. Para crear nuevos visitor usar el fichero "_PlantillaParaVisitors.txt".
	DefaultVisitor ofrece una implementación por defecto de cada nodo que se limita a visitar los nodos hijos.
*/
public class DefaultVisitor implements Visitor {

	//	class Programa { List<Operacion> operaciones; }
	public Object visit(Programa programa, Object param) {
		visitChildren(programa.getOperaciones(), param);
		return null;
	}

	//	class DefVariable { Tipo tipo;  String nombre; }
	public Object visit(DefVariable defVariable, Object param) {
		if (defVariable.getTipo() != null)
			defVariable.getTipo().accept(this, param);
		return null;
	}

	//	class DefStruct { String nombre;  List<Campo> atributos; }
	public Object visit(DefStruct defStruct, Object param) {
		visitChildren(defStruct.getAtributos(), param);
		return null;
	}

	//	class DefFuncion { String nombre;  List<DefVariable> parametros;  Tipo retorno;  List<DefVariable> variablesLocales;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion defFuncion, Object param) {
		visitChildren(defFuncion.getParametros(), param);
		if (defFuncion.getRetorno() != null)
			defFuncion.getRetorno().accept(this, param);
		visitChildren(defFuncion.getVariablesLocales(), param);
		visitChildren(defFuncion.getSentencias(), param);
		return null;
	}

	//	class Campo { Tipo tipo;  String nombre; }
	public Object visit(Campo campo, Object param) {
		if (campo.getTipo() != null)
			campo.getTipo().accept(this, param);
		return null;
	}

	//	class If { Expresion condicionTrue;  List<Sentencia> sentenciasTrue; }
	public Object visit(If _if, Object param) {
		if (_if.getCondicionTrue() != null)
			_if.getCondicionTrue().accept(this, param);
		visitChildren(_if.getSentenciasTrue(), param);
		return null;
	}

	//	class Ifelse { Expresion condicionTrue;  List<Sentencia> sentenciasTrue;  List<Sentencia> sentenciasFalse; }
	public Object visit(Ifelse ifelse, Object param) {
		if (ifelse.getCondicionTrue() != null)
			ifelse.getCondicionTrue().accept(this, param);
		visitChildren(ifelse.getSentenciasTrue(), param);
		visitChildren(ifelse.getSentenciasFalse(), param);
		return null;
	}

	//	class While { Expresion condicionWhile;  List<Sentencia> sentencia; }
	public Object visit(While _while, Object param) {
		if (_while.getCondicionWhile() != null)
			_while.getCondicionWhile().accept(this, param);
		visitChildren(_while.getSentencia(), param);
		return null;
	}

	//	class For { Sentencia asignacion;  Expresion condicionFor;  Sentencia incremento;  List<Sentencia> sentencia; }
	public Object visit(For _for, Object param) {
		if (_for.getAsignacion() != null)
			_for.getAsignacion().accept(this, param);
		if (_for.getCondicionFor() != null)
			_for.getCondicionFor().accept(this, param);
		if (_for.getIncremento() != null)
			_for.getIncremento().accept(this, param);
		visitChildren(_for.getSentencia(), param);
		return null;
	}

	//	class DoWhile { Expresion condicionDoWhile;  List<Sentencia> sentencia; }
	public Object visit(DoWhile doWhile, Object param) {
		if (doWhile.getCondicionDoWhile() != null)
			doWhile.getCondicionDoWhile().accept(this, param);
		visitChildren(doWhile.getSentencia(), param);
		return null;
	}

	//	class Switch { Expresion condicion;  List<Case> cases;  List<Sentencia> sentencia; }
	public Object visit(Switch _switch, Object param) {
		if (_switch.getCondicion() != null)
			_switch.getCondicion().accept(this, param);
		visitChildren(_switch.getCases(), param);
		visitChildren(_switch.getSentencia(), param);
		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read read, Object param) {
		if (read.getExpresion() != null)
			read.getExpresion().accept(this, param);
		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print print, Object param) {
		if (print.getExpresion() != null)
			print.getExpresion().accept(this, param);
		return null;
	}

	//	class Printsp { Expresion expresion; }
	public Object visit(Printsp printsp, Object param) {
		if (printsp.getExpresion() != null)
			printsp.getExpresion().accept(this, param);
		return null;
	}

	//	class Println { Expresion expresion; }
	public Object visit(Println println, Object param) {
		if (println.getExpresion() != null)
			println.getExpresion().accept(this, param);
		return null;
	}

	//	class Return { Expresion expresion; }
	public Object visit(Return _return, Object param) {
		if (_return.getExpresion() != null)
			_return.getExpresion().accept(this, param);
		return null;
	}

	//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion asignacion, Object param) {
		if (asignacion.getLeft() != null)
			asignacion.getLeft().accept(this, param);
		if (asignacion.getRight() != null)
			asignacion.getRight().accept(this, param);
		return null;
	}

	//	class MasIgual { Expresion left;  Expresion right; }
	public Object visit(MasIgual masIgual, Object param) {
		if (masIgual.getLeft() != null)
			masIgual.getLeft().accept(this, param);
		if (masIgual.getRight() != null)
			masIgual.getRight().accept(this, param);
		return null;
	}

	//	class OperadorMasMas { Expresion left; }
	public Object visit(OperadorMasMas operadorMasMas, Object param) {
		if (operadorMasMas.getLeft() != null)
			operadorMasMas.getLeft().accept(this, param);
		return null;
	}

	//	class LlamadaFuncionS { String nombre;  List<Expresion> parametros; }
	public Object visit(LlamadaFuncionS llamadaFuncionS, Object param) {
		visitChildren(llamadaFuncionS.getParametros(), param);
		return null;
	}

	//	class AsignacionArray { Expresion array;  List<Expresion> parametros; }
	public Object visit(AsignacionArray asignacionArray, Object param) {
		if (asignacionArray.getArray() != null)
			asignacionArray.getArray().accept(this, param);
		visitChildren(asignacionArray.getParametros(), param);
		return null;
	}

	//	class AsignacionE { List<Expresion> list;  Expresion right; }
	public Object visit(AsignacionE asignacionE, Object param) {
		visitChildren(asignacionE.getList(), param);
		if (asignacionE.getRight() != null)
			asignacionE.getRight().accept(this, param);
		return null;
	}

	//	class Case { Expresion condicion;  List<Sentencia> sentencia;  boolean hasBreak; }
	public Object visit(Case _case, Object param) {
		if (_case.getCondicion() != null)
			_case.getCondicion().accept(this, param);
		visitChildren(_case.getSentencia(), param);
		return null;
	}

	//	class ArithmeticExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(ArithmeticExpresion arithmeticExpresion, Object param) {
		if (arithmeticExpresion.getLeft() != null)
			arithmeticExpresion.getLeft().accept(this, param);
		if (arithmeticExpresion.getRight() != null)
			arithmeticExpresion.getRight().accept(this, param);
		return null;
	}

	//	class LogicExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(LogicExpresion logicExpresion, Object param) {
		if (logicExpresion.getLeft() != null)
			logicExpresion.getLeft().accept(this, param);
		if (logicExpresion.getRight() != null)
			logicExpresion.getRight().accept(this, param);
		return null;
	}

	//	class RelacionalExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(RelacionalExpresion relacionalExpresion, Object param) {
		if (relacionalExpresion.getLeft() != null)
			relacionalExpresion.getLeft().accept(this, param);
		if (relacionalExpresion.getRight() != null)
			relacionalExpresion.getRight().accept(this, param);
		return null;
	}

	//	class NotExpresion { Expresion expresion; }
	public Object visit(NotExpresion notExpresion, Object param) {
		if (notExpresion.getExpresion() != null)
			notExpresion.getExpresion().accept(this, param);
		return null;
	}

	//	class AccesoArray { Expresion left;  Expresion right; }
	public Object visit(AccesoArray accesoArray, Object param) {
		if (accesoArray.getLeft() != null)
			accesoArray.getLeft().accept(this, param);
		if (accesoArray.getRight() != null)
			accesoArray.getRight().accept(this, param);
		return null;
	}

	//	class AccesoCampo { Expresion expresion;  String campo; }
	public Object visit(AccesoCampo accesoCampo, Object param) {
		if (accesoCampo.getExpresion() != null)
			accesoCampo.getExpresion().accept(this, param);
		return null;
	}

	//	class LlamadaFuncionE { String nombre;  List<Expresion> parametros; }
	public Object visit(LlamadaFuncionE llamadaFuncionE, Object param) {
		visitChildren(llamadaFuncionE.getParametros(), param);
		return null;
	}

	//	class Cast { int casteo;  Expresion expresion; }
	public Object visit(Cast cast, Object param) {
		if (cast.getExpresion() != null)
			cast.getExpresion().accept(this, param);
		return null;
	}

	//	class Variable { String nombre; }
	public Object visit(Variable variable, Object param) {
		return null;
	}

	//	class IntConstant { String valor; }
	public Object visit(IntConstant intConstant, Object param) {
		return null;
	}

	//	class RealConstant { String valor; }
	public Object visit(RealConstant realConstant, Object param) {
		return null;
	}

	//	class CharConstant { String valor; }
	public Object visit(CharConstant charConstant, Object param) {
		return null;
	}

	//	class Truee {  }
	public Object visit(Truee truee, Object param) {
		return null;
	}

	//	class Falsee {  }
	public Object visit(Falsee falsee, Object param) {
		return null;
	}

	//	class Ternario { Expresion expr;  Expresion left;  Expresion right; }
	public Object visit(Ternario ternario, Object param) {
		if (ternario.getExpr() != null)
			ternario.getExpr().accept(this, param);
		if (ternario.getLeft() != null)
			ternario.getLeft().accept(this, param);
		if (ternario.getRight() != null)
			ternario.getRight().accept(this, param);
		return null;
	}

	//	class IntType {  }
	public Object visit(IntType intType, Object param) {
		return null;
	}

	//	class FloatType {  }
	public Object visit(FloatType floatType, Object param) {
		return null;
	}

	//	class CharType {  }
	public Object visit(CharType charType, Object param) {
		return null;
	}

	//	class BooleanType {  }
	public Object visit(BooleanType booleanType, Object param) {
		return null;
	}

	//	class VoidType {  }
	public Object visit(VoidType voidType, Object param) {
		return null;
	}

	//	class StringType { String nombre; }
	public Object visit(StringType stringType, Object param) {
		return null;
	}

	//	class VectorType { int tamaño;  Tipo tipo; }
	public Object visit(VectorType vectorType, Object param) {
		if (vectorType.getTipo() != null)
			vectorType.getTipo().accept(this, param);
		return null;
	}

    // Método auxiliar -----------------------------
    protected void visitChildren(List<? extends AST> children, Object param) {
        if (children != null)
            for (AST child : children)
                child.accept(this, param);
    }
}
