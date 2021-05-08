import java.util.NoSuchElementException;
import java.util.Iterator;


//Copied from princeton library (Queue.java), modified to be based on a double linked circular list.
//The Iterator is the only unchanged code from Queue.java, since it performs the same needed task in this class.
public class Queue3<Item>{
    private Node<Item> first;    // front of queue
    private Node<Item> last;     // back of queue
    private int n;               // size of queue

    
    //inner class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
		private Node<Item> prev; //double linked list
    }

    
    //initialize empty queue
    public Queue3() {
        first = null;
        last  = null;
        n = 0;
    }

   
    //returns true and resets size to zero (to prevent negative size), if the queue is empty. Otherwise returns false
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
        	oldlast.next = last; //link the new node to the end of the list 
        	last.prev = oldlast;//double linked list
        }
        
        first.prev = last;//circular double linked list
        last.next=first;
        
        n++;//size increases
       
        System.out.println(this.toString());//prints out the queue
    }

    
    //Dequeue removes an item from the front of queue.
    //If the queue is already empty, throw exception.
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow"); 
        Item item = first.item; // save item to return
        
        //if there is only one element in queue, set first to null (to set isEmpty true).
         if(n==1) {
        	first = null;
         }
       
         else {
          first = first.next;//delete first node
          first.prev = last;//circular double linked list
          last.next=first; 
         }
         
         n--; //decrease size
         System.out.println(this.toString()); //print out queue
         return item; //returns the removed item
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
    
    
    
    
    
    
    
    
    //this iterator is used in the testing, to show that the queue works as a circular double linked list.
    //iteratorTest is identical to iterator, except this is modified to print out the previous, current and next node
    public Iterator<Item> iteratorTest()  {
        return new LinkedIteratorTest(first);  
    }

    private class LinkedIteratorTest implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIteratorTest(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            
            //prints out previous, current and next item
            System.out.println("Previous: " + current.prev.item +" Current: "+item + " Next: " + current.next.item);
            
            current = current.next;
            
            return item;
            
        }
        
    }
}
