package com.nganlth.freshfoodadmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nganlth.freshfoodadmin.Adapter.BillAdapter;
import com.nganlth.freshfoodadmin.Adapter.CartAdapter;
import com.nganlth.freshfoodadmin.Model.Bill;
import com.nganlth.freshfoodadmin.Model.Cart;
import com.nganlth.freshfoodadmin.R;

import java.util.ArrayList;
import java.util.Iterator;

public class BillFragment extends Fragment {
    public static RecyclerView rcv_bill;
    DatabaseReference mDataBill;
    ArrayList<Bill> list_bill;
    public static BillAdapter billAdapter;
    ImageView iv_back;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill,container,false);
        mDataBill= FirebaseDatabase.getInstance().getReference("Bill");

        initView(view);



        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcv_bill.setLayoutManager(layoutManager);
        rcv_bill.hasFixedSize();
        rcv_bill.setHasFixedSize(false);

        list_bill = new ArrayList<Bill>();

        mDataBill.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_bill.clear();

                if (snapshot.exists()){
                    list_bill.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()){
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        Bill bill = next.getValue(Bill.class);
                        list_bill.add(bill);

                    }
                    billAdapter = new BillAdapter(getContext(),list_bill);
                    rcv_bill.setAdapter(billAdapter);
                    billAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        return view;
    }
    private void initView(View view){
        rcv_bill = view.findViewById(R.id.rcv_bill);


    }
}
