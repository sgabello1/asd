package com.example.goocar;

import android.app.Application;

public class Car extends Application{
	
	String lat;
	String lng;
	String UID;
	String marca;
	String modello;
	String motor;
	String targa;
	String image;
	String dealer;
	

	public Car(String lt,String ln, String u, String ma, String mo, String mot, String tr, String im, String dl ) {
		// TODO Auto-generated constructor stub
		
		lat = lt;
		lng = ln;
		UID = u;
		marca = ma;
		modello = mo;
		motor = mo;
		targa = tr;
		image = im;
		dealer = dl;
	}
	
public String getLat(){
	
	return lat;
}

public String getLng(){
	
	return lng;
}

public String getUid(){
	
	return UID;
}

public String getMarca(){
	
	return marca;
}

public String getModello(){
	
	return modello;
}

public String getMotor(){
	
	return motor;
}

public String getTarga(){
	
	return targa;
}
public String getImage(){
	
	return image;
}

public String getDealer(){
	
	return dealer;
}

}