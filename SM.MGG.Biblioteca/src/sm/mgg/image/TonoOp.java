/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.mgg.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Clase correspondiente a la operación de modificación del tono de los colores de una imagen.
 * @author Mario Guisado García
 */
public class TonoOp extends BufferedImageOpAdapter {
    /**
     * Parámetro de clase de tipo int que almacenará el valor proporcionado por el usuario
     * y que determinará el resultado de la transformación.
     */
    private int valor;
    /**
     * Constructor que inicializa el dato privado de clase.
     * @param val Dato int para inicializarlo, es el proporcionado por el usuario.
     */
    public TonoOp(int val) {
        this.valor = val;
    }
    /**
     * Método para realizar la transformación. Tras realizar las pertinentes comprobaciones con respecto a las imágenes origen y destino proporcionadas, 
     * creará dos arrays correspondientes a los valores de los pixeles origen y destino. Para cada componente se llevará a cabo la transformación de 
     * variación del tono: 𝐻𝑟𝑒𝑠𝑢𝑙𝑡𝑎𝑑𝑜 = (𝐻 + 𝜑) 𝑚𝑜𝑑 360, donde H corresponde al valor del tono, obtenido gracias al método RGBtoHSB de la clase Color y 
     * 𝜑 corresponde al valor introducido por el usuario. Después de realizar la transformación, obtendremos las componentes codificadas en forma de int,
     * por lo que habrá que emplear operadores binarios para poder separar las componentes.
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
        float[] pixelCompDest = new float[srcRaster.getNumBands()];
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                srcRaster.getPixel(x, y, pixelComp);
                Color.RGBtoHSB(pixelComp[0], pixelComp[1], pixelComp[2], pixelCompDest);
                pixelCompDest[0] = ((pixelCompDest[0] * 360 + valor) % 360) /360;
                destRaster.setPixel(x, y, pixelCompDest);
                int color = Color.HSBtoRGB(pixelCompDest[0], pixelCompDest[1], pixelCompDest[2]);
                pixelCompDest[0] = (color >> 16) & 0xFF;
                pixelCompDest[1] = (color >> 8) & 0xFF;
                pixelCompDest[2] = color & 0xFF;
                destRaster.setPixel(x, y, pixelCompDest);
                
            }
        }
        return dest;
    }
}
