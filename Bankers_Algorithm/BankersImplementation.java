import java.util.Scanner;

public class BankersImplementation {
    private int available[];
    private int maximum[][], allocation[][], need[][];
    private boolean isCompleted[];
    private int sequence[];
    private int noOfresources, noOfprocesses;
    private static final Scanner scanner = new Scanner(System.in);

    BankersImplementation(int noOfprocesses, int noOfresources, int maximum[][], int allocation[][], int available[]) {
        sequence = new int[noOfprocesses];
        isCompleted = new boolean[noOfprocesses];
        need = new int[noOfprocesses][noOfresources];
        this.maximum = maximum;
        this.allocation = allocation;
        this.noOfprocesses = noOfprocesses;
        this.noOfresources = noOfresources;
        this.available = available;
    }

    public void calculateNeedMatrix() {
        for(int i = 0 ; i < noOfprocesses; i++) {
            for(int j = 0; j < noOfresources; j++) {
                need[i][j] = maximum[i][j] - allocation[i][j];
            }
        }
    }

    public void displayNeedMatrix() {
        System.out.println("Resultant Need Matrix");
        for(int i = 0; i < noOfprocesses ; i++) {
            System.out.print("P"+i+"\t");
            for(int j = 0; j < noOfresources ; j++) {
                System.out.print(need[i][j]+" ");
            }
            System.out.println();
        }
    }

    public void calculateSafeSequence() {
        int count = 0; //for tracking how many processes completed their execution
        boolean execute = true;
        while(count < noOfprocesses) {
            boolean flag = false; //flag to break the while loop if none of the process can able to execute (DEADLOCK)
            for(int i = 0 ; i < noOfprocesses ; i++) {
                execute = true;
                if(isCompleted[i] == false) {
                    //check whether process can execute or not
                    for(int j = 0 ; j < noOfresources ; j++) {
                        if(need[i][j] > available[j]) {
                            execute = false;
                            break;
                        }
                    }
                    if(execute) {
                        //execute the process
                        //update available
                        for(int j = 0 ; j < noOfresources ; j++) {
                            available[j] = available[j] + allocation[i][j];
                        }
                        sequence[count++] = i;
                        isCompleted[i] = true;
                        flag = true;
                    }
                }
            }
            if(flag == false)
                break;
        }
        if(count < noOfprocesses) {
            System.out.println("SYSTEM IS UNSAFE");
        }
        else {
            //print the safe list
            System.out.println("SYSTEM IS SAFE");
            System.out.print("SAFE SEQUENCE IS ");
            for(int i = 0; i < sequence.length; i++) {
                if(i == sequence.length-1)
                    System.out.print("P"+ sequence[i]);
                else
                    System.out.print("P"+ sequence[i] + "=>");
            }
            System.out.println();
        }
    }

    public static void main(String args[]) {
        System.out.println("Bankers Algorithm Implementation");
        int noOfprocesses, noOfresources;
        int maximum[][], allocation[][];
        int available[];

        System.out.print("Enter No. of processes: ");
        noOfprocesses = scanner.nextInt();
        System.out.print("Enter No. of resources type: ");
        noOfresources = scanner.nextInt();

        available = new int[noOfresources];
        maximum = new int[noOfprocesses][noOfresources];
        allocation = new int[noOfprocesses][noOfresources];

        System.out.println("Available Resources");
        for(int i = 0; i < noOfresources ; i++) {
            System.out.println("Enter the available count of "+ (i+1) +" Resource");
            available[i] = scanner.nextInt();
        }

        System.out.println("Allocation Matrix");
        for(int i = 0; i < noOfprocesses; i++) {
            System.out.println("Enter the allocation matrix values for P"+i);
            for(int j = 0; j < noOfresources ; j++) {
                allocation[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Maximum Matrix");
        for(int i = 0; i < noOfprocesses; i++) {
            System.out.println("Enter the maximum matrix values for P"+i);
            for(int j = 0; j < noOfresources ; j++) {
                maximum[i][j] = scanner.nextInt();
            }
        }

        BankersImplementation bankersImplementation = new BankersImplementation(noOfprocesses, noOfresources, maximum, allocation, available);
        bankersImplementation.calculateNeedMatrix();
        bankersImplementation.displayNeedMatrix();
        bankersImplementation.calculateSafeSequence();
    }
}