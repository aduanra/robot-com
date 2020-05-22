package fr.atech.robotcom.ui.radiocommande;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import fr.atech.robotcom.R;

public class RadioCommandeFragment extends Fragment {

    private RadioCommandeViewModel radioCommandeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        radioCommandeViewModel =
                ViewModelProviders.of(this).get(RadioCommandeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_radiocommande, container, false);
        final TextView textView = root.findViewById(R.id.text_radiocommande);
        radioCommandeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
