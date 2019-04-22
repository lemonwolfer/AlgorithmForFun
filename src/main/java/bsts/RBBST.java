package bsts;

import lombok.Data;

public class RBBST<Key extends Comparable<Key>,Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root ;

    public RBBST() {
        this.root = null;
    }

    public Node put(Key key,Value value){
        Node root = doPut(this.root, key, value);
        return root;
    }

    private Node doPut(Node entryNode, Key key, Value value) {
        Node newNode = new Node(key,value,0,RED);
        if (entryNode == null){
            newNode.setColor(BLACK);
            return newNode;
        }
        if(key.compareTo(entryNode.key)<0)
            entryNode.left = doPut(entryNode.left,key,value);
        else if(key.compareTo(entryNode.key)>0)
            entryNode.right = doPut(entryNode.right,key,value);
        else
            entryNode.value = value;
    }
    @Data
    private class Node{
        private Key key;
        private Value value;
        private Node left,right;
        private int N;
        boolean color;

        private Node(Key key, Value value, int n) {
            this.key = key;
            this.value = value;
            N = n;
        }
        private Node(Key key, Value value, int n,boolean color) {
            this(key,value,n);
            this.color = color;
        }
    }
    private boolean isRed(Node x){
        if(x==null)
            return false;
        return x.color==RED;
    }
}
