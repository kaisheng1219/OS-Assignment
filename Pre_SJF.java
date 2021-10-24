import java.util.*;
 
public class Pre_SJF {

public static void Preemptive()
{
Scanner input =new Scanner(System.in);
boolean processNumberOutOfbound;
int numberOfProcess;

System.out.println("\n*** Preemptive SJF Scheduling ***\n");
        do {
            System.out.print("Enter no of process: ");
            numberOfProcess = input.nextInt();
            processNumberOutOfbound = false;
            if (numberOfProcess < 3 || numberOfProcess > 10) {
                processNumberOutOfbound = true;
                System.out.println("Number of process can only be in range from 3 to 10\n");
            }
        } while (processNumberOutOfbound);


//numberOfProcess= input.nextInt();
int pid[] = new int[numberOfProcess]; // it takes pid of process
int at[] = new int[numberOfProcess]; // at means arrival time
int bt[] = new int[numberOfProcess]; // bt means burst time
int ct[] = new int[numberOfProcess]; // ct means complete time
int ta[] = new int[numberOfProcess];// ta means turn around time
int wt[] = new int[numberOfProcess];  // wt means waiting time
int f[] = new int[numberOfProcess];  // f means it is flag it checks process is completed or not
int k[]= new int[numberOfProcess];   // it is also stores brust time
    int i, st=0, tot=0;
    float avgwt=0, avgta=0;
 
    for (i=0;i<numberOfProcess;i++)
    {
     pid[i]= i+1;
     System.out.println ("enter process " +(i)+ " arrival time:");
     at[i]= input.nextInt();
     System.out.println("enter process " +(i)+ " burst time:");
     bt[i]= input.nextInt();
     k[i]= bt[i];
     f[i]= 0;
    }
    
    while(true){
     int min=99,c=numberOfProcess;
     if (tot==numberOfProcess)
     break;
    
     for ( i=0;i<numberOfProcess;i++)
     {
     if ((at[i]<=st) && (f[i]==0) && (bt[i]<min))
     {
     min=bt[i];
     c=i;
     }
     }
    
     if (c==numberOfProcess)
     st++;
     else
     {
     bt[c]--;
     st++;
     if (bt[c]==0)
     {
     ct[c]= st;
     f[c]=1;
     tot++;
     }
     }
    }
    
    for(i=0;i<numberOfProcess;i++)
    {
     ta[i] = ct[i] - at[i];
     wt[i] = ta[i] - k[i];
     avgwt+= wt[i];
     avgta+= ta[i];
    }
    
    System.out.println("pid  arrival  burst  complete turn waiting");
    for(i=0;i<numberOfProcess;i++)
    {
     System.out.println(pid[i] +"\t"+ at[i]+"\t"+ k[i] +"\t"+ ct[i] +"\t"+ ta[i] +"\t"+ wt[i]);
    }
    
    System.out.println("\naverage tat is "+ (float)(avgta/numberOfProcess));
    System.out.println("average wt is "+ (float)(avgwt/numberOfProcess));
    input.close();
}
}
