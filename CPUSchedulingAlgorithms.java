import java.util.*;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int priority;
    int remainingTime;

    Process(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
    }
}

public class CPUSchedulingAlgorithms {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 0, 6, 2));
        processes.add(new Process(2, 2, 8, 1));
        processes.add(new Process(3, 3, 7, 2));
        processes.add(new Process(4, 5, 3, 3));
        processes.add(new Process(5, 8, 4, 2));

        // FCFS
        System.out.println("FCFS Scheduling:");
        fcfs(processes);

        // SJF (Preemptive)
        System.out.println("\nSJF (Preemptive) Scheduling:");
        sjfPreemptive(new ArrayList<>(processes));

        // Priority (Non-Preemptive)
        System.out.println("\nPriority (Non-Preemptive) Scheduling:");
        priorityNonPreemptive(new ArrayList<>(processes));

        // Round Robin (Preemptive)
        System.out.println("\nRound Robin (Preemptive) Scheduling:");
        roundRobinPreemptive(new ArrayList<>(processes), 2);
    }

    public static void fcfs(List<Process> processes) {
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;
        int totalWaitingTime = 0;

        for (Process process : processes) {
            int waitingTime = Math.max(0, currentTime - process.arrivalTime);
            totalWaitingTime += waitingTime;
            currentTime = Math.max(currentTime, process.arrivalTime) + process.burstTime;
            System.out.println("Process " + process.id + " waited " + waitingTime + " units.");
        }

        double averageWaitingTime = (double) totalWaitingTime / processes.size();
        System.out.println("Average Waiting Time: " + averageWaitingTime);
    }

    public static void sjfPreemptive(List<Process> processes) {
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.remainingTime));
        int currentTime = 0;
        int totalWaitingTime = 0;
        int completedProcesses = 0;

        while (!processes.isEmpty() || !pq.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                pq.offer(processes.remove(0));
            }

            if (!pq.isEmpty()) {
                Process process = pq.poll();
                int waitingTime = currentTime - process.arrivalTime;
                totalWaitingTime += waitingTime;
                currentTime += Math.min(process.remainingTime, 1);
                process.remainingTime -= Math.min(process.remainingTime, 1);

                if (process.remainingTime > 0) {
                    pq.offer(process);
                } else {
                    completedProcesses++;
                    System.out.println("Process " + process.id + " waited " + waitingTime + " units.");
                }
            } else {
                currentTime = processes.get(0).arrivalTime;
            }
        }

        double averageWaitingTime = (double) totalWaitingTime / completedProcesses;
        System.out.println("Average Waiting Time: " + averageWaitingTime);
    }

    public static void priorityNonPreemptive(List<Process> processes) {
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));
        int currentTime = 0;
        int totalWaitingTime = 0;
        int completedProcesses = 0;

        while (!processes.isEmpty() || !pq.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                pq.offer(processes.remove(0));
            }

            if (!pq.isEmpty()) {
                Process process = pq.poll();
                int waitingTime = currentTime - process.arrivalTime;
                totalWaitingTime += waitingTime;
                currentTime += process.burstTime;
                completedProcesses++;
                System.out.println("Process " + process.id + " waited " + waitingTime + " units.");
            } else {
                currentTime = processes.get(0).arrivalTime;
            }
        }

        double averageWaitingTime = (double) totalWaitingTime / completedProcesses;
        System.out.println("Average Waiting Time: " + averageWaitingTime);
    }

    public static void roundRobinPreemptive(List<Process> processes, int quantum) {
        Queue<Process> queue = new LinkedList<>();
        int currentTime = 0;
        int totalWaitingTime = 0;
        int completedProcesses = 0;

        while (!processes.isEmpty() || !queue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                queue.offer(processes.remove(0));
            }

            if (!queue.isEmpty()) {
                Process process = queue.poll();
                int waitingTime = currentTime - process.arrivalTime;
                totalWaitingTime += waitingTime;
                int remainingTime = Math.max(0, process.remainingTime - quantum);
                currentTime += Math.min(process.remainingTime, quantum);
                process.remainingTime = remainingTime;

                if (process.remainingTime > 0) {
                    queue.offer(process);
                } else {
                    completedProcesses++;
                    System.out.println("Process " + process.id + " waited " + waitingTime + " units.");
                }
            } else {
                currentTime = processes.get(0).arrivalTime;
            }
        }

        double averageWaitingTime = (double) totalWaitingTime / completedProcesses;
        System.out.println("Average Waiting Time: " + averageWaitingTime);
    }
}
