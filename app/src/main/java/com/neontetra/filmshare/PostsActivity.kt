package com.neontetra.filmshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.neontetra.filmshare.databinding.ActivityPostsBinding
import com.neontetra.filmshare.models.Posts
import com.neontetra.filmshare.models.User
import kotlinx.android.synthetic.main.activity_posts.*
import kotlin.math.log

private const val TAG = "PostsActivity"
private const val USERNAME_EXTRA = "USERNAME_EXTRA"

open class PostsActivity : AppCompatActivity() {

    private var signedInUser: User? = null
    private lateinit var fireStoreDb: FirebaseFirestore
    private lateinit var binding: ActivityPostsBinding
    private lateinit var postList: MutableList<Posts>
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        postList = mutableListOf()
        adapter = Adapter(this, postList)

        rvPosts.adapter = adapter
        rvPosts.layoutManager = LinearLayoutManager(this)
        fireStoreDb = FirebaseFirestore.getInstance()

        fireStoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { getCurrentUserTask ->

                signedInUser = getCurrentUserTask.toObject(User::class.java)
                Log.i(TAG, "signed in user: ${signedInUser.toString()}")
            }
            .addOnFailureListener { exception ->
                Log.i(TAG, "No user found for this profile")
            }


        var postsReference = fireStoreDb
            .collection("posts")
            .limit(20)
            .orderBy("creation_time", Query.Direction.DESCENDING)

        val username = intent.getStringExtra(USERNAME_EXTRA)
        if (username != null) {
            postsReference = postsReference.whereEqualTo("user.username", username)
        }

        postsReference.addSnapshotListener { snapshot, exception ->
            if (snapshot == null || exception != null) {
                Log.i(TAG, "Database retrieval error")
                return@addSnapshotListener
            }
            val posts = snapshot.toObjects(Posts::class.java)
            postList.clear()
            postList.addAll(posts)
            adapter.notifyDataSetChanged()
            for (entry in posts) {
                Log.i(TAG, "Entry: $entry")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mProfile) {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra(USERNAME_EXTRA,signedInUser?.username)
            startActivity(intent)

        }
        return super.onOptionsItemSelected(item)
    }
}