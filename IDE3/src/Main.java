

import java.io.IOException;


/*
 * Start the program
 */
public class Main {
	public static void main(String[] args) throws IOException { 
		// TODO Auto-generated method stub
		IDE3.readAtributosJuegoTxt();
		IDE3.readAJuegoTxt();
		IDE3.cargarValoresDeCadaAtributo();
		IDE3.dameRepeticionesPyN();
		IDE3.readAll();
		
		Tree tree = new Tree();
        /*
         * The second parameter for the addNode method is the identifier
         * for the node's parent. In the case of the root node, either
         * null is provided or no second parameter is provided.
         */
		tree.createAndInitializeTree();
		
		//tree.draw();
		tree.display("Jugar si...");
		
		//tree.printMap();
		System.out.println("\nTest Results: ");
		
		//Print on differents files the results
		tree.readTest("Test1Juego.txt");
		tree.readTest("Test2Juego.txt");
		tree.readTest("Test3Juego.txt");
		tree.printRules();
		
		return;
	}
}