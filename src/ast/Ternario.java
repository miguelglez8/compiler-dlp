/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	ternario:expresion -> expr:expresion  left:expresion  right:expresion

public class Ternario extends AbstractExpresion {

	public Ternario(Expresion expr, Expresion left, Expresion right) {
		this.expr = expr;
		this.left = left;
		this.right = right;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(expr, left, right);
	}

	public Ternario(Object expr, Object left, Object right) {
		this.expr = (Expresion) getAST(expr);
		this.left = (Expresion) getAST(left);
		this.right = (Expresion) getAST(right);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(expr, left, right);
	}

	public Expresion getExpr() {
		return expr;
	}
	public void setExpr(Expresion expr) {
		this.expr = expr;
	}

	public Expresion getLeft() {
		return left;
	}
	public void setLeft(Expresion left) {
		this.left = left;
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

	private Expresion expr;
	private Expresion left;
	private Expresion right;

	public String toString() {
       return "{expr:" + getExpr() + ", left:" + getLeft() + ", right:" + getRight() + "}";
   }
}
