package br.com.edb2;

//import org.jetbrains.annotations.NotNull;

public class Node{
    private Character letter;
    private Integer count;

    private Node left;
    private Node right;

    public Node() {
        this.letter = null;
        this.count = null;
        this.left = null;
        this.right = null;
    }

    public Node(Object letter, Object count) {
        this.letter = (Character)letter;
        this.count = (Integer) count;
    }

    public Node(Integer count, Node left, Node right) {
        this.count = count;
        this.left = left;
        this.right = right;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
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
