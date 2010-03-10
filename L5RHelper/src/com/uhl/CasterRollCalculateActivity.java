package com.uhl;

import java.util.ArrayList;
import java.util.Hashtable;

import com.uhl.calc.Raises;
import com.uhl.calc.Roll;
import com.uhl.db.DBServiceLocator;
import com.uhl.db.IDBHelper;
import com.uhl.db.Profile;
import com.uhl.db.Template;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CasterRollCalculateActivity extends Activity implements OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        dbHelper = DBServiceLocator.getDBHelper(this);
        profile = dbHelper.loadProfile(getIntent().getExtras().getInt("ID"));
        setContentView(R.layout.caster_roll_main_screen);
        Setup();        
        RegisterButtons();   
        configureTemplates();
    }

	private IDBHelper dbHelper;
    private Profile profile;
    private Roll roll;
    private int staticMod = 0;
    private int raises;
    private int range;
    private boolean[] validation = {true, true};
    private AlertDialog.Builder resultBuilder;
    private Hashtable<String, Template> templates;
    private ArrayList<Template> activeTemplates;
    

	private void configureTemplates() {
		
		templates = new Hashtable<String, Template>();
		activeTemplates = new ArrayList<Template>();
		Cursor cursor = dbHelper.getTemplates(profile.getId());		
		if(cursor.getCount() < 1){
			cursor.close();
			return;
		}
		do{
			Template aTemplate = new Template(cursor);
			templates.put(aTemplate.getName(), aTemplate);
		}while(cursor.moveToNext());
		cursor.close();
		
		String[] templateNames = new String[templates.size()];
		templates.keySet().toArray(templateNames);
		
		ListView lv = this.<ListView>GetView(R.id.template_view);
		
		ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.selectable_list_item, templateNames);
		
		lv.setAdapter(adapter);
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		    {	
		    	toggleTemplate(templates.get(((CheckedTextView)view).getText().toString()));
	    	}
		  	});
		
	}

	protected void toggleTemplate(Template template) {
		if(activeTemplates.contains(template)){
			activeTemplates.remove(template);
			calculateTemplates();
			return;
		}
		activeTemplates.add(template);
		calculateTemplates();
	}

	private void calculateTemplates() {
		int castingStat = 0;
		int mod = staticMod;		
		int rolled = 0;
		int kept = 0;
		
		for (Template t : activeTemplates){
			int casting = t.getCastingRing();
			switch(casting){
				case 0:break;
				case 1:castingStat = profile.getEarthRing(); break;
				case 2:castingStat = profile.getWaterRing();break;
				case 3:castingStat = profile.getFireRing();break;
				case 4:castingStat = profile.getAirRing();break;
				case 5:castingStat = profile.getVoidRing();break;
				default:break;
			}
			
			mod += t.getModifier();			
			rolled += t.getRolled();
			kept += t.getKept();
		}		
		if(castingStat <= 0 || (castingStat + kept) <= 0){
			roll = new Roll(0,0,0,0,0);
			SetRollText();
			return;
		}
		
		roll = new Roll(castingStat+rolled, castingStat+kept, mod, 0, 0);
		SetRollText();
	}

	private void RegisterButtons() {
		(this.<Button>GetView(R.id.calculate)).setOnClickListener(this);
	}    
	
	private void SetupResultDialog(int raises, double[] probs, int tn){
		String message = "Chance to succed";
		
		for(int i = 0; i < (range*2)+1; i++){
			if(raises-range+i >= 0){
				message += "\nraises:"+(raises-range+i)+" %:"+Math.round(probs[i]);
			}			
		}
		
		resultBuilder = new AlertDialog.Builder(this);		
		resultBuilder.setMessage(message)
	       .setCancelable(false)
	       .setPositiveButton("Done", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {	      		 
	           }
	       });	       
	}
	
    private void Setup() {	
    	roll = new Roll(0, 0, 0, 0, 0);
		SetRollText();
		if(profile.getLuck() == 0){
			this.<CheckBox>GetView(R.id.use_luck).setVisibility(View.INVISIBLE);
		}
				
		SetupTextListeners();
		
		raises = Integer.parseInt(this.<EditText>GetView(R.id.raises_box).getText().toString());
		range = Integer.parseInt(this.<EditText>GetView(R.id.range_box).getText().toString());		
			
	}

    

	private void SetupTextListeners() {
		TextWatcher mods = new TextWatcher(){
			public void afterTextChanged(Editable s) {
				int newValue;
				try{
					newValue = Integer.parseInt(s.toString());
					
				}catch(NumberFormatException e){
					newValue = 0;					
				}				
				roll.setStatMod(roll.getStatModRaw()-staticMod+newValue);
				staticMod = newValue;	
				SetRollText();
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
		};
		TextWatcher rangeMonitor = new TextWatcher(){
			public void afterTextChanged(Editable s) {
				int newValue;
				try{
					newValue = Integer.parseInt(s.toString());
					if(newValue >= 0 && newValue <= 3){
						validationMonitor(0, true);
						range = newValue;
						return;
					}
					
				}catch(NumberFormatException e){
					
				}				
				validationMonitor(0, false);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
		};
		
		TextWatcher raisesMonitor = new TextWatcher(){
			public void afterTextChanged(Editable s) {
				int newValue;
				try{
					newValue = Integer.parseInt(s.toString());
					if((10 >= newValue) && (newValue >= 0)){
						validationMonitor(1, true);
						raises = newValue;
						return;
					}
					
				}catch(NumberFormatException e){
					
				}				
				validationMonitor(1, false);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
		};
		
		
		this.<EditText>GetView(R.id.misc_mods).addTextChangedListener(mods);
		this.<EditText>GetView(R.id.range_box).addTextChangedListener(rangeMonitor);
		this.<EditText>GetView(R.id.raises_box).addTextChangedListener(raisesMonitor);
	}
	
	private void validationMonitor(int field, boolean valid){
		validation[field] = valid;
		boolean result = true;
		for(int i = 0; i < validation.length; i++){
			result &= validation[i];
		}
		this.<Button>GetView(R.id.calculate).setEnabled(result);
	}

	private void SetRollText() {
		this.<TextView>GetView(R.id.roll_text).setText(roll.toString());
	}
    
	@SuppressWarnings("unchecked")
	public <T extends View> T GetView(int id) {
		T result = null;
		try{			
			result = (T)findViewById(id);}
		catch(Exception e){			
		}
		return result;
	}

	@Override
	public void onClick(View e) {
		Button button = this.<Button>GetView(e.getId());		
		switch(button.getId()){
			case R.id.calculate:Calculate();break;
			default: break;
		}
	}

	private void Calculate() {		
		
		int luck = this.<CheckBox>GetView(R.id.use_luck).isChecked()? 1 : 0;
		roll.setLuck(luck);	
		
		int tn = (this.<Spinner>GetView(R.id.spell_rank_spin).getSelectedItemPosition()+1)*5+10+5*raises;
		
		SetupResultDialog(raises, Raises.CalculateRange(this, roll, range, tn), tn);		
		AlertDialog result = resultBuilder.create();
		
		result.show();
		
	}
	
	

}
