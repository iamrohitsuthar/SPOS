import java.util.Comparator;

public class PriorityPremptiveSortComparator implements Comparator<Process> {

	public int compare(Process o1, Process o2) {
		if(o1.getPriority() > o2.getPriority())
			return -1;
		else if(o1.getPriority() < o2.getPriority())
			return 1;
		return -1;
	}

}
