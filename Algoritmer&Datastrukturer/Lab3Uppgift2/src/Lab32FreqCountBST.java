/**
 * README
 * Name:	 Olle Gunnemyr
 * Date:	 2020-09-28
 * Program:	 Lab32FreqCountBST.java
 * 
 * Lab32FreqCountBST.java counts the frequency of occurrence of N (in the order of hundred) words larger than the minimum size minlen. The user decides both N and minlen.
 * The program prints out the word that occurs the most, along with the execution time of the insertion in the binary search tree (BST). 
 * 
 * The Frequency Counter algorithm is copied and modified from page 372 in Algorithms 4th edition.
 */


import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

import edu.princeton.cs.algs4.BinarySearchST;//the Binary Search Tree


public class Lab32FreqCountBST {
	public static void main(String[] args) throws FileNotFoundException
	{
		File file = new File("C:\\Users\\Olle Gunnemyr\\eclipse-workspace\\Lab3Uppgift2\\src\\The_text.txt");
		BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
		
		Scanner scanFile = new Scanner(file);//scanner of file
		Scanner scanInput = new Scanner(System.in);//scanner of user input
		
		System.out.println("N order of 100: ");
		int n = scanInput.nextInt();
		int N = n * 100;//number of words read
		int i = 0;
		
		System.out.println("Minimum length of word: ");
		int minlen = scanInput.nextInt(); // key-length cutoff
		 
		long STstart =System.nanoTime();//start timer
		
		while (scanFile.hasNext() && i<N)
		{ // Build symbol table and count frequencies.
			String word = scanFile.next();
			if (word.length() < minlen) {
				i++;
				continue; // Ignore short keys.
			}
			if (!st.contains(word)) st.put(word, 1);
			else st.put(word, st.get(word) + 1);
			i++;
		}
		 
		//close scanners
		scanFile.close();
		scanInput.close();
		
		//end timer and calculate total time
		long STend = System.nanoTime();
		float BSTtotal =(float) (STend - STstart);
		 
		// Find a key with the highest frequency count.
		String max = "";
		st.put(max, 0);
		for (String word : st.keys())
			if (st.get(word) > st.get(max))
				max = word;
		
		//print the word, occurrence and execution time in seconds
		System.out.println("Word: '"+max + "'");
		System.out.println("Amount: "+st.get(max));
		System.out.println("Time: "+BSTtotal/1000000000+"s");
	}		
}

