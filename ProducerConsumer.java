/*  Name: Rylan McAlister
    UA ID: 010794211    */

import java.util.concurrent.Semaphore;
import java.util.Random;
 
//BUFFER CLASS 
class Buffer{
    //Declare needed buffer varables 
    final int BUFFER_SIZE = 5;
    int[] buffer;
    int currentPos = 0; //How many slots are open in the buffer
    int outPos = 0; //Position of what comes out next
    int inPos = 0; //Position of what comes in next.

    //Create semaphores
    Semaphore full = new Semaphore(5);
    Semaphore empty = new Semaphore(0);
    Semaphore mutex = new Semaphore(1);

    public Buffer(){ //Create the bounded buffer array.
        this.buffer = new int[this.BUFFER_SIZE];
    }

    //Adds an item to the buffer
    int insert(int item){
        while(currentPos == BUFFER_SIZE){} //No need to do anything if we're full

        //if we're not full
        currentPos++;
        buffer[inPos] = item;
        inPos = (inPos+1) % BUFFER_SIZE;
        System.out.println("Producer Produced: "+ item);
        return 0;
    }

    //Removes an item form the buffer
    int remove(){
        while(currentPos == 0){} //No need to do anything if we're empty.
        
        //if we're not empty...
        currentPos--;
        int consumed = buffer[outPos];
        outPos = (outPos+1) % BUFFER_SIZE;
        System.out.println("Consumer consumed: "+consumed);
        return consumed;
    }

}

// PRODUCER CLASS - PUTS NUMBERS INTO BUFFER
class Producer extends Thread{
    Random randSleep = new Random();
    Random randInsert = new Random();
    Buffer b;

    public Producer(Buffer buffer){
        this.b = buffer;
    }

    public void run(){
        for(int i=0; i<100;i++){
            try{ 
                Thread.sleep(randSleep.nextInt(501)); 
            } catch(InterruptedException e){
                System.out.println("Error in Producer sleeping");
            }

            int item = randInsert.nextInt();

            try{ //Aquire
                b.empty.acquire();
            } catch(InterruptedException e){
                System.out.println("Failed to acquire empty in Producer class");
            }

            try{ //Aquire
                b.mutex.acquire();
            } catch(InterruptedException e){
                System.out.println("Failed to acquire mutex in Producer class");
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

    public Consumer(Buffer buffer){
        this.b = buffer;
    }

    public void run(){
        for(int i = 0;i<100;i++){
            try{
                Thread.sleep(rand.nextInt(501));
            }catch(InterruptedException e){
                System.out.println("Exception in Consumer Sleeping");
            }

            try{
                b.full.acquire();
            } catch (InterruptedException e){
                System.out.println("Exception in acquireing full in Consumer");
            }

            try{
                b.mutex.acquire();
            } catch (InterruptedException e){
                System.out.println("Exception in acquireing mutex in Consumer");
            }

            b.remove();
            b.mutex.release();
            b.full.release();

        } //End of loop
 
    }//End of run
}


public class ProducerConsumer{
    public static void main(String args[]) throws InterruptedException{
        //Grabbing command line arguements
        int sleepTime = Integer.parseInt(args[0]);
        int proThreads = Integer.parseInt(args[1]);
        int conThreads = Integer.parseInt(args[2]);

        //Initialize classes and threads
        Buffer buffer = new Buffer();

        for(int i = 0; i < proThreads;i++){
            Producer p = new Producer(buffer);
            p.start();
        }

        for(int i = 0; i < conThreads;i++){
            Consumer c = new Consumer(buffer);
            c.start();
        }

        Thread.sleep(sleepTime*1000); 
        System.exit(0);

    }

}
