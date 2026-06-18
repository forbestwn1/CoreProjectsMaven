package abc;

public class SingltonClass {

	private static SingltonClass instance;
	
	private SingltonClass() {
		
	}
	
	public static SingltonClass getInstance() {
		synchonize(SingltonClass.class){
		    if(instance==null) {
		    	
			   instance = new SingltonClass();
		    }
		}
		return instance;
	}
	
	
	
	
}
