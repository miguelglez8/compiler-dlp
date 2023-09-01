/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import visitor.*;

//	intType:tipo -> 

public class IntType extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	public int getSize() { 
		return 2; 
	}

	public String toString() {
       return "{IntType}";
    }

	@Override
	public char getSuffix() {
		return 'i';
	}

	@Override
	public String getMAPLName() {
		return "int";
	}
	
}
