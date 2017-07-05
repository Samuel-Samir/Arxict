package samuel.example.com.arxict.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import samuel.example.com.arxict.R;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.input_email)
    EditText emailEditText;

    @BindView(R.id.input_password)
    EditText passwordEditText;

    @BindView(R.id.btn_login)
    Button loginButton;

    @BindView(R.id.link_signup)
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(SignInActivity.this);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this ,SignUpActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this ,MainActivity.class));

            }
        });
    }
}
