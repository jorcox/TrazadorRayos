package math;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;

public class AlgebraLineal {
	
	/**
	 * Dado un punto P y una matriz M, devuelve el punto P*M
	 */
	public static Point3d multiplicar(Point3d p, Matrix4d m) {
		double x = (p.getX() * m.m00) + (p.getY() * m.m10) + p.getZ() * m.m20 + m.m30;
		double y = (p.getX() * m.m01) + (p.getY() * m.m11) + p.getZ() * m.m21 + m.m31;
		double z = (p.getX() * m.m02) + (p.getY() * m.m12) + p.getZ() * m.m22 + m.m32;
		double w = (p.getX() * m.m03) + (p.getY() * m.m13) + p.getZ() * m.m23 + m.m33;
		if (w!=1) {
			return new Point3d(x/w, y/w, z/w);
		} else return new Point3d(x, y, z);
	}
}
