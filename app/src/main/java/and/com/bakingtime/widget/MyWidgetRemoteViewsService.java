package and.com.bakingtime.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by dell on 16-05-2017.
 */

public class MyWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyWidgetRemoteViewsFactory(this.getApplicationContext());
    }
}
