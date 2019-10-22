package br.com.edb2.arvore;

//import org.jetbrains.annotations.NotNull;

import br.com.edb2.utils.Char;

public class Node {

    private Char value;
    private Node left;
    private Node right;
    private Integer bit;

    public Integer getBit() {
        return bit;
    }

    public void setBit(Integer bit) {
        this.bit = bit;
    }

    public Node() {

        this.value = null;
        this.left = null;
        this.right = null;
    }

    public Node(Integer c, Integer x) {
        this.value = new Char(c, x);
        this.left = null;
        this.right = null;
    }

    public Node(Integer x, Node left, Node right) {
        this.value = new Char(x);
        this.left = left;
        this.right = right;
        this.bit = null;
        this.left.setBit(0);
        this.right.setBit(1);
    }

    public Char getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this.value.getQuantitie() == ((Node) o).getValue().getQuantitie() && this.value.getCharacter() == ((Node) o).getValue().getCharacter()) {
            if (this.left != null && ((Node) o).left != null) {
                if (!this.left.equals(((Node) o).left)) {
                    return false;
                }

            } else if (this.right != null && ((Node) o).right != null) {
                if (!this.right.equals(((Node) o).right)) {
                    return false;
                }

            }
            return true;

        }

        return false;
    }

    public String toString(){
        return "["+this.value.getQuantitie()+"]";
    }
}
