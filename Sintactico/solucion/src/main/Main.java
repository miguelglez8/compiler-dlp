package main;

import org.antlr.v4.runtime.*;

import parser.*;

public class Main {

    public static void main(String[] args) throws Exception {
        // GrammarLexer lexer = new GrammarLexer(CharStreams.fromFileName("source.txt"));
        GrammarLexer lexer = new GrammarLexer(CharStreams.fromFileName("test.txt"));

        GrammarParser parser = new GrammarParser(new CommonTokenStream(lexer));

        parser.start();

        System.out.println("Compilación Finalizada");
    }
}
