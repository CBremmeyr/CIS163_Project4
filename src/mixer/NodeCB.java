package mixer;

public class NodeCB<T> {

    private int clipBoardNumber;
    private NodeD<Character> topOfClipBoard;
    private NodeCB next;

    /** The element that this Node holds */
    private T element;

    private T data;



    /** A to the next Node in the singly linked list */
    private NodeCB<T> previous;

    public NodeCB() {
        this.element = null;
        this.next = null;
        this.previous = null;

    }
    /**
     * A constructor that sets the element of this Node to the
     * specified element, and next
     * @param element that represents the element to set for this Node
     */
    public NodeCB(T element) {
        this.element = element;
        this.next = null;
        this.previous = null;
    }

    public int getClipBoardNumber() {
        return clipBoardNumber;
    }

    public void setClipBoardNumber(int clipBoardNumber) {
        this.clipBoardNumber = clipBoardNumber;
    }

    public NodeD<Character> getTopOfClipBoard() {
        return topOfClipBoard;
    }

    public void setTopOfClipBoard(NodeD<Character> topOfClipBoard) {
        this.topOfClipBoard = topOfClipBoard;
    }
    public void setNext(NodeCB<T> next) {
        this.next = next;
    }
    public void setData(T data) {
        this.data = data;
    }
    /**
     * A getter for the element of this Node
     * @return T that represents the element of this Node
     */
    public T getElement() {
        return this.element;
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
     * A setter for the previous Node in single linked list
     * @param previous that represents the previous Node in single
     *         linked list
     */
    public void setPrevious(NodeCB<T> previous) {
        this.previous = previous;
    }

    /**
     * A getter for the previous Node in single linked list
     * @return NodeCB<T> that represents the previous Node in the
     single linked list
     */
    public NodeCB<T> getPrevious() {
        return this.previous;
    }

    /**
     * A method to return a String representation of this Node
     * @return String that represents the element in this Node
     */
    public String toString() {
        return this.element.toString();
    }
}