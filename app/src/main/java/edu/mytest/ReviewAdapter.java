package edu.mytest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {
    private ArrayList<ItemReview> data = new ArrayList<ItemReview>();
    private Context context;

    public ReviewAdapter(Context context, ArrayList<ItemReview> arr) {
        if (arr != null) {
            data = arr;
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int num) {
        // TODO Auto-generated method stub
        return data.get(num);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int i, View someView, ViewGroup arg2) {
        //Получение объекта inflater из контекста
        LayoutInflater inflater = LayoutInflater.from(context);
        //Если someView (View из ListView) вдруг оказался равен
        //null тогда мы загружаем его с помошью inflater
        if (someView == null) {
            someView = inflater.inflate(R.layout.layout_reviews, arg2, false);
        }
        //Обявляем наши текствьюшки и связываем их с разметкой
        TextView header = (TextView) someView.findViewById(R.id.item_text);
        TextView subHeader = (TextView) someView.findViewById(R.id.item_rate);

        //Устанавливаем в каждую текствьюшку соответствующий текст
        // сначала заголовок
        header.setText(data.get(i).getText());
        // потом подзаголовок
        subHeader.setText(data.get(i).getRate());
        return someView;
    }

}
