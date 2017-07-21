package sales.pg.functions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sales.pg.DailyWork;
import sales.pg.R;
import sales.pg.adapters.Dealers;
import sales.pg.DealersRecylerAdapter;

public class DisplayRecylerview extends AppCompatActivity {
    ArrayList<Dealers> dealerses;
  RecyclerView recyclerview;
    DealersRecylerAdapter adapter;
    ProgressDialog progress;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recylerview);
        recyclerview= (RecyclerView)findViewById(R.id.recyclerview);
        progress = new ProgressDialog(DisplayRecylerview.this);
        progress.setMessage("Fetching data from server..");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
         if (internet()){
             new DisplayRecylerview.GetDelearnames().execute();
         }else {
             showalert("Please check your internet connection..","show");
         }
        dealerses = new ArrayList<Dealers>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DisplayRecylerview.this);
        recyclerview.setLayoutManager(layoutManager);

        adapter = new DealersRecylerAdapter(DisplayRecylerview.this,dealerses);

        recyclerview.addOnItemTouchListener(new DrawerItemClickListener());

        searchView = (SearchView)findViewById(R.id.mysearchview);
        //search(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });
    }

    private class DrawerItemClickListener implements RecyclerView.OnItemTouchListener {
        GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if(child != null && gestureDetector.onTouchEvent(e)) {
                int position = rv.getChildAdapterPosition(child);
                String xx = String.valueOf(position);

               /* SharedPreferences ss = getSharedPreferences("selected", Context.MODE_PRIVATE);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("delearname",ss.getString("dealername",""));
                returnIntent.putExtra("dealercode",ss.getString("dealercode",""));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                Toast.makeText(getBaseContext(),ss.getString("dealername",""),Toast.LENGTH_SHORT).show();*/
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    private class GetDelearnames extends AsyncTask<String, String, JSONArray> {
        private JSONArray json;
        ArrayList<NameValuePair> nameValuePairs;

        public GetDelearnames( ) {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONArray jsonObject) {
            super.onPostExecute(jsonObject);
            progress.dismiss();
            String dsd = String.valueOf(jsonObject.length());
           // Toast.makeText(getApplicationContext(),dsd,Toast.LENGTH_SHORT).show();
            for (int i=0;i<jsonObject.length();i++){
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                     dealerses.add(new Dealers(jsonObject1.getString("dealer_code"),jsonObject1.getString("dealer_name")));

                 } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            recyclerview.setAdapter(adapter);
        }

        @Override
        protected JSONArray doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();

            json = JSONParser.makeServiceCall("http://www.pg-iglobal.com/Arthmetic.asmx/getdealers", 1, nameValuePairs);
            //  json = JSONParser.makeServiceCall("http://timesofindia.indiatimes.com/rssfeeds/-2128936835.cms", 1, nameValuePairs);
            return json;
        }
    }

    public Boolean internet(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        return connected;
    }


    void showalert(String alert_msg, final String show) {
        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(DisplayRecylerview.this);
        alertDialogBuilder.setTitle("NGFCPL");
        // alertDialogBuilder.setIcon(R.drawable.aplogo);
        // set dialog message
        alertDialogBuilder.setMessage(alert_msg).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        // create alert dialog
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}
