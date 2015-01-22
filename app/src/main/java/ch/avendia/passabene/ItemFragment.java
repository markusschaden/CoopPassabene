package ch.avendia.passabene;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import ch.avendia.passabene.shopping.Product;
import ch.avendia.passabene.shopping.ShoppingCardHolder;
import ch.avendia.passabene.shopping.ShoppingCartItem;
import ch.avendia.passabene.shopping.ShoppingCartItemAdapter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link ch.avendia.passabene.ItemFragment.ItemFragmentListener}
 * interface.
 */
public class ItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ItemFragmentListener mListener;
    private final String FORMAT_TWO_DIGITS = "%.2f"; // two digits
    private double totalPrice = 0;
    private int totalQuantity = 0;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ShoppingCartItemAdapter mAdapter;
    private List<ShoppingCartItem> items = ShoppingCardHolder.shoppingCart;
    private TextView totalPriceTV;
    private TextView totalQuantityTV;

    // TODO: Rename and change types of parameters
    public static ItemFragment newInstance(String param1, String param2) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        items.add(new ShoppingCartItem(new Product("Test Produkt 1", 100.50)));
        items.add(new ShoppingCartItem(new Product("Test Produkt 2", 55.0)));
        items.add(new ShoppingCartItem(new Product("Test Produkt 3", 3200.0)));
        items.add(new ShoppingCartItem(new Product("Test Produkt 1", 100.50)));
        items.add(new ShoppingCartItem(new Product("Test Produkt 2", 55.0)));
        items.add(new ShoppingCartItem(new Product("Test Produkt 3", 3200.0)));

        items.add(new ShoppingCartItem(new Product("Test Produkt 1", 100.50)));
        items.add(new ShoppingCartItem(new Product("Test Produkt 2", 55.0)));
        items.add(new ShoppingCartItem(new Product("Test Produkt 3", 3200.0)));

        items.add(new ShoppingCartItem(new Product("Test Produkt 1", 100.50)));
        items.add(new ShoppingCartItem(new Product("Test Produkt 2", 55.0)));
        items.add(new ShoppingCartItem(new Product("Test Produkt 3", 3200.0)));
        items.add(new ShoppingCartItem(new Product("Test Produkt 1", 100.50)));
        items.add(new ShoppingCartItem(new Product("Test Produkt 2", 55.0)));
        items.add(new ShoppingCartItem(new Product("Test Produkt 10", 3200.0)));

        mAdapter = new ShoppingCartItemAdapter(getActivity().getBaseContext(), items);

    }


    public void addBarcode(String barcode) {

        items.add(new ShoppingCartItem(new Product(barcode, 90.50)));
        refresh();
    }

    public void refresh() {
        if(mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        calcTotalPrice();
        calcTotalQuantity();
    }

    public void calcTotalPrice() {
        double result = 0;
        for (ShoppingCartItem item : items) {
            result += (item.getQuantity() * item.getProduct().getPrice());
        }
        totalPrice = result;
        if(totalPriceTV != null) {
            totalPriceTV.setText(String.format(FORMAT_TWO_DIGITS, totalPrice));
        }
    }

    public void calcTotalQuantity() {
        int result = 0;
        for (ShoppingCartItem item : items) {
            result += item.getQuantity();
        }
        totalQuantity = result;
        if(totalQuantityTV != null) {
            totalQuantityTV.setText(totalQuantity + " " + "Artikel");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        totalPriceTV = (TextView)view.findViewById(R.id.totalPrice);
        calcTotalPrice();

        totalQuantityTV = (TextView)view.findViewById(R.id.totalQuantity);
        calcTotalQuantity();

        Button button = (Button) view.findViewById(R.id.buttonScan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onScanClick();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ItemFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onItemClick(items.get(position).getProduct().getName());

            Intent intent = new Intent(getActivity(), DetailitemActivity.class);
            intent.putExtra(MainActivity.ITEM_ID, position);
            startActivity(intent);

        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface ItemFragmentListener {
        public void onScanClick();

        public void onItemClick(String id);
    }

}
