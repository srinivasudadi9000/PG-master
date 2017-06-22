package sales.pg;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import sales.pg.functions.JSONParser;

public class Complaints extends Activity implements View.OnClickListener{
EditText date,coplainttype,dealerdetails,place,products,size,quantity,complaintdetails,remarks;
    ImageView back;
    RelativeLayout mycomplaint_rl;
    Button submit;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaints);
        mycomplaint_rl = (RelativeLayout)findViewById(R.id.mycomplaint_rl);
       /* Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
       // Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        mycomplaint_rl.startAnimation(slideUp);*/


        loadAnimations();
        //  mSetRightOut.setTarget(mycomplaint_rl);
        mSetLeftIn.setTarget(mycomplaint_rl);
        //  mSetRightOut.start();
        mSetLeftIn.start();

        back = (ImageView)findViewById(R.id.back);
        date = (EditText)findViewById(R.id.date);
        submit = (Button)findViewById(R.id.submit);

        coplainttype = (EditText)findViewById(R.id.coplainttype);
        dealerdetails = (EditText)findViewById(R.id.dealerdetails);
        place = (EditText)findViewById(R.id.place);
        products = (EditText)findViewById(R.id.products);
        size = (EditText)findViewById(R.id.size);
        quantity = (EditText)findViewById(R.id.quantity);
        complaintdetails = (EditText)findViewById(R.id.complaintdetails);
        remarks = (EditText)findViewById(R.id.remarks);
        date.setOnClickListener(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                progressDialog =new ProgressDialog(Complaints.this);
                progressDialog.setTitle("Checking with server...");
                progressDialog.show();

                 new Complaints.Mylatestnews(date.getText().toString(),coplainttype.getText().toString()
                        ,dealerdetails.getText().toString(),place.getText().toString(),products.getText().toString()
                        ,size.getText().toString(),quantity.getText().toString(),complaintdetails.getText().toString(),
                        remarks.getText().toString()).execute();
                break;

            case R.id.date:
                final Calendar c2 = Calendar.getInstance();
                mYear = c2.get(Calendar.YEAR);
                mMonth = c2.get(Calendar.MONTH);
                mDay = c2.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog2 = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss a");
                                String strDate =  mdformat.format(calendar.getTime());

                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year +" :"+strDate);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog2.show();
                 break;
        }
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }
    class Mylatestnews extends AsyncTask<String, String, JSONObject> {
        private JSONObject json;
        ArrayList<NameValuePair> nameValuePairs;
        String date,coplainttype,dealerdetails,place,products,size,quantity,complaintdetails,remarks;

         public Mylatestnews(String date, String coplainttype,String dealerdetails,String place,String products,
                            String size,String quantity,String complaintdetails,String remarks) {
            this.date = date;this.coplainttype = coplainttype;this.dealerdetails =dealerdetails;
            this.place = place;this.products = products;this.size = size;
            this.quantity = quantity;this.complaintdetails= complaintdetails;this.remarks = remarks;
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
                    Intent i = new Intent(Complaints.this,home.class);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            nameValuePairs = new ArrayList<NameValuePair>();
             nameValuePairs.add(new BasicNameValuePair("ddate",date));
            nameValuePairs.add(new BasicNameValuePair("areaandroute",coplainttype));
            nameValuePairs.add(new BasicNameValuePair("dealername",dealerdetails));
            nameValuePairs.add(new BasicNameValuePair("personmet",place));
            nameValuePairs.add(new BasicNameValuePair("contactnumber",products));
            nameValuePairs.add(new BasicNameValuePair("fromtime",size));
            nameValuePairs.add(new BasicNameValuePair("totime",quantity));
            nameValuePairs.add(new BasicNameValuePair("purposevisit",complaintdetails));

            nameValuePairs.add(new BasicNameValuePair("remarks",remarks));

            json = JSONParser.makeServiceCall("http://www.pg-iglobal.com/Arthmetic.asmx/insertdailywork",2, nameValuePairs);
            //  json = JSONParser.makeServiceCall("http://timesofindia.indiatimes.com/rssfeeds/-2128936835.cms", 1, nameValuePairs);
            return json;
        }
    }


}
