package com.koara.infoocoo;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

public class ClipboardInformation {
    public ClipboardInformation(Context context,TextView clipboardTextView) {
        ClipboardManager clipBoard = (ClipboardManager)context.getSystemService(CLIPBOARD_SERVICE);
        if (clipBoard.hasPrimaryClip()) {
            ClipData.Item item = clipBoard.getPrimaryClip().getItemAt(0);
            String clipboardText = item.getText().toString();
            clipboardTextView.setText(clipboardText);
        }else{
            clipboardTextView.setText("No Data in clipboard");
        }
        clipBoard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                ClipData clipData = clipBoard.getPrimaryClip();
                ClipData.Item item = clipData.getItemAt(0);
                String clipboardText = item.getText().toString();
                clipboardTextView.setText(clipboardText);
            }
        });
    }
}
