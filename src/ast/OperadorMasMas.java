/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	operadorMasMas:sentencia -> left:expresion

public class OperadorMasMas extends AbstractSentencia {

	public OperadorMasMas(Expresion left) {
		this.left = left;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(left);
	}

	public OperadorMasMas(Object left) {
		this.left = (Expresion) getAST(left);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(left);
	}

	public Expresion getLeft() {
		return left;
	}
	public void setLeft(Expresion left) {
		this.left = left;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion left;

	public String toString() {
       return "{left:" + getLeft() + "}";
   }
}
