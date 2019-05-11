package com.akramlebcir.mac.drivinglicense.Controller;


import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.akramlebcir.mac.drivinglicense.Adapter.InfractionAdapter;
import com.akramlebcir.mac.drivinglicense.R;
import com.akramlebcir.mac.drivinglicense.Model.Citizen;
import com.akramlebcir.mac.drivinglicense.Model.DriverLicense;
import com.akramlebcir.mac.drivinglicense.Model.Infraction;
import com.akramlebcir.mac.drivinglicense.helper.MyPreference;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.CardRequirements;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InfractionFragment extends android.support.v4.app.Fragment
        implements SwipeRefreshLayout.OnRefreshListener,
        InfractionAdapter.InfractionAdapterListener {

    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 1;
    private List<Infraction> infractions = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView numperofpoints;
    private FloatingActionButton fb;
    private InfractionAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PaymentsClient mPaymentsClient;
    private Citizen citizen;
    private MyPreference myPrefrence;
    private String uid;
    private int p=-1;

    public InfractionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_infraction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myPrefrence = MyPreference.getInstance(getActivity());
        uid = myPrefrence.getData("auth");

        recyclerView = view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        fb = view.findViewById(R.id.floatingActionButton);
        numperofpoints = view.findViewById(R.id.numberofpoints_id);

        mAdapter = new InfractionAdapter(getContext(),infractions,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        getInbox();
                    }
                }
        );

        mPaymentsClient =
                Wallet.getPaymentsClient(
                        getContext(),
                        new Wallet.WalletOptions.Builder()
                                .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                                .build());
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentDataRequest request = createPaymentDataRequest();
                if (request != null) {
                    AutoResolveHelper.resolveTask(
                            mPaymentsClient.loadPaymentData(request),
                            getActivity(),
                            // LOAD_PAYMENT_DATA_REQUEST_CODE is a constant value
                            // you define.
                            LOAD_PAYMENT_DATA_REQUEST_CODE);
                    int pos = 0;
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    for (int i =0;i<mAdapter.getSelectedItems().size();i++){
                        if (mAdapter.getSelectedItems().get(i)!=null)
                            pos = mAdapter.getSelectedItems().get(i);
                    }
                    toggleSelection(pos);
                    DatabaseReference myRef = database.getReference("Citizen/"+uid+"/driverLicense/infractions/"+p+"/pay");
                    myRef.setValue(true);
                }
            }
        });
        fb.setClickable(false);
    }

    private void isReadyToPay() {
        IsReadyToPayRequest request =
                IsReadyToPayRequest.newBuilder()
                        .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
                        .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
                        .build();
        Task<Boolean> task = mPaymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(
                new OnCompleteListener<Boolean>() {
                    public void onComplete(Task<Boolean> task) {
                        try {
                            boolean result = task.getResult(ApiException.class);
                            if (result == true) {
                                // Show Google as payment option.
                            } else {
                                // Hide Google as payment option.
                            }
                        } catch (ApiException exception) {
                        }
                    }
                });
    }

    private PaymentDataRequest createPaymentDataRequest() {
        PaymentDataRequest.Builder request =
                PaymentDataRequest.newBuilder()
                        .setTransactionInfo(
                                TransactionInfo.newBuilder()
                                        .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                                        .setTotalPrice("10.00")
                                        .setCurrencyCode("USD")
                                        .build())
                        .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
                        .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
                        .setCardRequirements(
                                CardRequirements.newBuilder()
                                        .addAllowedCardNetworks(
                                                Arrays.asList(
                                                        WalletConstants.CARD_NETWORK_AMEX,
                                                        WalletConstants.CARD_NETWORK_DISCOVER,
                                                        WalletConstants.CARD_NETWORK_VISA,
                                                        WalletConstants.CARD_NETWORK_MASTERCARD))
                                        .build());

        PaymentMethodTokenizationParameters params =
                PaymentMethodTokenizationParameters.newBuilder()
                        .setPaymentMethodTokenizationType(
                                WalletConstants.PAYMENT_METHOD_TOKENIZATION_TYPE_PAYMENT_GATEWAY)
                        .addParameter("gateway", "example")
                        .addParameter("gatewayMerchantId", "exampleGatewayMerchantId")
                        .build();

        request.setPaymentMethodTokenizationParameters(params);
        return request.build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        String token = paymentData.getPaymentMethodToken().getToken();
                        Toast.makeText(getContext(), "RESULT_OK", Toast.LENGTH_LONG).show();
                        deleteMessages();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getContext(), "RESULT_CANCELED", Toast.LENGTH_LONG).show();
                        deleteMessages();

                        break;
                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        // Log the status for debugging.
                        // Generally, there is no need to show an error to
                        // the user as the Google Pay API will do that.
                        Toast.makeText(getContext(), "RESULT_ERROR", Toast.LENGTH_LONG).show();
                        deleteMessages();
                        break;
                    default:
                        // Do nothing.
                        Toast.makeText(getContext(), "Do nothing", Toast.LENGTH_LONG).show();
                        deleteMessages();
                }
                break;
            default:
                // Do nothing.
                Toast.makeText(getContext(), "Do nothing", Toast.LENGTH_LONG).show();
                deleteMessages();
        }
    }

    private void getInbox() {
        swipeRefreshLayout.setRefreshing(true);

        infractions.clear();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Citizen/"+uid+"/driverLicense");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                infractions.clear();
                DriverLicense value = dataSnapshot.getValue(DriverLicense.class);
                for (int i = value.getInfractions().size()-1; i >= 0; i--) {
                    infractions.add(value.getInfractions().get(i));
                }
                numperofpoints.setText(value.getNumberofpoints() + " Points");
                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setRefreshing(false);
                Log.d("err firebase", "Value is: " + value.getInfractions().get(0));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                infractions.clear();
                for (int i = 0; i < citizen.getDriverLicense().getInfractions().size(); i++) {
                    infractions.add(citizen.getDriverLicense().getInfractions().get(i));
                }
                numperofpoints.setText(citizen.getDriverLicense().getNumberofpoints() + " Points");
                mAdapter.notifyDataSetChanged();


                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        DatabaseReference myRef2 = database.getReference("Citizen/"+uid);
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                citizen = dataSnapshot.getValue(Citizen.class);


                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = getResources().getIdentifier("mdcolor_" + typeColor, "array",getContext().getPackageName());

        if (arrayId != 0) {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }

    @Override
    public void onRefresh() {
        getInbox();
    }

    @Override
    public void onIconClicked(int position) {
        toggleSelection(position);
    }

    @Override
    public void onIconImportantClicked(int position) {

//        Infraction infraction = infractions.get(position);
//        infraction.setPay(!infraction.isPay());
//        infractions.set(position, infraction);
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMessageRowClicked(int position) {
        if (mAdapter.getSelectedItemCount() > 0) {
            enableActionMode(position);
        } else {
            // read the message which removes bold from the row
            Infraction infraction = infractions.get(position);
            //infraction.setRead(true);
            infractions.set(position, infraction);
            mAdapter.notifyDataSetChanged();

            Toast.makeText(getContext(), "Read: " + infraction.getInfractionname(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRowLongClicked(int position) {
        enableActionMode(position);
    }

    private void enableActionMode(int position) {
        swipeRefreshLayout.setEnabled(false);
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        int count = mAdapter.getSelectedItemCount();
        if ((mAdapter.selectedItems.get(position) || count<=0) && !infractions.get(position).isPay()) {
            mAdapter.toggleSelection(position);
            p = infractions.get(position).getId();
            fb.setClickable(true);
        }
    }

    private void deleteMessages() {
        mAdapter.resetAnimationIndex();
        List<Integer> selectedItemPositions =
                mAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            mAdapter.removeData(selectedItemPositions.get(i));
        }
        mAdapter.clearSelections();
        swipeRefreshLayout.setEnabled(true);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.resetAnimationIndex();
                 mAdapter.notifyDataSetChanged();
            }
        });
    }

}
