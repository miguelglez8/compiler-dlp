/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	ifelse:sentencia -> condicionTrue:expresion  sentenciasTrue:sentencia*  sentenciasFalse:sentencia*

public class Ifelse extends AbstractSentencia {

	public Ifelse(Expresion condicionTrue, List<Sentencia> sentenciasTrue, List<Sentencia> sentenciasFalse) {
		this.condicionTrue = condicionTrue;
		this.sentenciasTrue = sentenciasTrue;
		this.sentenciasFalse = sentenciasFalse;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(condicionTrue, sentenciasTrue, sentenciasFalse);
	}

	public Ifelse(Object condicionTrue, Object sentenciasTrue, Object sentenciasFalse) {
		this.condicionTrue = (Expresion) getAST(condicionTrue);
		this.sentenciasTrue = this.<Sentencia>getNodesFromContexts(sentenciasTrue);
		this.sentenciasFalse = this.<Sentencia>getNodesFromContexts(sentenciasFalse);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(condicionTrue, sentenciasTrue, sentenciasFalse);
	}

	public Expresion getCondicionTrue() {
		return condicionTrue;
	}
	public void setCondicionTrue(Expresion condicionTrue) {
		this.condicionTrue = condicionTrue;
	}

	public List<Sentencia> getSentenciasTrue() {
		return sentenciasTrue;
	}
	public void setSentenciasTrue(List<Sentencia> sentenciasTrue) {
		this.sentenciasTrue = sentenciasTrue;
	}

	public List<Sentencia> getSentenciasFalse() {
		return sentenciasFalse;
	}
	public void setSentenciasFalse(List<Sentencia> sentenciasFalse) {
		this.sentenciasFalse = sentenciasFalse;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion condicionTrue;
	private List<Sentencia> sentenciasTrue;
	private List<Sentencia> sentenciasFalse;

	public String toString() {
       return "{condicionTrue:" + getCondicionTrue() + ", sentenciasTrue:" + getSentenciasTrue() + ", sentenciasFalse:" + getSentenciasFalse() + "}";
   }
}
