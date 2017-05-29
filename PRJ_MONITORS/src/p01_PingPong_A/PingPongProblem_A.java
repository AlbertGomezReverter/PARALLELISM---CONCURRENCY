package p01_PingPong_A;


//processes "ping" and "pong" inside the monitor.

public class PingPongProblem_A {

 public static void main (String [] args) {

     PingPongMonitor monitor = new PingPongMonitor();
     Ping ping = new Ping(monitor);
     Pong pong = new Pong(monitor);

     pong.start();
     ping.start();

     try {Thread.sleep(5000);} catch(InterruptedException ie) {}

     ping.stop();
     pong.stop();
 }
}


class Ping extends Thread {
 private PingPongMonitor monitor;

 public Ping (PingPongMonitor monitor) {
     this.monitor = monitor;
 }

 @Override
 public void run () {
     while (true) {
         try {sleep((long)Math.random()*500);}
             catch(InterruptedException ie) {}
         monitor.doPing();
     }
 }
}

class Pong extends Thread {
 private PingPongMonitor monitor;

 public Pong (PingPongMonitor monitor) {
     this.monitor = monitor;
 }

 @Override
 public void run () {
     while (true) {
         try {sleep((long)Math.random()*500);}
             catch(InterruptedException ie) {}
         monitor.doPong();
     }
 }
}

class PingPongMonitor {

 public static final int PING = 1;
 public static final int PONG = 2;
 
 private int turn = PING;

 public synchronized void doPing() {
     while (turn!=PING)
         try{wait();}catch(InterruptedException ie){}

     System.out.println("PING");
     turn = PONG;
     notify();
 }

 public synchronized void doPong() {
     while (turn!=PONG)
         try{wait();}catch(InterruptedException ie){}

     System.out.println("   PONG");
     turn = PING;
     notify();
 }
 
}

