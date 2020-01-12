import java.util.Iterator;
import java.util.PriorityQueue;

public class ShortestJobFirst {
	private PriorityQueue<Process> readyQueue;
	
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
	
	public void schedule(int noOfProcess, PriorityQueue<Process> priorityQueue) {
		readyQueue = new PriorityQueue<Process>(noOfProcess, new RemainingTimeComparator());
		long lastArrivalTime = findLastArrivalTime(priorityQueue);
		
		double avgWaitingTime = 0;
		double avgTurnaroundTime = 0;
		int time = 0;
		int prevTime = 0;
		
		fillReadyQueue(time, prevTime, priorityQueue);
		Msg.println("PNO\tAT\tBT\tCT\tTAT\tWT");
		while(!readyQueue.isEmpty()) {
			Process process = readyQueue.peek();
			prevTime = time;
			time++;
			
			if(time <= lastArrivalTime)
				fillReadyQueue(time, prevTime, priorityQueue);
			
			if(process.getRemainingBurstTime() == 1) {
				process.setRemainingBurstTime(process.getRemainingBurstTime()-1);
				process.setCompletionTime(time);
				process.setTurnaroundTime(process.getCompletionTime() - process.getArrivalTime());
				process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
				readyQueue.remove();
			}
			else {
				process.setRemainingBurstTime(process.getRemainingBurstTime()-1);
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
	
