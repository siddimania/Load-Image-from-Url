package com.example.test;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

   public static final String URL = "http://static.comicvine.com/uploads/original/11116/111164870/3842465-1216439145-batma.png";
   ImageView imageView;

   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       imageView = (ImageView) findViewById(R.id.imageView);

       // Create an object for subclass of AsyncTask
       GetXMLTask task = new GetXMLTask();
       // Execute the task
       task.execute(new String[] { URL });
   }

   private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
       @Override
       protected Bitmap doInBackground(String... urls) {
           Bitmap map = null;
          
               map = downloadImage(urls[0]);
           
           return map;
       }

       // Sets the Bitmap returned by doInBackground
       @Override
       protected void onPostExecute(Bitmap result) {
           imageView.setImageBitmap(result);
       }

       // Creates Bitmap from InputStream and returns it
       private Bitmap downloadImage(String url) {
           Bitmap bitmap = null;
           InputStream stream = null;
           BitmapFactory.Options bmOptions = new BitmapFactory.Options();
           bmOptions.inSampleSize = 1;

           try {
               stream = getHttpConnection(url);
               bitmap = BitmapFactory.
                       decodeStream(stream, null, bmOptions);
               stream.close();
           } catch (IOException e1) {
               e1.printStackTrace();
           }
           return bitmap;
       }

       // Makes HttpURLConnection and returns InputStream
       private InputStream getHttpConnection(String urlString)
               throws IOException {
           InputStream stream = null;
           URL url = new URL(urlString);
           URLConnection connection = url.openConnection();

           try {
               HttpURLConnection httpConnection = (HttpURLConnection) connection;
               httpConnection.setRequestMethod("GET");
               httpConnection.connect();

               if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                   stream = httpConnection.getInputStream();
               }
           } catch (Exception ex) {
               ex.printStackTrace();
           }
           return stream;
       }
   }
}