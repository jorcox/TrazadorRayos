package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.vecmath.Point3d;
import objects.Triangulo;
import scene.Camara;

public class ImportadorObj {

	public static ArrayList<Triangulo> leerFigura(String fichero, Camara cam) {
		ArrayList<Triangulo> caras = new ArrayList<Triangulo>();
		ArrayList<Point3d> vertices = new ArrayList<Point3d>();
		vertices.add(null);
		try {
			Scanner obj = new Scanner(new File(fichero));
			while (obj.hasNextLine()) {
				String v = obj.next();
				switch (v) {
				// vertices
				case "v":
					vertices.add(new Point3d(Float.parseFloat(obj.next()), Float.parseFloat(obj.next()),
							Float.parseFloat(obj.next())));
					break;
				// face
				case "f":
					int v1 = leerV(obj.next());
					int v2 = leerV(obj.next());
					int v3 = leerV(obj.next());
					Point3d p1 = new Point3d(vertices.get(v1));
					Point3d p2 = new Point3d(vertices.get(v2));
					Point3d p3 = new Point3d(vertices.get(v3));
					
					Transformacion aumento = Transformacion.getMatrizEscala(0.3, 0.3, 0.3);
					Transformacion lejos = Transformacion.getMatrizTraslacion(1, 40, -1700);
					Transformacion camaraMundo = Transformacion.getMatrizCamaraMundo(cam);
					Transformacion giroX = Transformacion.getMatrizGiroX(30);
					p1 = giroX.transformar(p1);
					p2 = giroX.transformar(p2);
					p3 = giroX.transformar(p3);
					p1 = aumento.transformar(p1);
					p2 = aumento.transformar(p2);
					p3 = aumento.transformar(p3);
					p1 = lejos.transformar(p1);
					p2 = lejos.transformar(p2);
					p3 = lejos.transformar(p3);

					p1 = camaraMundo.transformar(p1);
					p2 = camaraMundo.transformar(p2);
					p3 = camaraMundo.transformar(p3);
					
					
					Triangulo t = new Triangulo(p1,p2,p3,new Color(255,0,0),0,0,1.3333333333);
					caras.add(t);
					break;
				}
				try {
					obj.nextLine();
				} catch (Exception e) {
				}
			}
			obj.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return caras;
	}

	private static int leerV(String v1) {
		String lv1[] = v1.split("/");

		return Integer.parseInt(lv1[0]);

	}
}