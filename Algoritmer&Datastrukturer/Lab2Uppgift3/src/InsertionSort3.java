/**
 * README
 * Name:	 Olle Gunnemyr
 * Date:	 2020-09-18
 * Program:	 InsertionSort3.java
 * 
 * InsertionSort3.java sorts an array of the type integer, in which the user decides the size(int) and input(int). 
 * The program prints out the content of the array each time a value changes index (in the sorting algorithm). 
 * A counter is added in the algorithm, which counts how many swaps are performed (and prints them out).
 * The method inversions is added, which prints out all inversions of an unsorted array.
 *    
 * The insertion sort algorithm is copied and modified from the Insertion sort in id1020-lecture 5. 
 * Since it uses the Comparable Interface, the sorting algorithm is applicable to both int and char by using wrapper classes Integer and Character.
 */

import java.util.Scanner;
public class InsertionSort3 {
	
	//Sorts the array and prints the content during every swap of values
			public static void sort(Comparable[] a) {
				int size = a.length;//size of array
				int count = 0; //counter swaps
				
				for (int i = 0; i < size; i++) {//compares the value in index j with previous index (j--)
					for(int j = i; j>0; j--) {
						
						
						if (less(a[j], a[j-1])) {//if the value of previous index is larger, exchange index. If not, break inner loop
							
							exch(a, j, j-1);
							count++;//add one to counter
							toString(a);//prints array
						}
						
						else {
							break;
						}
					}
				}
				System.out.println("Swaps: "+count);
			}
		
		
			//returns true if v is less than w (using the compareTo method of the object), otherwise false
			private static boolean less(Comparable v, Comparable w) {
				return v.compareTo(w) < 0;
			}
			
			//exchanges values between index j and k in array a
			private static void exch(Comparable[] a, int j, int k) {
				Comparable swap = a[j];
				a[j] = a[k];
				a[k] = swap;
			}
			
			//prints out every value in array
			private static void toString(Comparable[] a) {
				for(int i = 0; i < a.length; i++) {
					System.out.print(a[i]+" ");
				}
				System.out.println();
			}
			
	
	//prints out the inversions of an array and the amount. The Time Complexity of the method is (N*(N-1)/2 = (N^2)/2 - N/2 = O(N^2)
	private static void inversions(Comparable[] a) {
		int size = a.length;
		int count = 0;//counter of inversions
		
		//check for inversions(a[i] > a[j] and i<j), add one to counter and prints them out
		for(int i = 0; i < size; i++) {
			for(int j = i+1; j < size; j++) {
			
				if(less(a[j], a[i])) {
					System.out.println("["+i+","+a[i]+"],["+j+","+a[j]+"] ");
					count++;
				}
			}
		}
		System.out.println("Inversions: "+count);//print out number of inversions
		System.out.println();
	}
	
	
	
	
	//Unit Test
	public static void main(String[] args) {
				
	//test of creating and sorting an array of integers
		Scanner scan = new Scanner(System.in);
				
		System.out.print("Size of input(N): ");
		int size = scan.nextInt();
		Integer[] array = new Integer[size];//we use Integer (wrapper class) since Comparable<T> interface implements it
			    
			    
		System.out.print("Insert integers: ");//reads and adds values in array
		for(int i = 0; i < size; i++) {
			array[i] = scan.nextInt();
		}
		scan.close();//close scanner because "resource leak"	    
		
		inversions(array);//test inversions	    
		sort(array);//test sort
		
					    
		//test Comparable Interface with char and string (optional)
		System.out.println("");
		System.out.println("Character: ");
		Character[] arrayC = new Character[] {'D','A','C','B'};
		inversions(arrayC);
		sort(arrayC);
				
		System.out.println("");
		System.out.println("String: ");
		String[] arrayS = new String[] {"Diane", "Axel", "Caitlin","Ben"};
		inversions(arrayS);
		sort(arrayS);
			   
			
	}
}
