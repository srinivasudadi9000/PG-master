package sales.pg;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import sales.pg.functions.JSONParser;

public class Receipt extends Activity implements View.OnClickListener{
EditText datepicker,customer,place,osondate,particulars,dateddd,amount,receivedby,docsenddetails,remarks;
    ImageView back;
    Button submit;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt);
        back = (ImageView)findViewById(R.id.back);
        customer = (EditText)findViewById(R.id.customer);
        place = (EditText)findViewById(R.id.place);
        osondate = (EditText)findViewById(R.id.osondate);
        particulars = (EditText)findViewById(R.id.particulars);
        amount = (EditText)findViewById(R.id.amount);
        receivedby = (EditText)findViewById(R.id.receivedby);
        docsenddetails = (EditText)findViewById(R.id.docsenddetails);
        remarks = (EditText)findViewById(R.id.remarks);
        submit  = (Button)findViewById(R.id.submit);

        submit.setOnClickListener(this);
        datepicker.setOnClickListener(this);
        dateddd.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.datepicker:
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
            case R.id.dateddd:
                final Calendar c2 = Calendar.getInstance();
                mYear = c2.get(Calendar.YEAR);
                mMonth = c2.get(Calendar.MONTH);
                mDay = c2.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog2 = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateddd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog2.show();
                 break;
            case R.id.submit:
                progressDialog =new ProgressDialog(Receipt.this);
                progressDialog.setTitle("Checking with server...");
                progressDialog.show();

                 new Receipt.Mylatestnews(datepicker.getText().toString(),customer.getText().toString()
                        ,place.getText().toString(),osondate.getText().toString(),particulars.getText().toString()
                        ,dateddd.getText().toString(),amount.getText().toString(),receivedby.getText().toString(),
                        docsenddetails.getText().toString(),remarks.getText().toString()).execute();
                break;

            case R.id.back:
                finish();
                break;

        }
    }

    class Mylatestnews extends AsyncTask<String, String, JSONObject> {
        private JSONObject json;
        ArrayList<NameValuePair> nameValuePairs;
        String datepicker,customer,place,osondate,particulars,dateddd,amount,receivedby,docsenddetails,remarks;
        public Mylatestnews(String datepicker, String customer,String place,String osondate,String particulars,
                            String dateddd,String amount,String receivedby,String docsenddetails,String remarks) {
            this.datepicker = datepicker;this.customer = customer;this.place =place;
            this.osondate = osondate;this.particulars = particulars;this.dateddd = dateddd;
            this.amount = amount;this.receivedby= receivedby;this.docsenddetails = docsenddetails;
            this.remarks = remarks;
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
                    Intent i = new Intent(Receipt.this,home.class);
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
            nameValuePairs.add(new BasicNameValuePair("areaandroute",customer));
            nameValuePairs.add(new BasicNameValuePair("dealername",place));
            nameValuePairs.add(new BasicNameValuePair("personmet",osondate));
            nameValuePairs.add(new BasicNameValuePair("contactnumber",particulars));
            nameValuePairs.add(new BasicNameValuePair("fromtime",dateddd));
            nameValuePairs.add(new BasicNameValuePair("totime",amount));
            nameValuePairs.add(new BasicNameValuePair("purposevisit",receivedby));
            nameValuePairs.add(new BasicNameValuePair("nextvisitdate",docsenddetails));
            nameValuePairs.add(new BasicNameValuePair("remarks",remarks));

            json = JSONParser.makeServiceCall("http://www.pg-iglobal.com/Arthmetic.asmx/insertdailywork",2, nameValuePairs);
            //  json = JSONParser.makeServiceCall("http://timesofindia.indiatimes.com/rssfeeds/-2128936835.cms", 1, nameValuePairs);
            return json;
        }
    }

}
