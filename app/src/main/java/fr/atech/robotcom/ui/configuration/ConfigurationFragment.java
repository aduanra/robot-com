package fr.atech.robotcom.ui.configuration;

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

public class ConfigurationFragment extends Fragment {

    private ConfigurationViewModel configurationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        configurationViewModel =
                ViewModelProviders.of(this).get(ConfigurationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_configuration, container, false);
        final TextView textView = root.findViewById(R.id.editText_config_ip_robot);
        configurationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
