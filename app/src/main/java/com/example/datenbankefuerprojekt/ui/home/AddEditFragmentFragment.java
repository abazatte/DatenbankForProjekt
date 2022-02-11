package com.example.datenbankefuerprojekt.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datenbankefuerprojekt.R;
import com.example.datenbankefuerprojekt.databinding.FragmentAddEditFragmentBinding;

public class AddEditFragmentFragment extends Fragment {

    private EditText editTextTitel;
    private EditText editTextPrio;
    private EditText editTextEinAtmen;
    private EditText editTextLuftAnhalt;
    private EditText editTextAusAtem;
    private EditText editTextLuftAusHalt;
    private EditText editTextWiederholungen;
    private FragmentAddEditFragmentBinding binding;
    private HomeViewModel homeViewModel;
    private boolean isEdit = false;
    private int id;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        homeViewModel = new HomeViewModel(getActivity().getApplication());
        setHasOptionsMenu(true);
        binding = FragmentAddEditFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        editTextTitel = binding.editTextTitle;
        editTextPrio = binding.numberPickerPriority;
        editTextEinAtmen = binding.einatmenZeit;
        editTextLuftAnhalt = binding.luftAnhaltZeit;
        editTextAusAtem = binding.ausatemZeit;
        editTextLuftAusHalt = binding.luftAusAnhaltZeit;
        editTextWiederholungen = binding.counter;

        bundle = getArguments();

        ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("Add Fragment");
        if (bundle != null){
            id = bundle.getInt(HomeFragment.EXTRA_ID, -1);
            if (id != -1){
                ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("Edit Fragment");
                editTextTitel.setText(bundle.getString(HomeFragment.EXTRA_TITEL));
                editTextPrio.setText(Integer.toString(bundle.getInt(HomeFragment.EXTRA_PRIO)));
                editTextEinAtmen.setText(Integer.toString(bundle.getInt(AddEditUebungFragment.EXTRA_EIN)));
                editTextLuftAnhalt.setText(Integer.toString(bundle.getInt(AddEditUebungFragment.EXTRA_LUFTEIN)));
                editTextAusAtem.setText(Integer.toString(bundle.getInt(AddEditUebungFragment.EXTRA_AUS)));
                editTextLuftAusHalt.setText(Integer.toString(bundle.getInt(AddEditUebungFragment.EXTRA_LUFTAUS)));
                editTextWiederholungen.setText(Integer.toString(bundle.getInt(AddEditUebungFragment.EXTRA_COUNT)));

                isEdit = true;
            }
        }

        return root;
    }

    private void saveFragment() {
        String titel = editTextTitel.getText().toString();
        int prio = Integer.parseInt(editTextPrio.getText().toString());
        int einatmen = Integer.parseInt(editTextEinAtmen.getText().toString());
        int luftein = Integer.parseInt(editTextLuftAnhalt.getText().toString());
        int ausatmen = Integer.parseInt(editTextAusAtem.getText().toString());
        int luftaus = Integer.parseInt(editTextLuftAusHalt.getText().toString());
        int count = Integer.parseInt(editTextWiederholungen.getText().toString());
        int uebungId = bundle.getInt(AddEditUebungFragment.EXTRA_UEBUNG_ID);

        if (titel.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Bitte einen Titel eingeben", Toast.LENGTH_SHORT).show();
        }

        com.example.datenbankefuerprojekt.db.main.database.Fragment fragment = new com.example.datenbankefuerprojekt.db.main.database.Fragment(titel,uebungId,einatmen,luftein,ausatmen,luftaus,count, prio);
        homeViewModel.insertFragment(fragment);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_home, new HomeFragment());
        ((AppCompatActivity) getContext()).getSupportActionBar().setTitle(R.string.uebung_home);
        fragmentTransaction.commit();
    }

    private void updateFragment(){
        String titel = editTextTitel.getText().toString();
        int prio = Integer.parseInt(editTextPrio.getText().toString());
        int einatmen = Integer.parseInt(editTextEinAtmen.getText().toString());
        int luftein = Integer.parseInt(editTextLuftAnhalt.getText().toString());
        int ausatmen = Integer.parseInt(editTextAusAtem.getText().toString());
        int luftaus = Integer.parseInt(editTextLuftAusHalt.getText().toString());
        int count = Integer.parseInt(editTextWiederholungen.getText().toString());
        int uebungId = bundle.getInt(AddEditUebungFragment.EXTRA_UEBUNG_ID);

        if (titel.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Bitte einen Titel eingeben", Toast.LENGTH_SHORT).show();
        }

        com.example.datenbankefuerprojekt.db.main.database.Fragment fragment = new com.example.datenbankefuerprojekt.db.main.database.Fragment(titel,uebungId,einatmen,luftein,ausatmen,luftaus,count, prio);
        fragment.setId(id);
        homeViewModel.updateFragment(fragment);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_home, new HomeFragment());
        ((AppCompatActivity) getContext()).getSupportActionBar().setTitle(R.string.uebung_home);
        fragmentTransaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_fragment:
                if (isEdit){
                    updateFragment();
                } else {
                    saveFragment();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}