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
import com.nganlth.freshfoodadmin.Model.Bill;
import com.nganlth.freshfoodadmin.Model.Cart;
import com.nganlth.freshfoodadmin.Model.DetailBill;
import com.nganlth.freshfoodadmin.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    public static ArrayList<Cart> list_cart;

    DatabaseReference mDataCart;
    DatabaseReference mDataDetail;



    public CartAdapter(Context context, ArrayList<Cart> list_cart){
        this.context = context;
        this.list_cart = list_cart;
    }
    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDataCart = FirebaseDatabase.getInstance().getReference("Cart");
        mDataDetail= FirebaseDatabase.getInstance().getReference("Bill");
        LayoutInflater inflater =(LayoutInflater) ((AppCompatActivity)context).getLayoutInflater();

        View view = inflater.inflate(R.layout.item_cart, parent, false);
        CartAdapter.MyViewHolder holder = new CartAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, final int position) {
        Cart item = list_cart.get(position);
        holder.cart_nameFood.setText(item.getNameFood());
        holder.cart_price.setText(item.getPriceFood()+"");
        holder.cart_amount.setText(item.getAmount()+"");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = ((AppCompatActivity)context).getSharedPreferences("bill.dat",MODE_PRIVATE);
                String Id_Bill = pref.getString("bill_id","");
                String Date = pref.getString("Date","");
                String id_food = item.getId_food();
                String name_food = item.getNameFood();
                double price_food = item.getPriceFood();
                int amount = item.getAmount();

                mDataDetail.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data: snapshot.getChildren()){
                            String bill_id = data.getKey();
                            Bill bill = new Bill();

                            if (data.child("bill_id").getValue(String.class).equals(Id_Bill)){
                                bill.setDate(Date);
//                                bill.setDetail_Id(detail_id);
//                                bill.setId_food(id_food);
//                                bill.setNameFood(name_food);
                                DetailBill detai = new DetailBill();
                                detai.setPriceFood(price_food);
                                detai.setAmount(amount);

                                mDataDetail.child(Id_Bill).setValue(bill);

                            }
                        }
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
        return list_cart.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView cart_nameFood, cart_price, cart_amount;
        public MyViewHolder(final View view){
            super(view);
            cart_nameFood = view.findViewById(R.id.cart_nameFood);
            cart_price = view.findViewById(R.id.cart_price);
            cart_amount = view.findViewById(R.id.cart_amount);
            cardView = view.findViewById(R.id.item_cart);



        }
    }
}