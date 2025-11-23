package com.msoft.carehomeapp.ui.utils;

import javafx.scene.control.ComboBox;

/**
 *
 * @author lucas
 */
public class PromptRestore {
    public static <T> void enablePromptRestore(ComboBox<T> combo) {
        combo.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(combo.getPromptText());
                } else {
                    setText(item.toString());
                }
            }
        });
    }
}