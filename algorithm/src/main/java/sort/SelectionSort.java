package sort;

public class SelectionSort implements Sort{

    @Override
    public void sort(int[] array) {

        int loopCount = array.length - 1;


        for (int i = 0; i < loopCount; i++) {
            int minI = i;
            for (int j = i + 1; j < loopCount; j++) {
                if(array[minI] > array[j + 1]){
                    minI = j + 1;
                }

                System.out.println("i = " + i + ",j = " + j);
            }

            int minValue = array[minI];
            array[minI] = array[i];
            array[i] = minValue;
        }



    }
}
