package p01_PingPong_B;


//processes "ping" and "pong" outside the monitor.

public class PingPongProblem_B {

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

         monitor.wantToPing();
         System.out.println("PING");
         monitor.pingDone();
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

         monitor.wantToPong();
         System.out.println("  PONG");
         monitor.pongDone();
     }
 }
}

class PingPongMonitor {

 public static final int PING = 1;
 public static final int PONG = 2;
 private int turn = PING;

 public synchronized void wantToPing() {
     while (turn!=PING)
         try{wait();}catch(InterruptedException ie){}
 }

 public synchronized void pingDone() {
     turn = PONG;
     notify();
 }

 public synchronized void wantToPong() {
     while (turn!=PONG)
         try{wait();}catch(InterruptedException ie){}
 }

 public synchronized void pongDone() {
     turn = PING;
     notify();
 }
}
