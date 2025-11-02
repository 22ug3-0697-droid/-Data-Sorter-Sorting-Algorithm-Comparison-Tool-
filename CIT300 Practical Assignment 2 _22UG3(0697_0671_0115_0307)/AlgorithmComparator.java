import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Main class for the Sorting Algorithm Comparison Tool.
 * This class handles the UI, data generation, and comparison table.
 */
public class AlgorithmComparator {

    private static int[] data = null; // The dataset to be sorted
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Main method to run the program.
     */
    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        data = enterNumbersManually();
                        break;
                    case 2:
                        data = generateRandomNumbers();
                        break;
                    case 3:
                        performSort(new BubbleSort(), "Bubble Sort");
                        break;
                    case 4:
                        performSort(new MergeSort(), "Merge Sort");
                        break;
                    case 5:
                        performSort(new QuickSort(), "Quick Sort");
                        break;
                    case 6:
                        compareAllAlgorithms();
                        break;
                    case 7:
                        running = false;
                        // Updated exit message with team credits
                        System.out.println("\nThank you for using the Sorting Algorithm Comparison Tool!");
                        System.out.println("\n--- Development Team ---");
                        System.out.println("Vishwa(22UG3-0671)  - Bubble Sort Implementation");
                        System.out.println("Dilan(22UG3-0115)   - Merge Sort Implementation");
                        System.out.println("Rashmi(22UG3-0307)  - Quick Sort Implementation");
                        System.out.println("Mithun(22UG3-0697)  - UI, Data & System Integration");
                        System.out.println("\nGoodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
            System.out.println(); // Add a space for readability
        }
        scanner.close();
    }

    /**
     * Prints the main menu.
     */
    private static void printMenu() {
        System.out.println("--- Data Sorter: Sorting Algorithm Comparison Tool ---");
        if (data != null) {
            System.out.println("Current Dataset: " + data.length + " numbers.");
        } else {
            System.out.println("Current Dataset: None (Please load or generate data)");
        }
        System.out.println("------------------------------------------------------");
        System.out.println("1. Enter numbers manually");
        System.out.println("2. Generate random numbers");
        System.out.println("3. Perform Bubble Sort");
        System.out.println("4. Perform Merge Sort");
        System.out.println("5. Perform Quick Sort");
        System.out.println("6. Compare all algorithms (show performance table)");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Module for manual number entry (Mithun)
     */
    private static int[] enterNumbersManually() {
        System.out.println("Enter numbers separated by spaces (e.g., 5 2 9 1):");
        String line = scanner.nextLine();
        String[] parts = line.split("\\s+");
        int[] numbers = new int[parts.length];
        try {
            for (int i = 0; i < parts.length; i++) {
                numbers[i] = Integer.parseInt(parts[i]);
            }
            System.out.println("Data loaded: " + Arrays.toString(numbers));
            return numbers;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter only numbers separated by spaces.");
            return data; // Return the old data
        }
    }

    /**
     * Module for random number generation (Mithun)
     */
    private static int[] generateRandomNumbers() {
        try {
            System.out.print("Enter number of elements to generate: ");
            int size = scanner.nextInt();
            System.out.print("Enter maximum value: ");
            int maxVal = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (size <= 0) {
                System.out.println("Size must be greater than 0.");
                return data;
            }

            Random rand = new Random();
            int[] randomData = new int[size];
            for (int i = 0; i < size; i++) {
                randomData[i] = rand.nextInt(maxVal + 1);
            }
            System.out.println(size + " random numbers generated.");
            if (size <= 50) {
                System.out.println("Data: " + Arrays.toString(randomData));
            }
            return randomData;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid numbers.");
            scanner.nextLine(); // Clear the invalid input
            return data;
        }
    }

    /**
     * Helper to run and display a single sort (Mithun)
     */
    private static void performSort(SortingAlgorithm sorter, String name) {
        if (data == null) {
            System.out.println("No data to sort. Please load or generate data first (Option 1 or 2).");
            return;
        }

        System.out.println("Running " + name + "...");
        SortResult result = sorter.sort(data);

        System.out.println("--- " + name + " Results ---");
        System.out.println("Sorted Array:");
        printArray(result.sortedArray, 50); // Print max 50 elements
        System.out.printf("Execution Time: %.4f ms\n", result.executionTimeNano / 1_000_000.0);
        System.out.println("Operations (Steps): " + result.stepCount);
    }

    /**
     * Module for comparing all algorithms and displaying the table (Mithun)
     */
    private static void compareAllAlgorithms() {
        if (data == null) {
            System.out.println("No data to sort. Please load or generate data first (Option 1 or 2).");
            return;
        }
        
        System.out.println("Running all algorithms on dataset of " + data.length + " elements...");

        // Run each algorithm
        // Each .sort() method creates its own copy, so 'data' remains unmodified
        SortResult bubbleResult = new BubbleSort().sort(data);
        SortResult mergeResult = new MergeSort().sort(data);
        SortResult quickResult = new QuickSort().sort(data);

        // Display results table
        System.out.println("\n--- Performance Comparison Table ---");
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("| %-15s | %-20s | %-25s |\n", "Algorithm", "Time (ms)", "Operations (Steps)");
        System.out.println("-------------------------------------------------------------------");
        
        System.out.printf("| %-15s | %-20.4f | %-25d |\n", 
            "Bubble Sort", 
            bubbleResult.executionTimeNano / 1_000_000.0, 
            bubbleResult.stepCount);
            
        System.out.printf("| %-15s | %-20.4f | %-25d |\n", 
            "Merge Sort", 
            mergeResult.executionTimeNano / 1_000_000.0, 
            mergeResult.stepCount);
            
        System.out.printf("| %-15s | %-20.4f | %-25d |\n", 
            "Quick Sort", 
            quickResult.executionTimeNano / 1_000_000.0, 
            quickResult.stepCount);
            
        System.out.println("-------------------------------------------------------------------");
    }

    /**
     * Utility to print an array, limited to a certain number of elements.
     */
    private static void printArray(int[] arr, int limit) {
        if (arr.length == 0) {
            System.out.println("[]");
            return;
        }
        
        System.out.print("[");
        for (int i = 0; i < Math.min(arr.length, limit); i++) {
            System.out.print(arr[i] + (i < arr.length - 1 ? ", " : ""));
        }
        if (arr.length > limit) {
            System.out.print(" ... and " + (arr.length - limit) + " more");
        }
        System.out.println("]");
    }
}

/**
 * A simple class to hold the results of a sort.
 */
class SortResult {
    long executionTimeNano;
    long stepCount;
    int[] sortedArray;

    public SortResult(long executionTimeNano, long stepCount, int[] sortedArray) {
        this.executionTimeNano = executionTimeNano;
        this.stepCount = stepCount;
        this.sortedArray = sortedArray;
    }
}

/**
 * Interface for all sorting algorithms to implement.
 */
interface SortingAlgorithm {
    SortResult sort(int[] array);
}

/**
 * Implementation of Bubble Sort (Vishwa)
 */
class BubbleSort implements SortingAlgorithm {

    @Override
    public SortResult sort(int[] array) {
        // Create a copy to not modify the original 'data' array
        int[] arr = Arrays.copyOf(array, array.length);
        int n = arr.length;
        long steps = 0;
        
        long startTime = System.nanoTime();

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                steps++; // 1. Count the comparison
                if (arr[j] > arr[j + 1]) {
                    // Perform swap
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                    steps += 3; // 2. Count the 3 assignments for the swap
                }
            }
            if (!swapped) {
                break; // Optimization: If no swaps, array is sorted
            }
        }

        long endTime = System.nanoTime();
        
        return new SortResult(endTime - startTime, steps, arr);
    }
}

/**
 * Implementation of Merge Sort (Dilan)
 */
class MergeSort implements SortingAlgorithm {

    @Override
    public SortResult sort(int[] array) {
        int[] arr = Arrays.copyOf(array, array.length);
        long[] steps = {0}; // Use an array to pass by reference in recursion
        
        long startTime = System.nanoTime();
        mergeSortRecursive(arr, 0, arr.length - 1, steps);
        long endTime = System.nanoTime();

        return new SortResult(endTime - startTime, steps[0], arr);
    }

    private void mergeSortRecursive(int[] arr, int left, int right, long[] steps) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortRecursive(arr, left, mid, steps);
            mergeSortRecursive(arr, mid + 1, right, steps);
            merge(arr, left, mid, right, steps);
        }
    }

    private void merge(int[] arr, int left, int mid, int right, long[] steps) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = arr[left + i];
            steps[0]++; // 1. Count data copy to temp array
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[mid + 1 + j];
            steps[0]++; // 1. Count data copy to temp array
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            steps[0]++; // 2. Count the comparison
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            steps[0]++; // 3. Count the data write back to main array
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            steps[0]++; // 3. Count the data write
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            steps[0]++; // 3. Count the data write
            j++;
            k++;
        }
    }
}

/**
 * Implementation of Quick Sort (Rashmi)
 */
class QuickSort implements SortingAlgorithm {

    @Override
    public SortResult sort(int[] array) {
        int[] arr = Arrays.copyOf(array, array.length);
        long[] steps = {0}; // Use an array to pass by reference
        
        long startTime = System.nanoTime();
        quickSortRecursive(arr, 0, arr.length - 1, steps);
        long endTime = System.nanoTime();

        return new SortResult(endTime - startTime, steps[0], arr);
    }

    private void quickSortRecursive(int[] arr, int low, int high, long[] steps) {
        if (low < high) {
            int pi = partition(arr, low, high, steps);
            quickSortRecursive(arr, low, pi - 1, steps);
            quickSortRecursive(arr, pi + 1, high, steps);
        }
    }

    private int partition(int[] arr, int low, int high, long[] steps) {
        int pivot = arr[high];
        int i = (low - 1); // Index of smaller element

        for (int j = low; j < high; j++) {
            steps[0]++; // 1. Count the comparison
            if (arr[j] < pivot) {
                i++;
                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                steps[0] += 3; // 2. Count the 3 assignments for the swap
            }
        }

        // Swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        steps[0] += 3; // 3. Count the final pivot swap

        return i + 1;
    }
}