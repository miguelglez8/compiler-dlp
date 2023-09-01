/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	switch:sentencia -> condicion:expresion  cases:case*  sentencia:sentencia*

public class Switch extends AbstractSentencia {

	public Switch(Expresion condicion, List<Case> cases, List<Sentencia> sentencia) {
		this.condicion = condicion;
		this.cases = cases;
		this.sentencia = sentencia;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(condicion, cases, sentencia);
	}

	public Switch(Object condicion, Object cases, Object sentencia) {
		this.condicion = (Expresion) getAST(condicion);
		this.cases = this.<Case>getNodesFromContexts(cases);
		this.sentencia = this.<Sentencia>getNodesFromContexts(sentencia);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(condicion, cases, sentencia);
	}

	public Expresion getCondicion() {
		return condicion;
	}
	public void setCondicion(Expresion condicion) {
		this.condicion = condicion;
	}

	public List<Case> getCases() {
		return cases;
	}
	public void setCases(List<Case> cases) {
		this.cases = cases;
	}

	public List<Sentencia> getSentencia() {
		return sentencia;
	}
	public void setSentencia(List<Sentencia> sentencia) {
		this.sentencia = sentencia;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion condicion;
	private List<Case> cases;
	private List<Sentencia> sentencia;

	public String toString() {
       return "{condicion:" + getCondicion() + ", cases:" + getCases() + ", sentencia:" + getSentencia() + "}";
   }
}
