package binary;

public class Binary {

    private int currentMin;
    private int currentMax;
    private int currentGuess;
    private int[] array;
    public Binary(int[] array) {
        this.array = array;
        this.currentMin = 0;
        this.currentMax = array.length -1;
        this.currentGuess = this.calculateCurrentGuess();
    }

    private int calculateCurrentGuess(){
        return (currentMax - currentMin) / 2;
    }

    public boolean isFind(int value){
        int currentGuessValue = array[currentGuess];
        if(currentGuessValue == value){
            return true;
        }

        if(value < currentGuessValue){
            this.currentMax = currentGuess;
            this.currentGuess = this.calculateCurrentGuess();
            isFind(value);
        }

        if(value > currentGuessValue){
            this.currentMin = currentGuess;
            this.currentGuess = this.calculateCurrentGuess();
            isFind(value);
        }

        return false;
    }

}
