package core;

public class Color {

	private int r;
	private int g;
	private int b;

	/**
	 * Contiene un color formado por los tres canales RGB, cada uno de los
	 * cuales toma un valor entre 0 y 255. Si se introducen valores fuera de
	 * este intervalo, se hace saturacion por 0 o por 255.
	 */
	public Color(int r, int g, int b) {
		if (r > 255) {
			this.r = 255;
		} else if (r < 0) {
			this.r = 0;
		} else
			this.r = r;

		if (g > 255) {
			this.g = 255;
		} else if (g < 0) {
			this.g = 0;
		} else
			this.g = g;

		if (b > 255) {
			this.b = 255;
		} else if (b < 0) {
			this.b = 0;
		} else
			this.b = b;
	}

	/**
	 * Devuelve la componente roja
	 */
	public int getRed() {
		return r;
	}

	/**
	 * Devuelve la componente verde
	 */
	public int getGreen() {
		return g;
	}

	/**
	 * Devuelve la componente azul
	 */
	public int getBlue() {
		return b;
	}

	
	/**
	 * Multiplica el color actual por el brillo dado 
	 */
	public void setBrillo(double brillo) {
		r = (int) (r * brillo);
		g = (int) (g * brillo);
		b = (int) (b * brillo);

	}

	/**
	 * Metido auxiliar que sirve para aplicar intensidad a un color
	 */
	public Color aplicarIntensidad(double i) {
		int newR = (int) (this.r * i);
		int newG = (int) (this.g * i);
		int newB = (int) (this.b * i);
		return new Color(newR, newG, newB);

	}

	/**
	 * Calcula el Kd
	 */
	public Color calcularKD() {
		return new Color(255 - r, 255 - g, 255 - b);
	}

	/**
	 * 
	 * @param col
	 *            Color que se desea sumar a este
	 * @return un nuevo Color resultado de la suma de ambos
	 */
	public Color suma(Color col) {
		return new Color(r + col.getRed(), g + col.getGreen(), b + col.getBlue());
	}

	/**
	 * @param colores
	 *            Array de colores
	 * @return un nuevo Color que resulta del promedio de los colores del array,
	 *         es decir, el promedio de rojo, verde y azul.
	 */
	public static Color promedio(Color[] colores) {
		int red = 0;
		int green = 0;
		int blue = 0;

		for (Color c : colores) {
			red += c.getRed();
			green += c.getGreen();
			blue += c.getBlue();
		}
		red /= colores.length;
		green /= colores.length;
		blue /= colores.length;

		return new Color(red, green, blue);
	}

	
	/**
	 * Normaliza el color, es decir, si alguna de las componentes 
	 * esta por encima de 255, reduce todas proporcionalmente hasta 
	 * que la mayor llegue a 255
	 */
	public void normalizarColor() {
		int mayor = 0;
		if (r > mayor)
			mayor = r;
		if (g > mayor)
			mayor = g;
		if (b > mayor)
			mayor = b;
		if (mayor > 255) {
			double indiceReduccion = (double) mayor / 255;
			indiceReduccion = Math.sqrt(indiceReduccion);
			r = (int) (r / indiceReduccion);
			g = (int) (g / indiceReduccion);
			b = (int) (b / indiceReduccion);
		}

		if (r > 255)
			r = 255;
		if (g > 255)
			g = 255;
		if (b > 255)
			b = 255;

	}

}
