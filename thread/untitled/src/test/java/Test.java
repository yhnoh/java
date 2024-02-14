import java.util.stream.IntStream;

public class Test {


    @org.junit.jupiter.api.Test
    public void joinTest(){
        //데이터 셋팅
        int joinBufferSize = 10;
        int rowSize = 100;

        int[] joinBuffer = new int[joinBufferSize];
        int[] drivingTable = new int[rowSize];
        int[] drivenTable = new int[rowSize];

        for (int i = 1; i <= rowSize; i++) {
            drivingTable[i -1] = i;
            drivenTable[i -1] = i;
        }

        for (int i = 0; i < drivingTable.length; i++) { //10
            //조인 버퍼 SET
            joinBuffer[i % joinBufferSize] = drivingTable[i];
            boolean isFullJoinBuffer = (i + 1) % joinBufferSize == 0;
            if(isFullJoinBuffer){   //조인 버퍼 FULL
                for (int j = 0; j < joinBuffer.length; j++) {
                    for (int k = 0; k < drivenTable.length; k++) { // 100 * 10
                        if(drivenTable[k] == joinBuffer[j]){
                            System.out.println("조인 컬럼 기준이 일치함");
                        }
                    }
                }
            }
            //조인 버퍼 CLEAR
        }
    }
}
