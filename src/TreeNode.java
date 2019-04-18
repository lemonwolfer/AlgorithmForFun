import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * author:lichao
 * date:2019/4/18
 * description:
 **/
public class TreeNode<T extends Comparable> implements Comparable<TreeNode<? extends Comparable>>{
    T data;
    TreeNode<T> left;
    TreeNode<T> right;

    public void addNode(TreeNode<T> node) {

        if (data.compareTo(node.data) > 0) {
            if(Objects.isNull(this.left))
                this.left = node;
            else
                this.left.addNode(node);
        } else {
            if(Objects.isNull(this.right))
                this.right = node;
            else
                this.right.addNode(node);
        }
    }
    @Override
    public int compareTo(TreeNode<? extends Comparable> other) {

        return this.data.compareTo(other.data);
    }
    public static void  abc(){
        System.out.println("aaaaa");
    }
    @Test
    public void test(){
        abc();
    }

}

