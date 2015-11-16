package core;

import javax.vecmath.Point3d;

public abstract class Objeto {
	
	public enum tipo { TRIANGULO, PLANO, ESFERA }
	private Color color;
	
	public abstract Point3d interseccion(Rayo r);
	
	public Objeto(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
}
