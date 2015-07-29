import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class main {

	public static void main(String[] args) {
		String testIn = ". ^";
		String[] splitTest = testIn.split(" ");
		//System.out.println(splitTest.length);
		HashMap<String, HashMap<String,Integer>> myMap = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> counter = new HashMap<String, Integer>();
		for (int i = 0; i<splitTest.length-1; i++){
			HashMap<String, Integer> tempMap;
			if (!myMap.containsKey(splitTest[i])){
				tempMap = new HashMap<String, Integer>();
				tempMap.put(splitTest[i+1], 1);
				counter.put(splitTest[i], 0);
			}
			else if (myMap.get(splitTest[i]).containsKey(splitTest[i+1])){
				tempMap = myMap.get(splitTest[i]);
				tempMap.put(splitTest[i+1], tempMap.get(splitTest[i+1]) + 1);
			}
			else {
				tempMap = myMap.get(splitTest[i]);
				tempMap.put(splitTest[i+1], 1);
				//System.out.println (splitTest[i] + "_" + splitTest[i+1]);
			}
			myMap.put(splitTest[i], tempMap);
			int curVal = counter.get(splitTest[i]);
			counter.put(splitTest[i], curVal+1);
		}
		//System.out.println(myMap.get(".").values().size());
		//System.out.println(myMap.values());
		// Create
		String next = ".";
		while (!next.equals("^")){
			System.out.print(next + " ");
			HashMap<String, Integer> innerMap = myMap.get(next);
			int numVals = counter.get(next);
			//System.out.println(innerMap.keySet());
			//System.out.print (numVals);
			int randRes = (int) (Math.random() * numVals);
			//System.out.println (randRes);
			for (Map.Entry<String, Integer> entry : innerMap.entrySet()){
				randRes -= entry.getValue();
				//System.out.print ("(" + entry.getKey() + ")");
				if (randRes <= 0){
					next = entry.getKey();
					break;
				}
			}
		}
	}

}
