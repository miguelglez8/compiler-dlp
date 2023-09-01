/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import java.util.*;

import visitor.*;

//	if:sentencia -> condicionTrue:expresion  sentenciasTrue:sentencia*

public class If extends AbstractSentencia {

	public If(Expresion condicionTrue, List<Sentencia> sentenciasTrue) {
		this.condicionTrue = condicionTrue;
		this.sentenciasTrue = sentenciasTrue;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(condicionTrue, sentenciasTrue);
	}

	public If(Object condicionTrue, Object sentenciasTrue) {
		this.condicionTrue = (Expresion) getAST(condicionTrue);
		this.sentenciasTrue = this.<Sentencia>getNodesFromContexts(sentenciasTrue);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(condicionTrue, sentenciasTrue);
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

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion condicionTrue;
	private List<Sentencia> sentenciasTrue;

	public String toString() {
       return "{condicionTrue:" + getCondicionTrue() + ", sentenciasTrue:" + getSentenciasTrue() + "}";
   }
}
