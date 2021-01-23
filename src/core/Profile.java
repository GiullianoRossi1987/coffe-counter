package core;

import org.json.*;

public class Profile{
	private String name;
	private double cupVolume;
	private double hoursPerCup;
	private int metricSystem;
	private int indexInfo = -1;

	public Profile(String name, double cupvol, double hpc, int metric){
		this.name = name;
		this.cupVolume = cupvol;
		this.hoursPerCup = hpc;
		this.metricSystem = metric;
	}

	public Profile(JSONObject profile, int indexManager){
		this.name = profile.getString("name");
		this.hoursPerCup = profile.getDouble("hoursPerCup");
		this.cupVolume = profile.getDouble("cupvolume");
		this.metricSystem = profile.getInt("metricSystem");
		this.indexInfo = indexManager;
	}

	public String getName(){ return this.name; }

	public double getCupVolume(){ return this.cupVolume; }

	public double getHoursPerCup(){ return this.hoursPerCup; }

	public int getMetricSystem(){ return this.metricSystem; }

	public int getIndex(){ return this.indexInfo; }

	public void setName(String name){ this.name = name; }

	public void setCupVolume(double cupvol){ this.cupVolume = cupvol; }

	public void setHoursPerCup(double hpc){ this.hoursPerCup = hpc; }

	public void setMetricSystem(int ind){ this.metricSystem = ind; }

	public void setMetricSystem(JSONObject metricSystem) throws ConfigReader.GotConfigError, ConfigReader.ParseError{
		ConfigReader conf = new ConfigReader("src/core/config.json");
		JSONArray metrics = conf.getConfigurations().getJSONObject("mainvars").getJSONArray("metricSystems");
		for(int i = 0; i < metrics.length(); ++i){
			JSONObject ms = metrics.getJSONObject(i);
			if(ms.getString("name").equals(metricSystem.getString("name"))){
				this.metricSystem = i;
				break;
			}
		}
	}

	public double calcHoursPerCup(double cupvol){
		return (cupvol * this.hoursPerCup) / this.cupVolume;
	}

	public double calcCupsPerHour(double hours){
		return (hours * this.cupVolume) / this.hoursPerCup;
	}

	public JSONObject toJSONObject(){
		JSONObject self = new JSONObject();
		self.put("name", this.name);
		self.put("cupvolume", this.cupVolume);
		self.put("hoursPerCup", this.hoursPerCup);
		self.put("metricSystem", this.metricSystem);
		return self;
	}

	@Override 
	public String toString(){ return this.toJSONObject().toString(); }
}


