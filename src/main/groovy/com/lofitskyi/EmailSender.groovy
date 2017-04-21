package com.lofitskyi

interface EmailSender {
    void sendReport(String path, def errors, def price, def availability)
}