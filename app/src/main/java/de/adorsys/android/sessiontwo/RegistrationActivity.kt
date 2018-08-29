package de.adorsys.android.sessiontwo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegistrationActivity : AppCompatActivity() {

    private val credentialService : CredentialService = CredentialService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        setUsername()
        setPassword()
        setEmail()

        setupLoginLink()
        setupRegisterButtonClickListener()
    }

    private fun setUsername() {
        val userNameFromLogin = intent.getStringExtra(Constants.USERNAME_EXTRA_KEY)
        val usernameEditText = findViewById<TextView>(R.id.registrationUserNameEditText)
        usernameEditText.text = userNameFromLogin
    }

    private fun setPassword() {
        val passwordFromLogin = intent.getStringExtra(Constants.PASSWORD_EXTRA_KEY)
        val passwordEditText = findViewById<TextView>(R.id.registrationPasswordEditText)
        passwordEditText.text = passwordFromLogin
    }

    private fun setEmail() {
        val emailFromLogin = intent.getStringExtra(Constants.EMAIL_EXTRA_KEY)
        val emailEditText = findViewById<TextView>(R.id.registrationEmailEditText)
        emailEditText.text = emailFromLogin
    }

    private fun setupLoginLink() {
        val registrationHintTextView = findViewById<TextView>(R.id.registrationHintTextView)

        registrationHintTextView.setOnClickListener {
            switchToRegistrationActivity()
        }
    }

    private fun switchToRegistrationActivity() {
        val loginIntent = createLoginIntent()
        startActivity(loginIntent)

        finish()
    }

    private fun createLoginIntent(): Intent {
        val loginIntent = Intent(this, MainActivity::class.java)

        val userNameEditText = findViewById<EditText>(R.id.registrationUserNameEditText)
        val passwordEditText = findViewById<EditText>(R.id.registrationPasswordEditText)
        val emailEditText = findViewById<EditText>(R.id.registrationEmailEditText)

        loginIntent.putExtra(Constants.USERNAME_EXTRA_KEY, userNameEditText.text.toString())
        loginIntent.putExtra(Constants.PASSWORD_EXTRA_KEY, passwordEditText.text.toString())
        loginIntent.putExtra(Constants.EMAIL_EXTRA_KEY, emailEditText.text.toString())

        return loginIntent
    }

    private fun setupRegisterButtonClickListener() {
        val registrationButton = findViewById<Button>(R.id.registrationButton)

        registrationButton.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val credentials = readCredentialsFromView()
        credentialService.save(credentials)

        showToastMessage("saved!")
    }

    private fun readCredentialsFromView(): Credentials {
        val userNameEditText = findViewById<EditText>(R.id.registrationUserNameEditText)
        val passwordEditText = findViewById<EditText>(R.id.registrationPasswordEditText)

        val username = userNameEditText.text.toString()
        val password = passwordEditText.text.toString()

        return Credentials(username, password)
    }

    private fun showToastMessage(text: String) {
        Toast.makeText(this, "RegistrationActivity: $text", Toast.LENGTH_LONG).show()
    }
}
