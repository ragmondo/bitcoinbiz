package com.ragmondo;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: richard
 * Date: 06/10/12
 * Time: 01:14
 * To change this template use File | Settings | File Templates.
 */
public class NetworkBits {

    public static final String Tag = NetworkBits.class.getSimpleName();

    public static void FireAndForgetUrl(String path, Map<String, String> dict,Context c) {

        HttpURLConnection httpConnection = null;

            String post_string = c.getString(R.string.server_base) +
                    path + "?" + convertMapToParams(dict);


        try {
            //"&val=" +URLEncoder.encode(scanned_content, "UTF-8") ;

            Log.v(Tag, "Send And Forget: " + post_string);

            URL post_url = new URL(post_string);

            httpConnection = (HttpURLConnection) post_url.openConnection();

            int response_code = httpConnection.getResponseCode();

            if (response_code == HttpURLConnection.HTTP_OK) {
                Log.v(Tag, "Logging " + post_string + " succeeded.");
            } else {
                Log.v(Tag, "Logging " + post_string + " failed. Response: " + response_code);
            }

        } catch (MalformedURLException ex) {
            Log.e(Tag, "FireAndForget " + ex.toString());
        } catch (IOException ex) {
            Log.e(Tag, "FireAndForget " + ex.toString());
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }

    }

    public static void FireAndForgetSecureUrl(String path, Map<String, String> dict,Context c) {

        HttpURLConnection httpConnection = null;

        String post_string = c.getString(R.string.secure_server_base) +
                path + "?" + convertMapToParams(dict);


        try {
            //"&val=" +URLEncoder.encode(scanned_content, "UTF-8") ;

            //Log.v(Tag, "Send And Forget to: " + c.getString(R.string.secure_server_base) + path);

            URL post_url = new URL(post_string);

            httpConnection = (HttpURLConnection) post_url.openConnection();

            int response_code = httpConnection.getResponseCode();

            if (response_code == HttpURLConnection.HTTP_OK) {
//                Log.v(Tag, "Logging " + post_string + " succeeded.");
            } else {
 //               Log.v(Tag, "Logging " + post_string + " failed. Response: " + response_code);
            }

        } catch (MalformedURLException ex) {
            Log.e(Tag, "FireAndForget " + ex.toString());
        } catch (IOException ex) {
            Log.e(Tag, "FireAndForget " + ex.toString());
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }

    }


    public static String convertMapToParams(Map<String,String> dict)
    {   String post_string = "";
        // + thing_to_send;
        boolean first = true;
        for (String k : dict.keySet()) {
            if (!first) {
                post_string = post_string + "&";
            } else {
                first = false;
            }
            post_string = post_string + k + "=" + URLEncoder.encode(dict.get(k));
        }
        return post_string;
    }


    public static String request(String url_string) {

        HttpURLConnection connection = null;
        InputStream inputstream = null;
        String response = null;

//        Log.d(Tag, "request " + url_string);

        try {
            URL associate_url = new URL(url_string);

            connection = (HttpURLConnection) associate_url.openConnection();

            int response_code = connection.getResponseCode();

            if (response_code == HttpURLConnection.HTTP_OK) {

                inputstream = connection.getInputStream();

                StringBuffer response_buffer = new StringBuffer();

                byte[] buffer = new byte[2048];
                int total = 0;

                while (total != -1) {
                    total = inputstream.read(buffer);

                    if (total > 0) {
                        response_buffer.append(new String(buffer, 0, total));
                    }

                }

                response = response_buffer.toString();

            } else {
                         { Log.d(Tag, "request ResponseCode: " + response_code); }
            }

        } catch (MalformedURLException e) {
               { Log.e(Tag, "request " + e.getMessage()); }
        } catch (IOException e) {
             { Log.e(Tag, "request " + e.getMessage()); }
        } finally {
            if (inputstream != null) {
                try {
                    inputstream.close();
                } catch (IOException e) {
                    //      Log.d(TAG, e.toString());
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

//        Log.d(Tag, "response " + response);

        return response;
    }



    public static Uri DownloadRingtoneFromUrl(File dest, URL u, String localname) {

        Log.d(Tag, "File dest is " + dest);
        Log.d(Tag, "URl u is " + u);
        Log.d(Tag, "localname is " + localname);

        Uri ret = null;
        HttpURLConnection c = null;
        Log.d(Tag, "download started..");
        try {
            c  = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            //c.setDoOutput(true); this would make a POST request if set.
            c.connect();
            Log.d(Tag, "Connected.");
        }
     catch (ProtocolException e) {
            Log.e(Tag, "Exception", e);  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            Log.e(Tag, "Exception", e);  //To change body of catch statement use File | Settings | File Templates.
        }
        File file = new File(dest, localname);

        if (file.exists()) {
            Log.d(Tag, "Overwriting old filename");
            file.delete();
            try {
                file = new File(dest, localname);
                file.createNewFile();
            } catch (IOException e) {
                Log.e(Tag, "Exception", e);  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        try {
            Log.d(Tag, "File is " + file.getCanonicalPath().toString());
            Log.d(Tag, "Writing to " + file.getAbsolutePath());
            FileOutputStream f = null;
            try{
            f = new FileOutputStream(file);
            }
            catch (FileNotFoundException e) {
                Log.e(Tag, "WEIRD Exception - I JUST CREATED IT SECONDS AGO...", e);  //To change body of catch statement use File | Settings | File Templates.
            }

            InputStream in = c.getInputStream();

            byte[] buffer = new byte[8192];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                //Log.d(Tag,"downloading..");
            //    Log.d(Tag, "Writing " + len1 + " bytes");
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (FileNotFoundException e) {
            Log.e(Tag, "Exception", e);  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            Log.e(Tag, "Exception", e);  //To change body of catch statement use File | Settings | File Templates.
        }

        ret = Uri.parse(file.getAbsolutePath());
        if (ret == null) {
            Log.d(Tag, "ret is null :-(");
        } else {
            Log.d(Tag, "ret is " + ret);
        }
        Log.d(Tag, "download finished..");

        return ret;

    }

    /*

    public static String uploadAudioToServer(String surl, Uri uri, Context c) throws IOException {
        InputStream is = c.getContentResolver().openInputStream(uri);
        File outputDir = c.getCacheDir(); // context being the Activity pointer
        File outputFile = File.createTempFile("ringtone", "mp3", outputDir);
        FileOutputStream f = new FileOutputStream(outputFile);
            byte[] buffer = new byte[8192];
            int len1 = 0;
            while ((len1 = is.read(buffer)) > 0) {
                //Log.d(Tag,"downloading..");
                //Log.d(Tag,"Writing "+len1 + " bytes");
                f.write(buffer, 0, len1);
            }
            f.close();
        Log.d(Tag, "Uploading started..");
        HttpResponse response;
        Log.d(Tag, " byte length is  " + outputFile.length());
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(surl);

            FileBody bin = new FileBody(outputFile);
            StringBody comment = new StringBody("Filename: " + outputFile.getName());

            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("bin", bin);
            reqEntity.addPart("comment", comment);
            httppost.setEntity(reqEntity);

            response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            String resp = EntityUtils.toString(resEntity);
             Log.d(Tag, "resp was " + resp);
        return resp;
    }

*/

      /*
    public static String uploadAudioToServer(String surl, String filename, ContentResolver cr) throws IOException {

        //ParcelFileDescriptor pfd = getCon

        Log.d(Tag, "Uploading started..");
        File file = new File(filename);
        HttpResponse response;
        Log.d(Tag, " byte length is  " + file.length());
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(surl);

        FileBody bin = new FileBody(file);
        StringBody comment = new StringBody("Filename: " + file.getName());

        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("bin", bin);
        reqEntity.addPart("comment", comment);
        httppost.setEntity(reqEntity);

        response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        String resp = EntityUtils.toString(resEntity);
        Log.d(Tag, "resp was " + resp);
        return resp;
    }

        */




}
