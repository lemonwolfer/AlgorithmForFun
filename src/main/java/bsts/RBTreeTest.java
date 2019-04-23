package bsts;

import org.junit.jupiter.api.Test;

/**
 * author:lichao
 * date:2019/4/23
 * description:
 **/
public class RBTreeTest {
    @Test
    public void testInsertion(){
        RBBST<Integer,String> rbbst = new RBBST<>();
        rbbst.put(35,"a");
        rbbst.put(18,"a");
        rbbst.put(69,"a");
        rbbst.put(9,"a");
        rbbst.put(21,"a");
        rbbst.put(60,"a");
        rbbst.put(90,"a");
        rbbst.put(4,"a");
        rbbst.put(30,"a");
        rbbst.put(45,"a");
        rbbst.put(64,"a");
        rbbst.put(85,"a");
        rbbst.put(96,"a");
        rbbst.put(50,"a");
        rbbst.TravelTree();
        rbbst.TravelPyramid();
    }
}
    
