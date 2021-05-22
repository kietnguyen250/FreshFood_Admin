package com.nganlth.freshfoodadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.nganlth.freshfoodadmin.DAO.CustomerDAO;
import com.nganlth.freshfoodadmin.Fragment.CustomerFragment;
import com.nganlth.freshfoodadmin.Fragment.Infor_Customer_Fragment;
import com.nganlth.freshfoodadmin.Model.Customer;
import com.nganlth.freshfoodadmin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder>{

    Context context;
    ArrayList<Customer> dataCustomer;
    //Lấy ảnh
    FirebaseStorage firebaseStorage;
    CustomerDAO customerDAO;

    public CustomerAdapter(Context context, ArrayList<Customer> dataCustomer) {
        this.context = context;
        this.dataCustomer = dataCustomer;
    }

    @NonNull
    @Override
    public CustomerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_customer,parent,false);
        CustomerAdapter.MyViewHolder holder = new CustomerAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.MyViewHolder holder, int position) {
        Customer item = dataCustomer.get(position);
        holder.tv_email_customer.setText(item.getEmail());
        firebaseStorage = FirebaseStorage.getInstance();

        holder.cvCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("email_customer", item.getEmail());
                Infor_Customer_Fragment infor_customer_fragment = new Infor_Customer_Fragment();
                infor_customer_fragment.setArguments(bundle);
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,infor_customer_fragment).commit();

            }
        });

    }


    @Override
    public int getItemCount() {
        return dataCustomer.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cvCustomer;
        TextView tv_email_customer;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cvCustomer = itemView.findViewById(R.id.item_customer);
            tv_email_customer = itemView.findViewById(R.id.tv_email_customer);
        }
    }

}
