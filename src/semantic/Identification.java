package semantic;

import java.util.HashMap;
import java.util.Map;

import ast.AST;
import ast.Ambito;
import ast.Campo;
import ast.DefFuncion;
import ast.DefStruct;
import ast.DefVariable;
import ast.LlamadaFuncionE;
import ast.LlamadaFuncionS;
import ast.Position;
import ast.Sentencia;
import ast.StringType;
import ast.Variable;
import main.ErrorManager;
import visitor.DefaultVisitor;

public class Identification extends DefaultVisitor { 
	
	 public Identification(ErrorManager errorManager) { 
		 this.errorManager = errorManager; 
	 } 
	 
	 //	class DefVariable { Tipo tipo;  String nombre; }
	public Object visit(DefVariable node, Object param) { 
		node.getTipo().accept(this, param);
		
		DefVariable definicion = variables.getFromTop(node.getNombre()); 
		
		predicado(definicion == null, "Variable " + node.getAmbito() + " repetida: " + node.getNombre(), node); 
		
		variables.put(node.getNombre(), node); 
		
		return null;
	} 
			 
	// class Variable { String nombre; }
	public Object visit(Variable node, Object param) { 
		DefVariable definicion = variables.getFromAny(node.getNombre()); 

		predicado(definicion != null, "Variable no definida: " + node.getNombre(), node); 
			 
		node.setDefinicion(definicion); // Enlazar referencia con definici�n
			 
		return null; 
	}

	//	class DefStruct { String nombre;  List<Campo> atributos; }
	public Object visit(DefStruct node, Object param) {
		DefStruct definicion = structs.get(node.getNombre()); 
		predicado(definicion == null, "Estructura repetida: " + node.getNombre(), node);
		structs.put(node.getNombre(), node);
		campos.set();
		for (Campo var : node.getAtributos()) {
			var.accept(this, node.getNombre());
		}
		campos.reset();
		return null; 
	}
	
	//	class StringType { String nombre; }
	public Object visit(StringType node, Object param) { 
		DefStruct definicion = structs.get(node.getNombre()); 

		predicado(definicion != null, "Estructura no definida: " + node.getNombre(), node); 
				 
		node.setDefinicion(definicion); // Enlazar referencia con definici�n
				 
		return null; 
	}
		 
	// class Campo { Tipo tipo;  String nombre; }
	public Object visit(Campo node, Object param) { 
		node.getTipo().accept(this, param);
		
		Campo definicion = campos.getFromTop(node.getNombre()); 
		
		predicado(definicion == null, "Campo repetido: " + node.getNombre(), node); 
		
		campos.put(node.getNombre(), node); 
		
		return null;
	} 
	 
	//	class DefFuncion { String nombre;  List<DefVariable> parametros;  Tipo retorno;  List<DefVariable> variablesLocales;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion node, Object param) { 
		DefFuncion definicion = funciones.get(node.getNombre()); 
		predicado(definicion == null, "Funcion repetida: " + node.getNombre(), node); 
		funciones.put(node.getNombre(), node);
		variables.set();
		for (DefVariable var : node.getParametros()) {
			var.setAmbito(Ambito.PARAMETRO);
			var.accept(this, param);
		}
		if (node.getRetorno() != null)
			node.getRetorno().accept(this, param);
		for (DefVariable var : node.getVariablesLocales()) {
			var.setAmbito(Ambito.LOCAL);
			var.accept(this, param);
		}
		for (Sentencia var : node.getSentencias()) {
			var.accept(this, param);
		}
		variables.reset();
		return null; 
	}
	
	//	class LlamadaFuncionS { String nombre;  List<Expresion> parametros; }
	public Object visit(LlamadaFuncionS node, Object param) {
		super.visit(node, param);

		DefFuncion definicion = funciones.get(node.getNombre()); 

		predicado(definicion != null, "Procedimiento no definido: " + node.getNombre(), node); 
		 
		node.setDefinicion(definicion); // Enlazar referencia con definici�n
		 
		return null;
	}
	
	//	class LlamadaFuncionE { String nombre;  List<Expresion> parametros; }
	public Object visit(LlamadaFuncionE node, Object param) {
		super.visit(node, param);

		DefFuncion definicion = funciones.get(node.getNombre()); 

		predicado(definicion != null, "Funcion no definida: " + node.getNombre(), node); 
		 
		node.setDefinicion(definicion); // Enlazar referencia con definici�n
		 
		return null;
	}
	
	 // # --------------------------------------------------------
	 // Metodos auxiliares recomendados (opcionales) -------------
	 private void error(String msg, Position position) { 
		 errorManager.notify("Identification", msg, position); 
	 } 
	 
	 private void predicado(boolean condition, String errorMessage, AST node) { 
		 if (!condition) 
			 error(errorMessage, node.getStart()); 
	 } 
	 
	 private ErrorManager errorManager; 
	 
	 ContextMap<String, DefVariable> variables = new ContextMap<String, DefVariable>();
	 private Map<String, DefFuncion> funciones = new HashMap<String, DefFuncion>(); 
	 private Map<String, DefStruct> structs = new HashMap<String, DefStruct>(); 
	 ContextMap<String, Campo> campos = new ContextMap<String, Campo>(); 
}