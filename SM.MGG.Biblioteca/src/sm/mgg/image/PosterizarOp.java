/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.mgg.image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 *  Clase para llevar a cabo la posterización, es decir, reducción de número de colores de una imagen.
 * @author Mario Guisado García
 */
public class PosterizarOp extends BufferedImageOpAdapter {
    /**
     * Dato int correspondiente a los niveles deseados para llevar a cabo la reducción de los colores.
     */
    private int niveles;
    /**
     * Cosntructor que inicializa el parámetro de clase.
     * @param niveles Datao int correspondiente a los niveles deseados.
     */
    public PosterizarOp(int niveles) {
        this.niveles = niveles;
    }
    /**
     * Método para realizar la transformación. En este caso se trata de posterizar, es decir, de reducir el número de color de una imagen teniendo en cuenta 
     * un número específico de niveles, en este caso los correspondientes al parámetro de clase "niveles". Tras realizar las pertinentes comprobaciones con respecto a 
     * las imágenes proporcionadas como origen y destino, se lleva a cabo la transformación componente a componente.
     *
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
        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();
        int sample;
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                for (int band = 0; band < srcRaster.getNumBands(); band++) {
                    sample = srcRaster.getSample(x, y, band);
                    int K = (int) 256/ niveles;
                    sample = K * (int) (sample/K);
                    destRaster.setSample(x, y, band, sample);
                }
            }
        }
        return dest;
    }
}
