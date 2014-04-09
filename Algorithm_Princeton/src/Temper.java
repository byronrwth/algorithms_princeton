package org.chengalgorithm.first;

public class Temper implements Comparable<Temper>{

        private final double deg;
        
        public Temper(double deg) {
            // TODO Auto-generated constructor stub
            this.deg = deg;
        }
        
        public int compareTo(Temper that) {
            // TODO Auto-generated method stub
            double EPS = 0.1;
            if (this.deg < that.deg - EPS){
                return -1;
            }
            if (this.deg > that.deg + EPS){
                return +1;
            }
            return 0;
        }

    
    public static void main(String[] args) {
        // TODO Auto-generated method stub


        Temper a = new Temper(10.16);
        Temper b = new Temper(10.08);
        Temper c = new Temper(10.00);
        StdOut.println("a.compareTo(b): " + a.compareTo(b) );
        StdOut.println("b.compareTo(c): " + b.compareTo(c) );
        StdOut.println("a.compareTo(c): " + a.compareTo(c) );
    }


}
