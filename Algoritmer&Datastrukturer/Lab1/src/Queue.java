
import java.util.NoSuchElementException;
import java.util.Iterator;


public class Queue<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue
    private int n;               // number of elements on queue

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
		private Node<Item> prev; //double linked list
    }

    /**
     * Initializes an empty queue.
     */
    public Queue() { //konstruktor
        first = null;
        last  = null;
        n = 0;
    }

    /**
     * Returns true if this queue is empty.
     *
     * @return {@code true} if this queue is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int size() {
        return n;
    }

    /**
     * Returns the item least recently added to this queue.
     *
     * @return the item least recently added to this queue
     * @throws NoSuchElementException if this queue is empty
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.item;
    }

    /**
     * Adds the item to this queue.
     *
     * @param  item the item to add
     */
    public void enqueue(Item item) {
        Node<Item> oldlast = last; //last blir noden oldlast
        last = new Node<Item>(); //skapar nya noden last
        last.item = item; //ger den nya item
        last.next = null; //nya last pekar på null
        
       
        last.prev = oldlast; //last ska peka tillbaka på oldlast pga double
       
        if (isEmpty()) { //körs när man lägger till första elementet 
        first = last; 
        last.next=first;
        }
       
        else oldlast.next = last; //oldlast pekar på last
       
        first.prev = last;//cirkulär
        last.next=first;
        n++;//storlek
       
        System.out.println(this.toString());//new
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     *
     * @return the item on this queue that was least recently added
     * @throws NoSuchElementException if this queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item; // tar första elementet
        first = first.next;//nya första är det som var innan
     
        n--;
        if (isEmpty()) { last = null;
        
        }// to avoid loitering
        
        else {first.prev = last;//cirkulär
        last.next=first;
        }
        System.out.println(this.toString());
        return item;
    }

    /**
     * Returns a string representation of this queue.
     *
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public String toString() {
    	Iterator<Item> sIterator = iterator(); //iterator går igenom kön 
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n-1; i++){ //bygger sträng med item från elementen i kön
            s.append("[");
            s.append(sIterator.next());
            s.append("], ");
        }
        s.append("[");
        s.append(sIterator.next());//sista elementet ska inte sluta med kommatecken
        s.append("]");
        return s.toString();
    }

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     *
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
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
    
    public Iterator<Item> iterator2()  {
        return new LinkedIterator2(first);  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class LinkedIterator2 implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator2(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }
        
        
        	
        	
        

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            System.out.println("prev: " + current.prev.item);//new
            current = current.next;
            
            return item;
            
        }
        
       
        
    }

   
}
