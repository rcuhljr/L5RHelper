package com.uhl;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import com.uhl.db.DBHelper;
import com.uhl.db.DefaultViews;
import com.uhl.db.Profile;
import com.uhl.db.Template;

public class EditTemplateActivity extends Activity implements OnClickListener, OnItemSelectedListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_template);
        dbHelper = new DBHelper(this);
        Bundle info = getIntent().getExtras();
        int id = info.getInt("ID");
        int profileId = info.getInt("PROFILE_ID");
        if(id != -1)
        {
        	template = dbHelper.loadTemplate(id);        	
        }else{
        	template = new Template(profileId);
        	this.<Button>GetView(R.id.delete_template).setVisibility(View.INVISIBLE);
        }
        profile = dbHelper.loadProfile(profileId);	
        
        isMelee = profile.getDefaultViewId() == DefaultViews.melee.getId();
    	
        errorLabel = this.<TextView>GetView(R.id.error_message);
        traitLabel = this.<TextView>GetView(R.id.spin_trait_label);
        traitSpinner = this.<Spinner>GetView(R.id.spin_trait);
        
    	configName();                
        RegisterButtons();        
        setupBoxes();
    }
           
	private void setupBoxes() {
		this.<EditText>GetView(R.id.template_name_box).setText(template.getName());
		this.<EditText>GetView(R.id.modifier_textbox).setText(String.valueOf(template.getModifier()));
		this.<EditText>GetView(R.id.rolled_textbox).setText(String.valueOf(template.getRolled()));
		this.<EditText>GetView(R.id.kept_textbox).setText(String.valueOf(template.getKept()));
		
		if(isMelee){
			setSpinner(R.id.spin_agi, template.getAgility()+2);
			setSpinner(R.id.spin_ref, template.getReflexes()+2);
			setSpinner(R.id.spin_skills, template.getSkillRank());
			setSpinner(R.id.spin_trait, template.getUseReflexes());
			
			this.<CheckBox>GetView(R.id.use_gp).setChecked((profile.getGp() == 1)&&(template.getisGp() == 1));
	
			if(profile.getGp() == 0 || template.getSkillRank() == 0){
				this.<CheckBox>GetView(R.id.use_gp).setVisibility(View.INVISIBLE);
			}	
		}
		else{
			setSpinner(R.id.spin_trait, template.getCastingRing());
		}
	}
	
	private void setSpinner(int id, int value) {
		Spinner spinner = this.<Spinner>GetView(id);		
		spinner.setSelection(value);		
	}

	private void configName() {
		EditText nameBox = this.<EditText>GetView(R.id.template_name_box);
		nameBox.setText(template.getName());	
	}

	private Profile profile;
	private Template template;
	private DBHelper dbHelper;	 
	private TextView errorLabel;
	private TextView traitLabel;
	private Spinner traitSpinner;
	private boolean isMelee = false;

	private void RegisterButtons() {
		this.<Button>GetView(R.id.save_template).setOnClickListener(this);
		this.<Button>GetView(R.id.delete_template).setOnClickListener(this);
		if(isMelee){
			this.<Spinner>GetView(R.id.spin_skills).setOnItemSelectedListener(this);
		}else{
			this.<RelativeLayout>GetView(R.id.melee_layout_box).setVisibility(View.GONE);
			traitLabel.setText(getString(R.string.casting_ring));
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.casting_rings, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			traitSpinner.setAdapter(adapter);
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T extends View> T GetView(int id) {
		try{
			return (T)findViewById(id);}
		catch(Exception e){
			return null;
		}
	}


	@Override
	public void onClick(View e) {
		Button button = this.<Button>GetView(e.getId());		
		switch(button.getId()){			 
			case R.id.delete_template:
				dbHelper.deleteTemplate(template.getId(), template.getProfileId());
				this.finish();
				break; 
			case R.id.save_template: submit(); break;			
			default: break;
		}		
	}

	private void submit() {
		errorLabel.setText("");
		if(!validateName()){
			return;
		}
		if(!validateBoxes()){
			return;
		}
		updateTemplate();
		dbHelper.saveTemplate(template);
		this.finish();
	}

	private void updateTemplate() {
		if(isMelee){
			template.setAgility(GetValue(R.id.spin_agi));
			template.setisGp(this.<CheckBox>GetView(R.id.use_gp).isChecked()? 1 : 0);
			template.setReflexes(GetValue(R.id.spin_ref));
			template.setUseReflexes(traitSpinner.getSelectedItemPosition());
			template.setSkillRank(GetValue(R.id.spin_skills));
		}else
		{
			template.setCastingRing(traitSpinner.getSelectedItemPosition());
		}
	}

	private boolean validateBoxes() {
		EditText modifier = this.<EditText>GetView(R.id.modifier_textbox);
		EditText rolled = this.<EditText>GetView(R.id.rolled_textbox);
		EditText kept = this.<EditText>GetView(R.id.kept_textbox);
		TextView errorText = this.<TextView>GetView(R.id.error_message);
		
		try{
			template.setModifier(Integer.parseInt(modifier.getText().toString()));
			template.setRolled(Integer.parseInt(rolled.getText().toString()));
			template.setKept(Integer.parseInt(kept.getText().toString()));
		}catch(Exception e){
			errorText.setText(R.string.unable_to_parse);
			return false;
		}
		errorText.setText("");

		return true;
	}

	private boolean validateName() {
		
		EditText nameBox = this.<EditText>GetView(R.id.template_name_box);
		String name = nameBox.getText().toString();
		if(name == null || name .equals("") || name.equals(getString(R.string.error_no_name))){
			errorLabel.setText(getString(R.string.error_no_name));
			return false;
		}
		
		if(dbHelper.templateNameExists(name, template.getId(), template.getProfileId())){
			errorLabel.setText(R.string.name_in_use);
			return false;
		}
		if(name.equals(R.string.new_template))
		{
			errorLabel.setText(R.string.illegal_template_name);
		}
		template.setName(name);
		return true;
		
	}

	private int GetValue(int id) {
		int result = -1; //#todo find a better way to do this.
		Spinner spinner = this.<Spinner>GetView(id);
		if(spinner == null)
			return result;
		String value = (String)spinner.getSelectedItem();
		try {
			result = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			if(value.equals("Yes"))
				result = 1;
			if(value.equals("No"))
				result = 0;
		}
		return result;
	}

	@Override
	public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {		
		this.<CheckBox>GetView(R.id.use_gp).setVisibility(View.INVISIBLE);
		if(profile.getGp() == 1 && position > 0){
			this.<CheckBox>GetView(R.id.use_gp).setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}




}
