package com.example.asus.marqueeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marqueeview.MarqueeView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        ArrayList<String> list = new ArrayList<>();
        list.add("一路帶一路，中國全面進入小康社會");
        list.add("一路帶一路，中國全面進入小康社會");
        list.add("一路帶一路，中國全面進入小康社會");
        list.add("一路帶一路，中國全面進入小康社會");
        list.add("一路帶一路，中國全面進入小康社會");
        list.add("一路帶一路，中國全面進入小康社會");
        list.add("一路帶一路，中國全面進入小康社會");

        MarqueeView marqueeView = (MarqueeView) findViewById(R.id.mv_marquee);
//        marqueeView.setScrollHeight(dp2px(20));
        marqueeView.setInterval(2000);
        marqueeView.setAdapter(new marqueeAdapter(list));
    }

    private class marqueeAdapter extends RecyclerView.Adapter<marqueeViewHolder> {
        ArrayList<String> list = new ArrayList<>();

        public marqueeAdapter(ArrayList<String> list) {
            this.list = list;
        }

        @Override
        public marqueeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marquee_item, parent, false);
            return new marqueeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(marqueeViewHolder holder, int position) {
            holder.onBindData(list.get(position % list.size()));
        }

        @Override
        public int getItemCount() {
            if (list == null || list.size() == 0) {
                return 0;
            }
            return Integer.MAX_VALUE;
        }
    }

    private class marqueeViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_content;

        public marqueeViewHolder(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
        }

        public void onBindData(String content) {
            tv_content.setText(content);
        }
    }

    private int dp2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
