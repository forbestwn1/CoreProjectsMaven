package abc;

public class ForInterview {

	
	public static void main(String[] agus) {
		
		String input = "";
		
		char[] chars = input.toCharArray();
		
		int i = 0;
		int j = chars.length-1;
		
		boolean out = true;
		while(j>i) {
			if(chars[i]!=chars[j]) {
				out = false;
				break;
			}
			
			i++;
			j--;
		}
		
		
		
	}
	
	
	
}
