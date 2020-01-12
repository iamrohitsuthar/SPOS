
public class Process {
	private int pid;
	private long arrivalTime, burstTime, completionTime, turnaroundTime, waitingTime;
	private long remainingBurstTime;
	private int priority;
	
	public Process(int pid, long burstTime, long arrivalTime, int priority) {
		this.pid = pid;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.remainingBurstTime = burstTime;
		this.priority = priority;
	}
	
	public int getPid() {
		return pid;
	}
	
	public long getArrivalTime() {
		return arrivalTime;
	}
	
	public long getBurstTime() {
		return burstTime;
	}
	
	public void setCompletionTime(long completionTime) {
		this.completionTime = completionTime;
	}
	
	public long getCompletionTime() {
		return completionTime;
	}
	
	public void setTurnaroundTime(long turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}
	
	public long getTurnaroundTime() {
		return turnaroundTime;
	}
	
	public void setWaitingTime(long waitingTime) {
		this.waitingTime = waitingTime;
	}
	
	public long getWaitingTime() {
		return waitingTime;

	}
	
	public void setRemainingBurstTime(long remBurstTime) {
		this.remainingBurstTime = remBurstTime;
	}
	
	public long getRemainingBurstTime() {
		return remainingBurstTime;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void displayData() {
		Msg.println("P"+ pid + "\t" + arrivalTime + "\t" + burstTime + "\t" + completionTime + "\t" + turnaroundTime + "\t"+ waitingTime);
	}
}
