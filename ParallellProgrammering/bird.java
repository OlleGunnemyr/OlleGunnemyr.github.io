//  One producer, multiple consumers program with monitors
//
// Usage:
//         javac bird.java
//         java numBabyBirds numWorms
class Dish{
	private int value;
	private boolean empty = true;
	private int id;

	public synchronized void take(int id){//invoked by one thread at a time
		this.id = id;
		empty = (value == 0)? true : false;
		notifyAll();//signal all threads waiting on condition empty

		while(empty){
			try{
				wait();//wait on implicit condition empty
			}
			catch(InterruptedException e){
			}
		}
		value--;
		System.out.println("BabyBird "+id+" eats and sleeps");
	}

	public synchronized void put(int value){//invoked by one thread at a time
		while (!empty){
			try{
				wait();//wait on implicit condition !empty
			}
			catch (InterruptedException e){
			}
		}
		System.out.println("Baby "+id+" calls parent");
		this.value = value;
		System.out.println("Parent fills");
		empty = false;
		notifyAll();//signal all threads waiting on condition !empty

	}
}




class BabyBirds extends Thread{
	private Dish dish;
	private int id;
	public BabyBirds(Dish dish, int id){
		this.dish = dish;
		this.id = id;
	}

	public void run(){
		while(true){
			dish.take(id);
			try{
				sleep(1000);
				System.out.println("Baby "+id+" wakes up");
			}
			catch(InterruptedException e){
			}
		}
	}
}



class ParentBird extends Thread{
	private Dish dish;
	private int value;
	public ParentBird (Dish dish, int value){
		this.dish = dish;
		this.value = value;
	}

	public void run(){
		while(true){
			dish.put(value);
		}
	}
}



class bird{// main program -- N consumers and one producer
	private static int MAXBABYBIRDS = 10;
	private static int MAXWORMS = 10;

	public static void main(String args[]){
		Dish dish = new Dish();

		int numBabyBirds = (args.length > 0)? Integer.parseInt(args[0]) : MAXBABYBIRDS;
		int numWorms = (args.length > 1)? Integer.parseInt(args[1]) : MAXWORMS;
		if (numBabyBirds > MAXBABYBIRDS) numBabyBirds = MAXBABYBIRDS;
		if (numWorms > MAXWORMS) numWorms = MAXWORMS;

		ParentBird pb = new ParentBird(dish, numWorms);
		pb.start();
		for(int i = 0; i < numBabyBirds; i++){
			BabyBirds bb = new BabyBirds(dish, i);
			bb.start();
		}
	}
}