package objects;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import core.Color;
import scene.Rayo;

public class Triangulo extends Objeto {
	private Point3d p1;
	private Point3d p2;
	private Point3d p3;
	private Vector3d n;

	public Triangulo(Point3d p1, Point3d p2, Point3d p3, Color kd, double reflex, double iRefrac, double cRefrac) {
		super(kd, reflex, iRefrac, cRefrac, false);
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		Vector3d vector1 = new Vector3d();
		Vector3d vector2 = new Vector3d();
		/*
		 * Calculas los dos vectores que salen del punto uno para poder calcular
		 * la normal
		 */
		vector1.x = p2.x - p1.x;
		vector1.y = p2.y - p1.y;
		vector1.z = p2.z - p1.z;
		vector2.x = p3.x - p1.x;
		vector2.y = p3.y - p1.y;
		vector2.z = p3.z - p1.z;
		/*
		 * Ahora el calculo de la normal es el producto vectorial de los dos
		 * vectores recien calculados
		 */
		Vector3d normal = new Vector3d();
		normal.cross(vector2, vector1);
		Vector3d solucion = normal;
		solucion.normalize();
		this.n = new Vector3d(solucion.x, solucion.y, solucion.z);
		this.p1 = new Point3d(p1);
		this.p2 = new Point3d(p2);
		this.p3 = new Point3d(p3);
		super.setN(n);
	}

	@Override
	public Point3d interseccion(Rayo r) {

		Vector3d d = new Vector3d(r.getD());
		Point3d p = new Point3d(this.p1);
		double inf = d.dot(this.n);
		if (inf < 0) {
			/*
			 * La normal del triangulo y el rayo van en sentidos inveros,
			 * entonces intersectan
			 */
			p.sub(r.getP0());
			Vector3d vec = new Vector3d(p);
			double sup = vec.dot(n);
			p = new Point3d(r.getPunto(sup / inf));
			Point3d p1 = new Point3d(this.p1);
			Point3d p2 = new Point3d(this.p2);
			Point3d p3 = new Point3d(this.p3);
			p2.sub(p1);
			p.sub(p1);
			Vector3d a = new Vector3d(p2);
			Vector3d b = new Vector3d(p);
			Vector3d mPN = new Vector3d();
			mPN.cross(a, b);
			double s1 = mPN.dot(n);
			p1 = new Point3d(this.p1);
			p2 = new Point3d(this.p2);
			p3 = new Point3d(this.p3);
			p = new Point3d(r.getPunto(sup / inf));
			p3.sub(p2);
			p.sub(p2);
			a = new Vector3d(p3);
			b = new Vector3d(p);
			mPN = new Vector3d();
			mPN.cross(a, b);
			double s2 = mPN.dot(n);
			p1 = new Point3d(this.p1);
			p2 = new Point3d(this.p2);
			p3 = new Point3d(this.p3);
			p = new Point3d(r.getPunto(sup / inf));
			p1.sub(p3);
			p.sub(p3);
			a = new Vector3d(p1);
			b = new Vector3d(p);
			mPN = new Vector3d();
			mPN.cross(a, b);
			double s3 = mPN.dot(n);
			if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0))
				return p = new Point3d(r.getPunto(sup / inf));
			else
				return null;
		} else {

			/*
			 * El rayo no intersecta, se devuelve null ( inf == 0) o El
			 * triangulo esta de espaldas al ojo, suponiendo que solo se ver por
			 * una cara, entonces este no se ve (inf > 0)
			 */
			return null;
		}
	}

	public Vector3d getN() {
		return this.n;
	}
}