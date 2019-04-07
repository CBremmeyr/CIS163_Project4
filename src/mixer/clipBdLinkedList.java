package mixer;

public class clipBdLinkedList {

    private NodeCB top;
    private NodeCB tail;
	private int size;

    public clipBdLinkedList() {
        tail = null;
		top = null;
		size = 0;
    }

    // create methods you need.




	public boolean validIndex(int index) {

		if(index >= 0 && index < size) {
			return true;
		}

		return false;
	}
}
