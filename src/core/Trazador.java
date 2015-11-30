package core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import objects.Esfera;
import objects.Objeto;
import scene.Camara;
import scene.DatosEscena;
import scene.Luz;
import scene.Pantalla;
import scene.Rayo;

public class Trazador {

	private static Point3d origen = new Point3d(0, 0, 0);
	private static ArrayList<Objeto> objetos = new ArrayList<Objeto>();
	private static Camara camara = null;
	private static Luz luz = null;
	private static double iAmbiental = 0.0;
	private static final int NUM_ANTIALIASING = 1;
	private static final String NOMBRE_IMG = "imagen";
	private static final String FORMATO_IMG = "png";

	public static void main(String[] args) {
		Pantalla pantalla = null;
		Color[][] pixels = null;

		/* Procesar fichero y crear objetos */
		if (args.length >= 1) {
			try {
				DatosEscena datos = TrazadorUtils.cargarObjetos(args[0]);
				if (datos == null) {
					System.out.println("Error en el fichero de datos");
					System.exit(1);
				}
				objetos = datos.getObjetos();
				iAmbiental = datos.getAmbiental();
				pantalla = datos.getPantalla();
				camara = datos.getCamara();
				luz = datos.getLuz();
				pixels = new Color[pantalla.getnC()][pantalla.getnR()];
				pantalla.calcularCoordenadasCamaraYMundo(camara);
				
			} catch(Exception e) {
				System.out.println("Error en la entrada de datos");
				System.exit(1);
			}		
		} else {
			System.out.println("Error: no ha introducido un fichero de datos");
			System.exit(1);
		}

		/*
		 * Por cada pixel de la pantalla se lanza un rayo
		 */
		double varU = pantalla.getVarU();
		double varV = pantalla.getVarV();
		for (int i = 0; i < pantalla.getnC(); i++) {
			for (int j = 0; j < pantalla.getnR(); j++) {
				
				Point3d pixel = pantalla.getPuntoCoordMundo(i,j);
				Color[] colores = new Color[NUM_ANTIALIASING];
				Random random = new Random();
				
				/*
				 * Traza varios rayos en el pixel para el antialiasing.
				 */
				for (int k=0; k<NUM_ANTIALIASING; k++) {
					double offsetX = random.nextDouble()*varU - varU/2;
					double offsetY = random.nextDouble()*varV - varV/2;
					Point3d nuevo = new Point3d(pixel.getX() + offsetX, pixel.getY() + offsetY, pixel.getZ());
					Rayo rayo = new Rayo(camara.getE(), nuevo);
					colores[k] = trazarRayo(rayo, 0, null, null, false);
				}
				Color colorFinal = Color.promedio(colores);

				/*
				 * Asignamos el valor del color del pixel [i,j] en su posicion
				 * correspondiente
				 */
				pixels[i][j] = colorFinal;
			}
		}

		try {
			Imagen.crearImagen(pantalla, pixels, NOMBRE_IMG, FORMATO_IMG);
		} catch (IOException e) {
			System.out.println("Error al crear la imagen.");
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