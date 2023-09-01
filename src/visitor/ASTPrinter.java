/**
 * @generated VGen (for ANTLR) 1.7.4
 */

package visitor;

import java.io.*;

import ast.*;
import java.util.*;

/**
 * ASTPrinter. Utilidad que ayuda a validar un arbol AST:
 * - Muestra la estructura del árbol en HTML.
 * - Destaca los hijos/propiedades a null.
 * - Muestra a qué texto apuntan las posiciones de cada nodo (linea/columna)
 *      ayudando a decidir cual de ellas usar en los errores y generación de código.
 *
 * Esta clase se genera con VGen. El uso de esta clase es opcional (puede eliminarse del proyecto).
 *
 */
public class ASTPrinter extends DefaultVisitor {

    /**
     * toHtml. Muestra la estructura del AST indicando qué hay en las posiciones
     * (línea y columna) de cada nodo.
     *
     * @param sourceFile El fichero del cual se ha obtenido el AST
     * @param raiz       El AST creado a partir de sourceFile
     * @param filename   Nombre del fichero HMTL a crear con la traza del AST
     */

    public static void toHtml(String sourceFile, AST raiz, String filename) {
        toHtml(sourceFile, raiz, filename, 4);
    }

    public static void toHtml(AST raiz, String filename) {
        toHtml(null, raiz, filename);
    }

    // tabWidth deberían ser los espacios correspondientes a un tabulador en eclipse.
    // Normalmente no sería necesario especificarlo. Usar mejor los dos métodos anteriores.

    public static void toHtml(String sourceFile, AST raiz, String filename, int tabWidth) {
        try {
            PrintWriter writer = new PrintWriter(
                    new FileWriter(filename.endsWith(".html") ? filename : filename + ".html"));
            generateHeader(writer);
            writer.println("[ASTPrinter] -------------------------------- line:col  line:col");
            if (raiz != null) {
                ASTPrinter tracer = new ASTPrinter(writer, loadLines(sourceFile, tabWidth));
                raiz.accept(tracer, Integer.valueOf(0));
            } else
                writer.println("raiz == null");
            writer.println(ls + ls + "[ASTPrinter] --------------------------------");
            generateFooter(writer);
            writer.close();
            System.out.println(ls + "ASTPrinter: Fichero '" + filename
                    + ".html' generado. Abra dicho fichero para validar el AST generado.");
        } catch (IOException e) {
            System.out.println(ls + "ASTPrinter: No se ha podido crear el fichero " + filename);
            e.printStackTrace();
        }
    }

    private static void generateHeader(PrintWriter writer) {
        writer.println("<html>\r\n"
            + "<head>\r\n"
            + "<meta charset=\"utf-8\" />\r\n"
            + "<style type=\"text/css\">\r\n"
            + ".value { font-weight: bold; }\r\n"
            + ".dots { color: #bebdbd; }\r\n"
            + ".type { color: #BBBBBB; }\r\n"
            + ".pos { color: #CCCCCC; }\r\n"
            + ".sourceText { color: #BBBBBB; }\r\n"
            + ".posText {\r\n" + "	color: #BBBBBB;\r\n"
            + "	text-decoration: underline; font-weight: bold;\r\n"
            + "}\r\n"
            + ".null {\r\n"
            + "	color: #FF0000;\r\n"
            + "	font-weight: bold;\r\n"
            + "	font-style: italic;\r\n" + "}\r\n"
            + "</style>\r\n" + "</head>\r\n" + "\r\n"
            + "<body><pre>");
    }

    private static void generateFooter(PrintWriter writer) {
        writer.println("</pre>\r\n" + "</body>\r\n" + "</html>");
    }

    private ASTPrinter(PrintWriter writer, List<String> sourceLines) {
        this.writer = writer;
        this.sourceLines = sourceLines;
    }

    // ----------------------------------------------
	//	class Programa { List<Operacion> operaciones; }
	public Object visit(Programa programa, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Programa", programa, false);

		visit(indent + 1, "operaciones", "List<Operacion>", programa.getOperaciones());
		return null;
	}

	//	class DefVariable { Tipo tipo;  String nombre; }
	public Object visit(DefVariable defVariable, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "DefVariable", defVariable, false);

		visit(indent + 1, "tipo", "Tipo", defVariable.getTipo());
		print(indent + 1, "nombre", "String", defVariable.getNombre());
		return null;
	}

	//	class DefStruct { String nombre;  List<Campo> atributos; }
	public Object visit(DefStruct defStruct, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "DefStruct", defStruct, false);

		print(indent + 1, "nombre", "String", defStruct.getNombre());
		visit(indent + 1, "atributos", "List<Campo>", defStruct.getAtributos());
		return null;
	}

	//	class DefFuncion { String nombre;  List<DefVariable> parametros;  Tipo retorno;  List<DefVariable> variablesLocales;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion defFuncion, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "DefFuncion", defFuncion, false);

		print(indent + 1, "nombre", "String", defFuncion.getNombre());
		visit(indent + 1, "parametros", "List<DefVariable>", defFuncion.getParametros());
		visit(indent + 1, "retorno", "Tipo", defFuncion.getRetorno());
		visit(indent + 1, "variablesLocales", "List<DefVariable>", defFuncion.getVariablesLocales());
		visit(indent + 1, "sentencias", "List<Sentencia>", defFuncion.getSentencias());
		return null;
	}

	//	class Campo { Tipo tipo;  String nombre; }
	public Object visit(Campo campo, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Campo", campo, false);

		visit(indent + 1, "tipo", "Tipo", campo.getTipo());
		print(indent + 1, "nombre", "String", campo.getNombre());
		return null;
	}

	//	class If { Expresion condicionTrue;  List<Sentencia> sentenciasTrue; }
	public Object visit(If _if, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "If", _if, false);

		visit(indent + 1, "condicionTrue", "Expresion", _if.getCondicionTrue());
		visit(indent + 1, "sentenciasTrue", "List<Sentencia>", _if.getSentenciasTrue());
		return null;
	}

	//	class Ifelse { Expresion condicionTrue;  List<Sentencia> sentenciasTrue;  List<Sentencia> sentenciasFalse; }
	public Object visit(Ifelse ifelse, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Ifelse", ifelse, false);

		visit(indent + 1, "condicionTrue", "Expresion", ifelse.getCondicionTrue());
		visit(indent + 1, "sentenciasTrue", "List<Sentencia>", ifelse.getSentenciasTrue());
		visit(indent + 1, "sentenciasFalse", "List<Sentencia>", ifelse.getSentenciasFalse());
		return null;
	}

	//	class While { Expresion condicionWhile;  List<Sentencia> sentencia; }
	public Object visit(While _while, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "While", _while, false);

		visit(indent + 1, "condicionWhile", "Expresion", _while.getCondicionWhile());
		visit(indent + 1, "sentencia", "List<Sentencia>", _while.getSentencia());
		return null;
	}

	//	class For { Sentencia asignacion;  Expresion condicionFor;  Sentencia incremento;  List<Sentencia> sentencia; }
	public Object visit(For _for, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "For", _for, false);

		visit(indent + 1, "asignacion", "Sentencia", _for.getAsignacion());
		visit(indent + 1, "condicionFor", "Expresion", _for.getCondicionFor());
		visit(indent + 1, "incremento", "Sentencia", _for.getIncremento());
		visit(indent + 1, "sentencia", "List<Sentencia>", _for.getSentencia());
		return null;
	}

	//	class DoWhile { Expresion condicionDoWhile;  List<Sentencia> sentencia; }
	public Object visit(DoWhile doWhile, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "DoWhile", doWhile, false);

		visit(indent + 1, "condicionDoWhile", "Expresion", doWhile.getCondicionDoWhile());
		visit(indent + 1, "sentencia", "List<Sentencia>", doWhile.getSentencia());
		return null;
	}

	//	class Switch { Expresion condicion;  List<Case> cases;  List<Sentencia> sentencia; }
	public Object visit(Switch _switch, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Switch", _switch, false);

		visit(indent + 1, "condicion", "Expresion", _switch.getCondicion());
		visit(indent + 1, "cases", "List<Case>", _switch.getCases());
		visit(indent + 1, "sentencia", "List<Sentencia>", _switch.getSentencia());
		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read read, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Read", read, false);

		visit(indent + 1, "expresion", "Expresion", read.getExpresion());
		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print print, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Print", print, false);

		visit(indent + 1, "expresion", "Expresion", print.getExpresion());
		return null;
	}

	//	class Printsp { Expresion expresion; }
	public Object visit(Printsp printsp, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Printsp", printsp, false);

		visit(indent + 1, "expresion", "Expresion", printsp.getExpresion());
		return null;
	}

	//	class Println { Expresion expresion; }
	public Object visit(Println println, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Println", println, false);

		visit(indent + 1, "expresion", "Expresion", println.getExpresion());
		return null;
	}

	//	class Return { Expresion expresion; }
	public Object visit(Return _return, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Return", _return, false);

		visit(indent + 1, "expresion", "Expresion", _return.getExpresion());
		return null;
	}

	//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion asignacion, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Asignacion", asignacion, false);

		visit(indent + 1, "left", "Expresion", asignacion.getLeft());
		visit(indent + 1, "right", "Expresion", asignacion.getRight());
		return null;
	}

	//	class MasIgual { Expresion left;  Expresion right; }
	public Object visit(MasIgual masIgual, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "MasIgual", masIgual, false);

		visit(indent + 1, "left", "Expresion", masIgual.getLeft());
		visit(indent + 1, "right", "Expresion", masIgual.getRight());
		return null;
	}

	//	class OperadorMasMas { Expresion left; }
	public Object visit(OperadorMasMas operadorMasMas, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "OperadorMasMas", operadorMasMas, false);

		visit(indent + 1, "left", "Expresion", operadorMasMas.getLeft());
		return null;
	}

	//	class LlamadaFuncionS { String nombre;  List<Expresion> parametros; }
	public Object visit(LlamadaFuncionS llamadaFuncionS, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "LlamadaFuncionS", llamadaFuncionS, false);

		print(indent + 1, "nombre", "String", llamadaFuncionS.getNombre());
		visit(indent + 1, "parametros", "List<Expresion>", llamadaFuncionS.getParametros());
		return null;
	}

	//	class AsignacionArray { Expresion array;  List<Expresion> parametros; }
	public Object visit(AsignacionArray asignacionArray, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "AsignacionArray", asignacionArray, false);

		visit(indent + 1, "array", "Expresion", asignacionArray.getArray());
		visit(indent + 1, "parametros", "List<Expresion>", asignacionArray.getParametros());
		return null;
	}

	//	class AsignacionE { List<Expresion> list;  Expresion right; }
	public Object visit(AsignacionE asignacionE, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "AsignacionE", asignacionE, false);

		visit(indent + 1, "list", "List<Expresion>", asignacionE.getList());
		visit(indent + 1, "right", "Expresion", asignacionE.getRight());
		return null;
	}

	//	class Case { Expresion condicion;  List<Sentencia> sentencia;  boolean hasBreak; }
	public Object visit(Case _case, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Case", _case, false);

		visit(indent + 1, "condicion", "Expresion", _case.getCondicion());
		visit(indent + 1, "sentencia", "List<Sentencia>", _case.getSentencia());
		print(indent + 1, "hasBreak", "boolean", _case.getHasBreak());
		return null;
	}

	//	class ArithmeticExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(ArithmeticExpresion arithmeticExpresion, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "ArithmeticExpresion", arithmeticExpresion, false);

		visit(indent + 1, "left", "Expresion", arithmeticExpresion.getLeft());
		print(indent + 1, "operator", "String", arithmeticExpresion.getOperator());
		visit(indent + 1, "right", "Expresion", arithmeticExpresion.getRight());
		return null;
	}

	//	class LogicExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(LogicExpresion logicExpresion, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "LogicExpresion", logicExpresion, false);

		visit(indent + 1, "left", "Expresion", logicExpresion.getLeft());
		print(indent + 1, "operator", "String", logicExpresion.getOperator());
		visit(indent + 1, "right", "Expresion", logicExpresion.getRight());
		return null;
	}

	//	class RelacionalExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(RelacionalExpresion relacionalExpresion, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "RelacionalExpresion", relacionalExpresion, false);

		visit(indent + 1, "left", "Expresion", relacionalExpresion.getLeft());
		print(indent + 1, "operator", "String", relacionalExpresion.getOperator());
		visit(indent + 1, "right", "Expresion", relacionalExpresion.getRight());
		return null;
	}

	//	class NotExpresion { Expresion expresion; }
	public Object visit(NotExpresion notExpresion, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "NotExpresion", notExpresion, false);

		visit(indent + 1, "expresion", "Expresion", notExpresion.getExpresion());
		return null;
	}

	//	class AccesoArray { Expresion left;  Expresion right; }
	public Object visit(AccesoArray accesoArray, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "AccesoArray", accesoArray, false);

		visit(indent + 1, "left", "Expresion", accesoArray.getLeft());
		visit(indent + 1, "right", "Expresion", accesoArray.getRight());
		return null;
	}

	//	class AccesoCampo { Expresion expresion;  String campo; }
	public Object visit(AccesoCampo accesoCampo, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "AccesoCampo", accesoCampo, false);

		visit(indent + 1, "expresion", "Expresion", accesoCampo.getExpresion());
		print(indent + 1, "campo", "String", accesoCampo.getCampo());
		return null;
	}

	//	class LlamadaFuncionE { String nombre;  List<Expresion> parametros; }
	public Object visit(LlamadaFuncionE llamadaFuncionE, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "LlamadaFuncionE", llamadaFuncionE, false);

		print(indent + 1, "nombre", "String", llamadaFuncionE.getNombre());
		visit(indent + 1, "parametros", "List<Expresion>", llamadaFuncionE.getParametros());
		return null;
	}

	//	class Cast { int casteo;  Expresion expresion; }
	public Object visit(Cast cast, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Cast", cast, false);

		print(indent + 1, "casteo", "int", cast.getCasteo());
		visit(indent + 1, "expresion", "Expresion", cast.getExpresion());
		return null;
	}

	//	class Variable { String nombre; }
	public Object visit(Variable variable, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "Variable", variable, "nombre", variable.getNombre());
		return null;
	}

	//	class IntConstant { String valor; }
	public Object visit(IntConstant intConstant, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "IntConstant", intConstant, "valor", intConstant.getValor());
		return null;
	}

	//	class RealConstant { String valor; }
	public Object visit(RealConstant realConstant, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "RealConstant", realConstant, "valor", realConstant.getValor());
		return null;
	}

	//	class CharConstant { String valor; }
	public Object visit(CharConstant charConstant, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "CharConstant", charConstant, "valor", charConstant.getValor());
		return null;
	}

	//	class Truee {  }
	public Object visit(Truee truee, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Truee", truee, true);

		return null;
	}

	//	class Falsee {  }
	public Object visit(Falsee falsee, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Falsee", falsee, true);

		return null;
	}

	//	class Ternario { Expresion expr;  Expresion left;  Expresion right; }
	public Object visit(Ternario ternario, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Ternario", ternario, false);

		visit(indent + 1, "expr", "Expresion", ternario.getExpr());
		visit(indent + 1, "left", "Expresion", ternario.getLeft());
		visit(indent + 1, "right", "Expresion", ternario.getRight());
		return null;
	}

	//	class IntType {  }
	public Object visit(IntType intType, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "IntType", intType, true);

		return null;
	}

	//	class FloatType {  }
	public Object visit(FloatType floatType, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "FloatType", floatType, true);

		return null;
	}

	//	class CharType {  }
	public Object visit(CharType charType, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "CharType", charType, true);

		return null;
	}

	//	class BooleanType {  }
	public Object visit(BooleanType booleanType, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "BooleanType", booleanType, true);

		return null;
	}

	//	class VoidType {  }
	public Object visit(VoidType voidType, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "VoidType", voidType, true);

		return null;
	}

	//	class StringType { String nombre; }
	public Object visit(StringType stringType, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "StringType", stringType, "nombre", stringType.getNombre());
		return null;
	}

	//	class VectorType { int tamaño;  Tipo tipo; }
	public Object visit(VectorType vectorType, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "VectorType", vectorType, false);

		print(indent + 1, "tamaño", "int", vectorType.getTamaño());
		visit(indent + 1, "tipo", "Tipo", vectorType.getTipo());
		return null;
	}


	// -----------------------------------------------------------------
	// Métodos invocados desde los métodos visit -----------------------

	private void printName(int indent, String name, AST node, boolean empty) {
		String text = ls + tabula(indent) + name + " &rarr;  ";
		text = String.format("%1$-" + 93 + "s", text);
		if (empty)
			text = text.replace(name, valueTag(name));
		writer.print(text + getPosition(node));
	}

	private void print(int indent, String name, String type, Object value) {
		write(indent, formatValue(value) + "  " + typeTag(type));
	}

	private void print(int indent, String attName, String type, List<? extends Object> children) {
		write(indent, attName + "  " + typeTag(type) + " = ");
		if (children != null)
			for (Object child : children)
				write(indent + 1, formatValue(child));
		else
			writer.print(" " + valueTag(null));
	}

	// Versión compacta de una linea para nodos que solo tienen un atributo String
	private void printCompact(int indent, String nodeName, AST node, String attName, Object value) {
		String fullName = nodeName + '.' + attName;
		String text = ls + tabula(indent) + '\"' + value + "\"  " + fullName;
		text = String.format("%1$-" + 88 + "s", text);
		// text = text.replace(value.toString(), valueTag(value));
		text = text.replace(fullName, typeTag(fullName));
		writer.print(text + getPosition(node));
	}

	private void visit(int indent, String attName, String type, List<? extends AST> children) {
		write(indent, attName + "  " + typeTag(type) + " = ");
		if (children != null)
			for (AST child : children)
				child.accept(this, indent + 1);
		else
			writer.print(" " + valueTag(null));
	}

	private void visit(int indent, String attName, String type, AST child) {
		if (child != null)
			child.accept(this, Integer.valueOf(indent));
		else
			write(indent, valueTag(null) + "  " + attName + ':' + typeTag(type));
	}

	// -----------------------------------------------------------------
	// Métodos auxiliares privados -------------------------------------

	private void write(int indent, String text) {
		writer.print(ls + tabula(indent) + text);
	}

	private static String tabula(int count) {
		StringBuffer cadena = new StringBuffer("<span class=\"dots\">");
		for (int i = 0; i < count; i++)
			cadena.append(i % 2 == 0 && i > 0 ? "|  " : ".  ");
		return cadena.toString() + "</span>";
	}

	private String typeTag(String type) {
		if (type.equals("String"))
			return "";
		return "<span class=\"type\">" + type.replace("<", "&lt;").replace(">", "&gt;") + "</span>";
	}

	private String valueTag(Object value) {
		if (value == null)
			return "<span class=\"null\">null</span>";
		return "<span class=\"value\">" + value + "</span>";
	}

	private String formatValue(Object value) {
		String text = valueTag(value);
		if (value instanceof String)
			text = "\"" + text + '"';
		return text;
	}


	// -----------------------------------------------------------------
	// Métodos para mostrar las Posiciones -----------------------------

	private String getPosition(AST node) {
		String text = node.getStart() + "  " + node.getEnd();
		text = "<span class=\"pos\">" + String.format("%1$-" + 13 + "s", text) + "</span>";
		text = text.replace("null", "<span class=\"null\">null</span>");
		String sourceText = findSourceText(node);
		if (sourceText != null)
			text += sourceText;
		return text;
	}

	private String findSourceText(AST node) {
		if (sourceLines == null)
			return null;

		Position start = node.getStart();
		Position end = node.getEnd();
		if (start == null || end == null)
			return null;

		String afterText, text, beforeText;
		if (start.getLine() == end.getLine()) {
			String line = sourceLines.get(start.getLine() - 1);
			afterText = line.substring(0, start.getColumn() - 1);
			text = line.substring(start.getColumn() - 1, end.getColumn());
			beforeText = line.substring(end.getColumn());
		} else {
			String firstLine = sourceLines.get(start.getLine() - 1);
			String lastLine = sourceLines.get(end.getLine() - 1);

			afterText = firstLine.substring(0, start.getColumn() - 1);

			text = firstLine.substring(start.getColumn() - 1);
			text += "</span><span class=\"sourceText\">" + " ... " + "</span><span class=\"posText\">";
			text += lastLine.substring(0, end.getColumn()).replaceAll("^\\s+", "");

			beforeText = lastLine.substring(end.getColumn());
		}
		return "<span class=\"sourceText\">" + afterText.replaceAll("^\\s+", "")
				+ "</span><span class=\"posText\">" + text
				+ "</span><span class=\"sourceText\">" + beforeText + "</span>";
	}

	private static List<String> loadLines(String sourceFile, int tabWidth) {
		if (sourceFile == null)
			return null;
		try {
			String spaces = new String(new char[tabWidth]).replace("\0", " ");

			List<String> lines = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(sourceFile));
			String line;
			while ((line = br.readLine()) != null) {
			//	lines.add(line.replace("\t", spaces)); // Si tab = 4 espaces (Eclipse)
				lines.add(line);
            }
			br.close();
			return lines;
		} catch (FileNotFoundException e) {
			System.out.println("Warning. No se pudo encontrar el fichero fuente '" + sourceFile + "'. No se mostrará informaicón de posición.");
			return null;
		} catch (IOException e) {
			System.out.println("Warning. Error al leer del fichero fuente '" + sourceFile + "'. No se mostrará informaicón de posición.");
			return null;
		}
	}


	private List<String> sourceLines;
	private static String ls = System.getProperty("line.separator");
	private PrintWriter writer;
}
