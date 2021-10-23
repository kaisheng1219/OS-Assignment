import java.util.*;

public class SJF {
    // public static void main(String args[]) {
    public static void nonPreemptive() {
        Scanner input = new Scanner(System.in);
        boolean processNumberOutOfbound;
        int numberOfProcess;

        System.out.println("\n*** Non-Preemptive SJF Scheduling ***\n");
        do {
            System.out.print("Enter no of process: ");
            numberOfProcess = input.nextInt();
            processNumberOutOfbound = false;
            if (numberOfProcess < 3 || numberOfProcess > 10) {
                processNumberOutOfbound = true;
                System.out.println("Number of process can only be in range from 3 to 10\n");
            }
        } while (processNumberOutOfbound);

        String process[] = new String[numberOfProcess];
        String ganttChart[] = new String[numberOfProcess];
        int order = 0;      // use for arranging the order of proccesses in the gantt chart
        int ganttChartExecutionTime[] = new int[numberOfProcess + 1];
        int ganttChartCompletionTime[] = new int[numberOfProcess];

        int at[] = new int[numberOfProcess];        // arrival time
        int bt[] = new int[numberOfProcess];        // burst time
        int ct[] = new int[numberOfProcess];        // completion time
        int tat[] = new int[numberOfProcess];       // turn around time
        int wt[] = new int[numberOfProcess];        // waiting time
        boolean completed[] = new boolean[numberOfProcess];  // to check whether the process is completed or not
        int st = 0;         // system time
        int completedProcess = 0;
        float avgWT = 0, avgTAT = 0;
        boolean proccessArriveAt0 = false;

        for (int i = 0; i < numberOfProcess; i++) {
            System.out.print("\nEnter P" + i + " arrival time: ");
            at[i] = input.nextInt();
            System.out.print("Enter P" + i + " burst time: ");
            bt[i] = input.nextInt();
            process[i] = "P"+ i;
            completed[i] = false;
        }

        for (int i = 0; i < numberOfProcess; i++) {
            if (at[i] == 0) {
                proccessArriveAt0 = true;
                break;
            }
        }

        while (true) {
            int c = numberOfProcess, minBurstTime = 100;     // preset c = number of process

            if (completedProcess == numberOfProcess)         // if completed process = no of process, should break from this loop
                break;
            for (int i = 0; i < numberOfProcess; i++) {
                /*
                 * If i'th process arrival time <= system time and its completed = 0 and burst < minimum burst time
                 * Then set c points to that particular process
                 */
                if ((at[i] <= st) && (!completed[i]) && (bt[i] < minBurstTime)) {
                    minBurstTime = bt[i];
                    c = i;
                }
            }
            /*
             * If c still equals to number of process, means no process's arrival time < system time
             * so have to increase the system time
             */
            if (c == numberOfProcess)
                st++;
            else {
                ct[c] = st + bt[c];
                tat[c] = ct[c] - at[c];
                wt[c] = tat[c] - bt[c];
                completed[c] = true;
                completedProcess++;
                ganttChart[order] = process[c];
                ganttChartExecutionTime[order] = st;
                ganttChartCompletionTime[order] = ct[c];
                order++;
                st += bt[c];
            }
        }

        System.out.println("\nProcess\t\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < numberOfProcess; i++) {
            avgWT += wt[i];
            avgTAT += tat[i];
            System.out.println(process[i] + "\t\t" + at[i] + "\t" + bt[i] + "\t" + ct[i] + "\t" + tat[i] + "\t" + wt[i]);
        }

        // discard the last element in ganttChartExecutionTime[]
        ganttChartExecutionTime[process.length] = ganttChartCompletionTime[process.length - 1];

        System.out.println("\nGantt Chart : ");
        drawLine(proccessArriveAt0, ganttChart, ganttChartExecutionTime, ganttChartCompletionTime);
        System.out.print("|");
        if (!proccessArriveAt0)
            System.out.print("    |");
        for(int i = 0; i < ganttChart.length; i++) {
            System.out.print(" " + ganttChart[i] + " |");
            if (ganttChartExecutionTime[i + 1] != ganttChartCompletionTime[i])
                System.out.print("    |");
        }
        drawLine(proccessArriveAt0, ganttChart, ganttChartExecutionTime, ganttChartCompletionTime);
        System.out.print("0    ");
        if (!proccessArriveAt0)
            if (ganttChartExecutionTime[0] / 10 > 0)
                System.out.print(ganttChartExecutionTime[0] + "   ");
            else
                System.out.print(ganttChartExecutionTime[0] + "    ");

        for(int i = 0; i < ganttChart.length; i++) {
            if (ganttChartCompletionTime[i] / 10 > 0) {
                System.out.print(ganttChartCompletionTime[i] + "   ");
                if (ganttChartExecutionTime[i + 1] != ganttChartCompletionTime[i])
                    System.out.print(ganttChartExecutionTime[i + 1] + "   ");
            }
            else {
                System.out.print(ganttChartCompletionTime[i] + "    ");
                if (ganttChartExecutionTime[i + 1] != ganttChartCompletionTime[i])
                    System.out.print( ganttChartExecutionTime[i + 1] + "    ");
            }
        }

        System.out.println("\n\n**Average Turnaround Time is " + (float) (avgTAT / numberOfProcess));
        System.out.println("**Average Waiting Time is " + (float) (avgWT / numberOfProcess));
    }

    private static void drawLine(boolean proccessArriveAt0, String[] ganttChart,
                                 int[] ganttChartExecutionTime, int[] ganttChartCompletionTime)
    {
        System.out.println();
        if (!proccessArriveAt0)
            System.out.print("+----");
        for(int i = 0; i < ganttChart.length; i++) {
            System.out.print("+----");
            if (ganttChartExecutionTime[i + 1] != ganttChartCompletionTime[i])
                System.out.print("+----");
        }
        System.out.print("+");
        System.out.println();
    }
}
