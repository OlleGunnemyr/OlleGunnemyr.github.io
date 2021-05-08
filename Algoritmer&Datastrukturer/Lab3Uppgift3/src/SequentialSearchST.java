/**
 * README
 * Name:	 Olle Gunnemyr
 * Date:	 2020-09-28
 * Program:	 SequentialSearch.java
 * 
 * SequentialSearch.java is sequential search in a linked list of key-value pairs. 
 * 
 * The SequentialSearchST and SeperateChainingHashST are copied and modified from https://algs4.cs.princeton.edu.
 */


public class  SequentialSearchST<Key, Value>{
		private int n;           // number of key-value pairs
		private Node first;      // the linked list of key-value pairs

		// a helper linked list data type
		private class Node {
			private Key key;
			private Value val;
			private Node next;

			public Node(Key key, Value val, Node next)  {
				this.key  = key;
				this.val  = val;
				this.next = next;
			}
		}
		
		//constructor
	    public SequentialSearchST() {
	    }

	    //returns size of linked list
	    public int size() {
	        return n;
	    }
	    
	    //checks if key already exists
	    public boolean contains(Key key) {
	        return get(key) != null;
	    }
	    
	   //returns value if there is duplicate of key, else null
	    public Value get(Key key) {
	        for (Node x = first; x != null; x = x.next) {
	            if (key.equals(x.key))
	                return x.val;
	        }
	        return null;
	    }
	    
	    //add key-value pair to linked list
	    public void put(Key key, Value val) {
	        for (Node x = first; x != null; x = x.next) {
	            if (key.equals(x.key)) {
	                x.val = val;
	                return;
	            }
	        }
	        first = new Node(key, val, first);
	        n++;
	    }

	}
	
	