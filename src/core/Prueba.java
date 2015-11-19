package core;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Prueba {

	public static void main(String[] args) {
//		Pantalla p = new Pantalla(200, 200, 20, 1920,1080);
//		Point3d g = new Point3d(1,1,1);
//		Vector3d v = new Vector3d(-1,-3,2);
//		Camara c = new Camara(g,v);
//		p.calcularCoordenadasCamaraYMundo(c);
//		System.out.println("melon");
		
//		Esfera esf = new Esfera(2, new Point3d(0,0,0), new Color(255,0,0), 1);
//		Rayo r = new Rayo(new Point3d(1,1,-5), new Point3d(-1,-1,5));
//		Point3d inter = esf.interseccion(r);
//		System.out.println(inter);
		
//		Point3d origen = new Point3d(0,0,0);
//		Point3d luz = new Point3d(2,2,0);
//		Point3d pN = new Point3d(0,2,0);
//		pN.sub(origen);
//		Rayo l = new Rayo(origen, luz);
//		Vector3d n = new Vector3d(pN);
//		Vector3d r = l.getReflejado(n);
//		System.out.println(r);
		
		Rayo r = new Rayo(new Point3d(0,0,0), new Point3d(1,0,0));
		Vector3d v2 = new Vector3d(1,-1,0);
		double angulo = r.getAngulo(v2);
		System.out.println(angulo);
	}
}
