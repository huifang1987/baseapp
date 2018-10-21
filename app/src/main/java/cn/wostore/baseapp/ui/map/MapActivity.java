package cn.wostore.baseapp.ui.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.app.App;
import cn.wostore.baseapp.base.BaseActivity;
import cn.wostore.baseapp.ui.setting.SettingActivity;
import cn.wostore.baseapp.ui.video.VideoListActivity;
import cn.wostore.baseapp.utils.SharePreferencesUtil;
import cn.wostore.baseapp.utils.ToastUtil;
import cn.wostore.baseapp.widget.CustomToolBar;

/**
 * Created by Fanghui at 2018-10-21
 */
public class MapActivity extends BaseActivity implements AMap.OnMarkerClickListener, GeocodeSearch.OnGeocodeSearchListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout layout;

    @BindView(R.id.tool_bar)
    CustomToolBar toolbar;

    @BindView(R.id.username)
    TextView userNameTv;

    @BindView(R.id.rl_dev_info)
    RelativeLayout devInfoRl;

    @BindView(R.id.tv_dev_name)
    TextView devnameTv;

    @BindView(R.id.tv_dev_loc)
    TextView devlocTv;

    private  MapView mapView;
    private AMap aMap;
    private UiSettings mUiSettings;
    private MarkerOptions markerOption;
    private GeocodeSearch geocoderSearch;
    private LatLng nanjingLatLng = new LatLng(32.0480873484,118.7911042523);

    private DevicesInfo dev1 = new DevicesInfo("中网一号", "1000001", "南京中网卫星通信股份有限公司", "YH0001","4G", "江苏南京", "32.1008463723" ,"118.7486761971", false);
    private DevicesInfo dev2 = new DevicesInfo("中网二号", "1000001", "南京中网卫星通信股份有限公司", "YH0001","4G", "江苏南京", "31.9573910205","118.8500118946", true);
    private DevicesInfo[] devs = {dev1, dev2};

    private DevicesInfo currentDevice;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MapActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setUpToolbar();
        String username = SharePreferencesUtil.getNickname();
        if (!TextUtils.isEmpty(username)){
            userNameTv.setText(username);
        }
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);  //不显示默认的缩放按钮
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nanjingLatLng, 12));
            setUpMap();
        }
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);

    }

    private void setUpMap() {
        aMap.setOnMarkerClickListener(this);
        for (int i=0 ; i<devs.length ; i++){
            DevicesInfo dev = devs[i];
            addMarkersToMap(dev, i);// 往地图上添加marker
        }
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(DevicesInfo dev, int index) {
        View  iconView;
        if (dev.online){
            iconView = LayoutInflater.from(this).inflate(R.layout.ic_device_online, mapView,false);
        } else {
            iconView = LayoutInflater.from(this).inflate(R.layout.ic_device_offline,mapView,false);
        }
        LatLng positon = new LatLng(Double.parseDouble(dev.lat), Double.parseDouble(dev.lng));
        String title = Integer.toString(index);
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromView(iconView))
                .position(positon)
                .title(title)
                .draggable(true);
        aMap.addMarker(markerOption);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void setUpToolbar() {
        toolbar.setTitle(getString(R.string.title_map));
        toolbar.setShowBack(true);
        toolbar.setBackActionItemResId(R.mipmap.icon_mine);
        toolbar.setOnCustomToolBarListener(new CustomToolBar.OnCustomToolBarListener() {
            @Override
            public void onBackClick() {
                layout.openDrawer(GravityCompat.START);
            }

            @Override
            public void onRightActionItemClick() {

            }
        });
    }

    @OnClick({R.id.ll_video, R.id.ll_setting, R.id.btn_close})
    public void clickMenu(View view){
        switch (view.getId()){
            case R.id.ll_video:
                VideoListActivity.launch(this);
                break;
            case R.id.ll_setting:
                SettingActivity.launch(this);
                break;
            case R.id.btn_close:
                if (devInfoRl.getVisibility() == View.VISIBLE){
                    devInfoRl.setVisibility(View.GONE);
                }
                break;
        }
    }


    /**
     * 对marker标注点点击响应事件
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        int index = Integer.parseInt(marker.getTitle());
        showDeviceInfo(devs[index]);
        return true;
    }

    private void showDeviceInfo(DevicesInfo dev){
        currentDevice = dev;
        getAddress(Double.parseDouble(dev.lat), Double.parseDouble(dev.lng));
        if (devInfoRl.getVisibility() == View.VISIBLE){
            devInfoRl.setVisibility(View.GONE);
        }
        devnameTv.setText(dev.name);
        int colorRes;
        if (dev.online){
            colorRes = R.color.color_22c83a;
        } else {
            colorRes = R.color.color_ff3333;
        }
        devnameTv.setTextColor(App.getContext().getResources().getColor(colorRes));
        devInfoRl.setVisibility(View.VISIBLE);
    }

    /**
     * 逆地理编码回调 （从经纬度查地址）
     */
    public void getAddress(double lat, double lng) {
        LatLonPoint latLonPoint = new LatLonPoint(lat, lng);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }

    /**
     * 逆地理编码回调 （从经纬度查地址）
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (Double.parseDouble(currentDevice.lat) != result.getRegeocodeQuery().getPoint().getLatitude()
                || Double.parseDouble(currentDevice.lng) != result.getRegeocodeQuery().getPoint().getLongitude()){
            return;
        }
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                String addressName = result.getRegeocodeAddress().getFormatAddress();
                devlocTv.setText(addressName);
            } else {
                devlocTv.setText(mContext.getResources().getString(R.string.unknown_location));
            }
        } else {
            devlocTv.setText(mContext.getResources().getString(R.string.unknown_location));
        }
    }

    /**
     * 地理编码查询回调 (从地址查经纬度)
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    /**
     * 测试类
     */
    private static class DevicesInfo {
        private String name;
        private String number;
        private String owner;
        private String model;
        private String route;
        private String location;
        private boolean online;
        private String lat;
        private String lng;

        public DevicesInfo(String name, String number, String owner, String model, String route, String location, String lat, String lng, boolean online) {
            this.name = name;
            this.number = number;
            this.owner = owner;
            this.model = model;
            this.route = route;
            this.location = location;
            this.online = online;
            this.lat = lat;
            this.lng = lng;
        }

        public String getName() {
            return name;
        }

        public String getNumber() {
            return number;
        }

        public String getOwner() {
            return owner;
        }

        public String getModel() {
            return model;
        }

        public String getRoute() {
            return route;
        }

        public String getLocation() {
            return location;
        }

        public boolean isOnline() {
            return online;
        }

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }
    }
}
