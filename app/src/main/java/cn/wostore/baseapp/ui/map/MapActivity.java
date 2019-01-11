package cn.wostore.baseapp.ui.map;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
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
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.api.ApiEngine;
import cn.wostore.baseapp.api.request.GetTerminalListRequest;
import cn.wostore.baseapp.api.response.GetTerminalListResponse;
import cn.wostore.baseapp.api.response.GetTerminalListResponse.DataBean.TerminalBean;
import cn.wostore.baseapp.base.BaseActivity;
import cn.wostore.baseapp.rx.RxSchedulers;
import cn.wostore.baseapp.ui.setting.SettingActivity;
import cn.wostore.baseapp.ui.video.VideoListActivity;
import cn.wostore.baseapp.utils.CommonUtil;
import cn.wostore.baseapp.utils.L;
import cn.wostore.baseapp.utils.SharePreferencesUtil;
import cn.wostore.baseapp.utils.ToastUtil;
import cn.wostore.baseapp.widget.CustomToolBar;
import cn.wostore.baseapp.widget.LoadingDialog;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static cn.wostore.baseapp.Constants.CHANNEL_MAP;
import static cn.wostore.baseapp.Constants.STATUS_MAP;
import static cn.wostore.baseapp.Constants.SUCCESS_RESP;
import static cn.wostore.baseapp.Constants.TERMINAL_STATUS_ONLINE;
import static com.amap.api.location.AMapLocationClientOption.*;

/**
 * Created by Fanghui at 2018-10-21
 */
public class MapActivity extends BaseActivity implements AMap.OnMarkerClickListener, GeocodeSearch.OnGeocodeSearchListener {

    private static final String TAG = MapActivity.class.getSimpleName();

    @BindView(R.id.drawer_layout)
    DrawerLayout layout;

    @BindView(R.id.tool_bar)
    CustomToolBar toolbar;

    @BindView(R.id.username)
    TextView userNameTv;

    @BindView(R.id.phone_info)
    TextView phoneInfo;

    @BindView(R.id.rl_dev_info)
    RelativeLayout devInfoRl;

    @BindView(R.id.tv_dev_name)
    TextView devNameTv;

    @BindView(R.id.tv_dev_num)
    TextView devNumTv;

    @BindView(R.id.tv_company_name)
    TextView companyNameTv;

    @BindView(R.id.tv_device_model)
    TextView devModelTv;

    @BindView(R.id.tv_channel)
    TextView channelTv;

    @BindView(R.id.tv_dev_status)
    TextView devStatusTv;

    @BindView(R.id.tv_dev_loc)
    TextView devlocTv;

    @BindView(R.id.iv_device_img)
    ImageView devImgIv;

    @BindView(R.id.tv_weather)
    TextView weatherTv;

    /***************高德地图相关*****************/
    private MapView mapView;
    private AMap aMap;
    private UiSettings mUiSettings;
    private MarkerOptions markerOption;
    private GeocodeSearch geocoderSearch;
    public AMapLocationClient mLocationClient = null; //声明AMapLocationClient类对象
    public AMapLocationListener mLocationListener = null; //设置定位回调监听
    public AMapLocationClientOption mLocationOption = null;     //声明AMapLocationClientOption对象
    private List<TerminalBean> terminalList = new ArrayList<>();
    /**************天气查询相关*************/
    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;


    private static final String TITLE_MY_LOCAION = "my_location";

    private String currentCity = "南京市";
    private TerminalBean currentTerminal;   //当前展示详情的设备

    public static void launch(Context context) {
        Intent intent = new Intent(context, MapActivity.class);
        context.startActivity(intent);
    }

    private void getTermialList(){
        final LoadingDialog dialog = LoadingDialog.newInstance();
        dialog.show(this.getFragmentManager(), TAG);
        GetTerminalListRequest request = new GetTerminalListRequest();
        request.setUserId(SharePreferencesUtil.getUserID());
        ApiEngine.getInstance().getService()
                .getTerminalList(request.getRequestBodyMap())
                .compose(RxSchedulers.<GetTerminalListResponse>io_main())
                .subscribe(new Observer<GetTerminalListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetTerminalListResponse response) {
                        try {
                            if (SUCCESS_RESP.equals(response.getSuccess())){
                                if (response.getData().getList().size() != 0){
                                    terminalList.clear();
                                    terminalList.addAll(response.getData().getList());
                                    setUpMap();
                                } else {
                                    ToastUtil.showShort(mContext, mContext.getResources().getString(R.string.get_device_none));
                                }

                            } else {
                                ToastUtil.showShort(mContext, response.getMessage());
                            }
                        } catch (Exception e) {
                            L.e(e.getLocalizedMessage());
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        ToastUtil.showShort(mContext, mContext.getResources().getString(R.string.get_device_fail));
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                    }
                });
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
        String username = SharePreferencesUtil.getUsername();
        if (!TextUtils.isEmpty(username)){
            userNameTv.setText(username);
        }
        if (!TextUtils.isEmpty(Build.MODEL)){
            phoneInfo.setText(Build.MODEL);
        }
        initMap(savedInstanceState);
        initLocate();
        startLocate();
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        getTermialList();
    }


    /**
     * 初始化地图
     */
    private void initMap(Bundle savedInstanceState){
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);  //不显示默认的缩放按钮
        }
    }

    /**
     * 初始化定位
     */
    private void initLocate(){
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                ToastUtil.showShort(mContext, aMapLocation.getAddress());
                LatLng positon = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                getAddress(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                clearMyLocation();
//                markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
//                        .position(positon)
//                        .title(TITLE_MY_LOCAION)
//                        .draggable(true);
//                aMap.addMarker(markerOption);
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positon, 11));
            }
        };

        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    /**
     * 清除我的定位点
     */
    private void clearMyLocation() {
        //获取地图上所有Marker
        List<Marker> mapScreenMarkers = aMap.getMapScreenMarkers();
        for (int i = 0; i < mapScreenMarkers.size(); i++) {
            Marker marker = mapScreenMarkers.get(i);
            if (TITLE_MY_LOCAION.equals(marker.getTitle())) {
                marker.remove();//移除当前Marker
            }
        }
        aMap.invalidate();//刷新地图
    }

    /**
     * 清除设备定位点
     */
    private void clearTerminalLocations() {
        //获取地图上所有Marker
        List<Marker> mapScreenMarkers = aMap.getMapScreenMarkers();
        for (int i = 0; i < mapScreenMarkers.size(); i++) {
            Marker marker = mapScreenMarkers.get(i);
            if (! TITLE_MY_LOCAION.equals(marker.getTitle())) {
                marker.remove();//移除当前Marker
            }
        }
        aMap.invalidate();//刷新地图
    }

    /**
     * 查询实时天气
     */
    private void searchliveweather() {
        mquery = new WeatherSearchQuery(currentCity, WeatherSearchQuery.WEATHER_TYPE_LIVE);//检索参数为城市和天气类型，实时天气为1、天气预报为2
        mweathersearch=new WeatherSearch(this);
        mweathersearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
            @Override
            public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
                if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                        LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                        weatherTv.setText(weatherlive.getTemperature()+"°C "+weatherlive.getWeather());
                        //ToastUtil.showShort(mContext, weatherlive.getTemperature()+"°C "+weatherlive.getWeather());
                    }
                }
            }

            @Override
            public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

            }
        });
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }

    /**
     * 开始定位
     */
    private void startLocate() {
        mLocationClient.startLocation();
    }

    private void setUpMap() {
        clearTerminalLocations();
        aMap.setOnMarkerClickListener(this);
        for (int i = 0; i< terminalList.size() ; i++){
            TerminalBean dev = terminalList.get(i);
            addMarkersToMap(dev, i);// 往地图上添加marker
        }
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(TerminalBean dev, int index) {
        float hue= BitmapDescriptorFactory.HUE_GREEN;
        if (!TERMINAL_STATUS_ONLINE.equals(dev.getStatus())){
            hue = BitmapDescriptorFactory.HUE_RED;
        }
        LatLng positon = new LatLng(Double.parseDouble(dev.getLat()), Double.parseDouble(dev.getLon()));
        String title = Integer.toString(index);
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(hue))
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
        toolbar.setTitle(currentCity);
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

    @OnClick({R.id.ll_video, R.id.ll_setting, R.id.btn_close, R.id.btn_locate})
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
                currentTerminal = null;
                break;
            case R.id.btn_locate:
                startLocate();
        }
    }


    /**
     * 对marker标注点点击响应事件
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        int index = Integer.parseInt(marker.getTitle());
        showDeviceInfo(terminalList.get(index));
        return true;
    }

    private void showDeviceInfo(TerminalBean dev){
        currentTerminal = dev;
        getAddress(Double.parseDouble(dev.getLat()), Double.parseDouble(dev.getLon()));
        if (devInfoRl.getVisibility() == View.VISIBLE){
            devInfoRl.setVisibility(View.GONE);
        }
        int colorRes;
        if (TERMINAL_STATUS_ONLINE.equals(dev.getStatus())){
            colorRes = R.color.color_22c83a;
        } else {
            colorRes = R.color.color_ff3333;
        }
        devNameTv.setTextColor(mContext.getResources().getColor(colorRes));
        devNameTv.setText(dev.getTerminalName());
        devNumTv.setText(dev.getTerminalNum());
        companyNameTv.setText(dev.getCompanyName());
        devModelTv.setText(dev.getTerminalModel());
        channelTv.setText(CHANNEL_MAP.get(dev.getChannel()));
//        devlocTv.setText(dev.getAddress());
        devStatusTv.setTextColor(mContext.getResources().getColor(colorRes));
        devStatusTv.setText(STATUS_MAP.get(dev.getStatus()));
        devInfoRl.setVisibility(View.VISIBLE);
        Glide.with(mContext).load(CommonUtil.getImageUrl(dev.getImage())).into(devImgIv);
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
        if (currentTerminal == null){
            currentCity = result.getRegeocodeAddress().getCity();
            toolbar.setTitle(currentCity);
            searchliveweather();
            return;
        }
        if (Double.parseDouble(currentTerminal.getLat()) != result.getRegeocodeQuery().getPoint().getLatitude()
                || Double.parseDouble(currentTerminal.getLon()) != result.getRegeocodeQuery().getPoint().getLongitude()){
            return;
        }
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                String addressName = result.getRegeocodeAddress().getProvince()
                        +result.getRegeocodeAddress().getCity()
                        +result.getRegeocodeAddress().getDistrict();
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

}
