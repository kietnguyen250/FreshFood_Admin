package com.nganlth.freshfoodadmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nganlth.freshfoodadmin.Adapter.CartAdapter;
import com.nganlth.freshfoodadmin.DAO.FoodDAO;
import com.nganlth.freshfoodadmin.Model.Cart;
import com.nganlth.freshfoodadmin.Model.DetailBill;
import com.nganlth.freshfoodadmin.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class CartFragment extends Fragment {
    public static RecyclerView rcv_cart;
    TextView tvSum;
    ImageView iv_back;
    DatabaseReference mDataCart;
    DatabaseReference mDataDetail;

    double thanhTien;
    Button btnOrder;
    CartAdapter cartAdapter;
    ArrayList<Cart> ds_cart;
    String id_cart = "";
    String key = "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        initView(root);
        mDataCart= FirebaseDatabase.getInstance().getReference("Cart");
        mDataDetail= FirebaseDatabase.getInstance().getReference("Detail");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillFragment billFragment = new BillFragment();
//                fragment_food.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, billFragment).commit();
            }
        });
        //lấy email người dùng từ bill
        Bundle getdata =getArguments();
        final String bill_email_buyer = getdata.getString("bill_email_buyer");
        final String id_bill = getdata.getString("id_bill_cart");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcv_cart.setLayoutManager(layoutManager);
        rcv_cart.hasFixedSize();
        rcv_cart.setHasFixedSize(false);

        ds_cart = new ArrayList<Cart>();

        mDataCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ds_cart.clear();
                for (DataSnapshot data:snapshot.getChildren()){
                    //so sánh email để lấy đúng giỏ hàng của khách hàng
                    if (data.child("email_buyer").getValue(String.class).equalsIgnoreCase(bill_email_buyer)){
                        Cart item = data.getValue(Cart.class);

                        ds_cart.add(item);

                        thanhTien = thanhTien+item.getPriceFood();
//                        tvSum.setText(thanhTien+" Đ");

                        // Đặt kiểu số-----------------
                        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                        decimalFormat.applyPattern("#,###,###,###");
                        tvSum.setText(decimalFormat.format(thanhTien) + " Đ");
                        //-----------------------------
                    }

                }
                cartAdapter = new CartAdapter(getContext(),ds_cart);
                rcv_cart.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataCart.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ds_cart.clear();

                        for (DataSnapshot data:snapshot.getChildren()){
                            if (data.child("email_buyer").getValue(String.class).equalsIgnoreCase(bill_email_buyer)){

                                //get data cart
                                Cart item = data.getValue(Cart.class);
                                id_cart = data.getKey();
                                String id_food = item.getId_food();
                                String name_food = item.getNameFood();
                                double price_food = item.getPriceFood();
                                int amount = item.getAmount();

                                //add detail from cart
                                DetailBill detail = new DetailBill();
                                detail.setId_food(id_food);
                                detail.setNameFood(name_food);
                                detail.setPriceFood(price_food);
                                detail.setAmount(amount);
                                detail.setBill_id(id_bill);


                                key = mDataDetail.push().getKey();

                                mDataDetail.child(key).setValue(detail);

                            }


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                //delete cart after add them to detail
                mDataCart.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if (dataSnapshot.child("email_buyer").getValue(String.class).equalsIgnoreCase(bill_email_buyer)){
                                id_cart = dataSnapshot.getKey();
                                mDataCart.child(id_cart).removeValue();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(getActivity(), "Đã xác nhận", Toast.LENGTH_SHORT).show();
                BillFragment billFragment = new BillFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, billFragment).commit();

            }
        });

        return root;
    }

    private void initView(View root) {
        rcv_cart = root.findViewById(R.id.rcv_cart);
        iv_back = root.findViewById(R.id.iv_back);
        tvSum = root.findViewById(R.id.tvSum);
        btnOrder = root.findViewById(R.id.btnOrder);
    }
}
