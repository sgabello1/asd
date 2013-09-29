package com.example.goocar.ui;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import com.example.goocar.Car;
import com.example.goocar.R;
import com.example.goocar.R.layout;

import android.app.Activity;
import android.app.ListActivity;
import android.sax.RootElement;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CarList extends ListFragment {

	FragmentCommunicator fragCommunicator;
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
        	fragCommunicator = (FragmentCommunicator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
	
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
		
		
		View rootView = inflater.inflate(R.layout.car_list, container, false);

		// Hashmap for ListView
					ArrayList<HashMap<String, String>> carList = new ArrayList<HashMap<String, String>>();

			    	List<Car> li = null;
					try {
						li = fragCommunicator.updateFragment();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	
			    	if(li != null){
			    		for(int i=0;i<li.size();i++){
			    			
			    			Car car = li.get(i);
			    			
			    			// creating new HashMap
			    			HashMap<String, String> map = new HashMap<String, String>();
			    			
			    			// adding each child node to HashMap key => value
			    			String marca = car.getMarca();
			    			String modello = car.getModello();
			    			String targa = car.getTarga();
			    			
			    			map.put("marca", marca);
			    			map.put("modello", modello);
			    			map.put("targa", targa);
			    			// adding HashList to ArrayList
			    			carList.add(map);
			    		}
			        
			    	}
			    	
					ListAdapter adapter = new SimpleAdapter(getActivity(), carList,
							R.layout.list_item,
							new String[] { "targa","marca", "modello"}, new int[] {
									R.id.targa, R.id.modello, R.id.motor });

					setListAdapter(adapter);
		
		
	    return rootView;};
		
	    public  void updateView() throws JSONException {
	    	
	    	

			
		}
}
