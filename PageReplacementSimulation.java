import java.util.*;

public class PageReplacementSimulation {
    public static void main(String[] args) {
        int[] referenceString = { 1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5 };

        // Simulate FIFO Page Replacement Algorithm
        System.out.println("FIFO Page Replacement:");
        fifoPageReplacement(referenceString, 3);

        // Simulate LRU Page Replacement Algorithm
        System.out.println("\nLRU Page Replacement:");
        lruPageReplacement(referenceString, 3);

        // Simulate Optimal Page Replacement Algorithm
        System.out.println("\nOptimal Page Replacement:");
        optimalPageReplacement(referenceString, 3);
    }

    public static void fifoPageReplacement(int[] referenceString, int capacity) {
        int pageFaults = 0;
        Set<Integer> pages = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        for (int page : referenceString) {
            if (!pages.contains(page)) {
                pageFaults++;
                if (pages.size() == capacity) {
                    int removedPage = queue.poll();
                    pages.remove(removedPage);
                }
                pages.add(page);
                queue.offer(page);
            }
        }
        System.out.println("Page Faults: " + pageFaults);
    }

    public static void lruPageReplacement(int[] referenceString, int capacity) {
        int pageFaults = 0;
        Set<Integer> pages = new HashSet<>();
        Map<Integer, Integer> pageToLastUsedIndex = new HashMap<>();

        for (int i = 0; i < referenceString.length; i++) {
            int page = referenceString[i];
            if (!pages.contains(page)) {
                pageFaults++;
                if (pages.size() == capacity) {
                    int leastRecentlyUsedPage = Collections.min(pageToLastUsedIndex.values());
                    pages.remove(leastRecentlyUsedPage);
                    pageToLastUsedIndex.remove(leastRecentlyUsedPage);
                }
                pages.add(page);
            }
            pageToLastUsedIndex.put(page, i);
        }
        System.out.println("Page Faults: " + pageFaults);
    }

    public static void optimalPageReplacement(int[] referenceString, int capacity) {
        int pageFaults = 0;
        Set<Integer> pages = new HashSet<>();
        Map<Integer, Integer> nextOccurrence = new HashMap<>();

        for (int i = 0; i < referenceString.length; i++) {
            int page = referenceString[i];
            if (!pages.contains(page)) {
                pageFaults++;
                if (pages.size() == capacity) {
                    int pageToRemove = -1;
                    int farthestOccurrence = Integer.MIN_VALUE;
                    for (int p : pages) {
                        if (nextOccurrence.get(p) == null) {
                            pageToRemove = p;
                            break;
                        }
                        if (nextOccurrence.get(p) > farthestOccurrence) {
                            farthestOccurrence = nextOccurrence.get(p);
                            pageToRemove = p;
                        }
                    }
                    pages.remove(pageToRemove);
                }
                pages.add(page);
                for (int j = i + 1; j < referenceString.length; j++) {
                    if (referenceString[j] == page) {
                        nextOccurrence.put(page, j);
                        break;
                    }
                }
            }
        }
        System.out.println("Page Faults: " + pageFaults);
    }
}
