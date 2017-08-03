package com.example.cluex.Helper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cluex.R;

import java.util.List;

/**
 * Created by Juned on 3/27/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView> {

    private List<String> list;
    private List<String> list1;

    public class MyView extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public TextView textViewAdd;
        public Button btn;
        public ImageView img;




        public MyView(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.textView1);
            textViewAdd= (TextView) view.findViewById(R.id.textView2);
            btn= (Button) view.findViewById(R.id.button2);
            img=(ImageView) view.findViewById(R.id.ImageView);
            btn.setOnClickListener(this);



        }
        @Override
        public void onClick(View v) {

           if (v.getId() == btn.getId()){
                Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }

            else {
                Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        }







    }







    public RecyclerViewAdapter(List<String> horizontalList, List <String> horizontalList1) {
        this.list = horizontalList;
        this.list1=horizontalList1;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item, parent, false);

        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

        holder.textView.setText(list.get(position));
        holder.textViewAdd.setText(list1.get(position));
        holder.img.setImageResource(R.drawable.accident);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
