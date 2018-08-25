package com.promise.www.schoollibrary.Rgistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.promise.www.schoollibrary.MainActivity;
import com.promise.www.schoollibrary.R;


import javax.annotation.Nullable;


public class SignActivity extends AppCompatActivity implements  View.OnClickListener {

    private static final String TAG = "EmailPassword";


    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private TextInputEditText mEmailField;
    private TextInputEditText mPasswordField;
    private TextInputEditText mnameField;

    private TextInputLayout mEmailFieldla;
    private TextInputLayout mPasswordFieldla;
    private TextInputLayout mnameFieldla;



    private ProgressDialog progressDialog;
    private InputValidation validation;
    private String userId;

    private Button register, signin;

    // [START declare_auth]



    // [END declare_auth]

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_activity);
        progressDialog = new ProgressDialog(this);
        final String email = getIntent().getStringExtra("regno");
        String password = getIntent().getStringExtra("password");





        validation = new InputValidation(getApplicationContext());
        // Views

        mEmailField = (TextInputEditText) findViewById(R.id.register_field_email);
        mEmailField.setText(email);
        mPasswordField = (TextInputEditText) findViewById(R.id.register_field_password);
        mPasswordField.setText(password);

        mEmailFieldla = (TextInputLayout) findViewById(R.id.register_layout_email);
        mPasswordFieldla = (TextInputLayout) findViewById(R.id.register_layout_passsword);
        // Buttons
        register=(Button) findViewById(R.id.create_new_account);
        signin=(Button) findViewById(R.id.signin);
        register.setOnClickListener(this);
        signin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.create_new_account) {
            startActivity(new Intent(SignActivity.this, RegisterActivity.class));

        } else if (i == R.id.signin) {
            if (!validation.isInputEditTextFilled(mEmailField, mEmailFieldla, getString(R.string.error_message_emailnull))||
                    !validation.isInputEditTextFilled(mPasswordField, mPasswordFieldla, getString(R.string.error_message_emailnull))) {
                return;
            }
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);


        progressDialog.setTitle("Log in");
        progressDialog.setMessage("Processing your account,please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // [START sign_in_with_email]
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("regno" , email).whereEqualTo("password", password).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for(DocumentSnapshot documentSnapshot :queryDocumentSnapshots){
                    if(documentSnapshot.exists()){
                        Intent intent = new Intent(SignActivity.this, MainActivity.class);
                        intent.putExtra("spinnerdecision", "student");
                        intent.putExtra("regno", mEmailField.getText().toString());
                        startActivity(intent);
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(SignActivity.this, "Please create a new account", Toast.LENGTH_SHORT).show();
                    }

                }
              //  Toast.makeText(SignActivity.this, "Please try again..", Toast.LENGTH_SHORT).show();

            }
        });


    }






}



