
package edu.mytest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewAnswerModel {

    @SerializedName("review_id")
    @Expose
    private Integer reviewId;

    /**
     * 
     * @return
     *     The reviewId
     */
    public Integer getReviewId() {
        return reviewId;
    }

}
