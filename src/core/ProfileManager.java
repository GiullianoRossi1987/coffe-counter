package core;

import org.json.*;


public class ProfileManager extends ConfigReader{

	private JSONArray profiles = null;
	
	static class ProfileNotFound extends Exception{
		public ProfileNotFound(String profile){ super("Can't find profile \"" + profile + "\""); }
	}

	static class ProfileAlreadyExists extends Exception{
		public ProfileAlreadyExists(String profile){ super("The profile name \"" + profile + "\" already exists"); }
	}

	public ProfileManager(String configFile) throws ConfigReader.GotConfigError, ConfigReader.ConfigNotLoaded, ConfigReader.ParseError{
		super(configFile);
		this.profiles = super.getProfiles();
	}

	@Override public JSONArray getProfiles(){ return this.profiles; }

	private boolean checkProfileExists(String name) throws ConfigReader.ConfigNotLoaded{
		if(!gotConfig) throw new ConfigReader.ConfigNotLoaded(ConfigReader.ERR_CNL);
		for(int i = 0; i < this.profiles.length(); i++){
			JSONObject profile = this.profiles.getJSONObject(i);
			if(profile.getString("name").equals(name)) return true;
		}
		return false;
	}

	public void addProfile(JSONObject profile) throws ConfigReader.ConfigNotLoaded, ProfileManager.ProfileAlreadyExists,
			ConfigReader.ParseError{
		if(!gotConfig) throw new ConfigReader.ConfigNotLoaded(ConfigReader.ERR_CNL);
		if(this.checkProfileExists(profile.getString("name"))) 
			throw new ProfileManager.ProfileAlreadyExists(profile.getString("name"));
		this.profiles.put(profile);
		this.configurations.put("profiles", this.profiles);
		this.dump();
	}

	public void addProfile(String name, double cupVol, int metric, double hpc) throws ConfigReader.ConfigNotLoaded, ProfileManager.ProfileAlreadyExists,
			ConfigReader.ParseError{
		if(!gotConfig) throw new ConfigReader.ConfigNotLoaded(ConfigReader.ERR_CNL);
		if(this.checkProfileExists(name)) 
			throw new ProfileManager.ProfileAlreadyExists(name);
		JSONObject newProfile = new JSONObject();
		newProfile.put("name", name);
		newProfile.put("cupvolume", cupVol);
		newProfile.put("metricSystem", metric);
		newProfile.put("hoursPerCup", hpc);
		this.addProfile(newProfile);
	}

	private int indexOf(String name) throws ConfigReader.ConfigNotLoaded, ProfileManager.ProfileNotFound{
		if(!this.gotConfig) throw new ConfigReader.ConfigNotLoaded(ConfigReader.ERR_CNL);
		int index = 0;
		for(int i = 0; i < this.profiles.length(); ++i){
		 	JSONObject prof = this.profiles.getJSONObject(i);
		 	if(prof.getString("name").equals(name)){
		 		index = i;
		 		break;
			}
		}
		return index;
	}

	public void delProfile(String name) throws ConfigReader.ConfigNotLoaded, ProfileManager.ProfileNotFound{
		if(!gotConfig) throw new ConfigReader.ConfigNotLoaded(ConfigReader.ERR_CNL);
		if(!this.checkProfileExists(name))
			throw new ProfileManager.ProfileNotFound(name);
		else this.profiles.remove(this.indexOf(name));
	}

	public void chProfile(String name, JSONObject newData) throws ConfigReader.ConfigNotLoaded, ProfileManager.ProfileNotFound{
		if(!this.gotConfig) throw new ConfigReader.ConfigNotLoaded(ConfigReader.ERR_CNL);
		if(!this.checkProfileExists(name))
			throw new ProfileManager.ProfileNotFound(name);
		else this.profiles.put(this.indexOf(name), newData);
	}

	public static JSONObject parseData(String name, double cupVol, int metric, double hpc){
		JSONObject parsed = new JSONObject();
		parsed.put("name", name);
		parsed.put("cupvolume", cupVol);
		parsed.put("metricSystem", metric);
		parsed.put("hoursPerCup", hpc);
		return parsed;
	}

	public JSONObject getProfile(String name) throws ConfigReader.ConfigNotLoaded, ProfileManager.ProfileNotFound {
		if(!this.gotConfig) throw new ConfigReader.ConfigNotLoaded(ConfigReader.ERR_CNL);
		if(!this.checkProfileExists(name)) throw new ProfileManager.ProfileNotFound(name);
		int index = this.indexOf(name);
		return this.profiles.getJSONObject(index);
	}

	public Profile getProfileObj(String name) throws ConfigReader.ConfigNotLoaded, ProfileManager.ProfileNotFound {
		if(!this.gotConfig) throw new ConfigReader.ConfigNotLoaded(ConfigReader.ERR_CNL);
		if(!this.checkProfileExists(name)) throw new ProfileManager.ProfileNotFound(name);
		int index = this.indexOf(name);
		return new Profile(this.profiles.getJSONObject(index), index);
	}


	// public Profile getProfile(String name) throws

}
