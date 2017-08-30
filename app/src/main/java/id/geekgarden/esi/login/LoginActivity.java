package id.geekgarden.esi.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;

import id.geekgarden.esi.MainActivity;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.Login.BodyLogin;
import id.geekgarden.esi.data.model.Login.ResponseLogin;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static id.geekgarden.esi.data.apis.ApiService.BASE_URL;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView failedLoginMessage;
    private Api mApi;
    View focusView = null;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApi = ApiService.getervice();
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        failedLoginMessage = (TextView) findViewById(R.id.failed_login);
        mPasswordView= (EditText) findViewById(R.id.password);
        GlobalPreferences GlPref = new GlobalPreferences(getApplicationContext());
        Log.e(TAG, "onCreate: "+ GlPref.read(PrefKey.accessToken,String.class) );
       /* mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL){
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });*/
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                failedLoginMessage.setText("");
                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

    }

    private void attemptLogin() {
        boolean mCancel = this.loginValidation();

        if (mCancel){
            focusView.requestFocus();
        }else {
            loginProcessWithRetrofit(email,password);
        }
    }
    private void loginProcessWithRetrofit(String email, String password) {
        BodyLogin bodyLogin = new BodyLogin();
        bodyLogin.setUsername(email);
        bodyLogin.setPassword(password);
        rx.Observable<ResponseLogin> respon = mApi.authenticate(bodyLogin).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        respon.subscribe(new Observer<ResponseLogin>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: "+ e.getMessage() );
            }

            @Override
            public void onNext(ResponseLogin responseLogin) {
                Log.e(TAG, "onNext: "+ responseLogin.getData().getAccessToken().toString() );
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                GlobalPreferences GlPref = new GlobalPreferences(getApplicationContext());
                GlPref.write(PrefKey.accessToken,"Bearer "+responseLogin.getData().getAccessToken().toString(),String.class);
                /*GlPref.clear();*/
                /*GlPref.read(PrefKey.accessToken,String.class);*/
                Log.e(TAG, "onNext: "+ GlPref.read(PrefKey.accessToken,String.class));
                startActivity(i);
                finish();
            }
        });

       /* Call<Login> mService = mApiService.authenticate(email, password);
        mService.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login mLoginObject = response.body();
                String returnedResponse = mLoginObject.isLogin;
                Toast.makeText(LoginActivity.this, "Returned " + returnedResponse, Toast.LENGTH_LONG).show();
                //showProgress(false);
                if(returnedResponse.trim().equals("1")){
                    // redirect to Main Activity page
                    Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                    loginIntent.putExtra("EMAIL", email);
                    startActivity(loginIntent);
                }
                if(returnedResponse.trim().equals("0")){
                    // use the registration button to register
                    failedLoginMessage.setText(getResources().getString(R.string.registration_message));
                    mPasswordView.requestFocus();
                }*/
            }

    private boolean loginValidation() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();
        boolean cancel = false;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        return cancel;
        
    }

    private boolean isEmailValid(String email) {
        return email.contains("");
    }

    private boolean isPasswordValid(String password) {
        return password.length()> 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}


