import java.util.*;

public class RR {
    private static final int QUANTUM = 3;
    private static ArrayList<Process> processes = new ArrayList<>();
    private static LinkedList<Process> readyQueue = new LinkedList<>();
    private static Scanner in = new Scanner(System.in);

    public static void run() {
        int numOfProcess;
        int timer = 0;
        ArrayList<Integer> cycleCompletionTimes = new ArrayList<>(); // store the timer for each cycle
        ArrayList<Integer> processSequence = new ArrayList<>(); // store the overall sequence of process executed
        // for rerun purposes
        processes.clear();
        readyQueue.clear();
  
        System.out.println("\n*** Round Robin (Quantum = 3) Scheduling ***\n");
        do {
            System.out.print("Enter no of process: ");
            numOfProcess = in.nextInt();
            if (numOfProcess < 3 || numOfProcess > 10)
                System.out.println("Number of process can only be in range from 3 to 10\n");
            else 
                break;
        } while (true);

        // populate processes
        for (int i = 0; i < numOfProcess; i++) {
            int arrivalTime, burstTime;
            System.out.print("\nEnter P" + i + " arrival time: ");
            arrivalTime = in.nextInt();
            System.out.print("Enter P" + i + " burst time: ");
            burstTime = in.nextInt();
            processes.add(new Process(i, burstTime, arrivalTime));
        }

        // insert first arrived process
        Collections.sort(processes); // sort the processes by arrival time 
        cycleCompletionTimes.add(0); // as timer always starts from 0
        Process firstArrivedProcess = processes.get(0);
        readyQueue.addFirst(firstArrivedProcess);
        firstArrivedProcess.setInQueue(true);
        if (firstArrivedProcess.getArrivalTime() != 0) {
            timer = firstArrivedProcess.getArrivalTime();
            cycleCompletionTimes.add(timer);
            processSequence.add(null);
        }
        // start executing round robin
        while (!readyQueue.isEmpty()) {
            if (readyQueue.getFirst().getBurstTime() <= QUANTUM) {
                Process finishedProcess = readyQueue.pop();
                processSequence.add(finishedProcess.getProcessNum());
                timer += finishedProcess.getBurstTime();
                while (finishedProcess.getBurstTime() > 0) 
                    finishedProcess.decrementBurstTime();
                finishedProcess.setCompletionTime(timer); // set isComplete to true and calculate tt, wt
                checkForNewArrivals(timer);
            }
            else {
                Process unfinishProcess = readyQueue.pop();
                processSequence.add(unfinishProcess.getProcessNum());
                for (int i = 0; i < QUANTUM; i++)
                    unfinishProcess.decrementBurstTime();
                timer += QUANTUM;
                checkForNewArrivals(timer);
                readyQueue.addLast(unfinishProcess);
            }
            cycleCompletionTimes.add(timer);
        }
        
        // construct table 
        System.out.println("\n\nTable: ");
        printTable();

        // construct gantt chart
        System.out.println("\nGantt Chart: ");
        int numOfBoxes = processSequence.size();
        drawLine(numOfBoxes);
        System.out.print("|");
        for (int i = 0; i < numOfBoxes; i++) {
            if (processSequence.get(i) == null) 
                System.out.print("    |");
            else
                System.out.print(" P" + processSequence.get(i) + " |");
        }
        drawLine(numOfBoxes);
        for (int i = 0; i < cycleCompletionTimes.size(); i++) 
            if (cycleCompletionTimes.get(i) / 10 > 0)
                System.out.print(String.format("%d   ", cycleCompletionTimes.get(i)));
            else
                System.out.print(String.format("%d    ", cycleCompletionTimes.get(i)));

        // Display Average TT & WT
        System.out.println("\n\n**Average Turnaround Time = " + calculateAVGTurnaroundTime());
        System.out.println("**Average Waiting Time = " + calculateAVGWaitingTime());
    } 

    private static void checkForNewArrivals(int currentTime) {
        for (int i = 0; i < processes.size(); i++) {
            Process temp = processes.get(i);
            if (temp.getArrivalTime() <= currentTime && !temp.inQueue() && !temp.isComplete()) {
                temp.setInQueue(true);
                readyQueue.addLast(temp);
            }
        }
    }

    private static double calculateAVGTurnaroundTime() {
        double totalTT = 0;
        for (int i = 0; i < processes.size(); i++)
            totalTT += processes.get(i).getTurnAroundTime();
        return totalTT / processes.size();
    }

    private static double calculateAVGWaitingTime() {
        double totalWT = 0;
        for (int i = 0; i < processes.size(); i++)
            totalWT += processes.get(i).getWaitingTime();
        return totalWT / processes.size();
    }

    private static void printTable() {
        System.out.println("\n+---------+--------------+------------+-----------------+-----------------+--------------+");
        System.out.println("| Process | Arrival Time | Burst Time | Completion Time | Turnaround Time | Waiting Time |");
        System.out.println("+---------+--------------+------------+-----------------+-----------------+--------------+");
        for (int i = 0; i < processes.size(); i++) {
            int at, bt, ct, tt, wt;
            for (int j = 0; j < processes.size(); j++) {
                if (processes.get(j).getProcessNum() == i) {
                    at = processes.get(j).getArrivalTime();
                    bt = processes.get(j).getInitialBurstTime();
                    ct = processes.get(j).getCompletionTime();
                    tt = processes.get(j).getTurnAroundTime();
                    wt = processes.get(j).getWaitingTime();

                    String row = String.format(
                        "|   P%d    |      %2d      |     %2d     |       %2d        |       %2d        |      %2d      |", 
                        i, at, bt, ct, tt, wt
                    );
                    System.out.println(row);
                }
            }
        }
        System.out.println("+---------+--------------+------------+-----------------+-----------------+--------------+");
    }

    private static void drawLine(int numOfBoxes) {
        System.out.println();
        while (numOfBoxes > 0) {
            System.out.print("+----");
            numOfBoxes--;
        }
        System.out.print("+");
        System.out.println();
    }
}