package vn.dise.instagramphotoviewer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import vn.dise.instagramphotoviewer.R;
import vn.dise.instagramphotoviewer.adapters.InstagramPhotoAdapter;
import vn.dise.instagramphotoviewer.models.InstagramPhoto;

public class PhotoActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    //public static final String ACCESS_TOKEN = "807659949.ef9785e.ba2ea620f8374f739a3ec50a24a6a5cc";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotoAdapter aPhoto;
    AlphaInAnimationAdapter animationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        photos = new ArrayList<>();
        aPhoto = new InstagramPhotoAdapter(this,photos);
        animationAdapter = new AlphaInAnimationAdapter(aPhoto);
        ListView lvPhoto = (ListView)findViewById(R.id.lvPhoto);
        animationAdapter.setAbsListView(lvPhoto);
        lvPhoto.setAdapter(aPhoto);
        fetchPhoto();

    }
    public void fetchPhoto() {

       // String url = "https://api.instagram.com/v1/media/popular?access_token=" + ACCESS_TOKEN;
        String url = "https://api.instagram.com/v1/media/popular?&client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Iterate each of the photo items and decode the item into  a java object
                JSONArray photoJSON = null;
                try {
                    photoJSON = response.getJSONArray("data");
                    //iterate array of posts
                    for (int i = 0; i < photoJSON.length(); i++) {
                        JSONObject pJson = photoJSON.getJSONObject(i);
                        //decode  the attribute of the json into a data model
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.userName = pJson.getJSONObject("user").getString("username");
                        if(pJson.getJSONObject("caption") != null)
                            photo.caption = pJson.getJSONObject("caption").getString("text");
                        photo.imageUrl = pJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageProfileUrl = pJson.getJSONObject("user").getString("profile_picture");
                        /*photo.imageHeight = pJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");*/
                        photo.likesCount = pJson.getJSONObject("likes").getInt("count");
                        photo.timeStamp = pJson.getString("created_time");
                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aPhoto.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("DEBUG", responseString);
            }
        });
    }

}
