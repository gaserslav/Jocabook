package Jocabook.model;

import Jocabook.objects.*;
import joca.JocaArray;
import jte.Hasovanje;
import jte.MailsandER;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Random;

public class db {


    public static int Vreme_do_brisanja = 1000 * 60 * 5;
    public static JocaArray<interactiveaccount> accounti_za_menjanje = new JocaArray<>();
    public static JocaArray<interactiveaccount> accounti_za_pravljenje = new JocaArray<>();
    public static String chets_db = "chets";
    public static MailsandER mailsander = new MailsandER("www.kurwacar123@gmail.com", "jocacar.123");
    public static String password_db = "";
    public static String posts_db = "posts";
    public static String user_db = "gaser";
    public static String users_db = "users";
    static Connection conection_users_db;
    private static char[] alfabet_for_generating_rundom_string = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890".toCharArray();
    private static Connection conection_chets_db;
    private static Connection conection_posts_db;
    private static String sql;

    public static void addPost(post post) {
        try {
            final String sql = "insert into posts(title,content,autor,datum) values('" + post.title + "','" + post.content + "'," + post.autor + ",'" + tosqldatum(post.vreme) + "');";
            PreparedStatement stat = conection_posts_db.prepareStatement(sql);
            stat.execute();
        } catch (Exception fv) {

        }
    }

    public static String tosqldatum(Date d) {

        //ovako da izgleda '2020-01-01 15:10:10'
        return ((1900 + d.getYear()) + "-" + d.getMonth() + "-" + d.getDay() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getDay());
    }

    public static void adduser(account acc) {
        try {
            String hashedpass = Hasovanje.hashpoSHA256(acc.password.getBytes());
            PreparedStatement statement = conection_users_db.prepareStatement("insert into users (ime,mail,passwords,profile_pic) values ('" + acc.ime + "','" + acc.mail + "','" + hashedpass + "','/pics/anonimus');");
            statement.execute();
        } catch (Exception eee) {
        }
    }

    public static void change_password(String newpass, account account) {
        try {
            String hashedpass = Hasovanje.hashpoSHA256(newpass.getBytes());
            PreparedStatement statement = conection_users_db.prepareStatement("update users set passwords='" + hashedpass + "' where ime='" + account.ime + "' AND mail='" + account.mail + "' AND passwords='" + account.password + "';");
            statement.execute();
        } catch (Exception eee) {
        }
    }

    public static String generate_String() {
        return generate_String(20, 10);
    }

    public static String generate_String(int max, int min) {
        Random r = new Random();
        int lenght = r.nextInt(max - min) + min;
        String ret = "";
        for (int i = 0; i < lenght; i++) {
            ret += alfabet_for_generating_rundom_string[r.nextInt(alfabet_for_generating_rundom_string.length)];
        }
        return ret;
    }

    public static void generate_db_con() {
        try {

            conection_users_db = DriverManager.getConnection("jdbc:mysql://localhost:3306/{gas}?autoReconnect=true&useSSL=false".replace("{gas}", users_db), user_db, password_db);
            conection_posts_db = DriverManager.getConnection("jdbc:mysql://localhost:3306/{gas}?autoReconnect=true&useSSL=false".replace("{gas}", posts_db), user_db, password_db);
            conection_chets_db = DriverManager.getConnection("jdbc:mysql://localhost:3306/{gas}?autoReconnect=true&useSSL=false".replace("{gas}", chets_db), user_db, password_db);
        } catch (Exception eee) {
            System.out.println("cant connect to database \n" + eee);
            System.exit(69);
        }
    }

    public static Picture getPicturebytitle(String title) {
        Picture pic = new Picture();
        pic.title = title;
        try {
            PreparedStatement statement = db.conection_users_db.prepareStatement("SELECT * FROM pictures WHERE title='" + title + "'");
            ResultSet res = statement.executeQuery();
            res.next();
            InputStream is = res.getBinaryStream("raw");
            pic.raw = is.readAllBytes();
        } catch (Exception efdf) {
            System.out.println(efdf);
        }

        return pic;
    }

    public static account get_account_by_id(int id) {
        account account = null;
        try {
            PreparedStatement s = db.conection_users_db.prepareStatement("select * from users where id='" + id + "';");
            ResultSet res = s.executeQuery();
            res.next();

            String ime = res.getString("ime");
            String pas = res.getString("passwords");
            String mail = res.getString("mail");
            String prof = res.getString("profile_pic");

            if (ime == null || pas == null || mail == null || prof == null) {
                return null;
            } else {
                account = new account();
                account.ime = ime;
                account.password = pas;
                account.mail = mail;
                account.Profile = prof;
                account.id = id;

                return account;
            }

        } catch (Exception vv) {
        }

        return account;
    }

    public static account get_account_by_mail(String m) {
        account account = null;
        try {
            PreparedStatement s = db.conection_users_db.prepareStatement("select * from users where mail='" + m + "';");
            ResultSet res = s.executeQuery();
            res.next();
            String ime = res.getString("ime");
            String pas = res.getString("passwords");
            String mail = res.getString("mail");
            String prof = res.getString("profile_pic");
            int id = res.getInt("id");

            if (ime == null || pas == null || mail == null || prof == null) {
                return null;
            } else {
                account = new account();
                account.ime = ime;
                account.password = pas;
                account.mail = mail;
                account.Profile = prof;
                account.id = id;

                return account;
            }

        } catch (Exception vv) {
        }

        return account;
    }

    public static account get_account_by_username(String username) {
        account account = null;
        try {
            PreparedStatement s = db.conection_users_db.prepareStatement("select * from users where ime='" + username + "';");
            ResultSet res = s.executeQuery();
            res.next();
            String ime = res.getString("ime");
            String pas = res.getString("passwords");
            String mail = res.getString("mail");
            String prof = res.getString("profile_pic");
            int id = res.getInt("id");

            if (ime == null || pas == null || mail == null || prof == null) {
                return null;
            } else {
                account = new account();
                account.ime = ime;
                account.password = pas;
                account.mail = mail;
                account.Profile = prof;
                account.id = id;

                return account;
            }

        } catch (Exception vv) {
        }

        return account;
    }

    public static account get_account_by_username_and_password(String username, String password) {
        account account = null;
        try {
            PreparedStatement s = db.conection_users_db.prepareStatement("select * from users where ime='" + username + "' AND passwords='" + password + "';");
            ResultSet res = s.executeQuery();
            res.next();
            String ime = res.getString("ime");
            String pas = res.getString("passwords");
            String mail = res.getString("mail");
            String prof = res.getString("profile_pic");
            int id = res.getInt("id");

            if (ime == null || pas == null || mail == null || prof == null) {
                return null;
            } else {
                account = new account();
                account.ime = ime;
                account.password = pas;
                account.mail = mail;
                account.Profile = prof;
                account.id = id;

                return account;
            }

        } catch (Exception vv) {
        }

        return account;
    }

    public static JocaArray<account> get_accounts_by_search(String username, int offset) {

        JocaArray<account> ret = new JocaArray<>();
        offset = 1000;
        try {
            String sql = "select * from users where ime like '%" + username + "%' limit " + offset + ";";
            PreparedStatement s = db.conection_users_db.prepareStatement(sql);
            ResultSet res = s.executeQuery();
            while (res.next()) {

                String ime = res.getString("ime");
                String pas = res.getString("passwords");
                String mail = res.getString("mail");
                String prof = res.getString("profile_pic");
                int id = res.getInt("id");

                account acc = new account();
                acc.ime = ime;
                acc.password = pas;
                acc.mail = mail;
                acc.Profile = prof;
                acc.id = id;
                ret.appand(acc);
            }

        } catch (Exception vv) {
        }

        return ret;
    }

    public static JocaArray<chet> get_cannels(int id) {
        JocaArray<chet> chetovi = new JocaArray<>();
        try {
            PreparedStatement statement = conection_chets_db.prepareStatement("select* from chets where id2=" + id + " group by id1;");
            ResultSet rez = statement.executeQuery();

            while (rez.next()) {
                int iid = rez.getInt("id");
                int id1 = rez.getInt("id1");
                int id2 = rez.getInt("id2");
                String massage = rez.getString("massage");
                chet c = new chet();
                c.id = iid;
                c.id1 = id1;
                c.id2 = id2;
                c.massage = massage;
                chetovi.appand(c);
            }

        } catch (Exception eee) {
        }

        return chetovi;
    }

    public static JocaArray<post> get_post_by_search(String search, int offset) {
        JocaArray<post> ret = new JocaArray<>();
        offset = 1000;
        try {
            String sql = "select * from posts where title like '%" + search + "%' or content like '%" + search + "%' limit " + offset + ";";
            PreparedStatement s = db.conection_posts_db.prepareStatement(sql);
            ResultSet res = s.executeQuery();
            while (res.next()) {

                post p = new post();
                p.title = res.getString("title");
                p.content = res.getString("content");
                //p.vreme=res.getDate("datum");
                p.autor = res.getInt("autor");

                ret.appand(p);


            }
        } catch (Exception ee) {
        }

        return ret;
    }

    public static JocaArray<post> getposts(int load, account acc) {
        JocaArray<post> ret = new JocaArray<>();
        try {
            String sql = "select * from posts where autor=" + acc.id + " order by datum desc limit " + load + ";";
            PreparedStatement statement = db.conection_posts_db.prepareStatement(sql);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                post p = new post();
                p.title = res.getString("title");
                p.content = res.getString("content");
                //p.vreme=res.getDate("datum");
                p.autor = acc.id;

                ret.appand(p);
            }

        } catch (Exception fv) {
        }

        return ret;

    }

    public static void setPicture(Picture pic) {
        try {
            PreparedStatement statement = db.conection_users_db.prepareStatement("insert into pictures(title,raw) values(?,?)");
            InputStream is = new ByteArrayInputStream(pic.raw);
            statement.setBinaryStream(2, is);
            statement.setString(1, pic.title);
            statement.execute();
        } catch (Exception efdf) {
            System.out.println(efdf);
        }
    }

    public JocaArray<chet> getmassages(int i1, int i2) {
        JocaArray<chet> chetovi = new JocaArray<>();
        try {
            PreparedStatement statement = conection_chets_db.prepareStatement("select* from chets where (id1=" + i1 + " and i2=" + i2 + ") OR (id1=" + i1 + " and i2=" + i2 + ");");
            ResultSet rez = statement.executeQuery();

            while (rez.next()) {
                int iid = rez.getInt("id");
                int id1 = rez.getInt("id1");
                int id2 = rez.getInt("id2");
                String massage = rez.getString("massage");
                chet c = new chet();
                c.id = iid;
                c.id1 = id1;
                c.id2 = id2;
                c.massage = massage;
                chetovi.appand(c);
            }

        } catch (Exception eee) {
        }

        return chetovi;
    }

}
