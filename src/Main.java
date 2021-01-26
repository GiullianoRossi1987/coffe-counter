import org.json.*;
import core.ConfigReader;
import core.ProfileManager;
import core.Profile;
import desktop.ProfileConfig;


public class Main{
	public static void main(String args[]){
		try{ 
			// ConfigReader testReader = new ConfigReader("src/core/config.json"); 
			// ProfileManager profMan = new ProfileManager("src/core/config.json");
			ProfileManager Pman = new ProfileManager("src/core/config.json");
			Profile sel = Pman.getProfileObj("default");
			ProfileConfig pc = new ProfileConfig(sel);
			pc.show();
		}
		catch(Exception e){ e.printStackTrace();}
		// System.out.println("Works");
	}
}
