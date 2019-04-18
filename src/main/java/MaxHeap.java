/**
 * author:lichao
 * date:2019/4/18
 * description:
 **/
public class MaxHeap {
    int[] backing ;
    int eleNum = 0;
    public MaxHeap(int[] arr, int n) {
        backing = new int[n];
        for (int i=0;i<n;i++) {
            swim(arr[i]);
        }

    }

    private void swim(int ele) {
        int lastId = eleNum;
        backing[lastId] = ele;

        if(lastId>0){
            int eleId = lastId+1;
            int fatherId = eleId/2;
            int father;
            while (fatherId>0){
                father = backing[fatherId-1];
                if(ele>father){
                    swap(eleId,fatherId);
                    eleId = fatherId;
                    fatherId = eleId/2;
                }else{
                    backing[eleId-1] = ele;
                    break;
                }
            }
        }
        eleNum+=1;
    }

    private void swap(int eleId, int otherId) {
        int temp = backing[eleId-1];
        backing[eleId-1] = backing[otherId-1];
        backing[otherId-1] = temp;
    }

    public int[] dumpAll() {
        for (int i=1;i<eleNum-1;i++){
            swap(1,eleNum-i+1);
            sink(eleNum-i+1);
        }
        if(backing[1]<backing[0])
            swap(0,1);
        return backing;
    }

    private void sink(int maxId) {
        int sinker = backing[0];
        int sinkId = 1;
        int leftId,rightId,targetId,left,right,target;
        while (sinkId<maxId&&sinkId<maxId/2){
            leftId = 2*sinkId;
            rightId = 2*sinkId+1;
            targetId = leftId;
            left = backing[leftId-1];
            right = backing[rightId - 1];
            target = left;
            if(left<right){
                targetId = rightId;
                target = right;
            }
            if(target>sinker){
                swap(targetId,sinkId);
                sinkId = targetId;
            }else{
                break;
            }

        }



    }
}
    
