To compile and run
javac ProducerConsumer.java
java ProducerConsumer <Time> <Producer Threads> <Consumber Threads>


What this program does is uses a bounded buffer to store information
created by the producer and the information is taken out by the consumer.

It uses two semaphore and a mutex to allow movement in and out of
the critical sections.

At the start there are a user specified amount of producer and consumer Threads

When all the threads are created and starting to run, they will loop their run section 100 times total.

However the main program will sleep for the user specified time and will exit the program 
after reaching that time.  