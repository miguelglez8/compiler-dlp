/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	while:sentencia -> condicionWhile:expresion  sentencia:sentencia*

public class While extends AbstractSentencia {

	public While(Expresion condicionWhile, List<Sentencia> sentencia) {
		this.condicionWhile = condicionWhile;
		this.sentencia = sentencia;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(condicionWhile, sentencia);
	}

	public While(Object condicionWhile, Object sentencia) {
		this.condicionWhile = (Expresion) getAST(condicionWhile);
		this.sentencia = this.<Sentencia>getNodesFromContexts(sentencia);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(condicionWhile, sentencia);
	}

	public Expresion getCondicionWhile() {
		return condicionWhile;
	}
	public void setCondicionWhile(Expresion condicionWhile) {
		this.condicionWhile = condicionWhile;
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

	private Expresion condicionWhile;
	private List<Sentencia> sentencia;

	public String toString() {
       return "{condicionWhile:" + getCondicionWhile() + ", sentencia:" + getSentencia() + "}";
   }
}
