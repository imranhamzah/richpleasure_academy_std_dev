package com.material.nereeducation.activity.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.nereeducation.AppController;
import com.material.nereeducation.R;
import com.material.nereeducation.activity.dashboard.Dashboard;
import com.material.nereeducation.activity.register.RegisterActivity;
import com.material.nereeducation.config.AppConfig;
import com.material.nereeducation.model.Student;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity implements ValueEventListener{
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin,btnFbLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private CallbackManager mCallbackManager;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnFbLogin = findViewById(R.id.btnFbLogin);
        btnLinkToRegister = findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()/* && firebaseAuth.getCurrentUser() != null*/) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
        }

        mCallbackManager = CallbackManager.Factory.create();

        btnFbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnFbLogin.setEnabled(false);
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email","public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                        // ...
                    }
                });
            }
        });

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLoginWithFirebase(email,password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            updateUI(currentUser);
            Intent gotoDashboard = new Intent(getApplicationContext(),Dashboard.class);
            startActivity(gotoDashboard);
            finish();
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        Toast.makeText(LoginActivity.this,"You're logged in!",Toast.LENGTH_SHORT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        final String[] name = new String[1];
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        btnFbLogin.setEnabled(true);
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            session.setLogin(true);

                            String created_at = null;
                            db.addUser(user.getDisplayName(), user.getEmail(), user.getUid(), created_at);

                            //Update on students
                            updateStudentData(user.getUid());

                            Intent gotoDashboard = new Intent(getApplicationContext(),Dashboard.class);
                            startActivity(gotoDashboard);
                            finish();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    private void updateStudentData(final String studentId)
    {

        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final HashMap<Object,Object> studentData = new HashMap<>();

        //To check either student registered or not
        databaseReference.child("students/"+studentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //If Exist then ignore created_at
                if(dataSnapshot.getValue() != null)
                {
                    String studentDataReceived = gson.toJson(dataSnapshot.getValue());
                    Student student = gson.fromJson(studentDataReceived,Student.class);

                    //Update data only
                    studentData.put("last_login", ServerValue.TIMESTAMP);
                    studentData.put("created_at", student.dtCreated);
                    studentData.put("student_email",user.getEmail());
                    studentData.put("student_fullname",user.getDisplayName());
                    studentData.put("student_id",user.getUid());
                    databaseReference.child("students/"+studentId).setValue(studentData);

                }else
                {
                    //Create new
                    studentData.put("last_login", ServerValue.TIMESTAMP);
                    studentData.put("created_at", user.getMetadata().getCreationTimestamp());
                    studentData.put("student_email",user.getEmail());
                    studentData.put("student_fullname",user.getDisplayName());
                    studentData.put("student_id",user.getUid());
                    databaseReference.child("students/"+studentId).setValue(studentData);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void checkLoginWithFirebase(final String email, final String password)
    {

        final String[] name = new String[1];
        pDialog.setMessage("Logging in ...");
        showDialog();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideDialog();
                if(task.isSuccessful())
                {
                    session.setLogin(true);

                    // Now store the user in SQLite
                    final String uid = firebaseAuth.getUid();
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                    DatabaseReference mStudent = databaseReference.child("students/"+uid);

                    mStudent.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String key = dataSnapshot.getKey();
                            if(key.equals(uid))
                            {
                                try {
                                    JSONObject obj = new JSONObject(String.valueOf(dataSnapshot.getValue()));
                                    name[0] = obj.getString("student_fullname");

                                    String created_at = null;
                                    // Inserting row in users table
                                    db.addUser(name[0], email, uid, created_at);

                                    Intent gotoDashboard = new Intent(getApplicationContext(),Dashboard.class);
                                    startActivity(gotoDashboard);
                                    finish();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                }else
                {
                    Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * function to verify login details in mysql db
     */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);

                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this,
                                Dashboard.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}