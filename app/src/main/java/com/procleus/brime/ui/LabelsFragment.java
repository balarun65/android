/**
 * @author Saad Hassan <hassan.saad.mail@gmail.com>
 * @author Suraj Rawat <suraj.raw120@gmail.com>
 * @author Swastik Binjola <swastik.binjola2561@gmail.com>
 * @author Ujjwal Bhardwaj <ujjwalb1996@gmail.com>
 *
 * @license AGPL-3.0
 *
 * This code is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License, version 3,
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */

package com.procleus.brime.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.procleus.brime.data.NotesDbHelperOld;
import com.procleus.brime.R;
import com.procleus.brime.utils.CustomEditText;

import java.util.ArrayList;

public class LabelsFragment extends Fragment {

    private ArrayList<String> labelsRetrieved;
    private  ListView listView;
    EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle("Labels");
        ((MainActivity) getActivity()).showFloatingActionButton(true);
        final View v = inflater.inflate(R.layout.labels_gragment, container, false);
        final NotesDbHelperOld tn = new NotesDbHelperOld(getActivity());

        labelsRetrieved = new ArrayList<String>();
        labelsRetrieved = tn.retrieveLabel();


        listView = (ListView) v.findViewById(R.id.listLabel);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, labelsRetrieved);
        listView.setAdapter(arrayAdapter);
        ImageButton addLabelBtn = (ImageButton) v.findViewById(R.id.addLabelBtn);

        addLabelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLabelFunc(v, tn);
            }
        });


        /*/WORK OF LONG ITEM CLICK LISTENER*/


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_label);
                dialog.show();

                final Button negative = (Button) dialog.findViewById(R.id.btn_no_label);
                final Button positive = (Button) dialog.findViewById(R.id.btn_yes_label);


                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });


                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.i("brinjal", "Yes");

                        tn.deleteTextNote(String.valueOf(parent.getItemAtPosition(position)));
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, labelsRetrieved);
                        listView.setAdapter(arrayAdapter);
                        dialog.dismiss();

                    }

                });


                return true;

            }
        });

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        NotesDbHelperOld tn = new NotesDbHelperOld(getContext());
        labelsRetrieved = tn.retrieveLabel();
        listView = (ListView) getView().findViewById(R.id.listLabel);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, labelsRetrieved);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_label);
                dialog.show();

                final Button negative = (Button) dialog.findViewById(R.id.btn_no_label);
                final Button positive = (Button) dialog.findViewById(R.id.btn_yes_label);



        /*/This is Database Spinner Retreival*/
        /* Spinner getting Data from dataBase*/


                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();


                    }
                });


                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.i("brinjalResume", "Yes");

                        NotesDbHelperOld tn = new NotesDbHelperOld(getActivity());
                        tn.deleteTextNote(String.valueOf(parent.getItemAtPosition(position)));
                        labelsRetrieved=tn.retrieveLabel();
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, labelsRetrieved);
                        listView.setAdapter(arrayAdapter);
                        dialog.dismiss();
                    }

                });


                return true;

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(), "Inside in label" + ":" + String.valueOf(parent.getItemAtPosition(position)), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), LabelOpenActivity.class);
                i.putExtra("label", String.valueOf(parent.getItemAtPosition(position)));
                startActivity(i);

            }
        });

    }

    public void addLabelFunc(View v, NotesDbHelperOld nt) {
        editText = (CustomEditText) v.findViewById(R.id.addLabelInput);
        String ed = editText.getText().toString().trim();
        if (ed.isEmpty()) {
            editText.setError("Provide a Label Name");
        } else {

            nt.insertLabel(editText.getText().toString());
            Log.i("Bless", editText.getText().toString());
            ArrayList<String> labelsRetrieved = new ArrayList<String>();
            labelsRetrieved = nt.retrieveLabel();
            listView = (ListView) getView().findViewById(R.id.listLabel);
             ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, labelsRetrieved);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent i = new Intent(getActivity(), LabelOpenActivity.class);
                    i.putExtra("label", String.valueOf(parent.getItemAtPosition(position)));
                    Toast.makeText(getActivity(), "Inside in label" + ":" + String.valueOf(parent.getItemAtPosition(position)), Toast.LENGTH_SHORT).show();
                    startActivity(i);

                }
            });

        }
    }

}