package objects;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import core.Color;
import scene.Rayo;

public class Triangulo extends Objeto {
	private Point3d p1;
	private Point3d p2;
	private Point3d p3;

	public Triangulo(Point3d p1, Point3d p2, Point3d p3, Color kd, double reflex, double iRefrac, double cRefrac) {
		super(kd, reflex, iRefrac, cRefrac, false);
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		Point3d aux = new Point3d(p2);
		Vector3d a = new Vector3d(aux);
		a.sub(p1);
		Point3d aux2 = new Point3d(p3);
		Vector3d b = new Vector3d(aux2);
		b.sub(p1);
		a.cross(a, b);
		super.setN(a);
	}

	@Override
	public Point3d interseccion(Rayo r) {
		Point3d a = new Point3d(r.getP0());
		Vector3d resta = new Vector3d(p1);
		resta.sub(a);
		double numerador = resta.dot(super.getN(a));
		Vector3d d = new Vector3d(r.getD());
		double denominador = d.dot(super.getN(a));
		
		/* 
		 * Si d*n = 0, no hay interseccion.
		 * Si d*n > 0, la cara esta del reves.
		 */
		if (denominador>=0) {
			return null;
		} else {
			double lambda = numerador / denominador;
			/* Si l<0, esta entre camara y pantalla (no se ve) */
			if (lambda<0) {
				return null;
			} else {
				/* Comprobacion de que esta dentro del triangulo */
				Point3d p = new Point3d(r.getPunto(lambda));
				Vector3d p2p1 = new Vector3d(p2);
				p2p1.sub(p1);
				Vector3d pp1 = new Vector3d(p);
				pp1.sub(p1);
				Vector3d p3p2 = new Vector3d(p3);
				p3p2.sub(p2);
				Vector3d pp2 = new Vector3d(p);
				pp2.sub(p2);
				Vector3d p1p3 = new Vector3d(p1);
				p1p3.sub(p3);
				Vector3d pp3 = new Vector3d(p);
				pp3.sub(p3);
				Vector3d cross1 = new Vector3d();
				cross1.cross(p2p1, pp1);
				double s1 = cross1.dot(super.getN(a));
				Vector3d cross2 = new Vector3d();
				cross2.cross(p3p2, pp2);
				double s2 = cross2.dot(super.getN(a));
				Vector3d cross3 = new Vector3d();
				cross3.cross(p1p3, pp3);
				double s3 = cross3.dot(super.getN(a));
				if ((s1>0 && s2>0 && s3>0) || (s1==0 && s2==0 && s3==0) 
						|| (s1<0 && s2<0 && s3<0)) {
					return r.getPunto(lambda);
				} else return null;
			}
		}
	}
}
