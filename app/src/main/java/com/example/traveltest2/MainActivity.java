package com.example.traveltest2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import in.slanglabs.assistants.travel.AssistantConfiguration;
import in.slanglabs.assistants.travel.AssistantError;
import in.slanglabs.assistants.travel.AssistantSubDomain;
import in.slanglabs.assistants.travel.NavigationInfo;
import in.slanglabs.assistants.travel.NavigationUserJourney;
import in.slanglabs.assistants.travel.SearchInfo;
import in.slanglabs.assistants.travel.SearchUserJourney;
import in.slanglabs.assistants.travel.SlangTravelAssistant;

public class MainActivity extends AppCompatActivity implements SlangTravelAssistant.Action, SlangTravelAssistant.LifecycleObserver {

    String TAG = "TestTravel";

    Button button;
    TextView textView;
    TextView subdomainTextView;

    boolean inFlightMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AssistantConfiguration configuration = new AssistantConfiguration.Builder()
                .setAPIKey("a86ef9ca481a48a88ba2c5bffb05a91c")
                .setAssistantId("2357c7a185604e349a77a57c60090b51")
                .setEnvironment(SlangTravelAssistant.Environment.STAGING)  // Change this to PRODUCTION once you've published the Assistant to production environment
                .build();
        SlangTravelAssistant.initialize(this, configuration);

        SlangTravelAssistant.setAction(this);
        button = findViewById(R.id.button);
        subdomainTextView = findViewById(R.id.subdomainTextView);
        textView = findViewById(R.id.textView);
        try {
            subdomainTextView.setText("Default subdomain = "+SlangTravelAssistant.getAppDefaultSubDomain().toString());
        }catch (Exception e){
            subdomainTextView.setText("Default subdomain = null");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inFlightMode) {
                    SlangTravelAssistant.setAppDefaultSubDomain(AssistantSubDomain.FLIGHT);
                    inFlightMode = true;
                }else{
                    SlangTravelAssistant.setAppDefaultSubDomain(AssistantSubDomain.TRAIN);
                    inFlightMode = false;
                }
                subdomainTextView.setText("Default subdomain = "+SlangTravelAssistant.getAppDefaultSubDomain().toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SlangTravelAssistant.getUI().showTrigger(this);
    }

    @Override
    public void onAssistantInitSuccess() {

    }

    @Override
    public void onAssistantInitFailure(String s) {

    }

    @Override
    public void onAssistantInvoked() {

    }

    @Override
    public void onAssistantClosed(boolean b) {

    }

    @Override
    public void onAssistantLocaleChanged(Locale locale) {

    }

    @Override
    public boolean onUnrecognisedUtterance(String s) {
        return false;
    }

    @Override
    public void onUtteranceDetected(String s) {

    }

    @Override
    public void onOnboardingSuccess() {

    }

    @Override
    public void onOnboardingFailure() {

    }

    @Override
    public void onMicPermissionDenied() {

    }

    @Override
    public SearchUserJourney.AppState onSearch(SearchInfo searchInfo, SearchUserJourney searchUserJourney) {
        textView.setText(searchInfo.toJSONString());
        searchUserJourney.setSuccess();
        return SearchUserJourney.AppState.SEARCH_RESULTS;
    }

    @Override
    public NavigationUserJourney.AppState onNavigation(NavigationInfo navigationInfo, NavigationUserJourney navigationUserJourney) {
        textView.setText(navigationInfo.toJSONString());
        navigationUserJourney.setNavigationSuccess();
        return NavigationUserJourney.AppState.NAVIGATION;
    }

    @Override
    public void onAssistantError(AssistantError assistantError) {
        textView.setText(assistantError.toString());
    }
}