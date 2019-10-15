package br.com.edb2;

import java.util.Arrays;

public class Heap {
    private Node[] nodes;
    private int size;//quantos elementos tem
    private int capacity;//quantos elementos cabem

    public Heap() {
        this(10);
    }

    public Heap(int capacity) {
        nodes = new Node[capacity];
        this.size = 0;
        this.capacity = capacity;
    }


    public boolean isLeaf(Node node){
        if(node.getLeft() == null && node.getRight() == null){
            return true;
        }else
        {
            return false;
        }
    }

    public void insert(Integer letter, Integer count) {
        insert(new Node(letter, count));
    }

    public void insert(Node node) {
        ensureCapacity();
        nodes[getSize()] = node;
        heapifyUp(getSize());
        size++;
    }

    private void heapifyUp(int index) {
        int parentIndex = getParentIndex(index);

        if (parentIndex < 0) {
            return;
        }

        Node pai    = nodes[parentIndex];
        Node pessoa = nodes[index];

        if (pessoa.getCount() < pai.getCount()) {
            nodes[index]   = pai;
            nodes[parentIndex] = pessoa;
            heapifyUp(parentIndex);
        }
    }

    public int getParentIndex(int index) {
        return (int) Math.floor((index - 1) / 2);
    }

    private void ensureCapacity() {
        if (size == capacity) {
            nodes = Arrays.copyOf(nodes, capacity * 2);
            capacity = capacity * 2;
        }
    }

    public int getSize() {
        return size;
    }

    public Node peek() {
        if (getSize() == 0) {
            return null;
        }
        return nodes[0];
    }

    public Node peekRemove(){
        Node aux = this.peek();
        this.remove();
        return aux;
    }

    public void remove() {
        nodes[0] = nodes[getSize() - 1];
        nodes[getSize() - 1] = null;
        size--;
        heapifyDown(0);
    }

    private void heapifyDown(int index) {
        int leftChild = index * 2 + 1;
        int rightChild = index * 2 + 2;

        int childIndex = -1;
        if (leftChild < getSize()) {
            childIndex = leftChild;
        }

        if (childIndex == -1) {
            return;
        }

        if (rightChild < getSize()) {
            if (nodes[rightChild].getCount() < nodes[leftChild].getCount()) {
                childIndex = rightChild;
            }
        }

        if (nodes[index].getCount() > nodes[childIndex].getCount()) {
            Node tmp          = nodes[index];
            nodes[index]      = nodes[childIndex];
            nodes[childIndex] = tmp;
            heapifyDown(childIndex);
        }
    }
}
