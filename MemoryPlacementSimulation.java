import java.util.*;

class MemoryBlock {
    int id;
    int size;
    boolean allocated;

    MemoryBlock(int id, int size) {
        this.id = id;
        this.size = size;
        this.allocated = false;
    }
}

public class MemoryPlacementSimulation {
    public static void main(String[] args) {
        List<MemoryBlock> memory = new ArrayList<>();
        memory.add(new MemoryBlock(1, 10));
        memory.add(new MemoryBlock(2, 20));
        memory.add(new MemoryBlock(3, 30));
        memory.add(new MemoryBlock(4, 40));
        memory.add(new MemoryBlock(5, 50));

        // Simulate memory placement strategies
        System.out.println("Best Fit:");
        bestFit(memory, new MemoryBlock(6, 25));
        System.out.println("\nFirst Fit:");
        firstFit(memory, new MemoryBlock(7, 35));
        System.out.println("\nNext Fit:");
        nextFit(memory, new MemoryBlock(8, 45));
        System.out.println("\nWorst Fit:");
        worstFit(memory, new MemoryBlock(9, 55));
    }

    public static void bestFit(List<MemoryBlock> memory, MemoryBlock process) {
        MemoryBlock bestFitBlock = null;
        int minFragmentation = Integer.MAX_VALUE;

        for (MemoryBlock block : memory) {
            if (!block.allocated && block.size >= process.size && block.size - process.size < minFragmentation) {
                bestFitBlock = block;
                minFragmentation = block.size - process.size;
            }
        }

        if (bestFitBlock != null) {
            bestFitBlock.allocated = true;
            System.out.println("Process " + process.id + " allocated to Memory Block " + bestFitBlock.id);
        } else {
            System.out.println("Process " + process.id + " cannot be allocated (Best Fit)");
        }
    }

    public static void firstFit(List<MemoryBlock> memory, MemoryBlock process) {
        for (MemoryBlock block : memory) {
            if (!block.allocated && block.size >= process.size) {
                block.allocated = true;
                System.out.println("Process " + process.id + " allocated to Memory Block " + block.id);
                return;
            }
        }
        System.out.println("Process " + process.id + " cannot be allocated (First Fit)");
    }

    public static void nextFit(List<MemoryBlock> memory, MemoryBlock process) {
        int startIndex = 0;
        for (int i = 0; i < memory.size(); i++) {
            int index = (startIndex + i) % memory.size();
            MemoryBlock block = memory.get(index);
            if (!block.allocated && block.size >= process.size) {
                block.allocated = true;
                System.out.println("Process " + process.id + " allocated to Memory Block " + block.id);
                return;
            }
        }
        System.out.println("Process " + process.id + " cannot be allocated (Next Fit)");
    }

    public static void worstFit(List<MemoryBlock> memory, MemoryBlock process) {
        MemoryBlock worstFitBlock = null;
        int maxFragmentation = Integer.MIN_VALUE;

        for (MemoryBlock block : memory) {
            if (!block.allocated && block.size >= process.size && block.size - process.size > maxFragmentation) {
                worstFitBlock = block;
                maxFragmentation = block.size - process.size;
            }
        }

        if (worstFitBlock != null) {
            worstFitBlock.allocated = true;
            System.out.println("Process " + process.id + " allocated to Memory Block " + worstFitBlock.id);
        } else {
            System.out.println("Process " + process.id + " cannot be allocated (Worst Fit)");
        }
    }
}
