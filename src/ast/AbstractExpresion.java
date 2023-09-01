/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

public abstract class AbstractExpresion extends AbstractAST implements Expresion {
	public void setType(Tipo type) { 
		 this.type = type; 
	} 
	public Tipo getType() { 
		 return type; 
	} 
	public void setModificable(boolean modificable) { 
		 this.modificable = modificable; 
	} 
	public boolean isModificable() { 
		 return modificable; 
	} 
	private Tipo type; 
	private boolean modificable; 
}
