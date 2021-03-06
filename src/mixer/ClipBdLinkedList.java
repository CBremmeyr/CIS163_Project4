package mixer;

/**
 * single linked list for storing clip boards
 * @param <T> data being stored in single linked list
 */
public class ClipBdLinkedList<T> {

    /** Top node in single linked list*/
    private NodeCB<T> top;

    /** Botton node in single linked list*/
    private NodeCB<T> tail;

    /** size of linked list*/
    private int size;

    /**clip board key for where it should be stored in hash*/
    private int clipNum;

    /**
     * Constructor for linked list
     */
    public ClipBdLinkedList() {
        tail = null;
        top = null;
        size = 0;
    }

    /**
     * returns the length of the linked list
     * @return length of list
     */
    public int getLen() {
        return this.size;
    }

    /**
     * Inserts data before the index provided
     * @param data data to be inserted
     */
    public void insertBefore( T data) {

        NodeCB<T> temp;
        NodeCB<T> newNode = new NodeCB<>();
        newNode.setData(data);

        if (size == 0) {
            top = newNode;
        }
        if(size == 1 && getLen() <= 1){
            newNode.setNext(top);
            top = newNode;
        }

        else {

            temp = top;
            for (int i = 0; i < getLen()-1; i++) {
                temp = temp.getNext();
            }

            NodeCB<T> oldNext=temp.getNext();
            temp.setNext(newNode);
            newNode.setNext(oldNext);
        }

        size++;
        setTail();
    }

    /**
     * Inserts data after index provided
     * @param index area to insert after
     * @param data data to be inserted
     */
    public void insertAfter(int index, T data) {
        NodeCB<T> temp;
        NodeCB<T> newNode = new NodeCB<>();
        newNode.setData(data);

        if (size == 0) {
            top = newNode;
        }
        else if(size == 1 && index ==0){
            newNode.setNext(top);
            top = newNode;
        }
        else {

            temp = top;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            NodeCB<T> oldNext=temp.getNext();
            temp.setNext(newNode);
            newNode.setNext(oldNext);
        }

        size++;
        setTail();
    }

    /**
     * sets the bottom object on list
     */
    public void setTail() {

        // Move to end of list
        NodeCB<T> temp = this.top;
        while(true) {

            if(temp.getNext() != null) {
                temp = temp.getNext();
            }
            else {
                break;
            }
        }

        this.tail = temp;
    }

    /**
     * deletes the object at the index provided
     * @param index index of where to delete from
     * @return if its possible to delete at
     * @throws IllegalArgumentException
     */
    public boolean delAt(int index) throws IllegalArgumentException {

        if(index >= this.size || index < 0) {
            throw new IllegalArgumentException();
        }

        NodeCB<T> temp = this.top;

        // Move to node before the one being removed
        for(int i=0; i < index-1; ++i) {

            if(temp != null) {
                temp = temp.getNext();
            }
            else {
                return false;
            }
        }

        NodeCB<T> toRemove = temp.getNext();
        temp.setNext(toRemove.getNext());

        --this.size;
        setTail();
        return true;
    }

    /**
     * determines if the index provided was a valid index
     * @param index index given to do operation at
     * @return validity of index
     */
    public boolean validIndex(int index) {

        if(index >= 0 && index < size) {
            return true;
        }

        return false;
    }

    /**
     * prints the entire list foward
     * @return returns the string of the list in order
     */
    @Override
    public String toString() {

        // Show the linked list
        String string = "";
        NodeCB<T> currentNode = this.top;
        while (currentNode != null) {
            string += currentNode.getData();
            currentNode = currentNode.getNext();
        }
        // Return the String representation that shows the linked list
        return string;
    }
}
