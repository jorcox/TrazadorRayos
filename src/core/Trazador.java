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

	private static ArrayList<Objeto> objetos = new ArrayList<Objeto>();
	private static Camara camara = null;
	private static Luz luz = null;
	private static double iAmbiental = 0.0;

	public static void main(String[] args) {
		Pantalla pantalla = null;
		Point3d ojo = null;
		Vector3d g = null;

		int pixelsX = 0;
		int pixelsY = 0;
		Color[][] pixels = null;

		/* Procesar fichero y crear objetos */
		/*
		 * if (args[0]!=null) { try { File fichero = new File(args[0]);
		 * BufferedReader reader = new BufferedReader(new FileReader(fichero));
		 * ArrayList<String[]> lineas = new ArrayList<String[]>();
		 * 
		 * String linea = reader.readLine(); while (linea != null) {
		 * lineas.add(linea.split(" ")); linea = reader.readLine(); }
		 * reader.close();
		 * 
		 * for (String[] orden: lineas) { if (orden[0].equals("pantalla")) {
		 * pantalla = TrazadorUtils.getPantalla(orden); pixelsX =
		 * Integer.parseInt(orden[4]); pixelsY = Integer.parseInt(orden[5]); }
		 * else if (orden[0].equals("ojo")) { ojo = TrazadorUtils.getOjo(orden);
		 * } else if (orden[0].equals("g")) { g = TrazadorUtils.getG(orden); }
		 * else if (orden[0].equals("plano")) { Plano plano =
		 * TrazadorUtils.getPlano(orden); objetos.add(plano); } else if
		 * (orden[0].equals("triangulo")) { Triangulo triangulo =
		 * TrazadorUtils.getTriangulo(orden); objetos.add(triangulo); } else if
		 * (orden[0].equals("esfera")) { Esfera esfera =
		 * TrazadorUtils.getEsfera(orden); objetos.add(esfera); } else if
		 * (orden[0].equals("luz")) { luz = TrazadorUtils.getLuz(orden); } }
		 * 
		 * camara = new Camara(ojo, g); pixels = new Color[pixelsX][pixelsY];
		 * pantalla.calcularCoordenadasCamaraYMundo(camara); } catch
		 * (FileNotFoundException e) { System.out.println(
		 * "Error: fichero de datos no encontrado"); System.exit(1); } catch
		 * (FicheroDatosException e) { System.out.println(
		 * "Error: fichero de datos introducido incorrectamente");
		 * System.exit(1); } catch (Exception e) { System.out.println(
		 * "Error: problema con el fichero de datos"); System.exit(1); } } else
		 * { System.out.println("Error: no ha introducido un fichero de datos");
		 * System.exit(1); }
		 */

		/*
		 * Inicializacion de el ArrayList de objetos
		 */
		objetos = new ArrayList<Objeto>();

		/*
		 * Inicializacion de algunos elementos
		 */
		int anchura = 1920;
		int altura = 1080;
		pantalla = new Pantalla(60, 33.75, 200, anchura, altura);
		pixels = new Color[anchura][altura];
		ojo = new Point3d(0, 0, 0);
		g = new Vector3d(1,-0.7,1);
		camara = new Camara(ojo, g);
		pantalla.calcularCoordenadasCamaraYMundo(camara);
		// luz = new Luz(new Point3d(0,0,0), 1);

		/*
		 * PRUEBAS
		 */

		Rayo rayoPrimario1 = new Rayo(camara.getE(), pantalla.coordMundo[1550][700]);
		Rayo rayoPrimario4 = new Rayo(camara.getE(), pantalla.coordMundo[960][540]);
		Rayo rayoPrimario2 = new Rayo(camara.getE(), pantalla.coordMundo[960][0]);
		Rayo rayoPrimario3 = new Rayo(camara.getE(), pantalla.coordMundo[1060][440]);
		Rayo rayoPrimario5 = new Rayo(camara.getE(), pantalla.coordMundo[1919][1079]);
		Rayo rayoPrimario6 = new Rayo(camara.getE(), pantalla.coordMundo[960][1079]);
		// objetos.add(new Triangulo(rayoPrimario4.getPunto(1.1),
		// rayoPrimario3.getPunto(1.1),
		// rayoPrimario2.getPunto(1.1), new Color(255,0,0),0.9));
		// Point3d ss = rayoPrimario1.getPunto(1.1);
		objetos.add(new Esfera(10, rayoPrimario1.getPunto(2.5), new Color(0, 255, 0), 1));
		objetos.add(new Esfera(10, rayoPrimario4.getPunto(2.5), new Color(255, 0, 0), 1));
//		Vector3d jarl = new Vector3d(rayoPrimario6.getD());
//		jarl.negate();
		Vector3d jarl = new Vector3d(-1,0,0);
//		Point3d jj = new Point3d(rayoPrimario6.getPunto(5));
		Point3d jj = new Point3d(611.7642947406024, -508.5222180876726, 611.7642947406024);
		objetos.add(new Plano(jj, jarl, new Color(0, 0, 255), 0));
		Vector3d jarl2 = new Vector3d(0,0,-1);
		Rayo rayoPrimario7 = new Rayo(camara.getE(), pantalla.coordMundo[0][1079]);
		objetos.add(new Plano(jj, jarl2, new Color(255, 0, 0), 0));
		Vector3d jarl3 = new Vector3d(0,1,0);
		objetos.add(new Plano(jj, jarl3, new Color(255, 145, 0), 0));
		// objetos.add(new Plano(new Point3d(20,30,10), new Vector3d(-1,0,-1),
		// new Color(0,255,0),0.5));
		// objetos.add(new Plano(new Point3d(20,30,10), new Vector3d(0,-1,-1),
		// new Color(0,0,255),0.5));

		luz = new Luz(rayoPrimario2.getPunto(1), 1);
		//luz = new Luz(camara.getE(),1);

		iAmbiental = 0.08;

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
				 * Creamos el rayo primario del pixel i,j
				 */
				Rayo rayoPrimario = new Rayo(camara.getE(), pantalla.coordMundo[i][j]);
				// Disparamos el rayo primario a la escena y se comprueba si
				// intersecta

				if (i == 588 && j == 419) {
					System.out.println("melon");
				}
				Color col = trazarRayo(rayoPrimario, 0, null);

				pixels[i][j] = col;
			}
		}

		System.out.println(cu);
		BufferedImage img = new BufferedImage(anchura, altura, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < pantalla.getnC(); i++) {
			for (int j = 0; j < pantalla.getnR(); j++) {
				int col = (pixels[i][j].getRed() << 16) | (pixels[i][j].getGreen() << 8) | pixels[i][j].getBlue();
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

	private static Color trazarRayo(Rayo rayoPrimario, int recursion, Objeto original) {
		/*
		 * Por cada objeto se calcula con cual intersecta primer ( i.e. el mas
		 * cercano)
		 */
		Objeto objetoCol = null;
		Point3d puntoColision = null;
		Point3d puntoColisionFinal = null;
		double distanciaMin = Double.MAX_VALUE;
		for (int k = 0; k < objetos.size(); k++) {
			if (original == null || !original.equals(objetos.get(k))) {
				puntoColision = objetos.get(k).interseccion(rayoPrimario);
				if (puntoColision != null) {
					double distancia = puntoColision.distance(camara.getE());
					if (distancia < distanciaMin) {
						distanciaMin = distancia;
						objetoCol = objetos.get(k);
						puntoColisionFinal = puntoColision;
					}
				}
			}
		}
		/*
		 * Caso en el que el rayo ha intersectado con algun objeto
		 */
		if (objetoCol != null) {
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
				if (!objetoCol.equals(objetos.get(k))) {
					puntoColisionSombra = objetos.get(k).interseccion(rayoSombra);
					/*
					 * Si colisiona con un objeto, este pixel esta en la sombra
					 */
					double distanciaALuz = luz.getPunto().distance(puntoColisionFinal);
					
					if (puntoColisionSombra != null) {
						double distanciaAObjetoColisionado = puntoColisionSombra.distance(puntoColisionFinal);
						if(distanciaALuz > distanciaAObjetoColisionado) {
							esSombra = true;
						}
					}
				}
			}

			/*
			 * Aplicaciones de color segun si es sombra o no
			 */
			if (esSombra) {
				return objetoCol.getKd().aplicarIntensidad(iAmbiental);
			} else {
				/*
				 * Declaración, inicialización y normalización de los
				 * principales vectores para su uso en los próximos cálculos
				 */
				Vector3d N = new Vector3d(objetoCol.getN(puntoColisionFinal));
				N.normalize();
				Rayo alOjo = new Rayo(puntoColisionFinal, camara.getE());
				Vector3d V = new Vector3d(alOjo.getD());
				V.normalize();
				Rayo aLaLuz = new Rayo(puntoColisionFinal, luz.getPunto());
				Vector3d L = new Vector3d(aLaLuz.getD());
				L.normalize();
				Vector3d R = calcularReflejadoEspecular(L, N);
				R.normalize();
				Vector3d T = calcularRefractado(V, N, objetoCol.getIndiceRefraccion());

				/*
				 * Cálculo de la intensidad difusa
				 */
				double iDifusa = luz.getBrillo() * (Math.cos(L.angle(N)));

				/*
				 * Cálculo de la intensidad especular
				 */
				double iEspecular = 0;
				if (iDifusa > 0) {
					double angulo = Math.cos(R.angle(V));
					if (angulo > 0) {
						iEspecular = Math.pow(Math.cos(R.angle(V)), 100);
					} else {
						iEspecular = angulo;
					}
				}

				Color cl;
				if (iDifusa < 0 && iEspecular < 0) {
					cl = objetoCol.getKd().aplicarIntensidad(iAmbiental);
				} else if (iEspecular < 0) {
					Color ambiental = objetoCol.getKd().aplicarIntensidad(iAmbiental);
					Color difusa = objetoCol.getKd().aplicarIntensidad(iDifusa);
					cl = ambiental.suma(difusa);
				} else if (iDifusa < 0) {
					Color ambiental = objetoCol.getKd().aplicarIntensidad(iAmbiental);
					Color especular = objetoCol.getKs().aplicarIntensidad(iEspecular);
					cl = ambiental.suma(especular);
				} else {
					Color ambiental = objetoCol.getKd().aplicarIntensidad(iAmbiental);
					Color difusa = objetoCol.getKd().aplicarIntensidad(iDifusa);
					Color especular = objetoCol.getKs().aplicarIntensidad(iEspecular);
					Color aux = ambiental.suma(difusa);
					cl = aux.suma(especular);
				}

				if (recursion <= 3) {
					Rayo rayoReflejado = new Rayo(calcularReflejado(rayoPrimario.getD(), N), puntoColisionFinal);
					recursion += 1;
					Color nuevo = trazarRayo(rayoReflejado, recursion, objetoCol);
					Color reducido = nuevo.aplicarIntensidad(objetoCol.getIndiceRefraccion());
					cl = cl.suma(reducido);
				}
				cl.normalizarColor();
				return cl;

			}
		}
		/*
		 * Caso en el que el rayo no ha colisionado con nada
		 */
		else {
			return new Color(0, 0, 0);
		}
	}

	private static Vector3d calcularReflejado(Vector3d v, Vector3d n) {
		Vector3d V = new Vector3d(v);
		Vector3d N = new Vector3d(n);
		double aux = V.dot(N);
		N.scale(2 * aux);
		V.sub(N);
		return V;
	}

	private static Vector3d calcularRefractado(Vector3d i, Vector3d n, double indiceRefraccion) {
		Vector3d N = new Vector3d(n);
		Vector3d I = new Vector3d(i);
		double nXi = N.dot(I);
		// nr*(N*I)-sqrt(1-nr^2*(1-(N*I)^2))
		double pO = indiceRefraccion * nXi - Math.sqrt(1 - (Math.pow(indiceRefraccion, 2) * (1 - Math.pow(nXi, 2))));
		// ans*N
		N.scale(pO);
		// nr*I
		I.scale(indiceRefraccion);
		// ans*N-nr*I
		N.sub(I);
		return N;
	}

	private static Vector3d calcularReflejadoEspecular(Vector3d L, Vector3d n) {
		Vector3d nor = new Vector3d(n);
		double pO = nor.dot(L);
		nor.scale(2 * pO);
		nor.sub(L);
		nor.normalize();
		return nor;
	}
}
