package condo.model;

import java.util.ArrayList;

public class MailList{

    private ArrayList<Mail> mails;

    public MailList() {
        this.mails = new ArrayList<>();
    }

    public void add(Mail mail) {
        mails.add(mail);
    }

    public ArrayList<Mail> toList() {
        return mails;
    }
}
