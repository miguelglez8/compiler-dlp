package main;

import java.io.IOException;

import org.antlr.v4.runtime.*;

import parser.*;

public class Main {

    public static void main(String[] args) {

        try {
            Lexicon lexer = new Lexicon(CharStreams.fromFileName("source.txt"));
            RecursiveParser parser = new RecursiveParser(lexer);

            parser.prog();

            System.out.println(">> La entrada pertenece al lenguaje");

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (IOException e1) {
            System.out.println("Fichero no encontrado");
        }
    }
}
