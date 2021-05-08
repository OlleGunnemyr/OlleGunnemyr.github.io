/**
 * README
 * Name:	 Olle Gunnemyr
 * Date:	 2020-09-18
 * Program:	 insertionSort5.java
 * 
 * insertionSort5.java sorts an array with random values (between 1 and 1000) of the type integer. The user decides the size(int). 
 * The program prints out the execution time of the algorithm (in seconds).
 * 
 * The insertion sort algorithm is copied and modified from the Insertion sort in id1020-lecture 5. 
 * Since it uses the Comparable Interface, the sorting algorithm is applicable to both int and char by using wrapper classes Integer and Character.
 */


import java.util.Random;
import java.util.Scanner;

public class insertionSort5 {
	//Sorts the array and prints the content during every swap of values
		public static void sort(Comparable[] a) {
			int size = a.length;//size of array
			
			
			for (int i = 0; i < size; i++) {//compares the value in index j with previous index (j--)
				for(int j = i; j>0; j--) {
					
					
					if (less(a[j], a[j-1])) {//if the value of previous index is larger, exchange index. If not, break inner loop
						exch(a, j, j-1);
					}
					
					else {
						break;
					}
				}
			}
		
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
	
		
	//Unit Test
	public static void main(String[] args) {
	   
		Scanner scan = new Scanner(System.in);
		System.out.println("Insertion");
		System.out.println("Size of random array: ");
		int n = scan.nextInt();
		
		//test create an array with random integers
		Random rand = new Random();
		Integer[] numbers = new Integer[n];
		for(int i = 0; i <n; i++) {
			numbers[i] = rand.nextInt(1000);
		}
	    
	    long startTime = System.nanoTime();//start timer
	    
	    sort(numbers);//test sort
	    
	    long endTime = System.nanoTime();//end timer
	    float timeElapsed = (float)(endTime - startTime);//total time
	    
	   
	    System.out.println("Second: "+timeElapsed/1000000000);//print out time in seconds
	    
	
	   
	}
}
