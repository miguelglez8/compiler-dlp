/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	campo:operacion -> tipo:tipo  nombre:String

public class Campo extends AbstractOperacion {
	// dirección de la variable
	private int address;
		
	public int getAddress() { 
		return address; 
	} 
		
	public void setAddress(int address) { 
		this.address = address; 
	}

	public Campo(Tipo tipo, String nombre) {
		this.tipo = tipo;
		this.nombre = nombre;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(tipo);
	}

	public Campo(Object tipo, Object nombre) {
		this.tipo = (Tipo) getAST(tipo);
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getText() : (String) nombre;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(tipo, nombre);
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
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

	private Tipo tipo;
	private String nombre;

	public String toString() {
       return "{tipo:" + getTipo() + ", nombre:" + getNombre() + "}";
   }
}
