/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import visitor.*;

//	voidType:tipo -> 

public class VoidType extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	@Override
	public int getSize() {
		return 0;
	}

	public String toString() {
       return "{VoidType}";
    }

	@Override
	public char getSuffix() {
		return 0;
	}

	@Override
	public String getMAPLName() {
		return null;
	}
	
} 
