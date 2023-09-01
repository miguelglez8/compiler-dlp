/**
  * Tutorial de Diseño de Lenguajes de Programación
 * @author Raúl Izquierdo
 */

package main;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import org.antlr.v4.runtime.*;

import ast.*;
import visitor.*;

import parser.*;
import semantic.*;
import codegeneration.*;

/**
 * Clase que inicia el compilador e invoca a todas sus fases.
 *
 * Normalmente, no será necesario modificar este fichero. En su lugar, modificar
 * los ficheros de cada fase (los cuales son llamados desde aquí):
 * - Para Análisis Léxico: 'Lexico.g4'.
 * - Para Análisis Sintáctico: 'Grammar.g4'.
 * - Para Análisis Semántico: 'Identification.java' y 'TypeChecking.java'.
 * - Para Generación de Código: 'MemoryAllocation.java' y 'CodeSelection.java'.
 */
public class Main {
    public static final String INPUT_FILE = "source.txt"; // Fichero de entrada

    public static final String OUTPUT_FILE = "output.txt"; // Fichero de salida

    public static void main(String[] args) throws Exception {
        ErrorManager errorManager = new ErrorManager();
        AST ast = compile(INPUT_FILE, errorManager); // Sustituir "INPUT_FILE" por args[0] en la versión final
        if (errorManager.errorsCount() == 0)
            System.out.println("El programa se ha compilado correctamente.");

        ASTPrinter.toHtml(INPUT_FILE, ast, "AST"); // Utilidad generada por VGen (opcional)
    }

    /**
     * Método que coordina todas las fases del compilador
     */
    public static AST compile(String sourceName, ErrorManager errorManager) throws Exception {

        // 1. Fases de Análisis Léxico y Sintáctico
        GrammarLexer lexer = new GrammarLexer(CharStreams.fromFileName(sourceName));
        GrammarParser parser = new GrammarParser(new CommonTokenStream(lexer));

        AST ast = null;

        // IMPORTANTE: Cuando se genere el AST, INTERCAMBIAR las dos líneas siguientes:
        // parser.start();
        ast = parser.start().ast;
        
        // OPCIONAL: Para imprimir el contenido del AST
        // PrintVisitor visitor = new PrintVisitor();
        // ast.accept(visitor, null);
        
        if (parser.getNumberOfSyntaxErrors() > 0 || ast == null) { // Hay errores o el AST no se ha implementado aún
            errorManager.notify("El AST no ha sido creado.");
            return null;
        }

        // 2. Fase de Análisis Semántico
        SemanticAnalisys analyzer = new SemanticAnalisys(errorManager);
        analyzer.analize(ast);
        if (errorManager.errorsCount() > 0)
            return ast;

        // 3. Fase de Generación de Código
        File sourceFile = new File(sourceName);
        Writer out = new FileWriter(new File(sourceFile.getParent(), OUTPUT_FILE));

        CodeGeneration generator = new CodeGeneration();
        generator.generate(sourceFile.getName(), ast, out);
        out.close();

        return ast;
    }
}
