package org.altervista.andrearosa.statebutton;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by andrea on 02/12/15.
 */
public class StateButton extends RelativeLayout {

    private static final String TAG = "StateButton";

    private static final long ANIMATION_DURATION = 175;
    /**
     * Previous state of button
     */
    private BUTTON_STATES oldState;

    /**
     * Current state of the button
     */
    private BUTTON_STATES currentState;

    /**
     * Font
     */
    private String fontFace;
    private float fontSize;

    /**
     * Texts for the different states
     */
    private String disabledText = "";
    private String enabledText = "";
    private String loadingText = "";
    private String successText = "";
    private String failedText = "";

    /**
     * Texts color for the different states
     */
    private int disabledTextColor;
    private int enabledTextColor;
    private int loadingTextColor;
    private int successTextColor;
    private int failedTextColor;

    /**
     * Backgrounds for different states
     */
    private int disabledBackground;
    private int enabledBackground;
    private int loadingBackground;
    private int successBackground;
    private int failedBackground;

    /**
     * Icons for different states
     */
    private int disabledIcon;
    private int enabledIcon;
    private int loadingIcon;
    private int successIcon;
    private int failedIcon;

    /**
     * Icons color for different states
     */
    private int disabledIconColor;
    private int enabledIconColor;
    private int loadingIconColor;
    private int successIconColor;
    private int failedIconColor;

    /**
     * Icons visibility for different states
     */
    private boolean disabledIconVisibility;
    private boolean enabledIconVisibility;
    private boolean loadingIconVisibility;
    private boolean successIconVisibility;
    private boolean failedIconVisibility;

    /**
     * Rotating animation for icons
     */
    private RotateAnimation anim;

    /**
     * Animations for text
     */
    private Animation slideInTop;
    private Animation slideOutBottom;

    // TODO Fonts

    /**
     * Internal views
     */
    private TextView textView;
    private ImageView imageView;

    public StateButton(Context context) {
        this(context, null);
    }

    public StateButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.StateButton, 0, 0);

        try {
            currentState = BUTTON_STATES.fromValue(a.getInt(R.styleable.StateButton_state, 1));

            // Fontface
            fontFace = a.getString(R.styleable.StateButton_fontFace);
            fontSize = a.getFloat(R.styleable.StateButton_fontSize, 18);

            // Texts
            disabledText = a.getString(R.styleable.StateButton_disabledText);
            enabledText = a.getString(R.styleable.StateButton_enabledText);
            loadingText = a.getString(R.styleable.StateButton_loadingText);
            successText = a.getString(R.styleable.StateButton_successText);
            failedText = a.getString(R.styleable.StateButton_failedText);

            // Texts color
            disabledTextColor = a.getResourceId(R.styleable.StateButton_disabledTextColor, R.color.md_grey_500);
            enabledTextColor = a.getResourceId(R.styleable.StateButton_enabledTextColor, R.color.md_white_1000);
            loadingTextColor = a.getResourceId(R.styleable.StateButton_loadingTextColor, R.color.md_grey_500);
            successTextColor = a.getResourceId(R.styleable.StateButton_successTextColor, R.color.md_white_1000);
            failedTextColor = a.getResourceId(R.styleable.StateButton_failedTextColor, R.color.md_white_1000);

            // Backgrounds
            disabledBackground = a.getResourceId(R.styleable.StateButton_disabledBackground, R.drawable.statebutton_md_redbg_disabled);
            enabledBackground = a.getResourceId(R.styleable.StateButton_enabledBackground, R.drawable.statebutton_md_redbg_enabled);
            loadingBackground = a.getResourceId(R.styleable.StateButton_loadingBackground, R.drawable.statebutton_md_redbg_loading);
            successBackground = a.getResourceId(R.styleable.StateButton_successBackground, R.drawable.statebutton_md_redbg_success);
            failedBackground = a.getResourceId(R.styleable.StateButton_failedBackground, R.drawable.statebutton_md_redbg_failed);

            // Icons
            disabledIcon = a.getResourceId(R.styleable.StateButton_disabledIcon, R.drawable.statebutton_ic_disabled);
            enabledIcon = a.getResourceId(R.styleable.StateButton_enabledIcon, R.drawable.statebutton_ic_enabled);
            loadingIcon = a.getResourceId(R.styleable.StateButton_loadingIcon, R.drawable.statebutton_ic_loading);
            successIcon = a.getResourceId(R.styleable.StateButton_successIcon, R.drawable.statebutton_ic_success);
            failedIcon = a.getResourceId(R.styleable.StateButton_failedIcon, R.drawable.statebutton_ic_failed);

            // Icons color
            disabledIconColor = a.getResourceId(R.styleable.StateButton_disabledIconColor, R.color.md_grey_500);
            enabledIconColor = a.getResourceId(R.styleable.StateButton_enabledIconColor, R.color.md_white_1000);
            loadingIconColor = a.getResourceId(R.styleable.StateButton_loadingIconColor, R.color.md_grey_500);
            successIconColor = a.getResourceId(R.styleable.StateButton_successIconColor, R.color.md_white_1000);
            failedIconColor = a.getResourceId(R.styleable.StateButton_failedIconColor, R.color.md_white_1000);

            // Icons visibility
            disabledIconVisibility = a.getBoolean(R.styleable.StateButton_disabledIconVisible, true);
            enabledIconVisibility = a.getBoolean(R.styleable.StateButton_enabledIconVisible, true);
            loadingIconVisibility = a.getBoolean(R.styleable.StateButton_loadingIconVisible, true);
            successIconVisibility = a.getBoolean(R.styleable.StateButton_successIconVisible, true);
            failedIconVisibility = a.getBoolean(R.styleable.StateButton_failedIconVisible, true);

        } finally {
            a.recycle();
        }

        oldState = currentState;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.statebutton_layout, this, true);

        textView = (TextView) getChildAt(0);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        Typeface typeface = Typeface.create(fontFace, Typeface.NORMAL);
        textView.setTypeface(typeface);

        imageView = (ImageView) getChildAt(1);

        slideInTop = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_top);
        slideOutBottom = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom);

        anim = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);

        anim.setDuration(700);

        setup();

    }

    private class TempWrapper {
        String text;
        int textColor;
        int background;
        int icon;
        int iconColor;
        int iconVisibility;
        boolean animateIcon;
    }

    private void setup() {
        imageView.setAnimation(null);
        final TempWrapper tmp = new TempWrapper();
        final Drawable[] backgrounds = new Drawable[2];
        backgrounds[0] = getBackground();
        switch (currentState.getValue()) {
            case 0: // disabled
                tmp.text = disabledText;
                tmp.textColor = ContextCompat.getColor(getContext(), disabledTextColor);
                tmp.background = disabledBackground;
                tmp.icon = disabledIcon;
                tmp.iconColor = disabledIconColor;
                tmp.iconVisibility = disabledIconVisibility ? VISIBLE : INVISIBLE;
                tmp.animateIcon = false;
                break;
            case 1: // enabled
                tmp.text = enabledText;
                tmp.textColor = ContextCompat.getColor(getContext(), enabledTextColor);
                tmp.background = enabledBackground;
                tmp.icon = enabledIcon;
                tmp.iconColor = enabledIconColor;
                tmp.iconVisibility = enabledIconVisibility ? VISIBLE : INVISIBLE;
                tmp.animateIcon = false;
                break;
            case 2: // loading
                tmp.text = loadingText;
                tmp.textColor = ContextCompat.getColor(getContext(), loadingTextColor);
                tmp.background = loadingBackground;
                tmp.icon = loadingIcon;
                tmp.iconColor = loadingIconColor;
                tmp.iconVisibility = loadingIconVisibility ? VISIBLE : INVISIBLE;
                tmp.animateIcon = true;
                break;
            case 3: // success
                tmp.text = successText;
                tmp.textColor = ContextCompat.getColor(getContext(), successTextColor);
                tmp.background = successBackground;
                tmp.icon = successIcon;
                tmp.iconColor = successIconColor;
                tmp.iconVisibility = successIconVisibility ? VISIBLE : INVISIBLE;
                tmp.animateIcon = false;
                break;
            case 4: // failed
                tmp.text = failedText;
                tmp.textColor = ContextCompat.getColor(getContext(), failedTextColor);
                tmp.background = failedBackground;
                tmp.icon = failedIcon;
                tmp.iconColor = failedIconColor;
                tmp.iconVisibility = failedIconVisibility ? VISIBLE : INVISIBLE;
                tmp.animateIcon = false;
                break;
        }
        textView.animate().translationY(getHeight()).alpha(0f).setDuration(ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                backgrounds[1] = ContextCompat.getDrawable(getContext(), tmp.background);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (backgrounds[0] != null && backgrounds[1] != null) {
                        TransitionDrawable transition = new TransitionDrawable(backgrounds);
                        transition.setCrossFadeEnabled(true);
                        setBackground(transition);
                        transition.startTransition((int) ANIMATION_DURATION);
                    } else {
                        setBackgroundResource(tmp.background);
                    }
                } else {
                    setBackgroundResource(tmp.background);
                }
                int oldVisibility = imageView.getVisibility();
                if (oldVisibility == VISIBLE) {
                    imageView
                            .animate()
                            .scaleX(0)
                            .scaleY(0)
                            .alpha(0f)
                            .setDuration(ANIMATION_DURATION)
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    imageView.setVisibility(INVISIBLE);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                }
                if (tmp.iconVisibility == VISIBLE) {
                    imageView.setVisibility(tmp.iconVisibility);
                    imageView
                            .animate()
                            .scaleX(1)
                            .scaleY(1)
                            .alpha(1f)
                            .setDuration(ANIMATION_DURATION)
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                    imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), tmp.icon));
                                    imageView.getDrawable().setColorFilter(ContextCompat.getColor(getContext(), tmp.iconColor), PorterDuff.Mode.MULTIPLY);
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    imageView.setVisibility(VISIBLE);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                }
                textView.setTextColor(tmp.textColor);
                if (tmp.animateIcon)
                    imageView.startAnimation(anim);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                textView.setText(tmp.text);
                textView.setY(-getHeight());
                textView.animate().translationY(0f).alpha(1f).setDuration(ANIMATION_DURATION).setListener(null);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        invalidate();
    }


    public String getDisabledText() {
        return disabledText;
    }

    public void setDisabledText(String disabledText) {
        this.disabledText = disabledText;
        setup();
    }

    public String getEnabledText() {
        return enabledText;
    }

    public void setEnabledText(String enabledText) {
        this.enabledText = enabledText;
        setup();
    }

    public String getLoadingText() {
        return loadingText;
    }

    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
        setup();
    }

    public String getSuccessText() {
        return successText;
    }

    public void setSuccessText(String successText) {
        this.successText = successText;
        setup();
    }

    public String getFailedText() {
        return failedText;
    }

    public void setFailedText(String failedText) {
        this.failedText = failedText;
        setup();
    }

    /**
     * Set the state of the button
     *
     * @param state
     */
    public void setState(BUTTON_STATES state) {
        this.oldState = this.currentState;
        this.currentState = state;
        setup();
    }

    /**
     * Get the buttons state
     *
     * @return
     */
    public BUTTON_STATES getState() {
        return currentState;
    }

    /**
     * Enum for states
     */
    public enum BUTTON_STATES {

        DISABLED(0),
        ENABLED(1),
        LOADING(2),
        SUCCESS(3),
        FAILED(4);

        private final int value;

        BUTTON_STATES(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }

        public static BUTTON_STATES fromValue(int value) {
            for (BUTTON_STATES state : BUTTON_STATES.values()) {
                if (state.value == value) {
                    return state;
                }
            }
            return null;
        }
    }
}