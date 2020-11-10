package com.ozonetech.ozogram.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.databinding.FragmentPhotoBinding;
import com.ozonetech.ozogram.viewmodel.PhotoViewModel;
import com.ozonetech.ozogram.viewmodel.PostGalleryViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import iamutkarshtiwari.github.io.ananas.editimage.EditImageActivity;
import iamutkarshtiwari.github.io.ananas.editimage.ImageEditorIntentBuilder;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.ozonetech.ozogram.view.activity.GalleryActivity.ACTION_REQUEST_EDITIMAGE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoFragment extends Fragment implements Camera.PictureCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private String tag = "PhotoFragment";
    private CameraPreview mPreview;
    private Camera camera;
    private boolean cameraFront;
    private Camera.PictureCallback pictureCallback;
    private String path="";

    public PhotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotoFragment newInstance(String param1, String param2) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    FragmentPhotoBinding photoBinding;
    PhotoViewModel photoViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        photoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo, container, false);
        photoViewModel = ViewModelProviders.of(getActivity()).get(PhotoViewModel.class);
        photoBinding.setLifecycleOwner(getActivity());
        photoBinding.setPhot(photoViewModel);
        view = photoBinding.getRoot();
        initUI();
        return view;
    }

    private void initUI() {
        if (checkCameraHardware(getContext())) {
            camera = getCameraInstance();
            mPreview = new CameraPreview(getContext(), camera);
            photoBinding.camerLayer.addView(mPreview);
            // setParameter();
            pictureCallback = getPictureCallback();
        }

        photoBinding.btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (camera != null) {
                    Log.d(tag,"---take photo-camera");
                    camera.takePicture(null, null, pictureCallback);
                }
            }
        });

        photoBinding.ivCameraSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseCamera();
                chooseCamera();
            }
        });
        photoBinding.ivFlashOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashToggle();
            }
        });
    }
    int flashType = 1;
    private void flashToggle() {

        if (flashType == 1) {

            flashType = 2;
        } else if (flashType == 2) {

            flashType = 3;
        } else if (flashType == 3) {

            flashType = 1;
        }
        refreshCamera();
    }
    int flag = 0;
    public void refreshCamera() {

        if (mPreview == null) {
            return;
        }
        try {
            camera.stopPreview();
            Camera.Parameters param = camera.getParameters();

            if (flag == 0) {
                if (flashType == 1) {
                    param.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                    photoBinding.ivFlashOnOff.setImageResource(R.drawable.ic_flash_auto);
                } else if (flashType == 2) {
                    param.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                    Camera.Parameters params = null;
                    if (camera != null) {
                        params = camera.getParameters();

                        if (params != null) {
                            List<String> supportedFlashModes = params.getSupportedFlashModes();

                            if (supportedFlashModes != null) {
                                if (supportedFlashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                                    param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                                } else if (supportedFlashModes.contains(Camera.Parameters.FLASH_MODE_ON)) {
                                    param.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                                }
                            }
                        }
                    }
                    photoBinding.ivFlashOnOff.setImageResource(R.drawable.ic_flash_on);
                } else if (flashType == 3) {
                    param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    photoBinding.ivFlashOnOff.setImageResource(R.drawable.ic_flash_off);
                }
            }


            refrechCameraPriview(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void refrechCameraPriview(Camera.Parameters param) {
        try {
            camera.setParameters(param);
            setCameraDisplayOrientation(0);
            mPreview.refreshCamera(camera);
          //  camera.startPreview();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void chooseCamera() {
        //if the camera preview is the front
        if (cameraFront) {
            int cameraId = findBackFacingCamera();
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview

                camera = Camera.open(cameraId);
               setCameraDisplayOrientation(0);
                pictureCallback = getPictureCallback();
                mPreview.refreshCamera(camera);
            }
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview

                camera = Camera.open(cameraId);
                setCameraDisplayOrientation(0);
                pictureCallback = getPictureCallback();
                mPreview.refreshCamera(camera);
            }
        }
    }

    public void setCameraDisplayOrientation(int cameraId) {

        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);

        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();

        if (Build.MODEL.equalsIgnoreCase("Nexus 6") && flag == 1) {
            rotation = Surface.ROTATION_180;
        }
        int degrees = 0;
        switch (rotation) {

            case Surface.ROTATION_0:

                degrees = 0;
                break;

            case Surface.ROTATION_90:

                degrees = 90;
                break;

            case Surface.ROTATION_180:

                degrees = 180;
                break;

            case Surface.ROTATION_270:

                degrees = 270;
                break;

        }

        int result;

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {

            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror

        } else {
            result = (info.orientation - degrees + 360) % 360;

        }

        camera.setDisplayOrientation(result);

    }

    private int findBackFacingCamera() {
        int cameraId = -1;
        //Search for the back facing camera
        //get the number of cameras
        int numberOfCameras = Camera.getNumberOfCameras();
        //for every camera check
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                cameraFront = false;
                photoBinding.ivCameraSide.setBackgroundResource(R.drawable.ic__camer_rear);
                break;

            }

        }
        return cameraId;
    }

    private int findFrontFacingCamera() {

        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                cameraFront = true;
                photoBinding.ivCameraSide.setBackgroundResource(R.drawable.ic__camer_back);
                break;
            }
        }
        return cameraId;

    }



    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private static Camera getCameraInstance() {
        Camera camera1;
        try {
            if (android.os.Build.VERSION.SDK_INT >= 9) {
                return Camera.open();
            }
            camera1 = Camera.open();
        } catch (Exception exception) {
            return null;
        }
        return camera1;
    }

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {

    }


    public static final int ACTION_REQUEST_EDITIMAGE = 9;

    public void fillterImage(String path, File file) throws Exception {
        Log.d(tag,"----filter photo--"+file.getAbsolutePath());


        Intent intent = new ImageEditorIntentBuilder(getContext(),file.getAbsolutePath(),file.getAbsolutePath())
                .withAddText()
                .withFilterFeature()
                .withRotateFeature()
                .withCropFeature()
                .withBrightnessFeature()
                .withSaturationFeature()
                .withBeautyFeature()

                .forcePortrait(true)
                .setSupportActionBarVisibility(false)
                .build();
        // .withStickerFeature()
        //  .withPaintFeature()
        EditImageActivity.start(getActivity(), intent, ACTION_REQUEST_EDITIMAGE);
    }


    public Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                Log.d(tag,"----take photo--");
                if (pictureFile == null) {
                    Log.d(tag, "Error creating media file, check storage permissions");
                    return;
                }

                try {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                    fillterImage(path,pictureFile);
                } catch (FileNotFoundException e) {
                    Log.d(tag, "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d(tag, "Error accessing file: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(tag, "Error ex file: " + e.getMessage());
                }
            }
        };
        return pictureCallback;
    }

    public void onResume() {

        super.onResume();
        if (camera == null) {
            camera = Camera.open();
            camera.setDisplayOrientation(90);
            pictureCallback = getPictureCallback();
            mPreview.refreshCamera(camera);
            Log.d("nu", "null");
        } else {
            Log.d("nu", "no null");
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera() {
        // stop and release camera
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "OzoGram");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera mCamera;

        public CameraPreview(Context context, Camera camera) {
            super(context);
            mCamera = camera;
            mCamera.setDisplayOrientation(90);
            mHolder = getHolder();
            mHolder.addCallback(this);
            // deprecated setting, but required on Android versions prior to 3.0
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }



        @Override
        public void surfaceCreated(@NonNull SurfaceHolder holder) {
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                Log.d(tag, "Error setting camera preview: " + e.getMessage());
            }
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            refreshCamera(mCamera);
        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            mCamera.release();
        }

        public void refreshCamera(Camera camera) {
            if (mHolder.getSurface() == null) {
                // preview surface does not exist
                return;
            }
            // stop preview before making changes
            try {
                mCamera.stopPreview();
            } catch (Exception e) {
                // ignore: tried to stop a non-existent preview
            }
            // set preview size and make any resize, rotate or
            // reformatting changes here
            // start preview with new settings
            setCamera(camera);
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
            } catch (Exception e) {
                Log.d(VIEW_LOG_TAG, "Error starting camera preview: " + e.getMessage());
            }
        }

        public void setCamera(Camera camera) {
            //method to set a camera instance
            mCamera = camera;
        }
    }

}