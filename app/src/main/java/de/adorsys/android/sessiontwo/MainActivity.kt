package de.adorsys.android.sessiontwo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val credentialService : CredentialService = CredentialService(this)
    private lateinit var emailFromRegistraton : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUsername()
        setPassword()
        readEmailFromIntend()

        setupRegistrationLink()

        setupLoginButtonClickListener()
    }

    private fun readEmailFromIntend() {
        if(intent.hasExtra(Constants.EMAIL_EXTRA_KEY)) {
            emailFromRegistraton = intent.getStringExtra(Constants.EMAIL_EXTRA_KEY)
        } else {
            emailFromRegistraton = ""
        }
    }

    private fun setUsername() {
        val userNameFromRegistration = intent.getStringExtra(Constants.USERNAME_EXTRA_KEY)
        val usernameEditText = findViewById<TextView>(R.id.loginUserNameEditText)
        usernameEditText.text = userNameFromRegistration
    }

    private fun setPassword() {
        val passwordFromRegistration = intent.getStringExtra(Constants.PASSWORD_EXTRA_KEY)
        val passwordEditText = findViewById<TextView>(R.id.loginPasswordEditText)
        passwordEditText.text = passwordFromRegistration
    }

    private fun setupLoginButtonClickListener() {
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            tryToLogin()
        }
    }

    private fun tryToLogin() {
        val credentials = readCredentialsFromViews()

        if (credentialService.isCorrect(credentials)) {
            showToastMessage("correct!")
        } else {
            showToastMessage("wrong!")
        }
    }

    private fun readCredentialsFromViews(): Credentials {
        val userNameEditText = findViewById<EditText>(R.id.loginUserNameEditText)
        val passwordEditText = findViewById<EditText>(R.id.loginPasswordEditText)

        val username = userNameEditText.text.toString()
        val password = passwordEditText.text.toString()

        return Credentials(username, password)
    }

    private fun showToastMessage(text: String) {
        Toast.makeText(this, "MainActivity: $text", Toast.LENGTH_LONG).show()
    }

    private fun setupRegistrationLink() {
        val registrationHintTextView = findViewById<TextView>(R.id.loginHintTextView)

        registrationHintTextView.setOnClickListener {
            switchToLoginActivity()
        }
    }

    private fun switchToLoginActivity() {
        val registrationIntent = createRegistrationIntent()
        startActivity(registrationIntent)

        finish()
    }

    private fun createRegistrationIntent(): Intent {
        val registrationIntent = Intent(this, RegistrationActivity::class.java)

        val userNameEditText = findViewById<EditText>(R.id.loginUserNameEditText)
        val passwordEditText = findViewById<EditText>(R.id.loginPasswordEditText)

        registrationIntent.putExtra(Constants.USERNAME_EXTRA_KEY, userNameEditText.text.toString())
        registrationIntent.putExtra(Constants.PASSWORD_EXTRA_KEY, passwordEditText.text.toString())
        registrationIntent.putExtra(Constants.EMAIL_EXTRA_KEY, emailFromRegistraton)

        return registrationIntent
    }
}
