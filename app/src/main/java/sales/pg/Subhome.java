package sales.pg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sales.pg.functions.JSONParser;

public class Subhome extends Activity {
    Button button_login;
    EditText input_password, input_usename;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subhome);
        button_login = (Button) findViewById(R.id.button_login);
        input_usename = (EditText) findViewById(R.id.input_usename);
        input_password = (EditText) findViewById(R.id.input_password);

        final AlertDialog.Builder aler = new AlertDialog.Builder(Subhome.this);
        aler.setTitle("NGFCPL");
        aler.setIcon(getResources().getDrawable(R.drawable.appicon));


        Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        // Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        button_login.startAnimation(slideUp);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_usename.getText().toString().length() == 0) {
                    aler.setMessage("Enter username ");
                    aler.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    aler.show();
                } else if (input_password.getText().toString().length() == 0 || input_password.getText().toString().length() < 4) {
                    aler.setMessage("Enter Password ");
                    aler.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    aler.show();
                } else {
                  /*  Intent i = new Intent(Subhome.this,home.class);
                    startActivity(i);*/
                    progressDialog = new ProgressDialog(Subhome.this);
                    progressDialog.setTitle("Checking with server...");
                    progressDialog.show();
                      new Subhome.Mylatestnews(input_usename.getText().toString(),input_password.getText().toString()).execute();

                }

            }
        });

    }

    class Mylatestnews extends AsyncTask<String, String, JSONArray> {
        private JSONArray json;
        ArrayList<NameValuePair> nameValuePairs;
        String username, password;

        public Mylatestnews(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONArray jsonObject) {
            super.onPostExecute(jsonObject);
            progressDialog.dismiss();
//            Toast.makeText(getBaseContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
            if (jsonObject.length()>0){
                for (int i=0;i<jsonObject.length();i++){
                    try {
                        JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                        SharedPreferences.Editor s = getSharedPreferences("userdetails",MODE_PRIVATE).edit();
                        s.putString("userid",jsonObject1.getString("USERID"));
                        s.putString("username",jsonObject1.getString("USERNAME"));
                        s.putString("password",jsonObject1.getString("PASSWORD"));
                        Intent tt= new Intent(Subhome.this, home.class);
                        startActivity(tt);
                     } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }else {

            }

        }

        @Override
        protected JSONArray doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("username", username));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            json = JSONParser.makeServiceCall("http://www.pg-iglobal.com/Arthmetic.asmx/GetloginDetails", 1, nameValuePairs);
            //  json = JSONParser.makeServiceCall("http://timesofindia.indiatimes.com/rssfeeds/-2128936835.cms", 1, nameValuePairs);
            return json;
        }
    }

}
