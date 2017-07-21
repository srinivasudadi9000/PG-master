package sales.pg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import sales.pg.adapters.Dealers;
import sales.pg.functions.DisplayRecylerview;

/**
 * Created by USER on 20-07-2017.
 */

public class DealersRecylerAdapter extends RecyclerView.Adapter<DealersRecylerAdapter.viewHolder>  {
    DisplayRecylerview displayRecylerview;
    ArrayList<Dealers> dealerses;
    ArrayList<Dealers> dealersesfilter;
    public DealersRecylerAdapter(DisplayRecylerview displayRecylerview, ArrayList<Dealers> dealerses) {
        this.displayRecylerview = displayRecylerview;this.dealerses = dealerses;
         this.dealersesfilter = dealerses;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singledealers,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.dealername.setText(dealerses.get(position).getDealername());
        holder.delercode.setText(dealerses.get(position).getDealercode());
    }

    @Override
    public int getItemCount() {
        return dealerses.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView dealername,delercode;
        public viewHolder(final View itemView) {
            super(itemView);
            dealername = (TextView)itemView.findViewById(R.id.dealername);
            delercode  = (TextView)itemView.findViewById(R.id.delercode);
            dealername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("delearname", dealername.getText().toString());
                    returnIntent.putExtra("dealercode", delercode.getText().toString());
                    displayRecylerview.setResult(Activity.RESULT_OK, returnIntent);
                    displayRecylerview.finish();
                }
            });
        }
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toUpperCase(Locale.getDefault());
        Toast.makeText(displayRecylerview.getApplication(),charText.toString(),Toast.LENGTH_SHORT).show();
       // dealerses.clear();
        if (charText.length() == 0) {
            dealerses = dealersesfilter;
        } else {
          /*  String xx = String.valueOf(dealersesfilter.size());
            Toast.makeText(displayRecylerview.getApplication(),"texdtentered "+xx,Toast.LENGTH_SHORT).show();
          */
            ArrayList<Dealers> filteredList = new ArrayList<>();

            for (Dealers androidVersion : dealerses) {
                if (androidVersion.getDealername().contains(charText)){
                    filteredList.add(androidVersion);
                }
            }
            dealerses = filteredList;
        }
        notifyDataSetChanged();
    }

  }
