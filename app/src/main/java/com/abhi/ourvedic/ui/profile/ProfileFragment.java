package com.abhi.ourvedic.ui.profile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.abhi.ourvedic.R;
import com.abhi.ourvedic.model.ProfileModel;
import com.abhi.ourvedic.roomdatabase.MyDatabase;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ProgressDialog progressDialog;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ImageView image;
    private EditText name, housenumber, streetnumber, arealocality, landmark, pincode, email, number;
    private Button submit;
    private MyDatabase myDatabase;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Profile").child(user.getUid());
    private DatabaseReference imgRef = database.getReference("ProfilePic").child(user.getUid());
    private String permissions[] = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private HashMap<String, Object> hashMap = new HashMap<>();
    private String file = null;
    byte bb[];
    Bitmap thumbnail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        setImage();
        roomdb();
        functionpermission();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 101);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilestudent();
            }
        });
        return view;
    }

    private void dbsetup() {
        myDatabase = Room.databaseBuilder(getActivity(), MyDatabase.class, "profiledata")
                .allowMainThreadQueries().build();
    }

    private void roomdb() {
        myDatabase = Room.databaseBuilder(getActivity(), MyDatabase.class, "profiledata")
                .allowMainThreadQueries().build();
        List<ProfileModel> profileModels = myDatabase.dao().getProfile();
        boolean ch = profileModels.isEmpty();
        if (ch==false) {
            for (int i = 0; i < profileModels.size(); i++) {
                name.setText(String.valueOf(profileModels.get(i).getName()));
                housenumber.setText(String.valueOf(profileModels.get(i).getHouse()));
                streetnumber.setText(String.valueOf(profileModels.get(i).getStreet()));
                arealocality.setText(String.valueOf(profileModels.get(i).getArea()));
                landmark.setText(String.valueOf(profileModels.get(i).getLandmark()));
                pincode.setText(String.valueOf(profileModels.get(i).getPincode()));
                email.setText(String.valueOf(profileModels.get(i).getEmail()));
                number.setText(String.valueOf(profileModels.get(i).getNumber()));
            }
        } else {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String u_name = snapshot.child("" + "name").getValue(String.class);
                        name.setText(u_name);
                        String u_house = snapshot.child("house").getValue(String.class);
                        housenumber.setText(u_house);
                        String u_street = snapshot.child("street").getValue(String.class);
                        streetnumber.setText(u_street);
                        String u_area = snapshot.child("area").getValue(String.class);
                        arealocality.setText(u_area);
                        String u_land = snapshot.child("land").getValue(String.class);
                        landmark.setText(u_land);
                        String u_pincode = snapshot.child("pincode").getValue(String.class);
                        pincode.setText(u_pincode);
                        String u_email = snapshot.child("email").getValue(String.class);
                        email.setText(u_email);
                        String u_number = snapshot.child("number").getValue(String.class);
                        number.setText(u_number);

                        dbsetup();
                        ProfileModel profileModel = new ProfileModel(u_email, u_name, u_house, u_street, u_area, u_pincode, u_land, u_number);
                        myDatabase.dao().insertion(profileModel);
                    } else {
                        profilestudent();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("errorres", "onCancelled: "+error.getMessage() );
                }
            });
        }
    }


    private void init(View view) {
        progressDialog = new ProgressDialog(getActivity());
        image = view.findViewById(R.id.imagestudent);
        name = view.findViewById(R.id.namestudent);
        housenumber = view.findViewById(R.id.housenumberstudent);
        streetnumber = view.findViewById(R.id.streetnumberstudent);
        arealocality = view.findViewById(R.id.arealocalityteacher);
        landmark = view.findViewById(R.id.landmarkstudent);
        pincode = view.findViewById(R.id.pincodestudent);
        email = view.findViewById(R.id.emailteacher);
        number = view.findViewById(R.id.numberteacher);
        submit = view.findViewById(R.id.profileteacher);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 101 && data != null) {
                Uri selectedImage = data.getData();
                String[] filepath = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage, filepath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filepath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                thumbnail = (BitmapFactory.decodeFile(picturePath));
                image.setImageBitmap(thumbnail);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                bb = bytes.toByteArray();
                
                progressDialog.setCancelable(false);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog_view);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                if (bb != null) {
                    StorageReference storageReference = mStorageRef.child("Ourvedic/ProfilePic").child(user.getUid() + ".jpg");
                    storageReference.putBytes(bb).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            if (taskSnapshot.getTask().isSuccessful()) {
                                file = taskSnapshot.getMetadata().getPath();
                                hashMap.put("profilepicpath", file);
                                imgRef.setValue(hashMap);
                                progressDialog.dismiss();
                                Log.e("errorres", "Length" + taskSnapshot.getMetadata().getPath());
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("errorres", e.getMessage());
                        }
                    });
                }

            }
        }
    }

    private void setImage() {
        imgRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fileuri = snapshot.child("" + "profilepicpath").getValue(String.class);
                if (fileuri != null) {
                    StorageReference storageReference = mStorageRef.child(fileuri);
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(getActivity()).load(uri).into(image);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("errorres", e.getMessage());
                        }
                    });
                } else {
                    image.setImageResource(R.drawable.userde);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void functionpermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), permissions, 2);
        } else {
            Toast.makeText(getActivity(), "Give Permission", Toast.LENGTH_SHORT).show();
        }
    }

    public void profilestudent() {
        String name = this.name.getText().toString();
        String house = housenumber.getText().toString();
        String street = streetnumber.getText().toString();
        String area = arealocality.getText().toString();
        String land = landmark.getText().toString();
        String pincode = this.pincode.getText().toString();
        String email = this.email.getText().toString();
        String number = this.number.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(getActivity(), "Enter Your Name ", Toast.LENGTH_SHORT).show();
        } else if (house.equals("")) {
            Toast.makeText(getActivity(), "Enter Your House Number ", Toast.LENGTH_SHORT).show();
        } else if (street.equals("")) {
            Toast.makeText(getActivity(), "Enter Your Street/Building/Block ", Toast.LENGTH_SHORT).show();
        } else if (area.equals("")) {
            Toast.makeText(getActivity(), "Enter Your Area/Locality Name ", Toast.LENGTH_SHORT).show();
        } else if (land.equals("")) {
            Toast.makeText(getActivity(), "Enter Your Landmark ", Toast.LENGTH_SHORT).show();
        } else if (pincode.equals("")) {
            Toast.makeText(getActivity(), "Enter Your Pincode ", Toast.LENGTH_SHORT).show();
        } else if (email.equals("")) {
            Toast.makeText(getActivity(), "Enter Your Email ", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(getActivity(), "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
        } else if (number.equals("")) {
            Toast.makeText(getActivity(), "Enter Your Number ", Toast.LENGTH_SHORT).show();
        } else {
            dbsetup();
            ProfileModel profileModel = new ProfileModel(email, name, house, street, area, pincode, land, number);
            myDatabase.dao().insertion(profileModel);

            hashMap.put("name", name);
            hashMap.put("house", house);
            hashMap.put("street", street);
            hashMap.put("area", area);
            hashMap.put("land", land);
            hashMap.put("pincode", pincode);
            hashMap.put("email", email);
            hashMap.put("number", number);

            myRef.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("errorres", e.getMessage());
                }
            });
        }
    }
}
