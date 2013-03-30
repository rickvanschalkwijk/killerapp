/*
 * Copyright 2012 Hogeschool van Amsterdam.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.killerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class SplashActivity extends Activity {
	
	protected int _splashTime = 5000;
	private Thread splashThread;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		final SplashActivity sPlashActivity  = this;
		
		splashThread = new Thread(){
			@Override
			public void run(){
				try {
					synchronized (this) {
						wait(_splashTime);
					}
				} catch (InterruptedException e) {
					// TODO: handle exception
				}finally{
					finish();
					Intent intent = new Intent(sPlashActivity, MainActivity.class);
					startActivity(intent);
					stop(); //kill the current activity, user can't get it back with backbutton
				}
			}
		};
		splashThread.start();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			synchronized (splashThread) {
				splashThread.notifyAll();
			}
		}
		return true;
	}
}
