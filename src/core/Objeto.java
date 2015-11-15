package core;

import javax.vecmath.Point3d;

public abstract class Objeto {
	public enum tipo { TRIANGULO, PLANO, ESFERA }
	
	public abstract Point3d interseccion(Rayo r);
	
}
