package com.lofitskyi

import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.EmailAttachment
import org.apache.commons.mail.MultiPartEmail

import java.time.LocalDateTime

class EmailSenderApache implements EmailSender {
    @Override
    void sendReport(String path, def errors, def price, def availability) {

        String dateTime = LocalDateTime.now().toString()

        EmailAttachment attachment = new EmailAttachment()
        attachment.setPath(path)
        attachment.setDisposition(EmailAttachment.ATTACHMENT)
        attachment.setDescription("Report for ${dateTime}")
        attachment.setName("report_${dateTime}.csv")

        // Create the email message
        MultiPartEmail email = new MultiPartEmail()
        email.setHostName("smtp.googlemail.com")
        email.setSmtpPort(465)
        email.setAuthenticator(new DefaultAuthenticator("eduard.sell9@gmail.com", "qwerty12-9"))
        email.setSSLOnConnect(true)
        email.addTo("eduard.sell9@gmail.com", "Eduard Sell")
        email.setFrom("eduard.sell9@gmail.com", "Me")
        email.setSubject("Amz Report: ${dateTime}")
        email.setMsg("Here is the attachment with report\n[ERRORS]: ${errors.size()}\n[PRICE]: ${price.size()}\n[AVAILIBILITY]: ${availability.size()}")

        // add the attachment
        email.attach(attachment)

        // send the email
        email.send()
    }
}
