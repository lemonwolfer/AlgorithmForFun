package bsts;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

public class RBBST<Key extends Comparable<Key>,Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root ;

    public RBBST() {
        this.root = null;
    }
    public List<List<Key>> TravelTopToDown(Node root){
        Map<Integer,List<Node>> levelMap = new HashMap<>();
        processNode(root, levelMap, 1);
        List<List<Node>> result=levelMap.keySet().stream().sorted(Comparator.comparing(x->x)).map(k->levelMap.get(k))
                .collect(Collectors.toList());
        List<List<Key>> keyList = result.stream().map(l->l.stream().map(sl->sl.key).collect(Collectors.toList())).collect(Collectors.toList());
        return keyList;
    }

    private void processNode(Node node, Map<Integer, List<Node>> levelMap, int level) {
        if(node==null)
            return;
        levelMap.computeIfAbsent(level, k -> Lists.newArrayList()).add(node);
        processNode(node.left,levelMap,level+1);
        processNode(node.right,levelMap,level+1);
    }

    public void TravelTree(){
        System.out.println("TravelTree from smaller key to bigger");
        travesal(root);
    }
    public void TravelPyramid(){
        System.out.println("TravelTree from top");
        List<List<Key>> result=TravelTopToDown(root);
        List<Key> f =result.stream().flatMap(listContainer ->listContainer.stream()).collect(Collectors.toList());
        f.stream().forEach(x->System.out.println(x));
    }

    public List<List<Key>> levelOrderBottom(Node root) {
        List<List<Key>> list = new ArrayList<>();
        if(root == null)
            return list;
        //层次遍历
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(true){
            Queue<Node> temp = new LinkedList<>();
            List<Key> tempList = new ArrayList<>();
            while(!queue.isEmpty()){
                Node t = queue.poll();
                tempList.add(t.key);
                if(t.left!=null)
                    temp.add(t.left);
                if(t.right!=null)
                    temp.add(t.right);
            }
            list.add(tempList);//自底向上输出
            if(temp.isEmpty())
                break;
            queue = temp;
        }
        return list;
    }

    private void travesal(Node node) {
        if(node==null)
            return;
        travesal(node.left);
        System.out.println("["+node.key+":"+(node.color?"R":"B")+"]");
        travesal(node.right);
    }

    public void put(Key key,Value value){
        root= doPut(this.root, key, value);
    }

    private Node doPut(Node entryNode, Key key, Value value) {
        Node newNode = new Node(key,value,0,RED);
        Node Oldroot = entryNode;
        Node p = null;
        boolean isLeft=true;
        while (entryNode!=null){
            p = entryNode;
            if(key.compareTo(entryNode.key)<0){
                entryNode = entryNode.left;
                isLeft = true;
            }

            else if(key.compareTo(entryNode.key)>0){
                isLeft = false;
                entryNode = entryNode.right;
            }
            else{
                entryNode.value = value;
                return Oldroot;
            }
        }
        newNode.parent = p;
        if(p==null){
            root = newNode;
            root.color = BLACK;
            return root;
        }

        if(isLeft)
            p.left=newNode;
        else
            p.right=newNode;
        ReBalance(p);
        return Oldroot;
    }

    private void ReBalance(Node p) {
        if(p==null)
            return;

        if(isStraightLine(p))
            topTurn(p);
        if(isRed(p)&&isRed(p.right)&&!isRed(p.left)){
            leftTurn(p);
        }

        if (isRed(p.left)&&isRed(p.right)){
            flipColor(p);
        }
        if(p.parent==null){
            p.color = BLACK;
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
        Node right = p.right;
        Node left = p.left;
        if(p.parent.left==p){
            p.parent = father.parent;
            p.right = father;
            p.right.left = right;
            father.parent = p;
            left.color = BLACK;
            left.parent = p;
        }
        else if(p.parent.right==p){

            p.parent = father.parent;
            p.left = father;
            p.left.right = left;
            father.parent = p;
            right.color = BLACK;
            right.parent = p;

        }

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
