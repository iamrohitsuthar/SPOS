import java.util.Comparator;

public class RemainingTimeComparator implements Comparator<Process>{

	public int compare(Process o1, Process o2) {
		if(o1.getRemainingBurstTime() < o2.getRemainingBurstTime())
			return -1;
		else if(o1.getRemainingBurstTime() > o2.getRemainingBurstTime())
			return 1;
		return 1;
	}

}
