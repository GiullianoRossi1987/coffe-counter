import org.json.*;
import core.ConfigReader;
import core.ProfileManager;
import core.Profile;

public class Main{
	public static void main(String args[]){
		try{ 
			// ConfigReader testReader = new ConfigReader("src/core/config.json"); 
			// ProfileManager profMan = new ProfileManager("src/core/config.json");
			Profile prf = new Profile("teste", 200.0, 6.0, 0);
			System.out.println(prf.calcHoursPerCup(100));
			prf.setName("novo_teste");
			System.out.println(prf);
		}
		catch(Exception e){ e.printStackTrace();}
		System.out.println("Works");
	}
}
