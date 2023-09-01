/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	defFuncion:operacion -> nombre:String  parametros:defVariable*  retorno:tipo  variablesLocales:defVariable*  sentencias:sentencia*

public class DefFuncion extends AbstractOperacion {

	public DefFuncion(String nombre, List<DefVariable> parametros, Tipo retorno, List<DefVariable> variablesLocales, List<Sentencia> sentencias) {
		this.nombre = nombre;
		this.parametros = parametros;
		this.retorno = retorno;
		this.variablesLocales = variablesLocales;
		this.sentencias = sentencias;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(parametros, retorno, variablesLocales, sentencias);
	}

	public DefFuncion(Object nombre, Object parametros, Object retorno, Object variablesLocales, Object sentencias) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getText() : (String) nombre;
		this.parametros = this.<DefVariable>getNodesFromContexts(parametros);
		this.retorno = (Tipo) getAST(retorno);
		this.variablesLocales = this.<DefVariable>getNodesFromContexts(variablesLocales);
		this.sentencias = this.<Sentencia>getNodesFromContexts(sentencias);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(nombre, parametros, retorno, variablesLocales, sentencias);
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DefVariable> getParametros() {
		return parametros;
	}
	public void setParametros(List<DefVariable> parametros) {
		this.parametros = parametros;
	}

	public Tipo getRetorno() {
		return retorno;
	}
	public void setRetorno(Tipo retorno) {
		this.retorno = retorno;
	}

	public List<DefVariable> getVariablesLocales() {
		return variablesLocales;
	}
	public void setVariablesLocales(List<DefVariable> variablesLocales) {
		this.variablesLocales = variablesLocales;
	}

	public List<Sentencia> getSentencias() {
		return sentencias;
	}
	public void setSentencias(List<Sentencia> sentencias) {
		this.sentencias = sentencias;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<DefVariable> parametros;
	private Tipo retorno;
	private List<DefVariable> variablesLocales;
	private List<Sentencia> sentencias;

	public String toString() {
       return "{nombre:" + getNombre() + ", parametros:" + getParametros() + ", retorno:" + getRetorno() + ", variablesLocales:" + getVariablesLocales() + ", sentencias:" + getSentencias() + "}";
   }
}
