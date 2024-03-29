/*  Name: Rylan McAlister
    UA ID: 010794211    */

import java.util.concurrent.Semaphore;
import java.util.Random;
 
//BUFFER CLASS 
class Buffer{
    //Create semaphores
    Semaphore full = new Semaphore(0);
    Semaphore empty = new Semaphore(5);
    Semaphore mutex = new Semaphore(1);

    //Declare needed buffer varables 
    final int BUFFER_SIZE = 5;
    int[] buffer = new int[5];

    int count = 0; //How many slots are open in the buffer
    int outPos = 0; //Position of what comes out next
    int inPos = 0; //Position of what comes in next.

    //Adds an item to the buffer
    void insert(int item){
        while(count == BUFFER_SIZE){} //No need to do anything if we're full

        //if we're not full
        count++;
        buffer[inPos] = item;
        inPos = (inPos+1) % BUFFER_SIZE;
        System.out.println("Producer Produced: "+ item);
    }

    //Removes an item form the buffer
    int remove(){
        while(count == 0){} //No need to do anything if we're empty.
        
        //if we're not empty...
        count--;
        int consumed = buffer[outPos];
        outPos = (outPos+1) % BUFFER_SIZE;
        System.out.println("             Consumer consumed: "+consumed);
        return consumed;
    }

}

// PRODUCER CLASS - PUTS NUMBERS INTO BUFFER
class Producer extends Thread{
    int item;
    Random randSleep = new Random();
    Random randInsert = new Random();
    Buffer b;

    public Producer(Buffer buffer){
        this.b = buffer;
    }

    public void run(){
        for(int x=0;x<100;x++){
            try{ 
                Thread.sleep(randSleep.nextInt(501));
            } catch(InterruptedException e){
                System.out.println("Error in Producer sleeping");
            }

            item = randInsert.nextInt();

            try{ //Aquire
                b.empty.acquire();
                b.mutex.acquire();
            } catch(InterruptedException e){
                System.out.println("Failed to acquire in Producer class");
            }

            //Insert and release
            b.insert(item);
            b.mutex.release();
            b.full.release();
        } //End of the loop
    } //End of run

}

//CONSUMER CLASS - TAKES NUMBERS OUT OF BUFFER
class Consumer extends Thread{
    Random rand = new Random();
    Buffer b;
    int removed = 0;

    public Consumer(Buffer buffer){
        this.b = buffer;
    }

    public void run(){
        for(int x=0;x<100;x++){
            try{ //Sleep
                sleep(rand.nextInt(501));
            }catch(InterruptedException e){
                System.out.println("Exception in Consumer Sleeping");
            }

            try{ //Acquire 
                b.full.acquire();
                b.mutex.acquire();
            } catch (InterruptedException e){
                System.out.println("Exception in acquireing in Consumer");
            }

            //Remove and release
            removed = b.remove();
            b.mutex.release();
            b.empty.release();

        } //End of loop
    }//End of run
}


public class ProducerConsumer{
    public static void main(String args[]){
        //Grabbing command line arguements
        int sleepTime = Integer.parseInt(args[0]);
        int proThreads = Integer.parseInt(args[1]);
        int conThreads = Integer.parseInt(args[2]);

        //Initialize classes and threads
        Buffer buffer = new Buffer();

        //Create consumer/producer threads
        for(int i = 0; i < proThreads;i++){
            Producer p = new Producer(buffer);
            p.start();
        }

        for(int i = 0; i < conThreads;i++){
            Consumer c = new Consumer(buffer);
            c.start();
        }

        //Wait for user time and then exit.
        try{
            Thread.sleep(sleepTime*1000); 
        }catch(InterruptedException e){}
        
        System.exit(0);
    }
}
