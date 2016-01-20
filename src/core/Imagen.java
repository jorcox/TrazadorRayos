package core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import scene.Pantalla;

/**
 * 
 * @author Javier Beltran Jorba
 * @author Jorge Cancer Gil
 * 
 * La clase Imagen permite crear una imagen en disco a partir de los
 * colores de cada pixel calculados previamente.
 *
 */
public class Imagen {
	
	/**
	 * Dada una matriz con el color de cada pixel, crea una imagen con
	 * el nombre y formato indicados.
	 */
	public static void crearImagen(Pantalla pantalla, Color[][] pixels, String nombre, String formato) 
			throws IOException {
		BufferedImage img = new BufferedImage(pantalla.getnC(), pantalla.getnR(), BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < pantalla.getnC(); i++) {
			for (int j = 0; j < pantalla.getnR(); j++) {
				/*
				 * Desplazamos los colores para introducirlos de la forma
				 * RRRGGGBBB
				 */
				int col = (pixels[i][j].getRed() << 16) | (pixels[i][j].getGreen() << 8) | pixels[i][j].getBlue();
				img.setRGB(i, j, col);
			}
		}
		File outputfile = new File(nombre + "." + formato);
		ImageIO.write(img, formato, outputfile);
	}
	
}
