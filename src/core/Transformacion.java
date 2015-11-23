package core;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Transformacion {
	
	private Matrix4d m;
	
	private Transformacion(Matrix4d m) {
		this.m = m;
	}
	
	public static Transformacion getTranslationMatrix(double x, double y, double z) {
		Matrix4d m = new Matrix4d(1, 0, 0, 0,
								0, 1, 0, 0,
								0, 0, 1, 0,
								x, y, z, 1);
		return new Transformacion(m);
	}
	
	public Point3d traslacion(Point3d p) {
		Point3d res = multiplicar(p);
		return res;
	}
	
	public Vector3d traslacion(Vector3d v) {
		Point3d res = multiplicar(new Point3d(v));
		return new Vector3d(res);
	}
	
	private Point3d multiplicar(Point3d p) {
		double x = (p.getX() * m.m00) + (p.getY() * m.m10) + p.getZ() * m.m20 + m.m30;
		double y = (p.getX() * m.m01) + (p.getY() * m.m11) + p.getZ() * m.m21 + m.m31;
		double z = (p.getX() * m.m02) + (p.getY() * m.m12) + p.getZ() * m.m22 + m.m32;
		double w = (p.getX() * m.m03) + (p.getY() * m.m13) + p.getZ() * m.m23 + m.m33;
		if (w!=1) {
			return new Point3d(x/w, y/w, z/w);
		} else return new Point3d(x, y, z);
	}
	
}
