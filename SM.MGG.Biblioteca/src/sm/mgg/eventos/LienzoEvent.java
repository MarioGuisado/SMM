/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.mgg.eventos;
import java.awt.Color;
import java.awt.Shape;
import java.util.EventListener;
import java.util.EventObject;
/**
 * LienzoEvent será la clase que represente un evento lanzado por Lienzo
 * @author Mario Guisado García
 */
public class LienzoEvent extends EventObject {
    /**
     * forma será el objeto tipo Shape que almacenará la figura que lanza el evento.
     */
    private Shape forma;
    /**
     * color será un parámetro de clase de tipo Color que almacenará el atributo asociado a la figura
     */
    private Color color;
    
    /**
     * El constructor inicializará los atributos y llamará al constructor padre
     * @param source Objeto tipo Object para poder llamar al constructor padre
     * @param forma Objeto Shape para poder inicializar el correspondiente parámetro
     * @param color Objeto Color para poder inicializar el correspondiente parámetro
     */
    public LienzoEvent(Object source, Shape forma, Color color) {
        super(source);
        this.forma = forma;
        this.color = color;
    }
    
    /**
     * Get correspondiente al objeto Shape de la clase
     * @return objeto Shape, forma
     */
    public Shape getForma() {
        return forma;
    }
    /**
     * Get correspondiente al objeto Color de la clase
     * @return objeto Color, color
     */
    public Color getColor() {
        return color;
    }
}
