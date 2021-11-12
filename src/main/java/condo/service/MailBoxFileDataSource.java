package condo.service;

import condo.model.Mail;
import condo.model.MailList;

import java.io.IOException;

public interface MailBoxFileDataSource {

    void removeMail(Mail mail) throws IOException;
    MailList getMailData();

}
