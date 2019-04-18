import java.util.Random;

/**
 * author:lichao
 * date:2019/4/18
 * description:
 **/
public abstract class MathUtil {
    /**
     * 得到0到9的随机数
     * @return
     */
    public static int[] getRandomRangeInts(int max){
        int min=0;
        Random random = new Random();
        int[] arr= new int[max];
        for (int i = 0; i < max-1; i++) {
            arr[i] =random.nextInt(100)%(100-min+1) + min;
        }
        return arr;
    }
}
    
