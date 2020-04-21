import java.util.*;

public class FIFO {
	static Scanner scanner = new Scanner(System.in);

	public void FIFOImplementation(int pages[], int capacity) {
		int pageFaults = 0;
		HashMap<Integer, Integer> map = new HashMap();
		HashSet<Integer> currentSet = new HashSet();

		for(int i = 0 ; i < pages.length; i++) {

			if(currentSet.size() < capacity) {
				if(!currentSet.contains(pages[i])) {
					currentSet.add(pages[i]);
					pageFaults++;
				}
				map.put(pages[i],i);
			}
			else {
				if(!currentSet.contains(pages[i])) {

					Iterator<Integer> it = currentSet.iterator();
					int fifo = Integer.MAX_VALUE;
					int val = 0;
					while(it.hasNext()) {
						int temp = it.next();
						if(map.get(temp) < fifo) {
							fifo = map.get(temp);
							val = temp;
						}
					}
					currentSet.remove(val);
					map.remove(val);
					map.put(pages[i],i);
					currentSet.add(pages[i]);
					pageFaults++;
				}
			}
		}
		System.out.println("Page Faults: "+pageFaults);
		int pageHits = pages.length - pageFaults;
		System.out.println("Page Hits: "+pageHits);
		System.out.println("Hit Ratio: "+pageHits + "/" + pages.length + " = " + (double)pageHits/pages.length);
	}

	public static void main(String[] args) {
		int capacity, n, pages[];
		// int pages[] = {7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1};
		FIFO fifo = new FIFO();

		System.out.print("Enter capacity of page frame: ");	
		capacity = scanner.nextInt();

		System.out.print("Enter number of page sequence: ");
		n = scanner.nextInt();

		pages = new int[n];

		System.out.print("Enter values (space separated): ");
		for(int i = 0 ; i < n ; i++) {
			pages[i] = scanner.nextInt();
		}

		fifo.FIFOImplementation(pages, capacity);

	}	
}