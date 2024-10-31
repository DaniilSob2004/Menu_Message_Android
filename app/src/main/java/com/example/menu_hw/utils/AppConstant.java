package com.example.menu_hw.utils;

import com.example.menu_hw.R;
import java.util.ArrayList;
import java.util.List;

public class AppConstant {
    public static final List<Integer> ALL_ACTIONS_MENU = new ArrayList<>();
    public static final List<String> SELECT_TEXTS = new ArrayList<>();

    static {
        ALL_ACTIONS_MENU.add(R.id.action_edit_text);
        ALL_ACTIONS_MENU.add(R.id.action_select_text);
        ALL_ACTIONS_MENU.add(R.id.action_get_current_time);
        ALL_ACTIONS_MENU.add(R.id.action_change_date);
        ALL_ACTIONS_MENU.add(R.id.action_show_notification);

        SELECT_TEXTS.add("Hello");
        SELECT_TEXTS.add("Daniil");
        SELECT_TEXTS.add("Soboliev");
        SELECT_TEXTS.add("Kinder");
        SELECT_TEXTS.add("Haribol");
    }
}
