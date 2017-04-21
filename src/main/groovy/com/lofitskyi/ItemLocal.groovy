package com.lofitskyi

class ItemLocal {
    String name
    String ebayLink
    String amzLink
    String sku
    BigDecimal ebayPrice
    BigDecimal amzPrice

    ItemLocal(String name, String ebayLink, String amzLink, String sku, BigDecimal ebayPrice, BigDecimal amzPrice) {
        this.name = name
        this.ebayLink = ebayLink
        this.amzLink = amzLink
        this.sku = sku
        this.ebayPrice = ebayPrice
        this.amzPrice = amzPrice
    }
}
