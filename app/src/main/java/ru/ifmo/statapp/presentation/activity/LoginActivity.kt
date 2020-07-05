package ru.ifmo.statapp.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import ru.ifmo.statapp.App
import ru.ifmo.statapp.R
import ru.ifmo.statapp.domain.api.LoginIteractor
import javax.inject.Inject


const val LOG_TAG = "LoginActivity"

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var loginIteractor: LoginIteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        setContentView(R.layout.activity_login)
        login_btn.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    //Performs log in click
    override fun onClick(v: View?) {
        val email = email_field.text.toString()
        val password = password_field.text.toString()
        disposable.add(loginIteractor.logIn(email, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ info ->
                Log.d(LOG_TAG, "$info")
                Toast.makeText(this@LoginActivity, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                //TODO(GO TO MAIN ACTIVITY)
                startActivity(MainActivity.createIntent(this))
            }, { e ->
                Log.d(LOG_TAG, "Error", e)
                Toast.makeText(this@LoginActivity, "Smth went wrong", Toast.LENGTH_SHORT).show()
            }))
    }
}