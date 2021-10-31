import java.util.*;

public class SRTF {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter no of process:");
        int numberOfProcess = sc.nextInt();
        int pid[] = new int[numberOfProcess]; // it takes pid of process
        int arrivalTime[] = new int[numberOfProcess]; // at means arrival time
        int burstTime[] = new int[numberOfProcess]; // bt means burst time
        int completeTime[] = new int[numberOfProcess]; // ct means complete time
        int turnAroundTime[] = new int[numberOfProcess];// ta means turn around time
        int waitingTime[] = new int[numberOfProcess]; // wt means waiting time
        int complete[] = new int[numberOfProcess]; // f means it is flag it checks process is completed or not
        int k[] = new int[numberOfProcess]; // it is also stores brust time
        int i, st = 0, completedProcess = 0;
        float avgwt = 0, avgta = 0;

        for (i = 0; i < numberOfProcess; i++) {
            pid[i] = i + 1;
            System.out.println("enter process " + (i + 1) + " arrival time:");
            arrivalTime[i] = sc.nextInt();
            System.out.println("enter process " + (i + 1) + " burst time:");
            burstTime[i] = sc.nextInt();
            k[i] = burstTime[i];
            complete[i] = 0;
        }

        while (true) {
            int min = 100, c = numberOfProcess;
            if (completedProcess == numberOfProcess)
                break;

            for (i = 0; i < numberOfProcess; i++) {
                if ((arrivalTime[i] <= st) && (complete[i] == 0) && (burstTime[i] < min)) {
                    min = burstTime[i];
                    c = i;
                }
            }

            if (c == numberOfProcess)
                st++;
            else {
                burstTime[c]--;
                st++;
                if (burstTime[c] == 0) {
                    completeTime[c] = st;
                    complete[c] = 1;
                    completedProcess++;
                }
            }
        }

        for (i = 0; i < numberOfProcess; i++) {
            turnAroundTime[i] = completeTime[i] - arrivalTime[i];
            waitingTime[i] = turnAroundTime[i] - k[i];
            avgwt += waitingTime[i];
            avgta += turnAroundTime[i];
        }

        System.out.println("pid  arrival  burst  complete turn waiting");
        for (i = 0; i < numberOfProcess; i++) {
            System.out.println(pid[i] + "\t" + arrivalTime[i] + "\t" + k[i] + "\t" + completeTime[i] + "\t"
                    + waitingTime[i] + "\t" + waitingTime[i]);
        }

        System.out.println("\naverage tat is " + (float) (avgta / numberOfProcess));
        System.out.println("average wt is " + (float) (avgwt / numberOfProcess));
        sc.close();
    }
}

