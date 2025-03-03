package com.example.agriapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LogiinActivity extends AppCompatActivity {
    TextView tvtitle,tvNewUser,tvRegister;
    EditText etEmail,etPassword;
    Button btn;
    CheckBox checkBox;
    ProgressDialog progressDialog;
    CardView cardView;

    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_logiin);
       // tvtitle=findViewById(R.id.tvtt);
        tvNewUser=findViewById(R.id.tvnewUser);
        tvRegister=findViewById(R.id.tvnew);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btn=findViewById(R.id.btnLogin);
        //checkBox=findViewById(R.id.cbshowhide);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean email=etEmail.getText().toString().isEmpty();
                boolean pass=etPassword.getText().toString().isEmpty();

                if(etEmail.getText().toString().isEmpty())
                {
                    etEmail.setError("Enter Your Email");
                }else if(!etEmail.getText().toString().contains("@")&&!etEmail.getText().toString().contains(".com"))
                {
                    etEmail.setError("Please Enter Valid Email");
                }else if(etPassword.getText().toString().isEmpty())
                {
                    etPassword.setError("Please Enter Password");
                }else if(etPassword.getText().toString().length()<8)
                {
                    etPassword.setError("Password Should be greater than 8");
                }
                else
                {
                  /*  auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                //Toast.makeText(this,"Sing Up Successful ",Toast.LENGTH_SHORT).show();
                                Toast.makeText(LogiinActivity.this,"Sign Up Done",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LogiinActivity.this,RegisterActivity.class));
                            }
                        }
                    });*/
                 progressDialog=new ProgressDialog(LogiinActivity.this);
                  progressDialog.setTitle("Please Wait");
                 progressDialog.setMessage("Login In Process");
                  progressDialog.show();

              userLogin();
                }

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(LogiinActivity.this,RegisterActivity.class);
               startActivity(intent);
            }
        });


    }

   private void userLogin() {

        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();

        params.put("email",etEmail.getText().toString());
        params.put("password",etPassword.getText().toString());

        client.post("http://192.168.43.223:80/AgriAPI/userinfo.php",params,
              new JsonHttpResponseHandler()
              {
                  @Override
                  public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                      super.onSuccess(statusCode, headers, response);
                      try {
                          String status=response.getString("email");
                          if(status.equals("1"))
                          {
                              Intent intent=new Intent(LogiinActivity.this,HomeActivity.class);
                              startActivity(intent);
                          }
                          else
                          {
                              Toast.makeText(LogiinActivity.this,"Invaid Crential",Toast.LENGTH_SHORT).show();
                          }
                      } catch (JSONException e) {
                          throw new RuntimeException(e);
                      }
                  }

                  @Override
                  public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                      super.onFailure(statusCode, headers, throwable, errorResponse);
                  }
              }







        );




    }}
