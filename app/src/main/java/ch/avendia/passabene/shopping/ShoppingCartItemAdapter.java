package ch.avendia.passabene.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.avendia.passabene.R;

/**
 * Created by Markus on 12.01.2015.
 */
public class ShoppingCartItemAdapter extends ArrayAdapter<ShoppingCartItem> {
    private Context context;
    private List<ShoppingCartItem> items;
    private final String FORMAT_TWO_DIGITS = "%.2f"; // two digits

    public ShoppingCartItemAdapter(Context ctx, List<ShoppingCartItem> items) {
        super(ctx, R.layout.list_row, items);
        this.items = items;
        this.context = ctx;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row, parent, false);
        }

        TextView quantityView = (TextView) convertView.findViewById(R.id.productQuantity);
        TextView titleView = (TextView) convertView.findViewById(R.id.productTitle);
        TextView priceView = (TextView) convertView.findViewById(R.id.productTotalPrice);
        ShoppingCartItem p = items.get(position);

        quantityView.setText("" + p.getQuantity());
        titleView.setText(p.getProduct().getName());
        priceView.setText(String.format(FORMAT_TWO_DIGITS, p.getProduct().getPrice() * p.getQuantity()));


        return convertView;
    }

}
