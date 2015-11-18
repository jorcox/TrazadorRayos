package core;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Trazador {
	
	public static void main(String[] args) {
		Pantalla pantalla = null;
		Point3d ojo = null;
		Vector3d g = null;
		Luz luz = null;
		Camara camara = null;
		int pixelsX = 0;
		int pixelsY = 0;
		Color[][] pixels = null;
		ArrayList<Objeto> objetos = new ArrayList<Objeto>();
		
		/* Procesar fichero y crear objetos */
/*		if (args[0]!=null) {
			try {
				File fichero = new File(args[0]);
				BufferedReader reader = new BufferedReader(new FileReader(fichero));				
				ArrayList<String[]> lineas = new ArrayList<String[]>();

				String linea = reader.readLine();
				while (linea != null) {
					lineas.add(linea.split(" "));
					linea = reader.readLine();
				}
				reader.close();

				for (String[] orden: lineas) {
					if (orden[0].equals("pantalla")) {						
						pantalla = TrazadorUtils.getPantalla(orden);
						pixelsX = Integer.parseInt(orden[4]);
						pixelsY = Integer.parseInt(orden[5]);
					} else if (orden[0].equals("ojo")) {
						ojo = TrazadorUtils.getOjo(orden);
					} else if (orden[0].equals("g")) {
						g = TrazadorUtils.getG(orden);
					} else if (orden[0].equals("plano")) {
						Plano plano = TrazadorUtils.getPlano(orden);
						objetos.add(plano);
					} else if (orden[0].equals("triangulo")) {
						Triangulo triangulo = TrazadorUtils.getTriangulo(orden);
						objetos.add(triangulo);
					} else if (orden[0].equals("esfera")) {
						Esfera esfera = TrazadorUtils.getEsfera(orden);
						objetos.add(esfera);
					} else if (orden[0].equals("luz")) {
						luz = TrazadorUtils.getLuz(orden);
					}
				}
				
				camara = new Camara(ojo, g);
				pixels = new Color[pixelsX][pixelsY];
				pantalla.calcularCoordenadasCamaraYMundo(camara);
			} catch (FileNotFoundException e) {
				System.out.println("Error: fichero de datos no encontrado");
				System.exit(1);
			} catch (FicheroDatosException e) {
				System.out.println("Error: fichero de datos introducido incorrectamente");
				System.exit(1);
			} catch (Exception e) {
				System.out.println("Error: problema con el fichero de datos");
				System.exit(1);
			}
		} else {
			System.out.println("Error: no ha introducido un fichero de datos");
			System.exit(1);
		}*/
		
		/*
		 * Inicializacion de el ArrayList de objetos
		 */
		objetos = new ArrayList<Objeto>();
		
		/* 
		 * Inicializacion de algunos elementos
		 */
		int anchura = 1920;
		int altura = 1080;
		pantalla = new Pantalla(6, 3.375, 2, anchura, altura);
		pixels = new Color[anchura][altura];
		ojo = new Point3d(7,4,3);
		g = new Vector3d(-1,-1,0);
		camara = new Camara(ojo,g);
		pantalla.calcularCoordenadasCamaraYMundo(camara);
		luz = new Luz(new Point3d(7,4,3), 1);
		
		/*
		 * PRUEBAS
		 */
		
		Rayo rayoPrimario1 = new Rayo(camara.getE(),pantalla.coordMundo[960][450]);
		Rayo rayoPrimario4 = new Rayo(camara.getE(),pantalla.coordMundo[960][540]);
		Rayo rayoPrimario2 = new Rayo(camara.getE(),pantalla.coordMundo[860][440]);
		Rayo rayoPrimario3 = new Rayo(camara.getE(),pantalla.coordMundo[1060][440]);
		objetos.add(new Triangulo(rayoPrimario4.getPunto(1.1), rayoPrimario3.getPunto(1.1), 
				rayoPrimario2.getPunto(1.1), new Color(255,0,0),0.5));
		//Point3d ss = rayoPrimario1.getPunto(1.1); 
		objetos.add(new Esfera(0.51,rayoPrimario1.getPunto(1.1), new Color(255,0,0),0.5));
		//objetos.add(new Plano(rayoPrimario1.getPunto(1.1), new Vector3d(-1.5,10,1), new Color(255,0,0),0.5));
		double iAmbiental = 0.1;
		
		/*
		 * FIN DE PRUEBAS
		 */
		
			
		
		/*
		 * Por cada pixel de la pantalla se lanza un rayo
		 */
		int cu = 0;
		for (int i = 0; i < pantalla.getnC(); i++) {
			for (int j = 0; j < pantalla.getnR(); j++) {
				/*
				 *  Creamos el rayo primario del pixel i,j
				 */
				if(i==159 && j==106){
					System.out.println("hola");
					Point3d pppp = pantalla.coordCamara[i][j];
					pppp.toString();
				}
				Rayo rayoPrimario = new Rayo(camara.getE(),pantalla.coordMundo[i][j]);
				// Disparamos el rayo primario a la escena y se comprueba si intersecta
				Objeto objetoCol = null;
				Point3d puntoColision = null;
				Point3d puntoColisionFinal = null;
				double distanciaMin = Double.MAX_VALUE;
				
				
				/*
				 * Por cada objeto se calcula con cual intersecta primer ( i.e. el mas cercano)
				 */
				for (int k = 0; k < objetos.size(); k++) {
					puntoColision = objetos.get(k).interseccion(rayoPrimario);
					if(puntoColision != null){
						cu++;
						double distancia = puntoColision.distance(camara.getE());
						if (distancia < distanciaMin){
							distanciaMin = distancia;
							objetoCol = objetos.get(k);
							puntoColisionFinal = puntoColision;
						}
					}
				}
				/*
				 * Caso en el que el rayo ha intersectado con algun objeto 
				 */
				if(objetoCol != null){
					/*
					 * Se crea un rayo que va desde el punto de colision con el objeto
					 * hasta el foco de luz
					 */
					Rayo rayoSombra = new Rayo(puntoColisionFinal, luz.getPunto());
					Point3d puntoColisionSombra = null;
					boolean esSombra = false;
					/*
					 * Se comprueba si en el camino a la luz hay algun otro objeto
					 */
					for (int k = 0; k < objetos.size(); k++) {
						puntoColisionSombra = objetos.get(k).interseccion(rayoSombra);
						/*
						 * Si colisiona con un objeto, este pixel esta en la sombra
						 */
						if(puntoColisionSombra != null){
							esSombra = true;
						}
					}
					
					/*
					 * Aplicaciones de color segun si es sombra o no
					 */
					if(esSombra){
						//pixels[i][j] = objetoCol.getColor().aplicarIntensidad(objetoCol.getKd()*iAmbiental);
						Vector3d n = new Vector3d(objetoCol.getN(puntoColisionFinal));
						Point3d aux = new Point3d(luz.getPunto());
						aux.sub(puntoColisionFinal);
						Vector3d l = new Vector3d(aux);
						double iDifusa = objetoCol.getKd()*luz.getBrillo()*(1-n.angle(l));
						Color cl = objetoCol.getColor().aplicarIntensidad(objetoCol.getKd()*iAmbiental+objetoCol.getKd()*iDifusa);
						pixels[i][j] = cl;
					} else {
						Vector3d n = new Vector3d(objetoCol.getN(puntoColisionFinal));
						Point3d aux = new Point3d(luz.getPunto());
						aux.sub(puntoColisionFinal);
						Vector3d l = new Vector3d(aux);
						double iDifusa = objetoCol.getKd()*luz.getBrillo()*(1-n.angle(l));
						Color cl = objetoCol.getColor().aplicarIntensidad(objetoCol.getKd()*iAmbiental+objetoCol.getKd()*iDifusa);
						pixels[i][j] = cl;
					}
				} 
				/*
				 * Caso en el que el rayo no ha colisionado con nada
				 */
				else {
					pixels[i][j] = new Color(0,0,255);
				}
			}
		} 
		System.out.println(cu);
		BufferedImage img = new BufferedImage(anchura,altura,BufferedImage.TYPE_INT_RGB);
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
