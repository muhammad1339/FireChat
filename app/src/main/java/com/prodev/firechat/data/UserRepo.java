package com.prodev.firechat.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prodev.firechat.Constant;
import com.prodev.firechat.PrefManager;
import com.prodev.firechat.register.LoginPresenter;
import com.prodev.firechat.register.SignUpPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepo {
    private static final String TAG = UserRepo.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;
    private Uri imageUploadUri;
    private UserRepoCallback.UserRepoSignUpCallback repoSignUpCallback;
    private UserRepoCallback.UserRepoLoginCallback repoLoginCallback;
    private Context mContext;

    public UserRepo() {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    public UserRepo(SignUpPresenter presenter, Context context) {
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        repoSignUpCallback = (UserRepoCallback.UserRepoSignUpCallback) presenter;
    }

    public UserRepo(LoginPresenter presenter, Context context) {
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        repoLoginCallback = (UserRepoCallback.UserRepoLoginCallback) presenter;
    }

    public void signUpWithEmailAndPassword(final User user, Uri imagePath) {
        this.imageUploadUri = imagePath;
        repoSignUpCallback.onStartLoading();
        mAuth.createUserWithEmailAndPassword(user.getUserMail(), user.getUserPassword())
                .addOnCompleteListener(task -> Log.d(TAG, "signUpWithEmailAndPassword-onComplete: " + task.isComplete()))
                .addOnSuccessListener(authResult -> {
                    Log.d(TAG, "signUpWithEmailAndPassword-onSuccess: " + authResult.getUser().getUid());
                    userID = authResult.getUser().getUid();
                    user.setUid(userID);
                    storeUserWithEmailAndPassword(user);
                    // notify presenter to change view
                    repoSignUpCallback.onSignUpSuccess();
                    repoSignUpCallback.onEndLoading();
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "signUpWithEmailAndPassword-onFailure: " + e.getMessage());
                    repoSignUpCallback.onSignUpFailure(e.getMessage());
                    repoSignUpCallback.onEndLoading();
                });
    }

    public void loginUserWithEmailAndPassword(User user) {
        repoLoginCallback.onStartLoading();
        mAuth.signInWithEmailAndPassword(user.getUserMail(), user.getUserPassword())
                .addOnCompleteListener(task -> Log.d(TAG, "loginUserWithEmailAndPassword-onComplete: " + task.isComplete()))
                .addOnSuccessListener(authResult -> {
                    Log.d(TAG, "loginUserWithEmailAndPassword-onSuccess: " + authResult.getUser().getUid());
                    // notify presenter to change view
                    repoLoginCallback.onLoginSuccess();
                    repoLoginCallback.onEndLoading();
                    getUserWithEmail(user.getUserMail());
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "loginUserWithEmailAndPassword-onFailure: " + e.getMessage());
                    repoLoginCallback.onLoginFailure(e.getMessage());
                    repoLoginCallback.onEndLoading();
                });
    }

    public void storeUserWithEmailAndPassword(User user) {
        uploadUserImageProfile(imageUploadUri, user);
    }


    public void uploadUserImageProfile(Uri uriToSave, final User user) {
        final DatabaseReference ref = mFirebaseDatabase.getReference(Constant.USER_NODE);
        final String imageName = UUID.randomUUID().toString();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference images = firebaseStorage.getReference("images/" + imageName);
        images.putFile(uriToSave).addOnSuccessListener(taskSnapshot -> {
            Log.d(TAG, "onSuccess: " + taskSnapshot.getMetadata().getPath());
            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener((Uri uri) -> {
                Log.d(TAG, "onSuccess: " + uri.toString());
                user.setUserImagePath(uri.toString());
                PrefManager.savePref(user, mContext, Constant.USER_NODE);
                if (uri != null) {
                    ref.push().setValue(user)
                            .addOnSuccessListener(aVoid -> Log.d(TAG, "isUserAlreadyExist-onSuccess: "))
                            .addOnFailureListener(e -> Log.d(TAG, "isUserAlreadyExist-onFailure: " + e.getMessage()));
                }
            });
        });
    }

    public LiveData<List<User>> getAllUsers() {
        final MutableLiveData<List<User>> userMutableLiveData = new MutableLiveData<>();
        //get users from fire base
        DatabaseReference reference = mFirebaseDatabase.getReference(Constant.USER_NODE);
        reference.orderByChild("userMail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> userList = new ArrayList<>();
                String uid = FirebaseAuth.getInstance().getUid();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (!uid.equals(user.getUid()))
                        userList.add(user);
                }
                userMutableLiveData.postValue(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return userMutableLiveData;
    }

    private void getUserWithEmail(String email) {
        DatabaseReference reference = mFirebaseDatabase.getReference(Constant.USER_NODE);
        reference.orderByChild("userMail")
                .equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            Log.d(TAG, "getUserWithEmail-onDataChange:\n " +
                                    user.toString());
                            PrefManager.savePref(user, mContext, Constant.USER_NODE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "getUserWithEmail-onCancelled:\n " + databaseError.getDetails());
                    }
                });
    }

    public LiveData<User> getUserWithId(String id) {
        Log.d(TAG, "getUserWithId: ");
        final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        DatabaseReference reference = mFirebaseDatabase.getReference(Constant.USER_NODE);
        reference.orderByChild("uid")
                .equalTo(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            Log.d(TAG, "getUserWithId-onDataChange:\n " +
                                    user.toString());
                            userMutableLiveData.postValue(user);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "getUserWithId-onCancelled:\n " + databaseError.getDetails());
                    }
                });
        return userMutableLiveData;
    }

}
