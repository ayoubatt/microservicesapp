package com.org.emprunt.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookDTO {
    public BookDTO() {}
    
    private Long id;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @JsonProperty("titre")
    private String title;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
}
