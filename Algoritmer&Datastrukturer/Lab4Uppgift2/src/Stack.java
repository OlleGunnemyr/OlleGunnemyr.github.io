

//Abstract data type (ADT) copied from princeton slide 1.3 (StackLinkedString), modified to be generic
public class Stack<Item> {
private Node<Item> first = null;
private int n;
	
	//inner class
	private static class Node<Item>
	{ 
		private Item item;
		private Node<Item> next;
	}
	
	public boolean isEmpty()//if the stack is empty return true, otherwise false
	{ return first == null; }

	//push item on top of stack
	public void push(Item item)
	{ Node<Item> oldfirst = first;//save a link to the list
	first = new Node<Item>(); //create a new node for the beginning
	first.item = item; //set the instance variables in the new node
	first.next = oldfirst;
	n++;
	}
	
	//pop item from top of stack
	public Item pop()
	{ Item item = first.item; //save item to return
	first = first.next;//delete first node
	n--;
	return item;//return saved item
	}
	
	public int size() {
		return n;
	}
		
	

}