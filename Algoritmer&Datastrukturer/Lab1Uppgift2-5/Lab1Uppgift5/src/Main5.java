import  java.util.Iterator;
public class Main5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//create a queue of the type Integer
		Queue5<Integer> q1 = new Queue5<Integer>();
		Queue5<String> q2 = new Queue5<String>();
			
		//test enqueue
		System.out.println("Test for repeated enqueue: ");
		for(int i = 0; i < 4; i++) {
			q1.enqueue(i);
		}
				
		//test deleteAt
		System.out.println("Test deleteAt: ");
		q1.deleteAt(2);
		
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
		System.out.println("Test for Iterator: ");
		Iterator<Integer> testIterator = q1.iterator();
		while(testIterator.hasNext()) {
			System.out.println(testIterator.next()+" ");
		}
		
		//test dequeue
		System.out.println("Test for repeated dequeue: ");
		for(int i = 0; i < 3; i++) {
			q1.dequeue();
		}
		
		//tests deleteAt if index is larger or smaller than size
		System.out.println("Test for no such index: ");
		q2.deleteAt(7);
	}
	
}
