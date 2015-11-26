package core;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.vecmath.Point3d;

public class ImportadorObj {

	public static void main(String[] args) {
		new ImportadorObj().leerFigura("bird.obj");
	}

	public ArrayList<Triangulo> leerFigura(String fichero) {
		ArrayList<Triangulo> caras = new ArrayList<Triangulo>();
		ArrayList<Point3d> vertices = new ArrayList<Point3d>();
		vertices.add(null);
		try {
			Scanner obj = new Scanner(new File(fichero));
			while (obj.hasNextLine()) {
				String linea = obj.nextLine();
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
					Triangulo t = new Triangulo(vertices.get(v1), vertices.get(v2), vertices.get(v3),new Color(255,180,182),1.0,0.0,1.3333333333);
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

	private int leerV(String v1) {
		String lv1[] = v1.split("/");

		return Integer.parseInt(lv1[0]);

	}
}