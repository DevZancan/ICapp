package infocamere.it.icapp.home;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import infocamere.it.icapp.R;

public class HelperFragment extends Fragment {

    RelativeLayout layoutHelper;
    CardView cardViewHelper;
    TextView textViewHelper;
    SharedPreferences sharedPreferences;
    Runnable characterAdder;
    Handler handler;
    String testoHelper;
    int index = 0;
    Button buttonClose;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showcase, container, false);
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        layoutHelper = view.findViewById(R.id.layoutHelper);
        cardViewHelper = view.findViewById(R.id.cardViewHelper);
        textViewHelper = view.findViewById(R.id.textViewHelper);
        buttonClose = view.findViewById(R.id.buttonClose);

        if (sharedPreferences.getBoolean("firstOpen", true)) {
            testoHelper = getResources().getString(R.string.text_helper_home);
            cardViewHelper.setVisibility(View.GONE);
            handler = new Handler();
            characterAdder = new Runnable() {
                @Override
                public void run() {
                    textViewHelper.setText(testoHelper.subSequence(0, index++) + "_");
                    if (index <= testoHelper.length()) {
                        handler.postDelayed(characterAdder, 70);
                    }
                    if (index == testoHelper.length() - 1) {
                        cardViewHelper.setVisibility(View.VISIBLE);
                        Handler handler2 = new Handler();
                        handler2.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                ObjectAnimator animation = ObjectAnimator.ofFloat(cardViewHelper, "translationY", 500f);
                                animation.setDuration(1000);
                                animation.start();
                                animation.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("firstOpen", false);
                                        editor.commit();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });

                            }
                        }, 1000);
                    }
                }
            };
            characterAdder.run();

            buttonClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutHelper.setVisibility(View.GONE);
                }
            });
        } else {
            layoutHelper.setVisibility(View.GONE);
        }

        return view;
    }
}
