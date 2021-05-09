package com.nganlth.freshfoodadmin.TabFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nganlth.freshfoodadmin.Adapter.DetailAdapter;
import com.nganlth.freshfoodadmin.Model.Bill;
import com.nganlth.freshfoodadmin.Model.DetailBill;
import com.nganlth.freshfoodadmin.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class Tab_Statistical_Day extends Fragment {
    TextView txt_day, txt_hoaDon_day, txt_doanhThu_day;
    public static String day;
    DatabaseReference mDataTK;
    DatabaseReference mDatabill;
    ArrayList<Bill> list_bill;
    public static ArrayList<DetailBill> list_detail;
    private double doanhThu;
    double tong;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tk_day,container,false);
        mDataTK= FirebaseDatabase.getInstance().getReference("Detail");
        mDatabill= FirebaseDatabase.getInstance().getReference("Bill");

        initView(view);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        day = currentDate.substring(0, 2);

        txt_day.setText(currentDate);

        list_bill = new ArrayList<Bill>();
        list_detail = new ArrayList<>();
        mDatabill.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_bill.clear();
                for (DataSnapshot data:snapshot.getChildren()){

                        Bill item = data.getValue(Bill.class);




                        String dayTK = item.getDate().substring(0, 2);
                        if (day.matches(dayTK)) {
                            list_bill.add(item);
                            txt_hoaDon_day.setText(list_bill.size() + "");

                            String Bill_iD = data.getKey();
                            mDataTK.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot data : snapshot.getChildren()) {

                                        if (data.child("bill_id").getValue(String.class).equalsIgnoreCase(Bill_iD)) {

                                            mDataTK.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    DetailBill item1 = data.getValue(DetailBill.class);
                                                    doanhThu = doanhThu + item1.getPriceFood();
//                                                txt_doanhThu_day.setText(doanhThu+"");
                                                    final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                                                    decimalFormat.applyPattern("#,###,###,###");
                                                    txt_doanhThu_day.setText(decimalFormat.format(doanhThu) + "");
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });


                                        }


                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;
    }
    private void initView(View view){
        txt_day = view.findViewById(R.id.txt_day);
        txt_hoaDon_day = view.findViewById(R.id.txt_hoaDon_day);
        txt_doanhThu_day = view.findViewById(R.id.txt_doanhThu_day);
    }
}
