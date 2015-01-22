package ch.avendia.passabene;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import ch.avendia.passabene.api.PassabeneService;
import ch.avendia.passabene.wifi.CoopWifiManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ch.avendia.passabene.LoginFragment.OnLoginFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView usernameTV;
    private TextView passwordTV;

    private OnLoginFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(OnLoginFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_login, container, false);

        usernameTV = (TextView)mView.findViewById(R.id.username);
        passwordTV = (TextView)mView.findViewById(R.id.password);

        Button button =(Button)mView.findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                String username = usernameTV.getText().toString();
                String password = passwordTV.getText().toString();

                boolean cancel = false;
                View focusView = null;


                // Check for a valid password, if the user entered one.
                if (TextUtils.isEmpty(password) || !TextUtils.isDigitsOnly(password) || password.length() != 4) {
                    passwordTV.setError("Password invalid, only 4 digits");
                    focusView = passwordTV;
                    cancel = true;
                }

                // Check for a valid username address.
                if (TextUtils.isEmpty(username) || username.length() != 13) {
                    usernameTV.setError("Username invalid, has to be 13 digits");
                    focusView = usernameTV;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {

                    ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Login...", "Please Wait ...", true);
                    if(PassabeneService.authenticate(username, password)) {
                        progressDialog.hide();

                        WifiManager wifi = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
                        CoopWifiManager coopWifiManager = new CoopWifiManager(wifi);
                        coopWifiManager.addWifi(username, password);
                        String token = PassabeneService.getToken();
                        if (mListener != null) {
                            mListener.onSuccess();
                        }
                    } else {
                        progressDialog.hide();

                    }


                }
            }
        });


    return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnLoginFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnLoginFragmentInteractionListener {
        public void onSuccess();
    }

}
