import java.util.PriorityQueue;
import java.util.Scanner;

public class SchedulingAlgos {
	public static Scanner scanner = null;
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		scanner.close();		
	}
	
	public static void main(String[] args) {
		int noOfProcess = 0;
		scanner = new Scanner(System.in);
		int quantumTime = 0;
		PriorityQueue<Process> FCFSpriorityQueue = new PriorityQueue<Process>(3, new ArrivalTimeComparator());
		PriorityQueue<Process> SJFpriorityQueue = new PriorityQueue<Process>(3, new ArrivalTimeComparator());
		PriorityQueue<Process> RRpriorityQueue = new PriorityQueue<Process>(3, new ArrivalTimeComparator());
		PriorityQueue<Process> PNpriorityQueue = new PriorityQueue<Process>(3, new PriorityNonPreemptiveSortComparator());
		PriorityQueue<Process> PPpriorityQueue = new PriorityQueue<Process>(3, new ArrivalTimeComparator());
		
		
		Msg.print("Enter Number of Processes:");
		noOfProcess = scanner.nextInt();
		
		
		for(int i = 0 ; i < noOfProcess ; i++)
		{
			System.out.println("P("+(i+1)+"): Enter Arrival time, Burst time & Priority");
			int at = scanner.nextInt();
			int bt = scanner.nextInt();
			int priority = scanner.nextInt();
			FCFSpriorityQueue.add(new Process(i+1, bt, at, priority));
			RRpriorityQueue.add(new Process(i+1, bt, at, priority));
			SJFpriorityQueue.add(new Process(i+1, bt, at, priority));
			PNpriorityQueue.add(new Process(i+1, bt, at, priority));
			PPpriorityQueue.add(new Process(i+1, bt, at, priority));
		}
		
		Msg.print("Enter Quantum Time:");
		quantumTime = scanner.nextInt();
	
		
		//First Come First Serve (FCFS)
		Msg.println("First Come First Serve (FCFS) Implementation");
		new FirstComeFirstServe().schedule(noOfProcess, FCFSpriorityQueue);
		Msg.println("=================================================================");
		
		//Round Robin Scheduling (RR)
		Msg.println("Round Robin (RR) Implementation");
		new RoundRobin().schedule(noOfProcess, RRpriorityQueue, quantumTime);
		Msg.println("=================================================================");
		
		//Shortest Job First (SJF)
		Msg.println("Shortest Job First (SJF) Implementation");
		new ShortestJobFirst().schedule(noOfProcess, SJFpriorityQueue);
		Msg.println("=================================================================");
		
		//Priority (Preemptive)
		Msg.println("Priority Scheduling (Preemptive Priority) Implementation");
		new PriorityPreemptive().schedule(noOfProcess, PPpriorityQueue);
		Msg.println("=================================================================");
		
		//Priority (NonPreemptive)
		Msg.println("Priority Scheduling (Non Preemptive Priority) Implementation");
		new PriorityNonPreemptive().schedule(noOfProcess, PNpriorityQueue);
		Msg.println("=================================================================");
	}
	
}