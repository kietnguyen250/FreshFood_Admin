package com.nganlth.freshfoodadmin.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nganlth.freshfoodadmin.Adapter.CustomerAdapter;
import com.nganlth.freshfoodadmin.Model.Customer;

import java.util.ArrayList;

import static com.nganlth.freshfoodadmin.Fragment.CustomerFragment.rcv_customer;

public class CustomerDAO {
    // Write a message to the database
    DatabaseReference mData;
    private Context context;
    String key;
    CustomerAdapter adapter;


    public CustomerDAO( Context context) {
        //Tạo bảng thể loại
        this.mData = FirebaseDatabase.getInstance().getReference("Buyer");
        this.context = context;
    }
    // Read all list Category
    public ArrayList<Customer> getAllCustomer(){
        final ArrayList<Customer> dataCustomer = new ArrayList<Customer>();
        // Hàm đọc dữ liệu liên tục, add vào data
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataCustomer.clear(); // Xóa data cũ đi trước khi lấy value
                // Đếm all children trong bảng
                for (DataSnapshot data:snapshot.getChildren()){
                    // Gọi model ra getValue để lấy value và add vào array
                    Customer item = data.getValue(Customer.class);
                    dataCustomer.add(item);

                    adapter = new CustomerAdapter(context,dataCustomer);
                    rcv_customer.setAdapter(adapter);

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mData.addValueEventListener(listener);
        return dataCustomer;
    }

    public void delete(final String email) {

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("email").getValue(String.class).equalsIgnoreCase(email)){
                        key = data.getKey();

                        Log.d("getKey", "onCreate: key :" + key);


                        mData.child(key).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
