/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	asignacionArray:sentencia -> array:expresion  parametros:expresion*

public class AsignacionArray extends AbstractSentencia {

	public AsignacionArray(Expresion array, List<Expresion> parametros) {
		this.array = array;
		this.parametros = parametros;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(array, parametros);
	}

	public AsignacionArray(Object array, Object parametros) {
		this.array = (Expresion) getAST(array);
		this.parametros = this.<Expresion>getNodesFromContexts(parametros);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(array, parametros);
	}

	public Expresion getArray() {
		return array;
	}
	public void setArray(Expresion array) {
		this.array = array;
	}

	public List<Expresion> getParametros() {
		return parametros;
	}
	public void setParametros(List<Expresion> parametros) {
		this.parametros = parametros;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion array;
	private List<Expresion> parametros;

	public String toString() {
       return "{array:" + getArray() + ", parametros:" + getParametros() + "}";
   }
}
