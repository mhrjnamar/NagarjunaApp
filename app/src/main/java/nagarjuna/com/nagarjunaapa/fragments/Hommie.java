package nagarjuna.com.nagarjunaapa.fragments;

import android.support.v7.widget.RecyclerView;

import nagarjuna.com.headerlibs.BaseDecorationFragment;
import nagarjuna.com.headerlibs.StickyHeaderDecoration;
import nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter.EventNoticeAdapter;

/**
 * Created by User on 6/16/2016.
 */
public class Hommie extends BaseDecorationFragment {

    StickyHeaderDecoration decor;
    @Override
    protected void setAdapterAndDecor(RecyclerView list) {
        final EventNoticeAdapter adapter = new EventNoticeAdapter(this.getActivity());
        decor = new StickyHeaderDecoration(adapter);
        setHasOptionsMenu(true);

        list.setAdapter(adapter);
        list.addItemDecoration(decor, 1);

    }
}
