package infocamere.it.icapp.async;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import infocamere.it.icapp.model.ItemUI;
import infocamere.it.icapp.model.ServiceIC;
import infocamere.it.icapp.util.ItemTouchHelperCallback;
import infocamere.it.icapp.util.adapter.OnStartDragListener;
import infocamere.it.icapp.util.adapter.RVAdapter;
import infocamere.it.icapp.util.db.ServiceICRepo;

public class RendererServicesTask extends AsyncTask<Void, Void, Void> implements OnStartDragListener {

    private RecyclerView rv;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context cxt;
    private ItemTouchHelper mItemTouchHelper;
    private RVAdapter adapter;


    public RendererServicesTask(
            RecyclerView rv, RecyclerView.LayoutManager mLayoutManager, Context cxt) {
        this.rv = rv;
        this.mLayoutManager = mLayoutManager;
        this.cxt = cxt;
    }

    protected Void doInBackground(Void... params) {

        this.rv.setHasFixedSize(true);

        // use a linear layout manager
        this.mLayoutManager = new LinearLayoutManager(this.cxt);
        this.rv.setLayoutManager(this.mLayoutManager);

        // specify an adapter (see also next example)

        adapter = new RVAdapter(generateItemUi(), this);
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rv);
        this.rv.setAdapter(adapter);

        return null;
    }

    protected void onPostExecute(RVAdapter result) {
    }

    private static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private List<ItemUI> generateItemUi() {

        ServiceICRepo svcICRepo = new ServiceICRepo(this.cxt);
        Cursor c = svcICRepo.getServiceICList();
        ServiceIC svc = new ServiceIC();
        List<ItemUI> itemUIModelList = new ArrayList<>();
        ItemUI itemUI = null;

        if (c.moveToFirst()) {
            do {
                svc.setSvcId(c.getString(c.getColumnIndex(ServiceIC.KEY_ID)));
                svc.setSvcName(c.getString(c.getColumnIndex(ServiceIC.KEY_serviceName)));
                svc.setSvcVisible(
                        c.getInt(c.getColumnIndex(ServiceIC.KEY_serviceVisible)) > 0);

                if (svc.getSvcName().contains("Presenze")) {
                    Log.i("GENERATE", "PRESENZE");
                    itemUI = new ItemUI("#393185", svc.getSvcId(), "Il mio profilo",
                            svc.getSvcName(), "Gestisci il tuo foglio presenze. Tocca per vedere " +
                            "timbrature, anomalie, salidi e giustificativi.", "tempo2");
                }
                else if (svc.getSvcName().contains("Trasferte")) {
                    Log.i("GENERATE", "TRASFERTE");
                    itemUI = new ItemUI("#E83E00", svc.getSvcId(), "Il mio profilo",
                            svc.getSvcName(), "Gestisci le tue trasferte", "trasferte");
                }
                else {
                    Log.i("GENERATE", "ALTRO");
                    itemUI = new ItemUI("#3F9CDC", svc.getSvcId(), "Il mio profilo",
                            svc.getSvcName(), "Gestisci il tuo foglio presenze. Tocca per vedere " +
                            "timbrature, anomalie, salidi e giustificativi.", "tempo2");
                }

                itemUIModelList.add(itemUI);
            } while (c.moveToNext());
        }

        return itemUIModelList;
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        if (mItemTouchHelper!=null)
            mItemTouchHelper.startDrag(viewHolder);
    }
}
