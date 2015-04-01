package P1;



/*
 * Class that represents the information contained in boxes / nodes
 * 
 * Clase que representa la informacion contenida en las casillas/nodos
 */
public class Nodo {
	private int x;
	private int y;
	private int xPadre;
	private int yPadre;
	private double F;
	private double G;
	private double H;

	//constructor
	public Nodo(int x, int y, int xPadre,int yPadre, double g, double h, double f) {
		this.x = x;
		this.y = y;
		this.xPadre = xPadre; //future purposes
		this.yPadre = yPadre; //future purposes
		this.H = h;
		this.G = g;
		this.F = f; //*** = h+g

	}

	//Initialization values
	public Nodo(int x, int y) {
		this.x=x;
		this.y=y;
		this.xPadre = 0;
		this.yPadre = 0;
		this.H = 0;
		this.G = 0;
		this.F = 0;

	}

	//Distance formule
	public double calculaDistancia (int x1, int x2, int y1, int y2  ) {
		return  Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
	}

	
	
			//Getters and Setters
	
	
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getxPadre() {
		return xPadre;
	}

	public void setxPadre(int xPadre) {
		this.xPadre = xPadre;
	}

	public int getyPadre() {
		return yPadre;
	}

	public void setyPadre(int yPadre) {
		this.yPadre = yPadre;
	}

	public double getF() {
		return F;
	}

	public void setF(double f) {
		F = f;
	}

	public double getG() {
		return G;
	}

	public void setG(double g) {
		G = g;
	}

	public double getH() {
		return H;
	}

	public void setH(double h) {
		H = h;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String  getNodoAsString() {
		String s= x+","+y;
		return s;
	}
	public String  getNodoAsStringAlContrario() {
		String s= y+","+x;
		return s;
	}
}

