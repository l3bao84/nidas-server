package com.LeBao.sales.DTO;

public class ReviewDTO {

    private Long reviewDTOId;
    private int rating;
    private String content;

    public ReviewDTO() {
    }

    public Long getReviewDTOId() {
        return reviewDTOId;
    }

    public void setReviewDTOId(Long reviewDTOId) {
        this.reviewDTOId = reviewDTOId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
