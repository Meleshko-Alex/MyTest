
package edu.mytest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product")
    @Expose
    private Integer product;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("created_by")
    @Expose
    private CreatedBy createdBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @return
     *     The product
     */
    public Integer getProduct() {
        return product;
    }

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
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @return
     *     The createdBy
     */
    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

}
