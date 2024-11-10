package cl.tbvl.eva1;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AppState {

    private static AppState instance;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    private ArrayList<String> userData;
    private boolean isLoggedIn;

    private AppState(){

    }

    public static synchronized AppState getInstance(){
        if (instance == null){
            instance = new AppState();
        }
        return instance;
    }

    public ArrayList<String> getUserData() {
        return userData;
    }

    public void setUserData(ArrayList<String> userData) {
        this.userData = userData;
    }

    public boolean isLoggedIn(){
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn){
        isLoggedIn = loggedIn;
    }

    public boolean userLogin(String email, String password){
        auth.signInWithEmailAndPassword(
                email, password
        ).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setLoggedIn(true);
                            ArrayList<String> userData = new ArrayList<>();
                            userData.add(email);
                            userData.add(password);
                            setUserData(userData);
                        }else {
                            setLoggedIn(false);
                        }
                    }
                }
        );
        return isLoggedIn();
    }

}



