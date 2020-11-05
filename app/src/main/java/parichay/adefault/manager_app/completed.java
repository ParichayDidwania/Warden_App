package parichay.adefault.manager_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import parichay.adefault.manager_app.ui.completed.CompletedFragment;

public class completed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CompletedFragment.newInstance())
                    .commitNow();
        }
    }
}
