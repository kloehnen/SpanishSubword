package debajoPalabra;

public class Affix {
	
	String Summary;
	Boolean Prefix;

	public Affix(){}
	
	public void setSummary(String inSummary){
		Summary = inSummary;
	}
	
	public void setPrefix(String inPrefix){
		if (inPrefix.equals("True")){
			Prefix = true;
		}
		if (inPrefix.equals("False")){
			Prefix = false;
		}
	}
		
	public String getSummary(){
		return Summary;
	}

	public Boolean getPrefix(){
		return Prefix;
	}

}
