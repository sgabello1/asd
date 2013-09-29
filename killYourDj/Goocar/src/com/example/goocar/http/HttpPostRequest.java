package com.example.goocar.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

public class HttpPostRequest extends AsyncTask<String, Void, String>  
{
	TextView textView;
	Context context;
	public ProgressDialog dialog;
	
	public HttpPostRequest(Context c) {
		// TODO Auto-generated constructor stub
		
		this.context = c;
		dialog = new ProgressDialog(c);
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		
		super.onPreExecute();
		
		
		dialog = new ProgressDialog(context);
		dialog.setIndeterminate(true);
		dialog.setTitle("Getting location");
		dialog.show();
	} 
	
	
	

	@Override
	protected String doInBackground(String... params) 	
	{
		
		BufferedReader inBuffer = null;
		String url = "http://newapi.goocarclub.com/car/list";
		String result = "fail";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost request = new HttpPost(url);
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("operanda", params[0]));
			postParameters.add(new BasicNameValuePair("operandb", params[1]));
			postParameters.add(new BasicNameValuePair("answer", params[2]));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);

			request.setEntity(formEntity);
			HttpResponse httpResponse = httpClient.execute(request);
			inBuffer = new BufferedReader(
					new InputStreamReader(
							httpResponse.getEntity().getContent()));

			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String newLine = System.getProperty("line.separator");
			while ((line = inBuffer.readLine()) != null) {
				stringBuffer.append(line + newLine);
			}
			inBuffer.close();

			result = stringBuffer.toString();
			
		} catch(Exception e) {
			// Do something about exceptions
			result = e.getMessage();
		} finally {
			if (inBuffer != null) {
				try {
					inBuffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		

		
		return result; // this is the server answer
	}
	
	 
	protected void onPostExecute(String page)
	{    	

		dialog.dismiss();
	}
    
}
