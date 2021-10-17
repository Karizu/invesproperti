package com.selada.invesproperti.presentation.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.UserVerification;
import com.selada.invesproperti.model.response.City;
import com.selada.invesproperti.model.response.FundSources;
import com.selada.invesproperti.model.response.InvestmentGoal;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.anshul.libray.PasswordEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationData3Activity extends AppCompatActivity {

    @BindView(R.id.spinner_hubungan_ahli)
    Spinner spinner_hubungan_ahli;
    @BindView(R.id.spinner_sumber_pendanaan)
    Spinner spinner_fund_source;
    @BindView(R.id.spinner_tujuan_investasi)
    Spinner spinner_investment_goal;
    @BindView(R.id.et_pendapatan_tahunan)
    EditText et_pendapatan_tahunan;
    @BindView(R.id.et_ktp)
    EditText et_ktp;
    @BindView(R.id.et_npwp)
    EditText et_npwp;
    @BindView(R.id.et_ibu_kandung)
    EditText et_ibu_kandung;
    @BindView(R.id.et_ahli)
    EditText et_ahli;
    @BindView(R.id.rb_yes)
    RadioButton rb_yes;
    @BindView(R.id.rb_no)
    RadioButton rb_no;
    @BindView(R.id.etKataSandi)
    PasswordEditText etKataSandi;

    private boolean hasSecuritiesAccount = false;
    private String hubunganAhliSelectedItemId, fundSourceSelectedItemId, investmentGoalSelectedItemId;
    private Context appActivity;
    private List<String> fundSourceIdList, investmentGoalIdList;
    private UserVerification userVerification;

    @OnClick(R.id.rb_yes)
    void onClickYes(){
        hasSecuritiesAccount = true;
        rb_yes.setChecked(true);
        rb_no.setChecked(false);
    }

    @OnClick(R.id.rb_no)
    void onClickNo(){
        hasSecuritiesAccount = false;
        rb_yes.setChecked(false);
        rb_no.setChecked(true);
    }

    @OnClick(R.id.btn_back)
    void onClickBtnBack(){
        onBackPressed();
    }

    @OnClick(R.id.frame_save)
    void onClickSave(){
        //onSave
        PreferenceManager.setIsSaveVerificationData(true);
        setUserVerificationModel();
        MethodUtil.showSnackBar(findViewById(android.R.id.content), "Data berhasil disimpan");
    }

    @OnClick(R.id.btn_lanjut)
    void onClickBtnLanjut(){
        if (TextUtils.isEmpty(et_ahli.getText().toString()) || TextUtils.isEmpty(et_ibu_kandung.getText().toString())
                || TextUtils.isEmpty(et_ktp.getText().toString()) || TextUtils.isEmpty(et_npwp.getText().toString())
                ||  TextUtils.isEmpty(et_pendapatan_tahunan.getText().toString())) {
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Silahkan lengkapi data diri");
            return;
        }

        setUserVerificationModel();

        Intent intent = new Intent(this, VerificationBankActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_data3);
        ButterKnife.bind(this);
        appActivity = this;
        initComponent();
    }

    private void initComponent() {
        userVerification = new UserVerification();
        setCurrency(et_pendapatan_tahunan);
        if (PreferenceManager.getIsSaveVerificationData()){
            userVerification = PreferenceManager.getUserVerification();
            et_pendapatan_tahunan.setText(userVerification.getYearlyIncome()!=null?userVerification.getYearlyIncome():"");
            et_ktp.setText(userVerification.getIdCardNumber()!=null?userVerification.getIdCardNumber():"");
            et_ibu_kandung.setText(userVerification.getMotherName()!=null?userVerification.getMotherName():"");
            et_ahli.setText(userVerification.getHeirName()!=null?userVerification.getHeirName():"");
            if (userVerification.getHasSecuritiesAccount()) {
                rb_yes.setChecked(true);
            } else {
                rb_no.setChecked(false);
            }
        }

        getListSpouseRelation();
        getListFundSource();
        getListInvestmentGoal();

        if (PreferenceManager.getIsUnauthorized()){
            MethodUtil.refreshToken(this);
            initComponent();
        }
    }

    private void setUserVerificationModel() {
        UserVerification userVerification = PreferenceManager.getUserVerification();
        userVerification.setIdCardNumber(et_ktp.getText().toString());
        userVerification.setMotherName(et_ibu_kandung.getText().toString());
        userVerification.setHeirName(et_ahli.getText().toString());
        userVerification.setRelation(hubunganAhliSelectedItemId);
        userVerification.setYearlyIncome(et_pendapatan_tahunan.getText().toString());
        userVerification.setFundSourceId(fundSourceSelectedItemId);
        userVerification.setInvestmentGoalId(investmentGoalSelectedItemId);
        userVerification.setHasSecuritiesAccount(hasSecuritiesAccount);
        userVerification.setPassword(etKataSandi.getText().toString());
        PreferenceManager.setUserVerification(userVerification);
    }

    private void getListSpouseRelation() {
        String[] relation = {"Suami", "Istri", "Anak", "Ayah", "Ibu"};
        ArrayAdapter aa_3 = new ArrayAdapter(this, R.layout.custom_simple_spinner_item, relation){
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    return false;
//                } else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textview = (TextView) view;
//                if (position == 0) {
//                    textview.setTextColor(getResources().getColor(R.color.grey_text));
//                } else {
//                    textview.setTextColor(getResources().getColor(R.color.black_primary));
//                }
                return view;
            }
        };
        aa_3.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_hubungan_ahli.setAdapter(aa_3);
        spinner_hubungan_ahli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hubunganAhliSelectedItemId = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (userVerification.getInvestmentGoalId()!=null) {
            int spinnerPosition = aa_3.getPosition(userVerification.getInvestmentGoalId());
            spinner_hubungan_ahli.setSelection(spinnerPosition);
        }
    }

    private void getListFundSource(){
        Loading.show(this);

        fundSourceIdList = new ArrayList<>();
        List<String> fundSourceList = new ArrayList<>();
        ApiCore.apiInterface().getListFundSource(PreferenceManager.getSessionToken()).enqueue(new Callback<List<FundSources>>() {
            @Override
            public void onResponse(Call<List<FundSources>> call, Response<List<FundSources>> response) {
                Loading.hide(appActivity);
                try {
                    if (response.isSuccessful()){
                        for (int i = 0; i < response.body().size(); i++){
                            FundSources fundSources = response.body().get(i);
                            fundSourceList.add(fundSources.getName());
                            fundSourceIdList.add(fundSources.getId());
                        }

                        ArrayAdapter aa = new ArrayAdapter(appActivity, R.layout.custom_simple_spinner_item, fundSourceList);
                        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                        spinner_fund_source.setAdapter(aa);
                        spinner_fund_source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                fundSourceSelectedItemId = fundSourceIdList.get(i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        if (userVerification.getFundSourceId()!=null) {
                            for (int i = 0; i < fundSourceIdList.size(); i++){
                                if (fundSourceIdList.get(i).equals(userVerification.getFundSourceId())){
                                    spinner_fund_source.setSelection(i);
                                }
                            }
                        }
                    } else {
                        if (response.message().toLowerCase().contains("unauthorized")){
                            PreferenceManager.setIsUnauthorized(true);
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<List<FundSources>> call, Throwable t) {
                t.printStackTrace();
                Loading.hide(appActivity);
            }
        });
    }

    private void getListInvestmentGoal(){
        Loading.show(this);

        investmentGoalIdList = new ArrayList<>();
        List<String> investmentGoalList = new ArrayList<>();
        ApiCore.apiInterface().getListInvestmenGoal(PreferenceManager.getSessionToken()).enqueue(new Callback<List<InvestmentGoal>>() {
            @Override
            public void onResponse(Call<List<InvestmentGoal>> call, Response<List<InvestmentGoal>> response) {
                Loading.hide(appActivity);
                try {
                    if (response.isSuccessful()){
                        for (int i = 0; i < response.body().size(); i++){
                            InvestmentGoal investmentGoal = response.body().get(i);
                            investmentGoalList.add(investmentGoal.getName());
                            investmentGoalIdList.add(investmentGoal.getId());
                        }

                        ArrayAdapter aa = new ArrayAdapter(appActivity, R.layout.custom_simple_spinner_item, investmentGoalList);
                        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                        spinner_investment_goal.setAdapter(aa);
                        spinner_investment_goal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                investmentGoalSelectedItemId = investmentGoalIdList.get(i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        if (userVerification.getInvestmentGoalId() != null) {
                            for (int i = 0; i < investmentGoalIdList.size(); i++){
                                if (investmentGoalIdList.get(i).equals(userVerification.getInvestmentGoalId())){
                                    spinner_investment_goal.setSelection(i);
                                }
                            }
                        }
                    } else {
                        if (response.message().toLowerCase().contains("unauthorized")){
                            PreferenceManager.setIsUnauthorized(true);
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<List<InvestmentGoal>> call, Throwable t) {
                t.printStackTrace();
                Loading.hide(appActivity);
            }
        });
    }

    private void setCurrency(final EditText edt) {
        edt.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    edt.removeTextChangedListener(this);

                    Locale local = new Locale("id", "id");
                    String replaceable = String.format("[Rp,.\\s]",
                            NumberFormat.getCurrencyInstance().getCurrency()
                                    .getSymbol(local));
                    String cleanString = s.toString().replaceAll(replaceable,
                            "");

                    double parsed;
                    try {
                        parsed = Double.parseDouble(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0.00;
                    }

                    NumberFormat formatter = NumberFormat
                            .getCurrencyInstance(local);
                    formatter.setMaximumFractionDigits(0);
                    formatter.setParseIntegerOnly(true);
                    String formatted = formatter.format((parsed));

                    String replace = String.format("[Rp\\s]",
                            NumberFormat.getCurrencyInstance().getCurrency()
                                    .getSymbol(local));
                    String clean = formatted.replaceAll(replace, "");

                    current = formatted;
                    edt.setText(clean);
                    edt.setSelection(clean.length());
                    edt.addTextChangedListener(this);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}