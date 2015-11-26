package core;

import javax.vecmath.Point3d;
import javax.vecmath.Point4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

public class Esfera extends Objeto {
	
	double radio;
	Point3d centro;
	
	public Esfera(double radio, Point3d centro, Color kd, double reflex, double iRefrac, double cRefrac) {
		super(kd, reflex, iRefrac, cRefrac, true);
		this.radio = radio;
		this.centro = centro;
	}

	public double getRadio() {
		return radio;
	}
	
	public void setRadio(double radio) {
		this.radio = radio;
	}
	
	public Point3d getCentro() {
		return centro;
	}
	
	public void setCentro(Point3d centro) {
		this.centro = centro;
	}

	/**
	 * Calcula la interseccion entre la esfera y el rayo r. Si no hay interseccion,
	 * devuelve null. Si existe 1 interseccion, devuelve dicho punto. Si hay 2
	 * intersecciones, devuelve el punto mas cercano (el que se ve).
	 */
	public Point3d interseccion(Rayo r) {				
		/* Calcula a, b y c de la ecuacion de interseccion */
		Vector3d r1 = r.getD();
		double a = r1.dot(r.getD());
		a=Math.pow(r1.x,2)+Math.pow(r1.y,2)+Math.pow(r1.z,2);
		Point3d p0 = r.getP0();
		Vector3d ca = new Vector3d();
		ca.sub(p0, centro);
		double b = r1.dot(ca);
		double c = ca.dot(ca);
		c = c - Math.pow(radio, 2);
		
		/* Calcula el discriminante para decidir numero de soluciones */
		double d = Math.pow(b, 2) - a*c;
		if (d<0) {
			return null;
		} else if (d==0) {
			double lambda = (-2*b + Math.sqrt(4*Math.pow(b, 2) - 4*a*c)) / (double) (2*a);
			return r.getPunto(lambda);
		} else {
			double lambda1 = (-2*b + Math.sqrt(4*Math.pow(b, 2) - 4*a*c)) / (double) (2*a);
			double lambda2 = (-2*b - Math.sqrt(4*Math.pow(b, 2) - 4*a*c)) / (double) (2*a);
			if (lambda1<0 && lambda2<0) {
				return null;
			} else if (lambda1>0 && lambda2<0) {
				return r.getPunto(lambda1);
			} else if (lambda1>lambda2 && lambda2>0) {
				return r.getPunto(lambda2);
			} else if (lambda1<0 && lambda2>0) {
				return r.getPunto(lambda2);
			} else {
				return r.getPunto(lambda1);
			}
		}
 	}
	
	/**
	 * Calcula la interseccion entre la esfera y el rayo r. Si no hay interseccion,
	 * devuelve null. Si existe 1 interseccion, devuelve dicho punto. Si hay 2
	 * intersecciones, devuelve el punto mas cercano (el que se ve).
	 */
	public Point3d[] interseccionCompleja(Rayo r) {				
		/* Calcula a, b y c de la ecuacion de interseccion */
		Vector3d r1 = r.getD();
		double a = r1.dot(r.getD());
		a=Math.pow(r1.x,2)+Math.pow(r1.y,2)+Math.pow(r1.z,2);
		Point3d p0 = r.getP0();
		Vector3d ca = new Vector3d();
		ca.sub(p0, centro);
		double b = r1.dot(ca);
		double c = ca.dot(ca);
		c = c - Math.pow(radio, 2);
		
		/* Calcula el discriminante para decidir numero de soluciones */
		double d = Math.pow(b, 2) - a*c;
		if (d<0) {
			return null;
		} else if (d==0) {
			double lambda = (-2*b + Math.sqrt(4*Math.pow(b, 2) - 4*a*c)) / (double) (2*a);
			return new Point3d[] {r.getPunto(lambda)};
		} else {
			double lambda1 = (-2*b + Math.sqrt(4*Math.pow(b, 2) - 4*a*c)) / (double) (2*a);
			double lambda2 = (-2*b - Math.sqrt(4*Math.pow(b, 2) - 4*a*c)) / (double) (2*a);
			if (lambda1<0 && lambda2<0) {
				return null;
			} else if (lambda1>0 && lambda2<0) {
				return new Point3d[] {r.getPunto(lambda1)};
			} else if (lambda1>lambda2 && lambda2>0) {
				return new Point3d[] {r.getPunto(lambda2),r.getPunto(lambda1)};
			} else if (lambda1<0 && lambda2>0) {
				return new Point3d[] {r.getPunto(lambda2)};
			} else {
				return new Point3d[] {r.getPunto(lambda1)};
			}
		}
 	}
	
	public Vector3d getN(Point3d punto) {
		Vector3d n = new Vector3d();
		n.sub(punto, centro);
		return n;
	}

}
