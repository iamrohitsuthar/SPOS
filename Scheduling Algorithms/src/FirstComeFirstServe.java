import java.util.PriorityQueue;

public class FirstComeFirstServe {	
	
	public void schedule(int noOfProcess, PriorityQueue<Process> priorityQueue) {
		
		double avgWaitingTime = 0;
		double avgTurnaroundTime = 0;
		long prevCompletionTime = 0;
		
		Msg.println("PNO\tAT\tBT\tCT\tTAT\tWT");
		while(!priorityQueue.isEmpty()) {
			Process process = priorityQueue.remove();
			process.setCompletionTime(prevCompletionTime + process.getBurstTime());
			//update previous process completion time
			prevCompletionTime = prevCompletionTime + process.getBurstTime();
			process.setTurnaroundTime(process.getCompletionTime() - process.getArrivalTime());
			process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
			avgWaitingTime += process.getWaitingTime();
			avgTurnaroundTime += process.getTurnaroundTime();
			process.displayData();
		}
		Msg.println("Average Turnaround time: "+ avgTurnaroundTime/noOfProcess);
		Msg.println("Average Waiting time: "+ avgWaitingTime/noOfProcess);
	}

}
