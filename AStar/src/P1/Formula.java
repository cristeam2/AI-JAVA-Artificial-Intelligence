package P1;

/*
 * Class in order to calculate the distance between two positions
 * 
 * Clase para calcular la distancia entre dos posiciones
 */

public class Formula {

	public double calculaDistancia (int x1, int x2, int y1, int y2  ) {
		return  Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
	}

}
