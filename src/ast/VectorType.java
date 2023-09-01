/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	vectorType:tipo -> tamaño:int  tipo:tipo

public class VectorType extends AbstractTipo {

	public VectorType(int tamaño, Tipo tipo) {
		this.tamaño = tamaño;
		this.tipo = tipo;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(tipo);
	}

	public VectorType(Object tamaño, Object tipo) {
		this.tamaño = (tamaño instanceof Token) ? Integer.parseInt(((Token)tamaño).getText()) : (Integer) tamaño;
		this.tipo = (Tipo) getAST(tipo);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(tamaño, tipo);
	}

	public int getTamaño() {
		return tamaño;
	}
	
	public void setTamaño(int tamaño) {
		this.tamaño = tamaño;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private int tamaño;
	private Tipo tipo;
	
	@Override
	public int getSize() {
		return tamaño * tipo.getSize();
	}

	public String toString() {
       return "{tamaño:" + getTamaño() + ", tipo:" + getTipo() + "}";
	}

	@Override
	public char getSuffix() {
		return 0;
	}

	@Override
	public String getMAPLName() {
		return tamaño + "*" + tipo.getMAPLName();
	}
	
}
