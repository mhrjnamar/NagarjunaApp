package nagarjuna.com.nagarjunaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import nagarjuna.com.async.AuthenticationAsync;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    public ProgressBar dialog;
    FloatingActionButton skipLogin;
    TextInputEditText userName;
    TextInputEditText password;
    Button btn_login;
    SessionManager manager;
    private ScrollView mainHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        manager = new SessionManager(LoginActivity.this);

        //initilize views
        userName = (TextInputEditText) findViewById(R.id.userId);
        password = (TextInputEditText) findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.btn_login);
        skipLogin = (FloatingActionButton) findViewById(R.id.skipLogin);
        dialog = (ProgressBar) findViewById(R.id.prog);
        mainHolder = (ScrollView) findViewById(R.id.mainHolder);

        //For Login Button Activation
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isButtonReady();
            }
        });

        //set click listenter to views
        btn_login.setOnClickListener(this);
        skipLogin.setOnClickListener(this);

        userName.setText("amar");
        password.setText("amar123");
        hideSkipLogin();
    }

    public void isButtonReady() {
        Boolean isReady = userName.getText().toString().length() > 2 && password.getText().toString().length() > 3;
        btn_login.setEnabled(isReady);
    }

    public void showLoading(Boolean show) {
        dialog.setVisibility(show ? View.VISIBLE : View.GONE);
        mainHolder.setVisibility(!show ? View.VISIBLE : View.GONE);
        skipLogin.setVisibility(!show ? View.VISIBLE : View.GONE);
        btn_login.setVisibility(!show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {

        if (v.equals(btn_login)) {
            //Hide Keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            //Authenticate Login
            AuthenticationAsync authenticate = new AuthenticationAsync(LoginActivity.this);
            authenticate.execute(userName.getText().toString(), password.getText().toString());
        }

        if (v.getId() == R.id.skipLogin) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void hideSkipLogin() {
        skipLogin.setVisibility(manager.isFirstLogin() ? View.VISIBLE : View.GONE);
    }
}
