package com.nganlth.freshfoodadmin.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.nganlth.freshfoodadmin.DAO.CategoryDAO;
import com.nganlth.freshfoodadmin.DAO.CustomerDAO;
import com.nganlth.freshfoodadmin.Model.Customer;
import com.nganlth.freshfoodadmin.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Infor_Customer_Fragment extends Fragment {
    TextView tv_hoTen, tv_gioiTinh, tv_namSinh, tv_usn, tv_sdt, tv_mail;
    DatabaseReference mData;
    ImageView btn_back;
    Button btn_de_customer;
    ArrayList<Customer> dataCustomer;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    CustomerDAO customerDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.infor_customer_fragment, container,false);

        mData = FirebaseDatabase.getInstance().getReference("Buyer");
        initView(view);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerFragment customerFragment = new CustomerFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, customerFragment).commit();

            }
        });

//        SharedPreferences pref = ((AppCompatActivity) getActivity()).getSharedPreferences("buyer.dat",MODE_PRIVATE);
//        String strEmail = pref.getString("Email","");
//        String strPass = pref.getString("password","");
        Bundle getdata =getArguments();
        String email_customer = getdata.getString("email_customer");

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    String sdt = data.getKey();
                    Customer buyer = data.getValue(Customer.class);
                    String email = buyer.getEmail();
                    String address = buyer.getAddress();
                    String name = buyer.getHoTen();
                    String gioiTinh = buyer.getGioiTinh();
                    String ngaySinh = buyer.getNamSinh();

                    if ((email).equals(email_customer)){

                        tv_usn.setText(address);
                        tv_sdt.setText("(+84) "+sdt);
                        tv_mail.setText(email);
                        tv_hoTen.setText(name);
                        tv_gioiTinh.setText(gioiTinh);
                        tv_namSinh.setText(ngaySinh);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_de_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerDAO = new CustomerDAO(getContext());
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(getContext());
                builder1.setMessage("Bạn muốn xóa khách hàng "+ email_customer + "?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                customerDAO.delete(email_customer);
                                Toast.makeText(getContext(), "Xóa thành công tài khoản! ", Toast.LENGTH_SHORT).show();
                                CustomerFragment customerFragment = new CustomerFragment();
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, customerFragment).commit();
                                // sau khi xóa đọc lại dữ liệu
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

        return view;
    }

    private void initView(View view){
        tv_usn = view.findViewById(R.id.tv_usn);
        tv_sdt = view.findViewById(R.id.tv_sdt);
        tv_mail = view.findViewById(R.id.tv_mail);
        tv_hoTen = view.findViewById(R.id.tv_hoTen);
        tv_gioiTinh = view.findViewById(R.id.tv_gioiTinh);
        tv_namSinh = view.findViewById(R.id.tv_namSinh);
        btn_back = view.findViewById(R.id.iv_back);
        btn_de_customer = view.findViewById(R.id.btn_de_customer);


    }
}
