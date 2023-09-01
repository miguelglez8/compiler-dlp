/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import java.util.*;
import visitor.*;

//	programa -> operaciones:operacion*

public class Programa extends AbstractAST  {

	public Programa(List<Operacion> operaciones) {
		this.operaciones = operaciones;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(operaciones);
	}

	public Programa(Object operaciones) {
		this.operaciones = this.<Operacion>getNodesFromContexts(operaciones);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(operaciones);
	}

	public List<Operacion> getOperaciones() {
		return operaciones;
	}
	public void setOperaciones(List<Operacion> operaciones) {
		this.operaciones = operaciones;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private List<Operacion> operaciones;

	public String toString() {
       return "{operaciones:" + getOperaciones() + "}";
   }
}
