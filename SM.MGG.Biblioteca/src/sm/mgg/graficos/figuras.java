/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sm.mgg.graficos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;

/**
 *
 * @author Mario Guisado García
 */
public interface figuras {
     /**
     * Método para dibujar sobre el lienzo. Realiza un set de todos los
     * atributos definidos con anterioridad y pinta el vector de figuras.
     *
     * @param g Objeto Graphics necesario para instanciar el g2d, de la clase Graphics2D, y poder realizar las operaciones sobre él.
     */
     public void paint(Graphics g);

    /**
     * Método para definir el grosor del trazo con el que se trabajará. Asigna el grosor y lo almacena. 
     * También se establecerá el tipo de trazado.
     *
     * @param grosor Entero que lo define.
     * @param tipo Tipo de trazo, 1 para normal, 2 punteado, 3 personalizado
     */
    public void setTrazo(int grosor, int tipo);

    /**
     * Devuelve el grosor del trazo almacenado.
     *
     * @return grosor, tipo int.
     */
    public int getGrosorTrazo();

    /**
     * Define la transparencia de la figura y almacena si está activa o no.
     *
     * @param transparen booleano de activación.
     * @param grado Grado de transparencia
     */
    public void setTransparencia(boolean transparen, float grado);

    /**
     * Método para obtener si la transparencia está activa.
     *
     * @return bool indicando si está activa o no.
     */
    public Composite getTransparencia();

    /**
     * Método para definir la activación del antialising. Se almacena si se
     * activa o no.
     *
     * @param antiali define si se activa.
     */
    public void setAntialiasing(boolean antiali);

    /**
     * Devuelve si el antialiasing se encuentra activo.
     *
     * @return true si está activo, false en caso contrario.
     */
    public boolean getAntialiasing();

    /**
     * Devuelve el color seleccionado.
     *
     * @return objeto Color correspondiente.
     */
    public Color getColor();

    /**
     * Define el color.
     *
     * @param color objeto Color que lo define.
     */
    public void setColor(Color color);

    /**
     * Define el relleno de las figuras.
     *
     * @param fill true para rellenarlas, false en otro caso.
     */
    public void setRelleno(boolean fill);

    /**
     * Devuelve si el relleno está activo o no.
     *
     * @return true si está activo, false en otro caso.
     */
    public boolean getRelleno();
}
