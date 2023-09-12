/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package p14;
//Autor: Mario Guisado García   -Grupo: 3

import java.awt.Color;
import java.awt.Cursor;
import java.awt.List;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BandCombineOp;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBuffer;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.File;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import sm.image.EqualizationOp;
import sm.image.KernelProducer;
import sm.image.LookupTableProducer;
import sm.image.SepiaOp;
import sm.image.TintOp;
import sm.mgg.eventos.LienzoAdapter;
import sm.mgg.eventos.LienzoEvent;
import sm.mgg.graficos.figuras;
import sm.mgg.image.NuevaOp;
import sm.mgg.image.PosterizarOp;
import sm.mgg.image.RojoOp;
import sm.mgg.image.TonoOp;
import sm.mgg.iu.LienzoP9;
import sm.sound.SMClipPlayer;
import sm.sound.SMSoundRecorder;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

/**
 *
 * @author mario
 */
public class ventanaP14 extends javax.swing.JFrame {

    private InternalFrameListener manejador;
    private BufferedImage imgFuente = null;
    private double m = 128;
    private double parametro = 1;
    private SMClipPlayer player = null;
    private SMSoundRecorder recorder = null;

    /**
     * Creates new form ventana
     */
    public ventanaP14() {
        initComponents();
        manejador = new ManejadorVentanaInterna();
        sliderBrillo.setValue(0);
        sliderContraste.setValue(0);
        lista.setModel(new DefaultListModel());
    }

    public LienzoP9 getLienzoSeleccionado() {
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        return vi != null ? vi.getLienzo() : null;
    }

    private Kernel getKernel(int seleccion) {
        Kernel k = null;
        switch (seleccion) {
            case 0:
                /* //Máscara 5x5
                float filtroMedia[]  = { 0.1f, 0.1f, 0.1f, 0.1f, 0.1f,
                                        0.1f, 0.2f, 0.1f ,0.1f, 0.1f,
                                        0.1f, 0.1f, 0.1f,0.1f, 0.1f,
                                        0.1f, 0.1f, 0.1f,0.1f, 0.1f,
                                        0.1f, 0.1f, 0.1f, 0.1f, 0.1f} ;
                k = new Kernel(5, 5, filtroMedia);
                
                //Máscara 7x7
                float filtroMedia[]  = { 0.1f, 0.1f, 0.1f, 0.1f, 0.1f,0.1f,0.1f,
                                        0.1f, 0.1f, 0.1f ,0.1f, 0.1f,0.1f,0.1f,
                                        0.1f, 0.1f, 0.1f,0.1f, 0.1f,0.1f,0.1f,
                                        0.1f, 0.1f, 0.1f,0.1f, 0.1f,0.1f,0.1f,
                                        0.1f, 0.1f, 0.1f, 0.1f, 0.1f,0.1f,0.1f,
                                        0.1f, 0.1f, 0.1f, 0.1f, 0.1f,0.1f,0.1f,
                                        0.1f, 0.1f, 0.1f, 0.1f, 0.1f,0.1f,0.1f} ;
                k = new Kernel(7, 7, filtroMedia);
                 */
                k = KernelProducer.createKernel(KernelProducer.TYPE_MEDIA_3x3);
                break;
            case 1:
                k = KernelProducer.createKernel(KernelProducer.TYPE_BINOMIAL_3x3);
                break;
            case 2:
                k = KernelProducer.createKernel(KernelProducer.TYPE_ENFOQUE_3x3);
                break;
            case 3:
                k = KernelProducer.createKernel(KernelProducer.TYPE_RELIEVE_3x3);
                break;
            case 4:
                k = KernelProducer.createKernel(KernelProducer.TYPE_LAPLACIANA_3x3);
                break;
            case 5:
                float filtroMediaHorizontal[] = {0.2f, 0.2f, 0.2f, 0.2f, 0.2f};
                k = new Kernel(5, 1, filtroMediaHorizontal);
                break;
            case 6:
                float filtroMediaHorizontalx7[] = {0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f};
                k = new Kernel(7, 1, filtroMediaHorizontalx7);
                break;
            case 7:
                float filtroMediaHorizontalx10[] = {0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f};
                k = new Kernel(10, 1, filtroMediaHorizontalx10);
                break;
        }
        return k;
    }

    private static LookupTable lineal(double a, double b) {
        double m = 0;
        if (a != 255) {
            m = (255 - b) / (255 - a);
        }

        byte lt[] = new byte[256];
        for (int l = 0; l < 256; l++) {
            if (l < a) {
                lt[l] = (byte) ((b / a) * l);
            } else {
                lt[l] = (byte) ((m * (l - a)) + b);
            }

        }
        ByteLookupTable slt = new ByteLookupTable(0, lt);
        return slt;
    }

    private void AccionSliderLineal(double a, double b) {
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    LookupTable lt = lineal(a, b);
                    LookupOp lop = new LookupOp(lt, null);
                    lop.filter(imgFuente, img); // Imagen origen y destino iguales
                    vi.getLienzo().repaint();
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }

    private BufferedImage getImageBand(BufferedImage img, int banda) {
        //Creamos el modelo de color de la nueva imagen basado en un espcio de color GRAY
        ColorSpace cs = new sm.image.color.GreyColorSpace();
        ComponentColorModel cm = new ComponentColorModel(cs, false, false,
                Transparency.OPAQUE,
                DataBuffer.TYPE_BYTE);
        //Creamos el nuevo raster a partir del raster de la imagen original
        int vband[] = {banda};
        WritableRaster bRaster = (WritableRaster) img.getRaster().createWritableChild(0, 0,
                img.getWidth(), img.getHeight(), 0, 0, vband);
        //Creamos una nueva imagen que contiene como raster el correspondiente a la banda
        return new BufferedImage(cm, bRaster, false, null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoPincel = new javax.swing.ButtonGroup();
        grupoColor = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        botonNuevo = new javax.swing.JButton();
        botonAbrir = new javax.swing.JButton();
        botonGuardar = new javax.swing.JButton();
        botonLinea = new javax.swing.JToggleButton();
        botonRectangulo = new javax.swing.JToggleButton();
        botonElipse = new javax.swing.JToggleButton();
        botonCurva = new javax.swing.JToggleButton();
        botonTrazo = new javax.swing.JToggleButton();
        botonCara = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        colorNegro = new javax.swing.JToggleButton();
        colorRojo = new javax.swing.JToggleButton();
        colorAzul = new javax.swing.JToggleButton();
        colorAmarillo = new javax.swing.JToggleButton();
        colorVerde = new javax.swing.JToggleButton();
        dialogoColores = new javax.swing.JLabel();
        botonSeleccionar = new javax.swing.JToggleButton();
        botonRelleno = new javax.swing.JToggleButton();
        botonTransparencia = new javax.swing.JToggleButton();
        sliderGradoTransparencia = new javax.swing.JSlider();
        botonAntialiasing = new javax.swing.JToggleButton();
        spinnerTrazo = new javax.swing.JSpinner();
        tipoTrazo = new javax.swing.JComboBox<>();
        sliderLookupPropio = new javax.swing.JSlider();
        botonNegativo = new javax.swing.JButton();
        botonDuplicar = new javax.swing.JButton();
        botonReproducir = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        botonPausar = new javax.swing.JButton();
        listaReproduccion = new javax.swing.JComboBox<>();
        botonGrabar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        botonCamara = new javax.swing.JButton();
        botonCaptura = new javax.swing.JButton();
        barraEstado = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        escritorio = new javax.swing.JDesktopPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista = new javax.swing.JList<>();
        jPanel6 = new javax.swing.JPanel();
        volcar = new javax.swing.JButton();
        panelImagenOp = new javax.swing.JPanel();
        panelBrillo = new javax.swing.JPanel();
        sliderBrillo = new javax.swing.JSlider();
        sliderContraste = new javax.swing.JSlider();
        panelContraste = new javax.swing.JPanel();
        contrasteNormal = new javax.swing.JButton();
        contrasteIluminar = new javax.swing.JButton();
        contrasteOscurecer = new javax.swing.JButton();
        rotacion180 = new javax.swing.JButton();
        sliderRotacion = new javax.swing.JSlider();
        aumentar = new javax.swing.JButton();
        disminuir = new javax.swing.JButton();
        cuadratica = new javax.swing.JButton();
        panelLineal = new javax.swing.JPanel();
        lineal = new javax.swing.JToggleButton();
        sliderA = new javax.swing.JSlider();
        sliderB = new javax.swing.JSlider();
        sliderM = new javax.swing.JSlider();
        panelFiltros = new javax.swing.JPanel();
        seleccionMascara = new javax.swing.JComboBox<>();
        panelColor = new javax.swing.JPanel();
        botonCombinacion = new javax.swing.JButton();
        botonExtraccion = new javax.swing.JButton();
        seleccionEspacioColor = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        botonTintado = new javax.swing.JButton();
        botonSepia = new javax.swing.JButton();
        botonEcualizar = new javax.swing.JButton();
        botonRojo = new javax.swing.JButton();
        sliderPosterizar = new javax.swing.JSlider();
        sliderTono = new javax.swing.JSlider();
        botonNuevaOp = new javax.swing.JButton();
        spinnerParametroNuevaOp = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jSlider2 = new javax.swing.JSlider();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuItemNuevo = new javax.swing.JMenuItem();
        menuItemAbrir = new javax.swing.JMenuItem();
        menuItemGuardar = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menuImagen = new javax.swing.JMenu();
        menuItemRescale = new javax.swing.JMenuItem();
        menuItemConvolve = new javax.swing.JMenuItem();
        menuItemAffineTransform = new javax.swing.JMenuItem();
        menuItemLookup = new javax.swing.JMenuItem();
        BandCombineOp = new javax.swing.JMenuItem();
        ColorConvertOp = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        itemAcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(586, 500));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        jToolBar1.setRollover(true);
        jToolBar1.setPreferredSize(new java.awt.Dimension(500, 39));

        botonNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/nuevo.png"))); // NOI18N
        botonNuevo.setFocusable(false);
        botonNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoActionPerformed(evt);
            }
        });
        jToolBar1.add(botonNuevo);

        botonAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/abrir.png"))); // NOI18N
        botonAbrir.setFocusable(false);
        botonAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAbrirActionPerformed(evt);
            }
        });
        jToolBar1.add(botonAbrir);

        botonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/guardar.png"))); // NOI18N
        botonGuardar.setFocusable(false);
        botonGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });
        jToolBar1.add(botonGuardar);

        grupoPincel.add(botonLinea);
        botonLinea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/linea.png"))); // NOI18N
        botonLinea.setSelected(true);
        botonLinea.setToolTipText("Línea");
        botonLinea.setFocusable(false);
        botonLinea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonLinea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLineaActionPerformed(evt);
            }
        });
        jToolBar1.add(botonLinea);

        grupoPincel.add(botonRectangulo);
        botonRectangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/rectangulo.png"))); // NOI18N
        botonRectangulo.setToolTipText("Rectángulo");
        botonRectangulo.setFocusable(false);
        botonRectangulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRectangulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRectangulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRectanguloActionPerformed(evt);
            }
        });
        jToolBar1.add(botonRectangulo);

        grupoPincel.add(botonElipse);
        botonElipse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/elipse.png"))); // NOI18N
        botonElipse.setToolTipText("Elipse");
        botonElipse.setFocusable(false);
        botonElipse.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonElipse.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonElipse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonElipseActionPerformed(evt);
            }
        });
        jToolBar1.add(botonElipse);

        grupoPincel.add(botonCurva);
        botonCurva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/curva.png"))); // NOI18N
        botonCurva.setToolTipText("Curva");
        botonCurva.setFocusable(false);
        botonCurva.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonCurva.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonCurva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCurvaActionPerformed(evt);
            }
        });
        jToolBar1.add(botonCurva);

        grupoPincel.add(botonTrazo);
        botonTrazo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/trazo.png"))); // NOI18N
        botonTrazo.setToolTipText("Trazo");
        botonTrazo.setFocusable(false);
        botonTrazo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonTrazo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonTrazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTrazoActionPerformed(evt);
            }
        });
        jToolBar1.add(botonTrazo);

        grupoPincel.add(botonCara);
        botonCara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/smile.png"))); // NOI18N
        botonCara.setToolTipText("Cara");
        botonCara.setFocusable(false);
        botonCara.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonCara.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonCara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCaraActionPerformed(evt);
            }
        });
        jToolBar1.add(botonCara);

        jPanel3.setMinimumSize(new java.awt.Dimension(80, 10));
        jPanel3.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel3.setLayout(new java.awt.GridLayout(2, 3));

        colorNegro.setBackground(java.awt.Color.black);
        grupoColor.add(colorNegro);
        colorNegro.setToolTipText("Negro");
        colorNegro.setPreferredSize(new java.awt.Dimension(25, 25));
        colorNegro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorNegroActionPerformed(evt);
            }
        });
        jPanel3.add(colorNegro);

        colorRojo.setBackground(java.awt.Color.red);
        grupoColor.add(colorRojo);
        colorRojo.setToolTipText("Rojo");
        colorRojo.setPreferredSize(new java.awt.Dimension(15, 15));
        colorRojo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorRojoActionPerformed(evt);
            }
        });
        jPanel3.add(colorRojo);

        colorAzul.setBackground(java.awt.Color.blue);
        grupoColor.add(colorAzul);
        colorAzul.setToolTipText("Azul");
        colorAzul.setPreferredSize(new java.awt.Dimension(15, 15));
        colorAzul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorAzulActionPerformed(evt);
            }
        });
        jPanel3.add(colorAzul);

        colorAmarillo.setBackground(java.awt.Color.yellow);
        grupoColor.add(colorAmarillo);
        colorAmarillo.setToolTipText("Amarillo");
        colorAmarillo.setPreferredSize(new java.awt.Dimension(15, 15));
        colorAmarillo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorAmarilloActionPerformed(evt);
            }
        });
        jPanel3.add(colorAmarillo);

        colorVerde.setBackground(java.awt.Color.green);
        grupoColor.add(colorVerde);
        colorVerde.setToolTipText("Verde");
        colorVerde.setPreferredSize(new java.awt.Dimension(15, 15));
        colorVerde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorVerdeActionPerformed(evt);
            }
        });
        jPanel3.add(colorVerde);

        dialogoColores.setText("+");
        dialogoColores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dialogoColoresMouseClicked(evt);
            }
        });
        jPanel3.add(dialogoColores);

        jToolBar1.add(jPanel3);

        botonSeleccionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/seleccion.png"))); // NOI18N
        botonSeleccionar.setToolTipText("Selección");
        botonSeleccionar.setFocusable(false);
        botonSeleccionar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSeleccionar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSeleccionarActionPerformed(evt);
            }
        });
        jToolBar1.add(botonSeleccionar);

        botonRelleno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/rellenar.png"))); // NOI18N
        botonRelleno.setToolTipText("Relleno");
        botonRelleno.setFocusable(false);
        botonRelleno.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRelleno.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRelleno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRellenoActionPerformed(evt);
            }
        });
        jToolBar1.add(botonRelleno);

        botonTransparencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/transparencia.png"))); // NOI18N
        botonTransparencia.setToolTipText("Transparencia");
        botonTransparencia.setFocusable(false);
        botonTransparencia.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonTransparencia.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonTransparencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTransparenciaActionPerformed(evt);
            }
        });
        jToolBar1.add(botonTransparencia);

        sliderGradoTransparencia.setToolTipText("Grado de transparencia");
        sliderGradoTransparencia.setValue(100);
        sliderGradoTransparencia.setPreferredSize(new java.awt.Dimension(80, 20));
        sliderGradoTransparencia.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGradoTransparenciaStateChanged(evt);
            }
        });
        jToolBar1.add(sliderGradoTransparencia);

        botonAntialiasing.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/alisar.png"))); // NOI18N
        botonAntialiasing.setToolTipText("Antialiasing");
        botonAntialiasing.setFocusable(false);
        botonAntialiasing.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAntialiasing.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonAntialiasing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAntialiasingActionPerformed(evt);
            }
        });
        jToolBar1.add(botonAntialiasing);

        spinnerTrazo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerTrazoStateChanged(evt);
            }
        });
        jToolBar1.add(spinnerTrazo);

        tipoTrazo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Continuo", "Punteado", "Personalizado" }));
        tipoTrazo.setToolTipText("Tipo de trazo");
        tipoTrazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoTrazoActionPerformed(evt);
            }
        });
        jToolBar1.add(tipoTrazo);

        sliderLookupPropio.setMaximum(10);
        sliderLookupPropio.setMinimum(1);
        sliderLookupPropio.setToolTipText("LookUp Propio");
        sliderLookupPropio.setValue(1);
        sliderLookupPropio.setPreferredSize(new java.awt.Dimension(80, 20));
        sliderLookupPropio.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderLookupPropioStateChanged(evt);
            }
        });
        sliderLookupPropio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderLookupPropioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderLookupPropioFocusLost(evt);
            }
        });
        jToolBar1.add(sliderLookupPropio);

        botonNegativo.setText("Negativo");
        botonNegativo.setFocusable(false);
        botonNegativo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonNegativo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonNegativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNegativoActionPerformed(evt);
            }
        });
        jToolBar1.add(botonNegativo);

        botonDuplicar.setText("Duplicar");
        botonDuplicar.setFocusable(false);
        botonDuplicar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonDuplicar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonDuplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDuplicarActionPerformed(evt);
            }
        });
        jToolBar1.add(botonDuplicar);

        botonReproducir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/play24x24.png"))); // NOI18N
        botonReproducir.setFocusable(false);
        botonReproducir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonReproducir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonReproducir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReproducirActionPerformed(evt);
            }
        });
        jToolBar1.add(botonReproducir);
        jToolBar1.add(jSeparator1);

        botonPausar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/stop24x24.png"))); // NOI18N
        botonPausar.setFocusable(false);
        botonPausar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonPausar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonPausar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPausarActionPerformed(evt);
            }
        });
        jToolBar1.add(botonPausar);
        jToolBar1.add(listaReproduccion);

        botonGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/record24x24.png"))); // NOI18N
        botonGrabar.setFocusable(false);
        botonGrabar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonGrabar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGrabarActionPerformed(evt);
            }
        });
        jToolBar1.add(botonGrabar);
        jToolBar1.add(jSeparator2);

        botonCamara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/Camara.png"))); // NOI18N
        botonCamara.setFocusable(false);
        botonCamara.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonCamara.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonCamara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCamaraActionPerformed(evt);
            }
        });
        jToolBar1.add(botonCamara);

        botonCaptura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/Capturar.png"))); // NOI18N
        botonCaptura.setFocusable(false);
        botonCaptura.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonCaptura.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonCaptura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCapturaActionPerformed(evt);
            }
        });
        jToolBar1.add(botonCaptura);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        barraEstado.setText("Barra de estado");
        barraEstado.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(barraEstado, java.awt.BorderLayout.PAGE_END);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setResizeWeight(1.0);

        escritorio.setMinimumSize(new java.awt.Dimension(450, 300));

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1367, Short.MAX_VALUE)
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 303, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(escritorio);

        jPanel5.setLayout(new java.awt.BorderLayout());

        lista.setMaximumSize(new java.awt.Dimension(10, 100));
        lista.setMinimumSize(new java.awt.Dimension(5, 100));
        lista.setPreferredSize(new java.awt.Dimension(10, 100));
        jScrollPane1.setViewportView(lista);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.BorderLayout());

        volcar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/volcar.png"))); // NOI18N
        volcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volcarActionPerformed(evt);
            }
        });
        jPanel6.add(volcar, java.awt.BorderLayout.WEST);

        jPanel5.add(jPanel6, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setRightComponent(jPanel5);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        panelBrillo.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panelBrillo.setLayout(new java.awt.BorderLayout());

        sliderBrillo.setMaximum(255);
        sliderBrillo.setMinimum(-255);
        sliderBrillo.setToolTipText("Brillo");
        sliderBrillo.setValue(5);
        sliderBrillo.setPreferredSize(new java.awt.Dimension(80, 20));
        sliderBrillo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderBrilloStateChanged(evt);
            }
        });
        sliderBrillo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderBrilloFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderBrilloFocusLost(evt);
            }
        });
        panelBrillo.add(sliderBrillo, java.awt.BorderLayout.WEST);

        sliderContraste.setMaximum(20);
        sliderContraste.setToolTipText("Contraste");
        sliderContraste.setValue(0);
        sliderContraste.setPreferredSize(new java.awt.Dimension(80, 20));
        sliderContraste.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderContrasteStateChanged(evt);
            }
        });
        sliderContraste.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderContrasteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderContrasteFocusLost(evt);
            }
        });
        panelBrillo.add(sliderContraste, java.awt.BorderLayout.EAST);

        panelImagenOp.add(panelBrillo);

        panelContraste.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panelContraste.setLayout(new java.awt.GridLayout(1, 0));

        contrasteNormal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/contraste.png"))); // NOI18N
        contrasteNormal.setToolTipText("Contraste Normal");
        contrasteNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrasteNormalActionPerformed(evt);
            }
        });
        panelContraste.add(contrasteNormal);

        contrasteIluminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/iluminar.png"))); // NOI18N
        contrasteIluminar.setToolTipText("Contraste Iluminado");
        contrasteIluminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrasteIluminarActionPerformed(evt);
            }
        });
        panelContraste.add(contrasteIluminar);

        contrasteOscurecer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/oscurecer.png"))); // NOI18N
        contrasteOscurecer.setToolTipText("Contraste Oscurecido");
        contrasteOscurecer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrasteOscurecerActionPerformed(evt);
            }
        });
        panelContraste.add(contrasteOscurecer);

        rotacion180.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/rotacion180.png"))); // NOI18N
        rotacion180.setToolTipText("Rotacion 180");
        rotacion180.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotacion180ActionPerformed(evt);
            }
        });
        panelContraste.add(rotacion180);

        sliderRotacion.setMaximum(360);
        sliderRotacion.setToolTipText("Rotación");
        sliderRotacion.setPreferredSize(new java.awt.Dimension(70, 20));
        sliderRotacion.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderRotacionStateChanged(evt);
            }
        });
        sliderRotacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderRotacionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderRotacionFocusLost(evt);
            }
        });
        panelContraste.add(sliderRotacion);

        aumentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/aumentar.png"))); // NOI18N
        aumentar.setToolTipText("Aumentar");
        aumentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aumentarActionPerformed(evt);
            }
        });
        panelContraste.add(aumentar);

        disminuir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/disminuir.png"))); // NOI18N
        disminuir.setToolTipText("Disminuir");
        disminuir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disminuirActionPerformed(evt);
            }
        });
        panelContraste.add(disminuir);

        cuadratica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/cuadratica.png"))); // NOI18N
        cuadratica.setToolTipText("Cuadrática");
        cuadratica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuadraticaActionPerformed(evt);
            }
        });
        panelContraste.add(cuadratica);

        panelImagenOp.add(panelContraste);

        panelLineal.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panelLineal.setLayout(new java.awt.BorderLayout());

        lineal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/lineal.png"))); // NOI18N
        lineal.setToolTipText("Lineal");
        lineal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linealActionPerformed(evt);
            }
        });
        panelLineal.add(lineal, java.awt.BorderLayout.CENTER);

        sliderA.setMaximum(255);
        sliderA.setToolTipText("Slider A");
        sliderA.setValue(150);
        sliderA.setEnabled(false);
        sliderA.setPreferredSize(new java.awt.Dimension(80, 20));
        sliderA.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderAStateChanged(evt);
            }
        });
        panelLineal.add(sliderA, java.awt.BorderLayout.PAGE_START);

        sliderB.setMaximum(255);
        sliderB.setToolTipText("Slider B");
        sliderB.setValue(150);
        sliderB.setEnabled(false);
        sliderB.setPreferredSize(new java.awt.Dimension(80, 20));
        sliderB.setRequestFocusEnabled(false);
        sliderB.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderBStateChanged(evt);
            }
        });
        panelLineal.add(sliderB, java.awt.BorderLayout.PAGE_END);

        sliderM.setMaximum(255);
        sliderM.setToolTipText("Parámetro Cuadrática");
        sliderM.setValue(128);
        sliderM.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        sliderM.setPreferredSize(new java.awt.Dimension(80, 22));
        sliderM.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderMStateChanged(evt);
            }
        });
        panelLineal.add(sliderM, java.awt.BorderLayout.LINE_END);

        panelImagenOp.add(panelLineal);

        panelFiltros.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panelFiltros.setLayout(new java.awt.BorderLayout());

        seleccionMascara.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Media", "Binomial", "Enfoque", "Relieve", "Laplaciano", "Horizontal", "Horizontalx7", "Horizontalx10" }));
        seleccionMascara.setToolTipText("Mascara");
        seleccionMascara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionMascaraActionPerformed(evt);
            }
        });
        panelFiltros.add(seleccionMascara, java.awt.BorderLayout.CENTER);

        panelImagenOp.add(panelFiltros);

        panelColor.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panelColor.setLayout(new java.awt.BorderLayout());

        botonCombinacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/combinar.png"))); // NOI18N
        botonCombinacion.setToolTipText("Combinación");
        botonCombinacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCombinacionActionPerformed(evt);
            }
        });
        panelColor.add(botonCombinacion, java.awt.BorderLayout.CENTER);

        botonExtraccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/bandas.png"))); // NOI18N
        botonExtraccion.setToolTipText("Extracción");
        botonExtraccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonExtraccionActionPerformed(evt);
            }
        });
        panelColor.add(botonExtraccion, java.awt.BorderLayout.PAGE_START);

        seleccionEspacioColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RGB", "YCC", "GREY" }));
        seleccionEspacioColor.setToolTipText("Espacio de Color");
        seleccionEspacioColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionEspacioColorActionPerformed(evt);
            }
        });
        panelColor.add(seleccionEspacioColor, java.awt.BorderLayout.PAGE_END);

        panelImagenOp.add(panelColor);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        botonTintado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/tintar.png"))); // NOI18N
        botonTintado.setToolTipText("Tintado");
        botonTintado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTintadoActionPerformed(evt);
            }
        });
        jPanel4.add(botonTintado);

        botonSepia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/sepia.png"))); // NOI18N
        botonSepia.setToolTipText("Sepia");
        botonSepia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSepiaActionPerformed(evt);
            }
        });
        jPanel4.add(botonSepia);

        botonEcualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/ecualizar.png"))); // NOI18N
        botonEcualizar.setToolTipText("Ecualizar");
        botonEcualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEcualizarActionPerformed(evt);
            }
        });
        jPanel4.add(botonEcualizar);

        botonRojo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icono/rojo.png"))); // NOI18N
        botonRojo.setToolTipText("Resaltado Rojo");
        botonRojo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRojoActionPerformed(evt);
            }
        });
        jPanel4.add(botonRojo);

        sliderPosterizar.setMaximum(20);
        sliderPosterizar.setMinimum(2);
        sliderPosterizar.setToolTipText("Slider Posterizar");
        sliderPosterizar.setPreferredSize(new java.awt.Dimension(80, 20));
        sliderPosterizar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderPosterizarStateChanged(evt);
            }
        });
        sliderPosterizar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderPosterizarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderPosterizarFocusLost(evt);
            }
        });
        jPanel4.add(sliderPosterizar);

        sliderTono.setMaximum(360);
        sliderTono.setToolTipText("Slider Tono");
        sliderTono.setPreferredSize(new java.awt.Dimension(80, 20));
        sliderTono.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderTonoStateChanged(evt);
            }
        });
        sliderTono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderTonoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderTonoFocusLost(evt);
            }
        });
        jPanel4.add(sliderTono);

        botonNuevaOp.setText("NuevaOp");
        botonNuevaOp.setToolTipText("NuevaOp");
        botonNuevaOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevaOpActionPerformed(evt);
            }
        });
        jPanel4.add(botonNuevaOp);

        spinnerParametroNuevaOp.setToolTipText("Parámetro NuevaOp");
        spinnerParametroNuevaOp.setValue(1);
        jPanel4.add(spinnerParametroNuevaOp);

        panelImagenOp.add(jPanel4);

        jPanel1.add(panelImagenOp, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jSlider2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        menuArchivo.setText("Archivo");

        menuItemNuevo.setText("Nuevo");
        menuItemNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemNuevoActionPerformed(evt);
            }
        });
        menuArchivo.add(menuItemNuevo);

        menuItemAbrir.setText("Abrir");
        menuItemAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAbrirActionPerformed(evt);
            }
        });
        menuArchivo.add(menuItemAbrir);

        menuItemGuardar.setText("Guardar");
        menuItemGuardar.setActionCommand("menuItemGuardar");
        menuItemGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemGuardarActionPerformed(evt);
            }
        });
        menuArchivo.add(menuItemGuardar);

        jMenuItem1.setText("Abrir Audio");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuArchivo.add(jMenuItem1);

        jMenuItem2.setText("Grabar Audio");
        menuArchivo.add(jMenuItem2);

        jMenuBar1.add(menuArchivo);

        menuImagen.setText("Imagen");

        menuItemRescale.setText("RescaleOp");
        menuItemRescale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRescaleActionPerformed(evt);
            }
        });
        menuImagen.add(menuItemRescale);

        menuItemConvolve.setText("ConvolveOp");
        menuItemConvolve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemConvolveActionPerformed(evt);
            }
        });
        menuImagen.add(menuItemConvolve);

        menuItemAffineTransform.setText("AffineTransformOp");
        menuItemAffineTransform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAffineTransformActionPerformed(evt);
            }
        });
        menuImagen.add(menuItemAffineTransform);

        menuItemLookup.setText("LookupOp");
        menuItemLookup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLookupActionPerformed(evt);
            }
        });
        menuImagen.add(menuItemLookup);

        BandCombineOp.setText("BandCombineOp");
        BandCombineOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BandCombineOpActionPerformed(evt);
            }
        });
        menuImagen.add(BandCombineOp);

        ColorConvertOp.setText("ColorConvertOp");
        ColorConvertOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ColorConvertOpActionPerformed(evt);
            }
        });
        menuImagen.add(ColorConvertOp);

        jMenuBar1.add(menuImagen);

        menuAyuda.setText("Ayuda");

        itemAcercaDe.setText("Acerca de");
        itemAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAcercaDeActionPerformed(evt);
            }
        });
        menuAyuda.add(itemAcercaDe);

        jMenuBar1.add(menuAyuda);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void colorNegroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorNegroActionPerformed
        getLienzoSeleccionado().setColor(Color.black);
    }//GEN-LAST:event_colorNegroActionPerformed

    private void colorRojoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorRojoActionPerformed
        getLienzoSeleccionado().setColor(Color.red);
    }//GEN-LAST:event_colorRojoActionPerformed

    private void colorAzulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorAzulActionPerformed
        getLienzoSeleccionado().setColor(Color.blue);
    }//GEN-LAST:event_colorAzulActionPerformed

    private void colorAmarilloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorAmarilloActionPerformed
        getLienzoSeleccionado().setColor(Color.yellow);
    }//GEN-LAST:event_colorAmarilloActionPerformed

    private void colorVerdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorVerdeActionPerformed
        getLienzoSeleccionado().setColor(Color.green);
    }//GEN-LAST:event_colorVerdeActionPerformed

    private void menuItemAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAbrirActionPerformed
        this.botonAbrirActionPerformed(evt);
    }//GEN-LAST:event_menuItemAbrirActionPerformed

    private void menuItemGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemGuardarActionPerformed
        this.botonGuardarActionPerformed(evt);
    }//GEN-LAST:event_menuItemGuardarActionPerformed

    private void menuItemNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemNuevoActionPerformed
        this.botonNuevoActionPerformed(evt);
    }//GEN-LAST:event_menuItemNuevoActionPerformed

    private void botonLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLineaActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            getLienzoSeleccionado().setPincel(0);
        }
    }//GEN-LAST:event_botonLineaActionPerformed

    private void botonRectanguloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRectanguloActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            getLienzoSeleccionado().setPincel(1);
        }
    }//GEN-LAST:event_botonRectanguloActionPerformed

    private void botonElipseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonElipseActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            getLienzoSeleccionado().setPincel(2);
        }
    }//GEN-LAST:event_botonElipseActionPerformed

    private void botonTrazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTrazoActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            getLienzoSeleccionado().setPincel(3);
        }
    }//GEN-LAST:event_botonTrazoActionPerformed

    private void botonCurvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCurvaActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            getLienzoSeleccionado().setPincel(4);
        }
    }//GEN-LAST:event_botonCurvaActionPerformed

    private void botonCaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCaraActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            getLienzoSeleccionado().setPincel(5);
        }
    }//GEN-LAST:event_botonCaraActionPerformed

    private void spinnerTrazoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerTrazoStateChanged
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            getLienzoSeleccionado().setTrazo((int) spinnerTrazo.getValue(),tipoTrazo.getSelectedIndex());
            getLienzoSeleccionado().repaint();
        }
    }//GEN-LAST:event_spinnerTrazoStateChanged

    private void botonTransparenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTransparenciaActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            getLienzoSeleccionado().setTransparencia(botonTransparencia.isSelected(), (float)sliderGradoTransparencia.getValue());
            getLienzoSeleccionado().repaint();
        }
    }//GEN-LAST:event_botonTransparenciaActionPerformed

    private void botonSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSeleccionarActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            getLienzoSeleccionado().setMover(botonSeleccionar.isSelected());
            //Cambiamos cursor al seleccionar:
            if (botonSeleccionar.isSelected()) {
                getLienzoSeleccionado().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            } else {
                getLienzoSeleccionado().setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_botonSeleccionarActionPerformed

    private void botonRellenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRellenoActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            getLienzoSeleccionado().setRelleno(botonRelleno.isSelected());
            getLienzoSeleccionado().repaint();
        }
    }//GEN-LAST:event_botonRellenoActionPerformed

    private void botonAntialiasingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAntialiasingActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            getLienzoSeleccionado().setAntialiasing(botonAntialiasing.isSelected());
            getLienzoSeleccionado().repaint();
        }
    }//GEN-LAST:event_botonAntialiasingActionPerformed

    private void dialogoColoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dialogoColoresMouseClicked
        Color color = JColorChooser.showDialog(this, "Elije un color", Color.RED);
        getLienzoSeleccionado().setColor(color);
    }//GEN-LAST:event_dialogoColoresMouseClicked

    private void botonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevoActionPerformed
        JTextField altura = new JTextField(), anchura = new JTextField();
        int altura_ = 0, anchura_ = 0;

        Object[] message = {
            "Altura (mínimo 300):", altura,
            "Anchura (mínimo 300):", anchura
        };
        
        boolean introducido_correctamente = false;

        int option = JOptionPane.showConfirmDialog(null, message, "Altura y anchura", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                altura_ = Integer.parseInt(altura.getText());
                anchura_ = Integer.parseInt(anchura.getText());
                if (altura_ >= 300 && anchura_ >= 300) {
                    introducido_correctamente = true;
                }
                else{
                    JOptionPane.showMessageDialog(null, "Por favor, introduce tamaños válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, introduce tamaños válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
     
        if (introducido_correctamente) {
            VentanaInterna vi = new VentanaInterna();
            vi.setSize(anchura_, altura_);
            vi.setTitle("Nueva");
            escritorio.add(vi);
            vi.setVisible(true);
            BufferedImage img;
            img = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
            img.getGraphics().setColor(Color.white);
            img.getGraphics().fillRect(0, 0, 300, 300);
            vi.getLienzo().setImage(img);

            //Creamos objeto manejador y enlazamos (parte opcional P7)
            vi.addInternalFrameListener(manejador);

            //P9
            vi.getLienzo().addLienzoListener(new ManejadorLienzo());
            vi.addInternalFrameListener(new ManejadorVentanaInterna());
        }
    }//GEN-LAST:event_botonNuevoActionPerformed

    private void botonAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAbrirActionPerformed
        //Filtro mostrar solo archivos JPG, JPEG, PNG, WAV, AU, MP4, MPG y AVI
        FileFilter filter = new FileNameExtensionFilter("Archivos JPEG, JPG, PNG, WAV, AU, MP4, MPG Y AVI", "jpg", "jpeg", "png", "wav", "au", "mp4", "mpg", "avi");
        JFileChooser dlg = new JFileChooser();
        dlg.setFileFilter(filter);
        int resp = dlg.showOpenDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {
            try {
                File f = new File(dlg.getSelectedFile().getAbsolutePath()) {
                    @Override
                    public String toString() {
                        return this.getName();
                    }
                };

                String file = f.toString();
                int posicion_punto = file.indexOf(".");
                String file_nuevo = "";
                for (int i = posicion_punto + 1; i < file.length(); i++) {
                    file_nuevo += file.charAt(i);
                }
                if (file_nuevo.equalsIgnoreCase("jpg") || file_nuevo.equalsIgnoreCase("jpeg") || file_nuevo.equalsIgnoreCase("png")) {
                    BufferedImage img = ImageIO.read(f);
                    VentanaInterna vi = new VentanaInterna();
                    vi.getLienzo().setImage(img);
                    vi.setTitle(file);
                    this.escritorio.add(vi);
                    vi.setTitle(f.getName());
                    vi.setVisible(true);
                    vi.getLienzo().addLienzoListener(new ManejadorLienzo());
                    vi.addInternalFrameListener(new ManejadorVentanaInterna());
                } else if (file_nuevo.equalsIgnoreCase("wav") || file_nuevo.equalsIgnoreCase("au")) {
                    listaReproduccion.addItem(f);
                }
                else{
                    VentanaInternaVideo vv = VentanaInternaVideo.getInstance(f);
                    vv.addMediaPlayerEventListener(new VideoListener());
                    vv.setTitle(file);
                    escritorio.add(vv);
                    vv.setVisible(true);
                }
            } catch (Exception ex) {
                System.err.println("Error al leer el archivo");
            }
        }
    }//GEN-LAST:event_botonAbrirActionPerformed

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
              
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage(true);
            if (img != null) {
                //Filtro mostrar solo archivos JPG, JPEG, PNG
                FileFilter filter = new FileNameExtensionFilter("Archivos JPEG, JPG, PNG", "jpg", "jpeg", "png");
                JFileChooser dlg = new JFileChooser();
                dlg.setFileFilter(filter);
                int resp = dlg.showSaveDialog(this);
                if (resp == JFileChooser.APPROVE_OPTION) {
                    try {
                        File f = dlg.getSelectedFile();
                        ImageIO.write(img, "jpg", f);
                        vi.setTitle(f.getName());
                    } catch (Exception ex) {
                        System.err.println("Error al guardar la imagen");
                    }
                }
            }
        }
    }//GEN-LAST:event_botonGuardarActionPerformed

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        barraEstado.setText("Coordenadas escritorio X: " + evt.getLocationOnScreen().getX() + "Y: " + evt.getPoint().getY());
    }//GEN-LAST:event_formMouseMoved

    private void menuItemRescaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRescaleActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    RescaleOp rop = new RescaleOp(1.0F, 100.0F, null);
                    rop.filter(img, img);
                    vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_menuItemRescaleActionPerformed

    private void sliderBrilloFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderBrilloFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            ColorModel cm = vi.getLienzo().getImage().getColorModel();
            WritableRaster raster = vi.getLienzo().getImage().copyData(null);
            boolean alfaPre = vi.getLienzo().getImage().isAlphaPremultiplied();
            imgFuente = new BufferedImage(cm, raster, alfaPre, null);
        }
    }//GEN-LAST:event_sliderBrilloFocusGained

    private void sliderBrilloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderBrilloFocusLost
        imgFuente = null;
        this.sliderBrillo.setValue(0);
    }//GEN-LAST:event_sliderBrilloFocusLost

    private void sliderBrilloStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderBrilloStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null && imgFuente != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    int brillo = this.sliderBrillo.getValue();
                    RescaleOp rop = new RescaleOp(1.0F, brillo, null);
                    rop.filter(imgFuente, img);
                    escritorio.repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_sliderBrilloStateChanged

    private void menuItemConvolveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemConvolveActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    float filtro[] = {0.1f, 0.1f, 0.1f, 0.1f, 0.2f, 0.1f, 0.1f, 0.1f, 0.1f};
                    Kernel k = new Kernel(3, 3, filtro);
                    ConvolveOp cop = new ConvolveOp(k);

                    BufferedImage imgdest = cop.filter(img, null);
                    vi.getLienzo().setImage(imgdest);

                    vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_menuItemConvolveActionPerformed

    private void seleccionMascaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionMascaraActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            int seleccion = this.seleccionMascara.getSelectedIndex();
            Kernel k = getKernel(seleccion);
            if (img != null && k != null) {
                try {

                    ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);

                    BufferedImage imgdest = cop.filter(img, null);
                    vi.getLienzo().setImage(imgdest);

                    vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_seleccionMascaraActionPerformed

    private void sliderContrasteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderContrasteStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null && imgFuente != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    float contraste = this.sliderContraste.getValue();
                    contraste /= 10;
                    RescaleOp rop = new RescaleOp(contraste, 0.0f, null);
                    rop.filter(imgFuente, img);
                    vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_sliderContrasteStateChanged

    private void sliderContrasteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderContrasteFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            ColorModel cm = vi.getLienzo().getImage().getColorModel();
            WritableRaster raster = vi.getLienzo().getImage().copyData(null);
            boolean alfaPre = vi.getLienzo().getImage().isAlphaPremultiplied();
            imgFuente = new BufferedImage(cm, raster, alfaPre, null);
        }
    }//GEN-LAST:event_sliderContrasteFocusGained

    private void sliderContrasteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderContrasteFocusLost
        imgFuente = null;
        this.sliderBrillo.setValue(0);
    }//GEN-LAST:event_sliderContrasteFocusLost

    private void menuItemAffineTransformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAffineTransformActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    AffineTransform at = AffineTransform.getScaleInstance(1.25, 1.25);
                    AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(img, null);
                    vi.getLienzo().setImage(imgdest);
                    vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_menuItemAffineTransformActionPerformed

    private void menuItemLookupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemLookupActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    byte funcionT[] = new byte[256];
                    for (int x = 0; x < 256; x++) {
                        funcionT[x] = (byte) (255 - x); // Negativo
                    }
                    LookupTable tabla = new ByteLookupTable(0, funcionT);
                    LookupOp lop = new LookupOp(tabla, null);
                    lop.filter(img, img);
                    vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_menuItemLookupActionPerformed

    private void contrasteNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrasteNormalActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {

                    LookupTable lt = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_SFUNCION);
                    LookupOp lop = new LookupOp(lt, null);
                    lop.filter(img, img); // Imagen origen y destino iguales
                    vi.getLienzo().repaint();
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_contrasteNormalActionPerformed

    private void contrasteIluminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrasteIluminarActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {

                    LookupTable lt = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_GAMMA_CORRECTION);
                    LookupOp lop = new LookupOp(lt, null);
                    lop.filter(img, img); // Imagen origen y destino iguales
                    vi.getLienzo().repaint();
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_contrasteIluminarActionPerformed

    private void contrasteOscurecerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrasteOscurecerActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {

                    LookupTable lt = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_POWER);
                    LookupOp lop = new LookupOp(lt, null);
                    lop.filter(img, img); // Imagen origen y destino iguales
                    vi.getLienzo().repaint();
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_contrasteOscurecerActionPerformed

    private void rotacion180ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotacion180ActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(180), img.getWidth() / 2, img.getHeight() / 2);
                    AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(img, null);
                    vi.getLienzo().setImage(imgdest);
                    vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_rotacion180ActionPerformed

    private void aumentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aumentarActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    AffineTransform at = AffineTransform.getScaleInstance(1.25, 1.25);
                    AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(img, null);
                    vi.getLienzo().setImage(imgdest);
                    vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_aumentarActionPerformed

    private void disminuirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disminuirActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    AffineTransform at = AffineTransform.getScaleInstance(0.75, 0.75);
                    AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(img, null);
                    vi.getLienzo().setImage(imgdest);
                    vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_disminuirActionPerformed

    private void cuadraticaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuadraticaActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    LookupTable lt = cuadratica(m);
                    LookupOp lop = new LookupOp(lt, null);
                    lop.filter(img, img); // Imagen origen y destino iguales
                    vi.getLienzo().repaint();
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_cuadraticaActionPerformed

    private void linealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linealActionPerformed
        if (!lineal.isSelected()) {
            imgFuente = null;
            this.sliderBrillo.setValue(0);
            sliderA.setEnabled(false);
            sliderB.setEnabled(false);
        } else {
            VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
            if (vi != null) {
                ColorModel cm = vi.getLienzo().getImage().getColorModel();
                WritableRaster raster = vi.getLienzo().getImage().copyData(null);
                boolean alfaPre = vi.getLienzo().getImage().isAlphaPremultiplied();
                imgFuente = new BufferedImage(cm, raster, alfaPre, null);
            }
            sliderA.setEnabled(true);
            sliderB.setEnabled(true);
        }
    }//GEN-LAST:event_linealActionPerformed

    private void sliderAStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderAStateChanged
        AccionSliderLineal((double) sliderA.getValue(), (double) sliderB.getValue());
    }//GEN-LAST:event_sliderAStateChanged

    private void sliderBStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderBStateChanged
        AccionSliderLineal((double) sliderA.getValue(), (double) sliderB.getValue());
    }//GEN-LAST:event_sliderBStateChanged

    private void volcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volcarActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null && lista.getSelectedValuesList() != null) {
            vi.getLienzo().setSeleccionados(lista.getSelectedValuesList());

            DefaultListModel<figuras> modelo = (DefaultListModel<figuras>) lista.getModel();
            int[] indicesSeleccionados = lista.getSelectedIndices();
            for (int i = indicesSeleccionados.length - 1; i >= 0; i--) {
                modelo.removeElementAt(indicesSeleccionados[i]);
            }
            lista.updateUI();

            BufferedImage img = vi.getLienzo().getImage(true);
            vi.getLienzo().setImage(img);
        }
    }//GEN-LAST:event_volcarActionPerformed

    private void botonNegativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNegativoActionPerformed
        menuItemLookupActionPerformed(evt);
    }//GEN-LAST:event_botonNegativoActionPerformed

    private void sliderMStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderMStateChanged
        m = sliderM.getValue();
    }//GEN-LAST:event_sliderMStateChanged

    private void botonDuplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDuplicarActionPerformed
        VentanaInterna va  = (VentanaInterna) (escritorio.getSelectedFrame());
        if (va  != null) {
            VentanaInterna vi = new VentanaInterna();
            vi.setVisible(true);
            BufferedImage img = va.getLienzo().getImage(true);
            vi.getLienzo().setImage(img);
            vi.addInternalFrameListener(manejador);
            vi.getLienzo().addLienzoListener(new ManejadorLienzo());
            vi.addInternalFrameListener(new ManejadorVentanaInterna());
            vi.setTitle("Duplicado");
            escritorio.add(vi);
        }
    }//GEN-LAST:event_botonDuplicarActionPerformed

    private void BandCombineOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BandCombineOpActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    float[][] matriz = {{1.0F, 0.0F, 0.0F},
                    {0.0F, 0.0F, 1.0F},
                    {0.0F, 1.0F, 0.0F}};
                    BandCombineOp bcop = new BandCombineOp(matriz, null);
                    bcop.filter(img.getRaster(), img.getRaster());
                    vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_BandCombineOpActionPerformed

    private void ColorConvertOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ColorConvertOpActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                    ColorConvertOp op = new ColorConvertOp(cs, null);
                    BufferedImage imgdest = op.filter(img, null);
                    vi.getLienzo().setImage(imgdest);
                    vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_ColorConvertOpActionPerformed

    private void botonCombinacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCombinacionActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    float[][] matriz = {{0.0F, 0.5F, 0.5F},
                    {0.5F, 0.0F, 0.5F},
                    {0.5F, 0.5F, 0.0F}};
                    BandCombineOp bcop = new BandCombineOp(matriz, null);
                    bcop.filter(img.getRaster(), img.getRaster());
                    vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonCombinacionActionPerformed

    private void botonExtraccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonExtraccionActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                for (int i = 0; i < img.getRaster().getNumBands(); i++) {
                    BufferedImage imgbanda = getImageBand(img, i);
                    vi = new VentanaInterna();
                    vi.getLienzo().setImage(imgbanda);
                    vi.setTitle("Banda " + i);
                    escritorio.add(vi);
                    vi.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_botonExtraccionActionPerformed

    private void seleccionEspacioColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionEspacioColorActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                ColorSpace cs = null;
                int seleccion = this.seleccionEspacioColor.getSelectedIndex();
                switch (seleccion) {
                    case 0:
                        cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                        break;
                    case 1:
                        cs = ColorSpace.getInstance(ColorSpace.CS_PYCC);
                        break;
                    case 2:
                        cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                        break;
                }
                try {
                    ColorConvertOp op = new ColorConvertOp(cs, null);
                    BufferedImage imgdest = op.filter(img, null);
                    vi = new VentanaInterna();
                    vi.getLienzo().setImage(imgdest);
                    switch (seleccion) {
                        case 0:
                            vi.setTitle("RGB");
                            break;
                        case 1:
                            vi.setTitle("YCC");
                            break;
                        default:
                            vi.setTitle("GRAY");
                            break;
                    }
                    escritorio.add(vi);
                    vi.setVisible(true);
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_seleccionEspacioColorActionPerformed

    private void botonTintadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTintadoActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage(false);
            try {
                TintOp tintado = new TintOp(vi.getLienzo().getColor(), 0.5f);
                tintado.filter(img, img);
                vi.getLienzo().repaint();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_botonTintadoActionPerformed

    private void botonSepiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSepiaActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage(false);
            try {
                SepiaOp sepia = new SepiaOp();
                sepia.filter(img, img);
                vi.getLienzo().repaint();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_botonSepiaActionPerformed

    private void botonEcualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEcualizarActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage(false);
            try {
                EqualizationOp ecualizacion = new EqualizationOp();
                ecualizacion.filter(img, img);
                vi.getLienzo().repaint();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_botonEcualizarActionPerformed

    private void botonRojoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRojoActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage(false);
            try {
                RojoOp rojo = new RojoOp(30);
                rojo.filter(img, img);
                vi.getLienzo().repaint();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_botonRojoActionPerformed

    private void sliderPosterizarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderPosterizarFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            ColorModel cm = vi.getLienzo().getImage().getColorModel();
            WritableRaster raster = vi.getLienzo().getImage().copyData(null);
            boolean alfaPre = vi.getLienzo().getImage().isAlphaPremultiplied();
            imgFuente = new BufferedImage(cm, raster, alfaPre, null);
        }
    }//GEN-LAST:event_sliderPosterizarFocusGained

    private void sliderPosterizarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderPosterizarFocusLost
        imgFuente = null;
        this.sliderBrillo.setValue(0);
    }//GEN-LAST:event_sliderPosterizarFocusLost

    private void sliderPosterizarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderPosterizarStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage(false);
            try {
                PosterizarOp posterizar = new PosterizarOp(sliderPosterizar.getValue());
                posterizar.filter(imgFuente, img);
                vi.getLienzo().repaint();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_sliderPosterizarStateChanged

    private void sliderTonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderTonoFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            ColorModel cm = vi.getLienzo().getImage().getColorModel();
            WritableRaster raster = vi.getLienzo().getImage().copyData(null);
            boolean alfaPre = vi.getLienzo().getImage().isAlphaPremultiplied();
            imgFuente = new BufferedImage(cm, raster, alfaPre, null);
        }
    }//GEN-LAST:event_sliderTonoFocusGained

    private void sliderTonoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderTonoFocusLost
        imgFuente = null;
        this.sliderBrillo.setValue(0);
    }//GEN-LAST:event_sliderTonoFocusLost

    private void sliderTonoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderTonoStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null && imgFuente != null) {
            BufferedImage img = vi.getLienzo().getImage();
            if (img != null) {
                try {
                    TonoOp tono = new TonoOp(sliderTono.getValue());
                    tono.filter(imgFuente, img);
                    escritorio.repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_sliderTonoStateChanged

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        FileFilter filter = new FileNameExtensionFilter("Archivos WAV y AU", "wav", "au");
        JFileChooser dlg = new JFileChooser();
        dlg.setFileFilter(filter);
        int resp = dlg.showOpenDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {
            try {
                File f = new File(dlg.getSelectedFile().getAbsolutePath()) {
                    @Override
                    public String toString() {
                        return this.getName();
                    }
                };
                listaReproduccion.addItem(f);
            } catch (Exception ex) {
                System.err.println("Error al leer la imagen");
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void botonReproducirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReproducirActionPerformed
        if (escritorio.getSelectedFrame() != null && escritorio.getSelectedFrame().getClass() == VentanaInternaVideo.class) {
            VentanaInternaVideo vv = (VentanaInternaVideo) escritorio.getSelectedFrame();
            if (vv != null) {
                vv.play();
            }
        } else {
            File f = (File) listaReproduccion.getSelectedItem();
            if (f != null) {
                player = new SMClipPlayer(f);
                if (player != null) {
                    player.addLineListener(new ManejadorAudio());
                    player.play();
                }
            }
        }
    }//GEN-LAST:event_botonReproducirActionPerformed

    private void botonPausarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPausarActionPerformed
        if (escritorio.getSelectedFrame() != null && escritorio.getSelectedFrame().getClass() == VentanaInternaVideo.class) {
            VentanaInternaVideo vv = (VentanaInternaVideo)escritorio.getSelectedFrame();
            if(vv!= null){
                vv.stop();
            }
        }
        else {
            if (player != null) {
                player.stop();
                player = null;
            }
            if (recorder != null) {
                recorder.stop();
                recorder = null;
            }
        }
    }//GEN-LAST:event_botonPausarActionPerformed

    private void botonGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGrabarActionPerformed
        JFileChooser dlg = new JFileChooser();
        int resp = dlg.showSaveDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {
            try {
                File f = dlg.getSelectedFile();
                recorder = new SMSoundRecorder(f);
                if (recorder != null) {
                    recorder.record();
                }
            } catch (Exception ex) {
                System.err.println("Error al guardar el sonido");
            }
        }
    }//GEN-LAST:event_botonGrabarActionPerformed

    private void botonCamaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCamaraActionPerformed
        VentanaInternaCamara vc = VentanaInternaCamara.getInstance();
        if (vc != null) {
            vc.setTitle("Cámara");
            escritorio.add(vc);
            vc.setVisible(true);
        }
    }//GEN-LAST:event_botonCamaraActionPerformed

    private void botonCapturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCapturaActionPerformed
        VentanaInternaCamara vc = (VentanaInternaCamara) escritorio.getSelectedFrame();
        if (vc != null) {
            BufferedImage img = vc.getImage();
            VentanaInterna vi = new VentanaInterna();
            vi.getLienzo().setImage(img);
            vi.setTitle("Captura");
            escritorio.add(vi);
            vi.setVisible(true);
        }
    }//GEN-LAST:event_botonCapturaActionPerformed

    private void itemAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAcercaDeActionPerformed
        String title = "Práctica Sistemas Multimedia";
        String message = "Aplicación: Paint\nAutor: Mario Guisado García\nVersión: 2023";
        
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
        
    }//GEN-LAST:event_itemAcercaDeActionPerformed

    private void tipoTrazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoTrazoActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            getLienzoSeleccionado().setTrazo((int) spinnerTrazo.getValue(), tipoTrazo.getSelectedIndex());
            getLienzoSeleccionado().repaint();
        }
    }//GEN-LAST:event_tipoTrazoActionPerformed

    private void sliderGradoTransparenciaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderGradoTransparenciaStateChanged
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            getLienzoSeleccionado().setTransparencia(botonTransparencia.isSelected(), (float) sliderGradoTransparencia.getValue());
            getLienzoSeleccionado().repaint();
        }
    }//GEN-LAST:event_sliderGradoTransparenciaStateChanged

    private void sliderRotacionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderRotacionStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            if (imgFuente != null) {
                try {
                    AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(sliderRotacion.getValue()), imgFuente.getWidth() / 2, imgFuente.getHeight() / 2);
                    AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(imgFuente, null);
                    vi.getLienzo().setImage(imgdest);
                    vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_sliderRotacionStateChanged

    private void sliderRotacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderRotacionFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            imgFuente = vi.getLienzo().getImage();
        }
    }//GEN-LAST:event_sliderRotacionFocusGained

    private void sliderRotacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderRotacionFocusLost
        this.imgFuente = null;
        this.sliderRotacion.setValue(0);
    }//GEN-LAST:event_sliderRotacionFocusLost

    private void sliderLookupPropioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderLookupPropioStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            if (imgFuente != null) {
                try {
                    byte funcionT[] = new byte[256];
                    for (int x = 0; x < 256; x++) {
                        if (x <= 128) {
                            funcionT[x] = (byte) sqrt(-x + 128);
                        } else {
                            funcionT[x] = (byte) ((byte) (pow((x - 128), 2)) / (float) sliderLookupPropio.getValue());
                        }
                    }
                    LookupTable tabla = new ByteLookupTable(0, funcionT);
                    LookupOp lop = new LookupOp(tabla, null);
                    BufferedImage imgdest = lop.filter(imgFuente, null);
                    vi.getLienzo().setImage(imgdest);
                    vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_sliderLookupPropioStateChanged

    private void sliderLookupPropioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderLookupPropioFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            imgFuente = vi.getLienzo().getImage();
        }
    }//GEN-LAST:event_sliderLookupPropioFocusGained

    private void sliderLookupPropioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderLookupPropioFocusLost
        this.imgFuente = null;
        this.sliderLookupPropio.setValue(1);
    }//GEN-LAST:event_sliderLookupPropioFocusLost

    private void botonNuevaOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevaOpActionPerformed
         VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImage(false);
            try {
                NuevaOp nueva = new NuevaOp((int)spinnerParametroNuevaOp.getValue());
                nueva.filter(img, img);
                vi.getLienzo().repaint();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_botonNuevaOpActionPerformed
    private class ManejadorVentanaInterna extends InternalFrameAdapter {

        public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            VentanaInterna vi = (VentanaInterna) evt.getInternalFrame();
            botonRelleno.setSelected(vi.getLienzo().getRelleno());
            botonTransparencia.setSelected(vi.getLienzo().getTransparencia());
            spinnerTrazo.setValue(vi.getLienzo().getGrosorTrazo());
            botonAntialiasing.setSelected(vi.getLienzo().getAntialiasing());
            botonSeleccionar.setSelected(vi.getLienzo().getMover());

            botonLinea.setSelected(vi.getLienzo().getLinea());
            botonRectangulo.setSelected(vi.getLienzo().getRectangulo());
            botonElipse.setSelected(vi.getLienzo().getElipse());
            botonCurva.setSelected(vi.getLienzo().getCurva());
            botonTrazo.setSelected(vi.getLienzo().getTrazo());
            botonCara.setSelected(vi.getLienzo().getCara());

            DefaultListModel modelo = new DefaultListModel();
            modelo.addAll(vi.getLienzo().getShapeList());
            lista.setModel(modelo);
        }

        public void internalFrameClosing(InternalFrameEvent evt) {
            ((DefaultListModel) lista.getModel()).removeAllElements();
        }
    }

    public class ManejadorLienzo extends LienzoAdapter {

        public void shapeAdded(LienzoEvent evt) {
            Shape s = evt.getForma();
            ((DefaultListModel) lista.getModel()).addElement(s);
        }
    }

    public static LookupTable cuadratica(double m) {
        double Max;
        if (m >= 128) {
            Max = ((1.0 / 100.0) * Math.pow((0 - m), 2));
        } else {
            Max = ((1.0 / 100.0) * Math.pow((255 - m), 2));
        }
        double K = 255.0 / Max;
        byte lt[] = new byte[256];
        for (int l = 0; l < 256; l++) {
            lt[l] = (byte) (K * ((1.0 / 100.0) * Math.pow(((double) l - m), 2)));
        }
        ByteLookupTable slt = new ByteLookupTable(0, lt);
        return slt;
    }

    /*
    public class MiManejadorLienzo extends LienzoAdapter {

        public void shapeAdded(LienzoEvent evt) {
            System.out.println("Figura"+evt.getForma() +" añadida ");
        }
    }
     */
    class ManejadorAudio implements LineListener {

        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.START) {
                botonReproducir.setEnabled(false);
            }
            if (event.getType() == LineEvent.Type.STOP) {
                botonReproducir.setEnabled(true);
            }
            if (event.getType() == LineEvent.Type.CLOSE) {
            }
        }
    }
    
    private class VideoListener extends MediaPlayerEventAdapter {

        public void playing(MediaPlayer mediaPlayer) {
            botonPausar.setEnabled(true);
            botonReproducir.setEnabled(false);
        }

        public void paused(MediaPlayer mediaPlayer) {
            botonPausar.setEnabled(false);
            botonReproducir.setEnabled(true);
        }

        public void finished(MediaPlayer mediaPlayer) {
            this.paused(mediaPlayer);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem BandCombineOp;
    private javax.swing.JMenuItem ColorConvertOp;
    private javax.swing.JButton aumentar;
    private javax.swing.JLabel barraEstado;
    private javax.swing.JButton botonAbrir;
    private javax.swing.JToggleButton botonAntialiasing;
    private javax.swing.JButton botonCamara;
    private javax.swing.JButton botonCaptura;
    private javax.swing.JToggleButton botonCara;
    private javax.swing.JButton botonCombinacion;
    private javax.swing.JToggleButton botonCurva;
    private javax.swing.JButton botonDuplicar;
    private javax.swing.JButton botonEcualizar;
    private javax.swing.JToggleButton botonElipse;
    private javax.swing.JButton botonExtraccion;
    private javax.swing.JButton botonGrabar;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JToggleButton botonLinea;
    private javax.swing.JButton botonNegativo;
    private javax.swing.JButton botonNuevaOp;
    private javax.swing.JButton botonNuevo;
    private javax.swing.JButton botonPausar;
    private javax.swing.JToggleButton botonRectangulo;
    private javax.swing.JToggleButton botonRelleno;
    private javax.swing.JButton botonReproducir;
    private javax.swing.JButton botonRojo;
    private javax.swing.JToggleButton botonSeleccionar;
    private javax.swing.JButton botonSepia;
    private javax.swing.JButton botonTintado;
    private javax.swing.JToggleButton botonTransparencia;
    private javax.swing.JToggleButton botonTrazo;
    private javax.swing.JToggleButton colorAmarillo;
    private javax.swing.JToggleButton colorAzul;
    private javax.swing.JToggleButton colorNegro;
    private javax.swing.JToggleButton colorRojo;
    private javax.swing.JToggleButton colorVerde;
    private javax.swing.JButton contrasteIluminar;
    private javax.swing.JButton contrasteNormal;
    private javax.swing.JButton contrasteOscurecer;
    private javax.swing.JButton cuadratica;
    private javax.swing.JLabel dialogoColores;
    private javax.swing.JButton disminuir;
    private javax.swing.JDesktopPane escritorio;
    private javax.swing.ButtonGroup grupoColor;
    private javax.swing.ButtonGroup grupoPincel;
    private javax.swing.JMenuItem itemAcercaDe;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToggleButton lineal;
    private javax.swing.JList<figuras> lista;
    private javax.swing.JComboBox<File> listaReproduccion;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenu menuImagen;
    private javax.swing.JMenuItem menuItemAbrir;
    private javax.swing.JMenuItem menuItemAffineTransform;
    private javax.swing.JMenuItem menuItemConvolve;
    private javax.swing.JMenuItem menuItemGuardar;
    private javax.swing.JMenuItem menuItemLookup;
    private javax.swing.JMenuItem menuItemNuevo;
    private javax.swing.JMenuItem menuItemRescale;
    private javax.swing.JPanel panelBrillo;
    private javax.swing.JPanel panelColor;
    private javax.swing.JPanel panelContraste;
    private javax.swing.JPanel panelFiltros;
    private javax.swing.JPanel panelImagenOp;
    private javax.swing.JPanel panelLineal;
    private javax.swing.JButton rotacion180;
    private javax.swing.JComboBox<String> seleccionEspacioColor;
    private javax.swing.JComboBox<String> seleccionMascara;
    private javax.swing.JSlider sliderA;
    private javax.swing.JSlider sliderB;
    private javax.swing.JSlider sliderBrillo;
    private javax.swing.JSlider sliderContraste;
    private javax.swing.JSlider sliderGradoTransparencia;
    private javax.swing.JSlider sliderLookupPropio;
    private javax.swing.JSlider sliderM;
    private javax.swing.JSlider sliderPosterizar;
    private javax.swing.JSlider sliderRotacion;
    private javax.swing.JSlider sliderTono;
    private javax.swing.JSpinner spinnerParametroNuevaOp;
    private javax.swing.JSpinner spinnerTrazo;
    private javax.swing.JComboBox<String> tipoTrazo;
    private javax.swing.JButton volcar;
    // End of variables declaration//GEN-END:variables
}
