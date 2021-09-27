package com.selada.invesproperti.presentation.portofolio.tablayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.selada.invesproperti.R;
import com.selada.invesproperti.model.SliderItem;
import com.selada.invesproperti.presentation.adapter.SliderAdapterExample;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoFragment extends Fragment {
    @BindView(R.id.imageSlider)
    SliderView imageSlider;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;
    @BindView(R.id.tv_count_day)
    TextView tvCountDay;

    private List<SliderItem> mSliderItems;

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
        String day = "44";
        String styledText = "<font color='#428828;'>"+ day +"</font> hari lagi";
        tvCountDay.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);

        mSliderItems = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("");
            sliderItem.setImg_url("https://archinect.imgix.net/uploads/p9/p9m1npbrua0zyn97.jpg?fit=crop&auto=compress%2Cformat&w=1200");
            mSliderItems.add(sliderItem);
        }

        imageSlider.setSliderAdapter(new SliderAdapterExample(requireActivity(), mSliderItems));
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        try {
            imageSlider.setCurrentPageListener(this::addBottomDots);
        } catch (Exception e){}
        imageSlider.setScrollTimeInSec(4);
        imageSlider.startAutoCycle();

        addBottomDots(0);
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
            e.printStackTrace();
        }
    }
}
