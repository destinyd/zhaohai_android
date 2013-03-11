
package DD.Android.Zhaohai.ui.Act;

import DD.Android.Zhaohai.R;
import DD.Android.Zhaohai.R.id;
import DD.Android.Zhaohai.R.layout;
import DD.Android.Zhaohai.R.string;
import DD.Android.Zhaohai.authenticator.AccessToken;
import DD.Android.Zhaohai.authenticator.ZhaohaiAuthenticatorActivity;
import DD.Android.Zhaohai.core.Constants;
import DD.Android.Zhaohai.ui.Ada.AdaTextWatcher;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.*;
import android.widget.TextView.OnEditorActionListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.wishlist.Toaster;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockAccountAuthenticatorActivity;
import roboguice.inject.InjectView;
import roboguice.util.Ln;
import roboguice.util.RoboAsyncTask;
import roboguice.util.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static DD.Android.Zhaohai.core.Constants.Http.*;
import static android.R.layout.simple_dropdown_item_1line;
import static android.accounts.AccountManager.*;
import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static com.github.kevinsawicki.http.HttpRequest.post;

/**
 * Activity to authenticate the ABUser against an API (example API on Parse.com)
 */
public class ActReg extends
        RoboSherlockAccountAuthenticatorActivity {

    public static final String PARAM_CONFIRMCREDENTIALS = "confirmCredentials";
    public static final String PARAM_PASSWORD = "user[password]";
    public static final String PARAM_PASSWORD_CONFIRMATION = "user[password_confirmation]";
    public static final String PARAM_GENDER = "user[userinfo_attributes][gender]";
    public static final String PARAM_EMAIL = "user[email]";
    public static final String PARAM_NAME = "user[name]";
    public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";


    private AccountManager accountManager;

    @InjectView(id.et_email)
    private EditText emailText;

    @InjectView(id.et_name)
    private EditText nameText;

    @InjectView(id.et_password)
    private EditText passwordText;

    @InjectView(id.et_password_confirmation)
    private EditText passwordConfirmationText;

    @InjectView(id.rg_gender)
    private RadioGroup genderRadioGroup;

    @InjectView(id.rb_female)
    private RadioButton femaleButton;

    @InjectView(id.rb_male)
    private RadioButton maleButton;


    @InjectView(id.b_signuup)
    private Button signupButton;

    private TextWatcher watcher = validationTextWatcher();

    private RoboAsyncTask<Boolean> authenticationTask;
    private static String authToken;
    private String authTokenType;

    /**
     * If set we are just checking that the ABUser knows their credentials; this
     * doesn't cause the ABUser's password to be changed on the device.
     */
    private Boolean confirmCredentials = false;

    private String email;

    private String password, password_comfirmation, gender, name;

    private String errors;


    /**
     * In this instance the token is simply the sessionId returned from Parse.com. This could be a
     * oauth token or some other type of timed token that expires/etc. We're just using the parse.com
     * sessionId to prove the example of how to utilize a token.
     */
    private static String token;

    /**
     * Was the original caller asking for an entirely new account?
     */
    protected boolean requestNewAccount = false;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        accountManager = AccountManager.get(this);
        final Intent intent = getIntent();
        email = intent.getStringExtra(PARAM_EMAIL);
        authTokenType = intent.getStringExtra(PARAM_AUTHTOKEN_TYPE);
        authToken = intent.getStringExtra(KEY_AUTHTOKEN);
        requestNewAccount = email == null;
        confirmCredentials = intent.getBooleanExtra(PARAM_CONFIRMCREDENTIALS,
                false);

        setContentView(layout.act_reg);

        passwordConfirmationText.setOnKeyListener(new OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event != null && ACTION_DOWN == event.getAction()
                        && keyCode == KEYCODE_ENTER && signupButton.isEnabled()) {
                    handleReg(signupButton);
                    return true;
                }
                return false;
            }
        });

        passwordText.setOnEditorActionListener(new OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == IME_ACTION_DONE && signupButton.isEnabled()) {
                    handleReg(signupButton);
                    return true;
                }
                return false;
            }
        });

        nameText.addTextChangedListener(watcher);
        emailText.addTextChangedListener(watcher);
        passwordText.addTextChangedListener(watcher);
        passwordConfirmationText.addTextChangedListener(watcher);
    }

    private String getGender(int checkedId) {
        return checkedId == maleButton.getId() ?
                "male" : "female";
    }

    private List<String> userEmailAccounts() {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        List<String> emailAddresses = new ArrayList<String>(accounts.length);
        for (Account account : accounts)
            emailAddresses.add(account.name);
        return emailAddresses;
    }

    private TextWatcher validationTextWatcher() {
        return new AdaTextWatcher() {
            public void afterTextChanged(Editable gitDirEditText) {
                updateUIWithValidation();
            }

        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUIWithValidation();
    }

    private void updateUIWithValidation() {
        boolean populated = populated(emailText) && populated(passwordText) && populated(passwordConfirmationText) && populated(nameText) && (passwordConfirmationText.getText().toString().equals(passwordText.getText().toString()));
        signupButton.setEnabled(populated);
    }

    private boolean populated(EditText editText) {
        return editText.length() > 0;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(string.message_signing_up));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                if (authenticationTask != null)
                    authenticationTask.cancel(true);
            }
        });
        return dialog;
    }

    /**
     * Handles onClick event on the Submit button. Sends username/password to
     * the server for authentication.
     * <p/>
     * Specified by android:onClick="handleReg" in the layout xml
     *
     * @param view
     */
    public void handleReg(View view) {
        if (authenticationTask != null)
            return;

        if (requestNewAccount)
            email = emailText.getText().toString();
        name = nameText.getText().toString();
        password = passwordText.getText().toString();
        password_comfirmation = passwordConfirmationText.getText().toString();
        gender = getGender(genderRadioGroup.getCheckedRadioButtonId());

        showProgress();

        authenticationTask = new RoboAsyncTask<Boolean>(this) {
            public Boolean call() throws Exception {

                HttpRequest request = post(URL_REG)
                        .part(HEADER_PARSE_APP_ID, PARSE_APP_ID)
                        .part(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                        .part(PARAM_EMAIL, email)
                        .part(PARAM_NAME, name)
                        .part(PARAM_PASSWORD, password)
                        .part(PARAM_PASSWORD_CONFIRMATION, password_comfirmation)
                        .part(PARAM_GENDER, gender);


                errors = getResources().getString(string.errors);

                int code = request.code();
                Log.d("Auth", "response=" + code);

                if (code == 201) {
                    String tmp = Strings.toString(request.buffer());
                    Log.d("response body:", tmp);
                    final AccessToken model = JSON.parseObject(tmp, AccessToken.class);
                    token = model.getAccess_token();
                    return true;
                } else if (request.code() == 422) {
                    String tmp = Strings.toString(request.buffer());
                    Log.d("response body:", tmp);
                    Map<String, List<String>> map = JSON.parseObject(tmp, new TypeReference<Map<String, List<String>>>() {
                    });

                    StringBuilder sb = new StringBuilder();
                    sb.append(getResources().getString(string.errors));
                    sb.append(":\n");

                    for (String key : map.keySet()) {
                        String i18nkey = getStringResourceByName("label_" + key);
                        sb.append(i18nkey);
                        sb.append(":");
                        sb.append(TextUtils.join(",", map.get(key)));
                        sb.append("\n");
                    }
                    errors = sb.toString();
                }
                return false;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                Throwable cause = e.getCause() != null ? e.getCause() : e;

                String message;
                // A 404 is returned as an Exception with this message
                if ("Received authentication challenge is null".equals(cause
                        .getMessage()))
                    message = getResources().getString(
                            string.message_bad_credentials);
                else
                    message = cause.getMessage();

                Toaster.showLong(ActReg.this, message);
            }

            @Override
            public void onSuccess(Boolean authSuccess) {
                onAuthenticationResult(authSuccess);
            }

            @Override
            protected void onFinally() throws RuntimeException {
                hideProgress();
                authenticationTask = null;
            }
        };
        authenticationTask.execute();
    }

    /**
     * Called when response is received from the server for confirm credentials
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller.
     *
     * @param result
     */
    protected void finishConfirmCredentials(boolean result) {
        final Account account = new Account(email, Constants.Auth.ZHAOHAI_ACCOUNT_TYPE);
        accountManager.setPassword(account, password);

        final Intent intent = new Intent();
        intent.putExtra(KEY_BOOLEAN_RESULT, result);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Called when response is received from the server for authentication
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller. Also sets
     * the authToken in AccountManager for this account.
     */

    protected void finishReg() {
        final Account account = new Account(email, Constants.Auth.ZHAOHAI_ACCOUNT_TYPE);

        if (requestNewAccount)
            accountManager.addAccountExplicitly(account, password, null);
        else
            accountManager.setPassword(account, password);
        final Intent intent = new Intent();
        authToken = token;
        intent.putExtra(KEY_ACCOUNT_NAME, email);
        intent.putExtra(KEY_ACCOUNT_TYPE, Constants.Auth.ZHAOHAI_ACCOUNT_TYPE);
        if (authTokenType != null
                && authTokenType.equals(Constants.Auth.AUTHTOKEN_TYPE)) {
            intent.putExtra(KEY_AUTHTOKEN, authToken);
            accountManager.setAuthToken(account, authTokenType, authToken);
        }
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Hide progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void hideProgress() {
        dismissDialog(0);
    }

    /**
     * Show progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void showProgress() {
        showDialog(0);
    }

    /**
     * Called when the authentication process completes (see attemptLogin()).
     *
     * @param result
     */
    public void onAuthenticationResult(boolean result) {
        if (result)
            if (!confirmCredentials)
                finishReg();
            else
                finishConfirmCredentials(true);
        else {
            Toaster.showLong(ActReg.this,
                    errors);
        }
    }

    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }
}
