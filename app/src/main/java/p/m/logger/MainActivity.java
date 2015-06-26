package p.m.logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	FileOutputStream outputStream;
	String string = "Hello world!";
	String filename = "KMLOG.txt";
	int type = 0;
	Spinner typeSpinner;
	CheckBox offshore;
	TextView description;
	OutputStreamWriter out;
	File file;
	Button p1_button;
	Button select_job;
	Button newFilebut;
	private static final String TAG = "MEDIA";
	private TextView tv;
	boolean newFile = false;
	File fileFromSD;
	


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        typeSpinner = (Spinner) findViewById(R.id.spinner1);
        p1_button = (Button)findViewById(R.id.button1);
        select_job = (Button)findViewById(R.id.button2);
        newFilebut = (Button)findViewById(R.id.button3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        file = new File(this.getFilesDir(), filename);
        description = (TextView)findViewById(R.id.editText1);
        offshore = (CheckBox)findViewById(R.id.checkBox1);
        //check for status on resume
        File sdcard = Environment.getExternalStorageDirectory();

        //Get the text file
        fileFromSD = new File(sdcard,"/Notes/KMLOG.txt");
		description.setHint("Description");
		description.setHintTextColor(-16777216);
        
        //tv = (TextView) findViewById(R.id.textView1);
        //Check for status when destroyed
        checkStatus();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //Add onResume
    
    public void createReport(View view)
    {
    	startActivity(new Intent(getApplicationContext(), Output.class));
    	
    }
   
    public void checkStatus()
    {
    	
    	int change = 0;
    	Boolean offShore = false;
    	StringBuilder text = new StringBuilder();
        try {
        	FileReader reader = new FileReader(fileFromSD);
            BufferedReader br = new BufferedReader(reader);
            String line;
            
            while ((line = br.readLine()) != null) {
            	text.append(line);
                text.append('\n');
            }
            
            br.close();
            String[] strary = text.toString().split("\n");
            String[] time1 = null;
            String[] time2 = null;
            String tempVar = "";
            int i = 0;
            
            //i = strary.length-1;
            time1 = strary[strary.length-1].split(" ");
            if (time1[1].equals("Work") && time1[0].equals("Start"))
            {
            	change = 1;
            }
            if (time1[1].equals("Waiting") && time1[0].equals("Start"))
            {
            	change = 2;
            }
            if (time1[1].equals("Travel") && time1[0].equals("Start"))
            {
            	change = 3;
            }
            if (time1.length > 4 && time1[5].equals("Off"))
            {
            	offShore = true;
            }
            
        }
            catch (IOException e) {
                //error handling
            }
        //add logic for handling description
        switch (change)
        {
        case 1:
        	if (change == 1)
        	{
	        	typeSpinner.setSelection(0);
	        	typeSpinner.setEnabled(false);
	    		p1_button.setText(getResources().getString(R.string.stop));
	    		p1_button.setBackgroundColor(Color.RED);
	    		select_job.setEnabled(false);
	    		newFilebut.setEnabled(false);
	    		description.setText("");
	    		if(offShore == true){offshore.setChecked(true);}
	    		offshore.setEnabled(false);
        	}
        case 2:
        	if (change == 2)
        	{
	        	typeSpinner.setSelection(1);
	        	typeSpinner.setEnabled(false);
	    		p1_button.setText(getResources().getString(R.string.stop));
	    		p1_button.setBackgroundColor(Color.RED);
	    		select_job.setEnabled(false);
	    		newFilebut.setEnabled(false);
	    		description.setText("");
	    		if(offShore == true){offshore.setChecked(true);}
	    		offshore.setEnabled(false);
        	}
        case 3:
        	if (change == 3)
        	{
	        	typeSpinner.setSelection(2);
	        	typeSpinner.setEnabled(false);
	    		p1_button.setText(getResources().getString(R.string.stop));
	    		p1_button.setBackgroundColor(Color.RED);
	    		select_job.setEnabled(false);
	    		newFilebut.setEnabled(false);
	    		description.setText("");
	    		if(offShore == true){offshore.setChecked(true);}
	    		offshore.setEnabled(false);
        	}
        	
        }
        	
    }
    //on click, Start/Stop
    public void startStop(View view)
    {
    	
    	String tempString = p1_button.getText().toString();
    	if(tempString.contentEquals("Start"))
    	{
    		String tempstring;
    		//write time
    		if(typeSpinner.getSelectedItem().toString().contentEquals("Work"))
    		{
    			if(offshore.isChecked())
    			{
    				tempstring = "Start Work" + DateFormat.format(" MM-dd kk:mm", 
    						new java.util.Date()).toString() + "  Off Shore " + description.getText().toString() + "\n";
        			generateNoteOnSD(filename, tempstring);
        			
    			}
    			else
    			{
	    			tempstring = "Start Work" + DateFormat.format(" MM-dd kk:mm", 
	    					new java.util.Date()).toString() + "  On Shore " + description.getText().toString() + "\n";
	    			generateNoteOnSD(filename, tempstring);
    			}
    			
    			
    		}
    		if(typeSpinner.getSelectedItem().toString().contentEquals("Waiting"))
    		{
    			if(offshore.isChecked())
    			{
    				tempstring = "Start Waiting" + DateFormat.format(" MM-dd kk:mm", 
    						new java.util.Date()).toString() + "  Off Shore " + description.getText().toString() + "\n";
        			generateNoteOnSD(filename, tempstring);
        			
    			}
    			else
    			{
	    			tempstring = "Start Waiting" + DateFormat.format(" MM-dd kk:mm", 
	    					new java.util.Date()).toString() + "  On Shore " + description.getText().toString() + "\n";
	    			generateNoteOnSD(filename, tempstring);
    			}
//    			tempstring = "Start Waiting" + DateFormat.format(" MM-dd kk:mm", new java.util.Date()).toString() + "\n";
//    			generateNoteOnSD(filename, tempstring);
    			
    		}
    		if(typeSpinner.getSelectedItem().toString().contentEquals("Travel"))
    		{
    			if(offshore.isChecked())
    			{
    				tempstring = "Start Travel" + DateFormat.format(" MM-dd kk:mm", 
    						new java.util.Date()).toString() + "  Off Shore " + description.getText().toString() + "\n";
        			generateNoteOnSD(filename, tempstring);
        			
    			}
    			else
    			{
	    			tempstring = "Start Travel" + DateFormat.format(" MM-dd kk:mm", 
	    					new java.util.Date()).toString() + "  On Shore " + description.getText().toString() + "\n";
	    			generateNoteOnSD(filename, tempstring);
    			}
//    			tempstring = "Start Travel" + DateFormat.format(" MM-dd kk:mm", new java.util.Date()).toString() + "\n";
//    			generateNoteOnSD(filename, tempstring);
    			
    		}
    		//writeTime("work");
    		typeSpinner.setEnabled(false);
    		p1_button.setText(getResources().getString(R.string.stop));
    		p1_button.setBackgroundColor(Color.RED);
    		select_job.setEnabled(false);
    		newFilebut.setEnabled(false);
			offshore.setEnabled(false);
    		description.setText("");
    	}
    	else if(tempString.contentEquals("Stop"))
    	{
    		String tempstring;
    		//write time
    		if(typeSpinner.getSelectedItem().toString().contentEquals("Work"))
    		{
    			tempstring = "Stop Work" + DateFormat.format(" MM-dd kk:mm", new java.util.Date()).toString() + "\n";
    			generateNoteOnSD(filename, tempstring);
    			//writeTime(tempstring);
    			//readTime();
    		}
    		if(typeSpinner.getSelectedItem().toString().contentEquals("Waiting"))
    		{
    			tempstring = "Stop Waiting" + DateFormat.format(" MM-dd kk:mm", new java.util.Date()).toString() + "\n";
    			generateNoteOnSD(filename, tempstring);
    			//writeTime(tempstring);
    		}
    		if(typeSpinner.getSelectedItem().toString().contentEquals("Travel"))
    		{
    			tempstring = "Stop Travel" + DateFormat.format(" MM-dd kk:mm", new java.util.Date()).toString() + "\n";
    			generateNoteOnSD(filename, tempstring);
    			//writeTime(tempstring);
    		}
    		//write time
    		typeSpinner.setEnabled(true);
    		p1_button.setText(getResources().getString(R.string.start));
    		p1_button.setBackgroundColor(Color.GREEN);
    		select_job.setEnabled(true);
    		newFilebut.setEnabled(true);
    		description.setText("");
    		offshore.setEnabled(true);
    	}
    }
    
    public void writeTime(String timeType)
    {
    	try {
    		out=new OutputStreamWriter(openFileOutput(filename, MODE_APPEND));
    		out.write(timeType);
    		out.close();
    		} catch (Exception e) {
    		  e.printStackTrace();
    		}
    	
    }
    
    public void generateNoteOnSD(String sFileName, String sBody){
        try
        {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(sBody);
            writer.flush();
            writer.close();
            if (newFile == true)
            {
	            Toast toast = Toast.makeText(this,"File Created", Toast.LENGTH_LONG);
	            toast.setGravity(Gravity.CENTER, 0, 0);
	            toast.show();
            }
            else
            {
            	Toast toast = Toast.makeText(this,"Saved", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
        catch(IOException e)
        {
             e.printStackTrace();
             //importError = e.getMessage();
             //iError();
        }
       }  
    
    public void createNewJob(View view)
    {
    	new AlertDialog.Builder(this)
        .setTitle("Create New File")
        .setMessage("Are you sure you want to create a new file?  This will delete the existing file.")
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
            	File sdcard = Environment.getExternalStorageDirectory();

                //Get the text file
                File file = new File(sdcard,"/Notes/KMLOG.txt");
            	boolean deleted = file.delete();
            	newFile = true;
            	generateNoteOnSD(filename, "Test Job Laney Chouest\n");
            	newFile = false;
            }
         })
        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                // do nothing
            }
         })
        .setIcon(android.R.drawable.ic_dialog_alert)
         .show();
    }
    
    public void readTime()
    {
    	 try {
             InputStream inputStream = openFileInput(filename);
             p1_button.setText(inputStream.toString());
             
    	 }
    	 catch (FileNotFoundException e) {
    	 
    	 }
    	
    }
   
    
}
