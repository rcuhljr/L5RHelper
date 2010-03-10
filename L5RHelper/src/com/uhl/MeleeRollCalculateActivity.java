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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MeleeRollCalculateActivity extends Activity implements OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        dbHelper = DBServiceLocator.getDBHelper(this);
        profile = dbHelper.loadProfile(getIntent().getExtras().getInt("ID"));
        setContentView(R.layout.melee_roll_main_screen);
        Setup();        
        RegisterButtons();   
        configureTemplates();
    }

	private IDBHelper dbHelper;
    private Profile profile;
    private Roll roll;
    private int staticMod = 0;
    private int tntbh;
    private int confidence;
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
		int agi = profile.getAgility();
		int ref = profile.getReflexes();
		int mod = staticMod;
		int usegp = 0;
		int useref  = 0;
		int skillRanks = 0;
		int rolled = 0;
		int kept = 0;
		
		for (Template t : activeTemplates){			
			agi += t.getAgility();
			ref +=t.getReflexes();
			mod += t.getModifier();
			usegp += t.getisGp();
			useref += t.getUseReflexes();
			skillRanks += t.getSkillRank();
			rolled += t.getRolled();
			kept += t.getKept();
		}
		int attackTrait = agi;
		if(useref >= 1){
			attackTrait = ref;
		}
		if(attackTrait <= 0 || (attackTrait + kept) <= 0){
			roll = new Roll(0,0,0,0,0);
			SetRollText();
			return;
		}
		usegp = usegp > 0 ? 1: 0;
		skillRanks = skillRanks > 10 ? 10: skillRanks;
		
		
		roll = new Roll(attackTrait+skillRanks+rolled, attackTrait+kept, mod, 0, usegp);
		SetRollText();
	}

	private void RegisterButtons() {
		(this.<Button>GetView(R.id.calculate)).setOnClickListener(this);
	}    
	
	private void SetupResultDialog(int raises){
		String message;
		if(raises >= 0){
			message = "Assuming a target with TN:"+tntbh+" You can hit "+raises+" raises "+confidence+"% of the time.";
		}else{
			message = "Assuming a target with TN:"+tntbh+" You can't hit it with "+confidence+"% confidence.";
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
    	roll = new Roll(profile.getAgility(), profile.getAgility(), 0, 0, 0);
		SetRollText();
		if(profile.getLuck() == 0){
			this.<CheckBox>GetView(R.id.use_luck).setVisibility(View.INVISIBLE);
		}
				
		SetupTextListeners();
		
		tntbh = Integer.parseInt(this.<EditText>GetView(R.id.tn_box).getText().toString());
		confidence = Integer.parseInt(this.<EditText>GetView(R.id.confidence_box).getText().toString());		
			
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
		TextWatcher confidenceMonitor = new TextWatcher(){
			public void afterTextChanged(Editable s) {
				int newValue;
				try{
					newValue = Integer.parseInt(s.toString());
					if(newValue >= 1 && newValue <= 100){
						validationMonitor(0, true);
						confidence = newValue;
						return;
					}
					
				}catch(NumberFormatException e){
					
				}				
				validationMonitor(0, false);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
		};
		
		TextWatcher tntbhMonitor = new TextWatcher(){
			public void afterTextChanged(Editable s) {
				int newValue;
				try{
					newValue = Integer.parseInt(s.toString());
					if(newValue >= 0){
						validationMonitor(1, true);
						tntbh = newValue;
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
		this.<EditText>GetView(R.id.confidence_box).addTextChangedListener(confidenceMonitor);
		this.<EditText>GetView(R.id.tn_box).addTextChangedListener(tntbhMonitor);
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
		
		SetupResultDialog(Raises.calculateRaises(this, tntbh, roll, confidence));		
		AlertDialog result = resultBuilder.create();
		
		result.show();
		
	}
	
	

}
