/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	accesoCampo:expresion -> expresion:expresion  campo:String

public class AccesoCampo extends AbstractExpresion {

	public AccesoCampo(Expresion expresion, String campo) {
		this.expresion = expresion;
		this.campo = campo;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(expresion);
	}

	public AccesoCampo(Object expresion, Object campo) {
		this.expresion = (Expresion) getAST(expresion);
		this.campo = (campo instanceof Token) ? ((Token)campo).getText() : (String) campo;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(expresion, campo);
	}

	public Expresion getExpresion() {
		return expresion;
	}
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion expresion;
	private String campo;

	public String toString() {
       return "{expresion:" + getExpresion() + ", campo:" + getCampo() + "}";
   }
}
