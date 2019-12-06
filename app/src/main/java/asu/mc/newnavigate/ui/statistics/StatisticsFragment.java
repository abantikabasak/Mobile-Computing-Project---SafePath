package asu.mc.newnavigate.ui.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import asu.mc.newnavigate.Statistics;

public class StatisticsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Intent i = new Intent(getActivity(), Statistics.class);
        startActivity(i);
        return null;

    }
}
