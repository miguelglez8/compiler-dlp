/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	cast:expresion -> casteo:tipo  expresion:expresion

public class Cast extends AbstractExpresion {

	public Cast(Tipo casteo, Expresion expresion) {
		this.casteo = casteo;
		this.expresion = expresion;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(casteo, expresion);
	}

	public Cast(Object casteo, Object expresion) {
		this.casteo = (Tipo) getAST(casteo);
		this.expresion = (Expresion) getAST(expresion);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(casteo, expresion);
	}

	public Tipo getCasteo() {
		return casteo;
	}
	public void setCasteo(Tipo casteo) {
		this.casteo = casteo;
	}

	public Expresion getExpresion() {
		return expresion;
	}
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Tipo casteo;
	private Expresion expresion;

	public String toString() {
       return "{casteo:" + getCasteo() + ", expresion:" + getExpresion() + "}";
   }
}
