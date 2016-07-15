package com.gosuncn.core.widget;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * 主要实现进度条的显示
 * @author HWJ
 *
 */
public class ProgressWebChromeClient extends WebChromeClient {

	
	private ProgressBar  progressBar;
	public ProgressWebChromeClient(ProgressBar progressBar){
		this.progressBar=progressBar;
	}
	
	
	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		progressBar.setProgress(newProgress);
		if(newProgress==100){
			progressBar.setVisibility(View.GONE);
		}
		super.onProgressChanged(view, newProgress);
	}
	
	
	

}
