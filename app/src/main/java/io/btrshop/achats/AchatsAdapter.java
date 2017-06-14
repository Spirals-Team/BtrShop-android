package io.btrshop.achats;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.btrshop.R;
import io.btrshop.detailsproduct.domain.model.Product;

/**
 * Created by martin on 18/05/2017.
 */

public class AchatsAdapter extends BaseAdapter {
    public List<Product> listP;
    public Context context;
    public LayoutInflater inflater;
    private Bitmap bmp;

    public AchatsAdapter(Context context, List<Product> listP){
        this.listP = listP;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listP.size();
    }

    @Override
    public Object getItem(int position) {
        return listP.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        RelativeLayout layoutItem;

        if(convertView == null){
            layoutItem = (RelativeLayout) inflater.inflate(R.layout.articles_list, parent, false);
        }
        else{
            layoutItem = (RelativeLayout) convertView;
        }

        final ImageView logo = (ImageView) layoutItem.findViewById(R.id.logoArticle);
        final TextView nom = (TextView) layoutItem.findViewById(R.id.nomArticle);
        final TextView prix = (TextView) layoutItem.findViewById(R.id.prix_quantiteArticle);
        final ImageButton sup = (ImageButton) layoutItem.findViewById(R.id.supArticle);

        final Product p = listP.get(position);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    InputStream in = new URL(p.getLogo()).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    // log error
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (bmp != null)
                    logo.setImageBitmap(bmp);
                logo.setVisibility(View.VISIBLE);
            }

        }.execute();

        nom.setText(p.getName());
        prix.setText(p.getQuantity() + " x " + p.getOffers()[0].getPrice() + " " + p.getOffers()[0].getPriceCurrency());
        sup.setImageResource(R.drawable.delete);

        sup.setTag(position);
        sup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Integer position = (Integer)v.getTag();
                sendListener(listP.get(position),position);
            }
        });

        return layoutItem;
    }

    public interface AchatsAdapterListener{
        void onClickSup(Product item, int position);
    }

    private ArrayList<AchatsAdapterListener> listListener = new ArrayList<>();

    public void addListener(AchatsAdapterListener listener) {
        listListener.add(listener);
    }

    private void sendListener(Product item, int position){
        for(int i = listListener.size()-1 ;  i>=0 ; i--){
            listListener.get(i).onClickSup(item,position);
        }
    }
}
