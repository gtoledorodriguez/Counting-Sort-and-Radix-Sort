package sort.Radix;

import sort.Counting.CountingSort;

public class RadixSort {

	public RadixSort() {
		// TODO Auto-generated constructor stub
	}
	
	
	int getMax(int arr[], int n) { //Gets the max value in array
        int max = arr[0]; 
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i]; 
            }
        }
        return max; 
    }
	
	// Does counting sort on the array according to the digit represented by exp
	void countSort(int arr[], int n, int exp) { // Can not use the one from the other file because we need 3 inputs
					   							// Will use the code from CountingSort as a base
		int counting[] = new int[100];
		
		int output[] = new int[n];
		
		int x = 0;
		while(x<100){
			counting[x] = 0;
			x++;
		}
		
		int i;
		
        for (i = 0; i < n; i++) { //counts the number of occurrences in counting[]
            counting[ (arr[i]/exp)%10 ]++;
        }
		
        for (i = 1; i < 10; i++) { // count will contain the real position of the digit
            counting[i] += counting[i - 1];
        }
		
        for (i = n - 1; i >= 0; i--){ //Builds the output array
            output[counting[ (arr[i]/exp)%10 ] - 1] = arr[i]; 
            counting[ (arr[i]/exp)%10 ]--; 
        } 
		
		for (int b = 0; b < n; ++b) { // put sorted output array back into passed array
			arr[b] = output[b];		  // contains sorted numbers according to current digit
		}
		
	}
	
	void radixSort(int[] arr) {
		int n = arr.length;
		int max = getMax(arr, n); //Used to find the max # to know how many digits it contains
		
		for (int exp = 1; max/exp > 0; exp *= 10) { // Do counting sort for every digit
            countSort(arr, n, exp); 				// exp is 10^i where i the the current digit number
		}
	}
	
	public static void main(String args[]) 
	{
    int randData[] = new int[100];				// initializing array of size 100
    
    for(int i = 0; i < randData.length; i++){				// creates random number between 1 and 100 and puts it into array for every element of randData
      int randNum = (int)(Math.random()*100);
      randData[i] = randNum;
    }
      RadixSort test = new RadixSort();
      
      test.radixSort(randData);
      
      System.out.println("Sorted array is :");
      for(int q = 0; q < randData.length; ++q) {
    	  System.out.print(randData[q] + " ");
      }
 
	}

}
