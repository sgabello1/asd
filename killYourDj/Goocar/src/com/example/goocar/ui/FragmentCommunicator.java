package com.example.goocar.ui;

import java.util.List;

import org.json.JSONException;

import com.example.goocar.Car;

import android.widget.Toast;


	public interface FragmentCommunicator {

		public void fromActivitytoFragment();

		public List<Car> updateFragment() throws JSONException;

}