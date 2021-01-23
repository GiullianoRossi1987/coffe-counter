package core;
import org.json.*;

public class MetricSys{
	private String volume;
	private String weight;
	private String name;

	public MetricSys(String volume, String weight, String name){
		this.volume = volume;
		this.weight = weight;
		this.name = name;
	}

	public MetricSys(JSONObject metric){
		this.volume = metric.getString("volume");
		this.weight = metric.getString("weight");
		this.name = metric.getString("name");
	}

	public String getVolume(){ return this.volume; }

	public String getWeight(){ return this.weight; }

	public String getName(){ return this.name; }

	public void setVolume(String vol){ this.volume = vol; }

	public void setWeight(String wei){ this.weight = wei; }

	public void setName(String name){ this.name = name; }

	public JSONObject toJSONObject(){
		JSONObject parsed = new JSONObject();
		parsed.put("name", this.name);
		parsed.put("volume", this.volume);
		parsed.put("weight", this.weight);
		return parsed;
	}

	@Override
	public String toString(){ return this.toJSONObject().toString(); }

}
