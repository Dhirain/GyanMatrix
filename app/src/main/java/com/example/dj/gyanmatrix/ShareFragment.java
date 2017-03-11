package com.example.dj.gyanmatrix;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by DJ on 24-10-2016.
 */

public class ShareFragment extends DialogFragment {
    TextView mClose;
    TextView mFacebook;
    TextView mGooglePlus;
    TextView mTwitter;
    TextView mWhatsApp;
    TextView mMessenger;
    RelativeLayout mParent;

    private String text;
    /*private Tracker mTracker;*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
 View rootView = inflater.inflate(R.layout.share_fragment, container, false);
        setUpUI(rootView);
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setGravity(Gravity.FILL);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        Resources res = getResources();
        Bundle extra = this.getArguments();
        if (extra != null) {
            text = extra.getString("message");
        }
        mWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, text);
                try {
                    startActivity(whatsappIntent);
                } catch (ActivityNotFoundException activityNotFound) {
                    Context context = getContext();
                    CharSequence text = "Not Installed";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_SEND);
                facebookIntent.setType("text/plain");
                facebookIntent.setPackage("com.facebook.katana");
                facebookIntent.putExtra(Intent.EXTRA_TEXT, text);

                try {
                    startActivity(facebookIntent);
                } catch (ActivityNotFoundException activityNotFound) {

                    String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + text;
                    facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                    startActivity(facebookIntent);
                }

            }
        });

        mTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterIntent = new Intent(Intent.ACTION_SEND);
                twitterIntent.setType("text/plain");
                twitterIntent.setPackage("com.twitter.android");
                twitterIntent.putExtra(Intent.EXTRA_TEXT, text);
                try {
                    startActivity(twitterIntent);
                } catch (ActivityNotFoundException activityNotFound) {
                    String url = "https://twitter.com/intent/tweet?source=webclient&text=" + text;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }

            }
        });
        mGooglePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent googleplusIntent = new Intent(Intent.ACTION_SEND);
                googleplusIntent.setType("text/plain");
                googleplusIntent.setPackage("com.google.android.apps.plus");
                googleplusIntent.putExtra(Intent.EXTRA_TEXT, text);
                try {
                    startActivity(googleplusIntent);
                } catch (ActivityNotFoundException activityNotFound) {
                    String url = "https://plus.google.com/share?url=" + text;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }

            }
        });

        mMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent googleplusIntent = new Intent(Intent.ACTION_SEND);
                googleplusIntent.setType("text/plain");
                googleplusIntent.setPackage("com.facebook.orca");
                googleplusIntent.putExtra(Intent.EXTRA_TEXT, text);
                try {
                    startActivity(googleplusIntent);
                } catch (ActivityNotFoundException activityNotFound) {
                    Context context = getContext();
                    CharSequence text = "Not Installed";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });


        mClose.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          dismiss();
                                      }
                                  }
        );

        mParent.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           dismiss();
                                       }
                                   }
        );


        return rootView;
    }

    private void setUpUI(View rootView) {
        mClose = (TextView) rootView.findViewById(R.id.cancel_button);
        mFacebook = (TextView) rootView.findViewById(R.id.facebook_button);
        mGooglePlus = (TextView) rootView.findViewById(R.id.googlePlus_button);
        mTwitter = (TextView) rootView.findViewById(R.id.twitter_button);
        mWhatsApp = (TextView) rootView.findViewById(R.id.whatsapp_button);
        mMessenger = (TextView) rootView.findViewById(R.id.messenger_button);
        mParent = (RelativeLayout) rootView.findViewById(R.id.parent);
    }

    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_NoActionBar);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
