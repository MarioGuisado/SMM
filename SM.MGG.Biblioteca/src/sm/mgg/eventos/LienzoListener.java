/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.mgg.eventos;

import java.util.EventListener;

/**
 * Interfaz diseñada para que el "adapter" correspondiente la implemente
 * @author Mario Guisado García
 */
   
public interface LienzoListener extends EventListener {
    /**
     * shapeAdded será el método para notificar de la adición de una nueva figura al lienzo
     * @param evt El objeto LienzoEvent que representará un evento lanzado por Lienzo
     */
    public void shapeAdded(LienzoEvent evt);
    /**
     * propertyChange será el método para notificar de la modificación de un atributo
     * @param evt El objeto LienzoEvent que representará un evento lanzado por Lienzo
     */
    public void propertyChange(LienzoEvent evt);
}
