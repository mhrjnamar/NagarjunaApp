package nagarjuna.com.nagarjunaapa.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import inficare.net.sctlib.SCTPaymentActivity;
import inficare.net.sctlib.StaticVariables;
import nagarjuna.com.nagarjunaapp.MainActivity;
import nagarjuna.com.nagarjunaapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeePayment extends Fragment {

    Spinner facultySpinner;
    Spinner semesterSpinner;
    EditText full_name;
    EditText tu_reg;
    String[] faculty = {"BIM","BSc.CSIT"};
    String[] semester = {"1","2","3","4","5","6","7","8"};
    private EditText amt;

    private Button feePayment;
    public FeePayment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fee_payment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tu_reg = (EditText) view.findViewById(R.id.tu_reg);
        full_name = (EditText) view.findViewById(R.id.full_name);
        facultySpinner = (Spinner) view.findViewById(R.id.facultySpinner);
        semesterSpinner = (Spinner) view.findViewById(R.id.semesterSpinner);
        amt = (EditText) view.findViewById(R.id.amt);
        ArrayAdapter<String> faculty_adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,faculty);
        ArrayAdapter<String> semester_adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,semester);

        facultySpinner.setAdapter(faculty_adapter);
        semesterSpinner.setAdapter(semester_adapter);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Fee Payment");
        feePayment = (Button) view.findViewById(R.id.fee_payment);
        feePayment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SCTPaymentActivity.class);
                i.putExtra(StaticVariables.MERCHANT_AMOUNT, amt.getText().toString().trim());
                i.putExtra(StaticVariables.DESCRIPTION, "test description");
                i.putExtra(StaticVariables.MERCHANT_ID, "2");
                i.putExtra(StaticVariables.MERCHANT_TNX_ID, String.valueOf(System.currentTimeMillis()));
                i.putExtra(StaticVariables.MERCHANT_PASSWORD, "p2nsdkuser_api");
                i.putExtra(StaticVariables.MERCHANT_SIGNATURE_PASSCODE, "PTNSDK01");
                i.putExtra(StaticVariables.MERCHANT_USERNAME, "p2nsdkuser");
                startActivity(i);
                getActivity().registerReceiver(receiver,new IntentFilter(StaticVariables.MESSAGE_INTENT_FILTER));
            }
        });

    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra(StaticVariables.STATUS_CODE);
            String msg = intent.getStringExtra(StaticVariables.MESSAGE);
            String ref = intent.getStringExtra(StaticVariables.GTWREFNO);
            String tnx = intent.getStringExtra(StaticVariables.MERCHANT_TNX_ID);
          //  response.setText("STATUS: "+status+"\nmsg: "+msg+"\nref : "+ref+"\nTNX : "+tnx);
            Toast.makeText(getActivity(), "STATUS: "+status+" msg: "+msg+" ref : "+ref+" TNX : "+tnx, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver!= null){
            getActivity().unregisterReceiver(receiver);
            receiver = null;
        }


    }
}
