package asu.mc.newnavigate.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import asu.mc.newnavigate.HomeActivity;
import asu.mc.newnavigate.MainActivity;
import asu.mc.newnavigate.R;
import asu.mc.newnavigate.Signup;
import com.google.firebase.auth.FirebaseAuth;
import asu.mc.newnavigate.ui.tools.ToolsViewModel;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LogoutFragment extends Fragment {

    //private ViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Button btnlogout;
        FirebaseAuth mFirebaseAuth = null;
        FirebaseAuth.AuthStateListener mAuthStateListener;
        mFirebaseAuth.getInstance().signOut();
        Intent i = new Intent(getActivity(), Signup.class);
        startActivity(i);
        return null;

    }
}






