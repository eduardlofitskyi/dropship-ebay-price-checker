package com.lofitskyi

import org.apache.commons.lang3.StringUtils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

class EbayPriceChecker {

    def getPrice(List<ItemLocal> items){
        WebDriver driver = new HtmlUnitDriver()

        def error = []
        def wrongPrice = []
        def fewAvailable = []

        for (ItemLocal it: items){
            driver.get(it.ebayLink)

            String price
            String amount = ''
            try {
                amount = driver.findElementById('qtySubTxt').getText()
                price = driver.findElementById('prcIsum').getText()
            }catch (Exception e){
                try {price = driver.findElementById('mm-saleDscPrc').getText()}
                catch (Exception ex){
                    error.add(it)
//                    println ("[ERROR] \t${it.name}: COULD NOT READ PROPERTIES")
                    continue
                }
            }

            price = BigDecimal.valueOf(Double.parseDouble(price.replaceAll('([^\\d.]|\\B\\.|\\.\\B)+', '')))

            BigDecimal priceBD = BigDecimal.valueOf(Double.parseDouble(price))

            if (priceBD.compareTo(it.ebayPrice) > 0) {
                wrongPrice.add(new ItemEbay(it, amount, price))
//                println("[WARNING-PRICE]\t${it.name}: \$${it.ebayPrice.toString()}>>${price} ")
            }

            if (amount != 'More than 10 available' && amount != 'Limited quantity available' && (!StringUtils.isNumeric(amount.charAt(1).toString()) && !amount.contains(','))) {
                fewAvailable.add(new ItemEbay(it, amount, price))
//                println("[WARNING-AVALIABLE]\t${it.name}: [${amount}]")
            }
        }

        driver.close()
        return [error, wrongPrice, fewAvailable]
    }
}
