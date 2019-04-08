package mixer;

  
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
     */
    public E deleteAt(int index) throws ArrayIndexOutOfBoundsException {

        cursor = top;

        // Removing top
        if(index == 0) {

            // If removing only node in list
            if(size == 1) {
                top = null;
                size = 0;
                --size;
                return cursor.getData();
            }

            top = top.getNext();
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

            // Delete node that cursor is on
            NodeD<E> newNext = cursor.getNext();
            NodeD<E> newPrev = cursor.getPrev();

            newPrev.setNext(newNext);
            newNext.setPrev(newPrev);

//            cursor.getPrev().setNext(cursor.getNext());
//            cursor.getNext().setPrev(cursor.getPrev());
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
        
        if(!validIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    
        NodeD<E> newNode = new NodeD<E>(data);
        cursor = top;

        // Instering at head of list 
        if(index == 0) {
            
            top = newNode;

            if(cursor != null) {
                newNode.setNext(cursor);
                cursor.setPrev(newNode);
            }
            else {
                newNode.setNext(null);
                newNode.setPrev(null);
            }

            ++size;
            return;
        }

        // Move to node previous to insertion point
        for(int i=0; i < index-1; ++i) {
            if(cursor.getNext() != null) {
                cursor = cursor.getNext();
            }
        }

        // If inserting at end of the list
        if(cursor.getNext() == null) {
            
            cursor.setNext(newNode);
            newNode.setNext(null);
            newNode.setPrev(cursor);
            ++size;
            return;
        }

        System.out.println("-------\nindex: "+index);
        System.out.println(cursor.getData());
        System.out.println(cursor.getNext().getPrev().getData());

        // Insert inbetween two nodes
        NodeD<E> newNext = cursor.getNext();
        NodeD<E> newPrev = cursor;

        cursor.setNext(newNode);
        newNode.setPrev(newPrev);
        newNode.setNext(newNext); 
        ++size;
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

    /**
     * Get the current size of the list.
     *
     * @return the number of elements in the list.
     */
    public int size() {
        return size;
    }
}
