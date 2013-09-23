/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author CANHNGUYEN
 */
public class NewsHelper {

    Session session = null;

    public NewsHelper() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public List getCategoryTitles(int startID, int endID) {
        List<Categories> categories = null;
        try {
            Transaction transac = session.beginTransaction();
            // note : name of table is difference between lowercase and uppercase 
            // (name of table and column is name of pojo class and property in class)
            Query q = session.createQuery("from Categories where categoryId between " + startID + " and " + endID);
            categories = (List<Categories>) q.list();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return categories;
    }

    public boolean checkLogin(String username, String password) {
        boolean success = false;
        try {
            Transaction transac = session.beginTransaction();
            Query q = session.createQuery("from Users where username = '" + username + "' and password = '" + password + "'");
            if (q.list() != null) {
                success = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return success;
    }

    public List getNewsByID(int news_id) {
        List<News> news = null;
        try {
            Transaction transac = session.beginTransaction();
            Query q = session.createQuery("from News where newsId = " + news_id);
            news = (List<News>) q.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return news;
    }

    /*
     * get Catogory from newsID
     */
    public Categories getCategory(int news_Id) {
        List<Categories> categories = null;
        try {
            Transaction transac = session.beginTransaction();
            Query q = session.createQuery("from Categories where categoryId in (select categoriesId from News where categoriesId = " + news_Id +")");
            categories = (List<Categories>) q.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return categories.get(0);
    }
}
