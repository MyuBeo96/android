package com.tcscuat.mobiletrading.design;

import android.app.Service;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fss.mobiletrading.common.Rule;
import com.tcscuat.mobiletrading.R;

import org.w3c.dom.Text;

public class CustomPassLayout extends LinearLayout {

    static final int HORIZONTAL = 0;
    static final int VERTICAL = 1;
    static final int TYPE_NUMBER = 0;
    static final int TYPE_TEXT = 1;
    static final int TYPE_TEXTPASSWORD = 2;
    static final int TYPE_CAP = 3;
    static final int NUMBERDECIMAL = 4;
    static final int GRAVITY_CENTER = 0;
    static final int GRAVITY_LEFT = 1;
    static final int GRAVITY_RIGHT = 2;
    static final int STYLE_BOLD = 0;
    static final int STYLE_NORMAL = 1;

    TextView label;
    TextView content;
    EditText content2;
    TextView btn;
    View line;
    RelativeLayout viewgenotp;
    TextView labelremember;

    ImageButton checkBox;
    boolean edittable;
    boolean showlabel;

    boolean active_color_buysell;
    int[] attrsArray = new int[] { android.R.attr.id, // 0
            android.R.attr.layout_width, // 1
            android.R.attr.layout_height // 2
    };

    public CustomPassLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.LabelContentLayout);
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
        int orien = typedArray.getInt(
                R.styleable.LabelContentLayout_orientation, HORIZONTAL);
//        if (orien == VERTICAL) {
//            inflater.inflate(R.layout.labelcontentvertical, this);
//            label = (TextView) findViewById(R.id.labelcontentver_label);
//            content = (TextView) findViewById(R.id.labelcontentver_content);
//            content2 = (EditText) findViewById(R.id.labelcontentver_content2);
//            btn= (Button) findViewById(R.id.btn_genotp);
//            line = findViewById(R.id.labelcontentver_line);
//        } else {
//            inflater.inflate(R.layout.otplayouthorizontal, this);
//            label = (TextView) findViewById(R.id.labelcontenthori_label);
//            content = (TextView) findViewById(R.id.labelcontenthori_content);
//            content2 = (EditText) findViewById(R.id.labelcontenthori_content2);
//            btn= (Button) findViewById(R.id.btn_genotp);
//            line = findViewById(R.id.labelcontenthori_line);
//        }
        inflater.inflate(R.layout.custompasslayout, this);
        label = (TextView) findViewById(R.id.labelcontenthori_label);
        content = (TextView) findViewById(R.id.labelcontenthori_content);
        content2 = (EditText) findViewById(R.id.labelcontenthori_content2);
        btn= (TextView) findViewById(R.id.btn_genotp);
        line = findViewById(R.id.labelcontenthori_line);
        viewgenotp = (RelativeLayout) findViewById(R.id.view_genotp);
        labelremember = (TextView) findViewById(R.id.text_custompass_remember);

        checkBox= (ImageButton) findViewById(R.id.img_custompass_remember);

        label.setTextColor(typedArray.getColor(
                R.styleable.LabelContentLayout_labelColor, getResources()
                        .getColor(R.color.label_text_color)));
        content.setTextColor(typedArray.getColor(
                R.styleable.LabelContentLayout_contentColor, getResources()
                        .getColor(R.color.green_text)));
        content2.setTextColor(typedArray.getColor(
                R.styleable.LabelContentLayout_contentColor, getResources()
                        .getColor(R.color.green_text)));
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, typedArray.getInt(
                R.styleable.LabelContentLayout_labelSize, getResources()
                        .getInteger(R.dimen.m_label_size_value)));
        content.setTextSize(TypedValue.COMPLEX_UNIT_SP, typedArray.getInt(
                R.styleable.LabelContentLayout_contentSize, getResources()
                        .getInteger(R.dimen.m_content_size_value)));
        content2.setTextSize(TypedValue.COMPLEX_UNIT_SP, typedArray.getInt(
                R.styleable.LabelContentLayout_contentSize, getResources()
                        .getInteger(R.dimen.m_edittext_size_value)));
        label.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray
                .getDimensionPixelSize(
                        R.styleable.LabelContentLayout_labelsize_dimen,
                        getResources().getDimensionPixelSize(
                                R.dimen.m_label_size)));
        content.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray
                .getDimensionPixelSize(
                        R.styleable.LabelContentLayout_contentsize_dimen,
                        getResources().getDimensionPixelSize(
                                R.dimen.m_content_size)));
        content2.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray
                .getDimensionPixelSize(
                        R.styleable.LabelContentLayout_contentsize_dimen,
                        getResources().getDimensionPixelSize(
                                R.dimen.m_content_size)));
        content.setText(typedArray
                .getString(R.styleable.LabelContentLayout_contentText));
        content2.setText(typedArray
                .getString(R.styleable.LabelContentLayout_contentText));
        label.setText(typedArray
                .getString(R.styleable.LabelContentLayout_labelText));
        labelremember.setText(typedArray
                .getString(R.styleable.LabelContentLayout_labelRememberText));
        showlabel = typedArray.getBoolean(
                R.styleable.LabelContentLayout_showLabel,true);
        if(showlabel)
            label.setVisibility(View.VISIBLE);
        else
            label.setVisibility(View.GONE);
        content.setHint(typedArray
                .getString(R.styleable.LabelContentLayout_contentHint));
        content2.setHint(typedArray
                .getString(R.styleable.LabelContentLayout_contentHint));
        line.setBackgroundColor(typedArray.getColor(
                R.styleable.LabelContentLayout_separationColor, getResources()
                        .getColor(R.color.lc_separation_color)));
        edittable = typedArray.getBoolean(
                R.styleable.LabelContentLayout_editable, false);
        if (edittable) {
            content.setVisibility(GONE);
            content2.setVisibility(VISIBLE);
        }
        content.setSingleLine(typedArray.getBoolean(
                R.styleable.LabelContentLayout_singleLine, true));
        content2.setSingleLine(typedArray.getBoolean(
                R.styleable.LabelContentLayout_singleLine, true));
        switch (typedArray.getInt(R.styleable.LabelContentLayout_inputType,
                TYPE_TEXT)) {
            case TYPE_NUMBER:
                content2.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL
                        | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                break;
            case TYPE_CAP:
                content2.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
                        | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                break;
            case TYPE_TEXTPASSWORD:
                content2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD
                        | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                content2.setTransformationMethod(PasswordTransformationMethod
                        .getInstance());
                viewgenotp.setVisibility(View.GONE);
                break;
            case NUMBERDECIMAL:
                content2.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL
                        | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                break;
            default:
                content2.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                break;
        }
        if (typedArray.getString(R.styleable.LabelContentLayout_digits) != null) {
            content2.setKeyListener(DigitsKeyListener.getInstance(typedArray
                    .getString(R.styleable.LabelContentLayout_digits)));
        }
        switch (typedArray.getInt(
                R.styleable.LabelContentLayout_contentgravity, GRAVITY_LEFT)) {
            case GRAVITY_CENTER:
                content2.setGravity(Gravity.CENTER);
                content.setGravity(Gravity.CENTER);
                break;
            case GRAVITY_LEFT:
                content2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                content.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                break;
            case GRAVITY_RIGHT:
                content2.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                content.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                break;
            default:
                break;
        }
        active_color_buysell = typedArray.getBoolean(
                R.styleable.LabelContentLayout_active_color_buysell, false);
        if (typedArray.getBoolean(
                R.styleable.LabelContentLayout_checkPriceRull, false)) {
            checkPriceRule();
        }

        switch (typedArray.getInt(R.styleable.LabelContentLayout_setTextStyle,
                STYLE_NORMAL)) {
            case STYLE_BOLD:
                content.setTypeface(content.getTypeface(), Typeface.BOLD);
                content2.setTypeface(content2.getTypeface(), Typeface.BOLD);
                break;
            default:
                content.setTypeface(content.getTypeface(), Typeface.NORMAL);
                content2.setTypeface(content2.getTypeface(), Typeface.NORMAL);
                break;
        }
        int labelweight = typedArray.getInt(
                R.styleable.LabelContentLayout_labelweight, 5);
        ((LinearLayout.LayoutParams) label.getLayoutParams()).weight = labelweight;
        ((LinearLayout.LayoutParams) content.getLayoutParams()).weight = (10 - labelweight);
        ((LinearLayout.LayoutParams) content2.getLayoutParams()).weight = (10 - labelweight);
        // set gravity cho label
        switch (typedArray.getInt(R.styleable.LabelContentLayout_labelgravity,
                GRAVITY_LEFT)) {
            case GRAVITY_CENTER:
                label.setGravity(Gravity.CENTER);
                break;
            case GRAVITY_LEFT:
                label.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                break;
            case GRAVITY_RIGHT:
                label.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                break;
            default:
                break;
        }
    }

    public TextView getLabel() {
        return label;
    }

    public TextView getContent() {
        return content;
    }

    public EditText getEditContent() {
        return content2;
    }

    public TextView getbtn(){
        return btn;
    }



    public ImageButton getcheckbox(){return checkBox;}

    public void setText(String str) {
        if (str != null) {
            content.setText(str);
            content2.setText(str);
        }
    }

    public String getText() {
        if (edittable) {
            return content2.getText().toString();
        } else {
            return content.getText().toString();
        }

    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        if (content2 != null) {
            content2.setOnFocusChangeListener(l);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        content2.setEnabled(enabled);
    }

    @Override
    public void setActivated(boolean activated) {
        super.setActivated(activated);
        if (active_color_buysell) {
            if (activated) {
                content.setTextColor(getResources().getColor(R.color.color_Mua));
                content2.setTextColor(getResources()
                        .getColor(R.color.color_Mua));
            } else {
                content.setTextColor(getResources().getColor(R.color.color_Ban));
                content2.setTextColor(getResources()
                        .getColor(R.color.color_Ban));
            }
        }
    }

    private void checkPriceRule() {
        content2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Rule.checkPriceFormat(s.toString())) {
                    content2.removeTextChangedListener(this);
                    s.delete(s.length() - 1, s.length());
                    content2.addTextChangedListener(this);
                }
            }
        });
    }

    public void setContentColor(int color) {
        content.setTextColor(color);
        content2.setTextColor(color);
    }

}
