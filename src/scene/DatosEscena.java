package scene;

import java.util.ArrayList;

import objects.Objeto;

/**
 * 
 * @author Javier Beltran Jorba
 * @author Jorge Cancer Gil
 * 
 * Objeto que envuelve diferentes elementos de la escena.
 * En particular, la camara, las luces, la pantalla y 
 * los objetos. Tambien contiene la intensidad ambiental
 * definida.
 * 
 */
public class DatosEscena {
	
	private Camara camara;
	private ArrayList<Luz> luces;
	private Pantalla pantalla;
	private ArrayList<Objeto> objetos;
	private double iAmbiental;
	
	/**
	 * Crea un envoltorio DatosEscena.
	 */
	public DatosEscena(Camara cam, ArrayList<Luz> luces, Pantalla p, ArrayList<Objeto> obj, double iAmb) {
		camara = cam;
		this.luces = luces;
		pantalla = p;
		objetos = obj;
		iAmbiental = iAmb;
	}

	public ArrayList<Objeto> getObjetos() {
		return objetos;
	}

	public double getAmbiental() {
		return iAmbiental;
	}

	public Camara getCamara() {
		return camara;
	}

	public ArrayList<Luz> getLuces() {
		return luces;
	}

	public Pantalla getPantalla() {
		return pantalla;
	}
}
