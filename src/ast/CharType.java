/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import visitor.*;

//	charType:tipo -> 

public class CharType extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	public int getSize() { 
	    return 1; 
	}

	public String toString() {
       return "{CharType}";
    }

	@Override
	public char getSuffix() {
		return 'b';
	}

	@Override
	public String getMAPLName() {
		return "char";
	}

}
