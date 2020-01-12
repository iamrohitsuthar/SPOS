import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class RoundRobin {
	private Queue<Process> readyQueue;
	
	private void fillReadyQueue(int time, int prevTime, PriorityQueue<Process> priorityQueue) {
		Iterator<Process> iterator = priorityQueue.iterator();
		while(iterator.hasNext()) {
			Process process = iterator.next();
			if(process.getArrivalTime() <= time && ((process.getArrivalTime() > prevTime) || readyQueue.isEmpty())) {
				readyQueue.add(process);
			}
		}
	}
	
	private long findLastArrivalTime(PriorityQueue<Process> priorityQueue) {
		long lastArrivalTime = 0;
		Iterator<Process> iterator = priorityQueue.iterator();
		while(iterator.hasNext()) {
			lastArrivalTime = iterator.next().getArrivalTime();
		}
		return lastArrivalTime;
	}
	
	public void schedule(int noOfProcess, PriorityQueue<Process> priorityQueue, int quantumTime) {
		double avgWaitingTime = 0;
		double avgTurnaroundTime = 0;
		readyQueue = new LinkedList<Process>();
		long lastArrivalTime = findLastArrivalTime(priorityQueue);
		
		int time = 0;
		int prevTime = 0;
		
		fillReadyQueue(time, prevTime, priorityQueue);		
		Msg.println("PNO\tAT\tBT\tCT\tTAT\tWT");
		while(!readyQueue.isEmpty()) {
			Process process = readyQueue.peek();
			if(process.getRemainingBurstTime() > quantumTime) {
				prevTime = time;
				time += quantumTime;
				process.setRemainingBurstTime(process.getRemainingBurstTime() - quantumTime);
				if(time <= lastArrivalTime)
					fillReadyQueue(time, prevTime, priorityQueue);
				readyQueue.remove();
				readyQueue.add(process);
			}
			else {
				prevTime = time;
				time += process.getRemainingBurstTime();
				process.setRemainingBurstTime(0);
				process.setCompletionTime(time);
				process.setTurnaroundTime(process.getCompletionTime() - process.getArrivalTime());
				process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
				readyQueue.remove();
			}
		}
		while(!priorityQueue.isEmpty()) {
			Process process = priorityQueue.remove();
			avgWaitingTime += process.getWaitingTime();
			avgTurnaroundTime += process.getTurnaroundTime();
			process.displayData();
		}
		Msg.println("Average Turnaround time: "+ avgTurnaroundTime/noOfProcess);
		Msg.println("Average Waiting time: "+ avgWaitingTime/noOfProcess);
	}
	
}