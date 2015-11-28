package core;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import math.AlgebraLineal;
import scene.Camara;

public class Transformacion {
	
	private Matrix4d m;
	
	private Transformacion(Matrix4d m) {
		this.m = m;
	}
	
	public static Transformacion getMatrizCamaraMundo(Camara cam) {
		Matrix4d m = new Matrix4d(cam.getU().x,cam.getU().y,cam.getU().z,0,
								cam.getV().x,cam.getV().y,cam.getV().z,0,
								cam.getW().x,cam.getW().y,cam.getW().z,0,
								cam.getE().x,cam.getE().y,cam.getE().z,1);
		return new Transformacion(m);
	}
	
	public static Transformacion getMatrizTraslacion(double x, double y, double z) {
		Matrix4d m = new Matrix4d(1, 0, 0, 0,
								0, 1, 0, 0,
								0, 0, 1, 0,
								x, y, z, 1);
		return new Transformacion(m);
	}
	
	public static Transformacion getMatrizEscala(double x, double y, double z) {
		Matrix4d m = new Matrix4d(x, 0, 0, 0,
								0, y, 0, 0,
								0, 0, z, 0,
								0, 0, 0, 1);
		return new Transformacion(m);
	}
	
	public Point3d transformar(Point3d p) {
		Point3d res = AlgebraLineal.multiplicar(p, m);
		return res;
	}
	
	public Vector3d transformar(Vector3d v) {
		Point3d res = AlgebraLineal.multiplicar(new Point3d(v), m);
		return new Vector3d(res);
	}
	
}
