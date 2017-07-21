package sales.pg;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import sales.pg.functions.DisplayRecylerview;
import sales.pg.functions.JSONParser;

public class Orders extends Activity implements View.OnClickListener {
    EditText orderdate, delear_name, place, productgroup, brandname, uom, size, quantity, delivery_date, paymentcredit, remarks;
    ImageView back;
    TextView ordernumber;
    Button submit;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String myselect,dealercode,productgroupcode,product_brand_code,sizecode,placeid;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders);
        back = (ImageView) findViewById(R.id.back);
        ordernumber = (TextView) findViewById(R.id.ordernumber);
        orderdate = (EditText) findViewById(R.id.orderdate);
        delear_name = (EditText) findViewById(R.id.delear_name);
        place = (EditText) findViewById(R.id.place);
        productgroup = (EditText) findViewById(R.id.productgroup);
        brandname = (EditText) findViewById(R.id.brandname);
        uom = (EditText) findViewById(R.id.uom);
        size = (EditText) findViewById(R.id.size);
        quantity = (EditText) findViewById(R.id.quantity);
        delivery_date = (EditText) findViewById(R.id.delivery_date);
        paymentcredit = (EditText) findViewById(R.id.paymentcredit);
        remarks = (EditText) findViewById(R.id.remarks);
        submit = (Button)findViewById(R.id.submit_order);

        Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        //  SimpleDateFormat mdformat = new SimpleDateFormat("dd/ MM / yyyy_HH:mm:ss a");
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/ MM / yyyy");
        String strDate = mdformat.format(calendar.getTime());
        orderdate.setText(strDate);

        back.setOnClickListener(this);

        orderdate.setOnClickListener(this);
        delivery_date.setOnClickListener(this);
        submit.setOnClickListener(this);
        place.setOnClickListener(Orders.this);
        productgroup.setOnClickListener(Orders.this);
        productgroup.setOnClickListener(Orders.this);
        delear_name.setOnClickListener(Orders.this);
        brandname.setOnClickListener(Orders.this);
        uom.setOnClickListener(Orders.this);
        size.setOnClickListener(Orders.this);
        dealercode ="";myselect="";product_brand_code="";productgroupcode="";sizecode="";placeid="";
        Random r = new Random();
        String i1 = String.valueOf(r.nextInt(80 - 65) + 65);
        ordernumber.setText(i1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                if (myselect.equals("delearname")){
                    String result=data.getStringExtra("delearname");
                    delear_name.setText(result);
                    dealercode = data.getStringExtra("dealercode");
                    place.requestFocus();
                    Toast.makeText(getBaseContext(),data.getStringExtra("dealercode"),Toast.LENGTH_SHORT).show();
                }else if(myselect.equals("productgroup")){
                    String result=data.getStringExtra("delearname");
                     productgroup.setText(result);
                     productgroupcode = data.getStringExtra("dealercode");
                     brandname.requestFocus();
                }
                else if(myselect.equals("place")){
                    String result=data.getStringExtra("delearname");
                    place.setText(result);
                    placeid = data.getStringExtra("dealercode");
                    productgroup.requestFocus();
                }
                else if(myselect.equals("brandname")){
                    String result=data.getStringExtra("delearname");
                    brandname.setText(result);
                    product_brand_code = data.getStringExtra("dealercode");
                    uom.requestFocus();
                }else if (myselect.equals("uom")){
                    String result=data.getStringExtra("delearname");
                    uom.setText(result);
                    size.requestFocus();
                }
                else if (myselect.equals("size")){
                    String result=data.getStringExtra("delearname");
                    size.setText(result);
                    sizecode = data.getStringExtra("dealercode");
                    quantity.requestFocus();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.size:
                myselect="size";
                if (internet()){
                    Intent i = new Intent(Orders.this, DisplayRecylerview.class);
                    i.putExtra("from","size");
                    i.putExtra("productbrandcode",product_brand_code);
                    startActivityForResult(i, 1);
                }
                else {
                    showalert("Please Check Your Internet connection","not");
                }
                break;
            case R.id.uom:
                myselect="uom";
                if (internet()){
                    Intent i = new Intent(Orders.this, DisplayRecylerview.class);
                    i.putExtra("from","uom");
                    i.putExtra("productbrandcode",product_brand_code);
                    startActivityForResult(i, 1);
                }
                else {
                    showalert("Please Check Your Internet connection","not");
                }
                break;
            case R.id.brandname:
                myselect="brandname";
                if (internet()){
                    Intent i = new Intent(Orders.this, DisplayRecylerview.class);
                    i.putExtra("from","brandname");
                    i.putExtra("productgroupcode",productgroupcode);
                    startActivityForResult(i, 1);
                }
                else {
                    showalert("Please Check Your Internet connection","not");
                }
                break;
            case R.id.place:
                myselect = "place";
                if (internet()){
                    Intent i = new Intent(Orders.this, DisplayRecylerview.class);
                    i.putExtra("from","place");
                    i.putExtra("dealercode",dealercode);
                    startActivityForResult(i, 1);
                }
                else {
                    showalert("Please Check Your Internet connection","not");
                }
                break;
            case R.id.productgroup:
                myselect = "productgroup";
                if (internet()){
                    Intent i = new Intent(Orders.this, DisplayRecylerview.class);
                    i.putExtra("from","productgroup");
                    startActivityForResult(i, 1);
                }
                else {
                    showalert("Please Check Your Internet connection","not");
                }
                break;
            case R.id.delear_name:
                myselect = "delearname";
                if (internet()){
                    Intent i = new Intent(Orders.this, DisplayRecylerview.class);
                    i.putExtra("from","dealers");
                    startActivityForResult(i, 1);
                }
                else {
                    showalert("Please Check Your Internet connection","not");
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.delivery_date:

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
                                delivery_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year +" :"+strDate);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialogn.show();
                break;
            case R.id.orderdate:

                final Calendar c2 = Calendar.getInstance();
                mYear = c2.get(Calendar.YEAR);
                mMonth = c2.get(Calendar.MONTH);
                mDay = c2.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialogn2 = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss a");
                                String strDate =  mdformat.format(calendar.getTime());
                                orderdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year +" :"+strDate);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialogn2.show();
                break;
            case R.id.submit_order:
                progressDialog =new ProgressDialog(Orders.this);
                progressDialog.setTitle("Checking with server...");
                progressDialog.show();

                 new Orders.Mylatestnews(ordernumber.getText().toString(),orderdate.getText().toString()
                        ,delear_name.getText().toString(),place.getText().toString(),productgroup.getText().toString()
                        ,brandname.getText().toString(),uom.getText().toString(),size.getText().toString(),
                        quantity.getText().toString(),delivery_date.getText().toString(),
                        paymentcredit.getText().toString(),remarks.getText().toString()).execute();
                break;
         }
    }

    class Mylatestnews extends AsyncTask<String, String, JSONArray> {
        private JSONArray json;
        ArrayList<NameValuePair> nameValuePairs;
        String ordernumber,orderdate,delear_name,place,productgroup,brandname,uom,size,quantity,delivery_date
                ,paymentcredit,remarks;
        public Mylatestnews(String ordernumber, String orderdate,String delear_name,String place,String productgroup,
                            String brandname,String uom,String size,String quantity,String delivery_date,
                            String paymentcredit,String remarks) {
            this.ordernumber = ordernumber;this.orderdate = orderdate;this.delear_name =delear_name;
            this.place = place;this.productgroup = productgroup;this.brandname = brandname;
            this.uom = uom;this.size= size;this.quantity = quantity;
            this.delivery_date= delivery_date;this.paymentcredit = paymentcredit;this.remarks = remarks;
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
            nameValuePairs.add(new BasicNameValuePair("mobileorderno",ordernumber));
            nameValuePairs.add(new BasicNameValuePair("empid","Emp_1"));
            nameValuePairs.add(new BasicNameValuePair("orderdate",orderdate));
            nameValuePairs.add(new BasicNameValuePair("dealercode",dealercode));
            nameValuePairs.add(new BasicNameValuePair("placeid",placeid));
            nameValuePairs.add(new BasicNameValuePair("productgroupcode",productgroupcode));
            nameValuePairs.add(new BasicNameValuePair("productcode",productgroupcode));
            nameValuePairs.add(new BasicNameValuePair("productbrandcode",product_brand_code));
            nameValuePairs.add(new BasicNameValuePair("uom",uom));
            nameValuePairs.add(new BasicNameValuePair("packingsizecode",sizecode));
            nameValuePairs.add(new BasicNameValuePair("qty",quantity));
            nameValuePairs.add(new BasicNameValuePair("deliverydate",delivery_date));
            nameValuePairs.add(new BasicNameValuePair("paymentcreditdays",paymentcredit));
            nameValuePairs.add(new BasicNameValuePair("remarks",remarks));
            json = JSONParser.makeServiceCall("http://www.pg-iglobal.com/Arthmetic.asmx/insertorders",1, nameValuePairs);
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
        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(Orders.this);
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
