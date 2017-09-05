package in.nsit.com.automata.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

import in.nsit.com.automata.R;

/**
 * Created by ( Jatin Bansal ) on 09-03-2017.
 */

public class DeleteConfirmation extends android.support.v4.app.DialogFragment {

    View v;

    LoadFragment loadfragment;
    Button yes,no;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.deleteconfirmation,container,false);
        yes= (Button) v.findViewById(R.id.deleteyes);
        no= (Button) v.findViewById(R.id.deleteno);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    loadfragment.yesdeleteElement();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadfragment.remove();
            }
        });


        return  v;

    }
}
