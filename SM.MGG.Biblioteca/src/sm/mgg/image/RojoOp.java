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
 *  Clase para realizar la operación de resaltado del color rojo.
 * @author Mario Guisado García
 */
public class RojoOp extends BufferedImageOpAdapter {
    /**
     * Dato int necesario para establecer un umbral de corte a la hora de evaluar los colores.
     */
    private int umbral;
    /**
     * Constructor que inicializa el parámetro de clase.
     * @param umbral Dato int correspondiente al umbral a establecer.
     */
    public RojoOp(int umbral) {
        this.umbral = umbral;
    }
    /**
     * Método para realizar la transformación. Tras comprobar las imágenes de origen y destino, se crean dos arrays para los pixeles de origen y destino. Sobre ellos
     * se realizarán las operaciones y más tarde se asignarán a los ráster correspondientes. Sobre cada pixel se evaluará sus componentes teniendo en cuenta el umbral
     * establecido y se determinará si es suficientemente rojo o no. En caso de serlo el pixel no se modificará, en caso contrario se establecerá un nuevo valor medio teniendo en cuenta 
     * sus componentes.
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
        int[] pixelComp = new int[srcRaster.getNumBands()];
        int[] pixelCompDest = new int[srcRaster.getNumBands()];
        
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                srcRaster.getPixel(x, y, pixelComp);
                
                if(pixelComp[0] -pixelComp[1] -pixelComp[2] < umbral){
                    pixelCompDest[0] = (pixelComp[0] +pixelComp[1] +pixelComp[2]) / 3;
                    pixelCompDest[1] = (pixelComp[0] +pixelComp[1] +pixelComp[2]) / 3;
                    pixelCompDest[2] = (pixelComp[0] +pixelComp[1] +pixelComp[2]) / 3;
                }
                else{
                    pixelCompDest[0] = pixelComp[0];
                    pixelCompDest[1] = pixelComp[1];
                    pixelCompDest[2] = pixelComp[2];
                }
                destRaster.setPixel(x, y, pixelCompDest);            
            }
        }
        return dest;
    }
}
