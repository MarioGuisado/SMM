/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.mgg.image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;
import sm.image.BufferedImageOpAdapter;

/**
 * Clase de diseño propio para llevar a cabo la nueva operación pixel a pixel
 * @author Mario Guisado García
 */
public class NuevaOp extends BufferedImageOpAdapter {
    /**
     * Dato de tipo int que almacenará la aleatoriedad introducida por el usuario en las transformaciones realizadas.
     */
    private int aleatoriedad;
    
    /**
     * Constructor de la clase, inicializa el dato privado correspondiente a la aleatoriedad.
     * @param valor Dato int facilitado para inicializar el valor de la aleatoriedad.
     */
    public NuevaOp(int valor) {
        this.aleatoriedad = valor;
    }
    /**
     * Método para realizar la transformación. Tras realizar las pertinentes comprobaciones con respecto a las imágenes origen y destino proporcionadas, creará dos arrays correspondientes
     * a los valores de los pixeles origen y destino. Para cada uno de ellos, realizará una transformación. Se calculará un número aleatorio para cada uno, que irá de 0 al umbral
     * proporcionado por el usuario. Si el resultado es equivalente a 0, se realizará una transformación basada en la reducción del valor de cada banda, fijándose en los valores
     * de bandas distintas a la objetivo. En otro caso (el caso más probable normalmente), se multiplicará el valor de cada banda por 2, 3 y 4.
     * @param src Imagen origen, tipo BufferedImage
     * @param dest ImagenDestino, tipo BufferedImage
     * @return imagen resultado, tipo BufferedImage
     */
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }
        
        Random rand = new Random();
        int numeroAleatorio;  
        
        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();
        int[] pixelComp = new int[srcRaster.getNumBands()];
        int[] pixelCompDest = new int[srcRaster.getNumBands()];
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                srcRaster.getPixel(x, y, pixelComp);
                
                numeroAleatorio = rand.nextInt(aleatoriedad);
                
                if (numeroAleatorio == 0) {
                    pixelCompDest[0] = pixelComp[2] / 2;
                    pixelCompDest[1] = pixelComp[0] / 3;
                    pixelCompDest[2] = pixelComp[1] / 4;
                } else {
                    pixelCompDest[0] = pixelComp[0] * 2;
                    pixelCompDest[1] = pixelComp[1] * 3;
                    pixelCompDest[2] = pixelComp[2] * 4;
                }
                
                destRaster.setPixel(x, y, pixelCompDest);
                
            }
        }
        return dest;
    }
}
