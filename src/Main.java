import org.json.*;
import core.ConfigReader;

public class Main{
	public static void main(String args[]){
		try{ ConfigReader testReader = new ConfigReader("src/core/config.json"); }
		catch(Exception e){ e.printStackTrace();}
		System.out.println("Works");
	}
}
