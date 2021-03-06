package sales.pg;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import sales.pg.functions.JSONParser;

public class Expenses extends Activity implements View.OnClickListener {
    EditText datepicker,route,vehicletype,nokms,rateofkms,dailyfuel,dailyallowance,otherexpenses,remarks;
    ImageView back;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button submit_expenses;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses);
        back = (ImageView) findViewById(R.id.back);
        datepicker = (EditText) findViewById(R.id.datepicker);
        route = (EditText) findViewById(R.id.route);
        vehicletype = (EditText) findViewById(R.id.vehicletype);
        nokms = (EditText) findViewById(R.id.nokms);
        rateofkms = (EditText) findViewById(R.id.rateofkms);
        dailyfuel = (EditText) findViewById(R.id.dailyfuel);
        dailyallowance = (EditText) findViewById(R.id.dailyallowance);
        otherexpenses = (EditText) findViewById(R.id.otherexpenses);
        remarks = (EditText) findViewById(R.id.remarks);

        submit_expenses = (Button) findViewById(R.id.submit_expenses);
        submit_expenses.setOnClickListener(this);
        datepicker.setOnClickListener(this);

        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_expenses:
                /*if (datepicker.getText().toString().length() > 0) {
                    *//*final AlertDialog.Builder aler = new AlertDialog.Builder(Expenses.this);
                    aler.setTitle("NGFCPL");
                    aler.setIcon(getResources().getDrawable(R.drawable.appicon));
                    aler.setMessage("Sucessfully Created Record  ");
                    aler.show();*//*
                    progressDialog =new ProgressDialog(Expenses.this);
                    progressDialog.setTitle("Checking with server...");
                    progressDialog.show();



                } else {
                    final AlertDialog.Builder aler = new AlertDialog.Builder(Expenses.this);
                    aler.setTitle("NGFCPL");
                    aler.setIcon(getResources().getDrawable(R.drawable.appicon));
                    aler.setMessage("Please Fill Atleast One Entity  ");
                    aler.show();
                }*/

                progressDialog =new ProgressDialog(Expenses.this);
                progressDialog.setTitle("Checking with server...");
                progressDialog.show();
                new Expenses.Mylatestnews(datepicker.getText().toString(),route.getText().toString()
                        ,vehicletype.getText().toString(),nokms.getText().toString(),rateofkms.getText().toString()
                        ,dailyfuel.getText().toString(),dailyallowance.getText().toString(),otherexpenses.getText().toString(),
                        remarks.getText().toString()).execute();

                break;
            case R.id.back:
                finish();
                break;
            case R.id.datepicker:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialogn = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss a");
                                String strDate = mdformat.format(calendar.getTime());
                                datepicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year + " :" + strDate);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialogn.show();

                break;
        }
    }

    class Mylatestnews extends AsyncTask<String, String, JSONArray> {
        private JSONArray json;
        ArrayList<NameValuePair> nameValuePairs;
        String datepicker,route,vehicletype,nokms,rateofkms,dailyfuel,dailyallowance,otherexpenses,remarks;


        public Mylatestnews(String datepicker, String route,String vehicletype,String nokms,String rateofkms,
                            String dailyfuel,String dailyallowance,String otherexpenses,String remarks) {
            this.datepicker = datepicker;this.route = route;this.vehicletype =vehicletype;
            this.nokms = nokms;this.rateofkms = rateofkms;this.dailyfuel = dailyfuel;
            this.dailyallowance = dailyallowance;this.otherexpenses= otherexpenses;this.remarks = remarks;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONArray jsonObject) {
            super.onPostExecute(jsonObject);
            progressDialog.dismiss();
            Toast.makeText(getBaseContext(),jsonObject.toString(),Toast.LENGTH_SHORT).show();

        }

        @Override
        protected JSONArray doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("edate",datepicker));
            nameValuePairs.add(new BasicNameValuePair("eroute",route));
            nameValuePairs.add(new BasicNameValuePair("evehicletype",vehicletype));
            nameValuePairs.add(new BasicNameValuePair("enoofkms",nokms));
            nameValuePairs.add(new BasicNameValuePair("eratekm",rateofkms));
            nameValuePairs.add(new BasicNameValuePair("edailyfuelexpenses",dailyfuel));
            nameValuePairs.add(new BasicNameValuePair("edailyallowance",dailyallowance));
            nameValuePairs.add(new BasicNameValuePair("eotherexpenses",otherexpenses));
            nameValuePairs.add(new BasicNameValuePair("eremarks",remarks));

            json = JSONParser.makeServiceCall("http://www.pg-iglobal.com/Arthmetic.asmx/insertexpenses",1, nameValuePairs);
            //  json = JSONParser.makeServiceCall("http://timesofindia.indiatimes.com/rssfeeds/-2128936835.cms", 1, nameValuePairs);
            return json;
        }
    }

}
