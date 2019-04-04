package mixer;

import java.io.Serializable;

public class NodeD<E> implements Serializable {

		
	private static final long serialVersionUID = 0;

	
    public E data;
    

	public NodeD<E> next;
    

	public NodeD<E> prev;

    
	public NodeD() {
        super();
    }

	
	public NodeD(E data) {
		
		this.data = data;
		this.next = null;
		this.prev = null;
	}

	
    public NodeD(E data, NodeD<E> prev, NodeD<E> next) {
        
		this.data = data;
        this.next = next;
        this.prev = prev;
    }

	
    public E getData() {
        return data;
    }

	
    public void setData(E data2) {
        this.data = data2;
    }

	
    public NodeD<E> getNext() {
        return next;
    }

	
    public void setNext(NodeD<E> newNext) {
        this.next = newNext;
    }

	
    public NodeD<E> getPrev() {
        return prev;
    }

	
    public void setPrev(NodeD<E> newPrev) {
        this.prev = newPrev;
    }
}
