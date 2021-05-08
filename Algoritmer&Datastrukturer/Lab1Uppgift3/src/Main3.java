import java.util.Iterator;

public class Main3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//create two queues of the type Integer and String
		Queue3<Integer> q1 = new Queue3<Integer>();
		Queue3<String> q2 = new Queue3<String>();
		
		//test enqueue
		System.out.println("Test for repeated enqueue: ");
		for(int i = 0; i < 4; i++) {
            q1.enqueue(i);
        }
		
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
		
		//the test-iterator prints out the previous, current and next element in the queue. 
		System.out.println("Test for circular and double: ");
		Iterator<Integer> testIterator = q1.iteratorTest();
		for(int i = 0; i<4; i++) {
			testIterator.next();
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
