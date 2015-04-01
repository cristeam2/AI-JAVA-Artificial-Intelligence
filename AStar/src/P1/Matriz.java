package P1;

/*
 * Test class that emulates the info that I will obtain from the GUI
 * 
 * Clase de pruebas que emula la info que obtendre desde la interfaz grafica
 * 
 */

public class Matriz {

	public static final int M= IBoard.getFilas();  
	public static final int N= IBoard.getColumnas();
	int matriz[][] = new int[M][N];
	public static  int  casillaFinalX= IBoard.getExit().getX();
	public static int casillaFinalY=  IBoard.getExit().getY();
	public static int casillaInicioX=IBoard.getStart().getX();
	public static int casillaInicioY=IBoard.getStart().getY();
	
	
	public Matriz(){
		matriz= IBoard.getBoard();

	}

	
	
				//Getters and Setters
	
	
	public int [][] getM() {
		return matriz;
	}

	public static int getCasillaFinalX() {
		return casillaFinalX;
	}

	public static void setCasillaFinalX(int casillaFinalX) {
		Matriz.casillaFinalX = casillaFinalX;
	}

	public static int getCasillaFinalY() {
		return casillaFinalY;
	}

	public static void setCasillaFinalY(int casillaFinalY) {
		Matriz.casillaFinalY = casillaFinalY;
	}

	public static int getCasillaInicioY() {
		return casillaInicioY;
	}
	public static int getCasillaInicioX() {
		return casillaInicioX;
	}
//***
	public static void setCasillaInicioY(int casillaInicioY) {
		Matriz.casillaInicioY = casillaInicioY;
	}
//***
	public static void setCasillaInicioX(int casillaInicioX) {
		Matriz.casillaInicioX = casillaInicioX;
	}

}
