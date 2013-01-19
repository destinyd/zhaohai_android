package DD.Android.Zhaohai.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import DD.Android.Zhaohai.BootstrapServiceProvider;
import DD.Android.Zhaohai.core.News;
import DD.Android.Zhaohai.R;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import com.google.inject.Inject;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static DD.Android.Zhaohai.core.Constants.Extra.NEWS_ITEM;

public class NewsActivity extends BootstrapActivity {

//    @InjectExtra(NEWS_ITEM) protected News newsItem;

//    @InjectView(R.id.tv_title) protected TextView title;
//    @InjectView(R.id.tv_content) protected TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        setTitle(newsItem.getTitle());

//        title.setText(newsItem.getTitle());
//        content.setText(newsItem.getContent());

    }

}
