package edu.ktu.ktukrash;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.Locale;

public class CustomFilter extends Filter{

    ArrayList<recyclerModel> filterList;
    MyAdapter adapter;

    public CustomFilter(ArrayList<recyclerModel> filterList, MyAdapter adapter) {
        this.filterList = filterList;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();

        if (charSequence != null && charSequence.length() > 0){
            charSequence = charSequence.toString().toUpperCase();

            ArrayList<recyclerModel> filterModel = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++){
                if (filterList.get(i).getTitle().toUpperCase().startsWith(charSequence.toString())){
                    filterModel.add(filterList.get(i));
                }
            }

            results.count = filterModel.size();
            results.values = filterModel;
        }else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.arr = (ArrayList<recyclerModel>) filterResults.values;
        adapter.notifyDataSetChanged();
    }
}
