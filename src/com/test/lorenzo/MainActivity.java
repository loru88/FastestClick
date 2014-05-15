package com.test.lorenzo;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.os.CountDownTimer;
import android.os.Handler;

public class MainActivity extends Activity
{

private Button game_button;
private TextView stats_view;
private TextView count_click;
private TextView best_click_view;
private TextView messages_view;

private TextView debug;

private Handler mHandler = new Handler();

int click_counter = 0; //count how many times has been pressed the game button in the current second
boolean continue_active = false;
boolean active = false; //true if we are counting click
boolean reset = false;
int totalClicks = 0; //count how many times has been pressed the game button from the beginning
int totalSeconds = 0; //count the number of ticks, therefore the seconds passed to click the button
int rate = 0;
int best_clicks = 0; //the highest number of clicks in a given second
float average_speed_click = 0; //the average speed of clicks from the beginning
String[] messages_array;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        game_button = (Button)findViewById(R.id.game_button);
        stats_view = (TextView)findViewById(R.id.stats);
        count_click = (TextView)findViewById(R.id.current_clicks);
        
        best_click_view = (TextView)findViewById(R.id.best_clicks);
        
        //debug = (TextView)findViewById(R.id.debug);
        
        messages_array = getResources().getStringArray(R.array.messages);
        messages_view = (TextView)findViewById(R.id.messages);
        messages_view.setText(messages_array[0]);
        
    }
    
    public void countClick(View view){
    	click_counter++;
    	count_click.setText( getString(R.string.number_of_clicks) + click_counter);
    	
    	continue_active = true;
    	if( !active ){
	    	active = true;
	    	mHandler.removeCallbacks(onTick);
	    	mHandler.postDelayed(onTick, 1000);
	    	//debug.setText("continue_active: "+continue_active+" active: "+active);
	    		
    	}
    	game_button.setText( getString(R.string.keep_going) );
    	//debug.setText("continue_active: "+continue_active+" active: "+active);
    }
    
    private Runnable onTick = new Runnable(){
    	public void run(){
    	if( reset ){
	    		ResetAll();
	    		reset = false;
	    		
    		}
    		//debug.setText("continue_active: "+continue_active+" active: "+active);
    		if( continue_active ){
    			if(click_counter != 0){
	    			totalSeconds++;
	    			totalClicks += click_counter;
	    			
    			}
    			rate = click_counter;
    		}
    		
    		if(click_counter > best_clicks)
    			best_clicks = click_counter;
    		
    		if( totalSeconds != 0 )
    			average_speed_click = (float)totalClicks / (float)totalSeconds;
    			
    		String stats_text = String.format( getResources().getString(R.string.average_text), average_speed_click, totalSeconds);
    		 stats_view.setText( stats_text );
    		 
    		 switch( (int)average_speed_click){
    		 	case 1: case 2:
    		 	case 3: messages_view.setText(messages_array[1]); break;
    		 	case 4: messages_view.setText(messages_array[2]); break;
    		 	case 5: messages_view.setText(messages_array[3]); break;
    		 	case 6: messages_view.setText(messages_array[4]); break;
    		 	case 7: messages_view.setText(messages_array[5]); break;
    		 	case 8: messages_view.setText(messages_array[6]); break;
    		 	case 9: messages_view.setText(messages_array[7]); break;
    		 	case 10: messages_view.setText(messages_array[8]); break;
    		 	case 11: messages_view.setText(messages_array[9]); break;
    		 	case 12: messages_view.setText(messages_array[10]); break;
    		 	case 13: messages_view.setText(messages_array[11]); break;
    		 
    		 }
    		 
    		 count_click.setText( getString(R.string.number_of_clicks) + click_counter);
    		 best_click_view.setText( getString(R.string.best_click_text) + best_clicks);
    		 
    		 
    		 if( active && continue_active){
    		 	count_click.setText( getString(R.string.number_of_clicks) + click_counter);
    		 	click_counter=0; //reset the number of click in the current second
    		 	
	    		 
    		 }else{
    		 	count_click.setText(getString(R.string.number_of_clicks)+" - ");
    		 }
    		 
    		 if(continue_active){
	    		 continue_active=false;
	    		 mHandler.removeCallbacks(onTick);
		    	 mHandler.postDelayed(onTick, 1000);	    		 
    		 }else if(active){
    		 	active=false;
    		 	if(rate < average_speed_click){
	    		 	totalClicks -= rate;
	    		 	totalSeconds--;
	    		 	onTick.run();
    		 	}
    		 	game_button.setText( getString(R.string.game_button_text) );
    		 }
    		 
    }
    };
    
    
    public void ResetAll(){
    	active = false;
    	continue_active = false;
    	totalClicks = 0;
    	totalSeconds = 0;
    	rate = 0;
    }
    
    
}
