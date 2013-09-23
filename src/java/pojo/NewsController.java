/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author CANHNGUYEN
 */
@ManagedBean
@SessionScoped
public class NewsController {

    int startId;
    int endId;
    DataModel newsTitle;
    NewsHelper helper;
    private int recordCount = 100;
    private int pageSize = 10;
    private News current;
    private int selectedItemIndex;

    /**
     * Creates a new instance of NewsController
     */
    public NewsController() {
        helper = new NewsHelper();
        startId = 1;
        endId = 10;
    }

    public NewsController(int startId, int endId) {
        helper = new NewsHelper();
        this.startId = startId;
        this.endId = endId;
    }

    public News getSelected() {

        if (current == null) {
            current = new News();
            selectedItemIndex = -1;
        }
        return current;
    }

    public DataModel getNewsTitles() {
        if (newsTitle == null) {
            newsTitle = new ListDataModel(helper.getCategoryTitles(startId, endId));
        }
        return newsTitle;
    }

    void recreateModel() {
        newsTitle = null;
    }

    // 5 following methods that are used to display table and navigate the pages
    public boolean isHasNextPage() {
        if (endId + pageSize <= recordCount) {
            return true;
        }
        return false;
    }

    public boolean isHasPreviousPage() {
        if (startId - pageSize > 0) {
            return true;
        }
        return false;
    }

    public String next() {
        startId = endId + 1;
        endId = endId + pageSize;
        recreateModel();
        return "index";
    }

    public String previous() {
        startId = startId - pageSize;
        endId = endId - pageSize;
        recreateModel();
        return "index";
    }

    public int getPageSize() {
        return pageSize;
    }

    public String prepareView() {
        current = (News) getNewsTitles().getRowData();
        return "browse";
    }

    public String prepareList() {
        recreateModel();
        return "browse";
    }

    public String getcategories() {
        Categories categories = helper.getCategory(current.getNewsId());
        return categories.getCategoryName();
    }
}
