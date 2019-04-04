package mixer;

public class NodeCB {

    private int clipBoardNumber;
    private NodeD<Character> topOfClipBoard;
    private NodeCB next;

    public NodeCB() {
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
}
