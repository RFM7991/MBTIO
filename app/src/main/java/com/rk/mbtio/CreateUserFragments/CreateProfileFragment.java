package com.rk.mbtio.CreateUserFragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.rk.mbtio.DriverActivity;
import com.rk.mbtio.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CreateProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener, LocationListener {

    private String name, MBTI, age, height, sex, interest, bio;

    private String feet, inches;

    private LocationManager locationManager;
    private Location mLocation;
    private String longitude, latitude;

    private boolean locationEnabled;


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public CreateProfileFragment() {
        // required empty
    }

    public static CreateProfileFragment newInstance(String mbti) {
        CreateProfileFragment fragment = new CreateProfileFragment();

        Bundle args = new Bundle();
        args.putString("mbti",mbti);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            MBTI =  getArguments().get("mbti").toString();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_profile, container, false);


        TextView score = view.findViewById(R.id.MBTIIn);



        if (getArguments() != null) {
            MBTI =  getArguments().get("mbti").toString();
            score.setText(MBTI);
        }

        // initialize spinners with adapters
        initSpinners(view);

        // initialize location manger
        initLocationManager();

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void initSpinners(View view) {

        // sex
        String[] sexes = {"Male", "Female"};
        // interests
        String[] interests = {"Male", "Female", "Both"};

        // height
        String[] feetList = {"1", "2", "3", "4", "5", "6", "7", "8" };
        String[] inchesList = {"0", "1", "2", "3",  "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        // spinners
        Spinner sexSpinner = (Spinner) view.findViewById(R.id.sexIn);
        Spinner interestSpinner = (Spinner) view.findViewById(R.id.interestIn);
        Spinner feetSpinner = (Spinner) view.findViewById(R.id.feet);
        Spinner inchSpinenr = (Spinner) view.findViewById(R.id.inches);

        // Spinner Adapters
        ArrayAdapter<String> sexAdapter;
        ArrayAdapter<String> interestAdapter;
        ArrayAdapter<String> feetAdapter;
        ArrayAdapter<String> inchAdapter;

        // Set array to spinner
        sexAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, sexes);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item); // The drop down view
        sexSpinner.setAdapter(sexAdapter);
        sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex = parent.getItemAtPosition(position).toString().substring(0,1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // interests
        // Application of the Array to the Spinner
        interestAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, interests);
        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item); // The drop down view
        interestSpinner.setAdapter(interestAdapter);
        interestSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                interest = parent.getItemAtPosition(position).toString().substring(0,1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // feet
        // Application of the Array to the Spinner
        feetAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, feetList);
        feetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item); // The drop down view
        feetSpinner.setAdapter(feetAdapter);
        feetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                feet = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // inches
        // Application of the Array to the Spinner
        inchAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, inchesList);
        inchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item); // The drop down view
        inchSpinenr.setAdapter(inchAdapter);
        inchSpinenr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                inches = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // get name as string from edit text
    public String getName() {
        EditText e = getView().findViewById(R.id.nameIn);
        return e.getText().toString();
    }
    // get mbti as string from edit text
    public String getMBTI() {
        EditText e = getView().findViewById(R.id.MBTIIn);
        return e.getText().toString();
    }
    // get age as string from edit text
    public String getAge() {
        EditText e = getView().findViewById(R.id.AgeIn);
        return e.getText().toString();
    }
    // get bio as string from edit text
    public String getBio() {
        EditText e = getView().findViewById(R.id.bioIn);
        return e.getText().toString();
    }

    // return JSON object as a string
    public HashMap<String, String> getFields() {

        HashMap<String, String> fields = new HashMap<String, String>();

        try {
            fields.put("uid", "1");
            fields.put("pin", "2");
            fields.put("name", getName());
            fields.put("MBTI", getMBTI());
            fields.put("age", getAge());
            fields.put("height", feet +"0"+ inches);
            fields.put("sex", sex);
            fields.put("interest", interest);
            fields.put("bio", getBio());
            fields.put("long", "0");
            fields.put("lat", "0");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fields;
    }

    // set text for MBTI score
    public void setScore(View view, String s) {
        EditText v =  view.findViewById(R.id.MBTIIn);

        v.setText(s);
    }

    // get profile as a JSON object to Post
    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();

        try {
            obj.put("uid", "1");
            obj.put("pin", "2");
            obj.put("name", getName());
            obj.put("mbti", getMBTI());
            obj.put("age", getAge());
            obj.put("height", feet +"0"+ inches);
            obj.put("sex", sex);
            obj.put("interest", interest);
            obj.put("bio", getBio());
            obj.put("long", "2"); // todo fix long
            obj .put("lat", "2"); // todo fix lat
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    // initialize LocationManager
    public void initLocationManager() {

        // check if user has given the app permission to use location
        checkLocationPermission();

        if (locationEnabled) {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        }

    }

    // check for user permission and show alert dialog if neccesary
    public boolean checkLocationPermission() {
        // if permission hasn't been granted
        if (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            // check if permission must be given explicitly by user
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                // build an asynchronous alert message
                new AlertDialog.Builder(this.getContext())
                        .setTitle("Let MBTIO Locaion?")
                        .setMessage("Would you like to allow Bottled to use your location? Your perfect match could be around the corner!")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            // get permission
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // explicit permission unnecessary go ahead and  request from system
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            // permissions unchecked
            return false;
        } else {
            // Permission has been granted previously
            locationEnabled = true;
            return true;
        }

    }

    //
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // if request is cancelled, results are empty
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission granted
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationEnabled = true;
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    // get longitude as a string
    public String getLongitude() {
        if (mLocation != null) return "" + mLocation.getLongitude();
        else return null;
    }

    // get latitude as a string
    public String getLatitude() {
        if (mLocation != null) return "" + mLocation.getLatitude();
        else return null;

    }

    // checkProfile
    public boolean isReady() {

        Iterator it = getFields().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            if (!pair.getKey().equals("bio")) {
                if (pair.getValue().toString().length() <= 0)
                    return false;

            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return true;
    }

    //
    public boolean readyForPost() {

        JSONObject obj = getJSON();
        Iterator<String> keys = obj.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            try {
                //      Log.i("RFM", "JSON: " + obj.get(key).toString());
                if (!obj.get(key).equals("bio") && obj.get(key).toString().length() <= 0) {
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
