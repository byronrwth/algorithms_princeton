import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * A double-ended queue or deque (pronounced "deck")
 *  is a generalization of a stack and a queue that 
 *  supports inserting and removing items from either
 *   the front or the back of the data structure.
 * */
public class Deque<Item> implements Iterable<Item> {
    private int N = 0;         // number of elements on queue
    private Node first = null;    // beginning of queue
    private Node last = null;     // end of queue
    private boolean debugs = true;
    
    // helper linked list class
    private class Node {
        private Item item = null;
        private Node before = null;
        private Node next = null;
    }    
    
    public boolean isEmpty() {
//        if (first == null && last == null && N == 0) return true;
//        else if (first != null && last != null && N != 0) return false;
//        else {
//            assert check();
//        }
        return first == null || last == null;
    }
    
    public int size() {
        // return the number of items on the deque
        return N; 
    }
    
    public void addFirst(Item item) { //like LikedStack.push(item)
        // insert the item at the front
        if (item == null) {
            throw new NullPointerException();
        }
        else { 
            if (isEmpty()) {
                first = new Node();
                first.item = item;
                last = first;
            }
            else {
                Node oldfirst = first;
                first = new Node();
                first.item = item;
                first.next = oldfirst;
                oldfirst.before = first;
            }
            N++;

//            StdOut.println("N is " + N);
//            StdOut.println("first node is " + first.item);
//            StdOut.println("next to first node is " + first.next);
//            StdOut.println("previous to first node is " + first.before);
//            StdOut.println("last node is " + last.item);
//            StdOut.println("next to last node is " + last.next);
//            StdOut.println("previous to last node is " + last.before);
            
            if (debugs) {
                assert check();
            }
        }
    }
    
    public void addLast(Item item) {// LinkedQueue.enqueue(item)
        // insert the item at the end
        if (item == null) {
            throw new NullPointerException();
        }
        else { 
            if (isEmpty()) {
                last = new Node();
                last.item = item;
                first = last;
            }
            else {
                Node oldlast = last;
                last = new Node();
                last.item = item;
                //last.next = null;
                last.before = oldlast;
                oldlast.next = last;
            }
            N++;
            if (debugs) {
                assert check();
            }
        }
    }
    
    public Item removeFirst() {//like LinkedQueue.dequeue()
        // delete and return the item at the front
        
        //if the client attempts to remove an item from an empty deque
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        else { 
            Item item = first.item;        // save item to return
            first = first.next;            // delete first node
            
            // is now first pointing to null ?
            if (isEmpty()) {
                last = null;   // to avoid loitering
            }
            else first.before = null;
            
            N--;
            
            if (debugs) {
                assert check();
            }
            
            return item;    
        }
    }
    
    public Item removeLast() {//like LinkedQueue.dequeue()
        // delete and return the item at the end
        
        // if the client attempts to remove an item from an empty deque
            if (isEmpty()) {
                throw new NoSuchElementException();
            }
            else {
            Item item = last.item;
            last = last.before;
            
            if (isEmpty()) first = null;   // to avoid loitering
            else last.next = null;
            
            N--;
            
            if (debugs) {
                assert check();
            }
            
            return item;
            }
    }
 
    
    @Override
    public Iterator<Item> iterator() {
     // return an iterator over items in order from front to end
        return new ListIterator();
        //return null;
    }
    
    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first; // or last ?

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 

            return item;
        }
    }
    
//    /**
//     * Returns a string representation of this queue.
//     * @return the sequence of items in FIFO order, separated by spaces
//     */
//    public String toString() {
//        StringBuilder s = new StringBuilder();
//        for (Item item : this)
//            s.append(item + " ");
//        return s.toString();
//    } 
    
    // check internal invariants
    private boolean check() {
        if (N == 0) {
            if (first != null || last != null ) return false;
        }
        else if (N == 1) {
            if (first == null || last == null)      return false;
            if (first.next != null || last.before != null || first != last) return false;
        }
        else {
            if (first.next == null || last.before == null) return false;
        }

        // check internal consistency of instance variable N
        int numberOfNodesFirst = 0;
        int numberOfNodesLast = 0;
        for (Node x = first, y = last; x != null && y != null; x = x.next, y = y.before) {
            numberOfNodesFirst++;
            numberOfNodesLast++;
        }
        if (numberOfNodesFirst != N || numberOfNodesFirst != numberOfNodesLast) return false;

        return true;
    }


/*    
    // will be moved into Subset.java :
    public static void main(String[] args) {

        Deque<String> q = new Deque<String>();
        
        while (!StdIn.isEmpty()) {

            String input = StdIn.readString();
            
            if (input.equals("removefirst")) {
//                String input2 = StdIn.readString();
//                while (input2.equals("-")) {
                    StdOut.println(q.removeFirst() + " ");
                    StdOut.println("(" + q.size() + " left on deque)");
//                }
            }
            else if (input.equals("removeLast")) {
//                String input2 = StdIn.readString();
//                while (input2.equals("-")) {
                    StdOut.println(q.removeLast() + " ");
                    StdOut.println("(" + q.size() + " left on deque)");
//                }
            }
            else if (input.equals("addlast")) {
                //Item last = StdIn.readString();
                String input2 = StdIn.readString();
//                while (!input2.equals("+")) {
                    q.addLast(input2);
                    StdOut.println("(" + q.size() + " left on deque)");
//                }
                //StdOut.println(q.addLast(input) + " ");
            }
            else if (input.equals("addfirst")) {
                //String first = StdIn.readString();
                String input2 = StdIn.readString();
//                while (!input2.equals("+")) {
                    q.addFirst(input2);
                    StdOut.println("(" + q.size() + " left on deque)");
//                }
                //StdOut.println(q.addFirst(first) + " ");
            }
            
            
//            StdOut.println("(" + q.size() + " left on deque)");
        }
        //StdOut.println("(" + q.size() + " left on deque)");
        


    }*/ 
    

}
