package sales.pg;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import sales.pg.functions.JSONParser;

public class DailyWork extends Activity implements View.OnClickListener{

    ImageView back;
    Button submit_dailywork;
    ProgressDialog progressDialog;
    EditText datepicker,arearoute,delear_name,person_met,contactnumber,fromtime,totime,purposeofvisit,nextvisitdate,proposeorder;
    EditText areacompetitors,remarks;
    private int mYear, mMonth, mDay, mHour, mMinute;

    static final int TIME_DIALOG_ID = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailywork);
        back = (ImageView)findViewById(R.id.back);
        submit_dailywork = (Button)findViewById(R.id.submit_dailywork);

        datepicker=(EditText) findViewById(R.id.datepicker);
        arearoute=(EditText)findViewById(R.id.arearoute);
        delear_name = (EditText)findViewById(R.id.delear_name);
        person_met=(EditText)findViewById(R.id.person_met);
        contactnumber=(EditText)findViewById(R.id.contactnumber);
        fromtime=(EditText)findViewById(R.id.fromtime);
        totime= (EditText)findViewById(R.id.totime);
        purposeofvisit=(EditText)findViewById(R.id.purposeofvisit);
        nextvisitdate=(EditText)findViewById(R.id.nextvisitdate);
        proposeorder=(EditText)findViewById(R.id.proposeorder);
        areacompetitors= (EditText)findViewById(R.id.areacompetitors);
        remarks=(EditText)findViewById(R.id.remarks);

        Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
      //  SimpleDateFormat mdformat = new SimpleDateFormat("dd/ MM / yyyy_HH:mm:ss a");
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/ MM / yyyy");
        String strDate =  mdformat.format(calendar.getTime());
        datepicker.setText(strDate);

        datepicker.setOnClickListener(this);
        fromtime.setOnClickListener(this);
        totime.setOnClickListener(this);
        back.setOnClickListener(this);
        submit_dailywork.setOnClickListener(this);
        nextvisitdate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.nextvisitdate:
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
                                String strDate =  mdformat.format(calendar.getTime());
                                nextvisitdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year +" :"+strDate);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialogn.show();
                break;
            case R.id.fromtime:
                // Get Current Date
                final Calendar c5 = Calendar.getInstance();
                mYear = c5.get(Calendar.YEAR);
                mMonth = c5.get(Calendar.MONTH);
                mDay = c5.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss a");
                                String strDate =  mdformat.format(calendar.getTime());

                                fromtime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year +" :"+strDate);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

                break;
            case R.id.totime:
                // Get Current Date
                final Calendar c2 = Calendar.getInstance();
                mYear = c2.get(Calendar.YEAR);
                mMonth = c2.get(Calendar.MONTH);
                mDay = c2.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog2 = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                totime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss a");
                                String strDate =  mdformat.format(calendar.getTime());

                                totime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year +" :"+strDate);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog2.show();

                break;
            case R.id.datepicker:
                // Get Current Date
                final Calendar c3 = Calendar.getInstance();
                mYear = c3.get(Calendar.YEAR);
                mMonth = c3.get(Calendar.MONTH);
                mDay = c3.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog3 = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                datepicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog3.show();
                break;
            case R.id.submit_dailywork:
                progressDialog =new ProgressDialog(DailyWork.this);
                progressDialog.setTitle("Checking with server...");
                progressDialog.show();


                new DailyWork.Mylatestnews(datepicker.getText().toString(),arearoute.getText().toString()
                ,delear_name.getText().toString(),person_met.getText().toString(),contactnumber.getText().toString()
                        ,fromtime.getText().toString(),totime.getText().toString(),purposeofvisit.getText().toString(),
                        nextvisitdate.getText().toString(),proposeorder.getText().toString(),
                        areacompetitors.getText().toString(),remarks.getText().toString()).execute();
                break;

        }
    }


    class Mylatestnews extends AsyncTask<String, String, JSONObject> {
        private JSONObject json;
        ArrayList<NameValuePair> nameValuePairs;
        String datepicker,arearoute,delear_name,person_met,contactnumber,fromtime,totime,purposeofvisit,nextvisitdate,proposeorder
                ,areacompetitors,remarks;
        public Mylatestnews(String datepicker, String arearoute,String delear_name,String person_met,String contactnumber,
                            String fromtime,String totime,String purposeofvisit,String nextvisitdate,String proposeorder,
                            String areacompetitors,String remarks) {
           this.datepicker = datepicker;this.arearoute = arearoute;this.delear_name =delear_name;
            this.person_met = person_met;this.contactnumber = contactnumber;this.fromtime = fromtime;
            this.totime = totime;this.purposeofvisit= purposeofvisit;this.nextvisitdate = nextvisitdate;
            this.proposeorder= proposeorder;this.areacompetitors = areacompetitors;this.remarks = remarks;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            progressDialog.dismiss();
            Toast.makeText(getBaseContext(),jsonObject.toString(),Toast.LENGTH_SHORT).show();
            try {
                String username = jsonObject.getString("username");
                if (username.equals("null")){
                    Toast.makeText(getBaseContext(),"Invalid username or password",Toast.LENGTH_SHORT).show();
                }else {
                    Intent i = new Intent(DailyWork.this,home.class);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("ddate",datepicker));
            nameValuePairs.add(new BasicNameValuePair("areaandroute",arearoute));
            nameValuePairs.add(new BasicNameValuePair("dealername",delear_name));
            nameValuePairs.add(new BasicNameValuePair("personmet",person_met));
            nameValuePairs.add(new BasicNameValuePair("contactnumber",contactnumber));
            nameValuePairs.add(new BasicNameValuePair("fromtime",fromtime));
            nameValuePairs.add(new BasicNameValuePair("totime",totime));
            nameValuePairs.add(new BasicNameValuePair("purposevisit",purposeofvisit));
            nameValuePairs.add(new BasicNameValuePair("nextvisitdate",nextvisitdate));
            nameValuePairs.add(new BasicNameValuePair("purposeorders",proposeorder));
            nameValuePairs.add(new BasicNameValuePair("areacompetitors",areacompetitors));
            nameValuePairs.add(new BasicNameValuePair("remarks",remarks));

            json = JSONParser.makeServiceCall("http://www.pg-iglobal.com/Arthmetic.asmx/insertdailywork",2, nameValuePairs);
            //  json = JSONParser.makeServiceCall("http://timesofindia.indiatimes.com/rssfeeds/-2128936835.cms", 1, nameValuePairs);
            return json;
        }
    }

}
