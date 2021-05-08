/**
 * README
 * Name:	 Olle Gunnemyr
 * Date:	 2020-09-18
 * Program:	 MergeSort5.java
 * 
 * MergeSort5.java sorts an array with random values (between 1 and 1000) of the type integer. The user decides the size(int). 
 * The program prints out the execution time of the algorithm (in seconds).
 * 
 * The merge sort algorithm is copied and modified from the Merge sort in id1020-lecture 6. 
 * Since it uses the Comparable Interface, the sorting algorithm is applicable to both int and char by using wrapper classes Integer and Character.
 */




import java.util.Random;
import java.util.Scanner;

public class MergeSort5 {
	
	//merge the two halves of the array
	private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid,int hi) {
		for(int k = lo; k<=hi;k++) {//copy content of array to auxiliary
			aux[k] = a[k];
		}
		
		int i = lo, j = mid+1;
		for(int k = lo; k<=hi;k++) {//copy back to array in sorted order
			if (i>mid) a[k] = aux[j++];
			else if (j>hi) a[k]=aux[i++];
			else if(less(aux[j], aux[i])) a[k]=aux[j++];
			else a[k]=aux[i++];
		}
	}
	
	//splits the array in half repeatedly until there are only single elements left. 
	//Sort and merge each half repeatedly until the array is finished.
	private static void sort(Comparable[] a, Comparable[] aux, int lo ,int hi) {
		if(hi<=lo) return;//if there is only one element, return
		int mid = lo + (hi-lo)/2;
		sort(a,aux,lo,mid);
		sort(a,aux,mid+1,hi);
		merge(a,aux,lo,mid,hi);
		
	}
	
	//creates an empty auxiliary array of the same size as the array.
	public static void sort(Comparable[] a) {
		Comparable[] aux = new Comparable[a.length];
		sort(a,aux,0,a.length-1);//Calls the recursive sort method.
	}
	
	//returns true if v is less than w (using the compareTo method of the object), otherwise false
			private static boolean less(Comparable v, Comparable w) {
				return v.compareTo(w) < 0;
			}

	
	//Unit Test
		public static void main(String[] args) {
		   
			Scanner scan = new Scanner(System.in);
			System.out.println("Merge");
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
