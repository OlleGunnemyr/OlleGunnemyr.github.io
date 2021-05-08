
import java.util.Iterator;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		// Queue with iterator: Uppgift 3*/
		Queue<Integer> q2 = new Queue<Integer>();
		
		
		/*for(int i = 0; i < 3; i++) {
		q2.enqueue(i);
		}*/
		
		q2.dequeue();
		q2.dequeue();
		q2.dequeue();
		q2.dequeue();
		//System.out.println(q2.toString());
		
		/*Iterator<Integer> qIterator =  q2.iterator();
		Iterator<Integer> qIterator2 =  q2.iterator2();
		
		System.out.println(qIterator.next());
		System.out.println(qIterator2.next());
		/*while(qIterator.hasNext() && i <30){
		System.out.println("Current:  "+ qIterator.next());
		i++;
		}
		
		*/
		
		
	}
}

