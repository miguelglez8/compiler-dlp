/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import visitor.*;

//	booleanType:tipo -> 

public class BooleanType extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}


	public String toString() {
       return "{BooleanType}";
   }


	@Override
	public int getSize() {
		return 2;
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
