import java.util.Comparator;

public class ArrivalTimeComparator implements Comparator<Process>{

	public int compare(Process o1, Process o2) {
		if(o1.getArrivalTime() < o2.getArrivalTime())
			return -1;
		else if(o1.getArrivalTime() > o2.getArrivalTime())
			return 1;
		return 1;
	}	
}
