/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	for:sentencia -> asignacion:sentencia  condicionFor:expresion  incremento:sentencia  sentencia:sentencia*

public class For extends AbstractSentencia {

	public For(Sentencia asignacion, Expresion condicionFor, Sentencia incremento, List<Sentencia> sentencia) {
		this.asignacion = asignacion;
		this.condicionFor = condicionFor;
		this.incremento = incremento;
		this.sentencia = sentencia;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(asignacion, condicionFor, incremento, sentencia);
	}

	public For(Object asignacion, Object condicionFor, Object incremento, Object sentencia) {
		this.asignacion = (Sentencia) getAST(asignacion);
		this.condicionFor = (Expresion) getAST(condicionFor);
		this.incremento = (Sentencia) getAST(incremento);
		this.sentencia = this.<Sentencia>getNodesFromContexts(sentencia);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(asignacion, condicionFor, incremento, sentencia);
	}

	public Sentencia getAsignacion() {
		return asignacion;
	}
	public void setAsignacion(Sentencia asignacion) {
		this.asignacion = asignacion;
	}

	public Expresion getCondicionFor() {
		return condicionFor;
	}
	public void setCondicionFor(Expresion condicionFor) {
		this.condicionFor = condicionFor;
	}

	public Sentencia getIncremento() {
		return incremento;
	}
	public void setIncremento(Sentencia incremento) {
		this.incremento = incremento;
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

	private Sentencia asignacion;
	private Expresion condicionFor;
	private Sentencia incremento;
	private List<Sentencia> sentencia;

	public String toString() {
       return "{asignacion:" + getAsignacion() + ", condicionFor:" + getCondicionFor() + ", incremento:" + getIncremento() + ", sentencia:" + getSentencia() + "}";
   }
}
