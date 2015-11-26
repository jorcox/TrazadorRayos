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

	private static Point3d origen = new Point3d(0, 0, 0);
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
		g = new Vector3d(1, 1, 1);
		camara = new Camara(ojo, g);
		pantalla.calcularCoordenadasCamaraYMundo(camara);

		Transformacion camaraAMundo = Transformacion.getCameraToWorldMatrix(camara);

		Point3d pLuz = new Point3d(0, 100, -1350);
		pLuz = camaraAMundo.transformar(pLuz);
		luz = new Luz(pLuz, 1);

		Point3d cEsf1 = new Point3d(-170, -40, -1325);
		cEsf1 = camaraAMundo.transformar(cEsf1);
		objetos.add(new Esfera(15, cEsf1, new Color(0, 255, 0), 0, 1, 1.52));

		Point3d cEsf2 = new Point3d(0, 10, -1400);
		cEsf2 = camaraAMundo.transformar(cEsf2);
		objetos.add(new Esfera(21, cEsf2, new Color(0, 0, 0), 1, 0, 1.52));

		Point3d cEsf3 = new Point3d(0, 100, -1400);
		cEsf3 = camaraAMundo.transformar(cEsf3);
		objetos.add(new Esfera(21, cEsf3, new Color(150, 20, 189), 1, 0, 0.75));

		Point3d cEsf4 = new Point3d(-50, 100, -1400);
		cEsf4 = camaraAMundo.transformar(cEsf4);
		objetos.add(new Esfera(10, cEsf4, new Color(150, 189, 20), 1, 0, 2));

		Point3d cEsf5 = new Point3d(-41, 30, -1400);
		cEsf5 = camaraAMundo.transformar(cEsf5);
		objetos.add(new Esfera(21, cEsf5, new Color(20, 150, 189), 1, 0, 2));

		Point3d pPlanos = new Point3d(0, -25, -1550);
		pPlanos = camaraAMundo.transformar(pPlanos);

		Vector3d n1 = new Vector3d(7, -3, 6);
		n1 = camaraAMundo.transformar(n1);
		objetos.add(new Plano(pPlanos, n1, new Color(0, 0, 255), 0.1, 0, 2));

		Vector3d n2 = new Vector3d(-7, -3, 6);
		n2 = camaraAMundo.transformar(n2);
		objetos.add(new Plano(pPlanos, n2, new Color(255, 0, 0), 0.1, 0, 2));

		Vector3d n3 = new Vector3d(0, 1, 0.15);
		n3 = camaraAMundo.transformar(n3);
		objetos.add(new Plano(pPlanos, n3, new Color(255, 115, 0), 0.1, 0, 2));

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
				 * Creamos el rayo primario del pixel [i,j]
				 */
				Rayo rayoPrimario = new Rayo(camara.getE(), pantalla.coordMundo[i][j]);

				if (i == 38 && j == 100) {
					System.out.println("melon");
				}

				/*
				 * Lanzamos el rayo que sale del ojo y pasa por el pixel [i,j]
				 */
				Color col = trazarRayo(rayoPrimario, 0, null, null, false);

				/*
				 * Asignamos el valor del color del pixel [i,j] en su posicion
				 * correspondiente
				 */
				pixels[i][j] = col;
			}
		}

		/*
		 * Creacion y rellenado de la imagen
		 */
		BufferedImage img = new BufferedImage(anchura, altura, BufferedImage.TYPE_INT_RGB);
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
		File outputfile = new File("imagen.png");
		try {
			ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			System.out.println("Error en la creacion del fichero de la foto");
		}
	}

	/**
	 * Metodo principal del trazador
	 * 
	 * @param rayoPrimario
	 * @param recursion
	 *            indica las veces que se ha llamado al metodo recursivamente
	 *            para un mismo pixel
	 * @param original
	 *            Indica el objeto desde el que parte el rayo para no tenerlo en
	 *            cuenta en el calculo de las colisiones
	 *
	 * @return el Color del pixel
	 *
	 */
	private static Color trazarRayo(Rayo rayoPrincipal, int recursion, Objeto objetoIgnorar, Objeto objetoActual,
			boolean interno) {
		/*
		 * Por cada objeto se calcula con cual intersecta primer ( i.e. el mas
		 * cercano)
		 */
		Objeto objetoCol = null;
		Point3d puntoColision = null;
		Point3d puntoColisionFinal = null;
		double distanciaMin = Double.MAX_VALUE;
		/*
		 * Bucle para comprobar con que objeto se colisiona
		 */
		for (int k = 0; k < objetos.size(); k++) {
			if (objetoIgnorar == null || !objetoIgnorar.equals(objetos.get(k))) {
				/*
				 * Si el objeto es complejo tendra una entrada y una salida
				 */
				if (objetos.get(k).esComplejo()) {
					Point3d[] puntos = objetos.get(k).interseccionCompleja(rayoPrincipal);
					try {
						puntoColision = puntos[0];
						/*
						 * Si es complejo pero interno, puede haber una o dos
						 * colisiones, cogemos la mas lejana
						 */
						if (interno) {
							puntoColision = puntos[puntos.length - 1];
						}
					} catch (NullPointerException e) {
						puntoColision = null;
					}
				}
				/*
				 * Si el objeto no es complejo solo tendra una colision ( o
				 * ninguna )
				 */
				else {
					puntoColision = objetos.get(k).interseccion(rayoPrincipal);
				}
				/*
				 * Si ha habido colision se guarda la referencia
				 */
				if (puntoColision != null) {
					double distancia = puntoColision.distance(rayoPrincipal.getP0());
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
			 * Bucle en el que se comprueba si en el camino a la luz hay algun
			 * otro objeto
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
						if (distanciaALuz > distanciaAObjetoColisionado) {
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
				 * Declaracion, inicializacion y normalizacion de los
				 * principales vectores para su uso en los proximos calculos
				 */
				Vector3d N = new Vector3d(objetoCol.getN(puntoColisionFinal));
				N.normalize();
				if(interno){
					N.negate();
				}
				Rayo alOrigen = new Rayo(puntoColisionFinal, rayoPrincipal.getP0());
				Vector3d V = new Vector3d(alOrigen.getD());
				V.normalize();
				Rayo aLaLuz = new Rayo(puntoColisionFinal, luz.getPunto());
				Vector3d L = new Vector3d(aLaLuz.getD());
				L.normalize();
				Vector3d R = calcularReflejadoEspecular(L, N);
				R.normalize();
				/*
				 * Calculo de indice de refraccion teniendo en cuenta el medio
				 * por el que esta viajando ahora el rayo y el medio del objeto
				 * con el que hemos colisionado
				 */
				double coeficiente = 0.0;
				double indiceOrigen = 0.0;
				double indiceDestino = 0.0;
				/*
				 * Si el objeto actual es null (i.e. aire) su indice de
				 * refraccion es 1
				 */
				try {
					indiceOrigen = objetoActual.getCoeficienteRefraccion();
				} catch (NullPointerException e) {
					indiceOrigen = 1.0;
				}
				/*
				 * Si el objeto colisionado es null (i.e. aire) su indice de
				 * refraccion es 1
				 */
				try {
					/*
					 * Si estamos dentro de un objeto el indice de refraccion del objeto destino
					 * va a ser 1 porque vamos a salir al aire
					 */
					if(interno) {
						indiceDestino = 1.0;
					} else {
						indiceDestino = objetoCol.getCoeficienteRefraccion();
					}
				} catch (NullPointerException e) {
					indiceDestino = 1.0;
				}

				Vector3d T = calcularRefractado(V, N, indiceOrigen / indiceDestino, rayoPrincipal);

				/*
				 * Calculo de la intensidad difusa
				 */
				double iDifusa = luz.getBrillo() * (Math.cos(L.angle(N)));

				/*
				 * Calculo de la intensidad especular
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
					difusa = difusa.aplicarIntensidad(1-objetoCol.getIndiceRefraccion());
					cl = ambiental.suma(difusa);
				} else if (iDifusa < 0) {
					Color ambiental = objetoCol.getKd().aplicarIntensidad(iAmbiental);
					Color especular = objetoCol.getKs().aplicarIntensidad(iEspecular);
					especular = especular.aplicarIntensidad(1-objetoCol.getIndiceRefraccion());
					cl = ambiental.suma(especular);
				} else {
					Color ambiental = objetoCol.getKd().aplicarIntensidad(iAmbiental);
					Color difusa = objetoCol.getKd().aplicarIntensidad(iDifusa);
					difusa = difusa.aplicarIntensidad(1-objetoCol.getIndiceRefraccion());
					Color especular = objetoCol.getKs().aplicarIntensidad(iEspecular);
					especular = especular.aplicarIntensidad(1-objetoCol.getIndiceRefraccion());
					Color aux = ambiental.suma(difusa);
					cl = aux.suma(especular);
				}

				if (recursion < 3) {
					Rayo rayoReflejado = new Rayo(calcularReflejado(rayoPrincipal.getD(), N), puntoColisionFinal);
					recursion += 1;
					Color nuevo = trazarRayo(rayoReflejado, recursion, objetoCol, objetoCol, false);
					Color reducido = nuevo.aplicarIntensidad(objetoCol.getIndiceReflexion());
					cl = cl.suma(reducido);
					Rayo rayoRefractado = new Rayo(T, puntoColisionFinal);
					if (objetoCol instanceof Esfera) {
						if (!interno) {
							/*
							 * Si colisionamos con un objeto complejo y no
							 * estamos dentro ponemos el objetoIgnorar a null y
							 * el objetoActual como el objeto con el que se ha
							 * colisionado
							 */
							nuevo = trazarRayo(rayoRefractado, recursion, null, objetoCol, true);
						} else {
							/*
							 * Si colisionamos con un objeto complejo y estamos
							 * dentro ponemos el objetoIgnorar a objetoCol y el
							 * objetoActual como null ya que estamos saliendo al
							 * aire
							 */
							nuevo = trazarRayo(rayoRefractado, recursion, objetoCol, null, false);
						}

					} else {
						nuevo = trazarRayo(rayoRefractado, recursion, objetoCol, null, false);
					}

					reducido = nuevo.aplicarIntensidad(objetoCol.getIndiceRefraccion());
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

	private static Vector3d calcularRefractado(Vector3d i, Vector3d n, double indiceRefraccion, Rayo rayoPrincipal) {
		Vector3d N = new Vector3d(n);
		Vector3d I = new Vector3d(i);
		double nXi = N.dot(I);
		;
		// nr*(N*I)-sqrt(1-nr^2*(1-(N*I)^2))
		double enRaiz = 1 - (Math.pow(indiceRefraccion, 2) * (1 - Math.pow(nXi, 2)));
		if (enRaiz >= 0) {
			double pO = (indiceRefraccion * nXi) - Math.sqrt(enRaiz);
			// ans*N
			N.scale(pO);
			// nr*I
			I.scale(indiceRefraccion);
			// ans*N-nr*I
			N.sub(I);
			return N;
		} else {
			return calcularReflejado(rayoPrincipal.getD(), N);
		}

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