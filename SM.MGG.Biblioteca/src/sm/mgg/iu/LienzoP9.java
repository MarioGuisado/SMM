package sm.mgg.iu;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

//Autor: Mario Guisado García   -Grupo: 3
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import sm.mgg.eventos.LienzoEvent;
import sm.mgg.eventos.LienzoListener;
import sm.mgg.graficos.cara;
import sm.mgg.graficos.curva;
import sm.mgg.graficos.elipse;
import sm.mgg.graficos.figuras;
import sm.mgg.graficos.linea;
import sm.mgg.graficos.rectangulo;
import sm.mgg.graficos.trazo;

/**
 * La clase lienzo será la que represente todo lo que dibujemos en pantalla, 
 * aportando los métodos necesarios para el manejo de todos los eventos y figuras que creemos.
 * @author Mario Guisado García
 */

public class LienzoP9 extends javax.swing.JPanel {
    /**
     * el objeto lienzoEventListeners actuará como array para almacenar los objetos LienzoListener correspondientes
     */
    ArrayList<LienzoListener> lienzoEventListeners = new ArrayList();
    /**
     * El parámetro forma será un objeto figuras que almacenará la forma que estamos dibujando
     * en cada instante. Se encuentra inicializado a la forma linea.
     */
    private figuras forma;
    /**
     * El parámetro clipArea almacenará un objeto Shape que definirá el área sobre el que se puede dibujar
     */
    private Shape clipArea;
    /**
     * seleccionados será una lista de objetos figuras que almacenará los elementos seleccionados del JList
     */
    private List <figuras> seleccionados = new ArrayList();
    /**
     * El parámetro color será un objeto tipo Color que almacenará la propiedad del color del objeto 
     * que estemos tratando en cada instante.
     */
    private Color color = Color.black;
    /**
     * relleno es un booleano usado para controlar si la(s) figura(s) en cuestión deben estar rellenas o no.
     */
    private boolean relleno = false;
    /**
     * mover es un booleano usado para controlar si la(s) figura(s) en cuestión deben ser desplazadas o no.
     */
    private boolean mover = false;
    /**
     * punto_control es un booleano usado para pivotar entre las dos fases de la creación de la curva
     */
    private boolean punto_control = false;
    /**
     * stroke será un objeto BasicStroke que almacenará el tipo de "stroke" o trazo empleado en cada momento.
     */
    private BasicStroke stroke = new BasicStroke();
    /**
     * grosor_trazo es una variable tipo int que será utilizada para almacenar el número exacto del grosor del trazo actual.
     */
    private int grosor_trazo = 0;  
    /**
     * tipo_trazo es una variable tipo int que será utilizada para almacenar el tipo del trazo actual.
     */
    private int tipo_trazo = 0;
    /**
     * composicion es un atributo tipo Composite que utilizaremos para definir la transparencia de las figuras. 
     * Alpha correspondiente iniciado a 1.
     */
    private Composite composicion = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
    /**
     * transparencia es un booleano utilizado para pivotar entre los dos posibles estados de transparencia de las figuras.
     */
    private boolean transparencia = false;
    /**
     * grado es un float utilizado para definir el grado de transparencia de las figuras.
     */
    private float grado = 100;
    /**
     * render es un objeto RenderingHints que utilizaremos para definir el alisado de las figuras. 
     */
    private RenderingHints render = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    /**
     * antialiasing es un booleano utilizado para pivotar entre los posibles estados de "antialiasing" aplicado a las figuras.
     */
    private boolean antialiasing = false;
    /**
     * Herramienta es un enumerado que almacenará las distintas herramientas o figuras con las que trabajaremos.
     */
    private enum Herramienta{
        LINEA, RECTANGULO, ELIPSE, TRAZO, CURVA, CARA;
    }
    /**
     * pincel es un objeto tipo Herramienta que almacenará la herramienta actual. Inicializado a LINEA.
     */
    private Herramienta pincel = Herramienta.LINEA;
    /**
     * punto y punto_auxiliar son objetos tipo Point2D usados para cálculos en las definiciones de las figuras y sus modificaciones.
     */
    private Point2D punto, punto_auxiliar;
    /**
     * vShape es una lista de objetos figuras que almacenará las figuras dibujadas hasta el momento.
     */
    private List<figuras> vShape = new ArrayList();
    /**
     * img es un objeto BufferedImage usado para las actividades relacionadas con las imágenes externas en el lienzo de dibujo. Almacenará la imagen en cuestión.
     */
    private BufferedImage img;
    /**
     * puntoInicioDesplazamiento y puntoInicioDibujadoDesplazamiento son las coordenadas del punto inicial cuando se mueve un objeto, y su primer punto dibujado,
     * ayudarán a calcular el desplazamiento natural de los objetos.
     */
    private Point2D puntoInicioDesplazamiento, puntoInicioDibujadoDesplazamiento;
    /**
     * Creates new form Lienzo
     */
    public LienzoP9() {
        initComponents();
    }
    /**
     * Define la imagen pasada como argumento en el lienzo.
     * @param img Imagen candidata.
    */
    public void setImage(BufferedImage img) {
        this.img = img;
        if (img != null) {
            clipArea = new Rectangle2D.Float(0, 0, img.getWidth(), img.getHeight());
            setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        }
    }
    /**
     * Añade el listener al lienzo.
     *
     * @param listener Listener en cuestión, tipo LienzoListener.
     */
    public void addLienzoListener(LienzoListener listener) {
        if (listener != null) {
            lienzoEventListeners.add(listener);
        }
    }
    /**
     * Asigna los elementos seleccionados a la lista
     * @param lista lista de elementos seleccionados del JList
     */
     public void setSeleccionados(List<figuras> lista){
        seleccionados = lista;
    }
     /**
      * Método para el volcado de figuras, es llamado por getImage y realiza la misma
      * función que el método paint, pero para los objetos seleccionados. Una vez pintados
      * los elimina del vector vShape y limpia la lista
      * @param g Objeto graphics necesario para llevar a cabo los métodos sobre objetos graphics2D
      */
    
     public void volcarFiguras(Graphics g){
        super.paint(g);     
        Graphics2D g2d = (Graphics2D)g;
        if (img != null) g2d.drawImage(img,0,0,this);
        if (img != null) {
            g2d.setClip(clipArea);     
        }
         for (figuras s : seleccionados) {
                s.paint(g);
                vShape.remove(s);
            }
            seleccionados.clear();
     }
    
    /**
     * Dibuja el vector de formas pintadas sobre la imagen y la devuelve. Obtiene una imagen con su tipo, ancho y alto, y establece su opacidad. Crea el objeto Graphics2D
     * que será pasado más tarde a volcarFiguras o Paint. A la hora de dibujar las figuras, llamará al método paint o al método de volcado de figuras
     * atendiendo al estado del vector de seleccionados, que será no-vacío si se encuentra alguna figura seleccionada para el volcado.
     * @param pintaVector indica si ha sido dibujada alguna forma
     * @return imagen resultado, tipo BufferedImage
     */
    public BufferedImage getImage(boolean pintaVector) {
        if (pintaVector) {
            BufferedImage imgout = new BufferedImage(img.getWidth(),
                    img.getHeight(),
                    img.getType());
          
            boolean opacoActual = this.isOpaque();
            if (img.getColorModel().hasAlpha()) {
                this.setOpaque(false);
            }
            Graphics2D imgout2 = imgout.createGraphics();
            
            if(seleccionados.isEmpty())
                this.paint(imgout2);
            else
                this.volcarFiguras(imgout2);
            this.setOpaque(opacoActual);
            return(imgout);
        } else 
            return img;  
        
    }
    /**
     * Devuelve la imagen
     * @return imagen, objeto de tipo BufferedImage 
     */
    public BufferedImage getImage() {
        return img;
    }
    /**
     * Método para dibujar sobre el lienzo. Realiza un set del area clip previamente definida y llama al método paint correspondiente
     * a cada figura del vector de figuras.
     * @param g  Objeto Graphics necesario para realizar la llamada a la clase padre.
     */
    public void paint(Graphics g){
        super.paint(g);     
        Graphics2D g2d = (Graphics2D)g;
        
        if (img != null) {
            g2d.drawImage(img,0,0,this);
            g2d.setClip(clipArea);     
        }
 
        for (figuras s : vShape) {
            s.paint(g);
        }
           
    }
    /**
     * Método para obtener la lista de figuras registradas en el lienzo.
     * @return lista de objetos figuras
     */
    public List<figuras> getShapeList(){
        return vShape;
    }
    
    /**
     * Método para definir el grosor del trazo con el que se trabajará. Asigna el grosor y lo almacena. 
     * También se establecerá el tipo de trazado.
     *
     * @param grosor Entero que lo define.
     * @param tipo Tipo de trazo, 1 para normal, 2 punteado, 3 personalizado
     */
    public void setTrazo(int grosor, int tipo){
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
        if(forma != null)
            forma.setTrazo(grosor, tipo);
    }
    
    /**
     * Devuelve el grosor del trazo almacenado.
     * @return grosor, tipo int.
     */
    public int getGrosorTrazo(){
        if (forma != null) 
            return forma.getGrosorTrazo();
        else 
            return 1;
    }
    
    /**
     * Define la transparencia de la figura y almacena si está activa o no.
     * @param transparen booleano de activación.
     */
    public void setTransparencia(boolean transparen, float grado){
       transparencia = transparen;
       this.grado = grado;
       forma.setTransparencia(transparencia, grado);  
    }
    
    /**
     * Método para obtener si la transparencia está activa.
     * @return bool indicando si está activa o no.
     */
    public boolean getTransparencia(){
        return transparencia;
    }
    /**
     * Método para definir la activación del antialising. Se almacena si se activa o no.
     * @param antiali booleano que define si se activa.
     */
    public void setAntialiasing(boolean antiali){
        antialiasing = antiali;
        forma.setAntialiasing(antialiasing);
    }
    /**
     * Devuelve si el antialiasing se encuentra activo.
     * @return true si está activo, false en caso contrario.
     */
    public boolean getAntialiasing(){
        if(forma != null)
            return forma.getAntialiasing();
        else 
            return false;
    }
    /**
     * Método que define y dibuja un rectángulo redondeado.
     * @param g2d necesario para dibujarlo.
     */
    public void pruebaShape (Graphics2D g2d){
        Shape dibujo= new RoundRectangle2D.Double(100, 100,100, 150,50,60);
        g2d.draw(dibujo);
    }
    
    /**
     * Devuelve la figura seleccionada dado el punto pasado por parámetro.
     * @param p punto en el que nos encontramos.
     * @return objeto Shape correspondiente, null si no existe.
     */
    private figuras getFiguraSeleccionada(Point2D p){
        for(figuras s:vShape)
            if(((Shape)s).contains(p)) return s;
                return null;
    }
    
    /**
     * Devuelve el color seleccionado.
     * @return objeto Color correspondiente.
     */
    public Color getColor() {
        if(forma != null)
            return forma.getColor();
        else
            return color;
    }
    /**
     * Define el color.
     * @param color objeto Color que lo define.
     */
    public void setColor(Color color) {
        this.color = color;
        if (forma != null)
            forma.setColor(color);
            
    }
    /**
     * Define si movemos el objeto o no.
     * @param mov Parámetro que lo define, true si se mueve, false en otro caso.
     */
    public void setMover(boolean mov){
        mover = mov;
    }
    /**
     * Devuelve si estamos moviendo el objeto o no.
     * @return true si estamos moviendo el objeto, false en otro caso.
     */
    public boolean getMover(){
        return mover;
    }
    /**
     * Define el relleno de las figuras.
     * @param fill true para rellenarlas, false en otro caso.
     */
    public void setRelleno(boolean fill){
        relleno = fill;
        if (forma != null)
            forma.setRelleno(relleno);
    }
    /**
     * Devuelve si el relleno está activo o no.
     * @return true si está activo, false en otro caso.
     */
    public boolean getRelleno(){
        if(forma != null)
            return forma.getRelleno();
        return false;
    }
    /**
     * Define un nuevo dibujo y activa la linea como figura predeterminada.
     */
    public void setNuevoDibujo(){
        forma = (figuras) new linea(punto, punto_auxiliar);
        this.repaint();
    }
    /**
     * Define el pincel o herramienta elegido.
     * @param pincel_elegido entero que define la herramienta seleccionada.
     */
    public void setPincel(int pincel_elegido) {
        switch (pincel_elegido) {
            case 0:
                this.pincel = Herramienta.LINEA;
                break;
            case 1:
                this.pincel = Herramienta.RECTANGULO;       
                break;
            case 2:
                this.pincel = Herramienta.ELIPSE;
                break;
            case 3:
                this.pincel = Herramienta.TRAZO;
                break;
            case 4:
                this.pincel = Herramienta.CURVA;
                break;
            case 5:
                this.pincel = Herramienta.CARA;
                break;
            default:
                break;
        }
    }
    /**
     * Devuelve la herramienta correspondiente a la linea.
     * @return true si es la linea, false en caso contrario.
     */
    public boolean getLinea(){
        return this.pincel == Herramienta.LINEA;
    }
    /**
     * Devuelve la herramienta correspondiente al rectángulo.
     * @return true si es el rectangulo, false en caso contrario.
     */
    public boolean getRectangulo(){
        return this.pincel == Herramienta.RECTANGULO;
    }
    /**
     * Devuelve la herramienta correspondiente a la elipse.
     * @return true si es la elipse, false en caso contrario.
     */
    public boolean getElipse(){
        return this.pincel == Herramienta.ELIPSE;
    }
    /**
     * Devuelve la herramienta correspondiente al trazo.
     * @return true si es trazo, false en caso contrario.
     */
    public boolean getTrazo(){
        return this.pincel == Herramienta.TRAZO;
    }
    /**
     * Devuelve la herramienta correspondiente a la curva.
     * @return true si es la curva, false en caso contrario.
     */
    public boolean getCurva(){
        return this.pincel == Herramienta.CURVA;
    }
    /**
     * Devuelve la herramienta correspondiente a la cara.
     * @return true si es la cara, false en caso contrario.
     */
    public boolean getCara(){
        return this.pincel == Herramienta.CARA;
    }
    
   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(java.awt.Color.white);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Método para notificar una nueva forma añadida
     * @param evt El objeto LienzoEvent con el que trabajamos
     */
    private void notifyShapeAddedEvent(LienzoEvent evt) {
        if (!lienzoEventListeners.isEmpty()) {
            for (LienzoListener listener : lienzoEventListeners) {
                listener.shapeAdded(evt);
            }
        }
    }
    /**
     * Método para notificar de un cambio en alguna propiedad
     * @param evt El objeto LienzoEvent con el que trabajamos
     */
    private void notifyPropertyChangeEvent(LienzoEvent evt) {
        if (!lienzoEventListeners.isEmpty()) {
            for (LienzoListener listener : lienzoEventListeners) {
                listener.propertyChange(evt);
            }
        }
    }

    /**
     * Método para controlar el press del ratón. Comprueba si estamos en modo mover, en cuyo caso selecciona la figura en cuestión y calcula el punto de inicio
     * de dibujado de la misma. Si no estamos moviendo la figura calcula el punto actual y define la figura correspondiente según el pincel seleccionado.
     * @param evt evento del ratón.
     */
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
       if(mover){
            forma = getFiguraSeleccionada(evt.getPoint());
            if(forma!=null)
                puntoInicioDesplazamiento = evt.getPoint();
           if (forma != null && forma instanceof linea)
               puntoInicioDibujadoDesplazamiento = ((linea) forma).getP1();
           else if (forma != null && forma instanceof rectangulo)
               puntoInicioDibujadoDesplazamiento = new Point2D.Double(((rectangulo) forma).getX(), ((rectangulo) forma).getY());
           else if (forma != null && forma instanceof elipse)
               puntoInicioDibujadoDesplazamiento = new Point2D.Double(((elipse) forma).getX(), ((elipse) forma).getY());
           else if (forma != null && forma instanceof curva)
               puntoInicioDibujadoDesplazamiento = ((curva) forma).getP1();
           else if (forma != null && forma instanceof trazo)
               puntoInicioDibujadoDesplazamiento = new Point2D.Double(((trazo) forma).getCurrentPoint().getX(), ((trazo) forma).getCurrentPoint().getY());
           else if (forma != null && forma instanceof cara) {
               PathIterator iterator = ((cara) forma).getPathIterator(null);
               double[] coords = new double[2];
               iterator.currentSegment(coords);
               double x = coords[0];
               double y = coords[1];
               puntoInicioDibujadoDesplazamiento = new Point2D.Double(x, y);
           }
           this.repaint();
        }
       else{   
        if(null != pincel)
            switch (pincel) {
            case LINEA:
                forma = (figuras) new linea(evt.getPoint(), evt.getPoint());
                vShape.add(forma);
                notifyShapeAddedEvent( new LienzoEvent(this, (Shape) forma,color) );
                break;
            case RECTANGULO:
                punto = evt.getPoint();
                forma = new rectangulo();
                vShape.add(forma);
                notifyShapeAddedEvent( new LienzoEvent(this, (Shape) forma,color) );
                break;
            case ELIPSE:
                punto = evt.getPoint();
                forma = (figuras) new elipse();
                vShape.add(forma);
                notifyShapeAddedEvent( new LienzoEvent(this, (Shape) forma,color) );
                break;
            case TRAZO:              
                forma = (figuras) new trazo();
                ((trazo) forma).moveTo(evt.getPoint().getX(), evt.getPoint().getY());  
                vShape.add(forma);
                notifyShapeAddedEvent( new LienzoEvent(this, (Shape) forma,color) );
                break;
            case CURVA:   
                if(!punto_control){
                    forma = (figuras) new curva();
                    punto = evt.getPoint();
                    vShape.add(forma);
                    notifyShapeAddedEvent( new LienzoEvent(this, (Shape) forma,color) );
                }        
                punto_control = !punto_control;
                break;
            case CARA:
                forma = (figuras) new cara(evt.getPoint());
                vShape.add(forma);
                notifyShapeAddedEvent( new LienzoEvent(this, (Shape) forma,color) );
                this.repaint();
                break;
            default:
                break;
            }
            forma.setColor(color);
            forma.setRelleno(relleno);
            forma.setTransparencia(transparencia, grado);
            forma.setAntialiasing(antialiasing);
            forma.setTrazo(grosor_trazo, tipo_trazo);
        }
    }//GEN-LAST:event_formMousePressed

    /**
     * Método para controlar el arrastrado del ratón una vez pulsado. Si estamos en modo mover calculamos el punto en el que nos encontramos y su diferencia con el punto incial. 
     * Después calculamos el nuevo punto de origen de dibujado de la figura correspondiente que estuviésemos trasladando. En caso de no estar moviendo ninguna figura,
     * creamos aquella indicada por el objeto Shape con las nuevas coordenadas del ratón.
     * @param evt evento del ratón.
     */
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        if(mover){  
            Point2D nuevoPuntoOrigen = null;
            if(forma!=null){
                double dx = evt.getX() - puntoInicioDesplazamiento.getX();
                double dy = evt.getY() - puntoInicioDesplazamiento.getY();
                nuevoPuntoOrigen = new Point2D.Double(puntoInicioDibujadoDesplazamiento.getX() + dx, puntoInicioDibujadoDesplazamiento.getY() + dy);
            }
                //Código para el caso del rectángulo
            if (forma!=null && forma instanceof rectangulo)
                ((rectangulo)forma).setFrame(nuevoPuntoOrigen.getX(),nuevoPuntoOrigen.getY(),((Shape)forma).getBounds2D().getWidth(),((Shape)forma).getBounds2D().getHeight());
                //Código para el caso de la elipse
            if (forma!=null && forma instanceof elipse)
                ((elipse)forma).setFrame(nuevoPuntoOrigen.getX(),nuevoPuntoOrigen.getY(),((Shape)forma).getBounds2D().getWidth(),((Shape)forma).getBounds2D().getHeight());
            if (forma!=null && forma instanceof linea)
                ((linea)forma).setLocation(nuevoPuntoOrigen);
            if (forma!=null && forma instanceof curva)
                ((curva)forma).setLocation(nuevoPuntoOrigen);
            if (forma != null && forma instanceof trazo)
                ((trazo)forma).setLocation(nuevoPuntoOrigen);
             if (forma != null && forma instanceof cara)
                ((cara)forma).setLocation(nuevoPuntoOrigen);
        }
        else{
            if (null != pincel) {
                if (forma instanceof linea) {
                    ((linea) forma).setLine(((linea) forma).getP1(), evt.getPoint());
                }else if(forma instanceof rectangulo){
                    ((rectangulo) forma).setFrameFromDiagonal(punto, evt.getPoint());
                }else if(forma instanceof elipse){
                    ((elipse) forma).setFrameFromDiagonal(punto, evt.getPoint());
                }else if(forma instanceof trazo){
                    ((trazo) forma).lineTo(evt.getPoint().getX(), evt.getPoint().getY());
                }else if(forma instanceof curva){
                    if (punto_control) {
                        ((curva) forma).setCurve(punto, punto, evt.getPoint());
                        punto_auxiliar = evt.getPoint();
                    } else {
                        ((curva) forma).setCurve(punto, evt.getPoint(), punto_auxiliar);
                    } 
                }
            }
        }
        this.repaint();
    }//GEN-LAST:event_formMouseDragged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
