package cs246.ilearntracker;

/**
 * Created by Jbeag_000 on 3/14/2015.
 */
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class ClassListAdapter2 extends BaseAdapter implements ListAdapter {

    private List<Class> list = new ArrayList<Class>();
    private Context context;

    /**
     * The constructor for the list adapter
     * @param list The list of items to be shown
     * @param context The context associated with the class
     */
    public ClassListAdapter2(List<Class> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        //return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_class_adder, null);
        }

        TextView colorBox = (TextView)view.findViewById(R.id.colorBox);
        colorBox.setBackgroundColor(list.get(position).getClassColor());

        TextView classSelect = (TextView)view.findViewById(R.id.classButton);
        classSelect.setText(list.get(position).getClassName());

        return view;
    }
}