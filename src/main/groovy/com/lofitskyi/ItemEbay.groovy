package com.lofitskyi

class ItemEbay {
    String name
    String ebayLink
    String amzLink
    String sku
    String available
    BigDecimal docPrice
    String ebayPrice
    BigDecimal amzPrice

    ItemEbay(String name, String ebayLink, String amzLink, String sku, String available, BigDecimal docPrice, String ebayPrice, BigDecimal amzPrice) {
        this.name = name
        this.ebayLink = ebayLink
        this.amzLink = amzLink
        this.sku = sku
        this.available = available
        this.docPrice = docPrice
        this.ebayPrice = ebayPrice
        this.amzPrice = amzPrice
    }

    ItemEbay(ItemLocal itemLocal, String available, String ebayPrice){
        this(itemLocal.name, itemLocal.ebayLink, itemLocal.amzLink, itemLocal.sku, available, itemLocal.ebayPrice, ebayPrice, itemLocal.amzPrice)
    }
}
