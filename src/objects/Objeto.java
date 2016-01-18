package objects;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import core.Color;
import scene.Rayo;

public abstract class Objeto {
	
	public enum tipo { TRIANGULO, PLANO, ESFERA }
	private Color kd;
	private Color ks;
	protected Vector3d n;
	private double iRefrac;
	private double iReflex;
	private double cRefrac;
	private boolean esComplejo;
	public boolean A = false;
	public boolean AD = false;
	public boolean AE = false;
	
	public abstract Point3d interseccion(Rayo r);
	

	public Objeto(Color kd, Vector3d n, double reflex, double iRefrac, double cRefrac) {
		this.kd = kd;
		this.ks = new Color(255,255,255);
		this.n = n;
		iReflex = reflex;
		this.iRefrac = iRefrac;
		this.cRefrac = cRefrac;
		
	}
	
	public Objeto(Color kd, double reflex, double iRefrac, double cRefrac, boolean complejo) {
		this.kd = kd;
		//this.ks = kd.calcularKD();
		this.ks = new Color(255,255,255);
		iReflex = reflex;
		this.iRefrac = iRefrac;
		this.cRefrac = cRefrac;
		this.esComplejo = complejo;
	}
	
	public Color getKd() {
		return kd;
	}

	public Color getKs() {
		return ks;
	}
	
	public void setN(Vector3d n) {
		this.n = n;
	}

	public Vector3d getN(Point3d punto) {
		return this.n;
	}


	public double getIndiceRefraccion() {
		return this.iRefrac;
	}


	public double getIndiceReflexion() {
		return this.iReflex;
	}


	public double getCoeficienteRefraccion() {
		return this.cRefrac;
	}


	public boolean esComplejo() {
		return this.esComplejo;
	}
	
	public Point3d[] interseccionCompleja(Rayo r){
		return new Point3d[]{new Point3d(0,0,0)};
	}
}
