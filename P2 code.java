import java.util.Random;
import java.util.ArrayList;
import java.util.List;
/**
 * Main class sorting. It can sort lists using both quicksort and
 * heapsort.
 */
public class Sorting {
    // field variables
    private static int comparisonsQuicksort = 0;
    private static int comparisonsHeapsort = 0;

    /**
     * This method sorts elements using the quicksort algorithm. It uses
     * a helper method that splits the list in half and recursively sorts
     * each sublist. 
     * 
     * @param <T> Element we want to sort.
     * @param list The list we want to sort.
     * @return the number of comparisons made between elements.
     */
    public static<T extends Comparable<? super T>> int quicksort(List<T> list) {
    comparisonsQuicksort = 0; //counter variable to get the number of comparisons
    quicksortHelper(list, 0, list.size() - 1); // start with the whole list
    return comparisonsQuicksort;
    }
    /**
     * Helper method for quicksort. Determines which parts of the list to split by calling partition method.
     * 
     * @param <T> element type
     * @param list the list we want to sort
     * @param start which index to start 
     * @param end which index to stop
     */
    private static <T extends Comparable<? super T>> void quicksortHelper(List<T> list, int start, int end) {
        if (end <= start) return; // base case
        
        int pivotIndex = partition(list, start, end); //first split
        quicksortHelper(list, start, pivotIndex - 1); //lesser than pivot sublist
        quicksortHelper(list, pivotIndex + 1, end); //greater than pivot sublist
    }    
    /**
     * This method takes a pivot and compares each element of the list to the pivot. If the element is
     * less than the pivot it pushes them to the left. At the end, the pivot is moved so the lesser or equal
     * elements stay at the left of the pivot.
     * 
     * @param <T> elements tha we want to compare
     * @param list list we want to sort (or sublist)
     * @param start the index we want to start at 
     * @param end the index we want to end at
     * @return the index where the pivot is at the end 
     */
    private static <T extends Comparable<? super T>> int partition(List<T> list, int start, int end) {
        T pivot = list.get(start);
        int i = start + 1; 
        // for loop that starts one index after the first since it is the pivot
        for (int j = start + 1; j <= end; j++) {
            comparisonsQuicksort++; //adding for the comparisons
            // if the element of the list is smaller or equal than the pivot, we swap it towards the beginning
            if (list.get(j).compareTo(pivot) <= 0) {
                swap(list, j, i);
                i++;
            } 
        }
        // at the end we switch the pivot to the index where the last swap was made 
        swap(list, start, i - 1);
        return i-1; // index of pivot
    }
    /**
     * Uses the heapsort algorithm to sort elements and returns the number of comparisons
     * between elements made. It uses a max heap to sort the elements.
     * 
     * @param <T> element type we want to sort
     * @param list list we want to sort
     * @return number of comparisons between elements made
     */
    public static<T extends Comparable<? super T>> int heapsort(List<T> list) {
    comparisonsHeapsort = 0; // counter
    // making the max heap for all the elements of the list
    for (int i = 0; i < list.size(); i++) {
            bubbleUp(list, i); 
        }

    for (int end = list.size() - 1; end > 0; end--) {
            swap(list, 0, end);        // move largest element to the end
            bubbleDown(list, end, 0);    // restore heap property for the remaining part of the heap
        }  
    return comparisonsHeapsort;
    }
    // adapted from the solutions but made for max heap and also adding the list to the parameters
    private static<T extends Comparable<? super T>> void bubbleUp(List<T> list, int i) {
		int parent = (i - 1) / 2;
        if (i <= 0) return;
		comparisonsHeapsort++;
        if (list.get(i).compareTo(list.get(parent)) > 0) {
            swap(list, i, parent);
			bubbleUp(list, parent);
		}
	}
    // also same logic as the one for H5 but now we care about the largest element (max)
    private static<T extends Comparable<? super T>> void bubbleDown(List<T> list, int heapSize, int i) {
        int max = i;
        int leftChild = 2*i + 1;
        int rightChild = 2*i + 2;
        
        if (leftChild < heapSize) {
            comparisonsHeapsort++;
            if (list.get(leftChild).compareTo(list.get(max)) > 0) {
                max = leftChild;
            }
        } 
        if (rightChild < heapSize) {
            comparisonsHeapsort++;
            if (list.get(rightChild).compareTo(list.get(max)) > 0) {
                max = rightChild;
            }
        }
        if (max != i) {
            swap(list, i, max);
            bubbleDown(list, heapSize, max);
        }
    }
    // swap method adapted from H5 solution
    private static <T> void swap(List<T> list, int i, int j) {
		T temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
    }
    /**
     * The main method. Creates a list with 20,000 random numbers and then
     * sorts copies of the list with quicksort and heapsort. Then, it verifies if the 
     * list is actually sorted and also prints the number of comparisons made with each.
     * 
     * @param args we are not really referencing when running
     */
    public static void main(String[] args) {
        Random number = new Random();
        boolean sorted = true;
        List<Integer> unsorted = new ArrayList<>();        
        for (int i = 0; i < 20000; i++) {
            unsorted.add(number.nextInt());
        }
        List<Integer> listA = new ArrayList<>(unsorted);
        List<Integer> listB = new ArrayList<>(unsorted);
        System.out.println("Elements compared with Quicksort: " + quicksort(listA));
        for (int i = 0; i < listA.size()-1; i++ ) {
            if (listA.get(i) > listA.get(i+1)) {
                sorted = false;
                break;
            } 
        }
        if (sorted == true) {
        System.out.println("The quicksorted list is sorted");
        }
        else {System.out.println("The quicksorted list is NOT sorted");}
        
        // Sorting the list with Heapsort
        sorted = true;
        System.out.println("Elements compared with Heapsort: " + heapsort(listB));
        for (int i = 0; i < listB.size()-1; i++ ) {
        if (listB.get(i) > listB.get(i+1)) {
                sorted = false;
                break;
            } 
        }
        if (sorted == true) {
        System.out.println("The heapsorted list is sorted");
        }
        else {System.out.println("The heapsorted list is NOT sorted");}
    }
}
