package com.smartway.e_canteen.Service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.smartway.e_canteen.Common.Common;
import com.smartway.e_canteen.Common.ServerCommon;
import com.smartway.e_canteen.Model.Token;


/**
 * Created by Darshan Patel on 10-02-2018.
 */

public class ServerMyFirebaseIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String tokenRefreshed = FirebaseInstanceId.getInstance().getToken();
        if (ServerCommon.currentUser != null)
            updateToServer(tokenRefreshed);
    }

    private void updateToServer(String tokenRefreshed) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token = new Token(tokenRefreshed, true);
        tokens.child(ServerCommon.currentUser.getPhone()).setValue(token);
    }
}
