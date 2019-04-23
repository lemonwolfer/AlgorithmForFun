package bsts;

import lombok.Data;
import org.apache.commons.lang3.tuple.Triple;

public class RBBST<Key extends Comparable<Key>,Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root ;

    public RBBST() {
        this.root = null;
    }
    public void TravelTree(){
        travesal(root);
    }

    private void travesal(Node node) {
        if(node==null)
            return;
        travesal(node.left);
        System.out.println("["+node.key+":"+node.color+"]");
        travesal(node.right);
    }

    public void put(Key key,Value value){
        doPut(this.root, key, value);
    }

    private void doPut(Node entryNode, Key key, Value value) {
        Node newNode = new Node(key,value,0,RED);
        Node p = null;
        boolean isLeft=true;
        while (entryNode!=null){
            p = entryNode;
            if(key.compareTo(entryNode.key)<0)
                entryNode = entryNode.left;
            else if(key.compareTo(entryNode.key)>0){
                isLeft = false;
                entryNode = entryNode.right;
            }
            else{
                entryNode.value = value;
                return;
            }
        }
        newNode.parent = p;
        if(isLeft)
            p.setLeft(newNode);
        else
            p.setRight(newNode);
        ReBalance(p);
    }

    private void ReBalance(Node p) {
        if(p.parent==null){
            p.color = BLACK;
            return;
        }

        if(p.right.color==RED&&(p.left==null||(p.left!=null&&p.left.color!=RED))){
            leftTurn(p);
        }
        if(p.left.color==RED&&p.parent.color==RED){
            topTurn(p);
        }
        if (p.left.color==RED&&p.right.color==RED){
            flipColor(p);
        }
        ReBalance(p.parent);
    }

    /**
     * p的左右都是红色 ，p不可能是红色
     * @param p
     */
    private void flipColor(Node p) {
        p.color = RED;
        p.left.color = BLACK;
        p.right.color = BLACK;
    }

    /**
     * p和p.left都是红色 转为三角形 上红下黑
     * @param p
     */
    private void topTurn(Node p) {
        Node left = p.left;
        Node father = p.parent;
        p.right = father;
        father.parent = p;
        p.color = RED;
        father.color = BLACK;
        left.color = BLACK;
    }

    /**
     * p和p.right都是红色
     * @param p
     */
    private void leftTurn(Node p) {
        Node right = p.right;
        right.left = p;
        p.right = right.left;
        p.parent = right;
    }

    @Data
    private class Node{
        private Key key;
        private Value value;
        private Node left,right,parent;
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
