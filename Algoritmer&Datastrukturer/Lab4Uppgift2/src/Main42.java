import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import edu.princeton.cs.algs4.BST;

public class Main42 {

	public static void main(String[] args) throws IOException{ 
		// TODO Auto-generated method stub
		
		Scanner scanInput = new Scanner(System.in);
		
		System.out.println("Roadtrip");
		
		System.out.print("From: ");
		String start = scanInput.next();
		System.out.print("To: ");
		String end = scanInput.next();
		
		scanInput.close();
		
		BST<String, Integer> bst = new BST<String, Integer>();
		File file = new File("C:\\Users\\Olle Gunnemyr\\Desktop\\contiguous-usa.dat.txt");
		Scanner scanFile = new Scanner(file);
		
		int i = 0;
		while(scanFile.hasNext()) {
			String state = scanFile.next();
			if(!bst.contains(state)){
				bst.put(state, i);
				i++;
			}
		}
		
		scanFile.close();
		
		Graph g = new Graph(i);
		Scanner scanFile2 = new Scanner(file);
		
		while(scanFile2.hasNext()) {
			String v1 = scanFile2.next();
			String v2 = scanFile2.next();
			g.addEdge(bst.get(v1), bst.get(v2));
		}
		
		scanFile2.close();
		
		int x = bst.get(start);
		int y = bst.get(end);
		
		BFP d = new BFP(g, x);
		
		if(d.hasPathTo(y)) {
			
			Stack p = d.pathTo(y);
			int size = p.size();
		
			for(int count= 0; count < size; count++) {
				int v = (int) p.pop();
				
				if(v==y) {
					System.out.print(end);
					break;
				}
				
				for(String key : bst.keys()) {
					
					if(v == bst.get(key)) {
						System.out.print(key + " -> ");
						break;
					}
				}
			}
		}
		
		
		
	}

}