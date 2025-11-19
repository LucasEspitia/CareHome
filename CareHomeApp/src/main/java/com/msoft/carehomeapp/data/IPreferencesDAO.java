package com.msoft.carehomeapp.data;

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
