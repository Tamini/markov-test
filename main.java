import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class main {

	// http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		boolean first = true;
		String content = readFile("input.txt", Charset.defaultCharset());
		content = content.replaceAll("[\n-\"]", " ");
		content = content.replaceAll("[,.!?;:]", " $0");
		String testIn = ". " + content + " ^";
		String[] splitTest = testIn.split(" ");
		//System.out.println(splitTest.length);
		HashMap<String, HashMap<String,Integer>> myMap;
		HashMap<String, Integer> counter;
		if (first){
		myMap = new HashMap<String, HashMap<String, Integer>>();
		counter = new HashMap<String, Integer>();
		}
		else {
			FileInputStream fin = new FileInputStream("hash.txt");
			ObjectInputStream oos = new ObjectInputStream(fin);
			myMap = (HashMap<String, HashMap<String, Integer>>) oos.readObject();
			oos.close();
			fin.close();
			FileInputStream fcount = new FileInputStream("count.txt");
			ObjectInputStream coos = new ObjectInputStream(fcount);
			counter = (HashMap<String, Integer>) coos.readObject();
			coos.close();
			fcount.close();
		}
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
		FileOutputStream fout = new FileOutputStream("hash.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(myMap);
		FileOutputStream fcount = new FileOutputStream("count.txt");
		ObjectOutputStream coos = new ObjectOutputStream(fcount);
		coos.writeObject(counter);
		//System.out.println(myMap.get(".").values().size());
		//System.out.println(myMap.values());
		// Create
		String next = ".";
		StringBuilder combined = new StringBuilder();
		while (!next.equals("^")){
			combined.append(next + " ");
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
		System.out.println(combined);
	}

}