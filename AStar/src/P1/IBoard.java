package P1;


import java.util.ArrayList;

/*
 * Class to load the board
 * 
 * Clase para cargar el tablero
 */
public class IBoard {
	static int filas;
	static int columnas;
	static int[][] board;
	static Coordenate start;
	static ArrayList<Coordenate> listaSalidas = new ArrayList<Coordenate>(); //***
	static Coordenate exit;


	public IBoard( int filasAux,int columnasAux) {
		filas = filasAux;
		columnas = columnasAux;
		board = new int[filas][columnas];
		for (int i = 0; i < filas; i++) {
			for (int y = 0; y <columnas; y++) {
				board[i][y] = 0;
			}
		}
	}

	static void printGrid()
	{
		for(int x = 0; x <filas ; x++)
		{
			for(int y = 0; y < columnas; y++)
			{
				System.out.printf("%5d ", board[x][y]);
			}
			System.out.println();
		}
	}

	
	
			// Getters and setters
	 
	public static int getFilas() {
		return filas;
	}

	public static void setFilas(int filas) {
		IBoard.filas = filas;
	}

	public static int getColumnas() {
		return columnas;
	}

	public static void setColumnas(int columnas) {
		IBoard.columnas= columnas;
	}

	public static int[][] getBoard() {
		return board;
	}

	public static void setBoard(int[][] board) {
		IBoard.board = board;
	}

	public static Coordenate getStart() {
		return start;
	}

	public static void setStart(Coordenate start) {
		IBoard.start = start;
	}

	public static Coordenate getExit() {
		return exit;
	}

	public static void setExit(Coordenate exit) {
		IBoard.exit = exit;
	}

	public static ArrayList<Coordenate> getListaSalidas() {
		return listaSalidas;
	}

	public static void setListaSalidas(ArrayList<Coordenate> listaSalidas) {
		IBoard.listaSalidas = listaSalidas;
	}

}


