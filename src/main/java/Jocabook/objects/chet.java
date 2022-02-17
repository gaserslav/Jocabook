package Jocabook.objects;


import Jocabook.model.db;

public class chet {

    public int id;
    public int id1;
    public int id2;
    public String massage;

    public int differentID(int id) {
        if (id == id1) {
            return id2;
        } else if (id2 == id) {
            return id1;
        } else {
            return 0;
        }

    }

    public account getaccount1() {
        return db.get_account_by_id(id1);
    }

    public account getaccount2() {
        return db.get_account_by_id(id2);
    }


}


