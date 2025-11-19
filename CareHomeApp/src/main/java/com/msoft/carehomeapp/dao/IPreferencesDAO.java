package com.msoft.carehomeapp.dao;

import com.msoft.carehomeapp.model.Preferences;

/**
 *
 * @author lucas
 */
public interface IPreferencesDAO {
    Preferences loadPreferences();
    void saveOrUpdate(Preferences prefs);
    void isNotificationEnabled();
}
