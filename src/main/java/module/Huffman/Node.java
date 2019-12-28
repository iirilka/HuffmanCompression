package module.Huffman;

public class Node {

    private char letter;
    private int frequency;
    private Node leftChild = null;
    private Node rightChild = null;

    public Node(char letter, int frequency) {
        this.letter = letter;
        this.frequency = frequency;
    }

    public Node(char letter, int frequency, Node leftChild, Node rightChild) {
        this.letter = letter;
        this.frequency = frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public char getLetter() {
        return letter;
    }

    public int getFrequency() {
        return frequency;
    }

    public boolean isLeaf() {
        return (rightChild == null) && (leftChild == null);
    }
}
