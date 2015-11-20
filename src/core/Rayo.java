package core;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Rayo {
	Point3d p0;
	Point3d p1;
	Vector3d d;
	
	public Rayo (Point3d p0, Point3d p1) {
		this.p0 = p0;
		this.p1 = p1;
		d = new Vector3d();
		d.sub(p1, p0);
	}
	
	public Point3d getP0() {
		return p0;
	}

	public Point3d getP1() {
		return p1;
	}

	public Vector3d getD() {
		return d;
	}

	public Point3d getPunto(double lambda) {		
		Vector3d d = new Vector3d();
		d.sub(p1, p0);
		d.scale(lambda, d);
		Point3d p = new Point3d(p0);
		p.add(d);
		return p;
	}
	
	public double getAngulo(Vector3d v) {
		Vector3d a = new Vector3d(d);
		double radianes = a.angle(v);
		return Math.toDegrees(radianes);
	}
	
}
