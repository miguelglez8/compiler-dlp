/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	logicExpresion:expresion -> left:expresion  operator:String  right:expresion

public class LogicExpresion extends AbstractExpresion {

	public LogicExpresion(Expresion left, String operator, Expresion right) {
		this.left = left;
		this.operator = operator;
		this.right = right;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(left, right);
	}

	public LogicExpresion(Object left, Object operator, Object right) {
		this.left = (Expresion) getAST(left);
		this.operator = (operator instanceof Token) ? ((Token)operator).getText() : (String) operator;
		this.right = (Expresion) getAST(right);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(left, operator, right);
	}

	public Expresion getLeft() {
		return left;
	}
	public void setLeft(Expresion left) {
		this.left = left;
	}

	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Expresion getRight() {
		return right;
	}
	public void setRight(Expresion right) {
		this.right = right;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion left;
	private String operator;
	private Expresion right;

	public String toString() {
       return "{left:" + getLeft() + ", operator:" + getOperator() + ", right:" + getRight() + "}";
   }
}
