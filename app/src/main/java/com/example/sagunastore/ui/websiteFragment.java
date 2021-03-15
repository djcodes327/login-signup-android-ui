package com.example.sagunastore.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sagunastore.R;


public class websiteFragment extends Fragment {

    WebView webView;

    public websiteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_website, container, false);
        getActivity().setTitle("Website");
        // Inflate the layout for this fragment
        webView = view.findViewById(R.id.websiteView);

        webView.loadUrl("https://www.sagunastore.com");
        // this will enable the javascipt.
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        return view;

    }
}
