package bsts;

public class RBBST<Key extends Comparable<Key>,Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;
    public
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
    }
    private boolean isRed(Node x){
        if(x==null)
            return false;
        return x.color==RED;
    }
}
