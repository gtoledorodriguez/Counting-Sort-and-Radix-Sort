package sort.Counting;

public class CountingSort {
	void sort(int arr[])
	{
		int n = arr.length;
		
		int counting[] = new int[100];  //array for counting variables
		
		int output[] = new int[n];     //array that will be the sorted array
		
		int x = 0;
		while(x<100){			// initializing array values to zero
			counting[x] = 0;
			x++;
		}
		
		int i = 0;
		while(i<n) {		  	//stores count of all nubmers
			++counting[arr[i]];
			i++;
		}
		
		for (int c = 1; c < 100; ++c) {  //changing count to actual position
			counting[c] += counting[c-1];
		}
		
		for (int v = 0; v < n; ++v) {    // filling values of output array
			output[counting[arr[v]]-1] = arr[v];
			--counting[arr[v]];
		}
		
		for (int b = 0; b < n; ++b) { //put sorted output array back into passed array
			arr[b] = output[b];
		}
		
	}


public static void main(String args[]) 
	{
    int randData[] = new int[100];				// initializing array of size 100
    
    for(int i = 0; i < randData.length; i++){				// creates random number between 1 and 100 and puts it into array for every element of randData
      int randNum = (int)(Math.random()*100);
      randData[i] = randNum;
    }
      CountingSort test = new CountingSort();			//tesing counting sort 
      
      test.sort(randData);					// calling sort on randData 
      
      System.out.println("Sorted array is :");			//displaying sorted data
      for(int q = 0; q < randData.length; ++q) {
    	  System.out.print(randData[q] + " ");
      }
 
	}	
}
