package com.happyface.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;
import com.happyface.R;
import com.happyface.activities.LogInActivity;
import com.happyface.models.login_models.ErrorLoginResponse;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;

public class StaticMembers {

    public static final String domain = "https://insightclosely.com/happy/public/api/";
    public static final String CAT = "categories";
    public static final String CATEGORY = "category";
    public static final String CATEGORY_ID = "category_id";
    public static final String USERS = "users";
    public static final String USER = "user";
    public static final String FAV = "fav";
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String DATE_FORMAT_VIEW = "yyyy-M-dd hh:mm";
    public static final String SIGN_UP = "sign_up";
    public static final String PIN_CODE = "pincode";
    public static final String PIN_CODE_TOKEN = "pincodetoken";
    public static final String PRODUCT = "product";
    public static final String NAME = "name";
    public static final String SEARCH = "search";
    public static final String SUB_CATEGORY = "subcategory";
    public static final String ID = "id";
    public static final String WISHLIST = "wishlist";
    public static final String WISHLIST_ACTION = "wishlist/action/{id}";
    public static final String SORT = "sort";
    public static final String AMOUNT = "amount";
    public static final String CART = "cart";
    public static final String EDIT_CART = "cart/store";
    public static final String QUANTITY = "quantity";
    public static final String PRODUCT_ID = "product_id";
    public static final String DELETE_CART = "cart/delete/{id}";
    public static final String VIDEO = "video";
    public static final String IMAGE = "image";
    public static final String STOP = "stop";
    public static final String ACTION = "action";
    public static final String SLIDER = "slider";
    public static final String EDIT_NAME = "profile/edit";
    public static final String CONTACT = "contact";
    public static final String MESSAGE = "message";
    public static final String GIFT = "gift";
    public static final String CODE = "code";
    public static final String MAX_AMOUNT = "max";
    public static final String UNIT_SIZE = "unitsize";
    public static final String COLOR = "color";
    public static final String SUB_CATEGORY_ID = "subcategory_id";
    public static final String OFFERS = "offer";
    public static final String DISCOUNT = "discount";
    public static final String GIFT_GET = "gifts/all";
    public static final String GIFT_COVER = "gifts/cover";
    public static final String GIFT_ADDITION = "gifts/additional";
    public static final String GIFT_MESSAGE = "gifts/message";
    public static final String GIFT_COVERS_ID = "gifts_covers";
    public static final String GIFT_MESSAGE_ID = "gift_messegs";
    public static final String GIFT_MESSAGE_FROM = "gift_messegs_from";
    public static final String GIFT_MESSAGE_TO = "gift_messegs_to";
    public static final String GIFT_MESSAGE_TEXT = "gift_messegs_msg";
    public static final String GIFT_ADDITION_IDS = "gifts_additionals[]";
    public static final String GIFT_QUANTITY_ADDITION = "quantity_additionals[]";
    public static final String GOV = "governmant";
    public static final String AREA = "area";
    public static final String BLOCK = "block";
    public static final String STREET = "street";
    public static final String AVENUE = "avenue";
    public static final String REMARK_ADDRESS = "remarkaddress";
    public static final String HOUSE_NO = "house_no";
    public static final String MALE = "male";
    public static final String FEMALE = "female";
    public static final int LOCATION_CODE = 5544;
    private static final String LANGUAGE = "language";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String LAT = "latitude";
    public static final String LONG = "longitude";
    public static final String LON = "lon";
    public static final String LAT_ = "lat";
    public static final String ADDRESS = "address";
    public static final String TOKEN = "token";
    public static final String PAGE = "page";
    public static final String register = "register";
    public static final String getPinCode = "user/getPinCode";
    public static final String login = "login";
    public static final String verifyPinCode = "user/testpincode";
    public static final String reset = "user/password";

    ////////////////// change Dots////////////////////
    public static void changeDots(int currentPage, int count, LinearLayout dotsLayout, Context context) {
        ImageView[] dots = new ImageView[count];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(context);
            dots[i].setImageResource(R.drawable.bullet_unselected);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > currentPage)
            dots[currentPage].setImageResource(R.drawable.bullet_selected);
    }

    /////////////////Dates converter/////////////////////
    public static String changeDateFromIsoToView(String dateFrom) {
        SimpleDateFormat sdf = new SimpleDateFormat(StaticMembers.ISO_DATE_FORMAT, Locale.US);
        SimpleDateFormat sdfTo = new SimpleDateFormat(StaticMembers.DATE_FORMAT_VIEW, Locale.getDefault());
        try {
            return sdfTo.format(sdf.parse(dateFrom));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFrom;
    }

    public static <T extends Activity> void startActivityOverAll(T activity, Class<?> destinationActivity) {
        Intent intent = new Intent(activity, destinationActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finishAffinity();
    }


    public static <T extends Activity> void startActivityOverAll(Intent intent, T activity) {
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finishAffinity();
    }

    public static <T extends View> void hideKeyboard(T view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static <A extends Activity> void hideKeyboard(A activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    //////////////////////Password validation/////////////////////
    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean CheckValidationPassword(TextInputEditText editText, final TextInputLayout textInputLayout, final String errorMessage, final String errorMessage2) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    textInputLayout.setError(errorMessage);
                    textInputLayout.setErrorEnabled(true);
                } else {
                    if (!isValidPassword(s.toString())) {

                        textInputLayout.setError(errorMessage2);
                        textInputLayout.setErrorEnabled(true);
                    } else {
                        textInputLayout.setErrorEnabled(false);
                    }
                }

            }
        });
        if (TextUtils.isEmpty(editText.getText())) {
            textInputLayout.setError(errorMessage);
            textInputLayout.setErrorEnabled(true);
            return false;
        } else {
            if (!isValidPassword(editText.getText().toString())) {
                textInputLayout.setError(errorMessage2);
                textInputLayout.setErrorEnabled(true);
                return false;
            } else {
                textInputLayout.setErrorEnabled(false);
                return true;
            }
        }
    }
    //////////////////////Visiblity with Animation/////////////////////

    public static <V extends View> void makeVisible(V layout) {
        layout.setVisibility(View.VISIBLE);
        layout.setAlpha(0.0f);
        layout.animate()
                .translationY(0)
                .alpha(1.0f)
                .setListener(null);
    }

    public static <V extends View> void makeGone(V layout) {
        layout.setVisibility(View.GONE);
    }
    //////////////////////Toasts/////////////////////




    public static void toastMessageShortSuccess(Context context, String messaage) {
//        if (toast != null)
//            toast.cancel();
//        toast = Toast.makeText(context, messaage, Toast.LENGTH_SHORT);
//        toast.show();
        TastyToast.makeText(context,messaage,TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
    }

    public static void toastMessageShortFailed(Context context, String messaage) {
//        if (toast != null)
//            toast.cancel();
//        toast = Toast.makeText(context, messaage, Toast.LENGTH_SHORT);
//        toast.show();
        TastyToast.makeText(context,messaage,TastyToast.LENGTH_SHORT,TastyToast.ERROR);

    }


//
//    public static void toastMessageShort(Context context, CharSequence messaage) {
//        if (toast != null)
//            toast.cancel();
//        toast = Toast.makeText(context, messaage, Toast.LENGTH_SHORT);
//        toast.show();
//    }
//
//    public static void toastMessageLong(Context context, int messaage) {
//        if (toast != null)
//            toast.cancel();
//        toast = Toast.makeText(context, messaage, Toast.LENGTH_LONG);
//        toast.show();
//    }
//
//    public static void toastMessageLong(Context context, String messaage) {
//        if (toast != null)
//            toast.cancel();
//        toast = Toast.makeText(context, messaage, Toast.LENGTH_LONG);
//        toast.show();
//    }

    public static boolean CheckTextInputEditText(TextInputEditText editText, final TextInputLayout textInputLayout, final String errorMessage) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    editText.setError(errorMessage);
                    textInputLayout.setErrorEnabled(true);
                } else {
                    editText.setError(null);
                }
            }
        });
        if (TextUtils.isEmpty(editText.getText())) {
            editText.setError(errorMessage);
            textInputLayout.setErrorEnabled(true);
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public static String getLanguage(Context context) {
        return PrefManager.getInstance(context).getStringData(LANGUAGE);
    }

    public static void setLanguage(Context context, String langStr) {
        PrefManager.getInstance(context).setStringData(LANGUAGE, langStr);
    }

    public static void changeLocale(Context context, String langStr) {
        setLanguage(context, langStr);
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(langStr.toLowerCase()));
        res.updateConfiguration(conf, dm);
        Locale locale = context.getResources().getConfiguration().locale;
        Locale.setDefault(locale);
    }

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("yyyy-MM-dd hh:mma", Locale.getDefault()).format(calendar.getTime());

    }


    public static String getDate(Calendar calendar) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.getTime());
    }

    public static void openLogin(Context activity) {
        Intent intent = new Intent(activity, LogInActivity.class);
        intent.putExtra(StaticMembers.ACTION, true);
        activity.startActivity(intent);
    }

    public static void checkLoginRequired(ResponseBody errorBody, Context context) {
        try {
            ErrorLoginResponse errorLoginResponse = null;
            if (errorBody != null) {
                errorLoginResponse = new GsonBuilder().create().fromJson(errorBody.string(), ErrorLoginResponse.class);
                if (errorLoginResponse != null) {
                    if (errorLoginResponse.getError() != null && !errorLoginResponse.getError().isEmpty() && errorLoginResponse.getError().toLowerCase().contains("token")) {
                        PrefManager.getInstance(context).removeToken();
                        openLogin(context);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
