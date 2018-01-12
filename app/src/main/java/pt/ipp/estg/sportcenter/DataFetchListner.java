package pt.ipp.estg.sportcenter;

/**
 * Created by pmms8 on 1/7/2018.
 */

public interface DataFetchListner {
    void onDeliverData(DataModel dataModel);
    void onHideDialog();
}