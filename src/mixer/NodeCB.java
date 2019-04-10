package mixer;

public class NodeCB<T> {

    /** Refrence to next node in list */
    private NodeCB<T> next;

    /** Refrence to data that node is holding */
    private T data;

    /**
     * Defualt constructor that sets all refrence values to null.
     */
    public NodeCB() {
        this.data = null;
        this.next = null;
    }

    /**
     * A constructor that sets the element of this Node to the
     * specified element, and next
     *
     * @param data that represents the element to set for this Node
     */
    public NodeCB(T data) {

        this.data = data;
        this.next = null;
    }

    /**
     * Set refrence to next node in list.
     *
     * @param next - refrence to new next node.
     */
    public void setNext(NodeCB<T> next) {
        this.next = next;
    }

    /**
     * A getter for the next Node in single linked list
     * @return NodeCB<T> that represents the next Node in single
     *         linked list
     */
    public NodeCB<T> getNext() {
        return this.next;
    }

    /**
     * Get the data that the node has a refrence to.
     *
     * @return data being refrenced by this node.
     */
    public T getData() {
        return this.data;
    }

    /**
     * Set the data refrence in this node.
     *
     * @param data - new data refrence to be set.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * A method to return a String representation of this Node
     * @return String that represents the element in this Node
     */
    public String toString() {
        return this.data.toString();
    }
}
