package com.example.menu_hw;

import android.os.Bundle;
import android.app.DatePickerDialog;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.menu_hw.dialogs.EditTextDialog;
import com.example.menu_hw.dialogs.SelectTextDialog;
import com.example.menu_hw.interfaces.IText;
import com.example.menu_hw.utils.AppConstant;
import com.example.menu_hw.utils.TestUtils;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements IText {

    private Calendar dateAndTime;
    private DatePickerDialog.OnDateSetListener dateListener;

    private TextView editTimeTextView;
    private EditText editTextMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // подключаем toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // отключаем стандартный заголовок Toolbar, чтобы оставить только кастомный TextView
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // находим все нужные элементы
        init();

        // создаём канал для уведомлений
        MyNotificationManager.createNotificationChannel(this);

        // обработчик выбора даты
        dateAndTime = Calendar.getInstance();
        dateListener = (DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDate();
        };
    }

    // используется для создания и отображения меню в Toolbar (или ActionBar) активити
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // преобразует файл XML (R.menu.menu_main) в объекты меню, которые можно отобразить в интерфейсе
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (!AppConstant.ALL_ACTIONS_MENU.contains(itemId)) {
            return super.onOptionsItemSelected(item);
        }

        if (itemId == R.id.action_edit_text) {
            EditTextDialog dialog = new EditTextDialog();
            Bundle args = new Bundle();
            args.putString("str", editTextMain.getText().toString());
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "dialog");
        }
        else if (itemId == R.id.action_select_text) {
            SelectTextDialog dialog = new SelectTextDialog();
            dialog.show(getSupportFragmentManager(), "dialog");
        }
        else if (itemId == R.id.action_get_current_time) {
            MyNotificationManager.showNotification(
                    MainActivity.this,
                    "Current date and time",
                    TestUtils.getCurrentDateTime()
            );
        }
        else if (itemId == R.id.action_change_date) {
            showChoiceDate();
        }
        else if (itemId == R.id.action_show_notification) {
            String text = editTextMain.getText().toString();
            if (!text.isEmpty()) {
                MyNotificationManager.showNotification(
                        MainActivity.this,
                        "Current text",
                        text + " (" + TestUtils.getCurrentDateTime() + ")");
            }
        }

        return true;
    }

    @Override
    public void setText(String text) {
        editTextMain.setText(text);
        editTimeTextView.setText(TestUtils.getCurrentDateTime());
    }


    private void init() {
        editTimeTextView = findViewById(R.id.edit_time_text_view);
        editTextMain = findViewById(R.id.edit_text_main);
    }

    private void showChoiceDate() {
        new DatePickerDialog(
                MainActivity.this,
                dateListener,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void setDate() {
        editTimeTextView.setText(TestUtils.getCurrentDateTime(dateAndTime));
    }
}