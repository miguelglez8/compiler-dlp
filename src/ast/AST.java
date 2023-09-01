/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import visitor.*;

public interface AST {

    public Object accept(Visitor visitor, Object param);

    Position getStart();

    Position getEnd();

    public void setPositions(Object... children);

}
