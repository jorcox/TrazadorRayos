package core;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public abstract class Objeto {
	
	public enum tipo { TRIANGULO, PLANO, ESFERA }
	private Color kd;
	private Color ks;
	private Vector3d n;
	
	public abstract Point3d interseccion(Rayo r);
	
	
	public Objeto(Color kd, Vector3d n) {
		this.kd = kd;
		this.ks = kd.calcularKD();
		this.n = n;
	}
	
	public Objeto(Color kd) {
		this.kd = kd;
		//this.ks = kd.calcularKD();
		this.ks = new Color(255,255,255);
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
}
