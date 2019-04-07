package mixer;

public class DoubleLinkedList<E>  {
  
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
     * @param index location to get data from.
     * @return data at provided index, if index is out of bouds then
     *         returns null.
	 * @throws ArryOutOfBoundsException - if index input is larger than
	 *		   the size of the list.
     */
    public E get(int index) throws ArrayIndexOutOfBoundsException {
        
		if(index >= size) {
			throw new ArrayIndexOutOfBoundsException();
		}

        cursor = top;
        
        if(index <= size) {
            for (int i=0; i<index; ++i) {
                cursor = cursor.getNext();
            }
            return cursor.getData();
        }

        return null;
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
       
		if(index >= size) {
			throw new ArrayIndexOutOfBoundsException();
		}

		NodeD<E> removed = null;

		// Set postion to start of list
		if(top != null) {
			cursor = top;
		}

		// First node
		if(index == 0) {
			removed = top;
			top = removed.getNext();
			top.setPrev(null);
			--size;
			return removed.getData();
		}

		// Go to node to remove
		for(int i=0; i < index - 1; ++i) {
			cursor = cursor.getNext();
		}
		
		// Last node
		if(cursor.getNext() == null) {
			removed = cursor;
			cursor = cursor.getPrev();
			cursor.setNext(null);
			--size;
			return removed.getData();
		}

		// Middle node
		NodeD<E> newNext = cursor.getNext();
		NodeD<E> newPrev = cursor.getPrev();

		newPrev.setNext(newNext);
		newNext.setPrev(newPrev);

		--size;
		return cursor.getData();
    }

    /**
     * Add a new node at the end of the list.
     *
     * @param newData - the data to go into the new node.
     */
    public void add(E newData) {
       
        NodeD<E> newNode = new NodeD<E>(newData, cursor, null);
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
        ++this.size;
    }

    /**
     * Insert a data value at a index.
     * 
     * @param index - the location to insert data before.
     * @param data - the data to be inserted.
     */
    public void insertAt(int index, E data) throws IndexOutOfBoundsException {
        
        if(index >= size) {
            throw new IndexOutOfBoundsException();
        }
    
        NodeD<E> newNode = new NodeD<E>(data);
        cursor = top;
        ++size;

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
            return;
        }

        // Insert inbetween two nodes
        NodeD<E> newNext = cursor.getNext();
        NodeD<E> newPrev = cursor;

        cursor.setNext(newNode);
        newNode.setPrev(newPrev);
        newNode.setNext(newNext);
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


	public boolean validIndex(int index) {
		
		// Test if index is valid
		if(index >= 0 && index < size) {
			return true;
		}

		return false;
	}

	
	public int size() {
		return size;
	}
}
