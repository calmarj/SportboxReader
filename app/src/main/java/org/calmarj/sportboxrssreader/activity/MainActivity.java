package org.calmarj.sportboxrssreader.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.calmarj.sportboxrssreader.R;
import org.calmarj.sportboxrssreader.adapter.RSSAdapter;
import org.calmarj.sportboxrssreader.db.DatabaseHelper;
import org.calmarj.sportboxrssreader.model.RSS;
import org.calmarj.sportboxrssreader.retrofit.ServiceFactory;
import org.calmarj.sportboxrssreader.retrofit.SportboxService;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MAIN";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Subscription mSubscription;

    private DatabaseHelper mDatabaseHelper;

    private SportboxService mSportboxService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDatabaseHelper = new DatabaseHelper(getApplicationContext());

        mSportboxService = ServiceFactory.createRetrofitService(SportboxService.class, SportboxService.SERVICE_ENDPOINT);

        refresh();
    }

    private void refresh() {
        mSubscription = mSportboxService.getFootballChannel()
                .subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                })
                .subscribe(new Subscriber<RSS>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Log.d(TAG, e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(RSS rss) {
                        Log.d(TAG, rss.getChannel().getTitle());
                        mAdapter = new RSSAdapter(rss.getChannel(), getApplication());

                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mSubscription.unsubscribe();
        mDatabaseHelper.closeDB();
        super.onDestroy();
    }
}
