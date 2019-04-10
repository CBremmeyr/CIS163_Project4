package mixer;

public class DoubleLinkedList<E> {
  
    /** Referance to the top node of the list */ 
    protected NodeD<E> top;

    /** Referance to some node in the list */
    private NodeD<E> cursor;

    /** Current size of the list */
    private int size;

    /**
     * Default constructor that inits an empty list.
     */
    public DoubleLinkedList() {
        
        size = 0;
        top = null;
        cursor = null;
    }

    /**
     * Get the data at node with proided index.
     *
     * @param index - location to get data from.
     * @return data at provided index, if index is out of bouds then
     *         returns null.
     * @throws ArryOutOfBoundsException - if index input is larger than
     *         the size of the list.
     */
    public E get(int index) throws ArrayIndexOutOfBoundsException {
        
        if(!validIndex(index)) {
            throw new ArrayIndexOutOfBoundsException();
        }

        cursor = top;
        
        for(int i = 0; i < index; ++i) {
            cursor = cursor.getNext();
        }
 
        return cursor.getData();
    }

    /**
     * Repersent the list as a String.
     *
     * @return String to repersent the list.
     */
    public String toString() {

        String retVal = "";
        NodeD<E> cur = top;

        while (cur != null) {
            retVal += cur.getData();
            cur = cur.getNext();
        }

        return retVal;
    }

    /**
     * Delete a node at the given index.
     *
     * @param index is the location of the node to delete.
     * @return the value that was removed.
     * @throws ArrayIndexOutOfBoundsException if index to remove is not a valid index.
     */
    public E deleteAt(int index) throws ArrayIndexOutOfBoundsException {

        if(!validIndex(index)) {
            throw new ArrayIndexOutOfBoundsException();
        }

        NodeD<E> cursor = top;

        // Removing top
        if(index == 0) {

            // If removing only node in list
            if(size == 1) {
                top = null;
                size = 0;
                --size;
                return cursor.getData();
            }

            top = cursor.getNext();
            top.setPrev(null);

            --size;
            return cursor.getData();
        }

        // Remvoing tail
        else if(index == size-1) {
 
            // Move cursor to end of the list
            while(cursor.getNext() != null) {
                cursor = cursor.getNext();
            }

            // Remove node that cursor is on
            cursor.getPrev().setNext(null);
            --size;
            return cursor.getData();
        }

        // Removing middle
        else {

            // Move cursor to node that is being removed
            for(int i=0; i < index; ++i) {
                if(cursor.getNext() != null) {
                    cursor = cursor.getNext();
                }
            }

            System.out.println(cursor.getData());

            // Delete node that cursor is on
            NodeD<E> newNext = cursor.getNext();
            NodeD<E> newPrev = cursor.getPrev();

            newPrev.setNext(newNext);
            newNext.setPrev(newPrev);

            --size;

            return cursor.getData();
        }
    }

    /**
     * Add a new node at the end of the list.
     *
     * @param newData - the data to go into the new node.
     */
    public void add(E newData) {
       
        NodeD<E> newNode = new NodeD<E>(newData);
        cursor = top;

        if(cursor == null) {
            top = newNode;
            ++size;
            return;
        }

        // Go to last node
        while(cursor.getNext() != null) {
            cursor = cursor.getNext();
        }
        
        cursor.setNext(newNode);
        newNode.setPrev(cursor);
        ++size;
    }

    /**
     * Insert a data value at a index.
     * 
     * @param index - the location to insert data before.
     * @param data - the data to be inserted.
     */
    public void insertAt(int index, E data) throws IndexOutOfBoundsException {
        
        if(!validIndex(index) && index != size) {
            throw new IndexOutOfBoundsException();
        }
 
        NodeD<E> newNode = new NodeD<E>(data);
        NodeD<E> cursor = top;

        if(index == 0) {
            top = newNode;
            newNode.setPrev(null);
            newNode.setNext(cursor);
            cursor.setPrev(newNode);
            ++size;
            return;
        }
        if(index == size) { 
            add(data);
        }
        else {
            cursor = top;
            for(int i=0; i<index-1; ++i) {
                cursor = cursor.getNext();
            }

            NodeD<E> oldNext = cursor.getNext();
            NodeD<E> oldPrev = cursor;
            cursor.setNext(newNode);
            newNode.setNext(oldNext);
            oldNext.setPrev(newNode);
            newNode.setPrev(oldPrev);
            ++size;
            return;
        }
    }

    /**
     * Search for an data element in the list.
     *
     * @param data is the value being search for.
     * @return index of the location of first instance of 'data', if
     *         value is not found the -1 is returned.
     */
    public int search(E data) {

        cursor = top;
        int i=0;

        while(cursor != null) {

            if(cursor.getData().equals(data)) {
                return i;
            }
            
            ++i;
            cursor = cursor.getNext();
        } 

        return -1;
    }

    /**
     * Checks if index is valid for the current state of the list.
     *
     * @param index - index value to be checked.
     * @return true if valid, false if invalid.
     */
    public boolean validIndex(int index) {
        
        // Test if index is valid
        if(index >= 0 && index < size) {
            return true;
        }
        else {
            return false;
        }
    }

    public String toStringB() {
        
        String temp = "";
        cursor = top;
        while(cursor.getNext() != null) {
            cursor = cursor.getNext();
        }

        while(cursor != null) {
            temp += cursor.getData();


//            System.out.println(temp);


            cursor = cursor.getPrev();
        }
        return temp;
    }

    /**
     * Get the current size of the list.
     *
     * @return the number of elements in the list.
     */
    public int size() {
        return size;
    }
}
