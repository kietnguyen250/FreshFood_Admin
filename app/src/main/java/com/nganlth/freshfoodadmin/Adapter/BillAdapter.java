package com.nganlth.freshfoodadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nganlth.freshfoodadmin.Fragment.CartFragment;
import com.nganlth.freshfoodadmin.Fragment.DetailFragment;
import com.nganlth.freshfoodadmin.Model.Bill;
import com.nganlth.freshfoodadmin.Model.Cart;
import com.nganlth.freshfoodadmin.Model.DetailBill;
import com.nganlth.freshfoodadmin.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyViewHolder> {
    Context context;
    ArrayList<Bill> list_bill;
    DatabaseReference mDataBill;
    String id_bill = "";

    public BillAdapter(Context context, ArrayList<Bill> list_bill){
        this.context = context;
        this.list_bill = list_bill;
    }
    @NonNull
    @Override
    public BillAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater =(LayoutInflater) ((AppCompatActivity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_bill, parent, false);

        BillAdapter.MyViewHolder holder = new BillAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.MyViewHolder holder, final int position) {
        mDataBill= FirebaseDatabase.getInstance().getReference("Bill");
        Bill item = list_bill.get(position);
        holder.bill_email_buyer.setText(item.getEmail_buyer());
        holder.bill_date.setText(item.getDate()+"");
        holder.bill_address.setText(item.getAddress_buyer());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBill.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data:snapshot.getChildren()){
                            if (data.child("email_buyer").getValue(String.class).equalsIgnoreCase(item.getEmail_buyer()) && data.child("date").getValue(String.class).equalsIgnoreCase(item.getDate())){
                                id_bill = data.getKey();

                            }

                        }
                        //put email người dùng sang để lấy cart theo email
                        Bundle args = new Bundle();
                        args.putString("bill_email_buyer", item.getEmail_buyer());
                        args.putString("id_bill_detail", id_bill);
                        DetailFragment detailFragment = new DetailFragment();
                        detailFragment.setArguments(args);
                        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,detailFragment).commit();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return list_bill.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView bill_email_buyer, bill_date, bill_address;
        public MyViewHolder(final View view){
            super(view);
            bill_email_buyer = view.findViewById(R.id.bill_email_buyer);
            bill_date = view.findViewById(R.id.bill_date);
            cardView = view.findViewById(R.id.item_bill);
            bill_address = view.findViewById(R.id.bill_address);



        }
    }
}
