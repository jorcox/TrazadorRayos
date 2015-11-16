package core;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
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
	private Point3d[][] coordCamara;
	private Point3d[][] coordMundo;

	public Pantalla(int l, int t, double f, int nC, int nR) {
		this.l = l;
		this.t = t;
		coordCamara = new Point3d[nC][nR];
		coordMundo = new Point3d[nC][nR];
		this.f = f;
		this.nC = nC;
		this.nR = nR;
		varU = (l-(-l))/(nC-1);
		varV = (t-(-t))/(nR-1);
	}
	
	public void CalcularCoordenadasCamaraYMundo(Camara cam){
		Matrix4d mCW = new Matrix4d(cam.getU().x,cam.getU().y,cam.getU().z,0,
									cam.getV().x,cam.getV().y,cam.getV().z,0,
									cam.getW().x,cam.getW().y,cam.getW().z,0,
									cam.getE().x,cam.getE().y,cam.getE().z,1);
		int mColumnas = nC/2;
		int mFilas = nR/2;
		for(int i = 0; i < nC; i++){
			for(int j = 0; j < nR; j++){
				Point3d punto = new Point3d(i-mColumnas*varU, (-j)+mFilas*varV, -f);
				coordCamara[i][j] = punto;
				coordMundo[i][j] = multiplicar(mCW, new double[]{punto.x,punto.y,punto.z,1});
			}
		}
		System.out.println("jamon");
	}
	
	public static Point3d multiplicar(Matrix4d A, double[] x) {
        double[] y = new double[x.length];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                y[i] += A.getElement(i, j) * x[j];
        return new Point3d(y[0], y[1], y[2]);
    }
}
