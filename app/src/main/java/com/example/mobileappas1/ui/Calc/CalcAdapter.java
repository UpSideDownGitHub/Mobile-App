package com.example.mobileappas1.ui.Calc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappas1.R;

import java.util.ArrayList;

/*
 * this class is the adapter for the recycler view that will show
 * all of the current calculations
 */
public class CalcAdapter extends RecyclerView.Adapter<CalcAdapter.ViewHolder> {
    // Private variables
    private ArrayList  sumOutput;
    private Context context;

    /*
     * Constructor for initialization
     */
    public CalcAdapter(Context context, ArrayList sumOutput) {
        this.context = context;
        this.sumOutput = sumOutput;
    }

    /*
     * will initialize the view holder for the recycler view
     */
    @NonNull
    @Override
    public CalcAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calc_list_item, parent, false);
        // Passing view to ViewHolder
        CalcAdapter.ViewHolder viewHolder = new CalcAdapter.ViewHolder(view);
        return viewHolder;
    }

    /*
    * Bind the date to the specified position on screen
    */ 
    @Override
    public void onBindViewHolder(@NonNull CalcAdapter.ViewHolder holder, int position) {
        // TypeCast Object to int type
        holder.text.setText((String) sumOutput.get(position));
    }

    /*
     * returns to total item count
     */
    @Override
    public int getItemCount() {
        // Returns number of items
        // currently available in Adapter
        return sumOutput.size();
    }

    /*
     * Initialize the view, getting all of the text views that will be edited
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        // variables
        TextView text;

        /*
         * constructor to initialize the view holder
         */
        public ViewHolder(View view) {
            super(view);
            // get the text view
            text = (TextView) view.findViewById(R.id.note_item);
        }
    }

     /*
     * add an item to the list of items to show on the recycler view
     */
    public void addValue(String value){
        sumOutput.add(0, value);
        notifyDataSetChanged();
    }
}
