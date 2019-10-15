package br.com.edb2;

//import org.jetbrains.annotations.NotNull;

public class Node{
    private Integer letter;
    private Integer count;

    private Node left;
    private Node right;

    public Node() {
        this.letter = null;
        this.count = null;
        this.left = null;
        this.right = null;
    }

    public Node(Integer letter, Integer count) {
        this.letter = letter;
        this.count = count;
    }

    public Node(Integer count, Node left, Node right) {
        this.count = count;
        this.left = left;
        this.right = right;
    }

    public Integer getLetter() {
        return letter;
    }

    public void setLetter(Integer letter) {
        this.letter = letter;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

}
