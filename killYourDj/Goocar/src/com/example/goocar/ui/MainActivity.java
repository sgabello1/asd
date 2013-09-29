package com.example.goocar.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.goocar.Car;
import com.example.goocar.Dealers;
import com.example.goocar.R;

import com.example.goocar.http.HttpPostRequest;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements ActionBar.TabListener, FragmentCommunicator {

	String defaultResponse;		
	
	HttpPostRequest  post; //damn class for POST request
	String text; //JSON string format from 
    AppSectionsPagerAdapter mAppSectionsPagerAdapter; 
    
    Car car; //car obj
    Dealers dealers; //dealer obj
    ArrayList<Car> CarListItem = new ArrayList<Car>();
    
    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        defaultResponse = loadJSONFromAsset();

        //Singleton object
//        car = (Car) getApplicationContext();
        
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        
        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        
        
        //POST request
		post = new HttpPostRequest(this);
		
		post.execute(new String[] { "1", "3", "4" }); //it sends POST request along with these params
		
		try {
			text = post.get();  //waits for server response
			
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(text != null)
			try {
				createObj();  //create car&dealers objects
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0: //profile activity
                    
                    return new CarMaps();

                case 1: //statistics activity
                	
                	return new CarList();
                	
                	
                default://error zone
                	
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	
        //set tab titles
        	switch(position){
        	case 0 :
        		
        		return "Mappa";
        	case 1 :
        		
        		return "Lista ";
        	
        	default :
        		
        		return "Auch! Somethings went really wrong?!";
        }
    }

}

    public void createObj() throws JSONException{
    	
    	  
    	//error prevention
    	JSONObject errorPrevention = new JSONObject(text);
    	  //errorPrevention.get("message") == "ERROR"
    	if(text.contains("ERROR"))
    	{
    		//ok so server is screwed
    		
    		text = defaultResponse; //default response
    		
    	}
    		
    	JSONArray carJson = new JSONArray(text); //Convert String to arrayJSON Object
    	
    	for (int i = 0; i < carJson.length(); i++) {
            JSONObject carjsonObj = carJson.getJSONObject(i);
            
        car = new Car(carjsonObj.getString("lat"), carjsonObj.getString("lng"), carjsonObj.getString("UID"), 
        		carjsonObj.getString("marca"), carjsonObj.getString("modello"), carjsonObj.getString("motor"), 
        		carjsonObj.getString("targa"), carjsonObj.getString("image"), carjsonObj.getString("dealer"));
        
        CarListItem.add(car);
        
    	}	
    	
    }
    
	@Override
	public List<Car> updateFragment()  {
		// TODO Auto-generated method stub
		
		
		return CarListItem;
	}

	@Override
	public void fromActivitytoFragment() {
		// TODO Auto-generated method stub
		
	}

	public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("def.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

	
	}
