/*  Name: Rylan McAlister
    UA ID: 010794211    */

import java.util.concurrent.Sempahore;
import java.util.Random;

//BUFFER CLASS 
public class Buffer{
    final int BUFFER_SIZE = 5;
    int[] buffer;

    public Buffer(){
        buffer = new int[BUFFER_SIZE];

    }

    int insert(){

        return 0;
    }

    int remove(){
        
        return 0;
    }

}

// PRODUCER CLASS - PUTS NUMBERS INTO THING
public class Producer extends Thread{
    Random randSleep = new Random();
    Random randInsert = new Random();

    public Producer(){
        while(true){
            sleep(randSleep.nextInt(500));
            int item = randInsert.nextInt();

        }

    }
}

//CONSUMER CLASS - TAKES NUMBERS OUT OF THING
public class Consumer extends Thread{
    Random rand = new Random();

    public Consumer(){
        while(true){
            sleep(rand.nextInt(500));



        }
    }
}


public class ProducerConsumer{
    public static void main(String args[]){
        //Grabbing command line arguements
        int sleepTime = Integer.parseInt(args[0]);
        int proThreads = Integer.parseInt(args[1]);
        int conThreads = Integer.parseInt(args[2]);

        //Initialize classes
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