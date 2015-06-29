package com.jskierbi.coordinatorlayoutexample;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

import static butterknife.ButterKnife.bind;

public class MainActivity extends AppCompatActivity {

	private static final String[] ITEMS = {
					"Item 1",
					"Item 2",
					"Item 3",
					"Item 4",
					"Item 5",
					"Item 6",
					"Item 7",
					"Item 8",
					"Item 9",
					"Item 10",
					"Item 11"
	};

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;
	@Bind(R.id.swipe_refresh_layout)
	SwipeRefreshLayout mSwipeRefreshLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bind(this);

		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
			@Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				return new ViewHolder(LayoutInflater.from(MainActivity.this), parent);
			}
			@Override public void onBindViewHolder(ViewHolder holder, int position) {
				holder.setText(ITEMS[position]);
			}
			@Override public int getItemCount() {
				return ITEMS.length;
			}
		});

		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override public void onRefresh() {
				Observable.just(new Object())
								.delay(2, TimeUnit.SECONDS)
								.subscribeOn(Schedulers.io())
								.observeOn(AndroidSchedulers.mainThread())
								.subscribe(new Action1<Object>() {
									@Override public void call(Object o) {
										mSwipeRefreshLayout.setRefreshing(false);
									}
								});
			}
		});
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		@Bind(android.R.id.text1)
		TextView text1;

		public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
			super(inflater.inflate(android.R.layout.simple_list_item_1, parent, false));
			bind(this, this.itemView);
		}

		void setText(String text) {
			text1.setText(text);
		}
	}
}
