import  java.util.Iterator;
public class Main4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//create a queue of the type Integer
				Queue4<Integer> q1 = new Queue4<Integer>();
				Queue4<String> q2 = new Queue4<String>();
				
				//test enqueue
				System.out.println("Test for repeated enqueue: ");
				for(int i = 0; i < 4; i++) {
		            q1.enqueue(i);
		        }
				
				//test firstEnqueue
				System.out.println("Test for firstEnqueue: ");
				System.out.print("Q1: ");
				q1.firstEnqueue(5);
				System.out.print("Q2: ");
				q2.firstEnqueue("HEJ");
				
				//test lastDequeue
				System.out.println("Test for lastDequeue: ");
				System.out.print("Q1: ");
				q1.lastDequeue();
				System.out.print("Q2: ");
				q2.lastDequeue();
				
				//test toString
				System.out.println("Test toString: ");
				System.out.println("Q1: "+q1.toString());
				System.out.println("Q2: "+q2.toString());
				
				//test isEmpty
				System.out.println("Test for empty queues: ");
				System.out.println("Q1: "+ q1.isEmpty());
				System.out.println("Q2: " + q2.isEmpty());
				
				//test size
				System.out.println("Test for size: ");
				System.out.println("Q1: "+q1.size());
				System.out.println("Q2: "+q2.size());
				
				//the iterator returns the items from each element
				System.out.println("Test for circular: ");
				Iterator<Integer> testIterator = q1.iterator();
				for(int i = 0; i<5; i++) {
					System.out.println(testIterator.next()+" ");
				}
			
				//test dequeue
				System.out.println("Test for repeated dequeue: ");
				for(int i = 0; i < 4; i++) {
		            q1.dequeue();
		        }
				
				
				
				//tests dequeue is queue is empty
				System.out.println("Test for queue underflow: ");
				q2.dequeue();
	}
	
}