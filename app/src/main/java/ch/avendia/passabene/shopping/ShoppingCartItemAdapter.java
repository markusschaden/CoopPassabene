package ch.avendia.passabene.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.avendia.passabene.Constants;
import ch.avendia.passabene.R;
import ch.avendia.passabene.api.json.Item;

/**
 * Created by Markus on 12.01.2015.
 */
public class ShoppingCartItemAdapter extends ArrayAdapter<Item> {
    private Context context;
    private List<Item> items;

    public ShoppingCartItemAdapter(Context ctx, List<Item> items) {
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
        Item p = items.get(position);

        quantityView.setText("" + p.getQuantity());
        titleView.setText(p.getDescription());
        priceView.setText(String.format(Constants.FORMAT_TWO_DIGITS, p.getPrice() * p.getQuantity() / 100.0));


        return convertView;
    }

}
