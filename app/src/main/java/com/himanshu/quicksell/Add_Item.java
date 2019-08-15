package com.himanshu.quicksell;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.himanshu.quicksell.Model.Add_item_model;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Add_Item extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 52;
    String category = "", model_no = "", year_st = "", cost_st = "", title_st = "", description_st = "";
    CircleImageView car, bike, mobile, ac;
    boolean car_toggle = false, bike_toggle = false, mobile_toggle = false, ac_toggle = false;
    EditText model_name, year, cost, title, description;
    CircleImageView pic_1, pic_2, pic_3, pic_4, temp;
    Boolean img_selected = false;
    List<String> paths = new ArrayList<>();
    Button post_btn;
    ProgressBar progressBar;
    List<Uri> uris = new ArrayList<>();

    FirebaseFirestore db;

    FirebaseStorage storage;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__item);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Something here..");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_down_black_24dp);

        init_Views();
        setCategory();
        Btn_Clicks();

        db = FirebaseFirestore.getInstance();

        storage = FirebaseStorage.getInstance("gs://quickselll.appspot.com/");
        storageReference = storage.getReference();

    }

    private void init_Views() {
        car = findViewById(R.id.car);
        bike = findViewById(R.id.bike);
        mobile = findViewById(R.id.mobile);
        ac = findViewById(R.id.ac);
        progressBar = findViewById(R.id.progress_bar);
        pic_1 = findViewById(R.id.photo1);
        pic_2 = findViewById(R.id.photo2);
        pic_3 = findViewById(R.id.photo3);
        pic_4 = findViewById(R.id.photo4);
        post_btn = findViewById(R.id.post_btn);

        model_name = findViewById(R.id.model);
        year = findViewById(R.id.year);
        cost = findViewById(R.id.cost);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);

    }

    private void setCategory() {
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!car_toggle) {
                    car.setImageResource(R.drawable.ic_directions_car_black_24dp);
                    bike.setImageResource(R.drawable.unselect_bike);
                    mobile.setImageResource(R.drawable.unselect_phone);
                    ac.setImageResource(R.drawable.unselect_ac);
                    car_toggle = true;
                    ac_toggle = false;
                    bike_toggle = false;
                    mobile_toggle = false;
                    category = "Car";
                } else {
                    car.setImageResource(R.drawable.unselect_car);
                    car_toggle = false;
                    category = "";
                }


            }
        });

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bike_toggle) {
                    bike.setImageResource(R.drawable.bike);
                    mobile.setImageResource(R.drawable.unselect_phone);
                    ac.setImageResource(R.drawable.unselect_ac);
                    car.setImageResource(R.drawable.unselect_car);
                    bike_toggle = true;
                    car_toggle = false;
                    ac_toggle = false;
                    mobile_toggle = false;
                    category = "Bike";
                } else {
                    bike.setImageResource(R.drawable.unselect_bike);
                    bike_toggle = false;
                    category = "";
                }


            }
        });

        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mobile_toggle) {
                    mobile.setImageResource(R.drawable.select_phone);
                    ac.setImageResource(R.drawable.unselect_ac);
                    car.setImageResource(R.drawable.unselect_car);
                    bike.setImageResource(R.drawable.unselect_bike);
                    mobile_toggle = true;
                    bike_toggle = false;
                    car_toggle = false;
                    ac_toggle = false;
                    category = "Mobile";
                } else {
                    mobile.setImageResource(R.drawable.unselect_phone);
                    mobile_toggle = false;
                    category = "";
                }


            }
        });

        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ac_toggle) {
                    ac.setImageResource(R.drawable.select_ac);
                    car.setImageResource(R.drawable.unselect_car);
                    bike.setImageResource(R.drawable.unselect_bike);
                    mobile.setImageResource(R.drawable.unselect_phone);
                    ac_toggle = true;
                    bike_toggle = false;
                    mobile_toggle = false;
                    car_toggle = false;
                    category = "Ac";
                } else {
                    ac.setImageResource(R.drawable.unselect_ac);
                    ac_toggle = false;
                    category = "";
                }


            }
        });
    }

    private void Btn_Clicks() {
        pic_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryOrCameraDialog();
                temp = pic_1;
            }
        });

        pic_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryOrCameraDialog();
                temp = pic_2;
            }
        });

        pic_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryOrCameraDialog();
                temp = pic_3;
            }
        });

        pic_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryOrCameraDialog();
                temp = pic_4;
            }
        });

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckFields();
            }
        });
    }

    private void CheckFields() {
        model_no = model_name.getText().toString().trim();
        year_st = year.getText().toString().trim();
        cost_st = cost.getText().toString().trim();
        title_st = title.getText().toString().trim();
        description_st = description.getText().toString().trim();

        if (model_no.isEmpty()) {
            model_name.setError("Please enter Model number");
            model_name.setFocusable(true);
            return;
        }
        if (year_st.isEmpty()) {
            year.setError("Please enter year");
            year.setFocusable(true);
            return;
        }
        if (cost_st.isEmpty()) {
            cost.setError("Please enter a cost");
            cost.setFocusable(true);
            return;
        }
        if (title_st.isEmpty()) {
            title.setError("Please enter a title");
            title.setFocusable(true);
            return;
        }
        if (description_st.isEmpty()) {
            description.setError("Please enter description");
            model_name.setFocusable(true);
            return;
        }
        if (category.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Select Category from top Section", Toast.LENGTH_SHORT).show();
            return;
        }
        if (uris.size() == 0) {
            Toast.makeText(getApplicationContext(), "Please Add one product image.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            for (int i = 0; i < uris.size(); i++) {
                uploadImage(uris.get(i));
            }
        }

    }

    private void FireBase_AddItem(Add_item_model item) {
        db.collection("Products").document().set(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Successfully added..", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Something Went Wrong..", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void galleryOrCameraDialog() {
        //permissionManager.checkAndRequestPermissions(this);
        TextView galleryTv, cameraTv, noImageTv;
        View view = View.inflate(this, R.layout.dialog_camera_open, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .show();

        galleryTv = view.findViewById(R.id.galleryTv);
        cameraTv = view.findViewById(R.id.cameraTv);

        galleryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVideo();
                alertDialog.dismiss();
            }
        });

        cameraTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraIntent();
                alertDialog.dismiss();
            }
        });
    }

    private void openCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111 && resultCode == Activity.RESULT_OK) {
            img_selected = false;
            if (data != null) {
                Uri selectedVideoUri = data.getData();
                String filePath = getRealPathFromURI(selectedVideoUri);
                uris.add(selectedVideoUri);
                Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MINI_KIND);
                temp.setImageBitmap(bitmap);
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            img_selected = false;
            if (data != null) {
                img_selected = true;
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                temp.setImageBitmap(photo);
                Uri tempUri = getImageUri(getApplicationContext(), photo);
                uris.add(tempUri);
            }
        }
    }

    private void openVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, 1111);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImage(Uri filePath) {

        if (filePath != null) {

            StorageReference ref = storageReference.child("Photos").child(filePath.getLastPathSegment());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    paths.add(String.valueOf(task.getResult()));
                                    if (paths.size() == uris.size()) {
                                        upload_To_FireStore();
                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    private void upload_To_FireStore() {
        Add_item_model item = new Add_item_model(title_st, model_no, year_st, cost_st, description_st, category, paths);
        FireBase_AddItem(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }


}
