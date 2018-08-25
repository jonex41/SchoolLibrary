package com.promise.www.schoollibrary;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PostBookActivity extends AppCompatActivity  implements View.OnClickListener{

    private ImageView selectImage;
    private EditText nameofbook;
    private Button postpdf;
    private Uri pdfurl;
    private StorageReference storageReference;
    private FirebaseFirestore firestore;
    private ProgressDialog progressDialog;
    private Spinner spinner;
    private String spinnerchoice;
    private Bitmap bmp = null;
    private String imageurlforpdf, displayName11;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postbook);
        progressDialog = new ProgressDialog(this);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.books, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinnerchoice = "computer";
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){

                    case 0:
                        spinnerchoice = "computer";
                        break;
                    case 1:
                        spinnerchoice = "biology";
                        break;
                    case 2:
                        spinnerchoice = "physics";
                        break;
                    case 3:
                        spinnerchoice = "math";
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        selectImage = (ImageView) findViewById(R.id.pdfselectimage);
        nameofbook = (EditText) findViewById(R.id.edtnameofbook);
        postpdf = (Button) findViewById(R.id.btnsubmitpdf);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (ContextCompat.checkSelfPermission(PostBookActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                       == PackageManager.PERMISSION_GRANTED){

                   selectpdf();
               }else {

                   ActivityCompat.requestPermissions(PostBookActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 15);
               }

            }
        });

        postpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdfurl != null){

                    postpdfmethod(pdfurl);
                }else {

                    Toast.makeText(PostBookActivity.this, "Please select a file..", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void postpdfmethod(final Uri pdfurl) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(0);
        progressDialog.show();
        final StorageReference storageReferenceimage = FirebaseStorage.getInstance().getReference();
        final StorageReference filepath_cut = storageReferenceimage.child("pdfs").child(System.currentTimeMillis() + ".pdf");


        final UploadTask uploadTask = filepath_cut.putFile(pdfurl);


        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task_cut) {

                if (task_cut.isSuccessful()) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] thumbData = baos.toByteArray();


                    final StorageReference reference1 = storageReferenceimage.child("pdfimage" + System.currentTimeMillis() + ".jpg");

                    final UploadTask uploadTask = reference1.putBytes(thumbData);
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {

                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return reference1.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                imageurlforpdf = String.valueOf(downloadUri);
                            }
                        }
                    });
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {

                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return filepath_cut.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUripdf = task.getResult();


                                Map<String, Object> map = new HashMap<>();
                                map.put("pdfUrl",String.valueOf(downloadUripdf));
                                map.put("pdfImage", imageurlforpdf);
                                map.put("pdfName", displayName11);
                                map.put("typeOfBook", spinnerchoice);
                                FirebaseFirestore.getInstance().collection(spinnerchoice).add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if (task.isSuccessful()) {

                                            if (progressDialog.isShowing()) {
                                                progressDialog.dismiss();
                                            }
                                            Toast.makeText(PostBookActivity.this, "Uploading successful", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (progressDialog.isShowing()) {
                                                progressDialog.dismiss();
                                            }
                                            Toast.makeText(PostBookActivity.this, "Unable to upload", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                // Handle failures
                                //  mProgressDialog.dismiss();
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(getApplicationContext(), "unable to get file", Toast.LENGTH_LONG).show();

                                // ...
                            }

                        }
                    });

                } else {

                    Toast.makeText(PostBookActivity.this, "Unable to create pdf..", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PostBookActivity.this, "Unable to create pdf", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                  int currentprogress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentprogress);
            }
        });



        
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        if(requestCode==15&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
        selectpdf();
        }else{

        Toast.makeText(this,"Please grant the permission, before you can proceed",Toast.LENGTH_LONG).show();
        }
        }


    private void selectpdf() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 43);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 43&& resultCode == RESULT_OK&& data !=null){
            pdfurl = data.getData();
generateImageFromPdf(pdfurl);
          //  selectImage.setImageResource(R.drawable.e_library);
            String uriString = pdfurl.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getApplicationContext().getContentResolver().query(pdfurl, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }
            displayName11 = displayName.replace(".pdf", "");
            nameofbook.setText(displayName11);
        }else {
            Toast.makeText(this, "Try again, unable to pick pdf..", Toast.LENGTH_LONG).show();
        }

    }

    void generateImageFromPdf(Uri pdfUri) {
        int pageNumber = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(this);
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            ParcelFileDescriptor fd = getContentResolver().openFileDescriptor(pdfUri, "r");
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
             bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
            saveImage(bmp);
            selectImage.setImageBitmap(bmp);
            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch(Exception e) {
            //todo with exception
        }
    }

    public final static String FOLDER = Environment.getExternalStorageDirectory() + "/PDF";
    private void saveImage(Bitmap bmp) {
        FileOutputStream out = null;
        try {
            File folder = new File(FOLDER);
            if(!folder.exists())
                folder.mkdirs();
            File file = new File(folder, "PDF.png");
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
        } catch (Exception e) {
            //todo with exception
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                //todo with exception
            }
        }
    }
    @Override
    public void onClick(View view) {

    }
}
