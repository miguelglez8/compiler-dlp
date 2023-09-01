/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	asignacionE:sentencia -> list:expresion*  right:expresion

public class AsignacionE extends AbstractSentencia {

	public AsignacionE(List<Expresion> list, Expresion right) {
		this.list = list;
		this.right = right;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(list, right);
	}

	public AsignacionE(Object list, Object right) {
		this.list = this.<Expresion>getNodesFromContexts(list);
		this.right = (Expresion) getAST(right);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(list, right);
	}

	public List<Expresion> getList() {
		return list;
	}
	public void setList(List<Expresion> list) {
		this.list = list;
	}

	public Expresion getRight() {
		return right;
	}
	public void setRight(Expresion right) {
		this.right = right;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private List<Expresion> list;
	private Expresion right;

	public String toString() {
       return "{list:" + getList() + ", right:" + getRight() + "}";
   }
}
