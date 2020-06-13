import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<int[]> cityList = new ArrayList<int[]>();
	
		// Read input file line by line by using scanner
		try {
			Scanner scanner = new Scanner(new File("./inputs/example-input-1.txt"));
			while (scanner.hasNextLine()) {
				String[] arr = scanner.nextLine().split(" ");
				cityList.add(new int[] {Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2])});
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		 int V = 4;  // Number of vertices in graph 
	     int E = 5;  // Number of edges in graph 
	     graph graph = new graph(V, E); 
		
	}

}
