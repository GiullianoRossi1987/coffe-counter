package core;
import org.json.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;

public class ConfigReader{
	// using protected just in case, 
	protected JSONObject configurations = null;
	protected boolean gotConfig = false;
	protected String fileLoaded = null;

	public static final String ERR_GCE = "There's a configurations file loaded already";
	public static final String ERR_CNL = "There's no configurations file loaded";
	public static final String ERR_PEE = "Can't load file \"%file\": Error -> \"%message\"";

	public class GotConfigError extends Exception{
		public GotConfigError(String msg){ super(msg); }
	}
	public class ConfigNotLoaded extends Exception{
		public ConfigNotLoaded(String msg){ super(msg); }
	}
	public class ParseError extends Exception{
		public ParseError(String file, String msg){ super("Can't load file \"" + file + "\": Error -> \"" + msg + "\""); }
	}

	public class AttachError extends Exception{
		public AttachError(String msg){ super(msg); }
	}

	public ConfigReader(String configFile) throws ConfigReader.GotConfigError, ConfigReader.ParseError{
		if(gotConfig) throw new GotConfigError(ERR_GCE);
		try{

			FileReader reader = new FileReader(configFile);
			BufferedReader bfr = new BufferedReader(reader);
			StringBuilder builder = new StringBuilder();
			String pos;
			while((pos = bfr.readLine()) != null) builder.append(pos);
			this.configurations = new JSONObject(builder.toString());
		}	
		catch(IOException ioe){ throw new ConfigReader.ParseError(configFile, ioe.toString()); }
		// catch(ParseException pe){ throw new ConfigReader.ParseError(configFile, pe.toString()); }
		this.fileLoaded = configFile;
		this.gotConfig = true;
	}

	public JSONObject getConfigurations(){ return this.configurations; }

	public String getFileLoaded(){ return this.fileLoaded; }

	public boolean gotFile(){ return this.gotConfig; }

	public JSONArray getProfiles() throws ConfigReader.ConfigNotLoaded{
		if(!gotConfig) throw new ConfigReader.ConfigNotLoaded(ERR_CNL);
		else return this.configurations.getJSONArray("profiles");
	}

	public JSONArray getMetrics() throws ConfigReader.ConfigNotLoaded{
		if(!gotConfig) throw new ConfigReader.ConfigNotLoaded(ERR_CNL);
		else return this.configurations.getJSONObject("mainvars").getJSONArray("metricSystems");
	}

	@Override
	public String toString(){ return this.configurations.toString(); }

	private void refresh() throws ConfigReader.ConfigNotLoaded, ConfigReader.ParseError{
		if(!gotConfig) throw new ConfigReader.ConfigNotLoaded(ERR_CNL);
		try{

			FileReader reader = new FileReader(this.fileLoaded);
			BufferedReader bfr = new BufferedReader(reader);
			StringBuilder builder = new StringBuilder();
			String pos;
			while((pos = bfr.readLine()) != null) builder.append(pos);
			this.configurations = new JSONObject(builder.toString());
		}
		catch(IOException ioe){ throw new ConfigReader.ParseError(this.fileLoaded, ioe.toString());}
	}

	public void dump() throws ConfigReader.ConfigNotLoaded, ConfigReader.ParseError{
		if(!gotConfig) throw new ConfigReader.ConfigNotLoaded(ERR_CNL);
		this.dumpConfigurations(this.configurations);
	}


	public void dumpConfigurations(JSONObject newConfig) throws ConfigReader.ConfigNotLoaded, ConfigReader.ParseError{
		if(!gotConfig) throw new ConfigReader.ConfigNotLoaded(ERR_CNL);
		try{
			FileWriter writer = new FileWriter(this.fileLoaded);
			writer.write(newConfig.toString());
			writer.flush();
		}
		catch(IOException ioe){ throw new ConfigReader.ParseError(this.fileLoaded, ioe.toString());}
		this.refresh();
	}

	

}
