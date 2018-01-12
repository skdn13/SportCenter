package pt.ipp.estg.sportcenter;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;

/**
 * Created by pmms8 on 09/11/2017.
 */

public class MyDialog extends DialogFragment {
    NoticeDialogListener mListener;

    public interface NoticeDialogListener {
        void onDialogPositiveClick(android.support.v4.app.DialogFragment dialog);

        void onDialogNegativeClick(android.support.v4.app.DialogFragment dialog);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Iniciar Sessão");


        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(getActivity(), Main2Activity.class);
                EditText username = getView().findViewById(R.id.username);

                intent.putExtra("text", "Bem vindo, " + username.getText().toString());
                getActivity().startActivity(intent);
                //mListener.onDialogPositiveClick(new android.support.v4.app.DialogFragment());
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                // mListener.onDialogNegativeClick(new android.support.v4.app.DialogFragment());
            }
        });
        builder.setNeutralButton("Registar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                // mListener.onDialogNegativeClick(new android.support.v4.app.DialogFragment());
            }
        });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.alert, null));
        AlertDialog mDialog = builder.create();
        // mDialog.show();
        return mDialog;
    }


}