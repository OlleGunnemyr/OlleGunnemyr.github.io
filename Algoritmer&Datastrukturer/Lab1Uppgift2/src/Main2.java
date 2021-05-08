import edu.princeton.cs.algs4.StdIn;//import from princeton library
import edu.princeton.cs.algs4.StdOut;

public class Main2 {
	
	//test of code
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//test of iterative method
		System.out.print("Iterative method: ");
		iterative();
		System.out.println();
		
		//test of recursive method
		System.out.print("Recursive method: ");
		recursive();
	}
	
	
	//iterative function
	public static void iterative(){
		
		//abstract data type from ch 1.3
		StackLinked<Character> stack = new StackLinked<Character>();

		char c = StdIn.readChar();//reads the first char from input
		int size =0;
		
		//pushes every char on the stack, size increases.
		//jumps out of the loop when newline char is read.
		while(c != '\n')
		{
			stack.push(c);
			c = StdIn.readChar();
			size++;
		}

		//prints out every char from array in reverse order
		for(int i = 0; i<size; i++){
			StdOut.print(stack.pop());
		}
	}
	
	
	//recursive function
	public static void recursive() {
        char c = StdIn.readChar();
        
        //reverse will call itself until newline char is read (basecase).
        //It will then continue to go through each reverse-call and print out
        //the characters in reverse order
        if (c  != '\n') {
            recursive();
           StdOut.print(c);
        } 
    }
}
