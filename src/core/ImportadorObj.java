package core;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;

import math.AlgebraLineal;

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
					Matrix4d matrizAlejar = new Matrix4d(1, 0, 0, 0,
							0, 1, 0, 0,
							0, 0, 1, 0,
							0, 0, -1000, 1);
					Matrix4d matrizAumentar = new Matrix4d(50, 0, 0, 0,
							0, 50, 0, 0,
							0, 0, 50, 0,
							0, 0, 0, 1);
					Matrix4d matrizCamaraMundo = Transformacion.obtenerMatrizCamaraMundo(cam);
					Matrix4d matrizGiro = new Matrix4d(0.7071, 0, -0.7071, 0,
							0, 1, 0, 0,
							0.7071, 0, 0.7071, 0,
							0, 0, 0, 1);
//					p1 = AlgebraLineal.multiplicar(p1, matrizGiro);
//					p2 = AlgebraLineal.multiplicar(p2, matrizGiro);
//					p3 = AlgebraLineal.multiplicar(p3, matrizGiro);
					p1 = AlgebraLineal.multiplicar(p1, matrizAumentar);
					p2 = AlgebraLineal.multiplicar(p2, matrizAumentar);
					p3 = AlgebraLineal.multiplicar(p3, matrizAumentar);
					p1 = AlgebraLineal.multiplicar(p1, matrizAlejar);
					p2 = AlgebraLineal.multiplicar(p2, matrizAlejar);
					p3 = AlgebraLineal.multiplicar(p3, matrizAlejar);
					p1 = AlgebraLineal.multiplicar(p1, matrizCamaraMundo);
					p2 = AlgebraLineal.multiplicar(p2, matrizCamaraMundo);
					p3 = AlgebraLineal.multiplicar(p3, matrizCamaraMundo);
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