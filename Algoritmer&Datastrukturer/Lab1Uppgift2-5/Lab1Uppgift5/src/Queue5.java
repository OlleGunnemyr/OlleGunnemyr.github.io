import java.util.Iterator;
import java.util.NoSuchElementException;

//Copied from princeton library (Queue.java), modified to be based on a single linked list with one added method: deleteAt.
//The Iterator is the only unchanged code from Queue.java, since it performs the same needed task in this class.
public class Queue5<Item>{
	 private Node<Item> first;    // front of queue
	 private Node<Item> last;     // back of queue
	 private int n;               // size of queue

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    //initialize empty queue
    public Queue5() {
        first = null;
        last  = null;
        n = 0;
    }

  //returns true if its empty and resets size to zero
    public boolean isEmpty() {
    	
    	 if(first == null)          {
             n = 0;   
             return true;

         }
         return false; 
    }

    //returns size of queue
    public int size() {
        return n;
    }

    //adds new element in the back of queue
    public void enqueue(Item item) {
        Node<Item> oldlast = last;//save a link to the last node
        last = new Node<Item>();//creates a new node for the back
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;//Used when you add the first element
        else           oldlast.next = last;//link the new node to the back of the queue 
        n++;//size increases
        System.out.println(this.toString());//prints out the queue
    }

    //removes an element from the front of queue
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");//if queue is already empty, throw exception
        Item item = first.item;// save item to return
        first = first.next;//delete first node
        n--;  //size decreases
        System.out.println(this.toString());//prints out the queue
        if (isEmpty()) last = null;   // to avoid loitering
      
        return item;
    }

    //removes an element at chosen index
    public void deleteAt(int index) {
    	if (isEmpty()) throw new NoSuchElementException("Queue underflow");//if queue is already empty, throw exception
    	
    	 if(n==1) {
    	       	first = null;
    	       	n--;
    	       	 System.out.println(this.toString());
    	        }
    	
    	 else {
    	 
    	if(index > n || index <= 0) {
    		throw new NoSuchElementException("No such Index");//if index does not exist in queue, throw exception
    	}
    	
    	//to delete the last element, the method uses the same code as in lastDequeue from previous assignment
    	else if (index == 1) {
    		
    		 Node<Item> newlast = first;
    	     
    		 //newlast reference the second to last node.
    		 for(int i = 1; i < n; i++) {
    			 newlast = newlast.next;
    		 }
    	        
    		 last = newlast; //last references the new last node
    		 last.next = null;
    		 n--;//size decreases
    		 System.out.println(this.toString());
    		 
    	}
    	
    	//to delete the first element, you change the reference first to point to the next node
    	else if (index == n) {
    		first = first.next;
    		n--;
    		System.out.println(this.toString());
    	}
    	else {//the node travel moves from first to the second before index
    		Node<Item> move = first;
    		Node<Item> remove = null;
    		
    		for(int i = 1; i<n-index; i++) {
    			move = move.next;
    		}
    		
    		//remove referse to the index which will be removed
    		remove = move.next;
    		move.next = remove.next;//link the two nodes
    		 
    		remove = null;
    		n--;
    		System.out.println(this.toString());
    	}
    	
    }
    }
    

   
    //toString builds a string of the elements from queue. If the queue is empty, return string "No elements in queue".
    public String toString() {
    	if(isEmpty()) {
    		return "No elements in queue";
    	}
    	Iterator<Item> strIterator = iterator(); //creates iterator and string builder
        StringBuilder str = new StringBuilder();
        
        //the iterator iterates through the queue and returns items from each node.
        for (int i = 0; i < n-1; i++){ 
            str.append("[");
            str.append(strIterator.next());
            str.append("], ");
        }
        
        //the string will end with a bracket
        str.append("[");
        str.append(strIterator.next());
        str.append("]");
        return str.toString(); //return queue as string
    }

   
    public Iterator<Item> iterator()  {
        return new LinkedIterator(first);  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
}
