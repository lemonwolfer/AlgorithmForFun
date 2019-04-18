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
        boolean[] possibilities =  new boolean[]{true, true, true, true, true, true, true, true,true, true, true, true,true, true, true};
        int[] starts = new int[a.length];
        for (int i=a.length-1;i>=0;i--){
            int startI = findByEndPosition(a, i);
            starts[i] = startI;
            if(i>0){
                int startPreviousI = findByEndPosition(a,i-1);
                if(startI==startPreviousI){
                    if(a[i]>0)
                        possibilities[i - 1] = false;
                    else
                        possibilities[i] =false;
                    System.out.println("cover previous ,endI: "+i+",startI: "+startI);
                }
                if(i==startI){
                    possibilities[i-1] =false;
                    System.out.println("drop previous,endI: "+i+",startI: "+startI);
                }
            }
        }
        int biggest = 0;
        int startI=0;
        int foundEnd=0;
        for (int i = 0; i <possibilities.length ; i++) {
            if(possibilities[i]){
                startI = findByEndPosition(a, i);

                int total = getSum(a,Pair.of(startI,i));
                if(total>biggest){
                    biggest = total;
                    foundEnd = i;
                }

            }
        }
        System.out.println("biggest:"+biggest+"["+startI+","+foundEnd+"]");
    }
    private int findByEndPosition(int[] arr,int endI){
        if(endI==0)
            return endI;
        if(endI==1)
            return arr[0]>0?0:1;
        int startBypreviousEndI =findByEndPosition(arr,endI-1);
        return getSum(arr,Pair.of(startBypreviousEndI,endI-1))>0?startBypreviousEndI:endI;
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
