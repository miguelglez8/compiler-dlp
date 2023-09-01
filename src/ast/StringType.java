/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	stringType:tipo -> nombre:String

public class StringType extends AbstractTipo {
	
	// para el caso de definir los structs
	private DefStruct struct; 
								
	public DefStruct getDefinicion() { 
		return struct; 
	} 
								
	public void setDefinicion(DefStruct struct) { 
		this.struct = struct; 
	}	 

	public StringType(String nombre) {
		this.nombre = nombre;
	}

	public StringType(Object nombre) {
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
	
	@Override
	public int getSize() {
		int size = 0;
		for (int i = 0; i < struct.getAtributos().size(); i++) {
			size += struct.getAtributos().get(i).getTipo().getSize();
		}
		return size;
	}

	public String toString() {
       return "{nombre:" + getNombre() + "}";
   }

	@Override
	public char getSuffix() {
		return 0;
	}

	@Override
	public String getMAPLName() {
		return nombre;
	}


}
