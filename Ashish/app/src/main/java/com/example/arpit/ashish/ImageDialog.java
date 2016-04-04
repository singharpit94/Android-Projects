package com.example.arpit.ashish;

import java.io.File;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class ImageDialog extends DialogFragment implements LoaderCallbacks<Cursor>{

    LayoutInflater mLayoutInflater;
    ImageView mImgOriginal;

    String mId="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Initializing the Loader */
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /** Instantiating Builder object to create an alert dialog window */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        /** Getting layout inflater */
        mLayoutInflater = getActivity().getLayoutInflater();

        /** Getting the screen view corresponding to the layout image_dialog_layout */
        View v = mLayoutInflater.inflate(R.layout.image_dialog_layout, null);

        /** Getting a reference to the image widget imgOriginal of the layoutfile image_dialog_layout.xml */
        mImgOriginal = (ImageView) v.findViewById(R.id.imgOriginal);

        /** Setting a view on the builder */
        builder.setView(v);

        /** Settng a title for the alert dialog window */
        builder.setTitle("Image");

        /** Creating a alert window */
        return builder.create();
    }

    /** A callback method, invoked on initializing the loader */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        /** Getting Bundle object passed from MainActivity */
        Bundle b  = getArguments();

        /** Getting image_id from the bundle object */
        mId = b.getString("image_id");

        /** Setting uri to the original image files stored in external storage device */
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        /** Creating a cursor loader from the uri corresponds to mID **/
        CursorLoader cLoader= new CursorLoader(getActivity(), uri, null, "_id=" + mId , null, null);

        return cLoader;
    }

    /** A callback method, invoked on initializing the loader */
    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        if(arg1.getCount()==0)
            return;
        String path = "";

        /** Move to the first row of the cursor, if it exists */
        if(arg1.moveToFirst()){
            path = arg1.getString(arg1.getColumnIndex("_data"));
            File imgFile = new  File(path);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                mImgOriginal.setImageBitmap(myBitmap);
                mImgOriginal.setAdjustViewBounds(true);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
    }
}