package com.os.foodie.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by shachindrap on 6/7/2016.
 */
public class GetPathFromUrl {


    public static String get_Path(String file) {
        String path = "";
        File source_email = new File(file);

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Android/data/com.os.foodie/cache/image");
        myDir.mkdirs();
        Long tsLong = System.currentTimeMillis() / 1000;
        String timeStamp = tsLong.toString();
        String fname = timeStamp + "_" + ".jpg";
        File destFile = new File(myDir, fname);
        try {
            if (destFile.exists()) {
                destFile.delete();
            }
          /*  try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            }
            catch (Exception e){

            }*/
            destFile.createNewFile();


        } catch (IOException e) {
            e.printStackTrace();
        }

        copy(source_email, destFile);

        path = destFile.getAbsolutePath();
        return path;
    }


    public static void copy(File src, File dst) {
        try {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
