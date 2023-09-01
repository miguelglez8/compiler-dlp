/**
 * Tutorial de Diseño de Lenguajes de Programación
 * @author Raúl Izquierdo
 */

package codegeneration;

import ast.*;
import visitor.*;

/**
 * Clase encargada de asignar direcciones a las variables.
 */
public class MemoryAllocation extends DefaultVisitor {

	//	class Programa { List<Operacion> operaciones; }
	public Object visit(Programa node, Object param) {
		// super.visit(node, param);

		int address = 0;

		if (node.getOperaciones() != null){
			for (Operacion child : node.getOperaciones()){
				if(child instanceof DefVariable) {
					((DefVariable) child).setAddress(address);
					address += ((DefVariable) child).getTipo().getSize();
				}
				child.accept(this, param);
			}
		}

		return null;
	}
	
	//	class DefStruct { String nombre;  List<Campo> atributos; }
	public Object visit(DefStruct node, Object param) {
		// super.visit(node, param);
		
		int address = 0;

		if (node.getAtributos() != null){
			for (Campo child : node.getAtributos()){
				child.setAddress(address);
				address += child.getTipo().getSize();
				child.accept(this, param);
			}
		}	
		
		return null;
	}
	
	//	class DefFuncion { String nombre;  List<DefVariable> parametros;  Tipo retorno;  List<DefVariable> variablesLocales;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion node, Object param) {
		// super.visit(node, param);
		
		// la direcci�n es relativa al BP, NO ES ABSOLUTA!! 
		
		if (node.getParametros() != null){
			int address = 4;
		
			for (int i = (node.getParametros().size() - 1); i >= 0; i--){
				node.getParametros().get(i).setAddress(address);
				address += node.getParametros().get(i).getTipo().getSize();
				node.getParametros().get(i).accept(this, param);
			}
		}

		if (node.getRetorno() != null)
			node.getRetorno().accept(this, param);
		
		if (node.getVariablesLocales() != null){
			int address = 0;

			for (DefVariable child : node.getVariablesLocales()){
				address -= child.getTipo().getSize();
				child.setAddress(address);
				child.accept(this, param);
			}
		}
		
		visitChildren(node.getSentencias(), param);
		return null;
	}
	

}
