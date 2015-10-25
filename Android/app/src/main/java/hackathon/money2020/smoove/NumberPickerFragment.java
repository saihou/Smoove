package hackathon.money2020.smoove;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

public class NumberPickerFragment extends DialogFragment implements OnClickListener {
    public static final String TAG                = "NumberUnitPickerFragment";
    public OnNumberSetListener listener;
    private View               mView;

    private String             title;
    private int                titleId;
    private String             message;
    private int                messageId;
    private String             positiveButtonText = "Set";
    private int                positiveButtonId;
    private String             negativeButtonText = "Cancel";
    private int                negativeButtonId;
    private Drawable           icon;
    private int                iconId;
    private String             unit               = "";
    private Integer            unitId;

    private int                maxValue = 7;
    private int                minValue = 1;
    private int                value;
    private boolean            twoDigitsValues    = false;

    private NumberPicker       numberPicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        LinearLayout viewGroup = new LinearLayout(getActivity());
        viewGroup.setOrientation(LinearLayout.HORIZONTAL);
        viewGroup.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        viewGroup.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout numberLayout = new LinearLayout(getActivity());
        numberLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        numberLayout.setOrientation(LinearLayout.HORIZONTAL);
        numberLayout.setGravity(Gravity.CENTER_VERTICAL);

        // NumberPicker
        numberPicker = new NumberPicker(getActivity());
        if (maxValue > 0)
            numberPicker.setMaxValue(maxValue);
        if (minValue > 0)
            numberPicker.setMinValue(minValue);

        TextView pax = (TextView) getActivity().findViewById(R.id.pax);
        numberPicker.setValue(Integer.parseInt(pax.getText().toString()));

        numberLayout.addView(numberPicker);

        // Unit
        if (!TextUtils.isEmpty(unit) || unitId != null) {
            TextView unitTextView = new TextView(getActivity());

            if (unitId > 0)
                unitTextView.setText(unitId);
            else
                unitTextView.setText(unit);

            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            ll.setMargins(25, 0, 0, 0);
            unitTextView.setLayoutParams(ll);

            numberLayout.addView(unitTextView);
        }

        viewGroup.addView(numberLayout);

        alertDialogBuilder.setView(viewGroup);

        // Set title
        if (!TextUtils.isEmpty(title))
            alertDialogBuilder.setTitle(title);
        else if (titleId > 0)
            alertDialogBuilder.setTitle(titleId);

        // Set message
        if (!TextUtils.isEmpty(message))
            alertDialogBuilder.setMessage(message);
        else if (messageId > 0)
            alertDialogBuilder.setMessage(messageId);

        // Set icon
        if (icon != null)
            alertDialogBuilder.setIcon(icon);
        else if (iconId > 0)
            alertDialogBuilder.setIcon(iconId);

        // Set dialog positive button
        if (positiveButtonId > 0)
            alertDialogBuilder.setPositiveButton(positiveButtonId, this);
        else
            alertDialogBuilder.setPositiveButton(positiveButtonText, this);

        // Set dialog negative button
        if (negativeButtonId > 0)
            alertDialogBuilder.setNegativeButton(negativeButtonId, this);
        else
            alertDialogBuilder.setNegativeButton(negativeButtonText, this);


        this.listener = new OnNumberSetListener() {
            @Override
            public void onNumberSet(Integer number, View v) {
                TextView pax = (TextView) getActivity().findViewById(R.id.pax);
                pax.setText(number.toString());
            }
        };

        return alertDialogBuilder.create();
    }

    public void setView(View v) {
        this.mView = v;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle(int title) {
        this.titleId = title;
    }


    public void setDrawable(Drawable drawable) {
        this.icon = drawable;
    }

    public void setDrawable(int drawable) {
        this.iconId = drawable;
    }


    public void setOnNumberSetListener(OnNumberSetListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:

                try {
                    listener.onNumberSet(numberPicker.getValue(), mView);
                } catch (Exception e) {
                    Log.e("Exception", TAG + " must implement OnNumberSetListener");
                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:

                break;
        }
    }

    public interface OnNumberSetListener {
        void onNumberSet(Integer number, View v);
    }
}