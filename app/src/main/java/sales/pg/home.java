package sales.pg;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class home extends Activity implements View.OnClickListener {
    RelativeLayout dialywork, orders, receipt, product, expenses, outstanding, salesdetails, mail,complaints;
    ImageView logout, profile, home, notification;

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    RelativeLayout myrr_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        myrr_layout = (RelativeLayout)findViewById(R.id.myrr_layout);
       /*   loadAnimations();
      //  mSetRightOut.setTarget(mycomplaint_rl);
        mSetLeftIn.setTarget(myrr_layout);
      //  mSetRightOut.start();
        mSetLeftIn.start();*/

        dialywork = (RelativeLayout) findViewById(R.id.dialywork);
        orders = (RelativeLayout) findViewById(R.id.orders);
        receipt = (RelativeLayout) findViewById(R.id.receipt);
        product = (RelativeLayout) findViewById(R.id.product);
        expenses = (RelativeLayout) findViewById(R.id.expenses);
        outstanding = (RelativeLayout) findViewById(R.id.outstanding);
        salesdetails = (RelativeLayout) findViewById(R.id.salesdetails);
        mail = (RelativeLayout) findViewById(R.id.mail);
        complaints = (RelativeLayout) findViewById(R.id.complaints);

        home = (ImageView) findViewById(R.id.home);
        profile = (ImageView) findViewById(R.id.profile);
        logout = (ImageView) findViewById(R.id.logout);
        notification = (ImageView) findViewById(R.id.notification);

        dialywork.setOnClickListener(this);
        orders.setOnClickListener(this);
        receipt.setOnClickListener(this);
        product.setOnClickListener(this);
        expenses.setOnClickListener(this);
        outstanding.setOnClickListener(this);
        salesdetails.setOnClickListener(this);
        mail.setOnClickListener(this);
        home.setOnClickListener(this);
        profile.setOnClickListener(this);
        logout.setOnClickListener(this);
        notification.setOnClickListener(this);
        complaints.setOnClickListener(this);

     /*   Animation myAnim = AnimationUtils.loadAnimation(home.this, R.anim.zoomin);
        //dialywork.startAnimation(myAnim);
        dialywork.startAnimation(myAnim);
        orders.startAnimation(myAnim);
        receipt.startAnimation(myAnim);
        product.startAnimation(myAnim);
        expenses.startAnimation(myAnim);
        outstanding.startAnimation(myAnim);
        mail.startAnimation(myAnim);
        salesdetails.startAnimation(myAnim);*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialywork:
                Intent i = new Intent(home.this, DailyWork.class);
                startActivity(i);
                break;
            case R.id.orders:
                Intent orders = new Intent(home.this, Orders.class);
                startActivity(orders);
                break;
            case R.id.receipt:
                Intent receipt = new Intent(home.this, Receipt.class);
                startActivity(receipt);
                break;
            case R.id.product:
                Intent product = new Intent(home.this, Profile.class);
                startActivity(product);
                break;
            case R.id.expenses:
                Intent expenses = new Intent(home.this, Expenses.class);
                startActivity(expenses);
                break;
            case R.id.outstanding:
                Intent outstanding = new Intent(home.this, Profile.class);
                startActivity(outstanding);
                break;
            case R.id.salesdetails:
                Intent salesdetails = new Intent(home.this, Profile.class);
                startActivity(salesdetails);
                break;
            case R.id.mail:
                Intent mail = new Intent(home.this, Profile.class);
                startActivity(mail);
                break;
            case R.id.complaints:
                Intent complaints = new Intent(home.this, Complaints.class);
                startActivity(complaints);
                break;
            case R.id.home:
                Intent home = new Intent(home.this, Profile.class);
                startActivity(home);
                break;
            case R.id.profile:
                Intent profile = new Intent(home.this, Profile.class);
                startActivity(profile);
                break;
            case R.id.logout:
                finish();
                break;
            case R.id.notification:
                Intent notification = new Intent(home.this, Profile.class);
                startActivity(notification);
                break;

        }
    }
    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }
}
