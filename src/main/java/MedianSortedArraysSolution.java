import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

public class MedianSortedArraysSolution {

    @Test
    public void testFindKth(){
        int[] a=new int[]{1,3,4};
        int[] b= new int[]{2,7,8};
        int result = findMedianSortedArrays(a,b,5);
        System.out.println(result);
    }
    public int findMedianSortedArrays(int[] a,int[] b,int k){
        Pair<Integer, Integer> pair = doFindCandidatePair(a, b, k);
        return Math.max(a[pair.getKey()],b[pair.getValue()]);
    }

    public Pair<Integer,Integer> doFindCandidatePair(int[] a,int[] b,int k){
        if(k==1||k==2)
            return Pair.of(0,0);
        Pair<Integer,Integer> previous = doFindCandidatePair(a,b,k-1);
        int previousI = previous.getLeft();
        if(b[previous.getRight()]<a[previous.getLeft()])
            previousI = previous.getRight();
        if(previousI==previous.getLeft()){
            return Pair.of(previous.getLeft()+1,previous.getRight());
        }else {
            return Pair.of(previous.getLeft(),previous.getRight()+1);
        }
    }
}
