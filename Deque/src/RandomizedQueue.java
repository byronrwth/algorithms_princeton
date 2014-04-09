import java.util.Iterator;
import java.util.NoSuchElementException;



/*
public class RandomizedQueue<Item> implements Iterable<Item> {
   public RandomizedQueue()           // construct an empty randomized queue
   public boolean isEmpty()           // is the queue empty?
   public int size()                  // return the number of items on the queue
   public void enqueue(Item item)     // add the item
   public Item dequeue()              // delete and return a random item
   public Item sample()               // return (but do not delete) a random item
   public Iterator<Item> iterator()   // return an independent iterator over items in random order
}
*/
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;            // queue elements
    private int N = 0;           // number of elements on queue
    //private int first = 0;       // index of first element of queue
    private int last  = 0;       // index of next available slot


    /**
     * Initializes an empty queue.
     */
    public RandomizedQueue() {
        // cast needed since no generic array creation in Java
        q = (Item[]) new Object[2];
    }

    /**
     * Is this queue empty?
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Returns the number of items in this queue.
     * @return the number of items in this queue
     */
    public int size() {
        return N;
    }

    // resize the underlying array
    private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = q[i % q.length];
        }
        q = temp;
        //first = 0;
        last  = N;
    }

    /**
     * Adds the item to this queue.
     * @param item the item to add
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        else {
            // double size of array if necessary and recopy to front of array
            if (N == q.length) resize(2*q.length);   // double size of array if necessary
            q[last++] = item;                        // add item
            if (last == q.length) last = 0;          // wrap-around
            N++;
        }
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     * @return the item on this queue that was least recently added
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        else {
            int index = StdRandom.uniform(0 , N);
            Item item = q[index];
            q[index] = q[N-1];
            q[N-1] = null;
            N--;
            last = N;
            if (N > 0 && N == q.length/4) resize(q.length/2); 

            return item;
        }
    }
    /*
     * return (but do not delete) a random item 
     * */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        else {
            int index = StdRandom.uniform(0 , N);
            return q[index];
        }
    }
    


    /**
     * Returns an iterator that iterates over the items in this queue in random order !.
     * @return an iterator that iterates over the items in this queue in random order !.
     */
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private boolean[] searched = new boolean[N];
        
        public boolean hasNext()  { return i < N;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            else {
                int random = 0;
                    
                while (true) {
                    random = StdRandom.uniform(0 , N);
                    if (!searched[random]) {
                        break;
                    }
                }
                Item item = q[random];
                searched[random] = true;
                i++;
                return item;
           }
        }
    }

 
//    public static void main(String[] args) {
//        RandomizedQueue<String> q = new RandomizedQueue<String>();
//        while (!StdIn.isEmpty()) {
//            String item = StdIn.readString();
//            if (!item.equals("-")) q.enqueue(item);
//            else { 
//                StdOut.print(q.dequeue() + " ");
//            }
//        }
//        StdOut.println("(" + q.size() + " left on queue)");
//    }

}