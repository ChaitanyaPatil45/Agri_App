package com.example.agriapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {
    TextView tv1,tv2,tv3;
    EditText etname,etemail,etmobile,etusername,etpassword;
    Button btn;

    ProgressDialog progressDialog;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        auth=FirebaseAuth.getInstance();

        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tvv);
        etname=findViewById(R.id.etregistername);
        etemail=findViewById(R.id.etregisterEmail);
        etmobile=findViewById(R.id.etregisterMobileNo);
        etusername=findViewById(R.id.etregisterUserName);
        etpassword=findViewById(R.id.etregisterpassword);
        btn=findViewById(R.id.SignUpbtn);


     btn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if (etname.getText().toString().isEmpty()) {
                 etname.setError("Enter Your Name");
             }
             if (etemail.getText().toString().isEmpty()) {
                 etemail.setError("Enter Your Email");

             } else if (!etemail.getText().toString().contains("@") && !etemail.getText().toString().contains(".com")) {
                 etemail.setError("Please Enter Valid Email");
             } else if (etmobile.getText().toString().isEmpty()) {
                 etmobile.setError("Please Enter Your Mobile No");
             } else if (etmobile.getText().toString().length() != 10) {
                 etmobile.setError("Invalid Mobile Number");
             }else if(etusername.getText().toString().isEmpty())
             {
                 etusername.setError("Please Enter Your Username");
             }
             else if (etpassword.getText().toString().isEmpty()) {
                 etpassword.setError("Please Enter Password");
             } else if (etpassword.getText().toString().length() < 8) {
                 etpassword.setError("Password Should be greater than 8");
             } else if (!etpassword.getText().toString().matches(".*[A-Z].*")) {
                 etpassword.setError("Enter At Least One Capital letter");
             } else if (!etpassword.getText().toString().matches(".*[a-z].*")) {
                 etpassword.setError("Enter At Least One small letter");
             } else if (!etpassword.getText().toString().matches(".*[0-9].*")) {
                 etpassword.setError("Enter At Least One Number");
             } else {
                 progressDialog = new ProgressDialog(RegisterActivity.this);
                 progressDialog.setTitle("Registration In Process");
                 progressDialog.setMessage("Please Wait");
                 progressDialog.setCanceledOnTouchOutside(true);
                 progressDialog.show();
                 userRegisterDetails();


             }
         }

         private void userRegisterDetails() {

             //over the network data transfer AsyncHttpClient clasas ussed
             //client and server communication
             AsyncHttpClient asyncHttpClient=new AsyncHttpClient();//client and server communication
             RequestParams params=new RequestParams();  // put the data

             params.put("name",etname.getText().toString());//String key Int value;
             params.put("emailid",etemail.getText().toString());
             params.put("mobileno",etmobile.getText().toString());
             params.put("username",etusername.getText().toString());
             params.put("password",etpassword.getText().toString());

             asyncHttpClient.post("http://192.168.43.223:80/AgriAPI/userinfo.php",params,
                     new JsonHttpResponseHandler()
                     {
                         @Override
                         public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                             super.onSuccess(statusCode, headers, response);

                             try {
                                 String status = response.getString("success");
                                 if(status.equals("1"))
                                 {
                                     Toast.makeText(RegisterActivity.this,"Register Done",Toast.LENGTH_SHORT).show();
                                     Intent intent=new Intent(RegisterActivity.this,LogiinActivity.class);
                                     startActivity(intent);
                                 }
                                 else
                                 {
                                     Toast.makeText(RegisterActivity.this,"Already Data Present ",Toast.LENGTH_SHORT).show();
                                 }

                             } catch (JSONException e) {
                                 throw new RuntimeException(e);
                             }
                         }


                         @Override
                         public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                             super.onFailure(statusCode, headers, responseString, throwable);
                             Toast.makeText(RegisterActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
                         }
                     }
             );
         }




     });
    }
}