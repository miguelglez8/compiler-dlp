/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

public abstract class AbstractAST implements AST {

    @Override
    public Position getStart() {
        return start;
    }

    @Override
    public Position getEnd() {
        return end;
    }

    // setPositions. Hace que el nodo actualice sus posiciones `start` y `end` a partir de las de
    // los nodos pasados como argumento. Es decir, establece:
    // 1. Su posición inicial (`start`) a partir del hijo que tenga la posición `start` MENOR.
    // 2. Su posición final (`end`) a partir del hijo que tenga su posición `end` MAYOR.
    //
    // Si el nodo ya tiene `start` inicializado y los argumentos tienen un `start` mayor a éste, el
    // nodo deja su `start` como estaba. De la misma manera, el `end` sólo se modifica si los
    // argumentos ofrecen un `end` mayor.

    @Override
    public void setPositions(Object... children) {
        List<Object> childrenList = Arrays.asList(children);
        this.start = findStart(childrenList);
        this.end = findEnd(childrenList);
        invariant();
    }

    // -----------------------------------------------
    // # Métodos protected para ser llamado desde los constructores de las clases AST

    // Dado un Contexto de una regla de ANTLR, extrae su campo "ast".
    protected AST getAST(ParserRuleContext node) {
        try {
            return (AST) node.getClass().getField("ast").get(node);
        } catch (Exception e) {
            throw new IllegalArgumentException("El objecto pasado no tiene un atributo público llamado 'ast'");
        }
    }

    // Dado un AST o un ParserRuleContext, devuelve el AST.
    // - Si recibe un AST, no hace nada y devuelve el mismo objeto.
    // - Si recibe un ParserRuleContext, extrae el `AST` de su atributo `ast`.
    protected AST getAST(Object node) {
        if (node == null)
            return null; // No es error que no haya hijo
        if (node instanceof ParserRuleContext)
            return getAST((ParserRuleContext) node);
        if (node instanceof AST)
            return (AST) node;
        throw new IllegalArgumentException("El objecto pasado tiene que ser un `AST` o un `ParserRuleContext`");
    }

    // Recibe una List<AST> o List<ParserRuleContext> y devuelve una List<AST>.
    // - Si recibe List<AST>, no hace nada y devuelve los mismos elementos.
    // - Si recibe List<ParserRuleContext>, devuelve una List<...> con el valor del atributo "ast"
    //   de cada Context (no es una List<AST>, si no una List del tipo final del atributo).
    //
    // Es el equivalente a getStringsFromTokens pero para no-Terminales.
    @SuppressWarnings("unchecked")
    protected <T> List<T> getNodesFromContexts(Object definiciones) {
        if (definiciones == null)
            return null;

        if (!(definiciones instanceof List))
            throw new IllegalArgumentException("El argumento debería ser una `List`");

        List<T> result = new ArrayList<T>();
        for (Object element : (List<?>) definiciones)
            result.add((T) getAST(element));
        return result;
    }

    // Recibe una List<String> o List<Token> y devuelve una List<String>.
    // - Si recibe List<String>, no hace nada y devuelve los mismos elementos.
    // - Si recibe List<Token>, devuelve una List<String> con el getText() de cada Token.
    //
    // Es el equivalente a getNodesFromContexts pero para Terminales.
    protected List<String> getStringsFromTokens(Object objectList) {
        if (objectList == null)
            return null;

        if (!(objectList instanceof List))
            throw new IllegalArgumentException("El argumento debería ser una `List`");

        List<String> strings = new ArrayList<String>();
        for (Object o : (List<?>) objectList)
            strings.add((o instanceof Token) ? ((Token) o).getText() : (String) o);
        return strings;
    }

    // -----------------------------------------------
    // # Métodos privados para ser llamados SÓLO desde los métodos anteriores.

    private Position findStart(Object node) {
        if (node instanceof AST)
            return ((AST) node).getStart();
        if (node instanceof ParserRuleContext)
            return findStart(getAST(node));
        if (node instanceof List)
            return findStart((List<?>) node);
        if (node instanceof Token) {
            Token token = (Token) node;
            return new Position(token.getLine(), token.getCharPositionInLine() + 1);
        }
        return null;
    }

    private Position findStart(List<?> nodes) {
        Position start = this.start;
        for (Object node : nodes) {
            Position nodeStart = findStart(node);
            if (start == null)
                start = nodeStart;
            else // No suponer que los hijos se han pasado ordenados de izquierda a derecha
                start = (nodeStart != null && nodeStart.lessThan(start)) ? nodeStart : start;
        }
        return start;
    }

    private Position findEnd(Object node) {
        if (node instanceof AST)
            return ((AST) node).getEnd();
        if (node instanceof ParserRuleContext)
            return findEnd(getAST(node));
        if (node instanceof List)
            return findEnd((List<?>) node);
        if (node instanceof Token) {
            Token token = (Token) node;
            return new Position(token.getLine(), token.getCharPositionInLine() + token.getText().length());
        }
        return null;
    }

    private Position findEnd(List<?> nodes) {
        Position end = this.end;
        for (Object node : nodes) {
            Position nodeEnd = findEnd(node);
            if (end == null)
                end = nodeEnd;
            else // No suponer que los hijos se han pasado ordenados de izquierda a derecha
                end = (nodeEnd != null && nodeEnd.greaterThan(end)) ? nodeEnd : end;
        }
        return end;
    }

    // -----------------------------------------------
    // Para depuración. Quitar si se quiere.
    private void invariant() {
        if ((getStart() != null || getEnd() != null) && getStart().greaterThan(getEnd()))
            throw new IllegalStateException(
                    "Las posiciones del nodo (start y end) son inválidas: o son ambas null o (start <= end)");
    }

    private Position start, end;
}
