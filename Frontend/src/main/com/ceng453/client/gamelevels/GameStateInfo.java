package main.com.ceng453.client.gamelevels;

/*
 * A state class for keeping time, cycle, score between different calls of the gamelevels
 */
public class GameStateInfo{
    private long currentCycleCounter;
    private double previousLoopTime;
    private double elapsedTime;
    private int currentGameScore;

    public GameStateInfo( double initialSystemTime ){
        this.previousLoopTime = initialSystemTime;
        this.currentCycleCounter = 0;
        this.currentGameScore = 0;
    }

    public long getCurrentCycleCounter() {
        return currentCycleCounter;
    }

    public double getPreviousLoopTime() {
        return previousLoopTime;
    }

    public void setPreviousLoopTime(double previousLoopTime) {
        this.previousLoopTime = previousLoopTime;
    }

    public void incrementCurrentCycleCount(){
        this.currentCycleCounter++;
    }

    public int getCurrentGameScore() {
        return currentGameScore;
    }

    public double getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void incrementScoreBy(int bounty) {
        this.currentGameScore += bounty;
    }

    public void restartCycleCounter(){
        currentCycleCounter = -1;
    }

    public void setCurrentCycleCounter(long currentCycleCounter){ this.currentCycleCounter = currentCycleCounter; }
}
