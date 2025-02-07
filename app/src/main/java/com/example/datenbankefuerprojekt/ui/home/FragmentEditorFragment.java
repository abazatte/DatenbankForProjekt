package com.example.datenbankefuerprojekt.ui.home;

import static com.example.datenbankefuerprojekt.ui.home.HomeFragment.EXTRA_ID;
import static com.example.datenbankefuerprojekt.ui.home.HomeFragment.EXTRA_PRIO;
import static com.example.datenbankefuerprojekt.ui.home.HomeFragment.EXTRA_TITEL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
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

public class FragmentEditorFragment extends Fragment {
    private static String TAG = "FragmentEDITOR";

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
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        setHasOptionsMenu(true);
        binding = FragmentAddEditFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initUI();

        bundle = getArguments();



        //((AppCompatActivity) getContext()).getSupportActionBar().setTitle("Add Fragment");
        if (bundle != null){
            id = bundle.getInt(EXTRA_ID, -1);
            if (id != -1){
                //((AppCompatActivity) getContext()).getSupportActionBar().setTitle("Edit Fragment");
                editTextTitel.setText(bundle.getString(EXTRA_TITEL));
                editTextPrio.setText(Integer.toString(bundle.getInt(EXTRA_PRIO)));
                editTextEinAtmen.setText(Integer.toString(bundle.getInt(UebungEditorFragment.EXTRA_EIN)));
                editTextLuftAnhalt.setText(Integer.toString(bundle.getInt(UebungEditorFragment.EXTRA_LUFTEIN)));
                editTextAusAtem.setText(Integer.toString(bundle.getInt(UebungEditorFragment.EXTRA_AUS)));
                editTextLuftAusHalt.setText(Integer.toString(bundle.getInt(UebungEditorFragment.EXTRA_LUFTAUS)));
                editTextWiederholungen.setText(Integer.toString(bundle.getInt(UebungEditorFragment.EXTRA_FRAGMENT_COUNT)));

                Toast.makeText(getActivity(), "Titel: " + editTextTitel.getText().toString() + "Prio: " + editTextPrio.getText().toString() , Toast.LENGTH_LONG).show();

                isEdit = true;
            }
        }

        //sweetHome = homeViewModel.getUebungById(bundle.getInt(AddEditUebungFragment.EXTRA_UEBUNG_ID)).get(10,);

        return root;
    }

    /**
     * @author Abdurrahman Azattemür, Maximilian Jaesch
     * <p>hier werden die UI Elemente von dem Binding mit den XML elementen verbunden</p>
     * */
    private void initUI(){
        editTextTitel = binding.editTextTitle;
        editTextPrio = binding.numberPickerPriority;
        editTextEinAtmen = binding.einatmenZeit;
        editTextLuftAnhalt = binding.luftAnhaltZeit;
        editTextAusAtem = binding.ausatemZeit;
        editTextLuftAusHalt = binding.luftAusAnhaltZeit;
        editTextWiederholungen = binding.counter;
    }

    private void saveFragment() {
        //hier wird aus den editTexts die Werte entnommen
        if (isAFieldEmpty()) {
            Toast.makeText(getActivity(), "Bitte jedes Feld ausfüllen", Toast.LENGTH_SHORT).show();
            return;
        }

        String titel = editTextTitel.getText().toString();
        int prio = Integer.parseInt(editTextPrio.getText().toString());
        int einatmen = Integer.parseInt(editTextEinAtmen.getText().toString());
        int luftein = Integer.parseInt(editTextLuftAnhalt.getText().toString());
        int ausatmen = Integer.parseInt(editTextAusAtem.getText().toString());
        int luftaus = Integer.parseInt(editTextLuftAusHalt.getText().toString());
        int count = Integer.parseInt(editTextWiederholungen.getText().toString());
        //da die id nur intern ist, muss es aus dem Bundle entnommen werden
        int uebungId = bundle.getInt(UebungEditorFragment.EXTRA_UEBUNG_ID);


        //in die datenbank einfügen
        com.example.datenbankefuerprojekt.db.main.database.fragment.Fragment fragment = new com.example.datenbankefuerprojekt.db.main.database.fragment.Fragment(titel,uebungId,einatmen,luftein,ausatmen,luftaus,count, prio);
        homeViewModel.insertFragment(fragment);

        //hier iwie die übung aus datenbank holen und dann mit bundle
        //wir haben ja die übungs id

        //zu vorigen screen zurückkehren
        Navigation.findNavController(binding.getRoot()).popBackStack();
    }

    private void updateFragment(){
        if (isAFieldEmpty()) {
            Toast.makeText(getActivity(), "Bitte einen Titel eingeben", Toast.LENGTH_SHORT).show();
            return;
        }

        String titel = editTextTitel.getText().toString();
        int prio = Integer.parseInt(editTextPrio.getText().toString());
        int einatmen = Integer.parseInt(editTextEinAtmen.getText().toString());
        int luftein = Integer.parseInt(editTextLuftAnhalt.getText().toString());
        int ausatmen = Integer.parseInt(editTextAusAtem.getText().toString());
        int luftaus = Integer.parseInt(editTextLuftAusHalt.getText().toString());
        int count = Integer.parseInt(editTextWiederholungen.getText().toString());
        int uebungId = bundle.getInt(UebungEditorFragment.EXTRA_UEBUNG_ID);



        com.example.datenbankefuerprojekt.db.main.database.fragment.Fragment fragment = new com.example.datenbankefuerprojekt.db.main.database.fragment.Fragment(titel,uebungId,einatmen,luftein,ausatmen,luftaus,count, prio);
        fragment.setFragmentId(id);
        homeViewModel.updateFragment(fragment);

        //zu vorigen screen zurückkehren
        Navigation.findNavController(binding.getRoot()).popBackStack();
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @author Abdurrahman Azattemür, Maximilian Jaesch
     *       <p></p>
     *       Hier wird überprüft ob mindestens ein Feld leer ist.
     * */
    private boolean isAFieldEmpty(){

        return TextUtils.isEmpty(editTextTitel.getText()) ||
                TextUtils.isEmpty(editTextPrio.getText()) ||
                TextUtils.isEmpty(editTextEinAtmen.getText()) ||
                TextUtils.isEmpty(editTextLuftAnhalt.getText()) ||
                TextUtils.isEmpty(editTextAusAtem.getText()) ||
                TextUtils.isEmpty(editTextLuftAusHalt.getText()) ||
                TextUtils.isEmpty(editTextWiederholungen.getText());
    }


}

/* CODE GRAVEYARD:
    private Uebung returnUebung;

    homeViewModel.getUebungById(bundle.getInt(AddEditUebungFragment.EXTRA_UEBUNG_ID)).observe(this, new Observer<Uebung>() {
            @Override
            public void onChanged(Uebung uebung) {
                returnUebung = uebung;
            }
        });

    private void returnToUebungEditor(int uebungId){

        Bundle restoreUebung = new Bundle();

        Log.i(TAG, "returnToUebungEditor: " + uebungId);

        Uebung baseUebung = returnUebung;
        restoreUebung.putInt(EXTRA_ID, baseUebung.getId());
        restoreUebung.putString(EXTRA_TITEL, baseUebung.getTitel());
        restoreUebung.putString(EXTRA_DESC, baseUebung.getBeschreibung());
        restoreUebung.putInt(EXTRA_PRIO, baseUebung.getPrioritaet());
        restoreUebung.putInt(EXTRA_COUNT, baseUebung.getAnzahlDerWiederholungen());

        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_nav_home_add_edit_fragment_to_nav_home_add_edit_uebung, restoreUebung);

    }

    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_home, new HomeFragment());
        ((AppCompatActivity) getContext()).getSupportActionBar().setTitle(R.string.uebung_home);
        fragmentTransaction.commit();

 */