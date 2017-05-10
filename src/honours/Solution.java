package honours;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class Solution {

	static List<Vertex> solution ;
	static List<Vertex> solutionBaseline ;
	private static File speedfile;
	private static File category;
	
	public static void main(String [] args){
		List<String> fileNames = new ArrayList<String>();

		int i=2;
		while(i < args.length){
			fileNames.add(args[i]);
			i++;
		}
		runDataset(args[0], args[1], fileNames, Dataset.VERBEECK, "random");
	}

	/**
	 * 
	 * @param speedFileAddress: this is the location /name of the speed file
	 * @param categoryAddress: the location (/name )of the category file
	 * @param datafileAddress: the location of the first datafile
	 * @param numFiles: how many more files should be here
	 */
	private static void runDataset(String speedFileAddress, String categoryAddress, List<String> datafileAddress, Dataset dset, String hueristic){
		speedfile = new File(speedFileAddress);
	    category = new File(categoryAddress);
	    Dataset data = dset;
	    
	    System.out.println("------------------------------------------------------------------------------");
	    System.out.println("Instance          Existing total score            Total score of NN");
	    System.out.println("------------------------------------------------------------------------------");
	    
	    int i=1;
	    for(String dataf :datafileAddress){
	    	
	    	//run NN as a baseline
	    	int NN = createRoute(dataf, dataf,  dset, "NNT");
	    	//run the tested heuristic
	    	int H = createRoute(dataf, dataf, dset, "random");
	    	//print out the score
	    	
		    System.out.println(i+":                          "+H+"                       "+NN);
		    	i++;
	    }
	}
	/**
	 * This creates a route using a HEURISTIC and a set of files.
	 */
	private static int createRoute(String datafileAddress, String dataf,  Dataset dset, String Hueristic){
		//set up the problem with the files.
		File datafile = new File(dataf);
		Problem test = new Problem(speedfile, category, datafile, dset);
		
		Heuristic testH = new Heuristic();
		solution = new ArrayList<Vertex>();
		
		double endTime = (test.getTmax()+test.startTime);
		Vertex current = test.startVertex();
		solution.add(test.startVertex());
		test.vertices().remove(test.startVertex());
		test.vertices().remove(test.endVertex());
		double currentTime= 7.0;//the day starts at 7am
		//System.out.println(test.finishTime);
		
		
		//while there is still time left, pick the next vertex for solution using hueristic
		while(endTime > currentTime && !test.vertices().isEmpty()){
			Vertex next;
			if(Hueristic.equals("NNT")){
				next = testH.returnNearestBytravelTime(test, current, currentTime);
			} else if(Hueristic.equals("random")){
				next = testH.returnRandom(test, current, currentTime);
			} else{
				System.out.println("no matching heuristic known...");
				return -100;//throw an error?
			}

			//System.out.println(currentTime +" - "+test.travelTime(current, next, currentTime) +" - "+solution.get(solution.size()-1) + " - "+current + " - "+score(solution));
			double nextTime =currentTime + test.travelTime(current, next, currentTime);
			//System.out.println(currentTime +test.travelTime(current, next, currentTime) +test.travelTime(next, test.endVertex(), nextTime)+" "+endTime);

			if((currentTime + test.travelTime(current, next, currentTime) + test.travelTime(next, test.endVertex(), nextTime)) > endTime){
				currentTime += test.travelTime(current, test.endVertex(), currentTime);
				//System.out.println("breaking out"+ currentTime);
				solution.add(test.endVertex());
				return score(solution);
			}
			currentTime+= test.travelTime(current, next, currentTime);
			solution.add(next);
			//System.out.println(next.score(0)+"/"+score(solution) +" ("+next.xcord() + ","+ next.ycord()+")"+test.travelTime(current, next, currentTime));
			test.vertices().remove(next);
			current = next;
		} 
		solution.add(test.endVertex());

		return score(solution);
	}
	
	private static void print(List<Vertex> solutionList) {
		for(Vertex v: solutionList){
			System.out.print(v +", ");
		}
		
	}

	
	private static int score(List<Vertex> solution) {
		
		int score=0;
		for(Vertex v: solution){
			score += v.score(0);//single objective?
		}
		
		return score;
	}


	
}
