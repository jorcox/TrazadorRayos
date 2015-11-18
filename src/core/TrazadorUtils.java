package core;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class TrazadorUtils {
	
	public static Pantalla getPantalla(String[] orden) throws FicheroDatosException {
		int l = Integer.parseInt(orden[1]);
		int t = Integer.parseInt(orden[2]);
		double f = Double.parseDouble(orden[3]);
		int nC = Integer.parseInt(orden[4]);
		int nR = Integer.parseInt(orden[5]);
		return new Pantalla(l, t, f, nC, nR);
	}
	
	public static Point3d getOjo(String[] orden) throws FicheroDatosException {
		double x = Double.parseDouble(orden[1]);
		double y = Double.parseDouble(orden[2]);
		double z = Double.parseDouble(orden[3]);
		return new Point3d(x,y,z);
	}
	
	public static Vector3d getG(String[] orden) throws FicheroDatosException {
		double x = Double.parseDouble(orden[1]);
		double y = Double.parseDouble(orden[2]);
		double z = Double.parseDouble(orden[3]);
		return new Vector3d(x,y,z);
	}
	
	public static Plano getPlano(String[] orden) throws FicheroDatosException {
		double px = Double.parseDouble(orden[1]);
		double py = Double.parseDouble(orden[2]);
		double pz = Double.parseDouble(orden[3]);
		double nx = Double.parseDouble(orden[4]);
		double ny = Double.parseDouble(orden[5]);
		double nz = Double.parseDouble(orden[6]);
		int cR = Integer.parseInt(orden[7]);
		int cG = Integer.parseInt(orden[8]);
		int cB = Integer.parseInt(orden[9]);
		Point3d p = new Point3d(px,py,pz);
		Vector3d n = new Vector3d(nx,ny,nz);
		Color c = new Color(cR,cG,cB);
		return new Plano(p, n, c, 0.5);
	}
	
	public static Triangulo getTriangulo(String[] orden) throws FicheroDatosException {
		double p1x = Double.parseDouble(orden[1]);
		double p1y = Double.parseDouble(orden[2]);
		double p1z = Double.parseDouble(orden[3]);
		double p2x = Double.parseDouble(orden[4]);
		double p2y = Double.parseDouble(orden[5]);
		double p2z = Double.parseDouble(orden[6]);
		double p3x = Double.parseDouble(orden[7]);
		double p3y = Double.parseDouble(orden[8]);
		double p3z = Double.parseDouble(orden[9]);
		int cR = Integer.parseInt(orden[10]);
		int cG = Integer.parseInt(orden[11]);
		int cB = Integer.parseInt(orden[12]);
		Point3d p1 = new Point3d(p1x, p1y, p1z);
		Point3d p2 = new Point3d(p2x, p2y, p2z);
		Point3d p3 = new Point3d(p3x, p3y, p3z);
		Color c = new Color(cR, cG, cB); 
		return new Triangulo(p1, p2, p3, c, 0.5);
	}
	
	public static Esfera getEsfera(String[] orden) throws FicheroDatosException {
		double radio = Double.parseDouble(orden[1]);
		double px = Double.parseDouble(orden[2]);
		double py = Double.parseDouble(orden[3]);
		double pz = Double.parseDouble(orden[4]);
		int cR = Integer.parseInt(orden[5]);
		int cG = Integer.parseInt(orden[6]);
		int cB = Integer.parseInt(orden[7]);
		Point3d centro = new Point3d(px, py, pz);
		Color c = new Color(cR, cG, cB);
		return new Esfera(radio, centro, c, 0.5);
	}
	
	public static Luz getLuz(String[] orden) throws FicheroDatosException {
		double px = Double.parseDouble(orden[1]);
		double py = Double.parseDouble(orden[2]);
		double pz = Double.parseDouble(orden[3]);
		double intensidad = Double.parseDouble(orden[4]);
		Point3d p = new Point3d(px, py, pz);
		return new Luz(p, intensidad);
	}
	
}
