package parser;

import org.antlr.v4.runtime.*;

public class RecursiveParser {

    private Lexicon lex;

    public RecursiveParser(Lexicon lexico) throws ParseException {
        lex = lexico;
        advance();
    }

    // ------------------------------------------------------------------------
    // ------ Miembros auxiliares para todo analizador Recursivo Descendente
    private Token token;

    private void advance() {
        token = lex.nextToken();
    }

    void match(int tokenType) throws ParseException {
        if (token.getType() == tokenType)
            advance();
        else
            error();
    }

    private void error() throws ParseException {
        throw new ParseException("Error sintáctico en " + token.getLine() + ":" + token.getCharPositionInLine()
                + ". Lexema = " + token.getText());
    }

    // # ------------------------------------------------------------------------
    // # Aquí comienza el Analizador Sintáctico
    // #

    public void prog() throws ParseException {
        decl();
        decl();
        match(Lexicon.EOF);

    }

    void decl() throws ParseException {
        if (token.getType() == Lexicon.VAR)
            variables();
        else if (token.getType() == Lexicon.TYPE)
            typedef();
        else
            error();
    }

    void variables() throws ParseException {
        match(Lexicon.VAR);
        match(Lexicon.IDENT);
        restoIdents();
    }

    void restoIdents() throws ParseException {
        if (token.getType() == Lexicon.COMA) {
            match(Lexicon.COMA);
            match(Lexicon.IDENT);
            restoIdents();
        } else if (token.getType() == Lexicon.PUNTOS) {
            match(Lexicon.PUNTOS);
            tipo();
        } else
            error();
    }

    void tipo() throws ParseException {
        if (token.getType() == Lexicon.INT)
            match(Lexicon.INT);
        else if (token.getType() == Lexicon.FLOAT)
            match(Lexicon.FLOAT);
        else
            error();
    }

    void typedef() throws ParseException{
        match(Lexicon.TYPE);
        match(Lexicon.IDENT);
        restoIdents();
    }

}
