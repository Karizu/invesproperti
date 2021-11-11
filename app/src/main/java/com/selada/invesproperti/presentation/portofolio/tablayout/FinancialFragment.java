package com.selada.invesproperti.presentation.portofolio.tablayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.selada.invesproperti.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ithebk.barchart.BarChart;
import me.ithebk.barchart.BarChartModel;

public class FinancialFragment extends Fragment {
    @BindView(R.id.bar_chart_vertical)
    BarChart barChart;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_financial, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setContentHome();
    }

    private void setContentHome() {
        barChart.setBarMaxValue(100);
        List<BarChartModel> barChartModelList = new ArrayList<>();
        BarChartModel barChartModel = new BarChartModel();
        barChartModel.setBarValue(50);
        barChartModel.setBarColor(getResources().getColor(R.color.green_primary));
        barChartModel.setBarTag(null); //You can set your own tag to bar model
        barChartModel.setBarText("50");
        barChartModelList.add(barChartModel);

        barChartModel = new BarChartModel();
        barChartModel.setBarValue(70);
        barChartModel.setBarColor(getResources().getColor(R.color.green_primary));
        barChartModel.setBarTag(null); //You can set your own tag to bar model
        barChartModel.setBarText("70");
        barChartModelList.add(barChartModel);

        barChartModel = new BarChartModel();
        barChartModel.setBarValue(35);
        barChartModel.setBarColor(getResources().getColor(R.color.green_primary));
        barChartModel.setBarTag(null); //You can set your own tag to bar model
        barChartModel.setBarText("35");
        barChartModelList.add(barChartModel);

        barChartModel = new BarChartModel();
        barChartModel.setBarValue(95);
        barChartModel.setBarColor(getResources().getColor(R.color.green_primary));
        barChartModel.setBarTag(null); //You can set your own tag to bar model
        barChartModel.setBarText("95");
        barChartModelList.add(barChartModel);

        barChartModel = new BarChartModel();
        barChartModel.setBarValue(40);
        barChartModel.setBarColor(getResources().getColor(R.color.green_primary));
        barChartModel.setBarTag(null); //You can set your own tag to bar model
        barChartModel.setBarText("40");
        barChartModelList.add(barChartModel);

        barChart.addBar(barChartModelList);
    }
}
