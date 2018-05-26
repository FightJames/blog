package com.techapp.james.chatroomdemo.view.userSetting

import android.os.AsyncTask
import com.techapp.james.chatroomdemo.model.userModel.CurrentUser
import com.techapp.james.firebasechainofresponsibility.model.firebaseChain.FirebaseFacade

/**
 * Created by James on 2018/4/23.
 */
class AsyncSave : AsyncTask<Unit,Unit,Unit> {
    override fun doInBackground(vararg params: Unit?) {
        FirebaseFacade.saveUser(CurrentUser.dbUser!!)
    }

    override fun onProgressUpdate(vararg values: Unit?) {
        super.onProgressUpdate(*values)
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
    }

    constructor()
}