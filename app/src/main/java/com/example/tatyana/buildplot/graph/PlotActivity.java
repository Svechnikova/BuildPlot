package com.example.tatyana.buildplot.graph;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tatyana.buildplot.Constants;
import com.example.tatyana.buildplot.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Activity with plot
 */
public class PlotActivity extends AppCompatActivity {
    private static String TAG = PlotActivity.class.getName();

    ListView tableListView;
    Button buttonBuild;
    TextView textView;
    GraphView graphView;

    private ArrayList<Point> points;
    private String currentLanguage;
    private LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        tableListView = (ListView) findViewById(R.id.listView_table);
        graphView     = (GraphView) findViewById(R.id.graph);
        buttonBuild   = (Button) findViewById(R.id.btn_build);
        textView      = (TextView) findViewById(R.id.textView);

        // Set text in textView
        currentLanguage = getIntent().getStringExtra(Constants.LANGUAGE);
        if (currentLanguage.equals(Constants.EN)) {
            textView.setText(R.string.text_en);
        }else {
            textView.setText(R.string.text_rus);
        }

        points = new ArrayList<Point>();
        points.add(new Point());
        points.add(new Point());
        points.add(new Point());
        points.add(new Point());
        points.add(new Point());

        // Draw frame
        tableListView.setBackgroundResource(R.drawable.customborder);
        // Set custom adapter
        tableListView.setAdapter(new TableListAdapter(this, points));

        // Data for GraphView
        series = new LineGraphSeries<DataPoint>();
        graphView.addSeries(series);

        buttonBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGraph();
            }
        });
    }

    /**
     * Method of display graph on points
     */
    private void updateGraph(){
        DataPoint[] dataPoints;
        List<DataPoint> dataPointList = new ArrayList<>();
        List<Double> listY = new ArrayList<>();

        graphView = new GraphView(PlotActivity.this);
        graphView.removeAllSeries();

        for (Point point : points) {
            DataPoint dp = new DataPoint(point.getX(), point.getY());
            dataPointList.add(dp);
            listY.add(point.getY());
        }
        // Remove all empty points from tail in the table
        for (int i = dataPointList.size()-1; i >= 0 ; i--) {
            if (dataPointList.get(i).getX() == Constants.NULL_COORDINATE && dataPointList.get(i).getY() == Constants.NULL_COORDINATE) {
                dataPointList.remove(i);
                listY.remove(i);
            }
        }
        dataPoints = dataPointList.toArray(new DataPoint[dataPointList.size()]);

        Log.d(TAG, "dataPoints: " + dataPointList.toString());

        if (dataPointList.size() == 0) {
            if (currentLanguage.equals(Constants.EN))
                Toast.makeText(PlotActivity.this, "Enter coordinates in table", Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(PlotActivity.this, "Введите координаты в таблицу", Toast.LENGTH_LONG).show();
            }
            return;
        }

        try {
            series.resetData(dataPoints);
            // Customize a little bit viewport
            Viewport viewport = graphView.getViewport();

            // Set manual X and Y bounds
            viewport.setXAxisBoundsManual(true);
            viewport.setMaxX(dataPointList.get(dataPointList.size() - 1).getX());
            viewport.setMinX(dataPointList.get(0).getX());

            viewport.setYAxisBoundsManual(true);
            viewport.setMaxY(Collections.max(listY));
            viewport.setMinY(Collections.min(listY));

            // Enable scaling and scrolling
            viewport.setScalable(true);
            viewport.setScalableY(true);
            viewport.setScrollable(true);
            viewport.setScrollableY(true);

            // Plot the points on
            graphView.addSeries(series);

        }catch (IllegalArgumentException e){
            if (currentLanguage.equals(Constants.EN))
                Toast.makeText(PlotActivity.this, "X coordinates should be ordered from lowest to highest.", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(PlotActivity.this, "X координаты должны располагаться по возрастанию.", Toast.LENGTH_LONG).show();
        }
    }
}