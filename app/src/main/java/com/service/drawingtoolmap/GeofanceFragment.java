package com.service.drawingtoolmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class GeofanceFragment extends Fragment implements View.OnClickListener , OnMapReadyCallback {


    private MapView mapView;
    private GoogleMap map;
    private RecyclerView recyclerView;
   // private com.softobiz.petconnect.customView.ListViewMaxHeight geoList;
    private Button addnewFence;
    private WebView myWebView;
    /*private GeoFancingModel geoFancingModel;
    private GeoFancingModel geoFancingModel_new;
    */
    private boolean b_flag = false;
    private String key_flag;
    private List<LatLng> arrayPoints;
    private Button btnDelete;
    private Button btnEdit;
    private SessionManager session;
    private ProgressDialog pDialog;
//    private geoListAdapter adapter;
    private LinearLayout layoutSave;
    private LinearLayout layoutAddnew;
    private LinearLayout layout300;
    private SwitchCompat txt300;
    private LinearLayout btnSave;
    private EditText geo_fenceName;
 //   private GeoFancingModel geoFancingModel1;
    private int editPossition;
    private String editGeoId;
//    private GeoFancingModel geoFancingModelDelete;
    private LinearLayout btncancel;
    private LinearLayout btnReSet;
    private int autoFenceFlag=0;
    private ProgressBar progress_barGet;
    private int SaveStatusPossition;
    private ImageView backIcon;


    List<LatLng> location=new ArrayList<>();

    public GeofanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_geofance, container, false);
        /*geoFancingModel = new GeoFancingModel();
        geoFancingModel_new = new GeoFancingModel();*/

        init(view);


       /* session = new SessionManager(getActivity());

        if (session.checkLogin()) {
            HashMap<String, String> user = session.getUserDetails();
            // name
            String name = user.get(SessionManager.KEY_NAME);
            // email
            String email = user.get(SessionManager.KEY_EMAIL);
            String userId = user.get(SessionManager.KEY_USER_ID);
            String auth = user.get(SessionManager.KEY_AUTH);
            Log.e("Session", "in Session" + name + " :: " + email + " :: " + userId + "  :: " + auth);
            geoFancingModel.setAuthorization("Bearer " + auth);

            geoFancingModel.setUserid(userId);
            new getGeoFance().execute();


        }*/

        return view;
    }

    private void init(View view) {

        myWebView = (WebView)view.findViewById(R.id.webview);
        myWebView.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("file:///android_asset/testmap.html");

        //geoList = (com.softobiz.petconnect.customView.ListViewMaxHeight) view.findViewById(R.id.geoList);
        addnewFence = (Button) view.findViewById(R.id.addnewFence);
        layoutSave = (LinearLayout)view.findViewById(R.id.layoutSave);
        layoutAddnew = (LinearLayout)view.findViewById(R.id.layoutAddnew);
        layout300 = (LinearLayout)view.findViewById(R.id.layout300);
        txt300=(SwitchCompat)view.findViewById(R.id.txt300);

        btnSave = (LinearLayout) view.findViewById(R.id.btnsave);
        btnReSet = (LinearLayout) view.findViewById(R.id.btnReSet);
        btncancel = (LinearLayout) view.findViewById(R.id.btncancel);

        geo_fenceName = (EditText)view.findViewById(R.id.geo_fenceName);
        progress_barGet=(ProgressBar)view.findViewById(R.id.progress_barGet);
        backIcon=(ImageView)view.findViewById(R.id.backIcon);


//        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_text, data);
       /*adapter = new geoListAdapter(getActivity(), RufData.geoFanceModleList);
        geoList.setAdapter(adapter);*/


      /*  final SwipeDetector swipeDetector = new SwipeDetector();
        geoList.setOnTouchListener(swipeDetector);

        geoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //      final Exercises exercises = new Exercises();
                if (swipeDetector.swipeDetected()) {
                    //get the object's position in the list

                    LinearLayout v = (LinearLayout) view.findViewById(R.id.BtnLinearLayout);
                    btnDelete = (Button) view.findViewById(R.id.btnDelete);
                    btnEdit = (Button) view.findViewById(R.id.btnEdit);

                    v.setVisibility(View.VISIBLE);

                    btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            RufData.geoFanceModleList.remove(position);

                            adapter = new geoListAdapter(getActivity(), RufData.geoFanceModleList);
                            geoList.setAdapter(adapter);
                        }
                    });
                    btnEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(v.getContext(), mapDrawing.class);
                            Log.e("test position", "" + position);
                            intent.putExtra("position", "" + position);
                            intent.putExtra("name", RufData.geoFanceModleList.get(position).getGeoName());
                            startActivity(intent);


                        }
                    });


                } else {
                    //Item click

                    Log.e("click item", "name :" + RufData.geoFanceModleList.get(position).getGeoName());
                    Log.e("click item", "Shape :" + RufData.geoFanceModleList.get(position).getShape());
                    Log.e("click item", "redius :" + RufData.geoFanceModleList.get(position).getRadius());
                    Log.e("click item", "Lat  :" + RufData.geoFanceModleList.get(position).getLat());
                    Log.e("click item", "Log :" + RufData.geoFanceModleList.get(position).getLng());
                    Log.e("click item", "Polyon lat :" + RufData.geoFanceModleList.get(position).getPolygonLat());
                    Log.e("click item", "polyon Log :" + RufData.geoFanceModleList.get(position).getPolygonLog());

                    if (RufData.geoFanceModleList.get(position).getShape().toString().trim().equals("polygon")) {
                        Log.e("test", "test .......1");
                        showPolygonGeoFence(RufData.geoFanceModleList.get(position));
                    } else {
                        showCircleGeoFence(RufData.geoFanceModleList.get(position));
                    }

                }
            }
        });*/
        addnewFence.setOnClickListener(this);
        layout300.setOnClickListener(this);
        txt300.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btncancel.setOnClickListener(this);
        btnReSet.setOnClickListener(this);
        backIcon.setOnClickListener(this);

       // SwipeMenuCreator();


    }

    /*private void showPolygonGeoFence(GeoFancingModel geoFancingModel) {
        arrayPoints = new ArrayList<LatLng>();

        if (null != map) {
            map.clear();

            String[] log = geoFancingModel.getPolygonLog().split(",");
            String[] lat = geoFancingModel.getPolygonLat().split(",");
            for(int i=0;i<log.length; i++ )
            {

                arrayPoints.add(new LatLng(Double.valueOf(lat[i]), Double.valueOf(log[i])));
            }

            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.addAll(arrayPoints);
            polygonOptions.strokeColor(Color.GRAY);
            polygonOptions.strokeWidth(2);
            polygonOptions.fillColor(Color.argb(80, 172, 229, 161));
            Polygon polygon = map.addPolygon(polygonOptions);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(lat[1]), Double.valueOf(log[1])), 10);
            map.animateCamera(cameraUpdate);


        }
    }

    private void showCircleGeoFence(GeoFancingModel geoFancingModel) {
        if (null != map) {
            map.clear();
            float[]i={110,29,89};
            Circle circle = map.addCircle(new CircleOptions()
                    .center(new LatLng(geoFancingModel.getLat(), geoFancingModel.getLog()))
                    .radius(geoFancingModel.getRadius())
                    .strokeColor(Color.GRAY)
                    .strokeWidth(2)
                    .fillColor(Color.argb(80, 172, 229, 161)));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(geoFancingModel.getLat(), geoFancingModel.getLog()), 10);
            map.animateCamera(cameraUpdate);
        }
    }
*/

    private void mapInit(View view, Bundle savedInstanceState) {
        MapsInitializer.initialize(getActivity());

        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())) {
            case ConnectionResult.SUCCESS:

              /*  mapView = (MapView) view.findViewById(R.id.map);
                mapView.onCreate(savedInstanceState);
                // Gets to GoogleMap from the MapView and does initialization stuff
                if (mapView != null) {
                    mapView.getMapAsync(this);
                }*/
                break;
            case ConnectionResult.SERVICE_MISSING:
                Toast.makeText(getActivity(), "SERVICE MISSING", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Toast.makeText(getActivity(), "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View view) {

        if (view == addnewFence) {

            editPossition=-1;
            editGeoId=null;
            layoutSave.setVisibility(View.VISIBLE);
            layoutAddnew.setVisibility(View.GONE);
            layout300.setVisibility(View.GONE);
 //           geoList.setVisibility(View.GONE);
            myWebView.loadUrl("javascript:showPolygonTool()");
           // top_menu.setVisibility(View.VISIBLE);
            myWebView.loadUrl("javascript:clrMap()");

        }
        if(view==layout300 || view==txt300)
        {
            if(autoFenceFlag==0) {
                GPSTracker gpsTracker = new GPSTracker(getActivity());

                Log.e("300", "" + gpsTracker.getLatitude() + "   " + gpsTracker.getLongitude());

                myWebView.loadUrl("javascript:DrawCircle(" + "91.44" + "," + gpsTracker.getLatitude() + "," + gpsTracker.getLongitude() + ")");

                autoFenceFlag=1;
            }
            else
            {
                myWebView.loadUrl("javascript:clrMap()");
                autoFenceFlag=0;
            }
        }
        if(view==btnSave)
        {
            myWebView.loadUrl("javascript:GetPolygonListener()");
            //String geoFanceName = geo_fenceName.getText().toString().trim();


        }
        if(view==btncancel)
        {
            hideSoftKeyboard(getActivity());
            layoutSave.setVisibility(View.GONE);
            layoutAddnew.setVisibility(View.VISIBLE);
            layout300.setVisibility(View.VISIBLE);
         //   geoList.setVisibility(View.VISIBLE);
            myWebView.loadUrl("javascript:removePolygonTool()");
            myWebView.loadUrl("javascript:clrMap()");
            geo_fenceName.setText("");
        }
        if(view==btnReSet)
        {
            myWebView.loadUrl("javascript:clrMap()");
            geo_fenceName.setText("");
        }
        if(view==backIcon)
        {

        }

    }



    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        map.clear();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //  openBottomSheet("Title");
                map.clear();

            }
        });
    }

    public class WebAppInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }

        GPSTracker gpsTracker = new GPSTracker(getActivity());


        @JavascriptInterface
        public double getLat() {

            Log.e("::::::::", "getLat   ::" + gpsTracker.getLatitude());
          /*  if (geoFancingModel_new.getLat() != 0.0) {
                return geoFancingModel_new.getLat();
            } else {
                return gpsTracker.getLatitude();
            }*/
          return 0;
        }

        @JavascriptInterface
        public double getLog() {

            Log.e("::::::::", "getLng  :" + gpsTracker.getLongitude());/*
            if (geoFancingModel_new.getLog() != 0.0) {
                return geoFancingModel_new.getLog();
            } else {
                Log.e("::::::::", "getLng  :" + gpsTracker.getLongitude());
                return gpsTracker.getLongitude();
            }*/
            return 0;
        }

        @JavascriptInterface
        public void showToast(String toast) {

            Log.e("show toast ::::", "::   " + toast);
            if (null != toast) {
                String[] data = toast.split(",");
                /*geoFancingModel.setRadius(Double.valueOf(data[0]));
                geoFancingModel.setLat(Double.valueOf(data[1]));
                geoFancingModel.setLog(Double.valueOf(data[2]));*/
                b_flag = true;
                key_flag = "circle";
            }
        }

        @JavascriptInterface
        public void showPolygonCoordinates(String[] coordinates) {

            location=new ArrayList<>();
            Log.e("showPolygonCoordinates ", "hello  show polygon Coordinates");


            StringBuilder stringBuilderLat = new StringBuilder();
            for (int i = 0; i < coordinates.length; i++) {
                stringBuilderLat.append(coordinates[i]);
            }
          //  Log.e("Coordinates  ", "location ::   " + stringBuilderLat);

            String[] outPutCoordinaters = stringBuilderLat.toString().split("::");


            StringBuilder strOutputLAT = new StringBuilder();
            StringBuilder strOutputLog = new StringBuilder();
            for (int i = 0; i < outPutCoordinaters.length; i++) {
             //   Log.e("outPutCoordinaters  lat", "::   " + outPutCoordinaters[i]);

                String[] outPutCoordinaters2 = outPutCoordinaters[i].split(",");

                if(!outPutCoordinaters2[0].toString().trim().equals("NaN"))
                {
                    strOutputLAT.append(outPutCoordinaters2[0] + ",");
                    strOutputLog.append(outPutCoordinaters2[1] + ",");

                    location.add(new LatLng(Double.valueOf(outPutCoordinaters2[0]), Double.valueOf(outPutCoordinaters2[1])));
                }
            }

            for(LatLng latLng:location)
            {
                Log.e("TAG", "Locaton ::"+latLng);
            }


            b_flag = true;
            key_flag = "polygon";
        }



        /**
         * Show Dialog
         *
         * @param dialogMsg
         */
        @JavascriptInterface
        public void showDialog(String[] dialogMsg) {
            AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();

            // Setting Dialog Title
            alertDialog.setTitle("Dialog");
            // Setting Dialog Message

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < dialogMsg.length; i++) {
                stringBuilder.append(dialogMsg[i]);
            }
            alertDialog.setMessage(stringBuilder);

            // Setting alert dialog icon
            //alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(mContext, "Dialog dismissed!", Toast.LENGTH_SHORT).show();
                }
            });
            // Showing Alert Message
            alertDialog.show();
        }

        /**
         * Intent - Move to next screen
         */
        public void moveToNextScreen() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            // Setting Dialog Title
            alertDialog.setTitle("Alert");
            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want to leave to next screen?");
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Move to Next screen
                          /*  Intent chnIntent = new Intent(AndroidJSWebView.this, ChennaiIntent.class);
                            startActivity(chnIntent);*/
                        }
                    });
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Cancel Dialog
                            dialog.cancel();
                        }
                    });
            // Showing Alert Message
            alertDialog.show();
        }
    }



    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
