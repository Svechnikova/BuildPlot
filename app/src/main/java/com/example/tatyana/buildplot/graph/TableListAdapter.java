package com.example.tatyana.buildplot.graph;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.example.tatyana.buildplot.Constants;
import com.example.tatyana.buildplot.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom Adapter for ListView which manages items_table.xml
 */
public class TableListAdapter extends BaseAdapter {
    private List<Point> points;
    private Context context;

    public TableListAdapter(Context context, ArrayList<Point> points) {
        this.context = context;
        if (points != null) {
            this.points = points;
        }
    }

    @Override
    public int getCount() {
        return points.size();
    }

    @Override
    public Object getItem(int position) {
        return points.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        // If itemView no exist then load it with the inflater.
        if (convertView == null) {
            // Getting inflater object out of context. It displays the item's appearance.
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_table, null);

            final ViewHolder holder = new ViewHolder();
            // Declare TextViews and linking them with the layout.
            holder.editTextX = (EditText) view.findViewById(R.id.item_x);
            holder.editTextY = (EditText) view.findViewById(R.id.item_y);

            // Set enter X in ArrayList<Point>
            holder.editTextX.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    // Add or remove X coordinates
                    if (points.get(holder.cur_pos).getX() != Constants.NULL_COORDINATE && s.toString().equals(""))
                        points.get(holder.cur_pos).setX(Constants.NULL_COORDINATE);
                    else
                        points.get(holder.cur_pos).setX(s.toString());

                    // Add new row at the end of the table
                    addTableRow(holder.cur_pos + 1, s.toString());
                }
            });

            // Set enter Y in ArrayList<Point>
            holder.editTextY.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    // Add or remove Y coordinates
                    if (points.get(holder.cur_pos).getY() != Constants.NULL_COORDINATE && s.toString().equals(""))
                        points.get(holder.cur_pos).setY(Constants.NULL_COORDINATE);
                    else
                        points.get(holder.cur_pos).setY(s.toString());

                    // Add new row at the end of the table
                    addTableRow(holder.cur_pos + 1, s.toString());
                }
            });

            view.setTag(holder);
            holder.editTextX.setTag(points.get(position));
            holder.editTextY.setTag(points.get(position));
            holder.cur_pos = position;
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).editTextX.setTag(points.get(position).getX());
            ((ViewHolder) view.getTag()).editTextY.setTag(points.get(position).getY());
            ((ViewHolder) view.getTag()).cur_pos = position;
        }
        final ViewHolder holder = (ViewHolder) view.getTag();

        // Set X and Y coordinates in TextView
        if (points.get(position).getX() != Constants.NULL_COORDINATE) {
            holder.editTextX.setText(points.get(position).getX().toString());
        } else {
            holder.editTextX.setText(null);
        }

        if (points.get(position).getY() != Constants.NULL_COORDINATE) {
            holder.editTextY.setText(points.get(position).getY().toString());
        } else {
            holder.editTextY.setText(null);
        }

        return view;
    }

    /**
     * Class for optimization to performance of TableListAdapter.
     */
    private class ViewHolder {
        EditText editTextX, editTextY;
        int cur_pos;
    }

    /**
     * Method for add new table row.
     */
    private void addTableRow(int pos, String s) {
        if (pos == points.size() && !s.equals("")) {
            points.add(new Point());
        }
    }
}