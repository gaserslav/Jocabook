package Jocabook.controllers;

import Jocabook.constants;
import Jocabook.model.db;
import Jocabook.objects.Picture;
import Jocabook.objects.account;
import Jocabook.objects.interactiveaccount;
import Jocabook.objects.post;
import Jocabook.view.InteractiveHtml;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@RestController
public class restcon {

    @GetMapping("/change_password/{path}")
    public String change(@PathVariable String path) {

        for (int i = 0; i < db.accounti_za_menjanje.length(); i++) {
            if (path.equals(db.accounti_za_menjanje.get(i).path)) {

                return InteractiveHtml.changepassword();
            }

        }

        return new String(_404());
    }


    @GetMapping("/404")
    public byte[] _404() {

        InputStream is = restcon.class.getResourceAsStream("/static/error/404.html");
        byte[] b = null;
        try {
            b = is.readAllBytes();
        } catch (Exception ff) {
        }


        return b;
    }


    @PostMapping("/password_changed")
    public String changed(HttpServletRequest req) {
        String referer = req.getHeader("referer");
        String password = req.getParameter("password");
        referer = referer.substring(referer.indexOf("change_password/") + 16, referer.length());

        for (int i = 0; i < db.accounti_za_menjanje.length(); i++) {

            if (db.accounti_za_menjanje.get(i).path.equals(referer)) {

                System.out.println("sifra promenjena  za " + db.accounti_za_menjanje.get(i).ime + " sa " + db.accounti_za_menjanje.get(i).password);
                db.accounti_za_menjanje.get(i).change_password(password);
                System.out.println("na " + password);
            }

        }
        return "password changed";
    }

    @GetMapping("/change_password")
    public String changepassword(HttpServletRequest req) {

        account account = readCookies(req);
        interactiveaccount m = new interactiveaccount(account);
        db.accounti_za_menjanje.appand(m);
        db.accounti_za_menjanje.pop_after_time(m, db.Vreme_do_brisanja);
        System.out.println(account.mail + "\n" + account.ime + "\n" + account.password);
        db.mailsander.SendMail(account.mail, "change your password", " here is the link for you to change password \n" + constants.link_of_this_syte + "/change_password/" + m.path);
        return InteractiveHtml.gotomail();
    }

    private account readCookies(HttpServletRequest req) {

        Cookie[] cookies = req.getCookies();

        if (cookies == null) {
            return null;
        }

        String ime = null;
        String password = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user")) {
                ime = cookie.getValue();
            }

            if (cookie.getName().equals("password")) {
                password = cookie.getValue();
            }
        }
        account account;
        if (ime == null || password == null) {
            return null;
        } else {
            account = db.get_account_by_username_and_password(ime, password);
        }

        return account;
    }

    @GetMapping("/")
    public String def(HttpServletRequest req) {

        String offsets = req.getParameter("offset");
        int offset = 0;
        try {
            offset = Integer.parseInt(offsets);
        } catch (Exception ee) {
        }

        if (offset == 0) {
            offset = 10;
        }

        account account = readCookies(req);
        if (account == null) {
            return InteractiveHtml.login();
        }
        return InteractiveHtml.Account(account, account, offset);

    }

    @PostMapping("/")
    public String defpost(HttpServletRequest req) {


        //making post
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        account make = readCookies(req);
        if (make != null) {

            if (title != null && content != null) {
                //process of making post
                post p = new post();
                p.title = title;
                p.content = content;
                p.vreme = new Date();
                p.autor = make.id;

                db.addPost(p);

            }

            return InteractiveHtml.Account(make, make, 4);

        } else {
            return InteractiveHtml.login();
        }


    }

    @PostMapping("/forgotpassword")
    public String forgotpassword_post(HttpServletRequest req) {

        account acc = db.get_account_by_mail(req.getParameter("mail"));

        if (acc == null) {
            return forgotpassword(req);
        }

        System.out.println("forgot password" + acc.mail);
        interactiveaccount m = new interactiveaccount(acc);
        db.accounti_za_menjanje.appand(m);
        db.accounti_za_menjanje.pop_after_time(m, db.Vreme_do_brisanja);
        db.mailsander.SendMail(acc.mail, "change your password", " here is the link for you to change password \n" + constants.link_of_this_syte + "/change_password/" + m.path);


        return InteractiveHtml.gotomail();

    }

    @GetMapping("/forgotpassword")
    public String forgotpassword(HttpServletRequest req) {

        account acc = readCookies(req);

        if (acc != null) {
            return InteractiveHtml.Account(acc, acc, 0);
        }


        return InteractiveHtml.forgot_password();

    }

    @GetMapping("/pics/{title}")
    public @ResponseBody
    byte[] getImage(@PathVariable String title) throws IOException {

        Picture pic = db.getPicturebytitle(title);

        return pic.raw;
    }

    @GetMapping("/accounts/{ime}")
    public String ime(@PathVariable String ime, HttpServletRequest req) {

        String offsets = req.getParameter("offset");
        int offset = 0;
        try {
            offset = Integer.parseInt(offsets);
        } catch (Exception ee) {
        }

        if (offset == 0) {
            offset = 4;
        }

        account pozitive = readCookies(req);
        account target = db.get_account_by_username(ime);
        if (pozitive != null) {

            //client is login
            if (target != null) {
                return InteractiveHtml.Account(pozitive, target, offset);
            } else {
                return new String(_404());
            }

        } else {

            //client isnt login
            if (target != null) {
                return InteractiveHtml.not_login_account(db.get_account_by_username(ime), offset);
            } else {
                return new String(_404());
            }
        }

    }

    @PostMapping("/makeaccount")
    public String makeaccount(HttpServletRequest req) {
        String user = req.getParameter("user");
        String password = req.getParameter("password");
        String mail = req.getParameter("mail");

        if (user != null && password != null && mail != null) {

            //process of making account


            interactiveaccount account = new interactiveaccount();
            account.ime = user;
            account.mail = mail;
            account.password = password;


            db.accounti_za_pravljenje.appand(account);
            db.accounti_za_pravljenje.pop_after_time(account, db.Vreme_do_brisanja);
            db.mailsander.SendMail(account.mail, "Claim your account", "this is link of your account " + constants.link_of_this_syte + "/make_account/" + account.path);


            return InteractiveHtml.gotomail();
        }

        return InteractiveHtml.login();
    }

    @GetMapping("/make_account/{path}")
    public byte[] path(@PathVariable String path) {

        for (int i = 0; i < db.accounti_za_pravljenje.length(); i++) {

            if (path.equals(db.accounti_za_pravljenje.get(i).path)) {

                db.accounti_za_pravljenje.get(i).add_to_db();
                return InteractiveHtml.accountmade().getBytes();

            }

        }
        return _404();
    }

    @GetMapping("/posting")
    public String posting() {
        return InteractiveHtml.makingpost();

    }

    @GetMapping("/trazi")
    public String trazi(HttpServletRequest req) {
        String t = req.getParameter("trazi");
        account acc = readCookies(req);
        if (acc == null) {
            return InteractiveHtml.login();
        }

        String offsets = req.getParameter("offset");
        int offset = 0;
        try {
            offset = Integer.parseInt(offsets);
        } catch (Exception ee) {
        }

        if (offset == 0) {
            offset = 4;
        }

        return InteractiveHtml.trazi(acc, t, offset);
    }
}
