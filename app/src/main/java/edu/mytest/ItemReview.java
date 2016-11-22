package edu.mytest;

public class ItemReview {
    private String text;
    private String rate;

    public ItemReview(String text , String rate){
        this.text = text;
        this.rate = rate;
    }

    //Всякие гетеры и сеттеры
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getRate() {
        return rate;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }
}
