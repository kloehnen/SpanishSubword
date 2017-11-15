package debajoPalabra;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Affixes {
	
	HashMap<String,Affix> AffixMap = new HashMap<String,Affix>();
	ArrayList<String> OrderedAffixStrings;
	
	public Affixes(){
		BufferedReader inBr;
		FileInputStream inStream = null;
		String rawLine;
		try{
			// read in file
			inStream = new FileInputStream("/Users/pokea/Documents/Work/"
					+ "UofA/Current/MIS/AffixSimplification/Code/"
					+ "subwordSpanish/debajoPalabra/src/debajoPalabra/"
					+ "AffixDict1.txt");
			inBr = new BufferedReader(new InputStreamReader(inStream));
			while ((rawLine = inBr.readLine()) != null) {
				if(!rawLine.isEmpty()){
					if (!rawLine.startsWith("<")){
						 String[] lineInfo = rawLine.split("\t");
						 if (lineInfo.length == 3){
							 Affix A = new Affix();
							 A.setSummary(lineInfo[1]);
							 A.setPrefix(lineInfo[2]);
							 AffixMap.put(lineInfo[0], A);
						 }
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();				
		} finally {
			try {
				if (inStream != null){
					inStream.close();
				}
		    } catch (IOException ex) {  
				ex.printStackTrace();
			}
		}
		
		// Sort Affix Array by length
		OrderedAffixStrings = new ArrayList<>(AffixMap.keySet());
		OrderedAffixStrings.sort(Comparator.comparing(String::length).reversed());
		
	}
	
	public ArrayList<String> getMatches(String token){
		
		ArrayList<String> extractedSummary = new ArrayList<String>();
		String pMatch = null;
		String sMatch = null;
		for (String AffixString : OrderedAffixStrings){
			Affix CurrentA = AffixMap.get(AffixString);
			if(CurrentA.getPrefix() == true){
				if (token.toLowerCase().startsWith(AffixString.toLowerCase())){
					if (pMatch == null){
						pMatch = AffixString;
					}
				}
			}
			if(CurrentA.getPrefix() == false){
				if (token.toLowerCase().endsWith(AffixString.toLowerCase())){
					if (sMatch == null){
						sMatch = AffixString;
					}
				}
			}
		}
		if (pMatch != null){
			extractedSummary.add(pMatch + "\t" + AffixMap.get(pMatch).getSummary());
		}
		if (sMatch != null){
			extractedSummary.add(sMatch + "\t" + AffixMap.get(sMatch).getSummary());
		}
		return extractedSummary;
	}
	
	public Boolean getPrefix(String affixString){
		return AffixMap.get(affixString).getPrefix();
	}
}
