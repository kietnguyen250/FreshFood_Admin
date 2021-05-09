package com.nganlth.freshfoodadmin.BottomSheet;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nganlth.freshfoodadmin.Fragment.CartFragment;
import com.nganlth.freshfoodadmin.Model.Bill;
import com.nganlth.freshfoodadmin.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class BottomSheetBillAdd extends BottomSheetDialogFragment {
    Button btndate, btnAddBill;
    DatabaseReference mDataBill;
    ArrayList<Bill> ds_bill;
    String edbillID = "";
    EditText edBillId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_add_bill, container, false);
        mDataBill = FirebaseDatabase.getInstance().getReference("Bill");

        initView(view);
        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date();
            }
        });



        btnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bill_id = edBillId.getText().toString().trim();
                String date = btndate.getText().toString().trim();
                Bill bill = new Bill();
                bill.setBill_id(bill_id);
                bill.setDate(date);
                edbillID = mDataBill.push().getKey();
                mDataBill.child(edbillID).setValue(bill);

                dismiss();
                SharedPreferences preferences = ((AppCompatActivity)getActivity()).getSharedPreferences("bill.dat", MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences.edit();

                    editor.putString("bill_id", bill_id);
                    editor.putString("Date", date);


                editor.commit();
                CartFragment cartFragment = new CartFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, cartFragment).commit();
                Toast.makeText(getContext(), "Thêm hóa đơn thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void initView(View view){
        btndate = view.findViewById(R.id.btndate);
        btnAddBill = view.findViewById(R.id.btnAddBill);
        edBillId = view.findViewById(R.id.edBillID);
    }

    private void Date(){
        Date today =new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        final int months = calendar.get(Calendar.MONTH);
        final int years = calendar.get(Calendar.YEAR);

        final Calendar calendar1 = Calendar.getInstance();
        int date = calendar1.get(Calendar.DAY_OF_MONTH);
        int month = calendar1.get(Calendar.MONTH);
        int year = calendar1.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int i, int i1, int i2) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                calendar1.set(i,i1,i2);
                btndate.setText(simpleDateFormat.format(calendar1.getTime()));
            }
        },years, months,dayOfWeek);
        datePickerDialog.show();
    }
}
