package core;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public abstract class Objeto {
	
	public enum tipo { TRIANGULO, PLANO, ESFERA }
	private Color color;
	private double kd;
	private Vector3d n;
	
	public abstract Point3d interseccion(Rayo r);
	
	
	public Objeto(Color color, double kd, Vector3d n) {
		this.color = color;
		this.kd = kd;
		this.n = n;
	}
	
	public Objeto(Color color, double kd) {
		this.color = color;
		this.kd = kd;
	}
	
	public Color getColor() {
		return color;
	}

	public double getKd() {
		return kd;
	}
	
	public void setN(Vector3d n) {
		this.n = n;
	}

	public Vector3d getN(Point3d punto) {
		return this.n;
	}
}
