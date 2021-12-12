package pl.remplewicz.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.remplewicz.R;

public class HomeFragment extends Fragment{

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng mochowo = new LatLng(52.76778751720479, 19.558075570858506);
            googleMap.addMarker(new MarkerOptions().position(mochowo).title("Marker in Mochowo"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(mochowo));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mochowo,10));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_home);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }























//
//    private HomeViewModel homeViewModel;
//    private GoogleMap mMap;
//    private ActivityMapsBinding binding;
//    private MapView mMapView;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//        mMapView = (MapView) rootView.findViewById(R.id.mapView);
//        mMapView.onCreate(savedInstanceState);
//        mMapView.onResume();
//        try {
//            MapsInitializer.initialize(getActivity().getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        mMapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                mMap = googleMap;
//
//                // Add a marker in Sydney and move the camera
//                LatLng mochowo = new LatLng(52.76778751720479, 19.558075570858506);
////                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_baseline_emoji_people_24);
//                mMap.addMarker(new MarkerOptions()
//                        .position(mochowo)
//                        .icon(bitmapDescriptorFromVector(rootView.getContext(),R.drawable.ic_baseline_emoji_people_24))
//                        .title("Marker in Mochowo"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(mochowo));
//            }
//        });
//        return rootView;
//    }
//
////
////        String url = "http://google.com";
////        RequestQueue queue = Volley.newRequestQueue(this);
////        System.out.println(url);
////        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////                System.out.println(response);
////            }
////        },null);
////        queue.add(stringRequest);
////        queue.start();
//
//
//    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
//        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
//        assert vectorDrawable != null;
//        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
//        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        vectorDrawable.draw(canvas);
//        return BitmapDescriptorFactory.fromBitmap(bitmap);
//    }
}