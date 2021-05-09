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
import androidx.appcompat.app.AppCompatActivity;
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
import com.nganlth.freshfoodadmin.Adapter.DetailAdapter;
import com.nganlth.freshfoodadmin.Model.Cart;
import com.nganlth.freshfoodadmin.Model.DetailBill;
import com.nganlth.freshfoodadmin.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DetailFragment  extends Fragment {
    public static RecyclerView rcv_detail;
    TextView tvSum;
    ImageView iv_back;
    ImageView iv_AddDetail;
    DatabaseReference mDataDetail;

    double thanhTien;

    DetailAdapter detailAdapter;
    ArrayList<DetailBill> list_detail;
    String id_cart = "";
    String key = "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        initView(root);
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
        String bill_email_buyer = getdata.getString("bill_email_buyer");
        String id_bill = getdata.getString("id_bill_detail");

//        Toast.makeText(getActivity(), id_bill+"", Toast.LENGTH_SHORT).show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcv_detail.setLayoutManager(layoutManager);
        rcv_detail.hasFixedSize();
        rcv_detail.setHasFixedSize(false);

        list_detail = new ArrayList<DetailBill>();

        mDataDetail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_detail.clear();
                for (DataSnapshot data:snapshot.getChildren()){

                    //lấy dữ liệu chi tiết theo mã hóa đơn
                    if (data.child("bill_id").getValue(String.class).equalsIgnoreCase(id_bill)){
                        DetailBill item = data.getValue(DetailBill.class);

                        list_detail.add(item);

                        thanhTien = thanhTien+item.getPriceFood();
//                        tvSum.setText(thanhTien+" Đ");

                        // Đặt kiểu số-----------------
                        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                        decimalFormat.applyPattern("#,###,###,###");
                        tvSum.setText(decimalFormat.format(thanhTien) + " Đ");

                        //------------------------------
                    }

                }
                detailAdapter = new DetailAdapter(getContext(),list_detail);
                rcv_detail.setAdapter(detailAdapter);
                detailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        iv_AddDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("bill_email_buyer", bill_email_buyer);
                args.putString("id_bill_cart", id_bill);
                CartFragment cartFragment = new CartFragment();
                cartFragment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, cartFragment).commit();
            }
        });

        return root;
    }

    private void initView(View root) {
        rcv_detail = root.findViewById(R.id.rcv_detail);
        iv_back = root.findViewById(R.id.iv_back);
        tvSum = root.findViewById(R.id.tvSum);
        iv_AddDetail = root.findViewById(R.id.iv_AddDetail);
    }
}

