package scene;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Rayo {
	Point3d p0;
	Point3d p1;
	Vector3d d;
	
	public Rayo (Point3d p0, Point3d p1) {
		this.p0 = new Point3d(p0);
		this.p1 = new Point3d(p1);
		d = new Vector3d();
		d.sub(p1, p0);
	}
	
	public Rayo(Vector3d vector, Point3d punto) {
		d = new Vector3d(vector);
		p0 = new Point3d(punto);
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
		Vector3d D = new Vector3d(d);
		D.scale(lambda, D);
		Point3d p = new Point3d(p0);
		p.add(D);
		return p;
	}
	
	public double getAngulo(Vector3d v) {
		Vector3d a = new Vector3d(d);
		double radianes = a.angle(v);
		return Math.toDegrees(radianes);
	}
	
}
