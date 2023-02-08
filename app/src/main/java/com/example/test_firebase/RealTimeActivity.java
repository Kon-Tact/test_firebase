package com.example.test_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RealTimeActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance(
            "https://test-e782f-default-rtdb.europe-west1.firebasedatabase.app"
            );
    DatabaseReference messageRef = database.getReference("message");
    private Button btn_Message;
    private TextView tv_readMessage, tvTestInfo;
    private EditText et_Message;
    private static final String TAG = "RealTimeActivity";

    private void initUi() {
        btn_Message = findViewById(R.id.btn_send);
        et_Message = findViewById(R.id.et_message);
        tv_readMessage = findViewById(R.id.tv_readMessage);
        tvTestInfo = findViewById(R.id.tvTestInfo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time);

        initUi();
        sendMessage();
        readMessage();
        readTest();
    }

    public void sendMessage() {
        btn_Message.setOnClickListener(v -> messageRef.setValue(et_Message.getText().toString()));

    }

    public void readMessage() {
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String message = snapshot.getValue(String.class);
                tv_readMessage.setText(message);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Echec de la lecture du message ", error.toException());
            }
        });
    }

        public void readTest() {
            database.getReference("test").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String test = snapshot.getValue(String.class);
                    tvTestInfo.setText(test);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "Echec de la lecture du test ", error.toException());
                }
            });
        }


}