import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
public class MaxSubArrarySolution {

    @Test
    public void test(){
        int[] a={13,-3,-25,20,-3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};
        Pair<Integer, Integer> result =findMaxSubSum(a,0,a.length-1);
        StringBuilder ids = new StringBuilder();
        for (int i = result.getLeft(); i <= result.getRight(); i++) {
            ids.append(a[i]+",");
        }
        System.out.println(result);
        System.out.println(ids);
    }

    private Pair<Integer, Integer> findMaxSubSum(int[] arr, int begin, int end) {
        // gap zero and 1 special situation
        if(begin==end)
            return Pair.of(begin,end);
        if(begin+1==end){
            if(arr[begin]>0&&arr[end]>0)
                return Pair.of(begin,end);
            if(arr[begin]>arr[end])
                return Pair.of(begin,begin);
            else
                return Pair.of(end,end);
        }
        // split to left and right,no interference
        Pair<Integer,Integer> left = findMaxSubSum(arr,begin,(begin+end)/2-1);
        Pair<Integer,Integer> right = findMaxSubSum(arr,(begin+end)/2,end);
        // have at least one gaps between children solutions
        if(right.getLeft()>left.getRight()+1){
            // gaps negative either part,means gaps are abandoned
            if(getSum(arr,Pair.of(left.getRight()+1,right.getLeft()-1))+getSum(arr,left)<0||
                    getSum(arr,Pair.of(left.getRight()+1,right.getLeft()-1))+getSum(arr,right)<0){
                if(getSum(arr,right)>getSum(arr,left))
                    return right;
                else
                    return left;
            }
            // gaps positive either part,means gaps should be included  AA
            // left +gap >0 or right + gap >0
        }
        // no gaps between left and right,positive both accumulate,else return bigger one
        if(left.getRight()+1==right.getLeft()){
            if(getSum(arr,left)>0&&getSum(arr,right)>0)
                return Pair.of(left.getLeft(),right.getRight());
            if(getSum(arr,left)>=getSum(arr,right))
                return left;
            if(getSum(arr,left)<getSum(arr,right))
                return right;
        }
        return Pair.of(left.getLeft(),right.getRight()); //AA
    }

    private int getSum(int[] arr, Pair<Integer, Integer> pair) {
        int sum =0;
        for (int i = pair.getLeft(); i <= pair.getRight(); i++) {
            sum += arr[i];
        }
        return sum;
    }
}
