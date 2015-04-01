package P1;

/*
 * Class with the tipical attributes for one coordinate
 */

public class Coordenate {
	private int x;
	private int y;

	public Coordenate(int auxX, int auxY){
		x = auxX;
		y = auxY;
	}

	
				//Getters and setters
	 
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	public double getEuclidea(Coordenate c){
		return  Math.sqrt(Math.pow(this.getX()-c.getX(),2) + Math.pow(this.getY()-c.getY(),2));
	}

	public String getCoordentaAsString(){
		return (Integer.toString(this.getX()) + ',' + Integer.toString(this.getY()));
	}
	public String getCoordentaAsStringAlContario(){
		return (Integer.toString(this.getX()) + ',' + Integer.toString(this.getY()));
	}
}
