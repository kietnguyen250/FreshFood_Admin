package com.nganlth.freshfoodadmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nganlth.freshfoodadmin.Adapter.CustomerAdapter;
import com.nganlth.freshfoodadmin.DAO.CustomerDAO;
import com.nganlth.freshfoodadmin.Model.Customer;
import com.nganlth.freshfoodadmin.R;

import java.util.ArrayList;

public class CustomerFragment extends Fragment {
    // Biến toàn cục
    public static RecyclerView rcv_customer;
    ImageView iv_back;
    CustomerDAO customerDAO;
    ArrayList<Customer> dataCustomer;
    public static CustomerAdapter categoryAdapter;
    public static DatabaseReference mData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer,container,false);
        rcv_customer = view.findViewById(R.id.rcv_customer);
        iv_back = view.findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profile_Fragment = new ProfileFragment();
//                fragment_food.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, profile_Fragment).commit();
            }
        });

        // -----------------
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcv_customer.setLayoutManager(layoutManager);
        rcv_customer.hasFixedSize();
        rcv_customer.setHasFixedSize(true);
        // -----------------

        customerDAO = new CustomerDAO(getContext());
        mData = FirebaseDatabase.getInstance().getReference("Buyer");
        dataCustomer = new ArrayList<Customer>();
        dataCustomer = customerDAO.getAllCustomer();




        return view;
    }
}
