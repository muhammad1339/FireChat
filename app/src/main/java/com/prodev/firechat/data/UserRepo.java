package com.prodev.firechat.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepo {
    private static final String TAG = UserRepo.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;
    private Uri imageUploadUri;

    public UserRepo() {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void signUpWithEmailAndPassword(final User user, Uri imagePath) {
        this.imageUploadUri = imagePath;
        mAuth.createUserWithEmailAndPassword(user.getUserMail(), user.getUserPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signUpWithEmailAndPassword-onComplete: " + task.isComplete());
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "signUpWithEmailAndPassword-onSuccess: " + authResult.getUser().getUid());
                        userID = authResult.getUser().getUid();
                        user.setUid(userID);
                        storeUserWithEmailAndPassword(user);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "signUpWithEmailAndPassword-onFailure: " + e.getMessage());
                    }
                });
    }

    public void loginUserWithEmailAndPassword(User user) {
        mAuth.signInWithEmailAndPassword(user.getUserMail(), user.getUserPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "loginUserWithEmailAndPassword-onComplete: " + task.isComplete());
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "loginUserWithEmailAndPassword-onSuccess: " + authResult.getUser().getUid());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "loginUserWithEmailAndPassword-onFailure: " + e.getMessage());
                    }
                });
    }

    public void storeUserWithEmailAndPassword(User user) {
        isUserAlreadyExist(user);
    }

    public void isUserAlreadyExist(final User user) {
        final DatabaseReference ref = mFirebaseDatabase.getReference("user");
        ref.orderByChild("userMail").equalTo(user.getUserMail())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Log.d(TAG, "isUserAlreadyExist-onDataChange: ");
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Log.d(TAG, "isUserAlreadyExist-for- onDataChange: " + snapshot.getValue(User.class).toString());
                            }
                        } else {
                            //add here
                            uploadUserImageProfile(imageUploadUri, user);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "isUserAlreadyExist-onCancelled: " + databaseError.getDetails());
                    }
                });
    }


    public void uploadUserImageProfile(Uri uriToSave, final User user) {
        final DatabaseReference ref = mFirebaseDatabase.getReference("user");
        final String imageName = UUID.randomUUID().toString();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference images = firebaseStorage.getReference("images/" + imageName);
        images.putFile(uriToSave).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "onSuccess: " + taskSnapshot.getMetadata().getPath());
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: " + uri.toString());
                        user.setUserImagePath(uri.toString());
                        if (uri != null) {
                            ref.push().setValue(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "isUserAlreadyExist-onSuccess: ");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "isUserAlreadyExist-onFailure: " + e.getMessage());
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    public LiveData<List<User>> getAllUsers() {
        final MutableLiveData<List<User>> userMutableLiveData = new MutableLiveData<>();
        //get users from fire base
        DatabaseReference reference = mFirebaseDatabase.getReference("user");
        reference.orderByChild("userMail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> userList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "\n" + snapshot.getValue(User.class).toString());
                    userList.add(snapshot.getValue(User.class));
                }
                userMutableLiveData.postValue(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return userMutableLiveData;
    }

}
