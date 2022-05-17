package BFSDFS;

import java.lang.reflect.Array;
import java.util.Arrays;

public class siblingSearch {
	// assume that it's not a sorted array, tc : O(N + N/2*log(N/2)) = O(N*log(N)), sc : constant
	// if it is sorted, then we ain't need to sort the array again so TC  would be O(N)
	public  int[] siblingsearch(int length, int[] array, int query) {
		// base case: if empty array or the query is the FIRST element
		if (length == 0 || query == array[0]) return new int[] {-1}; 
		
		// do a linear scan to find the query index
		int i;
		for (i = 0; i < length; i++) 
			if (array[i] == query) break;
		
		if (i == length) return new int[] {-1}; // edge cae: if query d.n.e
		
		int depth = power_of_two(i+1); // i+1 means to convert the index to 1-based
		
		// keep in mind these two indexes are 1-based, means end_of_layer can go from 1 to length
		int start_of_layer = (int) Math.pow(2, depth);
		int end_of_layer = start_of_layer * 2 - 1;
		
		end_of_layer = end_of_layer<=length ? end_of_layer : length; // adjust the end index
		if (start_of_layer == end_of_layer)  return new int[] {-1}; // edge cae: if query is the start index of any layer
			
		int[] first_part =Arrays.copyOfRange(array, start_of_layer-1, i);
		int[] second_part = Arrays.copyOfRange(array, i+1, end_of_layer); // skip the query i 
		
		int[] combined = concat(first_part, second_part);
		quickSort(combined, 0,combined.length-1);
		//Arrays.sort(combined); // sort that shit
		
		return combined;
	}

	private int power_of_two(int num) {
		int powers = 0;
		while (num != 1) {
			num/=2;
			powers++;
		}
		return powers;
	}
	
	// a powerful helper function that support both primitive / object array!
	private <T> T concat(T a, T b) {
		// first check if a and b are arrays
		if (!a.getClass().isArray() || !b.getClass().isArray()) {
			throw new IllegalArgumentException();
		}
		Class<?> resCompType;
		Class<?> aCompType = a.getClass().getComponentType();
		Class<?> bCompType = b.getClass().getComponentType();
		
		if (aCompType.isAssignableFrom(bCompType)) resCompType = aCompType;
		else if (bCompType.isAssignableFrom(aCompType)) resCompType = bCompType;
		else throw new IllegalArgumentException();
		
		int aLen = Array.getLength(a);
		int bLen = Array.getLength(b);
		
		@SuppressWarnings("unchecked")
		T result = (T) Array.newInstance(resCompType, aLen+bLen);
		
		System.arraycopy(a, 0, result, 0, aLen);
		System.arraycopy(b, 0, result, aLen, bLen);
		
		return result;
	}
	
	public static void main(String[] args) {
		 //int[] array = {1,2,3,4,5,6,7,8,9,10};
		 int[] array = {800, 459, 234,1000,-45,32,-898,100,20,30};
		int query  = 1000;
		int[] ans = new siblingSearch().siblingsearch(array.length, array, query);
		Arrays.stream(ans).forEach(a->System.out.print(a+" "));
	}
	
	
	/* The main function that implements QuickSort
    arr[] --> Array to be sorted,
    low --> Starting index,
    high --> Ending index
	*/
	static void quickSort(int[] arr, int low, int high){
		if (low < high) {
	        // pi is partitioning index, arr[p]
	        // is now at right place
			int pi = partition(arr, low, high);
			
			quickSort(arr, low, pi-1);
			quickSort(arr, pi+1, high);
		}
	}
	
	/* This function takes last element as pivot, places
	   the pivot element at its correct position in sorted
	   array, and places all smaller (smaller than pivot)
	   to left of pivot and all greater elements to right
	   of pivot */
	static int partition(int[] arr, int low, int high){
		int pivotIndex = high;
		int smallerBefore = low;
		int largerAfter = high - 1;
		
		while (smallerBefore <= largerAfter) {
			  if (arr[smallerBefore] <= arr[pivotIndex]) smallerBefore++;
			  else {
				  swap(arr, smallerBefore, largerAfter);
				  largerAfter--;
			  }
		}
		swap(arr, smallerBefore, pivotIndex);
		return smallerBefore;
	}

	//A utility function to swap two elements
	static void swap(int[] arr, int i, int j){
		 int temp = arr[i];
		 arr[i] = arr[j];
		 arr[j] = temp;
	}
}


// left child: 2*parent + 1
// right child: 2*parent + 2
//  parent : (child - 1) / 2

//class TreeNode {
//int val;
//TreeNode left;
//TreeNode right;
//TreeNode(int x) { val = x; }
//}
//
//private int left(int index) {
//return index*2 + 1;
//}
//
//private int right(int index) {
//return index*2 + 2;
//}
//
//private int parent(int index) {
//return (index - 1) / 2;
//}
//
//private void insert(TreeNode node) {
//
//int parent = parent(node);
//}
