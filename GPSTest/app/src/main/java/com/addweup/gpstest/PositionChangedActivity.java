package com.addweup.gpstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PositionChangedActivity extends AppCompatActivity {
    static final String TAG = "PCA";
    RecyclerView mRecyclerView;
    MyAdapter mAdapter;
    ArrayList<GPSLocation> mGPSItems = new ArrayList<>();
    LocationManager locationManager;
    String myProvider = LocationManager.PASSIVE_PROVIDER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_changed);

        initListView();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        setListener();
    }

    void setListener(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }else{
            Log.i(TAG, "Update listener");
            locationManager.removeUpdates(myLocationListener);
            locationManager.requestLocationUpdates(myProvider, 5 * 1000, 10, myLocationListener);
        }
    }


    void initListView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(mGPSItems);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.passive:
                myProvider = LocationManager.PASSIVE_PROVIDER;
                setListener();
                return true;
            case R.id.gps:
                myProvider = LocationManager.GPS_PROVIDER;
                setListener();
                return true;
            case R.id.network:
                myProvider = LocationManager.NETWORK_PROVIDER;
                setListener();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onFinish(View view){
        finish();
    }

    public GPSLocation parseLocation(Location location){
        GPSLocation gpsLocation = new GPSLocation();
        gpsLocation.origin_lati = location.getLatitude();
        gpsLocation.origin_long = location.getLongitude();
        gpsLocation.latitude = Double.toString(location.getLatitude());
        gpsLocation.longtitude = Double.toString(location.getLongitude());
        if(mGPSItems.size() > 0){
            GPSLocation prev = mGPSItems.get(mGPSItems.size()-1);
            float[] dis = new float[1];
            Location.distanceBetween(prev.origin_lati, prev.origin_long, gpsLocation.origin_lati, gpsLocation.origin_long, dis);
            gpsLocation.distance = Float.toString(dis[0]);
        }
        return gpsLocation;
    }

    LocationListener myLocationListener = new LocationListener(){
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "On Location Changed");
            final GPSLocation gpsLocation = parseLocation(location);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mGPSItems.add(gpsLocation);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.scrollToPosition(mGPSItems.size()-1);
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Toast.makeText(PositionChangedActivity.this, "Status Changed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String s) {
            Toast.makeText(PositionChangedActivity.this, "Provider Enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String s) {
            Toast.makeText(PositionChangedActivity.this, "Provider Disabled", Toast.LENGTH_SHORT).show();
        }
    };

    class GPSLocation{
        double origin_lati;
        double origin_long;
        String latitude = "1";
        String longtitude = "10";
        String distance;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<GPSLocation> mItems;
        private int lastPosition = -1;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView index;
            TextView latitude;
            TextView longtitude;
            TextView distance;
            ViewHolder(View v) {
                super(v);
                index = (TextView) v.findViewById(R.id.index);
                latitude = (TextView) v.findViewById(R.id.latitude);
                longtitude = (TextView) v.findViewById(R.id.longtitude);
                distance = (TextView) v.findViewById(R.id.distance);
            }
        }

        MyAdapter(List<GPSLocation> items) {
            mItems = items;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_string, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
            GPSLocation location = mItems.get(position);
            holder.index.setText(Integer.toString(position));
            holder.latitude.setText(location.latitude);
            holder.longtitude.setText(location.longtitude);
            holder.distance.setText(location.distance);
            setAnimation(holder.itemView, position);
        }

        private void setAnimation(View viewToAnimate, int position)
        {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition)
            {
                Animation animation = AnimationUtils.loadAnimation(PositionChangedActivity.this, android.R.anim.slide_in_left);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }
}
