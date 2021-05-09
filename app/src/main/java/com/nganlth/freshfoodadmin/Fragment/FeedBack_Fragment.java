package com.nganlth.freshfoodadmin.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.nganlth.freshfoodadmin.Adapter.FeedbackAdapter;
import com.nganlth.freshfoodadmin.Model.FeedBack;
import com.nganlth.freshfoodadmin.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class FeedBack_Fragment extends Fragment {
    ArrayList<FeedBack> list_feedBack;
    RecyclerView rcv_feedback;
    EditText edFeedBack;
    ImageView imgSend;
    FeedbackAdapter feedbackAdapter;
    DatabaseReference mDataFeedBack;
    String key = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback_fragment, container,false);
        mDataFeedBack= FirebaseDatabase.getInstance().getReference("Feedback");

        initView(view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcv_feedback.setLayoutManager(layoutManager);
        rcv_feedback.hasFixedSize();
        rcv_feedback.setHasFixedSize(false);

        list_feedBack = new ArrayList<FeedBack>();

        SharedPreferences pref = getActivity().getSharedPreferences("custommer.dat",MODE_PRIVATE);
        String strEmail = pref.getString("Email","");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        mDataFeedBack.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_feedBack.clear();
                for (DataSnapshot data:snapshot.getChildren()){

                    //so sánh email để lấy đúng giỏ hàng của khách hàng
//                    if (data.child("email_Buyer").getValue(String.class).equalsIgnoreCase(strEmail)){
                        FeedBack item = data.getValue(FeedBack.class);

                        list_feedBack.add(item);

//                    }
                }
                feedbackAdapter = new FeedbackAdapter(getContext(),list_feedBack);
                rcv_feedback.setAdapter(feedbackAdapter);
                feedbackAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Content = edFeedBack.getText().toString();
                FeedBack feedBack = new FeedBack();
                feedBack.setEmail_Buyer(strEmail);
                feedBack.setContent_fb(Content);
                feedBack.setDateTime(currentDateandTime);
                key = mDataFeedBack.push().getKey();
                mDataFeedBack.child(key).setValue(feedBack);


                Toast.makeText(getContext(), "Đã gửi feedback", Toast.LENGTH_SHORT).show();

                edFeedBack.setText("");

            }
        });
        return view;
    }

    private void initView(View view){
        rcv_feedback = view.findViewById(R.id.rcv_feedback);
        edFeedBack = view.findViewById(R.id.edFeedBack);
        imgSend = view.findViewById(R.id.imgSend);
    }
}