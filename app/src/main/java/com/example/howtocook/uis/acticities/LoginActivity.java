package com.example.howtocook.uis.acticities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.example.howtocook.R;
import com.example.howtocook.model.basemodel.Users;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button login_btn_register;
    LoginButton login_button_Facebook;
    EditText DangNhap_EditText_Email;
    EditText DangNhap_EditText_MatKhau;
    Button DangNHap_Button_DangNhap;

    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;

    ProgressDialog progressDialog;


    private Users currentUser;

    private static final String TAG = "aaa";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_btn_register = findViewById(R.id.login_btn_register);
        login_button_Facebook = findViewById(R.id.login_button_Facebook);
        DangNhap_EditText_Email = findViewById(R.id.DangNhap_EditText_Email);
        DangNhap_EditText_MatKhau = findViewById(R.id.DangNhap_EditText_MatKhau);
        DangNHap_Button_DangNhap = findViewById(R.id.DangNHap_Button_DangNhap);

        currentUser = new Users();
        //checkLoginStatus();

        mAuth = FirebaseAuth.getInstance();

        DangNHap_Button_DangNhap.setOnClickListener(this);


        callbackManager = CallbackManager.Factory.create();

        login_button_Facebook.setReadPermissions(Arrays.asList("email","public_profile"));

        login_btn_register.setOnClickListener(this);


        login_button_Facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("Đăng nhập...");
                progressDialog.show();
                handleFacebookAccessToken(loginResult.getAccessToken());
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);

            }

            @Override
            public void onCancel() {
                Log.d("login", "cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("login", "fail");
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("aaaa ", "aaaa + "+user.getDisplayName()+" "+user.getEmail() + " "+ user.getPhotoUrl().toString());
                            Users users = new Users(user.getEmail(), " ", user.getDisplayName(), user.getPhotoUrl().toString(), false);
                            writeUser(users);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_register:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.DangNHap_Button_DangNhap:
                String username = DangNhap_EditText_Email.getText().toString().trim();
                String password = DangNhap_EditText_MatKhau.getText().toString().trim();

                if (TextUtils.isEmpty(username)){
                    DangNhap_EditText_Email.setError("Không được để trống trường email");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    DangNhap_EditText_MatKhau.setError("Vui lòng nhập mật khẩu");
                    return;
                }

                signIn(username, password);
                break;

        }
    }

    // ghi du lieu vao firebase
    private void writeUser(Users users){


        FirebaseUser curren_user = mAuth.getCurrentUser();
        // lay gia tri nhap tu activty
        users.setUserId(curren_user.getUid());


        // day du lieu len firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        String key = reference.child("Users").push().getKey();

        Map<String, Object> user_values = users.toMap();

        Map<String, Object> child_add = new HashMap<>();
        child_add.put("/Users/"+curren_user.getUid(), user_values);

        Task<Void> task = reference.updateChildren(child_add);

        if(task.isSuccessful() == false){
            progressDialog.dismiss();
        } else {
            Toast.makeText(this, "Login fail", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    private void signIn(String email, String password) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đăng nhập...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            gotoHomeScreen();
                            progressDialog.dismiss();
                        } else {
                            Log.e(TAG, "onComplete: Failed=" + task.getException().getMessage());
                            showDialog();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private void gotoHomeScreen() {
        Intent intent_home = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent_home);
        this.finish();
    }

    // chuyen den trang dang ky
    private void gotoRegisterScreen(){
        Intent intent_register = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent_register);
    }


    // phuong thuc show dialog
    public void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_fail);

        Button btn_register, btn_again;
        TextView txt_message_fail;

        btn_register = dialog.findViewById(R.id.btn_action);
        btn_again = dialog.findViewById(R.id.btn_again);
        txt_message_fail = dialog.findViewById(R.id.txt_message_fail);

        txt_message_fail.setText("Có vẻ như "+DangNhap_EditText_Email.getText().toString()+" không khớp với tài" +
                "khoản hiện tại. Nếu bạn chưa có tài khoản MyTour, bạn có thể tạo tài khoản ngay bây giờ.");

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegisterScreen();
            }
        });

        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }


}
