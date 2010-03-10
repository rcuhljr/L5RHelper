package com.uhl.db;

import android.database.Cursor;

import com.uhl.calc.Roll;

public interface IDBHelper {

	public abstract void saveProfile(Profile profile);

	public abstract Profile loadProfile(int id);

	public abstract Cursor getHistogram(Roll roll);

	public abstract Cursor getProfiles();

	public abstract void deleteProfile(int id);

	public abstract Cursor getTemplateNames(int profileId);

	public abstract boolean profileNameExists(String name, int id);

	public abstract boolean templateNameExists(String name, int id,
			int profileId);

	public abstract Cursor getTemplates(int profileId);

	public abstract Template loadTemplate(int id);

	public abstract void saveTemplate(Template template);

	public abstract void deleteTemplate(Integer id, Integer profileId);

}