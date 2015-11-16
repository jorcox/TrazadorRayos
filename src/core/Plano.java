package core;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Plano extends Objeto{
	
	private Point3d p;
	private Point3d p0;
	private Vector3d n;
	
	public Plano (Point3d p, Vector3d n, Color color) {
		super(color);
		this.p = p;
		this.n = n;
	}
	
	public Vector3d getNormal() {
		return n;
	}

	public Point3d interseccion(Rayo r) {
		// TODO Auto-generated method stub
		return null;
	}
}
