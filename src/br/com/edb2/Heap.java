package br.com.edb2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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


    public void insert(Node node) {
        ensureCapacity();
        nodes[getSize()] = node;
        heapifyUp(getSize());
        size++;
    }

    public void insert(HashMap<Character, Integer> hashmap) {
        Iterator it = hashmap.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry mapElement = (Map.Entry)it.next();
            this.insert(new Node(mapElement.getKey(), mapElement.getValue() ));
        }
    }
    private void ensureCapacity() {
        if (this.size == capacity) {
            this.nodes = Arrays.copyOf(this.nodes, this.capacity * 2);
            this.capacity = this.capacity * 2;
        }
    }

    public int getParentIndex(int index) {
        return (int) Math.floor((index - 1) / 2);
    }

    private void heapifyUp(int index) {
        int parentIndex = getParentIndex(index);

        if (parentIndex < 0) {
            return;
        }

        Node pai    = this.nodes[parentIndex];
        Node pessoa = this.nodes[index];

        if (pessoa.getCount() < pai.getCount()) {
            this.nodes[index]   = pai;
            this.nodes[parentIndex] = pessoa;
            heapifyUp(parentIndex);
        }
    }



    public int getSize() {
        return size;
    }

    public Node peek() {
        if (getSize() == 0) {
            return null;
        }
        return this.nodes[0];
    }

    public Node peekRemove(){
        Node aux = this.peek();
        this.remove();
        return aux;
    }
    public Node poll() {
        if (getSize() == 0) {
            return null;
        }
        Node n = nodes[0];
        this.remove();
        return n;
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
            if (this.nodes[rightChild].getCount() < this.nodes[leftChild].getCount()) {
                childIndex = rightChild;
            }
        }

        if (this.nodes[index].getCount() > this.nodes[childIndex].getCount()) {
            Node tmp          = this.nodes[index];
            this.nodes[index]      = this.nodes[childIndex];
            this.nodes[childIndex] = tmp;
            heapifyDown(childIndex);
        }
    }

    public Node criaArvore(){
        while(this.getSize() > 1){
            Node left = poll();
            Node rigth = poll();
            insert(new Node ((left.getCount() + rigth.getCount()), left, rigth));
        }
        return poll();
    }
}
