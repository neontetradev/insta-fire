package com.neontetra.filmshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.neontetra.filmshare.databinding.ActivityLoginBinding
import com.neontetra.filmshare.databinding.ActivityPostsBinding

private const val TAG = "ProfileActivity"
class ProfileActivity : PostsActivity() {
//    private lateinit var binding: ActivityLoginBinding


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mLogout){
            Log.i(TAG, "User wants to logout")
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}