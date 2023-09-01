/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	case -> condicion:expresion  sentencia:sentencia*  hasBreak:boolean

public class Case extends AbstractAST  {

	public Case(Expresion condicion, List<Sentencia> sentencia, boolean hasBreak) {
		this.condicion = condicion;
		this.sentencia = sentencia;
		this.hasBreak = hasBreak;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(condicion, sentencia);
	}

	public Case(Object condicion, Object sentencia, Object hasBreak) {
		this.condicion = (Expresion) getAST(condicion);
		this.sentencia = this.<Sentencia>getNodesFromContexts(sentencia);
		this.hasBreak = (hasBreak instanceof Token) ? Boolean.valueOf(((Token)hasBreak).getText()) : (Boolean) hasBreak;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(condicion, sentencia, hasBreak);
	}

	public Expresion getCondicion() {
		return condicion;
	}
	public void setCondicion(Expresion condicion) {
		this.condicion = condicion;
	}

	public List<Sentencia> getSentencia() {
		return sentencia;
	}
	public void setSentencia(List<Sentencia> sentencia) {
		this.sentencia = sentencia;
	}

	public boolean getHasBreak() {
		return hasBreak;
	}
	public void setHasBreak(boolean hasBreak) {
		this.hasBreak = hasBreak;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion condicion;
	private List<Sentencia> sentencia;
	private boolean hasBreak;

	public String toString() {
       return "{condicion:" + getCondicion() + ", sentencia:" + getSentencia() + ", hasBreak:" + getHasBreak() + "}";
   }
}
