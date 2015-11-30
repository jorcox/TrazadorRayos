package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import objects.*;
import scene.*;

public class TrazadorUtils {
	
	private static Camara cam;
	
	public static DatosEscena cargarObjetos(String rutaFichero) {
		try {
			File fichero = new File(rutaFichero);
			BufferedReader reader = new BufferedReader(new FileReader(fichero));
			ArrayList<String[]> lineas = new ArrayList<String[]>();
			
			String linea = reader.readLine();
			while (linea != null && !linea.contains("objetos")) {
				lineas.add(linea.split(" "));
				linea = reader.readLine();
			}
			
			Point3d ojo = new Point3d();
			Vector3d g = new Vector3d();
			Pantalla pantalla = null;
			Luz luz = null;
			double iAmbiental = 0;
			
			for (String[] orden: lineas) {
				if (orden[0].equals("pantalla")) {
					pantalla = TrazadorUtils.getPantalla(orden);
				} else if (orden[0].equals("ojo")) {
					ojo = TrazadorUtils.getOjo(orden);
				} else if (orden[0].equals("g")) {
					g = TrazadorUtils.getG(orden);
				} else if (orden[0].equals("iAmbiental")) {
					iAmbiental = Double.parseDouble(orden[1]);
				}
			}
			cam = new Camara(ojo, g);
			
			lineas = new ArrayList<String[]>();
			linea = reader.readLine();
			while (linea != null) {
				lineas.add(linea.split(" "));
				linea = reader.readLine();
			}
			reader.close();
			
			ArrayList<Objeto> objetos = new ArrayList<Objeto>();
			
			for (String[] orden: lineas) {
				if (orden[0].equals("plano")) {
					objetos.add(TrazadorUtils.getPlano(orden));
				} else if (orden[0].equals("triangulo")) {
					objetos.add(TrazadorUtils.getTriangulo(orden));
				} else if (orden[0].equals("esfera")) {
					objetos.add(TrazadorUtils.getEsfera(orden));
				} else if (orden[0].equals("luz")) {
					luz = TrazadorUtils.getLuz(orden);
				} else if (orden[0].equals("complejo")) {
					objetos.addAll(ImportadorObj.leerFigura(orden[1], cam));
				}
			}
			
			return new DatosEscena(cam, luz, pantalla, objetos, iAmbiental);
			
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public static Pantalla getPantalla(String[] orden) throws FicheroDatosException {
		double l = 0;
		double t = 0;
		double f = 0;
		int nC = 0;
		int nR = 0;
		for (int i=1; i<orden.length; i++) {
			String[] partes = orden[i].split(":");
			String clave = partes[0];
			String valor = partes[1];
			switch(clave) {
			case "l":
				l = Double.parseDouble(valor);
				break;
			case "t":
				t = Double.parseDouble(valor);
				break;
			case "f":
				f = Double.parseDouble(valor);
				break;
			case "nC":
				nC = Integer.parseInt(valor);
				break;
			case "nR":
				nR = Integer.parseInt(valor);
				break;
			default:
				throw new FicheroDatosException("Error de fichero");
			}
		}
		return new Pantalla(l, t, f, nC, nR);
	}
	
	public static Point3d getOjo(String[] orden) throws FicheroDatosException {
		double x = 0;
		double y = 0;
		double z = 0;
		for (int i=1; i<orden.length; i++) {
			String[] partes = orden[i].split(":");
			String clave = partes[0];
			String valor = partes[1];
			switch(clave) {
			case "x":
				x = Double.parseDouble(valor);
				break;
			case "y":
				y = Double.parseDouble(valor);
				break;
			case "z":
				z = Double.parseDouble(valor);
				break;
			default:
				throw new FicheroDatosException("Error de fichero");
			}
		}
		return new Point3d(x,y,z);
	}
	
	public static Vector3d getG(String[] orden) throws FicheroDatosException {
		double x = 0;
		double y = 0;
		double z = 0;
		for (int i=1; i<orden.length; i++) {
			String[] partes = orden[i].split(":");
			String clave = partes[0];
			String valor = partes[1];
			switch(clave) {
			case "x":
				x = Double.parseDouble(valor);
				break;
			case "y":
				y = Double.parseDouble(valor);
				break;
			case "z":
				z = Double.parseDouble(valor);
				break;
			default:
				throw new FicheroDatosException("Error de fichero");
			}
		}
		return new Vector3d(x,y,z);
	}
	
	public static Plano getPlano(String[] orden) throws FicheroDatosException {
		double px = 0; double py = 0; double pz = 0;
		double nx = 0; double ny = 0; double nz = 0;
		int cR = 0; int cG = 0; int cB = 0;
		double iRefl = 0; double iRefr = 0; double cRefr = 0;
		
		for (int i=1; i<orden.length; i++) {
			String[] partes = orden[i].split(":");
			String clave = partes[0];
			String valor = partes[1];
			switch(clave) {
			case "px":
				px = Double.parseDouble(valor);
				break;
			case "py":
				py = Double.parseDouble(valor);
				break;
			case "pz":
				pz = Double.parseDouble(valor);
				break;
			case "nx":
				nx = Double.parseDouble(valor);
				break;
			case "ny":
				ny = Double.parseDouble(valor);
				break;
			case "nz":
				nz = Double.parseDouble(valor);
				break;
			case "cR":
				cR = Integer.parseInt(valor);
				break;
			case "cG":
				cG = Integer.parseInt(valor);
				break;
			case "cB":
				cB = Integer.parseInt(valor);
				break;
			case "iRefl":
				iRefl = Double.parseDouble(valor);
				break;
			case "iRefr":
				iRefr = Double.parseDouble(valor);
				break;
			case "cRefr":
				cRefr = Double.parseDouble(valor);
				break;
			default:
				throw new FicheroDatosException("Error de fichero");
			}
		}
		Transformacion aMundo = Transformacion.getMatrizCamaraMundo(cam);
		Point3d p = new Point3d(px,py,pz);
		p = aMundo.transformar(p);
		Vector3d n = new Vector3d(nx,ny,nz);
		n = aMundo.transformar(n);
		Color kd = new Color(cR,cG,cB);
		return new Plano(p, n, kd,iRefl, iRefr, cRefr);
	}
	
	public static Triangulo getTriangulo(String[] orden) throws FicheroDatosException {
		double p1x = 0; double p1y = 0; double p1z = 0;
		double p2x = 0; double p2y = 0; double p2z = 0;
		double p3x = 0; double p3y = 0; double p3z = 0;
		int cR = 0; int cG = 0; int cB = 0;
		double iRefl = 0; double iRefr = 0; double cRefr = 0;
		
		for (int i=1; i<orden.length; i++) {
			String[] partes = orden[i].split(":");
			String clave = partes[0];
			String valor = partes[1];
			switch(clave) {
			case "p1x":
				p1x = Double.parseDouble(valor);
				break;
			case "p1y":
				p1y = Double.parseDouble(valor);
				break;
			case "p1z":
				p1z = Double.parseDouble(valor);
				break;
			case "p2x":
				p2x = Double.parseDouble(valor);
				break;
			case "p2y":
				p2y = Double.parseDouble(valor);
				break;
			case "p2z":
				p2z = Double.parseDouble(valor);
				break;
			case "p3x":
				p3x = Double.parseDouble(valor);
				break;
			case "p3y":
				p3y = Double.parseDouble(valor);
				break;
			case "p3z":
				p3z = Double.parseDouble(valor);
				break;
			case "cR":
				cR = Integer.parseInt(valor);
				break;
			case "cG":
				cG = Integer.parseInt(valor);
				break;
			case "cB":
				cB = Integer.parseInt(valor);
				break;
			case "iRefl":
				iRefl = Double.parseDouble(valor);
				break;
			case "iRefr":
				iRefr = Double.parseDouble(valor);
				break;
			case "cRefr":
				cRefr = Double.parseDouble(valor);
				break;
			default:
				throw new FicheroDatosException("Error de fichero");
			}
		}
		Transformacion aMundo = Transformacion.getMatrizCamaraMundo(cam);
		Point3d p1 = new Point3d(p1x, p1y, p1z);
		p1 = aMundo.transformar(p1);
		Point3d p2 = new Point3d(p2x, p2y, p2z);
		p2 = aMundo.transformar(p2);
		Point3d p3 = new Point3d(p3x, p3y, p3z);
		p3 = aMundo.transformar(p3);
		Color kd = new Color(cR, cG, cB); 
		return new Triangulo(p1, p2, p3, kd, iRefl, iRefr, cRefr);
	}
	
	public static Esfera getEsfera(String[] orden) throws FicheroDatosException {
		double radio = 0;
		double px = 0; double py = 0; double pz = 0;
		int cR = 0; int cG = 0; int cB = 0;
		double iRefl = 0; double iRefr = 0; double cRefr = 0;
		
		for (int i=1; i<orden.length; i++) {
			String[] partes = orden[i].split(":");
			String clave = partes[0];
			String valor = partes[1];
			switch(clave) {
			case "radio":
				radio = Double.parseDouble(valor);
				break;
			case "px":
				px = Double.parseDouble(valor);
				break;
			case "py":
				py = Double.parseDouble(valor);
				break;
			case "pz":
				pz = Double.parseDouble(valor);
				break;
			case "cR":
				cR = Integer.parseInt(valor);
				break;
			case "cG":
				cG = Integer.parseInt(valor);
				break;
			case "cB":
				cB = Integer.parseInt(valor);
				break;
			case "iRefl":
				iRefl = Double.parseDouble(valor);
				break;
			case "iRefr":
				iRefr = Double.parseDouble(valor);
				break;
			case "cRefr":
				cRefr = Double.parseDouble(valor);
				break;
			default:
				throw new FicheroDatosException("Error de fichero");
			}
		}
		Transformacion aMundo = Transformacion.getMatrizCamaraMundo(cam);
		Point3d centro = new Point3d(px, py, pz);
		centro = aMundo.transformar(centro);
		Color kd = new Color(cR, cG, cB);
		return new Esfera(radio, centro, kd, iRefl, iRefr, cRefr);
	}
	
	public static Luz getLuz(String[] orden) throws FicheroDatosException {
		double x = 0; double y = 0; double z = 0;
		double intensidad = 0;
		
		for (int i=1; i<orden.length; i++) {
			String[] partes = orden[i].split(":");
			String clave = partes[0];
			String valor = partes[1];
			switch(clave) {
			case "x":
				x = Double.parseDouble(valor);
				break;
			case "y":
				y = Double.parseDouble(valor);
				break;
			case "z":
				z = Double.parseDouble(valor);
				break;
			case "i":
				intensidad = Double.parseDouble(valor);
				break;
			default:
				throw new FicheroDatosException("Error de fichero");
			}
		}
		
		Point3d p = new Point3d(x, y, z);
		return new Luz(p, intensidad);
	}
	
}
