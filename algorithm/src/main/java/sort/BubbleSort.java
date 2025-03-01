package sort;

public class BubbleSort implements Sort{

    @Override
    public void sort(int[] array){

        int loopCount = array.length - 1;
        for (int i = 0; i < loopCount; i++) {

            for (int j = 0; j < loopCount - i; j++) {
                int value1 = array[j];
                int value2 = array[j + 1];

                if(value1 > value2){
                    array[j] = value2;
                    array[j + 1] = value1;
                }
            }
        }


    }

}
