package asu.mc.newnavigate;


import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Statistics extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private String Date, Time;
    BarChart crimeVsCount;
    HashMap<String, Integer> CrimeVsCount = new HashMap<>();
    // Sort method needs a List, so let's first convert Set to List in Java
    List<Entry<String, Integer>> listOfEntries;
    LinkedHashMap<String, Integer> sortedByValue = new LinkedHashMap<String, Integer>(10);
    //System.out.print("HashMap before sorting, random order ");
    private void captureScreen(View v) {
        File path = getFilesDir();
        //View v = getWindow().getDecorView().getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        try {
            //FileOutputStream fos = new FileOutputStream(new File(path.getCanonicalFile() + "SCREEN"
            //        + System.currentTimeMillis() + ".png"));
            File f = new File(Environment.getExternalStorageDirectory().getPath() + "/Bar_Chart.png");
            FileOutputStream fos = new FileOutputStream(f);
            Log.d("FIlePATH $$$$$$$", path.getCanonicalPath());
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                Bitmap bitmap = takeScreenshot(getWindow().getDecorView());
                saveBitmap(bitmap);

                //captureScreen(findViewById(R.id.bargraph));

            }


            public Bitmap takeScreenshot(View v) {
                //View rootView = findViewById(android.R.id.content).getRootView();
                v.setDrawingCacheEnabled(true);
                return v.getDrawingCache();
            }

            public void saveBitmap(Bitmap bitmap) {
                File imagePath = new File(Environment.getExternalStorageDirectory() + "/BarChart.jpeg");
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(imagePath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    Toast.makeText(getApplicationContext(),"Image downloaded",Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    Log.e("GREC", e.getMessage(), e);
                } catch (IOException e) {
                    Log.e("GREC", e.getMessage(), e);
                }
            }

        });
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                readCrimeData();

                crimeVsCount = (BarChart) findViewById(R.id.bargraph);
                crimeVsCount.setDrawBarShadow(false);
                crimeVsCount.setDrawValueAboveBar(true);
                crimeVsCount.setMaxVisibleValueCount(10);
                crimeVsCount.setPinchZoom(true);
                crimeVsCount.setDrawGridBackground(true);
                crimeVsCount.setVisibleXRangeMaximum(20);

                ArrayList<BarEntry> barEntries = new ArrayList<>();
                int count = 0;



                ArrayList<String> crimes1 = new ArrayList<>();
                Set<Entry<String, Integer>> entrySetSortedByValue = sortedByValue.entrySet();

                for(Entry<String, Integer> mapping : entrySetSortedByValue){
                    System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
                    crimes1.add(mapping.getKey());
                    barEntries.add(new BarEntry(count++, mapping.getValue()));


                }

                crimeVsCount.getXAxis().setValueFormatter(new IndexAxisValueFormatter(crimes1));
                BarDataSet barDataSet = new BarDataSet(barEntries, "Crimes");
                barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                //ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                //dataSets.add(barDataSet);

                BarData data = new BarData(barDataSet);
                data.setBarWidth(0.5f);

                crimeVsCount.setData(data);
                crimeVsCount.setDragEnabled(true);
                crimeVsCount.setScaleEnabled(true);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    private List<CrimeSample> crimesamples= new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void readCrimeData() throws IOException, ParseException {
        Set<String> Loc_Set = new HashSet<String>();
        Set<java.util.Date> Date_Set = new HashSet<Date>();
        //Set<Date> Date_Set1 = new HashSet<Date>();
        Set<String> Time = new HashSet<String>();
        Set<String> type_of_crime = new HashSet<String>();
        CrimeSample sample;
        InputStream is  = getResources().openRawResource(R.raw.crime);
        InputStreamReader isReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isReader);
        String line="";
        reader.readLine();
        int count=1;



        try {
            while (((line = reader.readLine()) != null ) && count<=9411) {
                System.out.println(count);
                count++;
                String[] tokens = line.split(",");

                //CrimeVsCount.getOrDefault(tokens[10], CrimeVsCount.get(tokens[10]) + 1);


                //CrimeVsCount.put(tokens[10], CrimeVsCount.getOrDefault(tokens[10], 0) + 1);



                sample = new CrimeSample();
                if (tokens.length>=12 && tokens[4].contains("T") == true && tokens[10].length()>0 && tokens[5].length()>0) {
                    //System.out.println(tokens[10]);
                    sample.setType_of_crime(tokens[10]);
                    type_of_crime.add(tokens[10]);
                    //System.out.println(tokens[4]);


                    CrimeVsCount.put(tokens[10], CrimeVsCount.getOrDefault(tokens[10], 0) + 1);


                    //System.out.println(tokens[4].indexOf('T'));
                    sample.setDate(tokens[4]);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = formatter.parse(tokens[4]);
                    Date_Set.add(date);
                    //Date_Set1.add(tokens[4]);
                    sample.setTime(tokens[4]);
                    Time.add(tokens[4]);
                    //System.out.println(tokens[5]);
                    sample.setLocation(tokens[5]);
                    Loc_Set.add(tokens[5]);
                    crimesamples.add(sample);
                    //Log.d("MyActivity", "Just created: " + sample);

                }

            }

            //prune_function(crimesamples,Loc_Set,Date_Set,Time,type_of_crime);

            for(String crime : CrimeVsCount.keySet()) {

                Log.d("Crime Vs Count", crime + " : " + CrimeVsCount.get(crime));
            }

            Set<Map.Entry<String, Integer>> entries = CrimeVsCount.entrySet();
            Comparator<Entry<String, Integer>> valueComparator = new Comparator<Entry<String,Integer>>() {

                @Override
                public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
                    Integer v1 = e1.getValue();
                    Integer v2 = e2.getValue();
                    return(v2-v1);
                }
            };

            listOfEntries = new ArrayList<Entry<String, Integer>>(entries);



            // sorting HashMap by values using comparator
            Collections.sort(listOfEntries, valueComparator);



            int count1 = 10;
            // copying entries from List to Map
            for(Entry<String, Integer> entry : listOfEntries){
                sortedByValue.put(entry.getKey(), entry.getValue());
                count1-=1;
                if(count1==0)
                {
                    break;
                }

            }

            System.out.println("HashMap after sorting entries by values ");
            Set<Entry<String, Integer>> entrySetSortedByValue = sortedByValue.entrySet();

            for(Entry<String, Integer> mapping : entrySetSortedByValue){
                System.out.println(mapping.getKey() + " ==> " + mapping.getValue());


            }







        }
        catch(IOException e)
        {
            prune_function(crimesamples,Loc_Set,Date_Set,Time,type_of_crime);
        }



    }

    private void prune_function(List<CrimeSample> crimesamples, Set<String> loc_set, Set<Date> date_set,Set<String> time, Set<String> type_of_crime) {
        System.out.println("Inside prune function");
        final String[] sSelected = new String[3];
        List<String> Loc_List = new ArrayList<String>();
        Loc_List.add("Select Location");
        Loc_List.addAll(loc_set);
        List<String> Date_List = new ArrayList<String>();
        Date_List.add("Select date from");
        Date_List.addAll(time);
        List<String> Date_List1 = new ArrayList<String>();
        Date_List1.add("Select date to");
        Date_List1.addAll(time);
        //List<String> Time_List = new ArrayList<String>();
        //Time_List.addAll(time);
        List<String> Type_List = new ArrayList<String>();

        Type_List.addAll(type_of_crime);
        System.out.println("After initialisations");
//
//        Spinner spinner = (Spinner) findViewById(R.id.spinner5);
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Loc_List);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);
//        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                sSelected[0] =  parent.getItemAtPosition(position).toString();
//                Log.d("MyActivity", "Location: " + sSelected[0]);
//                if(sSelected[0].equals("Select Location")){
//                    //Do nothing
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });



        /*Spinner spinner2 = (Spinner) findViewById(R.id.spinner4);

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Date_List);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter1);
        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSelected[1] =parent.getItemAtPosition(position).toString();

                if(sSelected[1].equals("Select Date From")){
                    //Do nothing
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner3 = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Date_List);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter2);
        spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               sSelected[2] =parent.getItemAtPosition(position).toString();

               if(sSelected[2].equals("Select Date to")){
                   //Do nothing
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
                                           });

        //openActivity(sSelected);

        *//*Spinner spinner4 = (Spinner) findViewById(R.id.spinner3);
        spinner4.setOnItemSelectedListener((OnItemSelectedListener) this);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Time_List);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(dataAdapter3);

        Spinner spinner5 =  findViewById(R.id.spinner2);
        spinner5.setOnItemSelectedListener((OnItemSelectedListener) this);
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Time_List);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(dataAdapter4);
        *//*

        Log.d("MyActivity", "Location: " + sSelected[0]);
        Log.d("MyActivity", "Date from: " + sSelected[1]);
        Log.d("MyActivity", "Date to: " + sSelected[2]);
*/



    }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}


