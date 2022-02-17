package Jocabook.objects;

import Jocabook.model.db;

public class interactiveaccount extends account {
    public String path = db.generate_String();


    public interactiveaccount(){

    }
    public interactiveaccount(account account) {
        super.ime = account.ime;
        super.mail = account.mail;
        super.password = account.password;
        super.Profile = account.Profile;
    }

    public void add_to_db() {
        db.adduser(this);
    }

    public void change_password(String newpass) {
        db.change_password(newpass, this);
    }

}
