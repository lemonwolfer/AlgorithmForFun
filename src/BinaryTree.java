import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * author:lichao
 * date:2019/4/18
 * description:
 **/
public class BinaryTree {
    TreeNode<Integer> root ;
    public  void addNode(Integer data){
        TreeNode<Integer> newNode = new TreeNode<>();
        newNode.data = data;
        if(Objects.isNull(root)){
            root = newNode;
        }else
            root.addNode(newNode);
    }
    public  void printNodesInOrder(){
        travelNodes(root);
    }
    public static void travelNodes(TreeNode<Integer> node){
        if(Objects.nonNull(node.left))
            travelNodes(node.left);
        System.out.println(node.data);
        if(Objects.nonNull(node.right))
            travelNodes(node.right);
    }
    @Test
    public  void testBuildTree() {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.addNode(2);
        binaryTree.addNode(1);
        binaryTree.addNode(7);
        binaryTree.addNode(4);
        binaryTree.addNode(6);
        binaryTree.addNode(3);
        binaryTree.addNode(8);
        binaryTree.printNodesInOrder();
    }
}
