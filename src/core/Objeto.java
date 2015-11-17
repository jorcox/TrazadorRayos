package core;

import javax.vecmath.Point3d;

public abstract class Objeto {
	
	public enum tipo { TRIANGULO, PLANO, ESFERA }
	private Color color;
	private double kd;
	
	public abstract Point3d interseccion(Rayo r);
	
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
	
}
