
import java.util.NoSuchElementException;
import java.util.Iterator;

//Copied from princeton library (Queue.java), modified to be based on a circular linked list with two added methods: firstEnqueue and lastDequeue.
//The Iterator is the only unchanged code from Queue.java, since it performs the same needed task in this class.
public class Queue4<Item> {
	 private Node<Item> first;    // front of queue
	 private Node<Item> last;     // back of queue
	 private int n;               // size of queue


	//inner class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    //initialize empty queue
    public Queue4() {
        first = null;
        last  = null;
        n = 0;
    }

   
    //returns true and resets size to zero (to prevent negative size), if the queue is empty. Otherwise returns false
    public boolean isEmpty() {
    	
    	 if(first == null || last == null)          {
             n = 0;   
             return true;
         }
         return false; 
    }

    
    //returns size of queue
    public int size() {
        return n;
    }

   
    //Enqueue adds a new item in the back of queue.
    //If it is the first element in queue, it will be both the front and back. 
    public void enqueue(Item item) {
    	Node<Item> oldlast = last; //save a link to the last node
    	last = new Node<Item>(); //creates a new node for the back
    	last.item = item; 
        
    	if (isEmpty()) { //Used when you add the first element
    		first = last; 
    	}
       
        else {
        	oldlast.next = last;//link the new node to the back of the queue 
        }
       
       last.next = first; //circular linked list
       n++; //size increases
       
       System.out.println(this.toString());//prints out the queue
    }
    
    
    //firstEnqueue adds a new item in front of queue.
    //If it is the first element in queue, it will be both the back and front. 
    public void firstEnqueue(Item item) {
    	 Node<Item> oldfirst = first; //save a link to the first node
         first = new Node<Item>();//creates a new node for the front
         first.item = item;
         
         if (isEmpty()) {//Used when you add the first element
         last = first;
         }
        
         else  {
        	 first.next = oldfirst; //link the new node to the front of the queue
         }
        
         last.next=first; //circular linked list
         n++;//size increases
         
         System.out.println(this.toString());//prints out the queue
    	
    }

    //Dequeue removes an element from the front of queue.
    //If the queue is already empty, throw exception.
    public Item dequeue() {
    	if (isEmpty()) throw new NoSuchElementException("Queue underflow"); 
        Item item = first.item; // save item to return
        
        //if there is only one element in queue, set first and last to null to complete isEmpty
        if(n==1) {
       	first = null;
       	last = null;
        }
        else {
        first = first.next;//delete first node
        last.next = first; //circular linked list
        }
        n--; //size decreases
        System.out.println(this.toString());//print out queue
        return item; //returns the removed item
    }

    
    //lastDequeue removes an item from the back of queue.
    //If the queue is already empty, throw exception.
    public Item lastDequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow"); //if queue is already empty, throw exception
        Item item = last.item; //save item to return
        
        //if there is only one element in queue, set first and last to null to complete isEmpty
        if(n==1) {
        	first = null;
        	last = null;
        }
        
        //since this is not a double linked list, we need to create a node who travels from first node to second to last node.
        else{
        	Node<Item> newlast = first;
       
        	//newlast reference the second to last node.
        	for(int i = 1; i < n; i++) {
        		newlast = newlast.next;
        	}
        
        	last = newlast; //last references the new last node
        	last.next = first; //circular linked list
       
        	} 
        n--;//size decreases
       	System.out.println(this.toString());//print out queue
       	return item; //return item
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

   
    //returns a new iterator, which iterates through the queue in FIFO-order
    public Iterator<Item> iterator()  {
        return new LinkedIterator(first);  
    }

    //unchanged iterator from Queue.java princeton
    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current; //declare reference current

        //current points to the first node
        public LinkedIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }
        
        
        //next() returns item from the element, and current moves to the next
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            
            return item;
            
        }

    }

   
}
