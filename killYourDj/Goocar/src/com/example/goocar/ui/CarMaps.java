package com.example.goocar.ui;



import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.goocar.Car;
import com.example.goocar.R;
import com.example.goocar.R.id;
import com.example.goocar.R.layout;
import com.example.goocar.http.HttpPostRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CarMaps extends Fragment  {

	// Google Map
    private GoogleMap googleMap;
    MapView mapView;
    
    // for debugging mode - response from server and so on
	String	whatToSay = "wait for servers response";
	HttpPostRequest  post;
	TextView postResult;
	View rootView;
	
	
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
	
	@SuppressWarnings("unchecked")
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
		
		//view
		rootView = inflater.inflate(R.layout.car_maps, container, false);
		
		//object view for map visualation - create
		mapView = (MapView) rootView.findViewById(R.id.mapView);
		mapView.onCreate(savedInstanceState);
					
		
		try {
			updateView(); // update view - this is callable by fragment communicatior interface when you want to update the view
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	    return rootView;
	    };
		
	    public  void updateView() throws JSONException {
	    	
	    	//it takes the list of objects from the mainactivity
	    	List<Car> li = fragCommunicator.updateFragment();
	    	
	    	//the list must be not null
	    	if(li != null){
	    	
	        
	        try{
	        	
	        	// Loading map
	            initilizeMap();
	         // Changing map type
	         			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	         			// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	         			// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	         			// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
	         			// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

	         			// Showing / hiding your current location
	         			googleMap.setMyLocationEnabled(true);

	         			// Enable / Disable zooming controls
	         			googleMap.getUiSettings().setZoomControlsEnabled(false);

	         			// Enable / Disable my location button
	         			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

	         			// Enable / Disable Compass icon
	         			googleMap.getUiSettings().setCompassEnabled(true);

	         			// Enable / Disable Rotate gesture
	         			googleMap.getUiSettings().setRotateGesturesEnabled(true);

	         			// Enable / Disable zooming functionality
	         			googleMap.getUiSettings().setZoomGesturesEnabled(true);

	         			// lets place all markers from car list 
	         			for (int i = 0; i < li.size(); i++) {
	         				
	         				Car car = li.get(i); //gets element i from car list 
	         				
	         				double lat = Double.valueOf(car.getLat());
	         				double lng = Double.valueOf(car.getLng());
	         				// Adding a marker
	         				MarkerOptions marker = new MarkerOptions().position(
	         						new LatLng(lat, lng));       	

	         				googleMap.addMarker(marker);

	         				// Move the camera to last position with a zoom level
	         				if (i == li.size() -1 ) {
	         					CameraPosition cameraPosition = new CameraPosition.Builder()
	         							.target(new LatLng(lat,
	         									lng)).zoom(15).build();

	         					googleMap.animateCamera(CameraUpdateFactory
	         							.newCameraPosition(cameraPosition));
	         				}
	         			}
	        	
	        	
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
	    	}
	    	
		}
	
	    private void initilizeMap() {
	        if (googleMap == null) {
	             	

	    		// Gets to GoogleMap from the MapView and does initialization stuff
	    		googleMap = mapView.getMap();
	    		
	     
	    		// Needs to call MapsInitializer before doing any CameraUpdateFactory calls
	    		try {
	    			MapsInitializer.initialize(this.getActivity());
	    		} catch (GooglePlayServicesNotAvailableException e) {
	    			e.printStackTrace();
	    		}
	            
	            // check if map is created successfully or not
	            if (googleMap == null) {
	                Toast.makeText(rootView.getContext(),
	                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
	                        .show();
	            }
	        }
	    }
	    
	    @Override
	    public void onResume() {
	    	// TODO Auto-generated method stub
	    	super.onResume();
	    	mapView.onResume();
	    	  }
	    
	    @Override
	    public void onPause() {
	    	// TODO Auto-generated method stub
	    	super.onPause();
	    	mapView.onPause();
	    }
	    
	    @Override
	    public void onDestroy() {
	    	// TODO Auto-generated method stub
	    	super.onDestroy();
	    	mapView.onDestroy();
	    }
	    
	    @Override
	    public void onLowMemory() {
	    	// TODO Auto-generated method stub
	    	super.onLowMemory();
	    	mapView.onLowMemory();
	    }
}
