package com.lofitskyi

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord

class Main {
    static void main(String[] args) {
        System.setProperty('webdriver.chrome.driver', 'chromedriver')

        def ebay = new EbayPriceChecker()
        def itemList = []

        Reader input = new FileReader("/home/NIX/lofitskyi/Desktop/ETA.csv")
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().withQuote('"' as char).parse(input)
        for (CSVRecord record : records) {
            itemList.add(new ItemLocal(record.get(0), record.get(6), record.get(7), record.get(8), BigDecimal.valueOf(Double.parseDouble(record.get(1))), BigDecimal.valueOf(Double.parseDouble(record.get(1)))))
        }

        def error
        def price
        def available
        (error, price, available) = ebay.getPrice(itemList)

        def file = new File('report.csv')
        file.delete()
        file << "ERRORS (CHECK IT MANUALLY)\nSKU,Ebay LINK\n"
        error.each { ItemLocal it->
            file << "${it.sku},${it.ebayLink}\n"
        }

        file << "\nPRICE RISES\nSKU,OLD PRICE,NEW PRICE,LINK\n"
        price.each { ItemEbay it->
            file << "${it.sku},${it.docPrice},${it.ebayPrice},${it.ebayLink}\n"
        }

        file << "\nFEW IN STOCK\nSKU,Ebay STOCK,LINK\n"
        available.each { ItemEbay it->
            file << "${it.sku},${it.available},${it.ebayLink}\n"
        }

        EmailSender sender = new EmailSenderApache()
        sender.sendReport(file.path, error, price, available)
    }
}
