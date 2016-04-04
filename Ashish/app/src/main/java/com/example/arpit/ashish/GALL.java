package com.example.arpit.ashish;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;


/**
 * Created by arpit on 2/4/16.
 */
public class GALL extends Fragment implements LoaderCallbacks<Cursor> {
    View view;
    Button b;
    /** SimpleCursorAdapter, holds images and layout for the gridview */
    SimpleCursorAdapter mAdapter;

    @Override
    public void onStart() {
        super.onStart();

        /** Initializes the Loader */
        getLoaderManager().initLoader(0, null,GALL.this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.gall, container, false);

        /** Getting a reference to gridview of the MainActivity layout */
        GridView gridView = (GridView) view.findViewById(R.id.gridview);

        /** Create an adapter for the gridview */
        /** This adapter defines the data and the layout for the grid view */
        mAdapter = new SimpleCursorAdapter(
                getContext(),
                R.layout.gridview,
                null,
                new String[] { "_data","_id"} ,
                new int[] { R.id.img},
                0
        );

        /** Setting adapter for the gridview */
        gridView.setAdapter(mAdapter);

        /** Loader to get images from the SD Card */
        getLoaderManager().initLoader(0, null, this);

        /** Defining item click listener for the grid view */
        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                /** Getting the cursor object corresponds to the clicked item */
                Cursor c1 = (Cursor ) arg0.getItemAtPosition(position);

                /** Getting the image_id from the cursor */
                /** image_id of the thumbnail is same as the original image id */
                String id = c1.getString(c1.getColumnIndex("image_id"));

                /** Creating a bundle object to pass the image_id to the ImageDialog */
                Bundle b = new Bundle();

                /** Storing image_id in the bundle object */
                b.putString("image_id", id);

                /** Instantiating ImageDialog, which displays the clicked image */
                ImageDialog img = new ImageDialog();

                /** Setting the bundle object to the ImageDialog */
                img.setArguments(b);

                /** Opening ImageDialog */
                img.show(getFragmentManager(), "IMAGEDIALOG");
                return ;
            }

        };

        /** Setting itemclicklistener for the grid view */
        gridView.setOnItemClickListener(itemClickListener);

        return  view;
    }
    /** A callback method invoked by the loader when initLoader() is called */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        /** Getting uri to the Thumbnail images stored in the external storage */
        Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;

        /** Invoking the uri */
        return new CursorLoader(getContext(), uri, null, null, null, null);
    }

    /** A callback method, invoked after the requested content provider returned all the data */
    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        mAdapter.swapCursor(arg1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
    }
}
