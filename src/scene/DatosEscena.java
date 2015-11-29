package scene;

import java.util.ArrayList;

import objects.Objeto;

public class DatosEscena {
	
	private Camara camara;
	private Luz luz;
	private Pantalla pantalla;
	private ArrayList<Objeto> objetos;
	private double iAmbiental;
	
	public DatosEscena(Camara cam, Luz l, Pantalla p, ArrayList<Objeto> obj, double iAmb) {
		camara = cam;
		luz = l;
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

	public Luz getLuz() {
		return luz;
	}

	public Pantalla getPantalla() {
		return pantalla;
	}
}
