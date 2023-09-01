/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	doWhile:sentencia -> condicionDoWhile:expresion  sentencia:sentencia*

public class DoWhile extends AbstractSentencia {

	public DoWhile(Expresion condicionDoWhile, List<Sentencia> sentencia) {
		this.condicionDoWhile = condicionDoWhile;
		this.sentencia = sentencia;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(condicionDoWhile, sentencia);
	}

	public DoWhile(Object condicionDoWhile, Object sentencia) {
		this.condicionDoWhile = (Expresion) getAST(condicionDoWhile);
		this.sentencia = this.<Sentencia>getNodesFromContexts(sentencia);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(condicionDoWhile, sentencia);
	}

	public Expresion getCondicionDoWhile() {
		return condicionDoWhile;
	}
	public void setCondicionDoWhile(Expresion condicionDoWhile) {
		this.condicionDoWhile = condicionDoWhile;
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

	private Expresion condicionDoWhile;
	private List<Sentencia> sentencia;

	public String toString() {
       return "{condicionDoWhile:" + getCondicionDoWhile() + ", sentencia:" + getSentencia() + "}";
   }
}
