/*  Name: Rylan McAlister
    UA ID: 010794211    */

import java.util.concurrent.Semaphore;
import java.util.Random;

//BUFFER CLASS 
class Buffer{
    final int BUFFER_SIZE = 5;
    int[] buffer;

    Semaphore full = new Semaphore(5);
    Semaphore empty = new Semaphore(0);
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
class Producer extends Thread{
    Random randSleep = new Random();
    Random randInsert = new Random();
    Buffer b;

    public Producer(Buffer buffer){
        this.b = buffer;
        while(true){
            try{    
                Thread.sleep(randSleep.nextInt(500)); 
            }
            catch(InterruptedException e){}
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
class Consumer extends Thread{
    Random rand = new Random();
    Buffer b;

    public Consumer(Buffer buffer){
        this.b = buffer;
        while(true){
            try{    
                Thread.sleep(rand.nextInt(500)); 
            }
            catch(InterruptedException e){}
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
            Producer p = new Producer(buffer);
            p.start();
            try{
                p.join();
            } catch(InterruptedException e){}
        }

        for(int i = 0; i < conThreads;i++){
            Consumer c = new Consumer(buffer);
            c.start();
            try{
                c.join();
            } catch(InterruptedException e){}
        }

        try{    
            Thread.sleep(sleepTime*1000); 
        }
        catch(InterruptedException e){}

    }

}