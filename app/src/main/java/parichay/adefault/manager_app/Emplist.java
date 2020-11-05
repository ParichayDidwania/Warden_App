package parichay.adefault.manager_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Emplist extends AppCompatActivity {

    ListView l;
    String service;
    String random;
    String slot;

    ArrayList<String> names;
    ArrayList<String> phones;
    ArrayList<String> services;
    ArrayList<String> emp_id;
    HashMap<String,String> updated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplist);

        setTitle("Assign");

        service = getIntent().getStringExtra("service");
        random = getIntent().getStringExtra("random");
        slot = getIntent().getStringExtra("slot");

        l = (ListView)findViewById(R.id.emp);
        names = new ArrayList<String>();
        phones = new ArrayList<String>();
        services = new ArrayList<String>();
        emp_id = new ArrayList<>();
        updated = new HashMap<>();
        final CustomAdapter customAdapter = new CustomAdapter();
        l.setAdapter(customAdapter);

        FirebaseDatabase.getInstance().getReference().child("Employee").child(slot).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(dataSnapshot.child("type").getValue().toString().equals(service) && dataSnapshot.child("status").getValue().toString().equals("open")) {
                    names.add(dataSnapshot.child("Name").getValue().toString());
                    services.add(service);
                    customAdapter.notifyDataSetChanged();
                    emp_id.add(dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Manager").child("Requests").child(random).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                updated.put(dataSnapshot.getKey(),dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                updated.put("status","confirmed");
                FirebaseDatabase.getInstance().getReference().child("Employee").child(slot).child(emp_id.get(i)).child("status").setValue("booked");
                FirebaseDatabase.getInstance().getReference().child("Manager").child("Requests").child(random).child("status").setValue(1);
                FirebaseDatabase.getInstance().getReference().child("Users").child(updated.get("uid")).child("Service").child(random).child("status").setValue(1);
                Intent intent = new Intent(getApplicationContext(),tab.class);
                startActivity(intent);


            }
        });


    }

    class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.emp_list_custom,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView3);

            Bitmap src = BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.deafult_image_icon2);

            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), src);
            circularBitmapDrawable.setCornerRadius(360);
            imageView.setImageDrawable(circularBitmapDrawable);

            TextView name = (TextView) view.findViewById(R.id.textView4);
            TextView service = (TextView) view.findViewById(R.id.textView6);

            name.setText(names.get(i));
            service.setText(services.get(i));

            return view;

        }
    }
}
