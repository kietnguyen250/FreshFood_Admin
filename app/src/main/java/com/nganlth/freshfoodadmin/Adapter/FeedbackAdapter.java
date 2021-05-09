package com.nganlth.freshfoodadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nganlth.freshfoodadmin.Model.FeedBack;
import com.nganlth.freshfoodadmin.R;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {
    Context context;
    public static ArrayList<FeedBack> list_feedback;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference mDataFeedBack;
    String key = "";

    public FeedbackAdapter(Context context, ArrayList<FeedBack> list_feedback){
        this.context = context;
        this.list_feedback = list_feedback;
    }
    @NonNull
    @Override
    public FeedbackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDataFeedBack = FirebaseDatabase.getInstance().getReference("Feedback");

        LayoutInflater inflater =(LayoutInflater) ((AppCompatActivity)context).getLayoutInflater();

        View view = inflater.inflate(R.layout.item_feedback, parent, false);
        FeedbackAdapter.MyViewHolder holder = new FeedbackAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.MyViewHolder holder, final int position) {
        FeedBack item = list_feedback.get(position);
        holder.txtEmail.setText(item.getEmail_Buyer());
        holder.txtContent.setText(item.getContent_fb());
        holder.txtTime.setText(item.getDateTime());

    }

    @Override
    public int getItemCount() {
        return list_feedback.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView txtEmail, txtContent, txtTime;
        public MyViewHolder(final View view){
            super(view);
            txtEmail = view.findViewById(R.id.txtEmail);
            txtContent = view.findViewById(R.id.txtContent);
            txtTime = view.findViewById(R.id.txtTime);
            cardView = view.findViewById(R.id.item_cart);

        }
    }
}