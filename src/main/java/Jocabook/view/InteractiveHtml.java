package Jocabook.view;

import Jocabook.model.db;
import Jocabook.objects.account;
import Jocabook.objects.chet;
import Jocabook.objects.post;
import joca.JocaArray;

import java.io.InputStream;

public class InteractiveHtml {


    public static String Account(account positive, account target, int ofset) {
        String s = null;
        try {
            InputStream fis = InteractiveHtml.class.getResourceAsStream("/static/account/account.html");
            s = new String(fis.readAllBytes());

        } catch (Exception efd) {
        }

        s = s.replace("{target_profile}", target.Profile).replace("{target_ime}", target.ime);
        s = s.replace("{positive_profile}", positive.Profile);
        s = s.replace("{positive_ime}", positive.ime);
        s = s.replace("{positive_id}", Integer.toString(positive.id));
        s = s.replace("{target_id}", Integer.toString(target.id));


        String chetovanje = "";
        JocaArray<chet> kanali = db.get_cannels(positive.id);
        for (int i = 0; i < kanali.length(); i++) {
            account acc = db.get_account_by_id(kanali.get(i).differentID(positive.id));
            chetovanje += "<div id='kanal'onclick=\"pick_chet(" + acc.id + ")\"> <img style='height:6vh;float:left' src='" + acc.Profile + "' > " + "<div style=''>" + acc.ime + "</div>" + "</div><br>";
        }


        s = s.replace("{positive_chets}", chetovanje);


        String posts = "";
        JocaArray<post> post = db.getposts(ofset, target);
        for (int i = 0; i < post.length(); i++) {

            posts += "<div class='jedanpost'><div class='posttitle'>" + post.get(i).title + "</div>";
            posts += "<div class='postautor'>" + post.get(i).autor + "</div>";
            posts += "<div class='postcontent'>" + post.get(i).content + "</div>";
            posts += "</div>";

        }


        s = s.replace("{target_posts}", posts);
        s = s.replace("{offset}", String.valueOf(ofset + 4));


        return s;
    }

    public static String accountmade() {
        String s = null;
        try {
            InputStream fis = InteractiveHtml.class.getResourceAsStream("/static/account/make.html");
            s = new String(fis.readAllBytes());

        } catch (Exception efd) {
        }
        return s;
    }

    public static String changepassword() {
        String s = null;
        try {
            InputStream fis = InteractiveHtml.class.getResourceAsStream("/static/account/change.html");
            s = new String(fis.readAllBytes());

        } catch (Exception efd) {
        }
        return s;
    }

    public static String forgot_password() {
        byte[] b = null;
        try {
            InputStream is = InteractiveHtml.class.getResourceAsStream("/static/login/forgot_password.html");
            b = is.readAllBytes();
        } catch (Exception eee) {
        }

        return new String(b);
    }

    public static String gotomail() {
        String s = null;
        try {
            InputStream fis = InteractiveHtml.class.getResourceAsStream("/static/account/gotomail.html");
            s = new String(fis.readAllBytes());

        } catch (Exception efd) {
        }
        return s;
    }

    public static String login() {
        String s = null;
        try {
            InputStream fis = InteractiveHtml.class.getResourceAsStream("/static/login/login.html");
            s = new String(fis.readAllBytes());

        } catch (Exception efd) {
        }
        return s;

    }

    public static String makingpost() {
        String s = null;
        try {
            InputStream fis = InteractiveHtml.class.getResourceAsStream("/static/post/post.html");
            s = new String(fis.readAllBytes());

        } catch (Exception efd) {
        }
        return s;

    }

    public static String not_login_account(account target, int ofset) {
        String s = null;
        try {
            InputStream fis = InteractiveHtml.class.getResourceAsStream("/static/account/accountu.html");
            s = new String(fis.readAllBytes());
            fis.close();
        } catch (Exception efd) {
        }

        String posts = "";
        JocaArray<post> post = db.getposts(ofset, target);
        for (int i = 0; i < post.length(); i++) {

            posts += "<div class='jedan-post'><div class='post-title'>" + post.get(i).title + "</div>";
            posts += "<div><div class='post-autor'>" + post.get(i).autor + "</div>";
            posts += "<div class='post-content'>" + post.get(i).content + "</div>";
            posts += "</div>";

        }

        s = s.replace("{offset}", String.valueOf(ofset + 4));
        s = s.replace("{target_posts}", posts);


        return s;
    }

    public static String signup() {
        String s = null;
        try {
            InputStream fis = InteractiveHtml.class.getResourceAsStream("/static/signup/sign.html");
            s = new String(fis.readAllBytes());

        } catch (Exception efd) {
        }
        return s;

    }

    public static String trazi(account positive, String pretraga, int ofset) {
        String s = null;
        try {
            InputStream fis = InteractiveHtml.class.getResourceAsStream("/static/account/account.html");
            s = new String(fis.readAllBytes());

        } catch (Exception efd) {
        }

        s = s.replace("{target_profile}", "").replace("{target_ime}", "");
        s = s.replace("{positive_profile}", positive.Profile);
        s = s.replace("{positive_ime}", positive.ime);
        s = s.replace("{positive_id}", Integer.toString(positive.id));
        s = s.replace("{target_id}", "");


        String chetovanje = "";
        JocaArray<chet> kanali = db.get_cannels(positive.id);
        for (int i = 0; i < kanali.length(); i++) {
            account acc = db.get_account_by_id(kanali.get(i).differentID(positive.id));
            chetovanje += "<div id='kanal'onclick=\"pick_chet(" + acc.id + ")\"> <img style='height:6vh;float:left' src='" + acc.Profile + "' > " + "<div style=''>" + acc.ime + "</div>" + "</div><br>";
        }

        s = s.replace("{positive_chets}", chetovanje);


        String posts = "";
        JocaArray<post> post = db.get_post_by_search(pretraga, ofset);
        for (int i = 0; i < post.length(); i++) {

            posts += "<div class='jedanpost'><div class='posttitle'>" + post.get(i).title + "</div>";
            posts += "<div class='postautor'>" + post.get(i).autor + "</div>";
            posts += "<div class='postcontent'>" + post.get(i).content + "</div>";
            posts += "</div>";

        }

        post = null;
        JocaArray<account> accounts = db.get_accounts_by_search(pretraga, ofset);

        for (int i = 0; i < accounts.length(); i++) {

            posts += "<div style='text-align:center;'><a href=\"/accounts/" + accounts.get(i).ime + "\">" + "<img style='height:3vh;' src=\"" + accounts.get(i).Profile + "\">" + accounts.get(i).ime + "</a></div>";

        }


        s = s.replace("{target_posts}", posts);
        s = s.replace("{offset}", String.valueOf(ofset + 4));


        return s;
    }


}
