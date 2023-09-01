/**
 * Tutorial de Dise�o de Lenguajes de Programacion
 * @author Raul Izquierdo
 */

package semantic;

import ast.*;
import main.*;
import visitor.*;

public class TypeChecking extends DefaultVisitor {

    public TypeChecking(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }
    
    //	class DefFuncion { String nombre;  List<DefVariable> parametros;  Tipo retorno;  List<DefVariable> variablesLocales;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion node, Object param) {
		if (node.getParametros() != null)
			for (DefVariable child : node.getParametros()){
				child.accept(this, param);
//				predicado(simple(child.getTipo()), "Los parametros de la funcion deben ser de tipo simple", node.getStart());
			}
		 
		if (node.getVariablesLocales() != null)
			for (DefVariable child : node.getVariablesLocales())
				child.accept(this, param);

		if (node.getSentencias() != null)
			for (Sentencia child : node.getSentencias()){
				child.accept(this, node.getRetorno()); // le pasamos el tipo de retorno
			}

		if(node.getRetorno() != null) {
			node.getRetorno().accept(this, param);
			predicado(simple(node.getRetorno()), "El tipo de retorno de la funcion debe ser simple", node.getStart());
		}
		
	    return null;
	}
	
	//	class If { Expresion condicionTrue;  List<Sentencia> sentenciasTrue; }
	public Object visit(If node, Object param) {
		if (node.getCondicionTrue() != null)
			node.getCondicionTrue().accept(this, param);

		if (node.getSentenciasTrue() != null)
			for (Sentencia child : node.getSentenciasTrue()){
				child.accept(this, param);
			}
		
		predicado(node.getCondicionTrue().getType() instanceof IntType ||
				node.getCondicionTrue().getType() instanceof BooleanType,
				"La condicion del if tiene que ser de tipo int o boolean", node.getStart());

		return null;
	}

	//	class Ifelse { Expresion condicionTrue;  List<Sentencia> sentenciasTrue;  List<Sentencia> sentenciasFalse; }
	public Object visit(Ifelse node, Object param) {
		if (node.getCondicionTrue() != null)
			node.getCondicionTrue().accept(this, param);

		if (node.getSentenciasTrue() != null)
			for (Sentencia child : node.getSentenciasTrue()){
				child.accept(this, param);
			}
		
		if (node.getSentenciasFalse() != null)
			for (Sentencia child : node.getSentenciasFalse()){
				child.accept(this, param);
			}
		
		predicado(node.getCondicionTrue().getType() instanceof IntType ||
				node.getCondicionTrue().getType() instanceof BooleanType,
				"La condicion del if tiene que ser de tipo int o boolean", node.getStart());
		
		return null;
	}

	//	class While { Expresion condicionWhile;  List<Sentencia> sentencia; }
	public Object visit(While node, Object param) {
		if (node.getCondicionWhile() != null)
			node.getCondicionWhile().accept(this, param);

		if (node.getSentencia() != null)
			for (Sentencia child : node.getSentencia()){
				child.accept(this, param);
			}
		
		predicado(node.getCondicionWhile().getType() instanceof IntType ||
				node.getCondicionWhile().getType() instanceof BooleanType,
				"La condicion del while tiene que ser de tipo int o boolean", node.getStart());
		
		return null;
	}
	
	//	class For { Sentencia asignacion;  Expresion condicionFor;  Sentencia incremento;  List<Sentencia> sentencia; }
	public Object visit(For node, Object param) {
		super.visit(node, param);

		predicado(node.getAsignacion() instanceof Asignacion,
				"La primera sentencia del for debe ser una asignacion", node.getStart());
		predicado(node.getCondicionFor().getType() instanceof IntType ||
				node.getCondicionFor().getType() instanceof BooleanType,
				"La condicion del for tiene que ser de tipo int o boolean", node.getStart());
		
		return null;
	}
	
	//	class DoWhile { Expresion condicionDoWhile;  List<Sentencia> sentencia; }
	public Object visit(DoWhile node, Object param) {
		if (node.getCondicionDoWhile() != null)
			node.getCondicionDoWhile().accept(this, param);

		if (node.getSentencia() != null)
			for (Sentencia child : node.getSentencia()){
				child.accept(this, param);
			}
		
		predicado(node.getCondicionDoWhile().getType() instanceof IntType ||
				node.getCondicionDoWhile().getType() instanceof BooleanType,
				"La condicion del doWhile tiene que ser de tipo int o boolean", node.getStart());
		
		return null;
	}
	
	//	class Switch { Expresion condicion;  List<Case> cases;  List<Sentencia> sentencia; }
	public Object visit(Switch node, Object param) {
		super.visit(node, node.getCondicion());

		predicado(simple(node.getCondicion().getType()), "La expresion del switch debe ser un tipo simple", node.getStart());
		predicado(node.getCondicion().isModificable(), "La expresion del switch debe ser modificable", node.getStart());

		return null;
	}

	
	//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {
		super.visit(node, param);

		predicado(simple(node.getExpresion().getType()), "La expresion del read debe ser un tipo simple", node.getStart());
		predicado(!(node.getExpresion().getType() instanceof BooleanType), "La expresion del read no puede ser boolean", node.getStart());
		predicado(node.getExpresion().isModificable(), "La expresion debe ser modificable para read", node.getStart());

		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		super.visit(node, param);
		
		if (node.getExpresion() instanceof Variable) {
			Variable variable = (Variable) node.getExpresion();
			predicado(simple(variable.getDefinicion().getTipo()), "La expresion a imprimir debe ser un tipo simple", node.getStart());
		}
		
		if (node.getExpresion() instanceof LlamadaFuncionE) {
			predicado(node.getExpresion().getType().getClass() != VoidType.class, "La funcion que se invoca en el print debe tener un retorno", node.getStart());
		} 
				
		return null;
	}

	//	class Printsp { Expresion expresion; }
	public Object visit(Printsp node, Object param) {
		super.visit(node, param);

		if (node.getExpresion() instanceof Variable) {
			Variable variable = (Variable) node.getExpresion();
			predicado(simple(variable.getDefinicion().getTipo()), "La expresion a imprimir debe ser un tipo simple", node.getStart());
		}
		
		if (node.getExpresion() instanceof LlamadaFuncionE) {
			predicado(node.getExpresion().getType().getClass() != VoidType.class, "La funcion que se invoca en el print debe tener un retorno", node.getStart());
		} 
				
		return null;
	}

	//	class Println { Expresion expresion; }
	public Object visit(Println node, Object param) {
		super.visit(node, param);

		if (node.getExpresion() instanceof Variable) {
			Variable variable = (Variable) node.getExpresion();
			predicado(simple(variable.getDefinicion().getTipo()), "La expresion a imprimir debe ser un tipo simple", node.getStart());
		}
		
		if (node.getExpresion() instanceof LlamadaFuncionE) {
			predicado(node.getExpresion().getType().getClass() != VoidType.class, "La funcion que se invoca en el print debe tener un retorno", node.getStart());
		} 
				
		return null;
	}
	
	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		super.visit(node, param);
		
		if(node.getExpresion() == null) {
			predicado(param.getClass() == VoidType.class, "La funcion debe tener un tipo de retorno " + param, node.getStart());
		} else {
			if (param.getClass() == VoidType.class) {
				predicado(param.getClass() != VoidType.class,
						"El return no debe tener expresion en funciones void", node.getStart());			
			} else {
				predicado(param.getClass() == node.getExpresion().getType().getClass(),
						"La funcion return " + node.getExpresion().getType() + ", no tiene el mismo retorno que la funcion "
						+ param, node.getStart());
			}
		}
		
		return null;
	}
	
    //	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {
		super.visit(node, param); 
		
	    predicado(simple(node.getLeft().getType()), "El tipo de la izquierda de la asignacion debe de ser de tipo simple", node.getStart()); 
	    predicado(mismoTipo(node.getLeft(), node.getRight()), "Los operandos de la asignacion deben ser del mismo tipo", node.getStart()); 
	    predicado(node.getLeft().isModificable(), "Se requiere expresion modificable en la izquierda de la asignacion", node.getLeft()); 
	    
	    return null; 
	}
	
	//	class MasIgual { Expresion left;  Expresion right; }
	public Object visit(MasIgual node, Object param) {
		super.visit(node, param);
		
		predicado(!(node.getLeft().getType() instanceof BooleanType) && !(node.getRight().getType() instanceof BooleanType), 
				"No se puede operar con boolean en una operacion +=", node.getStart());
		
		predicado(!(node.getLeft().getType() instanceof CharType) && !(node.getRight().getType() instanceof CharType), 
					"No se puede operar con char en una operacion +=", node.getStart());
		
		predicado(simple(node.getLeft().getType()), "El tipo de la izquierda de la asignacion debe de ser de tipo simple", node.getStart()); 
	    predicado(mismoTipo(node.getLeft(), node.getRight()), "Los operandos de la asignacion deben ser del mismo tipo", node.getStart()); 
	    predicado(node.getLeft().isModificable(), "Se requiere expresion modificable en la izquierda de la asignacion", node.getLeft()); 
	    
		return null;
	}
	
	//	class OperadorMasMas { Expresion left; }
	public Object visit(OperadorMasMas node, Object param) {
		super.visit(node, param);

		predicado(node.getLeft().getType() instanceof IntType || node.getLeft().getType() instanceof FloatType, "El tipo de la expresion debe de ser int o float", node.getStart()); 
	    predicado(node.getLeft().isModificable(), "Se requiere expresion modificable para incrementar la variable", node.getLeft()); 
		
		return null;
	}
	
	//	class LlamadaFuncionS { String nombre;  List<Expresion> parametros; }
	public Object visit(LlamadaFuncionS node, Object param) {
		super.visit(node, param);
		
		predicado(node.getDefinicion().getParametros().size() == node.getParametros().size(),
				"Debe coincidir el numero de argumentos de la funcion con los parametros (parametros=" + node.getDefinicion().getParametros().size() +
				", argumentos=" + node.getParametros().size() + ")", node.getStart());
		
		if(node.getDefinicion().getParametros().size() == node.getParametros().size()){
			for(int i = 0; i < node.getParametros().size(); i++){
				predicado(node.getDefinicion().getParametros().get(i).getTipo().getClass() == 
						  node.getParametros().get(i).getType().getClass(),
						"El tipo del parametro de la funcion definida " + i + " " + node.getDefinicion().getParametros().get(i).getTipo() + " no coincide con el "
								+ "argumento recibido en la invocacion " + node.getParametros().get(i).getType(), node.getStart());
			}
		}
		
		return null;
	}
	
	//	class AsignacionArray { Expresion array;  List<Expresion> parametros; }
	public Object visit(AsignacionArray node, Object param) {
		super.visit(node, param);
		
		predicado(node.getArray().getType() instanceof VectorType,
				"El operando de la derecha debe de ser de tipo array", node.getStart());
		
		if (node.getArray().getType() instanceof VectorType) {
			VectorType array = (VectorType) node.getArray().getType();
			
			predicado(node.getParametros().size() == array.getTamaño(),
					"Debe coincidir el numero de variables con el tamaño del array", node.getStart());
			
			if(node.getParametros().size() == array.getTamaño()){
				for(int i = 0; i < node.getParametros().size(); i++){
					predicado(node.getParametros().get(i) instanceof Variable, "Se debe de recibir una variable en la posición " + (i+1), node.getStart());
					predicado(node.getParametros().get(i).getType().getClass() == 
							  array.getTipo().getClass(),
							"El tipo de la variable definida " + i + " " + node.getParametros().get(i).getType() + " no coincide con el "
									+ "tipo del array " + array.getTipo(), node.getStart());
				}
			}
		}
		
		
		
		return null;
	}
	
	//	class Case { Expresion condicion;  List<Sentencia> sentencia;  boolean hasBreak; }
	public Object visit(Case node, Object param) {
		super.visit(node, param);

		predicado(simple(node.getCondicion().getType()), "La expresion del case debe ser un tipo simple", node.getStart());
	    predicado(mismoTipo(node.getCondicion(), (Expresion) param), "La condición de cada case debe ser del mismo tipo que el switch", node.getStart()); 

		return null;
	}
	
	//	class ArithmeticExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(ArithmeticExpresion node, Object param) {
		super.visit(node, param);
		
		predicado(!(node.getLeft().getType() instanceof BooleanType) && !(node.getRight().getType() instanceof BooleanType), 
				"No se puede operar con boolean en una operacion aritmetica", node.getStart());
		
		predicado(!(node.getLeft().getType() instanceof CharType) && !(node.getRight().getType() instanceof CharType), 
					"No se puede operar con char en una operacion aritmetica", node.getStart());
		
		predicado(simple(node.getLeft().getType()) && simple(node.getRight().getType()), "Los operandos en una operacion aritmetica deben de ser de tipos simples", node.getStart());
		
		if (simple(node.getLeft().getType()) && simple(node.getRight().getType()))
			predicado(mismoTipo(node.getRight(), node.getLeft()),
					"Los dos operandos en una operacion aritmetica: " + node.getLeft().getType() + " y " + node.getRight().getType() + ", no son del mismo tipo", node.getStart());
		
		if (node.getOperator().equals("%"))
			predicado(node.getLeft().getType() instanceof IntType, "Para el caso de % en una operacion aritmetica, solo pueden ser enteros", node.getStart());
		
		node.setModificable(false);
		
		if(node.getOperator().equals("*") 
			|| node.getOperator().equals("+") 
			|| node.getOperator().equals("-")
			|| node.getOperator().equals("/")) {
			node.setType(node.getLeft().getType());
		} else{
			node.setType(new IntType());
		}
		
		return null; 
	}
	
	//	class LogicExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(LogicExpresion node, Object param) {
		super.visit(node, param);

		predicado((node.getLeft().getType() instanceof IntType &&
				node.getRight().getType() instanceof IntType) ||
				(node.getLeft().getType() instanceof BooleanType &&
				node.getRight().getType() instanceof BooleanType), 
				"Las operaciones logicas solo pueden operar con enteros o booleans", node.getStart());
		
		node.setModificable(false);
		node.setType(node.getLeft().getType());
		
		return null;
	}
	
	//	class RelacionalExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(RelacionalExpresion node, Object param) {
		super.visit(node, param);
		
		predicado(!(node.getLeft().getType() instanceof BooleanType) && !(node.getRight().getType() instanceof BooleanType), 
				"No se puede operar con boolean en una operacion comparativa", node.getStart());
		
		predicado(!(node.getLeft().getType() instanceof CharType) && !(node.getRight().getType() instanceof CharType), 
					"No se puede operar con char en una operacion comparativa", node.getStart());
		
		predicado(simple(node.getLeft().getType()) && simple(node.getRight().getType()), "Los operandos en una operacion comparativa deben de ser de tipos simples", node.getStart());
		
		if (simple(node.getLeft().getType()) && simple(node.getRight().getType()))
			predicado(mismoTipo(node.getRight(), node.getLeft()),
					"Los dos operandos en una operacion comparativa: " + node.getLeft().getType() + " y " + node.getRight().getType() + ", no son del mismo tipo", node.getStart());
		
		node.setModificable(false);
		node.setType(new IntType());

		return null;
	}
	
	
	//	class NotExpresion { Expresion expresion; }
	public Object visit(NotExpresion node, Object param) {
		super.visit(node, param); 
		
		predicado(node.getExpresion().getType() instanceof IntType || node.getExpresion().getType() instanceof IntType, "La operacion en un NOT ha de ser de tipo int o boolean", node.getStart());
		
		node.setModificable(false);
		node.setType(node.getExpresion().getType());
		
		return null;
	}
	
	//	class AccesoArray { Expresion left;  Expresion right; }
	public Object visit(AccesoArray node, Object param) {
		super.visit(node, param); 
		node.setModificable(true);
		
		predicado(node.getLeft().getType().getClass() == VectorType.class, "La variable debe de ser de tipo array", node.getStart()); 
		predicado(node.getRight().getType().getClass() == IntType.class, "El acceso al array es invalido, debe de ser una expresion entera", node.getStart()); 
		
		if (node.getLeft().getType() instanceof VectorType) {
			node.setType(((VectorType) node.getLeft().getType()).getTipo());
		}
		
		return null;
	}
	
	
	//	class AccesoCampo { Expresion expresion;  String campo; }
	public Object visit(AccesoCampo node, Object param) {
		super.visit(node, param); 
		
		predicado(node.getExpresion().getType() instanceof StringType, "Se requiere una variable de tipo struct", node.getStart());
		node.setModificable(true);
		
		if(node.getExpresion().getType() instanceof StringType){
			StringType stringType = (StringType) node.getExpresion().getType();
			DefStruct struct = (DefStruct) stringType.getDefinicion();
			
			for(Campo campo : struct.getAtributos()){
				if(campo.getNombre().equals(node.getCampo())){
					node.setType(campo.getTipo());
				}
			}
			predicado(node.getType() != null, 
					"Campo " + node.getCampo() + " no definido en el struct de tipo " + struct.getNombre(), node.getStart()); 
		}
		
		return null;
	}
	
	//	class LlamadaFuncionE { String nombre;  List<Expresion> parametros; }
	public Object visit(LlamadaFuncionE node, Object param) {
		super.visit(node, param);
		
		predicado(node.getDefinicion().getParametros().size() == node.getParametros().size(),
				"Debe coincidir el numero de argumentos de la funcion con los parametros (parametros=" + node.getDefinicion().getParametros().size() +
				", argumentos=" + node.getParametros().size() + ")", node.getStart());
				
		if(node.getDefinicion().getParametros().size() == node.getParametros().size()){
			for(int i = 0; i < node.getParametros().size(); i++){
				predicado(node.getDefinicion().getParametros().get(i).getTipo().getClass() == 
						  node.getParametros().get(i).getType().getClass(),
						"El tipo del parametro de la funcion definida " + i + " " + node.getDefinicion().getParametros().get(i).getTipo() + " no coincide con el "
								+ "argumento recibido en la invocacion " + node.getParametros().get(i).getType(), node.getStart());
			}
		}
		
		node.setType(node.getDefinicion().getRetorno());
		node.setModificable(false);
		
		return null;
	}
	

	//	class Cast { Tipo casteo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {
		super.visit(node, param); 
		
		node.setModificable(false);
		
		predicado(simple(node.getCasteo()), "El tipo de un cast debe de ser simple", node.getStart()); 
		predicado(simple(node.getExpresion().getType()), "El tipo de la expresion de un cast debe de ser simple", node.getStart()); 
		if (simple(node.getCasteo()) && simple(node.getExpresion().getType()))
			predicado(node.getCasteo().getClass() != node.getExpresion().getType().getClass(), "El tipo de un cast y el de su "
					+ "expresion han de ser diferentes, son ambos de tipo " + node.getExpresion().getType(), node.getStart()); 
		
		
		node.setType(node.getCasteo());
				
		return null;
	}
	
    // class Variable { String nombre; }
    public Object visit(Variable node, Object param) { 
    	super.visit(node.getDefinicion(), param);
    	
	    node.setType(node.getDefinicion().getTipo()); 
	    node.setModificable(true); 
	    
	    return null; 
    } 
    
    // class IntConstant { String valor; }
    public Object visit(IntConstant node, Object param) { 
	    node.setType(new IntType()); 
	    node.setModificable(false); 
	    
	    return null; 
    } 
    
    // class RealConstant { String valor; }
    public Object visit(RealConstant node, Object param) { 
	    node.setType(new FloatType()); 
	    node.setModificable(false); 
	    
	    return null; 
    }
    
    // class CharConstant { String valor; }
    public Object visit(CharConstant node, Object param) { 
	    node.setType(new CharType()); 
	    node.setModificable(false); 
	    
	    return null; 
    }
    
    //	class Truee {  }
	public Object visit(Truee node, Object param) {
		node.setType(new BooleanType()); 
	    node.setModificable(false); 
	    
	    return null; 
	}

	//	class Falsee {  }
	public Object visit(Falsee node, Object param) {
		node.setType(new BooleanType()); 
	    node.setModificable(false); 
	    
	    return null; 
	}
	
	//	class AsignacionE { List<Expresion> list;  Expresion right; }
	public Object visit(AsignacionE node, Object param) {
    	super.visit(node, param);
    	
    	predicado(simple(node.getRight().getType()), "El tipo de la derecha de la asignacion expr debe de ser de tipo simple", node.getStart()); 
    	
    	for(Expresion expr: node.getList()) {
        	predicado(simple(expr.getType()), "El tipo de la izda de la asignacion expr debe de ser de tipo simple", node.getStart()); 
     	    predicado(mismoTipo(expr, node.getRight()), "Los operandos de la asignacion expr deben ser del mismo tipo", node.getStart()); 
     	    predicado(expr.isModificable(), "Se requiere expresion modificable en la izquierda de la asignacion expr", node.getStart()); 
    	}
    	
 	    		
		return null;
	}
	
	//	class Ternario { Expresion expr;  Expresion left;  Expresion right; }
	public Object visit(Ternario node, Object param) {
		super.visit(node, param);
    	
		predicado(node.getExpr().getType() instanceof IntType || node.getExpr().getType() instanceof BooleanType, 
				"Se requiere que la expresion sea de tipo int o boolean", node.getStart());
    	predicado(simple(node.getLeft().getType()), "El tipo de la izquierda del ternario debe de ser de tipo simple", node.getStart()); 
    	predicado(simple(node.getRight().getType()), "El tipo de la derecha del ternario debe de ser de tipo simple", node.getStart()); 
 	    predicado(mismoTipo(node.getLeft(), node.getRight()), "Los operandos del ternario deben ser del mismo tipo", node.getStart()); 
 	    
 	    node.setType(node.getLeft().getType()); 
	    node.setModificable(false);
		
		return null;
	}

    // # ----------------------------------------------------------
    // Metodos auxiliares recomendados (opcionales) -------------

    /**
     * predicado. Metodo auxiliar para implementar los predicados. Borrar si no se quiere usar.
     *
     * Ejemplos de uso (suponiendo que existe un metodo "esPrimitivo(expr)"):
     *
     *      1. predicado(esPrimitivo(expr.tipo), "La expresion debe ser de un tipo primitivo", expr.getStart());
     *      2. predicado(esPrimitivo(expr.tipo), "La expresion debe ser de un tipo primitivo", expr); // Se asume getStart()
     *      3. predicado(esPrimitivo(expr.tipo), "La expresion debe ser de un tipo primitivo");
     *
     * NOTA: El metodo getStart() (ejemplo 1) indica la linea/columna del fichero fuente donde estaba el nodo
     * (y asi poder dar informacion mas detallada de la posicion del error). Si se usa VGen, dicho metodo
     * habra sido generado en todos los nodos del AST.
     * No es obligatorio llamar a getStart() (ejemplo 2), ya que si se pasa el nodo, se usara por defecto dicha
     * posicion.
     * Si no se quiere imprimir la posicion del fichero, se puede omitir el tercer argumento (ejemplo 3).
     *
     * @param condition     Debe cumplirse para que no se produzca un error
     * @param errorMessage  Se imprime si no se cumple la condicion
     * @param posicionError Fila y columna del fichero donde se ha producido el error.
     */
    
    private boolean mismoTipo(Expresion a, Expresion b) { 
    	 return (a.getType().getClass() == b.getType().getClass()); 
    }

    private void predicado(boolean condition, String errorMessage, AST node) {
        predicado(condition, errorMessage, node.getStart());
    }

    private void predicado(boolean condition, String errorMessage, Position position) {
        if (!condition)
            errorManager.notify("Type Checking", errorMessage, position);
    }
    
    private boolean simple(Tipo tipo){
		return tipo instanceof CharType || tipo instanceof IntType || tipo instanceof FloatType || tipo instanceof BooleanType || tipo instanceof VoidType || tipo instanceof Cast;
	}

    private ErrorManager errorManager;
}
