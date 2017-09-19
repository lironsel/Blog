package com.moveosoftware.blog.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moveosoftware.blog.Data.BlogRecyclerAdapter;
import com.moveosoftware.blog.Model.Blog;
import com.moveosoftware.blog.R;

import java.util.ArrayList;
import java.util.List;

public class PostListActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    private BlogRecyclerAdapter blogRecyclerAdapter;
    private List<Blog> blogList;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
            //get the db
        mDatabase = FirebaseDatabase.getInstance();
        //get the reference
        mDatabaseReference = mDatabase.getReference().child("MBlog");
        //make sure everything is synced
        mDatabaseReference.keepSynced(true);


        blogList = new ArrayList<>();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                    if (mUser!=null && mAuth!=null){
                        startActivity(new Intent(PostListActivity.this, AddPostActivity.class));
                    finish();
                    }
                break;
            case R.id.action_signout:
                if (mUser!=null && mAuth!=null){
                    mAuth.signOut();
                    startActivity(new Intent(PostListActivity.this, MainActivity.class));
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
