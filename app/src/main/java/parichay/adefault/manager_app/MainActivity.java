package parichay.adefault.manager_app;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText emailId;
    EditText passwd;
    FirebaseAuth firebaseAuth;

    public void login(View view)
    {
        String emailID = emailId.getText().toString();
        String paswd = passwd.getText().toString();
        if (emailID.isEmpty()) {
            emailId.setError("Provide your Email first!");
            emailId.requestFocus();
        } else if (paswd.isEmpty()) {
            passwd.setError("Set your password");
            passwd.requestFocus();
        } else if (emailID.isEmpty() && paswd.isEmpty()) {
            Toast.makeText(MainActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
        } else if (!(emailID.isEmpty() && paswd.isEmpty())) {
            firebaseAuth.signInWithEmailAndPassword(emailID, paswd).addOnCompleteListener(MainActivity.this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(MainActivity.this.getApplicationContext(),
                                "Login unsuccessful: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this.getApplicationContext(),
                                "Login successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, tab.class));
                    }
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailId = findViewById(R.id.editText);
        passwd = findViewById(R.id.editText2);
        firebaseAuth = FirebaseAuth.getInstance();

    }
}
