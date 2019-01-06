package com.example.arthur.myapplication;

import android.Manifest;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button button;
    String finalQuery = "";
    int idIndex = 1001;

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = "";

            Log.i("URL", strings[0]);

            URL url;
            HttpURLConnection urlConnection;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1){
                    char current = (char)data;
                    result += current;
                    data = reader.read();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

//            Log.i("Result", result);

            try {

                JSONObject jsonObject = new JSONObject(result);

                String identityInfo  = jsonObject.getString("results");

                JSONArray arr = new JSONArray(identityInfo);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);

                    //VICTIM

                    //Gender
                    String gender = jsonPart.getString("gender");

                    //Name
                    String name = jsonPart.getJSONObject("name").getString("first") + " " + jsonPart.getJSONObject("name").getString("last");

                    Random rand = new Random();

                    //ic
                    int n1 = rand.nextInt(999999) + 1;
                    int n2 = rand.nextInt(99) + 1;
                    int n3 = rand.nextInt(9999) + 1;
                    String ic = Integer.toString(n1) + "-" + Integer.toString(n2) + "-" + Integer.toString(n3);

                    //dob
                    String dob = new SimpleDateFormat("dd-MMM-yyyy").format(randomDate());

                    //phone number
                    int p1 = rand.nextInt(999) + 100;
                    int p2 = rand.nextInt(999) + 100;
                    int p3 = rand.nextInt(9999) + 1000;
                    String phoneNumber = Integer.toString(p1) + " " + Integer.toString(p2) + " " + Integer.toString(p3);

                    //id VICTIM
                    String idVictim;
                    if (idIndex < 10) {
                        idVictim = "V000" + idIndex;
                    } else if (idIndex < 100) {
                        idVictim = "V00" + idIndex;
                    } else if (idIndex < 1000) {
                        idVictim = "V0" + idIndex;
                    } else {
                        idVictim = "V" + idIndex;
                    }
                    idIndex++;

//                    Log.i("Info", idVictim);
//                    Log.i("Info", name);
//                    Log.i("Info", ic);
//                    Log.i("Info", gender);
//                    Log.i("Info", phoneNumber);
//                    Log.i("info", dob);

                    writeVictimQuery(idVictim, name, ic, gender, phoneNumber, dob);


//-----------------------------------------------------------------------------------------------------------------------------------

//                    //STAFF
//                    String code = "" ;
//                    Random rand = new Random();
//
//                    //type
//                    String type = "";
//                    int luck = rand.nextInt(10) + 1;
//                    if(luck > 7){
//                        code = "D";
//                        type = "Doctor";
//
//                    } else if(luck <= 2){
//                        code = "M";
//                        type = "Manager";
//                    } else {
//                        code = "VL";
//                        type = "Volunteer";
//                    }
//
//                    //id
//                    //id STAFF
//                    String idStaff;
//                    if(idIndex < 10){
//                        idStaff = code + "000"+ idIndex;
//                    } else if (idIndex < 100){
//                        idStaff = code + "00"+ idIndex;
//                    } else {
//                        idStaff = code + "0"+ idIndex;
//                    }
//                    idIndex++;
//
//                    //name
//                    String name = jsonPart.getJSONObject("name").getString("first") + " " + jsonPart.getJSONObject("name").getString("last");
//
//                    //ic
//                    int n1 = rand.nextInt(999999) + 1;
//                    int n2 = rand.nextInt(99) + 1;
//                    int n3 = rand.nextInt(9999) + 1;
//                    String ic = Integer.toString(n1) + "-" + Integer.toString(n2) + "-" + Integer.toString(n3);
//
//                    //phone number
//                    int p1 = rand.nextInt(999) + 100;
//                    int p2 = rand.nextInt(999) + 100;
//                    int p3 = rand.nextInt(9999) + 1000;
//                    String phoneNumber = Integer.toString(p1) + " " + Integer.toString(p2) + " " + Integer.toString(p3);
//
//                    //address
//                    String street = jsonPart.getJSONObject("location").getString("street");
//                    String city = jsonPart.getJSONObject("location").getString("city");
//                    String state = jsonPart.getJSONObject("location").getString("state");
//                    String postcode = jsonPart.getJSONObject("location").getString("postcode");
//                    String address = street + city + " " + state + " " + postcode;
//
//                    writeStaffQuery(idStaff, name, ic, phoneNumber, address, type);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            writeToFile();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (shouldAskPermissions()) {
            askPermissions();
        }

        button = findViewById(R.id.button);


    }

    public void buttonGenerateVictim(View view) {
        executeTaskURL();
    }

    public void executeTaskURL(){
        DownloadTask task = new DownloadTask();
        EditText amountIdentity = findViewById(R.id.editText);
        try {
            task.execute("https://randomuser.me/api/?nat=AU,BR,CA,CH,DE,DK,ES,FI,FR,GB,IE,NO,NL,NZ,US&results=" + amountIdentity.getText().toString() ).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Date randomDate (){
        Random rnd;
        Date dt;
        long ms;

        rnd = new Random();
        ms = -946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));

        dt = new Date (ms);

        return dt;
    }

    public void writeVictimQuery(String id, String name, String ic, String gender, String phone, String dob) {


        finalQuery += "INSERT INTO victim VALUES ('" +
                id + "', '" + name + "', '" + ic + "', '" + gender + "', '" + phone + "', TO_DATE('" + dob + "', 'dd-MON-yyyy'));\n";

//        Log.i("Query", finalQuery);
    }

    public void writeStaffQuery(String id, String name, String ic, String phone, String address, String type){
        finalQuery += "INSERT INTO staff VALUES ('" +
                id + "', '" + name + "', '" + ic + "', '" + phone + "', '" + address + "', '" + type +"');\n" ;

//        Log.i("Query", finalQuery);
    }



    public void writeToFile() {
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        try {

           if(root.canWrite()){

               File file = new File(root, "victimInsert.txt");
//               File file = new File(root, "staffInsert.txt");
               FileWriter fileWriter = new FileWriter(file);
               BufferedWriter out = new BufferedWriter(fileWriter);
               out.write(finalQuery);
               out.close();
           }

            Log.i("Write Status" , "Write Success!");
            Log.i("Write Status" , "File written to " + root);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", "File write failed: " + e.toString());
        }

        finalQuery = "";

    }


}
