/*  Name: Rylan McAlister
    UA ID: 010794211    */

import java.util.concurrent.Sempahore;
import java.util.Random;

//BUFFER CLASS 
public class Buffer{
    final int BUFFER_SIZE = 5;
    int[] buffer;

    Semaphore full = new Sempahore(5);
    Semapgore empty = new Semaphore(0);
    Semaphore mutex = new Semaphore(1);

    public Buffer(){
        this.buffer = new int[this.BUFFER_SIZE];

    }

    int insert(int item){

        return 0;
    }

    int remove(int item){

        return 0;
    }

}

// PRODUCER CLASS - PUTS NUMBERS INTO THING
public class Producer extends Thread{
    Random randSleep = new Random();
    Random randInsert = new Random();
    Buffer b;

    public Producer(Buffer buffer){
        this.b = buffer;
        while(true){
            Thread.sleep(randSleep.nextInt(500));
            int item = randInsert.nextInt();
            if(b.insert(item) == 0){
                System.out.println("Producer Produced "+item+".");
            } else {
                System.out.println("Producer error");
            }

        }
    }
}

//CONSUMER CLASS - TAKES NUMBERS OUT OF THING
public class Consumer extends Thread{
    Random rand = new Random();
    Buffer b;

    public Consumer(Buffer buffer){
        this.b = buffer;
        while(true){
            Thread.sleep(rand.nextInt(500));
            int item = b.remove(0);
            if(item == 0){
                System.out.println("Consumer Consumed"+ item);
            } else {
                System.out.println("Consumer error");
            }

        }
    }
}


public class ProducerConsumer{
    public static void main(String args[]){
        //Grabbing command line arguements
        int sleepTime = Integer.parseInt(args[0]);
        int proThreads = Integer.parseInt(args[1]);
        int conThreads = Integer.parseInt(args[2]);

        //Initialize classes and threads
        Buffer buffer = new Buffer();

        for(int i = 0; i < proThreads;i++){
            Producer p = new Producer();
            p.start();

        }

        for(int i = 0; i < conThreads;i++){
            Consumer c = new Consumer();
            c.start();
        }

        sleep(1000);

    }

}