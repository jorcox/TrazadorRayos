package core;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Esfera extends Objeto {
	
	double radio;
	Point3d centro;
	
	public Esfera(double radio, Point3d centro, Color color, double kd) {
		super(color, kd);
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

}
