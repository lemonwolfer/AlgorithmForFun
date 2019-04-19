import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;


public class MaxSubArrarySolution {
    int[] a={13,-3,-25,20,-3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};

    @Test
    public void testByDivideAndConquer(){
        Pair<Integer, Integer> result =findMaxSubSum(a,0,a.length-1);
        StringBuilder ids = new StringBuilder();
        for (int i = result.getLeft(); i <= result.getRight(); i++) {
            ids.append(a[i]+",");
        }
        System.out.println(result);
        System.out.println(ids);

    }
    @Test
    public void testByPositionSolution(){
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
    @Test
    public void testByDP(){
        Triple<?,?,?> result = null;
        for (int i = 0; i <a.length-1 ; i++) {
            result= findByDP(a,0,i);
        }
        System.out.println(result);
    }
    @Test
    public void testSimpleDP(){
        System.out.println(findBySimpleDP(a));
    }
    @Test
    public void testTestBook(){
        int sum = findByTextBook(a, 0, a.length - 1);
        System.out.println(sum);
    }
    private int findByTextBook(int[] a,int low,int high){
        if (low == high) {
            return a[low];
        }
        int mid = (low+high)/2;
        int left = findByTextBook(a,low,mid);
        int right = findByTextBook(a,mid+1,high);
        Triple<Integer,Integer,Integer> cross = findCross(a,low,mid,high);
        return Math.max(Math.max(left,right),cross.getRight());
    }

    private Triple<Integer, Integer, Integer> findCross(int[] a, int low, int mid, int high) {
        int leftsum =a[mid];
        int sosum = 0;
        int min_right = mid;
        for (int i = mid; i >= low&&mid>=low; i--) {
            sosum+=a[i];
            if(sosum>leftsum){
                leftsum =sosum;
                min_right = i;
            }
        }
        int rightsum =a[mid+1];
        sosum = 0;
        int max_right = mid+1;
        for (int ii = mid+1; ii <=high&&high>mid+1; ii++) {
            sosum+=a[ii];
            if(sosum>rightsum){
                rightsum =sosum;
                max_right = ii;
            }
        }
        return Triple.of(min_right,max_right,leftsum+rightsum);
    }

    private int findBySimpleDP(int[] a) {
        int[] dp = new int[a.length];
        dp[0] = a[0];
        int maxtotal = dp[0];
        for (int i = 1; i < a.length; i++) {
            dp[i] = a[i]+Math.max(dp[i-1],0);
            maxtotal = Math.max(maxtotal,dp[i]);
        }
        return maxtotal;
    }

    private Triple<Integer, Integer,Integer> findByDP(int[] arr, int bi, int i){
        if(i==0)
            return Triple.of(0,0,arr[0]);
        if(bi==i)
            return Triple.of(i,i,arr[i]);
        Triple<Integer,Integer,Integer> previous = findByDP(arr, bi, i-1);
        int previousSum = previous.getRight();
        int previousStartI= previous.getLeft();
        int previousEndI = previous.getMiddle();
        if(previousSum>0){
            if(arr[i]<=0)
                return previous;
            if(previousEndI+1==i){
                return Triple.of( previousStartI, i, previousSum+arr[i]);
            }
            else {
                Triple<Integer, Integer,Integer> gap = findByDP(arr, previousEndI+1,i-1 );

                if((previousSum+gap.getRight()>0)&&(arr[i]+gap.getRight()>0)&&
                        gap.getLeft()==previousEndI+1 &&
                        gap.getMiddle()==i-1)
                    return Triple.of(previousStartI, i, previousSum + gap.getRight() + arr[i]);
                if(gap.getMiddle()+1==i)
                {
                    if(gap.getRight()+arr[i]>=previousSum)
                        return Triple.of(gap.getLeft(),i,gap.getRight()+arr[i]);
                    return previous;
                }
                if(arr[i]>=previousSum)
                    return Triple.of(i,i,arr[i]);
                else
                    return previous;
            }
        }else
            return Triple.of(i,i,arr[i]);
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
            if (getSum(arr, Pair.of(left.getRight() + 1, right.getLeft() - 1)) + getSum(arr, left) > 0 &&
                    getSum(arr, Pair.of(left.getRight() + 1, right.getLeft() - 1)) + getSum(arr, right) > 0) {
                return Pair.of(left.getLeft(),right.getRight());
            }
            if (getSum(arr, right) > getSum(arr, left))
                return right;
            else
                return left;
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
        return Pair.of(left.getLeft(),right.getRight());
    }

    private int getSum(int[] arr, Pair<Integer, Integer> pair) {
        int sum =0;
        for (int i = pair.getLeft(); i <= pair.getRight(); i++) {
            sum += arr[i];
        }
        return sum;
    }
}
