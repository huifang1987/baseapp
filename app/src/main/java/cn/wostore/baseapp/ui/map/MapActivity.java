package cn.wostore.baseapp.ui.map;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static cn.wostore.baseapp.Constants.CHANNEL_MAP;
import static cn.wostore.baseapp.Constants.STATUS_MAP;
import static cn.wostore.baseapp.Constants.SUCCESS_RESP;
import static cn.wostore.baseapp.Constants.TERMINAL_STATUS_ONLINE;

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

    private  MapView mapView;
    private AMap aMap;
    private UiSettings mUiSettings;
    private MarkerOptions markerOption;
    private GeocodeSearch geocoderSearch;
    private LatLng nanjingLatLng = new LatLng(32.0480873484,118.7911042523);

    private List<TerminalBean> terminalList = new ArrayList<>();

    private TerminalBean currentTerminal;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MapActivity.class);
        context.startActivity(intent);
    }

    private void getTermialList(){
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
                                terminalList.clear();
                                terminalList.addAll(response.getData().getList());
                                setUpMap();
                            } else {
                                ToastUtil.showShort(mContext, response.getMessage());
                            }
                        } catch (Exception e) {
                            L.e(e.getLocalizedMessage());
                            ToastUtil.showShort(mContext, mContext.getResources().getString(R.string.login_fail));

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);  //不显示默认的缩放按钮
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nanjingLatLng, 11));
        }
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        getTermialList();
    }

    private void setUpMap() {
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
        View  iconView;
        if (TERMINAL_STATUS_ONLINE.equals(dev.getStatus())){
            iconView = LayoutInflater.from(this).inflate(R.layout.ic_device_online, mapView,false);
        } else {
            iconView = LayoutInflater.from(this).inflate(R.layout.ic_device_offline,mapView,false);
        }
        LatLng positon = new LatLng(Double.parseDouble(dev.getLat()), Double.parseDouble(dev.getLon()));
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
