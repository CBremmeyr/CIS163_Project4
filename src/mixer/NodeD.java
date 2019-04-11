package mixer;

import java.io.Serializable;

/**
 * This is a node for a double linked list
 * @param <E> data type of class called
 */

public class NodeD<E> implements Serializable {

    /** required by serializable interface*/
	private static final long serialVersionUID = 0;

    /** data held by the node*/
    public E data;

    /**reference to the next node in the list*/
	public NodeD<E> next;

    /** reference to the previous node in the list*/
	public NodeD<E> prev;

    /**
     * Constructor for NodeD  with no input arguments
     * @param data data for linked list
     */
	public NodeD(E data) {
		
		this.data = data;
		this.next = null;
		this.prev = null;
	}

    /**
     * Constructor for NodeD with inputs
     * @param data data for the linked list
     * @param prev the next object in linked list
     * @param next the previous object in linked list
     */
    public NodeD(E data, NodeD<E> prev, NodeD<E> next) {
        
		this.data = data;
        this.next = next;
        this.prev = prev;
    }

    /**
     * Gets the data for the linked list
     * @return the linked list data
     */
    public E getData() {
        return data;
    }

    /**
     * sets data into the linked list
     * @param data2 data to be set into linked list
     */
    public void setData(E data2) {
        this.data = data2;
    }

    /**
     * gets the next object in the linked list
     * @return the next object in the linked list
     */
    public NodeD<E> getNext() {
        return next;
    }

    /**
     * sets the next object in the linked list
     * @param newNext the object to be set into the linked list
     */
    public void setNext(NodeD<E> newNext) {
        this.next = newNext;
    }

    /**
     * gets the previous object in the linked list
     * @return the previous object in the linked list
     */
    public NodeD<E> getPrev() {
        return prev;
    }

    /**
     * sets a previous obeject in the linked list
     * @param newPrev sets a new previous object to the linked list
     */
    public void setPrev(NodeD<E> newPrev) {
        this.prev = newPrev;
    }
}
