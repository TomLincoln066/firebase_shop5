package com.example.shop5

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    var TAG = "WillTest"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        val userEmail: String = "Willtest4@gmail.com"
        val userPassword: String = "123456"

        createAccount("willtest4@gmail.com1568", "123456", "willtest4")
        signIn(userEmail, userPassword)

        val userId = "1"
        articlePost("hi", userId, listOf("aaa"), "aaa", "aaa", "yyyy/mm/dd hh:mm")

//        Log.d(TAG,"${convertLongToDateString(System.currentTimeMillis())}")


//        queryEmail("guo@test.com")


//        queryEmail("tom@gmail.com")

        queryEmail("burger@example.com")

//        setSupportActionBar(toolbar)


        //
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        //


        //創建資料到database


        val db = FirebaseFirestore.getInstance()


        val user = hashMapOf(

            "email" to userEmail,
            "id" to "9o1n5k5b10fG3yCEoy7eL56nnbD3",
            "name" to "name"


        )

        db.collection("member")
            .document(userEmail)
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.d("MainActivity", "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w("MainActivity", "Error adding document", e)
            }

        //創建資料到database
    }

    private fun queryEmail(email: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("member")
            .document(email)
            .get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "${result.id} => ${result.data}")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }


    private fun createAccount(email: String, password: String, name: String) {
        Log.d(TAG, "createAccount:$email")
//        if (!validateForm()) {
//            return
//        }
        val db = FirebaseFirestore.getInstance()


        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
//                    val user = auth.currentUser
                    val userId = auth.currentUser!!.uid
//        createAccount("willtest@gmail.com", "123456")
//        signIn("willtest@gmail.com","123456")
                    val user = hashMapOf(
                        "email" to email,
                        "id" to userId,
                        "name" to name,
                        "password" to password
                    )
// Add a new document with a generated ID

                    db.collection("Users")
                        .document(email)
                        .set(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${userId}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
//                    updateUI(null)
                }
                // [START_EXCLUDE]
//                hideProgressBar()
                // [END_EXCLUDE]
            }
        // [END create_user_with_email]
    }


    //發文

    private fun articlePost(
        content: String,
        id: String,
        tag: List<String>,
        title: String,
        author: String,
        time: String
    ) {


        val db = FirebaseFirestore.getInstance()
        val docID = db.collection("article").document().id
        val user = hashMapOf(

            "article_content" to content,
            "article_id" to docID,
            "article_tag" to tag,
            "article_title" to title,
            "author" to author,
            "article_createTime" to time


        )
        // Add a new document with a generated ID

        db.collection("article")
            .document(docID)
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with content_POST:$content")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document_POST", e)
            }
    }


    //發文


    //sign in

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
//        if (!validateForm()) {
//            return
//        }
//
//        showProgressBar()
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
//                    updateUI(null)
                }
                // [START_EXCLUDE]
//                if (!task.isSuccessful) {
//                    status.setText(R.string.auth_failed)
//                }
//                hideProgressBar()
                // [END_EXCLUDE]
            }
        // [END sign_in_with_email]
    }


    //sign in


    //sign out


    private fun signOut() {
        auth.signOut()
    }

    //sign out


    @SuppressLint("SimpleDateFormat")
    fun convertLongToDateString(systemTime: Long): String {
        return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
            .format(systemTime).toString()
    }


}
