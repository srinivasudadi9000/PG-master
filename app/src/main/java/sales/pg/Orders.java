package sales.pg;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

import sales.pg.functions.JSONParser;

public class Orders extends Activity implements View.OnClickListener {
    EditText orderdate, delear_name, place, productgroup, brandname, uom, size, quantity, delivery_date, paymentcredit, remarks;
    ImageView back;
    TextView ordernumber;
    Button submit;
    private int mYear, mMonth, mDay, mHour, mMinute;
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            nameValuePairs.add(new BasicNameValuePair("orderno",ordernumber));
        /*    nameValuePairs.add(new BasicNameValuePair("areaandroute",orderdate));
            nameValuePairs.add(new BasicNameValuePair("dealername",delear_name));*/
            nameValuePairs.add(new BasicNameValuePair("place",place));
            nameValuePairs.add(new BasicNameValuePair("products",productgroup));
            nameValuePairs.add(new BasicNameValuePair("size",size));
            nameValuePairs.add(new BasicNameValuePair("qty",quantity));
            nameValuePairs.add(new BasicNameValuePair("deliverydate",delivery_date));
            nameValuePairs.add(new BasicNameValuePair("paymentincredit",paymentcredit));
            nameValuePairs.add(new BasicNameValuePair("remarks",remarks));
            json = JSONParser.makeServiceCall("http://www.pg-iglobal.com/Arthmetic.asmx/insertorders",1, nameValuePairs);
            //  json = JSONParser.makeServiceCall("http://timesofindia.indiatimes.com/rssfeeds/-2128936835.cms", 1, nameValuePairs);
            return json;
        }
    }

}
