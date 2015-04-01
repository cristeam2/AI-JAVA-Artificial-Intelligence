
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/*
 * Class with all logic methods and recursive algorithms to find the rules
 */
public class Tree {

    private final static int ROOT = 0;
    private HashMap<String, Node> nodes;
    @SuppressWarnings({ "unchecked", "rawtypes" })
		private Set<String> noRepeatDisplay = new HashSet();
    private  ArrayList<ArrayList<String>> rules = new ArrayList<ArrayList<String>>();
    
	// Constructors
    public Tree() {
        this.nodes = new HashMap<String, Node>();
    }

    // Properties
    public HashMap<String, Node> getNodes() {
        return nodes;
    }

    // Public interface
    public Node addNode(String identifier) {
        return this.addNode(identifier, null);
    }

    
    public Node addNode(String identifier, String parent) {
        Node node = new Node(identifier);
        nodes.put(identifier, node);

        if (parent != null) {
            nodes.get(parent).addChild(identifier);
        }

        return node;
    }

    void createAndInitializeTree(){
		 ArrayList<String>[] allLines = IDE3.getAllLines();
		 ArrayList<String> attrs = allLines[0];
		 String fatherNode = attrs.get(attrs.size()-1) + " si...";
		 this.addNode(fatherNode);
		 List<Atributo> meritos = IDE3.getMeritos();
		 //initializing
		 ArrayList<String> pendingValues = new ArrayList<String>();
		 Atributo attr = meritos.get(1);
		 Valor[] valores = attr.getValores();
		 Boolean firstLineFlag = true;
		 for(int i = 0; i < valores.length; i++){
			if(firstLineFlag)//the pending values = all values best attr
				pendingValues.add(valores[i].getNombre());
		 }
		 firstLineFlag = false;
	
		 //recursive tree creator
     @SuppressWarnings({ "unchecked", "rawtypes" })
		 ArrayList<String> path = new ArrayList();
		 this.createTree(fatherNode,allLines[0].size()-1, allLines[0],allLines, 1,meritos,pendingValues,path,"");
	 }

  //Recursive tree
	public void createTree(String fatherNode, int length, ArrayList<String> attrs, ArrayList<String>[] allLines, int level, List<Atributo> meritos, ArrayList<String> pendingValues, ArrayList<String> path, String prefix) {
		int number = 1;
		while(!pendingValues.isEmpty()){
			String currentAttr = meritos.get(level).getNombre();//get the attr corresponding to the level
			//search current attr column in allLines of 
			int i = 0;
			for(String attr : attrs){
				if(currentAttr.equals(attr))
					break;
				else
					i++;
			}
			String currentValue = pendingValues.get(0); //get pending val on this level
			path.add(currentValue);
			pendingValues.remove(0);//remove it

			//if (same value different final result) ? create new pendingList and call recursively : add res to tree
			String res = "";
			Boolean differenceFound = false;
			for(int j = 1; j < allLines.length; j++){
				ArrayList<String> currentLineInAllLines = allLines[j];//iterate all val lines
				if(currentLineInAllLines==null)
					break;
				if(currentLineInAllLines.get(i).equals(currentValue)){
					if(res == "" || currentLineInAllLines.get(length).equals(res))
						res = currentLineInAllLines.get(length);
					else{
						differenceFound = true;
						break;
					}
				}
			}
			if(!differenceFound){//(!same value different final result) --> add res to tree
				this.addNode(prefix+"."+Integer.toString(number)+ " "+currentValue,fatherNode);
				this.addNode(res,prefix+"."+Integer.toString(number)+ " "+currentValue);
				//creating rules
		    @SuppressWarnings({ "unchecked" })
				ArrayList<String> lineRule = (ArrayList<String>) path.clone();
		    @SuppressWarnings({ "unchecked", "rawtypes" })
		    ArrayList<String> lineRuleAux = new ArrayList();
				for(int u = 0; u < lineRule.size(); u++){
					String lineRuleStr = lineRule.get(u);
					int pos = 0;
					for (int v = 0; v < lineRuleStr.length(); v++) {
		                if (Character.isLetter(lineRuleStr.charAt(v))) {
		                   pos = v;
		                   break;
		                }
		            }
					lineRuleStr.substring(pos);
					lineRuleAux.add(lineRuleStr);
				}
				lineRuleAux.add(res);
				rules.add(lineRuleAux);
			}else
				if(level == meritos.size()-1){//last level--> check line with same path
				 for(int j = 1; j < allLines.length; j++){
					Boolean flagCorrectLastLine = false;
					ArrayList<String> currentLineInAllLines = allLines[j];//iterate all val lines
					if(currentLineInAllLines==null)
						break;
				   for(String val : path){//checking the line that has the entire path
						flagCorrectLastLine = false;
						for(int k = 0; k < currentLineInAllLines.size(); k++){
							if(currentLineInAllLines.get(k).equals(val)){
								flagCorrectLastLine = true;
								break;
							}
						}
						if(!flagCorrectLastLine)
							break;
					}
					if(flagCorrectLastLine){//once the line has been chosen get the result(last column)
						String resLastLine = currentLineInAllLines.get(currentLineInAllLines.size()-1);
						if(prefix=="")
							prefix = Integer.toString(number);
						else
							prefix += "."+Integer.toString(number);
						this.addNode(prefix+"."+Integer.toString(number)+ " "+currentValue,fatherNode);
						this.addNode(resLastLine,prefix+"."+Integer.toString(number)+ " "+currentValue);
						
						//creating rules
						ArrayList<String> lineRule = (ArrayList<String>) path.clone();
						ArrayList<String> lineRuleAux = new ArrayList();
						for(int u = 0; u < lineRule.size(); u++){
							String lineRuleStr = lineRule.get(u);
							int pos = 0;
							for (int v = 0; v < lineRuleStr.length(); v++) {				
				                if (Character.isLetter(lineRuleStr.charAt(v))) {
				                   pos = v;
				                   break;
				                }
				            }
							lineRuleStr.substring(pos);
							lineRuleAux.add(lineRuleStr);
						}
						lineRuleAux.add(resLastLine);
						rules.add(lineRuleAux);
						break;
					}
				}
			}
				else {
					
				//same value different final result --> create new pendingList and call recursively
				//create a new pendingValues that depend of this value
				ArrayList<String> pendingValuesCurrentNode = searchDependingValues(meritos, attrs, currentValue, i,level+1,allLines,path);
				if(prefix=="")
					prefix = Integer.toString(number);
				else
					prefix += "."+Integer.toString(number);
				this.addNode(prefix+"."+Integer.toString(number)+ " "+currentValue,fatherNode);
				this.createTree(prefix+"."+Integer.toString(number)+ " "+currentValue,length, attrs,allLines,level+1,meritos,pendingValuesCurrentNode,path,prefix);
			}
			path.remove(currentValue);
			number++;
		}
	}

	//serching attributes
	private ArrayList<String> searchDependingValues(List<Atributo> meritos, ArrayList<String> attrs, String currentValue, int attrColumn, int level,ArrayList<String>[] allLines, ArrayList<String> path) {
		ArrayList<String> newPendingVals = new ArrayList<String>();
		HashSet<String> newPendingValsSet = new HashSet<String>();
		int i = 0;
		String currentAttr = meritos.get(level).getNombre();
		for(String attr : attrs){
			if(currentAttr.equals(attr))
				break;
			else
				i++;
		}
		
		//get depending values from this attr val
		for(int j = 1; j < allLines.length; j++){
			ArrayList<String> currentLineInAllLines = allLines[j];//iterate all val lines
			if(currentLineInAllLines == null)
				break;
			Boolean correctPath = false;
			for(String val : path){
				correctPath = false;
				for(int k = 0; k < currentLineInAllLines.size(); k++){
					if(currentLineInAllLines.get(k).equals(val)){
						correctPath = true;
						break;
					}
				}
				if(!correctPath)
					break;
			}
			if(currentLineInAllLines.get(attrColumn).equals(currentValue) && correctPath){
				//find next level attr
				if(!newPendingValsSet.contains(currentLineInAllLines.get(i))){
					newPendingVals.add(currentLineInAllLines.get(i));
					newPendingValsSet.add(currentLineInAllLines.get(i));
				}
			}
		}
		return newPendingVals;
	}
	
	//Method to show the result 
	public void display(String identifier) {
  	PrintWriter writer = null;
			try {
				writer = new PrintWriter("Arbol.txt", "UTF-8");
			}
			catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
			this.display(identifier, ROOT,	writer);
      	writer.close();

    }

	
		//Method to show the result 
    public void display(String identifier, int depth,	PrintWriter writer) {
    	String res = "";
        ArrayList<String> children = nodes.get(identifier).getChildren();
        if(children.isEmpty() || !noRepeatDisplay.contains(identifier)){
	        if (depth == ROOT) {
	            System.out.println(nodes.get(identifier).getIdentifier());
	          	  writer.println(nodes.get(identifier).getIdentifier());
 
	        } else {
	            String tabs = String.format("%0" + depth + "d", 0).replace("0", "    "); // 4 spaces
	            res = nodes.get(identifier).getIdentifier();
	            int pos = 0;
	            for (int i = 0; i < res.length(); i++) {
	                if (Character.isLetter(res.charAt(i))) {
	                   pos = i;
	                   break;
	                }
	            }
	            res = tabs + res.substring(pos);
	            System.out.println(res);
	            writer.println(res);
	        }
        }
        noRepeatDisplay.add(identifier);
        depth++;
        for (String child : children) {

            // Recursive call
            this.display(child, depth,writer);
        }
    }
	
   
	void draw(){
		ArrayList<String>[] allLines = IDE3.getAllLines();
		ArrayList<String> attrs = allLines[0];
		String fatherNode = attrs.get(attrs.size()-1) + " si...";
		System.out.println(fatherNode);
		Node n = this.nodes.get(fatherNode);
		paintChildren(n);
		
	}
	
	void paintChildren(Node n){
		ArrayList<String> childrenFatherNode = n.getChildren();
		for(String child : childrenFatherNode){
			System.out.println(child+" ");
			Node newN = this.getNodes().get(child);
			paintChildren(newN);
		}
	}
	
	
	public void printMap() {
        Iterator it = nodes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
	
	
	//save on a file the solution of each test
	public void readTest(String nombre) throws IOException{
		PrintWriter writer = new PrintWriter("resultado de "+nombre, "UTF-8");
		DataInputStream in = new DataInputStream(new FileInputStream(nombre));
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine=br.readLine() ;
		String[] argumentos  = strLine.split(",");
		in.close();
		
		String[] aux  = new String[argumentos.length];
		aux[0]=argumentos[0];
		aux[1]=argumentos[2];
		aux[2]=argumentos[3];
		aux[3]=argumentos[1];
	
		boolean jugar =false;
		int cont=0, i=0;
		if (aux[0].equals("nublado")) {
			writer.println("Si se juega en "+ nombre);
			System.out.println("Si se juega en "+ nombre+"\n");
		}
		else {
	   	while ( !jugar && i <rules.size()  ) {
					if (!rules.get(i).get(0).equals("nublado")){
							for (int j=0; j<argumentos.length-1; j++) {
								if (rules.get(i).get(j).equalsIgnoreCase(aux[j]))
									cont++;
							}
							if ((cont==argumentos.length-1) && (rules.get(i).get(argumentos.length-1).equalsIgnoreCase("si")))
							{
								writer.println("Si se juega en "+ nombre);
								System.out.println("Si se juega en "+ nombre);
								jugar=true;
							}
							else cont=0;
					}
					i++;			 
			}
			if (cont !=argumentos.length-1) {
					writer.println("No se juega en "+ nombre);
					System.out.println("No se juega en "+ nombre+"\n");	
				}
			}
		writer.close();
	}
  
 //To show the finally tree rules
 public void printRules() throws FileNotFoundException, UnsupportedEncodingException{
	 	PrintWriter writer = new PrintWriter("reglas.txt", "UTF-8");
	 	System.out.println("Rules: ");
			for (Iterator it = rules.iterator(); it.hasNext();) {
				String msj=it.next().toString();
				System.out.println(msj);
				writer.println(msj);
			}
			writer.close();
	 
	 
 }
}