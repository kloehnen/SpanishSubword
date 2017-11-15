package debajoPalabra;

import java.util.ArrayList;
import java.util.HashMap;

public class Subword {
	
	private static HashMap<String, HashMap<String,String>> summaryMapMaster;
	private static Affixes As;
	Integer startdx;
	Integer enddx;
	HashMap<String,String> currentMap;
	
	Subword(){
		summaryMapMaster = new HashMap<String, HashMap<String,String>>();
		As = new Affixes();
	}
	
	public String summarize(String token){
		currentMap = new HashMap<String,String>();
		String stripped = strip(token);
		if (!stripped.equals(token)){
			String stripped2 = strip(stripped);
			if (!stripped2.equals(stripped)){
				stripped = strip(stripped2);
			} else {
				stripped = stripped2;
			}
		}
		System.out.println(token);
		System.out.println(stripped);
		for (String k : currentMap.keySet()){
			System.out.println(k);
		}
		System.out.println();
		return "";
	}
	
	private String strip(String token){
		String stripped = null;
		startdx = null;
		enddx = null;
		ArrayList<String> matches = As.getMatches(token);
		for (String match : matches){
			String[] info = match.split("\t");
			String affix = info[0];
			currentMap.put(affix, info[1]);
			if (As.getPrefix(affix) == true){
				startdx = token.indexOf(affix.toLowerCase()) + affix.length();
			}
			if (As.getPrefix(affix) == false){
				enddx = token.lastIndexOf(affix.toLowerCase());
			}
		}
		if (startdx == null){
			startdx = 0;
		}
		if (enddx == null){
			enddx = token.length();
		}
		// Get root of word!
		try{
			stripped = token.substring(startdx,enddx);
		} catch (IndexOutOfBoundsException e) {
			if (startdx + enddx >= token.length() + 1){
				stripped = "";
			} else {
			stripped = token;
			}
		}
		return stripped;
	}
}
