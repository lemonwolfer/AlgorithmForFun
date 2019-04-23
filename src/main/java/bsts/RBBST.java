package bsts;

import lombok.Data;

public class RBBST<Key extends Comparable<Key>,Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root ;

    public RBBST() {
        this.root = null;
    }

    public void TravelTree(){
        System.out.println("TravelTree from smaller key to bigger");
        travesal(root);
    }
    public void TravelPyramid(){
        System.out.println("TravelTree from top");
        travesalPyramid(root);
    }

    private void travesalPyramid(Node node) {
        if(node==null)
            return;
        System.out.println("["+node.key+":"+(node.color?"R":"B")+"]");
        travesal(node.left);
        travesal(node.right);
    }

    private void travesal(Node node) {
        if(node==null)
            return;
        travesal(node.left);
        System.out.println("["+node.key+":"+(node.color?"R":"B")+"]");
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
        if(p==null){
            root = newNode;
            return;
        }

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
        if(isStraightLine(p))
            topTurn(p);
        if(isRed(p.right)&&!isRed(p.left)){
            leftTurn(p);
        }

        if (isRed(p.left)&&isRed(p.right)){
            flipColor(p);
        }
        ReBalance(p.parent);
    }

    private boolean isStraightLine(Node p) {
        boolean leftSleap = isRed(p.left)&&isRed(p)&&p.parent.left==p;
        boolean rightSleap =isRed(p.right)&&isRed(p)&&p.parent.right==p;
        return leftSleap||rightSleap;
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
        Node father = p.parent;
        if(p.parent.left==p){
            Node left = p.left;
            p.right = father;
            p.left = left;
            father.parent = p;
            left.color = BLACK;
            left.parent = p;
        }
        else if(p.parent.right==p){
            Node right = p.right;
            p.left = father;
            p.right = right;
            father.parent = p;
            right.color = BLACK;
            right.parent = p;

        }
        p.parent = father.parent;
        p.color = RED;
        father.color = BLACK;


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
