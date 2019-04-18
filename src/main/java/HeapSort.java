import org.junit.jupiter.api.Test;

/**
 * author:lichao
 * date:2019/4/18
 * description:
 **/
public class HeapSort {
    @Test
    public void test(){
        //Initializing array
        //int arr[] = {9, 4, 8,12, 3, 1, 2, 5,0};
        int arr[] = {76, 30, 84 ,27 ,60 ,94 ,75, 3, 27, 0 };
        //arr = MathUtil.getRandomRangeInts(10);
        System.out.print("Initial Array  : ");
        printArray(arr);
        arr = heapsort(arr);
        System.out.print("After Sorting  : ") ;
        printArray(arr);
    }

    private int[] heapsort(int[] arr) {
        int N= arr.length;
        MaxHeap heap = new MaxHeap(arr,N);

        return heap.dumpAll();

    }

    private void printArray(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
    
