package core;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Point4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

public class Pantalla {
	private int l;
	private int t;
	private double f;
	private int nC;
	private int nR;
	private double varU;
	private double varV;
	Point3d[][] coordCamara;
	Point3d[][] coordMundo;

	public Pantalla(int l, int t, double f, int nC, int nR) {
		this.l = l;
		this.t = t;
		coordCamara = new Point3d[nC][nR];
		coordMundo = new Point3d[nC][nR];
		this.f = f;
		this.nC = nC;
		this.nR = nR;
		int L = 0;
		int R = 0;
		int T = 0;
		int B = 0;
		L = l/2;
		R = L;
		T = t/2;
		B = T;
		
		varU = (R-(-L))/(double)(nC-1);
		varV = (T-(-B))/(double)(nR-1);
	}
	
	public void calcularCoordenadasCamaraYMundo(Camara cam){
		Matrix4d mCW = new Matrix4d(cam.getU().x,cam.getU().y,cam.getU().z,0,
									cam.getV().x,cam.getV().y,cam.getV().z,0,
									cam.getW().x,cam.getW().y,cam.getW().z,0,
									cam.getE().x,cam.getE().y,cam.getE().z,1);
		int mColumnas = nC/2;
		int mFilas = nR/2;
		for(int i = 0; i < nC; i++){
			for(int j = 0; j < nR; j++){
				if(i==159&&j==106){
					System.out.println("lololo");
				}
				Point3d punto = new Point3d((i-mColumnas)*varU, (-j+mFilas)*varV, -f);
				coordCamara[i][j] = punto;
				Point4d dd = multiplyPointMatrix(new Point4d(punto.x,punto.y,punto.z,1), mCW);
				coordMundo[i][j] =new Point3d(dd.x, dd.y, dd.z);
			}
		}
	}
	
	public static Point4d multiplyPointMatrix(Point4d point, Matrix4d matrix) {
		return new Point4d(point.x * matrix.m00 + point.y * matrix.m10
				+ point.z * matrix.m20 + point.w * matrix.m30, point.x
				* matrix.m01 + point.y * matrix.m11 + point.z * matrix.m21
				+ point.w * matrix.m31, point.x * matrix.m02 + point.y
				* matrix.m12 + point.z * matrix.m22 + point.w * matrix.m32,
				point.x * matrix.m03 + point.y * matrix.m13 + point.z
						* matrix.m23 + point.w * matrix.m33);
	}
	
	public static Point4d multiplicar(Matrix4d A, double[] x) {
		return new Point4d(x[0] * A.m00 + x[1] * A.m10
				+ x[2] * A.m20 + x[3] * A.m30, x[0]
				* A.m01 + x[1] * A.m11 + x[2] * A.m21
				+ x[3] * A.m31, x[0] * A.m02 + x[1]
				* A.m12 + x[2] * A.m22 + x[3] * A.m32,
				x[0] * A.m03 + x[1] * A.m13 + x[2]
						* A.m23 + x[3] * A.m33);
//        double[] y = new double[x.length];
//        for (int i = 0; i < 4; i++)
//            for (int j = 0; j < 4; j++)
//                y[i] += A.getElement(i, j) * x[j];
//        return new Point3d(y[0], y[1], y[2]);
    }

	public int getL() {
		return l;
	}

	public int getT() {
		return t;
	}

	public double getF() {
		return f;
	}

	public int getnC() {
		return nC;
	}

	public int getnR() {
		return nR;
	}

	public Point3d[][] getCoordCamara() {
		return coordCamara;
	}

	public Point3d[][] getCoordMundo() {
		return coordMundo;
	}
}
