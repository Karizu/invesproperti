package com.selada.invesproperti.presentation.portofolio.tablayout;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.madapps.liquid.LiquidRefreshLayout;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.SliderItem;
import com.selada.invesproperti.model.response.detailproject.ResponseDetailProject;
import com.selada.invesproperti.presentation.adapter.SliderAdapterExample;
import com.selada.invesproperti.presentation.home.DetailProductActivity;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFragment extends Fragment {
    @BindView(R.id.imageSlider)
    SliderView imageSlider;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;
    @BindView(R.id.tv_count_day)
    TextView tvCountDay;
    @BindView(R.id.tv_start_price)
    TextView tv_start_price;
    @BindView(R.id.tv_end_price)
    TextView tv_end_price;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.rp_1_350_000_lot)
    TextView tv_price_per_lot;
    @BindView(R.id._7000_lot)
    TextView tv_total_lot;
    @BindView(R.id.rp_1_350_000_lot5)
    TextView tv_period_deviden;
    @BindView(R.id.rp_1_350_000_lot4)
    TextView tv_estimasi_deviden;
    @BindView(R.id.textView)
    TextView tv_desc_usaha;
    @BindView(R.id.textView2)
    TextView tv_address;
    @BindView(R.id.refreshLayout)
    LiquidRefreshLayout refreshLayout;

    private List<SliderItem> mSliderItems;
    private String id = "";
    private String latitude, longitude;
    private ResponseDetailProject detailProject;

    @OnClick(R.id.frame_open_maps)
    void onClickOpenMaps(){
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @OnClick(R.id.frame_unduh)
    void onClickUnduhProspektus(){
        getProspectus();
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setContentHome();
    }

    @SuppressLint("SetTextI18n")
    private void setContentHome() {
        Bundle bundle=getArguments();
        if(bundle!=null){
            if(bundle.containsKey("id")){
                id = bundle.getString("id");
            }
        }

        getDetailProject(id, false);

        try {
            imageSlider.setCurrentPageListener(this::addBottomDots);
        } catch (Exception e){}

        refreshLayout.setOnRefreshListener(new LiquidRefreshLayout.OnRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {
                getDetailProject(id, true);
            }
        });
    }

    private void getDetailProject(String id, boolean isRefresh) {
        Loading.show(requireActivity());
        ApiCore.apiInterface().getDetailProject(id, PreferenceManager.getSessionToken()).enqueue(new Callback<ResponseDetailProject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseDetailProject> call, Response<ResponseDetailProject> response) {
                Loading.hide(requireActivity());
                if (isRefresh) refreshLayout.finishRefreshing();
                try {
                    if (response.isSuccessful()) {
                        detailProject = response.body();
                        String street = Objects.requireNonNull(detailProject).getAddress().getStreet();
                        String subDistrict = detailProject.getAddress().getSubDistrict();
                        String district = detailProject.getAddress().getDistrict();
                        String city = detailProject.getAddress().getCity() != null ? ", " + detailProject.getAddress().getCity().getName() : "";
                        String province = detailProject.getAddress().getCity() != null ? ", " + detailProject.getAddress().getProvince().getName() : "";
                        latitude = detailProject.getLatitude();
                        longitude = detailProject.getLongitude();

                        tv_start_price.setText("Rp " + MethodUtil.toCurrencyFormat(String.valueOf(Objects.requireNonNull(detailProject).getFundingAmount())));
                        tv_end_price.setText("Rp " + MethodUtil.toCurrencyFormat(String.valueOf(detailProject.getRequestedAmount())));
                        tv_company_name.setText(detailProject.getCompanyName() != null ? detailProject.getCompanyName() : "-");
                        tv_price_per_lot.setText("Rp " + MethodUtil.toCurrencyFormat(String.valueOf(detailProject.getPricePerLot())) + "/lot");
                        tv_total_lot.setText(detailProject.getTotalLot() + " lot");
                        tv_period_deviden.setText(detailProject.getDividenPeriod());
                        tv_estimasi_deviden.setText(detailProject.getInterestRate());
                        tv_desc_usaha.setText(detailProject.getDetail());
                        tv_address.setText(street + ", " + subDistrict + ", " + district + city + province);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateEnd = sdf.parse(detailProject.getDeadlineDate());
                        Date dateStart = new Date();
                        long diff = Objects.requireNonNull(dateEnd).getTime() - dateStart.getTime();
                        System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                        String styledText = "<font color='#428828;'>" + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + "</font> hari lagi";

                        tvCountDay.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);

                        mSliderItems = new ArrayList<>();
                        for (int i = 0; i < detailProject.getPicture().size(); i++) {
                            SliderItem sliderItem = new SliderItem();
                            sliderItem.setDescription("");
                            sliderItem.setImg_url("https://archinect.imgix.net/uploads/p9/p9m1npbrua0zyn97.jpg?fit=crop&auto=compress%2Cformat&w=1200");
                            sliderItem.setImgBase64(detailProject.getPicture().get(i));
                            mSliderItems.add(sliderItem);
                        }

                        imageSlider.setSliderAdapter(new SliderAdapterExample(requireActivity(), mSliderItems));
                        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                        imageSlider.setScrollTimeInSec(4);
                        imageSlider.startAutoCycle();

                        addBottomDots(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailProject> call, Throwable t) {
                if (isRefresh) refreshLayout.finishRefreshing();
                Loading.hide(requireActivity());
                t.printStackTrace();
            }
        });
    }

    private void addBottomDots(int currentPage) {
        FrameLayout[] dots = new FrameLayout[mSliderItems.size()];
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(6, 0, 0, 0);

        dotsLayout.removeAllViews();
        try {
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new FrameLayout(requireActivity());
                dots[i].setBackground(getResources().getDrawable(R.drawable.ellipse_4));
                dots[i].setLayoutParams(params);
                dotsLayout.addView(dots[i]);
            }

            if (dots.length > 0) {
                dots[currentPage].setBackground(getResources().getDrawable(R.drawable.rectangle_4));
            }
        } catch (Exception e){

        }
    }

    private void getProspectus(){
        Loading.show(requireActivity());
        ApiCore.apiInterface().getProspectus(id, PreferenceManager.getSessionToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Loading.hide(requireActivity());
                try {
                    if (response.isSuccessful()){
                        boolean writtenToDisk = writeResponseBodyToDisk(Objects.requireNonNull(response.body()));
                        if (writtenToDisk) {
                            String fileName = "prospectus.pdf";
                            //File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                            java.io.File file = new java.io.File(requireActivity().getExternalFilesDir(null) + File.separator + "project-prospectus.pdf");

//                            Uri fileUri = FileProvider.getUriForFile(requireActivity(), requireActivity().getApplicationContext().getPackageName() + ".provider", file);

                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Snackbar.make(requireActivity().findViewById(android.R.id.content), "No PDF reader found to open this file.", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        MethodUtil.getErrorMessage(response.errorBody(), requireActivity());
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(requireActivity().findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Loading.hide(requireActivity());
                t.printStackTrace();
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(requireActivity().getExternalFilesDir(null) + File.separator + "project-prospectus.pdf");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = new BufferedInputStream(body.byteStream());
//                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
