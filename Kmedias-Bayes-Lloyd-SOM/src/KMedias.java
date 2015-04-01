import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

/*
 * Class with the Kmedias algorithm
 */
public class KMedias {

	 private static ArrayList<String>[]  allLines;
	 private static double[][] V = {{4.6,3.0,4.0,0.0}, {6.8,3.4,4.6,0.7}};
	 private static long[][] VtPlus1 = new long[2][4];
	 private static double E = 0.01;
 
public static void main() throws IOException {
	// TODO Auto-generated method stub
	allLines=Main.getAllLines();
try {
		KMedias();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	//testAllLines();
	readTest("Testiris01.txt");
	readTest("Testiris02.txt");
	readTest("Testiris03.txt");
}


//Applying the formulas:
public static void KMedias() throws ParseException, FileNotFoundException, UnsupportedEncodingException{
	boolean finalizationCriteria = false;
	
	//Calculating D
	double[][] D = new double[2][allLines.length];
	double[][] U = new double[2][allLines.length];
	int counter = 0;
	do{
		counter++;
		//for each case
		for (int j = 0; j < allLines.length; j++){
			ArrayList<String> line = allLines[j];
			//for each class
			for (int i = 0; i < 2; i++) {
				float Dij = 0;
				//for each Xn
				for (int k = 0; k < line.size()-1; k++) {
					String x = line.get(k);
					Dij += Math.pow(Double.parseDouble(x)-V[i][k], 2);
				}
				D[i][j] = Dij;
			}
		}

		//Calculating U
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < allLines.length; j++) {
				U[i][j] = (1/D[i][j])/((1/D[0][j])+(1/D[1][j]));
			}
		}
	
		//Calculating V t+1 class 1
		double[] Vx = {0,0,0,0};
		double totalP = 0;
		
		for (int i= 0; i < allLines.length; i++){
			double p2 = Math.pow(U[0][i], 2);
			ArrayList<String> line = allLines[i];
			for (int k = 0; k < line.size()-1; k++) {
				String x = line.get(k);
				Vx[k] += p2 * Double.parseDouble(x);
			}
			totalP +=p2;//denominator
		}
		for (int i= 0; i < Vx.length; i++){
			VtPlus1[0][i] = (long) (Vx[i]/totalP);
		}
		
		//Calculating V t+1 class 2
		totalP = 0;
		double[] Vx2 = {0,0,0,0};
		for (int i= 0; i < allLines.length; i++){
			double p2 = Math.pow(U[1][i], 2);
			ArrayList<String> line = allLines[i];
			for (int k = 0; k < line.size()-1; k++) {
				String x = line.get(k);
				Vx2[k] += p2 * Double.parseDouble(x);
			}
			totalP +=p2;//denominator
		}
		for (int i= 0; i < Vx2.length; i++){
			VtPlus1[1][i] = (long) (Vx2[i]/totalP);
		}
		
		//finalization criteria
		Double euclidianDistanceV1 = Math.sqrt(Math.pow(V[0][0] - VtPlus1[0][0],2)+Math.pow(V[0][1] - VtPlus1[0][1],2)+Math.pow(V[0][2] - VtPlus1[0][2],2)+Math.pow(V[0][3] - VtPlus1[0][3],2));
		Double euclidianDistanceV2 = Math.sqrt(Math.pow(V[1][0] - VtPlus1[1][0],2)+Math.pow(V[1][1] - VtPlus1[1][1],2)+Math.pow(V[1][2] - VtPlus1[1][2],2)+Math.pow(V[1][3] - VtPlus1[1][3],2));
		if(euclidianDistanceV1 < E && euclidianDistanceV2 < E) 
			finalizationCriteria = true;
		else{
			for(int i=0; i<V.length; i++)
				  for(int j=0; j<V[i].length; j++)
					  V[i][j]=VtPlus1[i][j];
		}
	}while(!finalizationCriteria);
	
	//printing V solution.
	String name="K-medias Centres result";
	PrintWriter writer = new PrintWriter(name, "UTF-8");
	DataInputStream in = new DataInputStream(new FileInputStream(name));
	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	writer	.println("Final VtPlus1 K-medas");
	System.out.println("Final VtPlus1 K-medias");
	writer	.println("-------------------------------------");
	System.out.println("-------------------------------------");
	for(int i=0; i<VtPlus1.length; i++){
		  for(int j=0; j<VtPlus1[i].length; j++){
			  DecimalFormat df=new DecimalFormat("0.00");
			  String formate = df.format(VtPlus1[i][j]); 
			  long finalValue = (long)df.parse(formate);
			  if(j==VtPlus1[i].length-1){
				  System.out.print(finalValue);
				  writer	.print(finalValue); 
			  }else{
				  System.out.print(finalValue + " , ");
				  writer	.print(finalValue + " , "); 
			  }
			  
		  }
		  System.out.println(" ");
		  writer	.println(" ");
	}
	System.out.println("-------------------------------------");
	writer	.println("-------------------------------------");
	System.out.println(counter+" loops");
	writer	.println(counter+" loops");
	writer.close();
}

//TEST
public static void testAllLines(){
	Double distanceV1 = null;
	Double distanceV2 = null;
	for (int j = 0; j < allLines.length; j++){
		ArrayList<String> line = allLines[j];
		distanceV1 = (Math.pow(Double.parseDouble(line.get(0)) - VtPlus1[0][0],2)+Math.pow(Double.parseDouble(line.get(1)) - VtPlus1[0][1],2)+Math.pow(Double.parseDouble(line.get(2)) - VtPlus1[0][2],2)+Math.pow(Double.parseDouble(line.get(3)) - VtPlus1[0][3],2));
		distanceV2 = (Math.pow(Double.parseDouble(line.get(0)) - VtPlus1[1][0],2)+Math.pow(Double.parseDouble(line.get(1)) - VtPlus1[1][1],2)+Math.pow(Double.parseDouble(line.get(2)) - VtPlus1[1][2],2)+Math.pow(Double.parseDouble(line.get(3)) - VtPlus1[1][3],2));
		System.out.println("case:" + j);
		if(distanceV1<distanceV2)
			System.out.println("Class1");
		else
			System.out.println("Class2");
	}
}

/*
 * Result of all test
 */
public static void readTest(String nombre) throws IOException{
	System.out.println("\nK-medias Test: "+nombre+ "\n");
	PrintWriter writer = new PrintWriter("K-medias result of "+nombre, "UTF-8");
	DataInputStream in = new DataInputStream(new FileInputStream(nombre));
	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	String strLine=br.readLine() ;
	String[] argumentos  = strLine.split(",");
	in.close();
	
	double[] V= new double[argumentos.length-1];
	for (int i =0 ; i <argumentos.length-1;i ++)
			V[i]=Double.parseDouble(argumentos[i]);
	Double distanceV1 = (Math.pow(V[0] - VtPlus1[0][0],2)+Math.pow(V[1] - VtPlus1[0][1],2)+Math.pow(V[2] - VtPlus1[0][2],2)+Math.pow(V[3] - VtPlus1[0][3],2));
	Double distanceV2 = (Math.pow(V[0] - VtPlus1[1][0],2)+Math.pow(V[1] - VtPlus1[1][1],2)+Math.pow(V[2] - VtPlus1[1][2],2)+Math.pow(V[3] - VtPlus1[1][3],2));
	double b=2.0;
	double p1=(Math.pow( (1.0/distanceV1),(1/(b-1)) ))/( (Math.pow( (1.0/distanceV1),(1/(b-1)) )) + (Math.pow( (1.0/distanceV2),(1/(b-1)) )));
	double p2=(Math.pow( (1.0/distanceV2),(1/(b-1)) ))/( (Math.pow( (1.0/distanceV1),(1/(b-1)) )) + (Math.pow( (1.0/distanceV2),(1/(b-1)) )));
	p1=new BigDecimal((p1)).setScale(2, RoundingMode.HALF_UP).doubleValue();
	p2=new BigDecimal((p2)).setScale(2, RoundingMode.HALF_UP).doubleValue();
	
	System.out.println("Grado de pertenencia a la clase 1: " + (p1)*100+"%");
	writer.println	("Grado de pertenencia a la clase 1: " + (p1)*100+"%");
	System.out.println("Grado de pertenencia a la clase 2: " + (p2)*100+"%");
	writer.println("Grado  de pertenencia a la clase 2: " + (p2)*100+"%");
			
	System.out.println("Distance 1: "+distanceV1);
	System.out.println("Distance 2: "+distanceV2);
	writer.println("Distance 1: "+distanceV1);
	writer.println("Distance 2: "+distanceV2);
	if (distanceV1 < distanceV2) {
		System.out.println("Because D1 < D2  It's belong to first class,which is: " +allLines[0].get(Main.getSizeArgumentos()-1));
		writer.println("Because D1 < D2 It's belong to first class,which is: " +allLines[0].get(Main.getSizeArgumentos()-1));
  }	
  else {
		System.out.println("Because D2 < D1 It's belong to sencond class,which is: " +allLines[allLines.length-1].get(Main.getSizeArgumentos()-1));
		writer.println	("Because D2 < D1 It's belong to second class,which is: " +allLines[allLines.length-1].get(Main.getSizeArgumentos()-1));
  }
	 writer.close();
 }

}