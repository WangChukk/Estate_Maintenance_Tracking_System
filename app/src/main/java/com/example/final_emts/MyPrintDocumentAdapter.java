package com.example.final_emts;

import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MyPrintDocumentAdapter extends PrintDocumentAdapter {
    private String content;

    public MyPrintDocumentAdapter(String content) {
        this.content = content;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, android.os.Bundle extras) {
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        PrintDocumentInfo info = new PrintDocumentInfo.Builder("my_document.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .build();

        callback.onLayoutFinished(info, true);
    }

    @Override
    public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
        OutputStream output = null;

        try {
            output = new FileOutputStream(destination.getFileDescriptor());
            byte[] bytes = content.getBytes();
            output.write(bytes);
            callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
        } catch (IOException e) {
            // Handle exceptions here
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                // Handle exceptions here
            }
        }
    }
}
