package mixer;

public class DoubleLinkedList<E>  {
   
    protected NodeD<E> top;

    // This instance variable is not required, however if you
    // find it useful, then you are welcome to use it
    protected NodeD<E> cursor;  // The current NodeD<E> in the list

    public DoubleLinkedList() {
        top = null;
        cursor = null;
    }

    public E get(int position) {
        
        cursor = top;

        for (int i = 0; i < position; i++) {
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
    public E deleteAt(int index) {
        
    }

	/**
	 * Add a new node at the end of the list.
	 *
	 * @param newData is the data to go into the new node.
	 */
    public void add(E newData) {
        
    }

    /**
     * Insert a data value at a index.
     * 
     * @param index is the location to insert data at.
     * @param data is the data to be inserted.
     */
    public void insertAt(int index, E data) {
        
    }

    /**
     * Search for an data element in the list.
     *
     * @param data is the value being search for.
     * @return index of the location of first instance of 'data'.
     */
    public int search(E data) {
        
    }
}
