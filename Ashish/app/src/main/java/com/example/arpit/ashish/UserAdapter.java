package com.example.arpit.ashish;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by arpit on 2/4/16.
 */
public class UserAdapter  extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    List<User> list;

    private Context context;


    public UserAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        User stationary = list.get(position);

        Log.d("OnBind", "calling on bind");


        holder.textViewBody.setText(stationary.getBody());
        holder.textViewTitle.setText(stationary.gettitle());

       holder.textViewUserId.setText(Integer.toString(stationary.getUserid()));

        holder.textViewID.setText(Integer.toString(stationary.getID()));









    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder   {

        public TextView textViewTitle;

        public TextView textViewBody;
        public TextView textViewID;

        public TextView textViewUserId;


        public int id;


        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            context=itemView.getContext();
            textViewBody=(TextView)itemView.findViewById(R.id.body);
            textViewTitle=(TextView)itemView.findViewById(R.id.title);
            textViewID=(TextView)itemView.findViewById(R.id.id);
            textViewUserId=(TextView)itemView.findViewById(R.id.userid);


        }



    }
}


