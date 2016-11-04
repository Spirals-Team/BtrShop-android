package fr.lille1.univ.android.architecture.btrshop.scanner;

import fr.lille1.univ.android.architecture.btrshop.BasePresenter;
import fr.lille1.univ.android.architecture.btrshop.BaseView;

/**
 * Created by charlie on 20/10/16.
 */

public interface ScannerContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
