package com.happyfaceapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.happyfaceapp.R;
import com.happyfaceapp.adapters.MessageCardsAdapter;
import com.happyfaceapp.helpers.CallbackRetrofit;
import com.happyfaceapp.helpers.Loading;
import com.happyfaceapp.helpers.RetrofitModel;
import com.happyfaceapp.models.gifts.messages.DataItem;
import com.happyfaceapp.models.gifts.messages.MessageGiftResponse;
import com.happyfaceapp.models.gifts.messages.MessageInput;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class MessageBottomSheet extends BottomSheetDialogFragment {
    @BindView(R.id.fromText)
    TextInputEditText fromText;
    @BindView(R.id.toText)
    TextInputEditText toText;
    @BindView(R.id.messageText)
    EditText messageText;
    @BindView(R.id.chooseCardText)
    TextView chooseCardText;
    @BindView(R.id.save)
    CardView save;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cards)
    RecyclerView cardsRecycler;
    private List<DataItem> messageCards;
    private MessageCardsAdapter messageCardsAdapter;
    private MessageInput messageInput;
    private MessageListener messageListener;
    Loading loading;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        loading=new Loading(getActivity());
        if (messageInput == null)
            messageInput = new MessageInput();
        else {
            fromText.setText(messageInput.getFrom());
            toText.setText(messageInput.getTo());
            messageText.setText(messageInput.getMessage());
        }
        messageCards = new ArrayList<>();
        messageCardsAdapter = new MessageCardsAdapter(getContext(), messageCards, pos -> {
            if (pos < messageCards.size() && pos > -1) {
                float total = Float.parseFloat(messageCards.get(pos).getPrice());
                chooseCardText.setText(String.format(Locale.getDefault(),
                        getString(R.string.choose_from_cards), new DecimalFormat("#.#").format(total)));
                messageInput.setCoverId(messageCards.get(pos).getId());
            } else {
                chooseCardText.setText(String.format(Locale.getDefault(),
                        getString(R.string.choose_from_cards), new DecimalFormat("#.#").format(0)));
                messageInput.setCoverId(-1);
            }
        });
        cardsRecycler.setAdapter(messageCardsAdapter);
        chooseCardText.setText(String.format(Locale.getDefault(),
                getString(R.string.choose_from_cards), new DecimalFormat("#.#").format(0)));
        getMessageCards();
        toolbar.setNavigationOnClickListener(v -> dismiss());
        save.setOnClickListener(v -> {
            messageInput.setFrom(fromText.getText().toString()+"");
            messageInput.setTo(toText.getText().toString()+"");
            messageInput.setMessage(messageText.getText().toString()+"");
            messageListener.onSaveClick(messageInput);
            dismiss();
        });
    }


    public static MessageBottomSheet getInstance(MessageInput messageInput, MessageListener messageListener) {
        MessageBottomSheet messageBottomSheet = new MessageBottomSheet();
        messageBottomSheet.messageInput = messageInput;
        messageBottomSheet.messageListener = messageListener;
        return messageBottomSheet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    void getMessageCards() {
        if (loading!=null){
            loading.show();
        }
        Call<MessageGiftResponse> call = RetrofitModel.getApi(getActivity()).getMessages();
        call.enqueue(new CallbackRetrofit<MessageGiftResponse>(getActivity()) {
            @Override
            public void onResponse(@NotNull Call<MessageGiftResponse> call, @NotNull Response<MessageGiftResponse> response) {

                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    messageCards.clear();
                    messageCards.addAll(response.body().getData());
                    for (int i = 0; i < messageCards.size(); i++) {
                        DataItem message = messageCards.get(i);
                        if (messageInput != null && messageInput.getCoverId() == message.getId()) {
                            messageCardsAdapter.setCheckedIndex(i);
                            chooseCardText.setText(String.format(Locale.getDefault(),
                                    getString(R.string.choose_from_cards), new DecimalFormat("#.#").format(Double.parseDouble(message.getPrice()))));
                            break;
                        }
                    }
                    messageCardsAdapter.notifyDataSetChanged();


                }
                if (loading!=null&&loading.isShowing()){
                    loading.dismiss();
                }
            }
        });

    }

    public interface MessageListener {
        void onSaveClick(MessageInput messageInput);
    }
}
