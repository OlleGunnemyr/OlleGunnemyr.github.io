/**
 * README
 * Name:	 Olle Gunnemyr
 * Date:	 2020-09-28
 * Program:	 SeparateChainingHashST.java
 * 
 * SeparateChainingHashST.java represents a symbol table, distributed in an array of the type SequentialSearchST.
 * The hash method uses hashcode, when distributing the keys and values.
 * 
 * The SequentialSearchST and SeperateChainingHashST are copied and modified from https://algs4.cs.princeton.edu.
 * A method is added to return the size of a linked list in index.
 */

public class SeparateChainingHashST<Key, Value>{
		    private int n;                                // number of key-value pairs
		    private int m;                                // hash table size
		    private SequentialSearchST<Key, Value>[] st;  // array of linked-list symbol tables
		    
		    //constructor
		    public SeparateChainingHashST(int m) {
		        this.m = m;
		        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
		        for (int i = 0; i < m; i++)
		            st[i] = new SequentialSearchST<Key, Value>();
		    } 
		    
		 // hash value between 0 and m-1
		    private int hash(Key key) {
		        return (key.hashCode() & 0x7fffffff) % m;
		    } 
		    
		    //returns size of hash table
		    public int size() {
		        return n;
		    } 
		    
		    //returns size of linked list in index i
		    public int getSize(int i) {
		    	return st[i].size();
		    }
		    
		    //adds key-value pair to hash table
		    public void put(Key key, Value val) {
		        int i = hash(key);
		        if (!st[i].contains(key)) n++;
		        st[i].put(key, val);
		    } 
		    
		    
		    
		    
	}
	