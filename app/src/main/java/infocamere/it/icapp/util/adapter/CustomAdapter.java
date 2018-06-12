package infocamere.it.icapp.util.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import infocamere.it.icapp.R;
import infocamere.it.icapp.model.UserIC;

public class CustomAdapter extends CursorAdapter {
    TextView txtId, txtName, txtSurname, txtEmail;
    private LayoutInflater mInflater;

    public CustomAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_search, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.txtId = (TextView) view.findViewById(R.id.txtId);
        holder.txtName = (TextView) view.findViewById(R.id.txtName);
        holder.txtSurname = (TextView) view.findViewById(R.id.txtSurname);
        holder.txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        holder.txtPhoneMobile = (TextView) view.findViewById(R.id.txtPhoneMobile);
        holder.txtPhoneFix = (TextView) view.findViewById(R.id.txtPhoneFix);
        holder.txtSede = (TextView) view.findViewById(R.id.txtSede);
        holder.txtOffice = (TextView) view.findViewById(R.id.txtOffice);
        holder.txtMatricola = (TextView) view.findViewById(R.id.txtMatricola);
        holder.txtCdr = (TextView) view.findViewById(R.id.txtCdr);

        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //If you want to have zebra lines color effect uncomment below code

        if (cursor.getPosition()%2 == 1) {
            view.setBackgroundResource(R.color.colorPrimaryZebra);
        }
        else {
            view.setBackgroundResource(R.color.colorSecondaryZebra);
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.txtId.setText(cursor.getString(cursor.getColumnIndex(UserIC.KEY_ROWID)));
        holder.txtName.setText(cursor.getString(cursor.getColumnIndex(UserIC.KEY_name)));
        holder.txtSurname.setText(cursor.getString(cursor.getColumnIndex(UserIC.KEY_surname)));
        holder.txtEmail.setText(cursor.getString(cursor.getColumnIndex(UserIC.KEY_email)));
        holder.txtCdr.setText(cursor.getString(cursor.getColumnIndex(UserIC.KEY_cdr)));
        holder.txtMatricola.setText(cursor.getString(cursor.getColumnIndex(UserIC.KEY_matricola)));
        holder.txtOffice.setText(cursor.getString(cursor.getColumnIndex(UserIC.KEY_office)));
        holder.txtSede.setText(cursor.getString(cursor.getColumnIndex(UserIC.KEY_sede)));
        holder.txtPhoneFix.setText(cursor.getString(cursor.getColumnIndex(UserIC.KEY_phone_fix)));
        holder.txtPhoneMobile.setText(cursor.getString(cursor.getColumnIndex(UserIC.KEY_phone_mobile)));
    }

    static class ViewHolder {
        TextView txtId;
        TextView txtName;
        TextView txtSurname;
        TextView txtEmail;
        TextView txtCdr;
        TextView txtMatricola;
        TextView txtPhoneFix;
        TextView txtPhoneMobile;
        TextView txtOffice;
        TextView txtSede;
    }
}
