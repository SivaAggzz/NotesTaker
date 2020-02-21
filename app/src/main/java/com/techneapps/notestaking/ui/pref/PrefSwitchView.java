package com.techneapps.notestaking.ui.pref;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.providers.interfaces.OnPrefClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PrefSwitchView extends RelativeLayout {
    @BindView(R.id.prefSwitch)
    SwitchCompat prefSwitch;
    @BindView(R.id.prefSummary)
    TextView prefSummary;
    @BindView(R.id.prefTitle)
    TextView prefTitle;
    @BindView(R.id.prefProTag)
    TextView prefProTag;
    private Context context;
    private boolean prefEnabled = true;
    private OnPrefClickListener onPrefClickListener;


    public PrefSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate(context, R.layout.cutom_pref_switch_comp, this);
        ButterKnife.bind(this);


        TypedArray attributes = null;
        try {
            attributes = context.obtainStyledAttributes(attrs, R.styleable.PrefSwitchView);
            String titleValue = attributes.getString(R.styleable.PrefSwitchView_switchTitle);
            String summaryValue = attributes.getString(R.styleable.PrefSwitchView_switchSummary);
            boolean switchValue = attributes.getBoolean(R.styleable.PrefSwitchView_switchChecked, false);
            boolean proTagShown = attributes.getBoolean(R.styleable.PrefSwitchView_switchProTag, false);


            prefTitle.setText(titleValue);
            prefSummary.setText(summaryValue);
            prefSwitch.setChecked(switchValue);
            setProTagVisible(proTagShown);


        } finally {
            assert attributes != null;
            attributes.recycle();
        }


        setOnClickListener(v -> {
            if (onPrefClickListener != null) {
                if (prefEnabled) {
                    onPrefClickListener.OnPrefClick();
                }
            }
        });


    }

    public void addOnPrefClickListener(OnPrefClickListener onPrefClickListener) {
        this.onPrefClickListener = onPrefClickListener;

    }

    public void setChecked(boolean switchValue) {
        prefSwitch.setChecked(switchValue);
    }

    private void setProTagVisible(boolean value) {
        if (value) {
            prefProTag.setVisibility(VISIBLE);
        } else {
            prefProTag.setVisibility(GONE);
        }
    }

    public void setPrefEnabled(boolean enabled) {
        this.prefEnabled = enabled;
        if (enabled) {
            prefTitle.setTextColor(getContext().getResources().getColor(R.color.md_grey_900));
            prefSummary.setTextColor(getContext().getResources().getColor(R.color.md_grey_700));
        } else {
            prefTitle.setTextColor(getContext().getResources().getColor(R.color.disabled_text_color_for_dark_theme));
            prefSummary.setTextColor(getContext().getResources().getColor(R.color.disabled_text_color_for_dark_theme));


        }
    }

}
