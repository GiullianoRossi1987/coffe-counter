package desktop;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import core.ProfileManager;
import core.Profile;
import core.ConfigReader.ConfigNotLoaded;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import org.json.*;


public class ProfileConfig extends JFrame{
	private Profile selectedProfile;
	private ProfileManager mainManager;
	private List<Profile> choices = new ArrayList<Profile>();

	// Components
	private JLabel profileLabel;
	private JComboBox profileSelector;
	private JTextField jtf_Name;
	private JTextField jtf_cupVol;
	private JTextField jtf_hpc;
	private JComboBox jcb_metric;

	private JLabel jl_name;
	private JLabel jl_cupVol;
	private JLabel jl_hpc;
	private JLabel jl_metric;

	public static int indexOf(String pool[], String needle){
		int index = -1;
		for(int i = 0; i < pool.length; ++i){
			if(pool[i] == needle){
				index = i;
				break;
			}
		}
		return index;
	}

	private String[] getMetricsSys(ProfileManager man) throws ConfigNotLoaded{
		String[] data = new String[man.getMetrics().length()];
		for(int i = 0; i < man.getMetrics().length(); ++i)
			data[i] = man.getMetrics().getJSONObject(i).getString("name");
		return data;
	}

	public ProfileConfig(Profile selected){
		try{
			mainManager = new ProfileManager("src/core/config.json");
			this.choices = mainManager.getObjAllProfiles();
			String names[] = new String[this.choices.size()];
			for(int p = 0; p < this.choices.size(); ++p)
				names[p] = this.choices.get(p).getName();
			this.profileSelector = new JComboBox(names);
			this.profileSelector.addItemListener(new ItemListener(){
				public void itemStateChanged(ItemEvent e){
					loadProfile(profileSelector.getSelectedItem().toString());
				}
			});
			this.jcb_metric = new JComboBox(this.getMetricsSys(mainManager));
			this.jcb_metric.setBounds(10, 120, 100, 30);
			this.add(this.jcb_metric);
					
		}
		catch(Exception e){ e.printStackTrace(); }
		this.selectedProfile = selected;
		
		this.profileLabel = new JLabel("Selected profile is: " + selected.getName());
		this.profileLabel.setBounds(100, 100, 300, 30);
		this.profileSelector.setBounds(0, 20, 250, 20);

		this.jl_name = new JLabel("Name: ");
		this.jl_cupVol = new JLabel("Cup Volume / Cup Weight: ");
		this.jl_hpc = new JLabel("Hours Per Cup (HPC): ");
		this.jl_metric = new JLabel("Metric System used: ");





		this.add(profileSelector);
		this.add(profileLabel);
		this.setSize(400, 400);
		this.setLayout(null);
		// this.setVisible(true);
	}

	public void loadProfile(Profile profile){
		this.profileLabel.setText("Selected profile is: " + profile.getName());
		// int id = indexOf(
		// this.profileSelector.setSelectedItem(profile.getName());
	}

	public void loadProfile(String name){
		this.profileLabel.setText("Selected profile is: " + name);
		// this.profileSelector.setSelectedItem(name);
	}

}
