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

import id.geekgarden.esi.helper.UiUtils;
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
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(view -> {
            failedLoginMessage.setText("");
            attemptLogin();
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
        rx.Observable<ResponseLogin> respon = mApi.authenticate(bodyLogin)
            .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        respon.subscribe(responseLogin -> {
            Log.e(TAG, "onNext: " + responseLogin.getData().getUserType().toString());
            String usertype = responseLogin.getData().getUserType().toString();
            if (usertype.equals("Client")) {
                UiUtils.showToast(getApplicationContext(),
                    "You are client. Use the SABA customer application.");
            } else {
                String type = responseLogin.getData().getUserType();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                GlobalPreferences GlPref = new GlobalPreferences(getApplicationContext());
                GlPref.write(PrefKey.accessToken,
                    "Bearer " + responseLogin.getData().getAccessToken().toString(), String.class);
                GlPref.write(PrefKey.userType, usertype, String.class);
                UiUtils.showToast(getApplicationContext(),"Logged In As" + " " + type);
                startActivity(i);
                finish();
            }
        }, throwable -> {
            Log.e("loginProcess", "LoginActivity" + throwable.getMessage());
            if (throwable.getMessage().equals("HTTP 401 Unauthorized")){
                UiUtils.showToast(getApplicationContext(),"Wrong Username And Password");
                mEmailView.setError("This");
                mPasswordView.setError("This");
            }
        });
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