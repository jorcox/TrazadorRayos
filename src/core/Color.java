package core;

public class Color {
	
	private int r;
	private int g;
	private int b;
	
	/**
	 * Contiene un color formado por los tres canales RGB,
	 * cada uno de los cuales toma un valor entre 0 y 255. Si
	 * se introducen valores fuera de este intervalo, se hace
	 * saturacion por 0 o por 255.
	 */
	public Color(int r, int g, int b) {
		if (r>255) {
			this.r = 255;
		} else if (r<0) {
			this.r = 0;
		} else this.r = r;
		
		if (g>255) {
			this.g = 255;
		} else if (g<0) {
			this.g = 0;
		} else this.g = g;
		
		if (b>255) {
			this.b = 255;
		} else if (b<0) {
			this.b = 0;
		} else this.b = b;
	}

	public int getRed() {
		return r;
	}

	public int getGreen() {
		return g;
	}

	public int getBlue() {
		return b;
	}
	
	public void setBrillo(double brillo){
		r = (int) (r*brillo);
		g = (int) (g*brillo);
		b = (int) (b*brillo);
		
	}
	
}
