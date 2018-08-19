/** Summer Assignment 2 - Master of Science in Software Management Program
 * <h1>Mysterious Safeguards</h1>
 * Program find the mysterious lifeguard that has minimum impact on swimming pool time
 * coverage based on the the maximum coverage of each lifeguards.
 * 
 * Input Files			: 1.in, 2.in, 3.in, 4.in, 5.in, 6.in, 7.in, 8.in, 9.in, 10.in
 * Output File			: 1.out, 2.out. 3.out, 4.out, 5.out, 6.out, 7.out, 8.out, 9.out, 10.out
 * Methods			: main(String[] args)
 * 				  sort(int count, int[][] timings) 
 * 				  quickSort(int lower_index, int higher_index) 
 * 				  swap(int first, int second)
 * 				  max_coverage(int employee_count, int[][] employee_timings)
 * 
 * Exceptions			: IOException
 * 
 * IDE				: Eclipse IDE for Java Developers (4.8.0)
 * JDK version			: jdk1.8.0_172
 * 
 * @author	Sathish Marikani
 * @version	1.0
 * @since 	08-08-2018
 */

import java.util.Scanner;
import java.io.*;

public class MaximumCoverage 
{
	public static int employee_timings[][];
	
	// Start of main() Method
	/*
	 * This module reads one of the input files line-by-line and stores it in array.
	 * Then, it sorts the file, calls the max_coverage method to find the maximum 
	 * coverage and minimum impact lifeguard's time interval and finally writes the
	 * result to the output file.
	 * File Handling exceptions - IOException - is implemented.
	 */
	public static void main(String[] args) 
	{
		int max_coverage;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the input file name: ");
		String input_file_1 = scanner.next();
		String output_file_1 = input_file_1.substring(0, input_file_1.length() - 3) + ".out";
		try 
		{
			scanner = new Scanner(new File(input_file_1));
			int employee_count = Integer.parseInt(scanner.nextLine());
			int employee_timings[][] = new int[employee_count][2];
			while (scanner.hasNextInt()) 
			{
				for (int row = 0; row < employee_count; row++) 
				{
					for (int col = 0; col < 2; col++) 
					{
						employee_timings[row][col] = scanner.nextInt();
					}
				}
			}
			scanner.close();
			/*
			 * Sort the lifeguards time interval
			 */
			sort(employee_count, employee_timings);
			/*
			 * Find the maximum coverage value
			 */
			max_coverage = max_coverage(employee_count, employee_timings);
			/*
			 * Write the maximum coverage to the output file
			 */
			File file = new File(output_file_1);
			if(!file.exists())
			{
			  file.createNewFile();
			}
			PrintWriter pw1 = new PrintWriter(file);
			pw1.print(String.valueOf(max_coverage));
			pw1.close();			
		} 
		catch (IOException i) 
		{
			System.out.println("File does not exist");
		}
	}	// End of Method - main()
	
	/*
	 * This module calls the quickSort method if the array is not empty with the
	 * starting and ending index as parameters.
	 * timings - Timings to be sorted,
	 * count - size of the array.
	 */
	public static void sort(int count, int[][] timings) 
	{
		if (timings == null || count == 0) 
		{
			return;
		}
		employee_timings = timings;
		int length = count;
		quickSort(0, length - 1);
	}	// End of Method - sort()
	
	/*
	 * This module sorts the array by choosing the middle element in the array as pivot. 
	 * The values lesser than pivot comes before the pivot while the values greater 
	 * than pivot come after it. 
	 */
	private static void quickSort(int lower_index, int higher_index) 
	{
		int i = lower_index;
		int j = higher_index;
		int pivot_node = employee_timings[lower_index + (higher_index - lower_index) / 2][0];
		while (i <= j) 
		{
			while (employee_timings[i][0] < pivot_node) 
			{
				i++;
			}
			while (employee_timings[j][0] > pivot_node) 
			{
				j--;
			}
			if (i <= j) 
			{
				swap(i, j);
				i++;
				j--;
			}
		}
		/* 
		 * Recursively sort elements before partition and after partition
		 */
		if (lower_index < j)
			quickSort(lower_index, j);
		if (i < higher_index)
			quickSort(i, higher_index);
	}	// End of Method - quicksort()
	
	/* 
	 * This method swaps the input values.
	 */
	
	private static void swap(int first, int second) 
	{
		int temp1 = employee_timings[first][0];
		int temp2 = employee_timings[first][1];
		employee_timings[first][0] = employee_timings[second][0];
		employee_timings[first][1] = employee_timings[second][1];
		employee_timings[second][0] = temp1;
		employee_timings[second][1] = temp2;
	}	// End of Method - swap()

	public static int max_coverage(int employee_count, int[][] employee_timings) 
	{
		int max_coverage = Integer.MIN_VALUE;
		int employee_coverage = 0, employee_skip = 0, startpoint, endpoint, employee_range = 0, initial_startpoint = 0, initial_endpoint = 0;
		for (int i = 0; i < employee_count; i++) 
		{
			employee_coverage = 0;
			for (int j = 0; j < employee_count; j++) 
			{
				if (employee_skip == j)
					continue;
				else 
				{
					startpoint = employee_timings[j][0];
					endpoint = employee_timings[j][1];
					if (employee_coverage == 0) 
					{
						initial_startpoint = employee_timings[j][0];
						initial_endpoint = employee_timings[j][1];
						employee_range = endpoint - startpoint;
						employee_coverage = employee_coverage + employee_range;
					} 
					else 
					{
						if (startpoint < initial_endpoint && endpoint > initial_endpoint) 
						{
							employee_range = endpoint - initial_endpoint;
							initial_endpoint = endpoint;
							employee_coverage = employee_coverage + employee_range;
						} 
						else if (endpoint > initial_startpoint && endpoint > initial_endpoint) 
						{
							employee_range = endpoint - startpoint;
							initial_endpoint = endpoint;
							employee_coverage = employee_coverage + employee_range;
						} 
						else if (endpoint < initial_endpoint) 
						{
							// Do Nothing
						}
					}
				}
			}
			employee_skip++;
			if (max_coverage < employee_coverage) 
			{
				max_coverage = employee_coverage;
			}
		}
		//Display the maximum coverage and the minimum impact lifeguard's time interval
		System.out.println("Maximum Coverage: " + max_coverage);
		return max_coverage;
	}	// End of Method - max_coverage()
}	// End of class - MaximumCoverage
