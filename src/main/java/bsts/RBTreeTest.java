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
        rbbst.put(1,"a");
        rbbst.put(2,"a");
        rbbst.put(3,"a");
        rbbst.TravelTree();
        rbbst.TravelPyramid();
    }
}
    
