//  Multiple producers, one consumer program with monitors
//
// Usage:
//         javac bear.java
//         java numBees numHoney
class Pot{
	private int value;
	private boolean full = true;
	private int id;

	public synchronized void put(int id, int maxValue){//invoked by one thread at a time
		this.id = id;
		full = (value == maxValue)? true : false;
		notifyAll();//signal all threads waiting on condition full

		while(full){
			try{
				wait();//wait on implicit condition full
			}
			catch(InterruptedException e){
			}
		}
		value++;
		System.out.println("Bee "+id+" add");

	}

	public synchronized void take(){//invoked by one thread at a time
		while (!full){
			try{
				wait();//wait on implicit condition !full
			}
			catch (InterruptedException e){
			}
		}
		System.out.println("Bee "+id+" calls bear");
		this.value = 0;
		System.out.println("Bear eats honeypot");
		full = false;
		notifyAll();//signal all threads waiting on condition !full
	}
}


class Bees extends Thread{
	private Pot pot;
	private int id;
	private int maxValue;
	public Bees(Pot pot, int id, int maxValue){
		this.pot = pot;
		this.id = id;
		this.maxValue = maxValue;
	}

	public void run(){
		while(true){
			pot.put(id, maxValue);
			try{
				sleep(1000);
				System.out.println("Bee "+id+" wakes up");
			}
			catch(InterruptedException e){
			}
		}
	}
}



class BigBear extends Thread{
	private Pot pot;
	public BigBear (Pot pot){
		this.pot = pot;
	}

	public void run(){
		while(true){
			pot.take();
		}
	}
}





class bear{// main program -- N producers and one consumer
	private static int MAXBEES = 10;
	private static int MAXHONEY = 10;

	public static void main(String args[]){
		Pot pot = new Pot();

		int numBees = (args.length > 0)? Integer.parseInt(args[0]) : MAXBEES;
		int numHoney = (args.length > 1)? Integer.parseInt(args[1]) : MAXHONEY;
		if (numBees > MAXBEES) numBees = MAXBEES;
		if (numHoney > MAXHONEY) numHoney = MAXHONEY;


		BigBear p = new BigBear(pot);
		p.start();
		for(int i = 0; i < numBees; i++){
			Bees c = new Bees(pot, i, numHoney);
			c.start();
		}
	}
}