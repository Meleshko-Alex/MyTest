package edu.mytest;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import edu.mytest.models.OneReviewModel;
import edu.mytest.models.ReviewAnswerModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends Activity {
    private EditText et_review;
    private SeekBar seekBar;
    private TextView tv_rate;
    private Button btn_post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_review);

        et_review = (EditText)findViewById(R.id.et_review);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        tv_rate = (TextView)findViewById(R.id.tv_rate);
        btn_post = (Button)findViewById(R.id.btn_post);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_rate.setText(String.valueOf(seekBar.getProgress()));
            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OneReviewModel review = new OneReviewModel();
                review.setRate(Integer.parseInt(tv_rate.getText().toString()));
                review.setText(et_review.getText().toString());

                Call<ReviewAnswerModel> call = LoginActivity.service.setReview(review);

                call.enqueue(new Callback<ReviewAnswerModel>() {
                    @Override
                    public void onResponse(Call<ReviewAnswerModel> call, Response<ReviewAnswerModel> response) {
                        ReviewAnswerModel answer = response.body();
                        if (response.code() != 200) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
                            builder.setMessage(response.message().toString() + "\n" + response.code()
                                    + "\n" + "Попытайтесь позже")
                                    .setTitle("Ошибка сервера")
                                    .setCancelable(false)
                                    .setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                    finish();
                                                }
                                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewAnswerModel> call, Throwable t) {

                    }
                });
            }
        });
    }
}
