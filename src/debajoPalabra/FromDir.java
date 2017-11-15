package debajoPalabra;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class FromDir {
	
	private File[] listOfFiles = null;
	private static FrequencyFilter F;
	public Terms Ts = new Terms();
	public Subword Sw = new Subword();
	

	public FromDir(String dirPath,  
			StanfordCoreNLP pipeline, 
			Indexer I){
		listOfFiles = new File(dirPath).listFiles();
		I.reset(0);
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String docName = listOfFiles[i].getName();
				if (docName.endsWith("txt")) {
					I.add(0);
					Doc D = new Doc(
							dirPath + "/" + docName,
							pipeline,
							Ts,
							I);
				}
			}
		}
	}
	
	public void filter(){
		F = new FrequencyFilter(Ts, 1772474);
	}
	
	public void writeSummaries(){
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			fw = new FileWriter("/Users/pokea/Documents/"
					+ "Work/UofA/Current/MIS/AffixSimplification/"
					+ "Code/subwordSpanish/debajoPalabra/Output.txt");
			bw = new BufferedWriter(fw);
			
			HashSet<String> kSet = new HashSet<String>(Ts.getMap("1").keySet());
			for (String idx : kSet){
				String token = Ts.get(idx, "1");
				// Get the Subword Summary
				Sw.summarize(token);
				
				bw.write(idx + "\t" + token + "\n");
				
				//if (Ts.containsK(token, "3")){
					//if (F.contains(token)){
						//As.getSummary(token);
						//System.out.println(
						//		idx + "\t" +
						//		token + "\t" + 
						//		String.valueOf(F.getVal(token))
						//		);
					//}
				//}			
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
	}
}

