package com.LeBao.sales.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProReq {

    private String name;
    private String price;
    private String stock;
    private String piece;
    private String category;
    private String desc;

    public void trim() {
        this.name = this.name.trim();
        this.price = this.price.trim();
        this.stock = this.stock.trim();
        this.piece = this.piece.trim();
        this.category = this.category.trim();
        this.desc = this.desc.trim();
    }
}
