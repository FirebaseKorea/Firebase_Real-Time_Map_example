package com.firebasekorea.map.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebasekorea.map.R;
import com.firebasekorea.map.controllers.MarkerController;
import com.firebasekorea.map.utils.ToastUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by namhoonkim on 26/02/2017.
 */

public class AddMarkerActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = AddMarkerActivity.class.getSimpleName();
    private final int RESULT_LOAD_IMG = 9000;

    private String mLongitude;
    private String mLatitude;

    private boolean isPictureSelect = false;
    private boolean isPikachuSelect = false;
    private int isFlag = 0;

    /* View Component */
    private TextView mMarkerSelected;
    private RelativeLayout mPictureLayout;
    private ImageView mPictureImageView;
    private RelativeLayout mPikachuLayout;
    private Button mAddMarkerButton;
    private TextView mMarkerText;

    /* Firebase */
    private FirebaseStorage mStrorage = FirebaseStorage.getInstance();
    private StorageReference mStorageReference = mStrorage.getReferenceFromUrl("gs://slow-campus.appspot.com");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marker);

        setTitle("Add Marker");

        mLongitude = getIntent().getStringExtra("longitude");
        mLatitude = getIntent().getStringExtra("latitude");

        init();
    }

    private void init() {
        mMarkerSelected = (TextView) findViewById(R.id.add_marker_selected);
        mMarkerSelected.setText("선택한 경도 / 위도\n" + mLongitude + " / " + mLatitude);

        mPictureLayout = (RelativeLayout) findViewById(R.id.add_marker_picture_layout);
        mPictureLayout.setOnClickListener(this);
        mPictureImageView = (ImageView) findViewById(R.id.add_marker_picture_image_view);
        mPictureImageView.setDrawingCacheEnabled(true);
        mPictureImageView.buildDrawingCache();

        mPikachuLayout = (RelativeLayout) findViewById(R.id.add_marker_pikachu_layout);
        mPikachuLayout.setOnClickListener(this);


        mMarkerText = (TextView) findViewById(R.id.add_marker_text);

        mAddMarkerButton = (Button) findViewById(R.id.add_marker_button);
        mAddMarkerButton.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mPictureImageView.setVisibility(View.VISIBLE);
                mPictureImageView.setImageBitmap(selectedImage);
                isPictureSelect = true;
                updateMarkerTextView();
                ToastUtil.makeShortToast(AddMarkerActivity.this, "이미지를 선택했습니다");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ToastUtil.makeShortToast(AddMarkerActivity.this, "이미지를 불러오는데 실패했습니다");
            }

        }
        else {
            ToastUtil.makeShortToast(AddMarkerActivity.this, "이미지를 불러올 수 없습니다");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_marker_picture_layout:
                onClickPictureLayout();
                break;
            case R.id.add_marker_pikachu_layout:
                onClickPikachuLayout();
                break;
            case R.id.add_marker_button:
                onAddMarkerButton();
                break;
            default:
                break;
        }
    }

    private void onClickPictureLayout() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    private void onClickPikachuLayout() {
        isPikachuSelect = true;
        updateMarkerTextView();
    }

    private void updateMarkerTextView() {
        if (isPictureSelect == false && isPikachuSelect == false) {
            mMarkerText.setText("이미지를 불러오거나 피카츄를 선택해주세요");
            isFlag = 1;
        }
        else if (isPictureSelect == true && isPikachuSelect == false) {
            mMarkerText.setText("이미지를 마커로 사용합니다");
            isFlag = 2;
        }
        else if (isPictureSelect == false && isPikachuSelect == true) {
            mMarkerText.setText("피카츄를 마커로 사용합니다");
            isFlag = 3;
        }
        else if (isPictureSelect == true && isPikachuSelect == true) {
            mMarkerText.setText("이미지와 피카츄를 둘 다 선택하셨습니다\n이미지를 마커로 사용합니다");
            isFlag = 4;
        }
    }

    private void onAddMarkerButton() {
        switch (isFlag) {
            case 0:
            case 1:
                // Nothing
                break;
            case 2:
            case 4:
                // 이미지 마커 사용
                generateMarkerWithPicture();
                break;
            case 3:
                // 피카츄 마커 사용
                generateMarkerWithPiakchu();
                break;
            default:
                break;
        }
    }

    private void generateMarkerWithPicture() {
        String markerUid = MarkerController.createMarker(Double.parseDouble(mLongitude), Double.parseDouble(mLatitude), true, false);
        uploadImageToFirebaseStorage(markerUid);


        // TODO : 이미지를 Storage에 올린다
        // TODO : 이미지 url을 받는다
        // TODO : marker에 image url property를 추가한다
        // TODO : Firebase Update.
//        MarkerController.createMarker(Double.parseDouble(mLongitude), Double.parseDouble(mLatitude), true, false);
    }

    private void generateMarkerWithPiakchu() {
        MarkerController.createMarker(Double.parseDouble(mLongitude), Double.parseDouble(mLatitude), false, true);
        finish();
    }

    private void uploadImageToFirebaseStorage(final String markerUid) {
        Bitmap bitmap = mPictureImageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mStorageReference.child("images").child(markerUid).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                ToastUtil.makeShortToast(AddMarkerActivity.this, "Upload Complete");
                MarkerController.updateImageUrl(markerUid, downloadUrl.toString());
                finish();
            }
        });

    }

}

