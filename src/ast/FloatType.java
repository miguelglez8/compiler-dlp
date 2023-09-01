/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import visitor.*;

//	floatType:tipo -> 

public class FloatType extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	public int getSize() { 
		return 4; 
	}

	public String toString() {
       return "{FloatType}";
    }

	@Override
	public char getSuffix() {
		return 'f';
	}

	@Override
	public String getMAPLName() {
		return "float";
	}

}
