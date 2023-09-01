/**
 * Tutorial de Diseño de Lenguajes de Programación
 * @author Raúl Izquierdo
 */

package codegeneration;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.*;
import visitor.*;

public class CodeSelection extends DefaultVisitor {
	
	private Map<String, String> instruccion = new HashMap<String, String>();
	private int counterIfElse = 1;
	private int contadorWhile = 1;
	private int[] tamañosFuncion = {0, 0, 0}; // ret, local, params [tamaños] : se utiliza para el return
	private int contadorFor = 1;
	private int contadorDoWhile = 1;
	private int contadorSwitch = 1;
	private int contadorCases = 1;
	private int counterTernario = 1;
	
	enum CodeFunction {
		VALUE, ADDRESS
	}

    public CodeSelection(Writer writer, String sourceFile) {
        this.writer = new PrintWriter(writer);
        this.sourceFile = sourceFile;
        // arithmetic
        instruccion.put("+", "add"); 
        instruccion.put("-", "sub"); 
        instruccion.put("*", "mul"); 
        instruccion.put("/", "div"); 
        instruccion.put("%", "mod");
        // relacional
        instruccion.put(">", "gt"); 
        instruccion.put(">=", "ge");
        instruccion.put("<", "lt"); 
        instruccion.put("<=", "le");
        instruccion.put("==", "eq"); 
        instruccion.put("!=", "ne");
        // logic
        instruccion.put("&&", "and"); 
        instruccion.put("||", "or");
    }

    //	class Programa { List<Operacion> operaciones; }
	public Object visit(Programa node, Object param) {
		out("#source \"" + sourceFile + "\"\n");
		
		// recorremos primero las variables globales y los structs
		if (node.getOperaciones() != null) {
			for (Operacion child : node.getOperaciones()) {
				if (child instanceof DefVariable || child instanceof DefStruct)
					child.accept(this, param);
			}
		}
		
		out("call main");
		out("halt"); 
		
		// recorremos el resto de instrucciones
		if (node.getOperaciones() != null) {
			for (Operacion child : node.getOperaciones()) {
				if (!(child instanceof DefVariable || child instanceof DefStruct))
					child.accept(this, param);
			}
		}
		
		return null;
	}

	//	class DefVariable { Tipo tipo;  String nombre; }
	public Object visit(DefVariable node, Object param) {
		out("#" + node.getAmbito().toString().toLowerCase() + " " + node.getNombre() + ":" + node.getTipo().getMAPLName()); 
		return null;
	}
	
	//	class DefStruct { String nombre;  List<Campo> atributos; }
	public Object visit(DefStruct node, Object param) {
		out("#type " + node.getNombre() + ": {"); 
		for (int i=0; i < node.getAtributos().size(); i++) {
			out("\t" + node.getAtributos().get(i).getNombre() + ":" + node.getAtributos().get(i).getTipo().getMAPLName()); 
		}
		out("}");
		return null;
	}
	
	//	class DefFuncion { String nombre;  List<DefVariable> parametros;  Tipo retorno;  List<DefVariable> variablesLocales;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion node, Object param) {
		int sizeLocales = 0;
		int sizeParametros = 0;

		out(node.getNombre() + ":");
		
		out("#func " + node.getNombre());
		
		if (node.getRetorno() instanceof VoidType) {
			out("#ret void");
		} else {
			out("#ret " + node.getRetorno().getMAPLName());
		}

		for (DefVariable child : node.getParametros()) {
			if (child.getTipo() instanceof VectorType ||
				child.getTipo() instanceof StringType) {
				out("#param " + child.getNombre() + ":" + "address");
				sizeParametros += 2; // apilas la dirección
			} else {
				out("#param " + child.getNombre() + ":" + child.getTipo().getMAPLName());
				sizeParametros += child.getTipo().getSize();
			}
		}

		int i = 0;
		for (DefVariable child : node.getVariablesLocales()) {
			out("#local " + child.getNombre() + ":" + child.getTipo().getMAPLName());
			sizeLocales += child.getTipo().getSize();
			if (i==node.getVariablesLocales().size()-1) {
				line(child);
				out("enter " + sizeLocales);
			}
			i++;
		}

		tamañosFuncion[0] = node.getRetorno() != null ? node.getRetorno().getSize() : 0;
		tamañosFuncion[1] = sizeLocales;
		tamañosFuncion[2] = sizeParametros;

		if (node.getSentencias() != null)
			for (Sentencia child : node.getSentencias()) {
				// podemos pasar por parámetro el tamaño de las locales y params
				child.accept(this, tamañosFuncion);
			}

		if (node.getRetorno() instanceof VoidType) {
			out("\n#line " + (node.getEnd().getLine() + 1));
			out("ret 0, " + sizeLocales + ", " + sizeParametros);
		}

		return null;
	}
	
	//	class If { Expresion condicionTrue;  List<Sentencia> sentenciasTrue; }
	public Object visit(If node, Object param) {
		line(node.getStart()); 
		int contadorIfs = this.counterIfElse;
		this.counterIfElse++;
		node.getCondicionTrue().accept(this, CodeFunction.VALUE);
		
		out("jz finif" + contadorIfs);
		
		for(int i = 0; i < node.getSentenciasTrue().size(); i++){
			node.getSentenciasTrue().get(i).accept(this, param);
		}

		out("finif" + contadorIfs + ":");
		
		return null;
	}

	//	class Ifelse { Expresion condicionTrue;  List<Sentencia> sentenciasTrue;  List<Sentencia> sentenciasFalse; }
	public Object visit(Ifelse node, Object param) {
		line(node.getStart()); 
		int contadorIfs = this.counterIfElse;
		this.counterIfElse++;
		node.getCondicionTrue().accept(this, CodeFunction.VALUE);
		
		if(node.getSentenciasFalse() != null){
			out("jz else" + contadorIfs);
		}
		else{
			out("jz finif" + contadorIfs);
		}
		
		boolean etiqueta = false;
		for(int i = 0; i < node.getSentenciasTrue().size(); i++){
			node.getSentenciasTrue().get(i).accept(this, param);
			if(i == node.getSentenciasTrue().size()-1 && !(node.getSentenciasTrue().get(i) instanceof Return)){
				etiqueta = true;
				out("jmp finif" + contadorIfs);
			}
		}

		if(node.getSentenciasFalse() != null){
			out("else"+contadorIfs+":");
			for (Sentencia child : node.getSentenciasFalse())
				child.accept(this, param);
		}
		
		if (etiqueta)
			out("finif" + contadorIfs + ":");
		
		return null;
	}

	//	class While { Expresion condicionWhile;  List<Sentencia> sentencia; }
	public Object visit(While node, Object param) {
		line(node.getStart()); 
		int contadorWhile = this.contadorWhile;
		this.contadorWhile++;
		out("while" + contadorWhile+":");
		node.getCondicionWhile().accept(this, CodeFunction.VALUE);
		out("jz finwhile" + contadorWhile);

		for (Sentencia child : node.getSentencia())
			child.accept(this, param);
		
		out("jmp while"+contadorWhile);
		
		out("finwhile"+contadorWhile+":");
		return null;
	}
	
	//	class For { Sentencia asignacion;  Expresion condicionFor;  Sentencia incremento;  List<Sentencia> sentencia; }
	public Object visit(For node, Object param) {
		line(node.getStart()); 
		int contadorFor = this.contadorFor;
		this.contadorFor++;
		node.getAsignacion().accept(this, param);
		out("for" + contadorFor +":");
		node.getCondicionFor().accept(this, CodeFunction.VALUE);
		out("jz finfor" + contadorFor);

		for (Sentencia child : node.getSentencia())
			child.accept(this, param);
		
		node.getIncremento().accept(this, param);
		
		out("jmp for"+contadorFor);
		
		out("finfor"+contadorFor+":");
		return null;
	}
	
	//	class DoWhile { Expresion condicionDoWhile;  List<Sentencia> sentencia; }
	public Object visit(DoWhile node, Object param) {
		line(node.getStart()); 
		int contadorDoWhile = this.contadorDoWhile;
		this.contadorDoWhile++;
		out("dowhile" + contadorDoWhile+":");

		for (Sentencia child : node.getSentencia())
			child.accept(this, param);
		
		node.getCondicionDoWhile().accept(this, CodeFunction.VALUE);

		out("jnz dowhile" + contadorDoWhile);
		
		return null;
	}
	
	//	class Switch { Expresion condicion;  List<Case> cases;  List<Sentencia> sentencia; }
	public Object visit(Switch node, Object param) {
		line(node.getStart()); 
		
		int contadorSwitch = this.contadorSwitch;
		this.contadorSwitch++;
		
		int size = node.getCases().size();
		
		node.getCondicion().accept(this, CodeFunction.VALUE);
		if (node.getCondicion().getType() instanceof CharType)
			out("b2i");
		
		Object[] obj = {node.getCondicion(), contadorSwitch, size};
		
		this.contadorCases = 1;
		for (Case child : node.getCases())
			child.accept(this, obj);
		
		out("defaultSwitch" + contadorSwitch + ":");
		for (Sentencia child : node.getSentencia())
			child.accept(this, param);

		out("finSwitch" + contadorSwitch + ":");
		
		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {
		line(node); 
		node.getExpresion().accept(this, CodeFunction.ADDRESS);
		out("in", node.getExpresion().getType());
		out("store", node.getExpresion().getType());
		
		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		line(node); 
		if (node.getExpresion() != null) {
			node.getExpresion().accept(this, CodeFunction.VALUE); 
			out("out", node.getExpresion().getType()); 
		}
		
		return null; 
	}

	//	class Printsp { Expresion expresion; }
	public Object visit(Printsp node, Object param) {
		line(node);
		if (node.getExpresion() != null) {
			node.getExpresion().accept(this, CodeFunction.VALUE); 
			out("out", node.getExpresion().getType());
		}
		out("pushb 32"); 
		out("outb"); 
		return null; 
	}

	//	class Println { Expresion expresion; }
	public Object visit(Println node, Object param) {
		line(node); 
		if (node.getExpresion() != null) {
			node.getExpresion().accept(this, CodeFunction.VALUE); 
			out("out", node.getExpresion().getType());
		} 
		out("pushb 10"); 
		out("outb"); 
		return null; 
	}
	
	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		line(node); 
		
		int[] tamaños = (int[]) param; // tamaños para pasarle al return
		
		if (node.getExpresion() == null)
			out("ret 0, " +  tamaños[1] + ", " +  tamaños[2]);
		else{
			node.getExpresion().accept(this, CodeFunction.VALUE);
			out("ret " + node.getExpresion().getType().getSize() + ", " + tamaños[1] + ", " + tamaños[2]);
		}

		return null;
	}
    
    //	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {
		line(node); 
		node.getLeft().accept(this, CodeFunction.ADDRESS); 
		node.getRight().accept(this, CodeFunction.VALUE); 
		out("store", node.getLeft().getType());
		return null; 
	}
	
	//	class MasIgual { Expresion left;  Expresion right; }
	public Object visit(MasIgual node, Object param) {
		line(node); 
		node.getLeft().accept(this, CodeFunction.ADDRESS); 
		node.getLeft().accept(this, CodeFunction.VALUE); 
		node.getRight().accept(this, CodeFunction.VALUE); 
		out("add", node.getLeft().getType());
		out("store", node.getLeft().getType());
		return null; 
	}
	
	//	class OperadorMasMas { Expresion left; }
	public Object visit(OperadorMasMas node, Object param) {
		line(node); 
		node.getLeft().accept(this, CodeFunction.ADDRESS); 
		node.getLeft().accept(this, CodeFunction.VALUE); 
		out("push" + node.getLeft().getType().getSuffix() + " 1");
		out("add", node.getLeft().getType());
		out("store", node.getLeft().getType());
		return null; 
	}	
	
	//	class LlamadaFuncionS { String nombre;  List<Expresion> parametros; }
	public Object visit(LlamadaFuncionS node, Object param) {
		line(node); 
		
		for(Expresion child: node.getParametros()){
			if (child instanceof Variable) {
				Variable var = (Variable) child;
				if (var.getDefinicion().getTipo() instanceof VectorType ||
					var.getDefinicion().getTipo() instanceof StringType) {
					child.accept(this, CodeFunction.ADDRESS); // apilas la dirección
				} else  {
					child.accept(this, CodeFunction.VALUE);
				}
			} else {
				child.accept(this, CodeFunction.VALUE);
			}
		}
		
		out("call " + node.getNombre());
		
		if(! (node.getDefinicion().getRetorno() instanceof VoidType)){
			out("pop");
		}
		
		return null;
	}
	
	//	class AsignacionArray { Expresion array;  List<Expresion> parametros; }
	public Object visit(AsignacionArray node, Object param) {
		line(node); 
		
		int i = 0;
		for(Expresion child: node.getParametros()){
			child.accept(this, CodeFunction.ADDRESS);
			
			new AccesoArray(node.getArray(), new IntConstant(i + "")).accept(this, CodeFunction.VALUE);
						
			out("store", child.getType());
			
			i++;
		}
		
		return null;
	}
	
	//	class Case { Expresion condicion;  List<Sentencia> sentencia;  boolean hasBreak; }
	public Object visit(Case node, Object param) {
		line(node); 
		
		Object[] obj = (Object[]) param;
		
		int contadorCases = this.contadorCases;
		this.contadorCases++;
		int contadorSwitch = (int) obj[1];
		int size = (int) obj[2];
		
		if (contadorCases != 1)
			out("caseSwitch" + contadorSwitch + "" + contadorCases + "" + ":");
		
		node.getCondicion().accept(this, CodeFunction.VALUE);
		if (node.getCondicion().getType() instanceof CharType)
			out("b2i");
		if (contadorCases != 1) {
			((Expresion) obj[0]).accept(this, CodeFunction.VALUE);
			if (node.getCondicion().getType() instanceof CharType)
				out("b2i");
		}
		out("eq");
		
		if (contadorCases < size)
			out("jz caseSwitch" + contadorSwitch + "" + (contadorCases+1) + "");
		else 
			out("jz defaultSwitch" + contadorSwitch);
			
		for (Sentencia child : node.getSentencia())
			child.accept(this, param);
		
		if (node.getHasBreak())
			out("jmp finSwitch" + contadorSwitch);
		
		return null;
	}
    
    //	class ArithmeticExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(ArithmeticExpresion node, Object param) {
		assert (param == CodeFunction.VALUE); 
		node.getLeft().accept(this, CodeFunction.VALUE); 
		node.getRight().accept(this, CodeFunction.VALUE); 
		out(instruccion.get(node.getOperator()), node.getType()); 
		return null;
	}
	
	//	class LogicExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(LogicExpresion node, Object param) {
		assert (param == CodeFunction.VALUE);
		if (node.getOperator().equals("**")) {
			node.getLeft().accept(this, CodeFunction.VALUE);
			out("not");
			node.getRight().accept(this, CodeFunction.VALUE);
			out("and");

			node.getLeft().accept(this, CodeFunction.VALUE);
			node.getRight().accept(this, CodeFunction.VALUE);
			out("not");
			out("and");

			out("or");
		} else {
			node.getLeft().accept(this, CodeFunction.VALUE); 
			node.getRight().accept(this, CodeFunction.VALUE); 
			out(instruccion.get(node.getOperator()));
		}
		
		return null;
	}

	//	class RelacionalExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(RelacionalExpresion node, Object param) {
		assert (param == CodeFunction.VALUE); 
		node.getLeft().accept(this, CodeFunction.VALUE); 
		node.getRight().accept(this, CodeFunction.VALUE); 
		out(instruccion.get(node.getOperator()), node.getLeft().getType()); 
		return null;
	}

	//	class NotExpresion { Expresion expresion; }
	public Object visit(NotExpresion node, Object param) {
		assert (param == CodeFunction.VALUE); 
		node.getExpresion().accept(this, CodeFunction.VALUE); 
		out("not"); 
		return null;
	}
	
	//	class AccesoArray { Expresion left;  Expresion right; }
	public Object visit(AccesoArray node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, CodeFunction.ADDRESS);
		
		if (node.getRight() != null){
			node.getRight().accept(this, CodeFunction.VALUE);
		}
		
		out("push " + ((VectorType)(node.getLeft().getType())).getTipo().getSize());
		
		out("mul");
		out("add");
		
		if(CodeFunction.VALUE.equals(param)){
			out("load", ((VectorType)node.getLeft().getType()).getTipo());
		}
		
		return null;
	}

	//	class AccesoCampo { Expresion expresion;  String campo; }
	public Object visit(AccesoCampo node, Object param) {
		node.getExpresion().accept(this, CodeFunction.ADDRESS);
		
		List<Campo> atributos = ((StringType)(node.getExpresion()).getType()).getDefinicion().getAtributos();
		
		Tipo tipo = null;
		for(Campo campo: atributos){
			if(campo.getNombre().equals(node.getCampo())){
				out("push " + campo.getAddress());
				tipo = campo.getTipo();
			}
		}
		
		out("add");
		
		if(CodeFunction.VALUE.equals(param)){
			out("load", tipo);
		}
		
		return null;
	}

	//	class LlamadaFuncionE { String nombre;  List<Expresion> parametros; }
	public Object visit(LlamadaFuncionE node, Object param) {
		for(Expresion child: node.getParametros()){
			if (child instanceof Variable) {
				Variable var = (Variable) child;
				if (var.getDefinicion().getTipo() instanceof VectorType ||
					var.getDefinicion().getTipo() instanceof StringType) {
					child.accept(this, CodeFunction.ADDRESS);
				} else  {
					child.accept(this, CodeFunction.VALUE);
				}
			} else {
				child.accept(this, CodeFunction.VALUE);
			}
		}
		
		out("call " + node.getNombre());
		
		return null;
	}
	
	//	class Cast { Tipo casteo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, CodeFunction.VALUE);
		
		if(node.getCasteo() instanceof IntType && node.getExpresion().getType() instanceof CharType){
			out("b2i");
		}
		else if(node.getCasteo() instanceof IntType && node.getExpresion().getType() instanceof FloatType){
			out("f2i");
		}
		else if(node.getCasteo() instanceof CharType && node.getExpresion().getType() instanceof IntType){
			out("i2b");
		}
		else if(node.getCasteo() instanceof FloatType && node.getExpresion().getType() instanceof IntType){
			out("i2f");
		}
		else if(node.getCasteo() instanceof FloatType && node.getExpresion().getType() instanceof CharType){
			out("b2i");
			out("i2f");
		}
		else if(node.getCasteo() instanceof CharType && node.getExpresion().getType() instanceof FloatType){
			out("f2i");
			out("i2b");
		}
		else if(node.getCasteo() instanceof FloatType && node.getExpresion().getType() instanceof BooleanType){
			out("i2f");
		}
		else if(node.getCasteo() instanceof CharType && node.getExpresion().getType() instanceof BooleanType){
			out("i2b");
		}
		else if(node.getCasteo() instanceof BooleanType && node.getExpresion().getType() instanceof CharType){
			out("b2i");
		}
		else if(node.getCasteo() instanceof BooleanType && node.getExpresion().getType() instanceof FloatType){
			out("f2i");
		}
		
		return null;
	}
    
    // class Variable { String nombre; }
    public Object visit(Variable node, Object param) { 
    	if (((CodeFunction) param) == CodeFunction.VALUE) { 
    		 visit(node, CodeFunction.ADDRESS); 
    		 out("load", node.getType()); 
    	} else { // Funcion.DIRECCION
    		 assert (param == CodeFunction.ADDRESS); 
    		 if(! node.getDefinicion().getAmbito().equals(Ambito.GLOBAL)){
    			// LOCAL o PARAMETRO
    			out("pusha bp");
    			out("push " + node.getDefinicion().getAddress());
    			out("add");
    			if (node.getDefinicion().getAmbito().equals(Ambito.PARAMETRO) &&
    				(node.getDefinicion().getTipo() instanceof VectorType ||
    				node.getDefinicion().getTipo() instanceof StringType)) {
    				out("load");
    			} 
 			} else { // GLOBAL
 				out("pusha " + node.getDefinicion().getAddress());
 			} 
    	} 
    	return null; 
    } 
    
    // class IntConstant { String valor; }
    public Object visit(IntConstant node, Object param) { 
    	assert (param == CodeFunction.VALUE); 
    	out("push " + node.getValor()); 
	    
	    return null;  
    } 
    
    // class RealConstant { String valor; }
    public Object visit(RealConstant node, Object param) { 
    	assert (param == CodeFunction.VALUE); 
    	out("pushf " + node.getValor()); 
	    
	    return null; 
    }
    
    // class CharConstant { String valor; }
    public Object visit(CharConstant node, Object param) { 
    	assert (param == CodeFunction.VALUE); 
    	
    	if("'\\n'".equals(node.getValor())){
    		out("pushb 10");
		} else{
			out("pushb " + (int)(node.getValor().charAt(1)));
		}
	    
	    return null; 
    }
    
    //	class Truee {  }
	public Object visit(Truee node, Object param) {
		assert (param == CodeFunction.VALUE); 
    	out("pushi 1");
	    
	    return null; 
	}

	//	class Falsee {  }
	public Object visit(Falsee node, Object param) {
		assert (param == CodeFunction.VALUE); 
    	out("pushi 0");

	    return null;  
	}
	
	//	class AsignacionE { List<Expresion> list;  Expresion right; }
	public Object visit(AsignacionE node, Object param) {
		line(node); 
		
		for(Expresion child: node.getList()){
			child.accept(this, CodeFunction.ADDRESS); 
			node.getRight().accept(this, CodeFunction.VALUE); 
			out("store", node.getRight().getType());
		}
		
		return null; 		
	}
	
	//	class Ternario { Expresion expr;  Expresion left;  Expresion right; }
	public Object visit(Ternario node, Object param) {
		assert (param == CodeFunction.VALUE);
		
		int counterTernario = this.counterTernario ;
		this.counterTernario++;
		node.getExpr().accept(this, CodeFunction.VALUE);
		
		out("jz falsoTernario" + counterTernario);
		
		node.getLeft().accept(this, CodeFunction.VALUE);

		out("jmp finTernario" + counterTernario);
		
		out("falsoTernario"+counterTernario+":");
		node.getRight().accept(this, CodeFunction.VALUE);
	
		out("finTernario" + counterTernario + ":");
		
		return null; 	
	}

    // # ----------------------------------------------------------
    // Metodos auxiliares recomendados (opcionales) -------------

    // Imprime una linea en el fichero de salida
     private void out(String instruction) {
        writer.println(instruction);
    }
     
    private void out(String instruccion, Tipo tipo) { 
    	out(instruccion + tipo.getSuffix()); 
    } 
    
    private void line(AST node) {
        line(node.getEnd());
    }

    private void line(Position pos) {
        if (pos != null)
            out("\n#line " + pos.getLine());
        else
            System.out.println("#line no generado. Se ha pasado una Position null. Falta informacion en el AST");
    }

    private PrintWriter writer;
    private String sourceFile;
}
