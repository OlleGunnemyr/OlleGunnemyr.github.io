/**
 * README
 * Name:	 Olle Gunnemyr
 * Date:	 2020-09-28
 * Program:	 Lab33.java
 * 
 * Lab33.java reads a text file and distributes the words in a hash table using SeperateChainingHashST.
 * The program prints out the size of each linked list of words in the hash table, along with medium, max, min and deviation of sizes.
 * The purpose is to show how evenly the hashing is performed using hashcode.
 * 
 * The SequentialSearchST and SeperateChainingHashST are copied and modified from https://algs4.cs.princeton.edu.
 */


import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class Lab33 {
	
	public static void main(String[] args) throws IOException {
		
		SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>(100);
        File file = new File("C:\\Users\\Olle Gunnemyr\\eclipse-workspace\\Lab3Uppgift2\\src\\The_text.txt");
        Scanner scan = new Scanner(file);

        String word;
        
        //create symbol table
        while (scan.hasNext()) {
            word = scan.next();
            st.put(word, 1);//value is constant 1, since it has no impact on the assignment
        }
        
        scan.close();
        
        //print all sizes of each linked list
        for(int k = 0; k < 100; k++ ) {
        	int size = st.getSize(k);
        	System.out.println(k+": "+size);
        }
        
        int max =  st.getSize(0);
        int min  =  st.getSize(0);
        
        //check for maximum and minimum size
        for(int j = 1; j < 100; j++ ) {
        	if ( max <  st.getSize(j)) {
        		max =  st.getSize(j);;
        	}
        }
        for(int j = 1; j < 100; j++ ) {
        	if ( min >  st.getSize(j)) {
        		min =  st.getSize(j);;
        	}
        }
        
        System.out.println("Medium size: "+st.size()/100);
        System.out.println("Minimum: "+min);
        System.out.println("Maximum: "+ max);
        System.out.println("Deviation: "+(max-min));
	}

}
