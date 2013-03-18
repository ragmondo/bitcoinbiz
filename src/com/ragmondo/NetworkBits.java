package com.ragmondo;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
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





}
