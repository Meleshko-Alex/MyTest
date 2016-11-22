
package edu.mytest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OneReviewModel {

    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("text")
    @Expose
    private String text;

    /**
     * 
     * @return
     *     The rate
     */
    public Integer getRate() {
        return rate;
    }

    /**
     *
     * @param rate
     *     The rate
     */
    public void setRate(Integer rate) {
        this.rate = rate;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

}
