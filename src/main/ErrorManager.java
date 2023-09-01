/**
 * Tutorial de Diseño de Lenguajes de Programación
 * @author Raúl Izquierdo
 */

package main;

import ast.*;

public class ErrorManager {

    public void notify(String fase, String msg, Position position) {
        String texto = "Error en " + fase + ": ";
        if (position != null)
            texto += "[" + position + "] ";
        notify(texto + msg);
    }

    public void notify(String fase, String msg) {
        notify(fase, msg, null);
    }

    public void notify(String msg) {
        errorsCount++;
        System.out.println(msg);
    }

    public int errorsCount() {
        return errorsCount;
    }

    private int errorsCount = 0;
}
