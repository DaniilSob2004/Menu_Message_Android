package com.example.menu_hw.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.menu_hw.interfaces.IText;
import com.example.menu_hw.MainActivity;
import com.example.menu_hw.R;

public class EditTextDialog extends DialogFragment {

    private IText text;

    // вызывается, когда фрагмент прикрепляется к активности
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        text = (MainActivity)context;  // привязываем интерфейс к контексту
    }

    // вызывается, когда фрагмент диалога должен создать свое диалоговое окно
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // getActivity() - возвращает активность, к которой прикреплен фрагмент
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_title)
                .setView(R.layout.my_input_text)  // установка польз. макета, который будет отображаться в диалоговом окне
                .setPositiveButton(R.string.dialog_ok, (dialog, which) -> {
                    Dialog d = getDialog();
                    Bundle b = getArguments();
                    if (d != null && b != null) {
                        EditText editText = d.findViewById(R.id.dialog_et);
                        text.setText(editText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.dialog_no, null)
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog d = getDialog();  // текущее диалоговое окно
        Bundle b = getArguments();  // аргументы, переданные в диалог
        if (d != null && b != null) {
            String str = b.getString("str");
            EditText editText = d.findViewById(R.id.dialog_et);
            editText.setText(str);
        }
    }
}
