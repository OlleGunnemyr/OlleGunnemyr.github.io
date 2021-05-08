import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Labb3Uppgift4 {
    public static void main(String[] args) throws FileNotFoundException
    {
       
        BinarySearchST<String, Integer> ST = new BinarySearchST<String, Integer>();
     
        
        File file = new File("C:\\Users\\Olle Gunnemyr\\eclipse-workspace\\Lab3Uppgift4\\src\\StorText.txt");
        Scanner scan = new Scanner(file);
        
        int i=0;
         
        String question = "1994";
        	question = question.toLowerCase(); 
        	
        while(scan.hasNext())
        {
        	String word = scan.next().toLowerCase();
            ST.put(word,i++);
        }

       
       System.out.println(ST.get(question));
        	

    }

}