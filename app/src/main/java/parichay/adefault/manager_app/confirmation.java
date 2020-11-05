package parichay.adefault.manager_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class confirmation extends AppCompatActivity {

    String uid;
    String random;
    String service_next;
    TextView name;
    TextView service;
    TextView block;
    TextView room;
    ArrayList<String> address;
    TextView slot;
    Button assign;
    String slotTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        setTitle("Confirmation");
        uid = getIntent().getStringExtra("uid");
        random = getIntent().getStringExtra("random");
        name = (TextView) findViewById(R.id.textView4);
        service = (TextView) findViewById(R.id.textView8);
        block = (TextView) findViewById(R.id.textView5);
        room = (TextView) findViewById(R.id.textView9);
        address = new ArrayList<>();
        slot = (TextView) findViewById(R.id.textView7);
        assign = (Button)findViewById(R.id.button2);

        String currentTime = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());
        int time = Integer.parseInt(currentTime);
        //int time = 21;

        String FinalslotTime;

        if (time >= 6 && time <= 11) {
            slotTime = "Morning";
            FinalslotTime = "Slot : "+slotTime;
        } else if (time >= 12 && time <= 17) {
            slotTime = "Afternoon";
            FinalslotTime = "Slot : "+slotTime;
        } else if (time >= 18 && time <= 20) {
            slotTime = "Evening";
            FinalslotTime = "Slot : "+slotTime;
        } else
        {
            slotTime = "Employees Unavailable!";
            FinalslotTime = slotTime;
            assign.setAlpha(.5f);
            assign.setClickable(false);
        }
        slot.setText(FinalslotTime);

        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText("Name : "+dataSnapshot.child("Name").getValue().toString());
                service.setText("Service : "+dataSnapshot.child("Service").child(random).child("type").getValue().toString());
                block.setText("Block : "+dataSnapshot.child("Block").getValue().toString());
                room.setText("Room : "+dataSnapshot.child("Room").getValue().toString());
                service_next = dataSnapshot.child("Service").child(random).child("type").getValue().toString();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void assign(View view)
    {
        Intent i = new Intent(this,Emplist.class);
        i.putExtra("service",service_next);
        i.putExtra("slot",slotTime);
        i.putExtra("random",random);
        startActivity(i);
    }

    public void cancel(View view)
    {
        FirebaseDatabase.getInstance().getReference().child("Manager").child("Requests").child(random).child("status").setValue(1);
        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Service").child(random).child("status").setValue(2);
        Intent intent = new Intent(getApplicationContext(),tab.class);
        startActivity(intent);
    }
}
