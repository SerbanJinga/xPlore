package com.roh44x.xPlore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roh44x.xPlore.model.Comment;
import com.roh44x.xPlore.model.Post;
import com.roh44x.xPlore.model.User;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_POST_KEY = "post_key";
    private DatabaseReference mPostReference;
    private DatabaseReference mCommentsReference;
    private ValueEventListener mPostListener;
    private String mPostKey;
    private CommentAdapter mAdapter;

    private TextView mAuthorView;
    private TextView mTitleView;
    private TextView mBodyView;
    private EditText mCommentField;
    private Button mCommentButton;
    private RecyclerView mCommentsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);


        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass extra post key");
        }

        mPostReference = FirebaseDatabase.getInstance().getReference().child("posts").child(mPostKey);
        mCommentsReference = FirebaseDatabase.getInstance().getReference().child("post-comments").child(mPostKey);

        mAuthorView = findViewById(R.id.postAuthor);
        mTitleView = findViewById(R.id.postTitle);
        mBodyView = findViewById(R.id.postBody);
        mCommentField = findViewById(R.id.fieldCommentText);
        mCommentButton = findViewById(R.id.buttonPostComment);
        mCommentsRecycler = findViewById(R.id.recyclerPostComments);

        mCommentButton.setOnClickListener(this);
        mCommentsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);

                mAuthorView.setText(post.author);
                mTitleView.setText(post.title);
                mBodyView.setText(post.body);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(PostDetailActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mPostReference.addValueEventListener(postListener);
        mPostListener = postListener;

        mAdapter = new CommentAdapter(this, mCommentsReference);
        mCommentsRecycler.setAdapter(mAdapter);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mPostListener != null) {
            mPostReference.removeEventListener(mPostListener);
        }

        // Clean up comments listener
        mAdapter.cleanupListener();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonPostComment) {
            postComment();
        }
    }

    private void postComment() {
        final String uid = getUid();

        FirebaseDatabase.getInstance().getReference().child("Users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        String authorName = user.firstName;
                        String commentText = mCommentField.getText().toString();
                        Comment comment = new Comment(uid, authorName, commentText);

                        mCommentsReference.push().setValue(comment);

                        mCommentField.setText(null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    private static class CommentViewHolder extends RecyclerView.ViewHolder {

        public TextView authorView;
        public TextView bodyView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);


            authorView = itemView.findViewById(R.id.commentAuthor);
            bodyView = itemView.findViewById(R.id.commentBody);
        }
    }


    private static class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {


        private Context mContext;
        private DatabaseReference mDatabaseReference;
        private ChildEventListener mChildEventListener;

        private List<String> mCommentIds = new ArrayList<>();
        private List<Comment> mComments = new ArrayList<>();

        public CommentAdapter(final Context context, DatabaseReference ref) {
            mContext = context;
            mDatabaseReference = ref;
            ChildEventListener eventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Comment comment = dataSnapshot.getValue(Comment.class);

                    mCommentIds.add(dataSnapshot.getKey());
                    mComments.add(comment);
                    notifyItemInserted(mComments.size() - 1);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Comment newComment = dataSnapshot.getValue(Comment.class);
                    String commentKey = dataSnapshot.getKey();

                    int commentIndex = mCommentIds.indexOf(commentKey);
                    if (commentIndex > -1) {
                        // Replace with the new data
                        mComments.set(commentIndex, newComment);

                        // Update the RecyclerView
                        notifyItemChanged(commentIndex);
                    } else {
                        Toast.makeText(context, "It is not working", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    String commentKey = dataSnapshot.getKey();
                    int commentIndex = mCommentIds.indexOf(commentKey);
                    if (commentIndex > -1) {
                        // Remove data from the list
                        mCommentIds.remove(commentIndex);
                        mComments.remove(commentIndex);

                        // Update the RecyclerView
                        notifyItemRemoved(commentIndex);
                    } else {
                        Toast.makeText(context, "It is not working", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Comment movedComment = dataSnapshot.getValue(Comment.class);
                    String commentKey = dataSnapshot.getKey();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, "Failed to load comments", Toast.LENGTH_SHORT).show();
                }
            };
            ref.addChildEventListener(eventListener);
            mChildEventListener = eventListener;

        }

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {
            Comment comment = mComments.get(position);
            holder.authorView.setText(comment.author);
            holder.bodyView.setText(comment.text);
        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }

        public void cleanupListener() {
            if (mChildEventListener != null) {
                mDatabaseReference.removeEventListener(mChildEventListener);
            }

        }
    }
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}

