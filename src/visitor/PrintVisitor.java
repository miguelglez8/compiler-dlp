package visitor;

import ast.*;

public class PrintVisitor extends DefaultVisitor {

	//	class DefVariable { Tipo tipo;  String nombre; }
	public Object visit(DefVariable defVariable, Object param) {
		System.out.print("var " + defVariable.getNombre() + ":");
		
		defVariable.getTipo().accept(this, null);
		
		System.out.println();
		
		return null;
	}

	//	class DefStruct { String nombre;  List<Campo> atributos; }
	public Object visit(DefStruct defStruct, Object param) {
		System.out.println("struct " + defStruct.getNombre() + "{");

		if (defStruct.getAtributos() != null)
			for (Campo child : defStruct.getAtributos())
				child.accept(this, param);

		System.out.println("};");

		return null;
	}

	//	class DefFuncion { String nombre;  List<DefVariable> parametros;  Tipo retorno;  List<DefVariable> variablesLocales;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion defFuncion, Object param) {
		System.out.print(defFuncion.getNombre() + "(");

		if (defFuncion.getParametros() != null) {
			int i = 0;
			for (DefVariable child : defFuncion.getParametros()) {
				if (i!=0) {
					System.out.print(", ");
				} 
				child.accept(this, param);
				i++;
			}
		}
				
		System.out.print(")");

		if (defFuncion.getRetorno() != null) {
			System.out.print(":");
			defFuncion.getRetorno().accept(this, param);
		}
		
		System.out.println("{");

		if (defFuncion.getVariablesLocales() != null) {
			for (DefVariable child : defFuncion.getVariablesLocales()) {
				System.out.print("\t");
				child.accept(this, param);
			}
		}
		
		if (defFuncion.getSentencias() != null) {
			System.out.println();
			for (Sentencia child : defFuncion.getSentencias()) {
				System.out.print("\t");
				child.accept(this, param);
			}
		}
		
		System.out.println("}");
		
		return null;
	}

	//	class Campo { Tipo tipo;  String nombre; }
	public Object visit(Campo campo, Object param) {
		System.out.print("\t" + campo.getNombre() + ":");

		campo.getTipo().accept(this, null);
		
		System.out.println(";");
		
		return null;
	}

	//	class If { Expresion condicionTrue;  List<Sentencia> sentenciasTrue; }
	public Object visit(If _if, Object param) {
		System.out.print("if (");

		if (_if.getCondicionTrue() != null)
			_if.getCondicionTrue().accept(this, param);

		System.out.println(") {");

		if (_if.getSentenciasTrue() != null)
			for (Sentencia child : _if.getSentenciasTrue()) {
				System.out.print("\t\t");
				child.accept(this, param);
			}

		System.out.println("\t}");

		return null;
	}

	//	class Ifelse { Expresion condicionTrue;  List<Sentencia> sentenciasTrue;  List<Sentencia> sentenciasFalse; }
	public Object visit(Ifelse ifelse, Object param) {
		System.out.print("if (");

		if (ifelse.getCondicionTrue() != null)
			ifelse.getCondicionTrue().accept(this, param);

		System.out.println(") {");

		if (ifelse.getSentenciasTrue() != null)
			for (Sentencia child : ifelse.getSentenciasTrue()) {
				System.out.print("\t\t");
				child.accept(this, param);
			}
		
		System.out.println("\t} else {");

		if (ifelse.getSentenciasFalse() != null)
			for (Sentencia child : ifelse.getSentenciasFalse()) {
				System.out.print("\t\t");
				child.accept(this, param);
			}

		System.out.println("\t}");

		return null;
	}

	//	class While { Expresion condicionWhile;  List<Sentencia> sentencia; }
	public Object visit(While _while, Object param) {
		System.out.print("while (");

		if (_while.getCondicionWhile() != null)
			_while.getCondicionWhile().accept(this, param);

		System.out.println(") {");

		if (_while.getSentencia() != null)
			for (Sentencia child : _while.getSentencia()) {
				System.out.print("\t\t");
				child.accept(this, param);
			}
		
		System.out.println("\t}");

		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read read, Object param) {
		System.out.print("read ");

		if (read.getExpresion() != null)
			read.getExpresion().accept(this, param);

		System.out.println(";");
		
		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print print, Object param) {
		System.out.print("print ");

		if (print.getExpresion() != null)
			print.getExpresion().accept(this, param);

		System.out.println(";");
		
		return null;
	}

	//	class Printsp { Expresion expresion; }
	public Object visit(Printsp printsp, Object param) {
		System.out.print("printsp ");

		if (printsp.getExpresion() != null)
			printsp.getExpresion().accept(this, param);

		System.out.println(";");

		return null;
	}

	//	class Println { Expresion expresion; }
	public Object visit(Println println, Object param) {
		System.out.print("println ");

		if (println.getExpresion() != null)
			println.getExpresion().accept(this, param);

		System.out.println(";");

		return null;
	}

	//	class Return { Expresion expresion; }
	public Object visit(Return _return, Object param) {
		System.out.print("return ");

		if (_return.getExpresion() != null)
			_return.getExpresion().accept(this, param);

		System.out.println(";");

		return null;
	}

	//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion asignacion, Object param) {
		if (asignacion.getLeft() != null)
			asignacion.getLeft().accept(this, param);

		System.out.print("=");

		if (asignacion.getRight() != null)
			asignacion.getRight().accept(this, param);

		System.out.println(";");

		return null;
	}

	//	class LlamadaFuncionS { String nombre;  List<Expresion> parametros; }
	public Object visit(LlamadaFuncionS llamadaFuncionS, Object param) {
		System.out.print(llamadaFuncionS.getNombre() + "(");

		if (llamadaFuncionS.getParametros() != null) {
			int i = 0;
			for (Expresion child : llamadaFuncionS.getParametros()) {
				if (i!=0) {
					System.out.print(", ");
				} 
				child.accept(this, param);
				i++;
			}
		}
		
		System.out.print(")");

		System.out.println(";");

		return null;
	}

	//	class ArithmeticExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(ArithmeticExpresion arithmeticExpresion, Object param) {
		if (arithmeticExpresion.getLeft() != null)
			arithmeticExpresion.getLeft().accept(this, param);
		
		System.out.print(arithmeticExpresion.getOperator());

		if (arithmeticExpresion.getRight() != null)
			arithmeticExpresion.getRight().accept(this, param);

		return null;
	}

	//	class LogicExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(LogicExpresion logicExpresion, Object param) {
		if (logicExpresion.getLeft() != null)
			logicExpresion.getLeft().accept(this, param);
		
		System.out.print(logicExpresion.getOperator());

		if (logicExpresion.getRight() != null)
			logicExpresion.getRight().accept(this, param);

		return null;
	}

	//	class RelacionalExpresion { Expresion left;  String operator;  Expresion right; }
	public Object visit(RelacionalExpresion relacionalExpresion, Object param) {
		if (relacionalExpresion.getLeft() != null)
			relacionalExpresion.getLeft().accept(this, param);

		System.out.print(relacionalExpresion.getOperator());

		if (relacionalExpresion.getRight() != null)
			relacionalExpresion.getRight().accept(this, param);

		return null;
	}

	//	class NotExpresion { Expresion expresion; }
	public Object visit(NotExpresion notExpresion, Object param) {
		System.out.print("! ");

		if (notExpresion.getExpresion() != null)
			notExpresion.getExpresion().accept(this, param);

		return null;
	}

	//	class AccesoArray { Expresion left;  Expresion right; }
	public Object visit(AccesoArray accesoArray, Object param) {
		if (accesoArray.getLeft() != null)
			accesoArray.getLeft().accept(this, param);
		
		System.out.print("[");
		
		if (accesoArray.getRight() != null)
			accesoArray.getRight().accept(this, param);

		System.out.print("]");

		return null;
	}

	//	class AccesoCampo { Expresion expresion;  String campo; }
	public Object visit(AccesoCampo accesoCampo, Object param) {		
		accesoCampo.getExpresion().accept(this, null);

		System.out.print("." + accesoCampo.getCampo());

		return null;
	}

	//	class LlamadaFuncionE { String nombre;  List<Expresion> parametros; }
	public Object visit(LlamadaFuncionE llamadaFuncionE, Object param) {
		System.out.print(llamadaFuncionE.getNombre() + "(");

		if (llamadaFuncionE.getParametros() != null) {
			int i = 0;
			for (Expresion child : llamadaFuncionE.getParametros()) {
				if (i!=0) {
					System.out.print(", ");
				} 
				child.accept(this, param);
				i++;
			}
		}
		
		System.out.print(")");

		return null;
	}

	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast cast, Object param) {
		System.out.print("<");
		
		cast.getCasteo().accept(this, null);
		
		System.out.print(">" + "(");
		
		if (cast.getExpresion() != null)
			cast.getExpresion().accept(this, param);
		
		System.out.print(")");
		
		return null;
	}

	//	class Variable { String nombre; }
	public Object visit(Variable variable, Object param) {
		System.out.print(variable.getNombre());
		return null;
	}

	//	class IntConstant { String valor; }
	public Object visit(IntConstant intConstant, Object param) {
		System.out.print(intConstant.getValor());
		return null;
	}

	//	class RealConstant { String valor; }
	public Object visit(RealConstant realConstant, Object param) {
		System.out.print(realConstant.getValor());
		return null;
	}

	//	class CharConstant { String valor; }
	public Object visit(CharConstant charConstant, Object param) {
		System.out.print(charConstant.getValor());
		return null;
	}

	//	class IntType {  }
	public Object visit(IntType intType, Object param) {
		System.out.print("int");
		return null;
	}

	//	class FloatType {  }
	public Object visit(FloatType floatType, Object param) {
		System.out.print("float");
		return null;
	}

	//	class CharType {  }
	public Object visit(CharType charType, Object param) {
		System.out.print("char");
		return null;
	}

	//	class StringType { String nombre; }
	public Object visit(StringType stringType, Object param) {
		System.out.print(stringType.getNombre());
		return null;
	}

	//	class VectorType { String valor;  Tipo tipo; }
	public Object visit(VectorType vectorType, Object param) {		
		System.out.print("[" + vectorType.getTamaño() + "]");
		
		vectorType.getTipo().accept(this, null);
		
		return null;
	}
}
