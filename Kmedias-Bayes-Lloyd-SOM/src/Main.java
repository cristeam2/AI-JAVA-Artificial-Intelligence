import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * Class to launch all algoritms
 */
public class Main {

	 private static ArrayList<String>[]  allLines=null;
	 private static int sizeArgumentos=0;

	 
	public static void main(String[] args) throws IOException {
		readAJuegoTxt();
		KMedias.main(); 
		Bayes.main();
		LLoyd.main(null);
		Som.main(null); 
	}
		
	
	
	
	@SuppressWarnings("unchecked")
	public static void readAJuegoTxt() throws IOException {
		DataInputStream in = new DataInputStream(new FileInputStream("iris2Clases.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine=br.readLine() ;
		String[] argumentos  = strLine.split(",");
		int lineas=0;
		sizeArgumentos= argumentos.length;
		
		ArrayList<String> temp = new ArrayList<String>();
		while ( strLine != null) {
			argumentos  = strLine.split(",");
			for (int i = 0; i < argumentos.length; i++) {
 				temp.add(argumentos[i]);
				}
			lineas++;
			strLine = br.readLine();
		}
		
		//elementosPorFila = (temp.size()-1)/(lineas-1);
		allLines= new ArrayList[lineas];
		for (int j=0; j<temp.size(); j++)
				if (j*sizeArgumentos+sizeArgumentos>temp.size());
				else 
					allLines[j]=new ArrayList<String>(temp.subList(j*sizeArgumentos,(j*sizeArgumentos)+sizeArgumentos));	
		in.close(); 
	}

	
	   //Getters and setters
	
	
	public static ArrayList<String>[] getAllLines() {
		return allLines;
	}


	public static int getSizeArgumentos() {
		return sizeArgumentos;
	}

	
}

