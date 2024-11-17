package org.mall.app.presentation.screen.setup

import org.mall.app.data.model.Order

interface SetupInteractionListener{
    fun onChangeKeyMapping(key:String , value:String)

    fun onDismissError()
    fun onChangeUrl(value: String)
    fun onChangeKeyPath(value: String)
    fun onChangeUsername(value: String)
    fun onChangeHeader(index: Int, value: Pair<String, String>)
    fun onRemoveHeader(index: Int)
    fun addNewHeader()
    fun onChangeOperation(value:Operation)
    fun onChangePassword(value: String)
    fun onChangeBearer(value: String)
}