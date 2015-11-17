package core;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Trazador {
	
	public static void main(String[] args) {
		/* Procesar fichero y crear objetos */
		/*if (args[0]!=null) {
			try {
				File fichero = new File(args[0]);
				BufferedReader reader = new BufferedReader(new FileReader(fichero));
				String linea = reader.readLine();
				while (linea!= null) {
					
				}
			} catch (FileNotFoundException e) {
				System.out.println("Error: fichero de datos no encontrado");
				System.exit(1);
			} catch (Exception e) {
				System.out.println("Error: problema con el fichero de datos");
				System.exit(1);
			}
		} else {
			System.out.println("Error: no ha introducido un fichero de datos");
			System.exit(1);
		}*/
		
		Objeto[] objetos = new Objeto[1];
		//objetos[0] = new Plano(new Point3d(1094.4538089846817, 186.794213613377, -123.94017127971526), new Vector3d(-1.5,10,1), new Color(255,0,0));
		/* Crear componentes (pantalla, etc) */
		Pantalla pantalla = new Pantalla(300, 300, 20, 1920,1080);
		Color[][] pixels = new Color[1920][1080];
		Point3d g = new Point3d(1,1,1);
		Vector3d v = new Vector3d(-1,-3,2);
		Camara camara = new Camara(g,v);
		pantalla.calcularCoordenadasCamaraYMundo(camara);
		Luz luz = new Luz(new Point3d(0,0,0), 1);
		
		Rayo rayoPrimario1 = new Rayo(camara.getE(),pantalla.coordMundo[960][540]);
		Rayo rayoPrimario2 = new Rayo(camara.getE(),pantalla.coordMundo[870][650]);
		Rayo rayoPrimario3 = new Rayo(camara.getE(),pantalla.coordMundo[1070][650]);
		objetos[0] = new Triangulo(rayoPrimario1.getPunto(1.1), rayoPrimario3.getPunto(1.1), 
				rayoPrimario2.getPunto(1.1), new Color(255,0,0));
		//objetos[0] = new Esfera(200,rayoPrimario1.getPunto(1.1), new Color(255,0,50));
		
		
		/* Lanzar rayos desde cada pixel (doble jarl recorriendo pantalla) */
		
		
		int cu = 0;
		int ca = 0;
		for (int i = 0; i < pantalla.getnC(); i++) {
			for (int j = 0; j < pantalla.getnR(); j++) {
				// Creamos el rayo primario del pixel i,j
				Rayo rayoPrimario = new Rayo(camara.getE(),pantalla.coordMundo[i][j]);
				// Disparamos el rayo primario a la escena y se comprueba si intersecta
				Objeto objetoCol = null;
				Point3d puntoColision = null;
				double distanciaMin = Double.MAX_VALUE;
				
				
				for (int k = 0; k < objetos.length; k++) {
					puntoColision = objetos[k].interseccion(rayoPrimario);
					if(puntoColision != null){
						cu++;
						double distancia = puntoColision.distance(camara.getE());
						if (distancia < distanciaMin){
							objetoCol = objetos[k];
						}
					}
				}
				if(objetoCol != null){
					ca++;
					Rayo rayoSombra = new Rayo(puntoColision, luz.getPunto());
					Point3d puntoColisionSombra = null;
					boolean esSombra = false;
					for (int k = 0; k < objetos.length; k++) {
						puntoColisionSombra = objetos[k].interseccion(rayoSombra);
						if(puntoColisionSombra != null){
							esSombra = true;
						}
					}
					if(esSombra){
						pixels[i][j] = new Color(0,255,0);
					} else {
						Color col = objetoCol.getColor();
						col.setBrillo(luz.getBrillo());
						pixels[i][j] = col;
					}
				} else {
					pixels[i][j] = new Color(0,0,255);
				}
			}
		} 
		System.out.println(cu);
		System.out.println(ca);
		BufferedImage img = new BufferedImage(1920,1080,BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < pantalla.getnC(); i++) {
			for (int j = 0; j < pantalla.getnR(); j++) {
				int col = (pixels[i][j].getRed() << 16) | (pixels[i][j].getGreen() << 8) | 
				pixels[i][j].getBlue();
				img.setRGB(i, j, col);
			}
		}
		File outputfile = new File("saved.png");
		try {
			ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
