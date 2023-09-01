/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	variable:expresion -> nombre:String

public class Variable extends AbstractExpresion {

	// para el caso de definir las variables
	private DefVariable definition; 
	
	public DefVariable getDefinicion() { 
		return definition; 
	} 
	
	public void setDefinicion(DefVariable definition) { 
		this.definition = definition; 
	}
	
	// para el caso de la definici�n de funciones
	private DefFuncion funcion; 
		
	public DefFuncion getFuncion() { 
		return funcion; 
	} 
		
	public void setFuncion(DefFuncion funcion) { 
		this.funcion = funcion; 
	}

	
	public Variable(String nombre) {
		this.nombre = nombre;
	}

	public Variable(Object nombre) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getText() : (String) nombre;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(nombre);
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;

	public String toString() {
       return "{nombre:" + getNombre() + "}";
   }
}
