package sm.mgg.graficos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Clase para definir la línea. Extiende Line2D.
 * @author Mario Guisado García
 */
public class linea extends Line2D.Float implements figuras{
    /**
     * El parámetro color será un objeto tipo Color que almacenará la propiedad
     * del color del objeto que estemos tratando en cada instante.
     */
    private Color color = Color.black;

    /**
     * relleno es un booleano usado para controlar si la(s) figura(s) en
     * cuestión deben estar rellenas o no.
     */
    private boolean relleno = false;

    /**
     * composicion es un atributo tipo Composite que utilizaremos para definir
     * la transparencia de las figuras. Alpha correspondiente iniciado a 1.
     */
    private Composite composicion = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);

    /**
     * antialiasing es un booleano utilizado para pivotar entre los posibles
     * estados de "antialiasing" aplicado a las figuras.
     */
    private boolean antialiasing = false;

    /**
     * render es un objeto RenderingHints que utilizaremos para definir el
     * alisado de las figuras.
     */
    private RenderingHints render = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

    /**
     * grosor_trazo es una variable tipo int que será utilizada para almacenar
     * el número exacto del grosor del trazo actual. Iniciado a 0.
     */
    private int grosor_trazo = 0;
    
    /**
     * tipo_trazo es una variable tipo int que será utilizada para almacenar el
     * tipo del trazo actual. Iniciado a 0.
     */
    private int tipo_trazo = 0;

    /**
     * stroke será un objeto BasicStroke que almacenará el tipo de "stroke" o
     * trazo empleado en cada momento.
     */
    private BasicStroke stroke = new BasicStroke();
    /**
     * grado es un float utilizado para definir el grado de transparencia de las
     * figuras.
     */
    private float grado = 100;
    /**
     * Constructor
     * @param p1 primer objeto Point2D necesario para definir la figura
     * @param p2 segundo objeto Point2D necesario para definir la figura
     */
    public linea(Point2D p1, Point2D p2){
        super(p1,p2);
    }
    
    /**
     * Método para indicar si el punto en cuestión está cercano a la figura.
     * @param p punto clicado
     * @return true si lo está, false en caso contrario.
     */
    public boolean isNear(Point2D p) {
        // Caso p1=p2 (punto)
        if (this.getP1().equals(this.getP2())) {
            return this.getP1().distance(p) <= 2.0;
        }
        // Caso p1!=p2
        return this.ptLineDist(p) <= 2.0;
    }
    
    /**
     * Método para definir la posición de la línea en el lienzo. Calcula la diferencia de punto inicial y actual y establece la nueva posición en base al desplazamiento.
     * Utiliza el método setLine una vez averiguada la nueva posición.
     * @param pos Point2D actual
     */
    public void setLocation(Point2D pos) {
        double dx = pos.getX() - this.getX1();
        double dy = pos.getY() - this.getY1();
        Point2D newp2 = new Point2D.Double(this.getX2() + dx, this.getY2() + dy);
        this.setLine(pos, newp2);
    }
    /**
     * Método para averiguar si la figura se encuentra cerca del punto pasado como parámetro. Hace uso del método isNear heredado.
     * @param p punto Point2D en cuestión.
     * @return true si lo contiene o esta suficientemente cerca del mismo, false en otro caso.
     */
    @Override
    public boolean contains(Point2D p) {
        return isNear(p);
    }
    /**
     * Método toString para conocer el nombre de la clase.
     * @return string con el nombre de la clase.
     */
    @Override
    public String toString(){
        return "Linea";
    }
    /**
     * Método para dibujar sobre el lienzo. Realiza un set de todos los
     * atributos definidos con anterioridad y pinta el vector de figuras.
     *
     * @param g Objeto Graphics necesario para instanciar el g2d, de la clase Graphics2D, y poder realizar las operaciones sobre él.
     */
    @Override
    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(stroke);

        if (antialiasing) {
            g2d.setRenderingHints(render);
        } else {
            RenderingHints render2 = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
            g2d.setRenderingHints(render2);
        }

        g2d.setPaint(color);
        if (relleno) {
            g2d.fill(this);
        }
        g2d.setComposite(composicion);

        g2d.draw(this);
    }

    /**
     * Método para definir el grosor del trazo con el que se trabajará. Asigna
     * el grosor y lo almacena. También se establecerá el tipo de trazado.
     *
     * @param grosor Entero que lo define.
     * @param tipo Tipo de trazo, 0 para normal, 1 punteado, 2 personalizado
     */
    @Override
    public void setTrazo(int grosor, int tipo) {
        tipo_trazo = tipo;
        switch (tipo) {
            case 1:
                float patronDiscontinuidad[] = {15.0f, 15.0f};
                stroke = new BasicStroke(grosor, BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_MITER, 1.0f,
                        patronDiscontinuidad, 0.0f);
                break;
            case 2:
                float patronDiscontinuidad2[] = {10.0f, 5.0f, 2.0f, 5.0f};
                stroke = new BasicStroke(grosor, BasicStroke.CAP_SQUARE,
                        BasicStroke.JOIN_ROUND, 0.0f,
                        patronDiscontinuidad2, 0.0f);
                break;
            default:
                stroke = new BasicStroke(grosor);
                break;
        }

        grosor_trazo = grosor;
    }

    /**
     * Devuelve el grosor del trazo almacenado.
     *
     * @return grosor, tipo int.
     */
    @Override
    public int getGrosorTrazo() {
        return grosor_trazo;
    }

    /**
     * Define la transparencia de la figura y almacena si está activa o no.
     *
     * @param transparen booleano de activación.
     * * @param grado Grado de transparencia
     */
    @Override
    public void setTransparencia(boolean transparen, float grado) {
        if (transparen) {
            this.grado = grado;
            grado /= 100;
            composicion = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, grado);
        } else {
            composicion = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        }
    }

    /**
     * Método para obtener si la transparencia está activa.
     *
     * @return bool indicando si está activa o no.
     */
    @Override
    public Composite getTransparencia() {
        return composicion;
    }

    /**
     * Método para definir la activación del antialising. Se almacena si se
     * activa o no.
     *
     * @param antiali define si se activa.
     */
    @Override
    public void setAntialiasing(boolean antiali) {
        antialiasing = antiali;
    }

    /**
     * Devuelve si el antialiasing se encuentra activo.
     *
     * @return true si está activo, false en caso contrario.
     */
    @Override
    public boolean getAntialiasing() {
        return antialiasing;
    }

    /**
     * Devuelve el color seleccionado.
     *
     * @return objeto Color correspondiente.
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Define el color.
     *
     * @param color objeto Color que lo define.
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Define el relleno de las figuras.
     *
     * @param fill true para rellenarlas, false en otro caso.
     */
    @Override
    public void setRelleno(boolean fill) {
        relleno = fill;
    }

    /**
     * Devuelve si el relleno está activo o no.
     *
     * @return true si está activo, false en otro caso.
     */
    @Override
    public boolean getRelleno() {
        return relleno;
    }
}
