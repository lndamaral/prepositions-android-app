package leonardoamaral.view;

import android.app.Activity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import leonardoamaral.java.Stats;
import leonardoamaral.prepositions.R;

public class ProgressScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_screen);
        generatePieChart();
    }

    public void generatePieChart(){

        Stats s = Stats.getInstance();

        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        if (s.getTotalOfCorrect() > 0) {
            entries.add(new Entry(s.getTotalOfCorrect(), 0));
            labels.add("Correct Answers");
        }

        if (s.getTotalOfWrong() > 0 ) {
            entries.add(new Entry(s.getTotalOfWrong(), 1));
            labels.add("Wrong Answers");
        }

        if (s.getTotalOfSkipped() > 0) {
            entries.add(new Entry(s.getTotalOfSkipped(), 2));
            labels.add("Skipped Answers");
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieChart chart = new PieChart(this);
        setContentView(chart);


        PieData data = new PieData(labels, dataSet);
        data.getDataSet().setValueFormatter(new PercentFormatter());

        chart.setData(data);


        data.getDataSet().setValueTextSize(14);
        chart.setUsePercentValues(true);
        chart.setDescription("");

        Legend l = chart.getLegend();
        l.setEnabled(false);

        chart.animateX(1000);
    }

}
