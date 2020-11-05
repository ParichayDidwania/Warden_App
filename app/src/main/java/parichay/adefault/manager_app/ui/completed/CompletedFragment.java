package parichay.adefault.manager_app.ui.completed;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import parichay.adefault.manager_app.R;
import parichay.adefault.manager_app.requests;

public class CompletedFragment extends Fragment {

    ListView l;
    ArrayList<String> names;
    ArrayList<String> services;
    CustomAdapter customAdapter;

    private CompletedViewModel mViewModel;

    public static CompletedFragment newInstance() {
        return new CompletedFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.completed_fragment2, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        l = (ListView)view.findViewById(R.id.listview_c);
        names = new ArrayList<>();
        services = new ArrayList<>();
        customAdapter = new CustomAdapter();
        l.setAdapter(customAdapter);



        FirebaseDatabase.getInstance().getReference().child("Manager").child("Requests").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if((long)dataSnapshot.child("status").getValue()==1) {
                    names.add(dataSnapshot.child("customer").getValue().toString());
                    services.add(dataSnapshot.child("service").getValue().toString());
                    customAdapter.notifyDataSetChanged();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CompletedViewModel.class);
        // TODO: Use the ViewModel
    }

}
