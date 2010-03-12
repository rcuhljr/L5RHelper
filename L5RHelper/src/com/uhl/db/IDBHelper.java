package com.uhl.db;

import android.database.Cursor;

import com.uhl.calc.Roll;

public interface IDBHelper {

	public abstract boolean saveProfile(Profile profile);

	public abstract Profile loadProfile(int id);

	public abstract Cursor getHistogram(Roll roll);

	public abstract Profile[] getProfiles();

	public abstract boolean deleteProfile(int id);

	public abstract Cursor getTemplateNames(int profileId);

	public abstract boolean profileNameExists(String name, int id);

	public abstract boolean templateNameExists(String name, int id,
			int profileId);

	public abstract Cursor getTemplates(int profileId);

	public abstract Template loadTemplate(int id);

	public abstract boolean saveTemplate(Template template);

	public abstract boolean deleteTemplate(Integer id, Integer profileId);

}