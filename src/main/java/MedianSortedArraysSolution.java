import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

public class MedianSortedArraysSolution {

    @Test
    public void testFindKth(){
        int[] a=new int[]{1,3,4,6,11,34,45,56,78};
        int[] b= new int[]{2,7,8,9,12,47,59,89,100};
        int result = findMedianSortedArrays(a,b,12);
        System.out.println(result);
        result= findKthSortedArrays(a,b,0,0,12);
        System.out.println(result);
    }

    public int findKthSortedArrays(int[] a, int[] b, int i, int j, int k) {
        // 首先需要让数组1的长度小于或等于数组2的长度
        if (a.length - i > b.length - j) {
            return findKthSortedArrays(b, a, j, i, k);
        }
        // 判断小的数组是否为空，为空的话，直接在另一个数组找第K个即可
        if (a.length -1 < i) {
            return b[j + k - 1];
        }
        if(k==1)
            return Math.min(a[i],b[j]);


        int pa = Math.min(i + k / 2, a.length);
        int pb = j + k - pa + i;
        if (a[pa - 1] < b[pb - 1])
            return findKthSortedArrays(a, b, pa, j, k - pa +i);
        else if (a[pa - 1] > b[pb - 1])
            return findKthSortedArrays(a, b, i, pb, k - pb +j);
        else
            return a[pa - 1];


    }
    public int findMedianSortedArrays(int[] a,int[] b,int k){
        Pair<Integer, Integer> pair = doFindCandidatePair(a, b, 0, a.length - 1, 0, b.length - 1, k);
        if(pair.getKey()>a.length-1||pair.getKey()==-1)
            return b[pair.getValue()];
        if(pair.getValue()>b.length-1||pair.getValue()==-1)
            return b[pair.getKey()];
        return Math.min(a[pair.getKey()],b[pair.getValue()]);
    }

    public Pair<Integer,Integer> doFindCandidatePair(int[] a,int[] b,int alow,int ahigh,int blow,int bhigh,int k){
        if(k==1)
            return Pair.of(alow,blow);
        if(ahigh<alow)
            return Pair.of(-1,doSingleCandidate(b,blow,bhigh,k));
        if(bhigh<blow)
            return Pair.of(doSingleCandidate(a,alow,ahigh,k),-1);
        Pair<Integer,Integer> previous = doFindCandidatePair(a,b,alow,ahigh,blow,bhigh,k/2);
        boolean inLeft = true;
        int halfK = k%2==0?k/2:k/2+1;
        int previousI = previous.getLeft();
        if(previous.getKey()==-1)
            return Pair.of(-1, doSingleCandidate(b,previous.getRight()+1,bhigh ,halfK ));
        if(previous.getValue()==-1)
            return Pair.of(doSingleCandidate(a,previous.getLeft()+1,ahigh,halfK  ),-1);
        if(b[previous.getRight()]<a[previous.getLeft()]){
            previousI = previous.getRight();
            inLeft = false;
        }


        if(inLeft) {
            return doFindCandidatePair(a,b,previousI+1,ahigh,previous.getRight(),bhigh,halfK);
        }
        return doFindCandidatePair(a,b,previous.getLeft(),ahigh,previousI+1,bhigh,halfK);


    }

    private int doSingleCandidate(int[] a, int alow, int ahigh, int k) {
        return alow+k-1;
    }
}
