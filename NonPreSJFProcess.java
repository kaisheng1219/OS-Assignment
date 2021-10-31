public class NonPreSJFProcess {
    private String processNum;
    private int arrivalTime;
    private int burstTime;
    private int completionTime;
    private int turnAroundTime;
    private int waitingTime;
    private boolean completed = false;  // to check whether the process is completed or not

    public NonPreSJFProcess(String processNum, int arrivalTime, int burstTime) {
        this.processNum = processNum;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    public String getProcessNum() {
        return processNum;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void updateProcessTime(int completionTime) {
        this.completionTime = completionTime;
        turnAroundTime = completionTime - arrivalTime;
        waitingTime = turnAroundTime - burstTime;
        completed = true;
    }
}
