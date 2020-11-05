package parichay.adefault.manager_app;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class requests extends Fragment {
    ArrayList<String> names;
    ArrayList<String> services;
    ArrayList<String> uid;
    ArrayList<String> random;
    ListView l;
    int cnt=0;

    CustomAdapter customAdapter;

    public requests() {
        // Required empty public constructor
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {



        names = new ArrayList<>();
        services = new ArrayList<>();
        uid = new ArrayList<>();
        random = new ArrayList<>();

        try{

        }catch (Exception e){}

        l=(ListView)view.findViewById(R.id.list);
        customAdapter = new CustomAdapter();
        l.setAdapter(customAdapter);

        FirebaseDatabase.getInstance().getReference().child("Manager").child("Requests").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if((long)dataSnapshot.child("status").getValue()==0) {
                    names.add(dataSnapshot.child("customer").getValue().toString());
                    services.add(dataSnapshot.child("service").getValue().toString());
                    customAdapter.notifyDataSetChanged();
                    uid.add(dataSnapshot.child("uid").getValue().toString());
                    random.add(dataSnapshot.getKey());
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

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(),confirmation.class);
                intent.putExtra("uid",uid.get(i));
                intent.putExtra("random",random.get(i));
                startActivity(intent);
            }
        });


    }

    class CustomAdapter extends BaseAdapter {

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

            view = getLayoutInflater().inflate(R.layout.custom_layout,null);
            TextView name = (TextView)view.findViewById(R.id.textView6);
            TextView serv = (TextView)view.findViewById(R.id.textView3);

            name.setText("Customer : "+names.get(i));
            serv.setText("Service : "+services.get(i));

            return view;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_requests, container, false);

    }

}
