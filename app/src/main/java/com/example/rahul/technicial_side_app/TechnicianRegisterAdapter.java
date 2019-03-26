package com.example.rahul.technicial_side_app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


    public class TechnicianRegisterAdapter  extends RecyclerView.Adapter<TechnicianRegisterAdapter.ViewHolder> {

        private static final String TAG = "RecyclerViewForUv";
        //vars
        private ArrayList<String> mCid1 = new ArrayList<>();
        private ArrayList<String> mCid2 = new ArrayList<>();
        private ArrayList<String>mcid3 = new ArrayList<>();
        private ArrayList<String> mCid4 = new ArrayList<>();
        private ArrayList<String> mCid5 = new ArrayList<>();
        private ArrayList<String> mCid6 = new ArrayList<>();
        private Context mContext;

        public TechnicianRegisterAdapter(Context mContext,ArrayList<String> mCid1, ArrayList<String> mCid2, ArrayList<String> mcid3, ArrayList<String> mCid4, ArrayList<String> mCid5, ArrayList<String> mCid6) {
            this.mCid1 = mCid1;
            this.mCid2 = mCid2;
            this.mcid3 = mcid3;
            this.mCid4 = mCid4;
            this.mCid5 = mCid5;
            this.mCid6 = mCid6;
            this.mContext = mContext;
        }



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_register, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Log.d(TAG, "onBindViewHolder: called.");

            holder.text1.setText(mCid1.get(position));
            holder.text2.setText(mCid2.get(position));
            holder.text3.setText(mcid3.get(position));
            holder.text4.setText(mCid4.get(position));
            holder.text5.setText(mCid5.get(position));
            holder.text6.setText(mCid6.get(position));

        }

        @Override
        public int getItemCount() {
            return mCid2.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView text1,text2,text3,text4,text5,text6,text7;

            public ViewHolder(View itemView) {
                super(itemView);

                text1 = itemView.findViewById(R.id.name);
                text2 = itemView.findViewById(R.id.address);
                text3 = itemView.findViewById(R.id.pin);
                text4 = itemView.findViewById(R.id.new1);
                text5 = itemView.findViewById(R.id.new2);
                text6 = itemView.findViewById(R.id.new3);

            }
        }
    }

