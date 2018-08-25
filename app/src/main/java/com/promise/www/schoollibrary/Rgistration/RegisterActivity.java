package com.promise.www.schoollibrary.Rgistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.promise.www.schoollibrary.R;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements  View.OnClickListener{

    private static final String TAG = "EmailPassword";
    private TextInputEditText mPasswordField;
    private TextInputEditText mnameField;
    private TextInputLayout mPasswordFieldla;
    private TextInputLayout mnameFieldla;

    private ProgressDialog progressDialog;
    private InputValidation validation;
    private AppCompatButton register, signin;

    private TextInputEditText regNo;
    private TextInputLayout mRegNo;


    // [END declare_auth]

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        validation = new InputValidation(getApplicationContext());
        mPasswordField = (TextInputEditText) findViewById(R.id.register_field_password);
        mnameField = (TextInputEditText) findViewById(R.id.register_field_name);
        regNo = (TextInputEditText) findViewById(R.id.register_field_reg_no);

        mnameFieldla = (TextInputLayout) findViewById(R.id.register_layout_name);
        mPasswordFieldla = (TextInputLayout) findViewById(R.id.register_layout_passsword);
        mRegNo = (TextInputLayout) findViewById(R.id.register_layout_reg_no);

        // Buttons
        register=(AppCompatButton) findViewById(R.id.registerAccount);
        signin=(AppCompatButton) findViewById(R.id.siginagian);
        register.setOnClickListener(this);
        signin.setOnClickListener(this);
        // [START initialize_auth]


        progressDialog = new ProgressDialog(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.registerAccount) {


            if (!validation.isInputEditTextFilled(mnameField, mnameFieldla, getString(R.string.error_message_emailnull))||

                    !validation.isInputEditTextFilled(regNo, mRegNo, getString(R.string.error_message_emailnull))||!
                    validation.isInputEditTextFilled(mPasswordField, mPasswordFieldla, getString(R.string.error_message_emailnull))) {
                return;
            }

            if(Pattern.matches("[a-zA-Z]+", regNo.getText().toString()) || regNo.getText().toString().matches("\\d+(?:\\.\\d+)?")){
                Toast.makeText(getApplicationContext(), "check your student reg no", Toast.LENGTH_SHORT).show();
                return;
            }


            createAccount(regNo.getText().toString(),  mPasswordField.getText().toString(),
                    mnameField.getText().toString());
        } else if (i == R.id.siginagian) {

            startActivity(new Intent(RegisterActivity.this, SignActivity.class));
            finish();
        }
    }



    @Override
    public void onStart() {
        super.onStart();

    }


    private void createAccount(final String regno, final String password, final String name) {

        progressDialog.setTitle("Creating new account");
        progressDialog.setMessage("please wait, while we create an account for you");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("regno", regno);
        map.put("password", password);

        FirebaseFirestore.getInstance().collection("users").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
           if(task.isSuccessful()){
               Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
              Intent intent = new Intent(RegisterActivity.this, SignActivity.class);
              intent.putExtra("regno", regno);
              intent.putExtra("password", password);
              startActivity(intent);
               finish();
           }else {
               Toast.makeText(RegisterActivity.this, "Please try again later", Toast.LENGTH_LONG).show();
           }

            }
        });






    }
}



