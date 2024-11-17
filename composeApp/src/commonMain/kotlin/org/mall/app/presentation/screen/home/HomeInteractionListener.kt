package org.mall.app.presentation.screen.home

import org.mall.app.data.model.Order

interface HomeInteractionListener {
    fun onChangeKeyMapping(key:String , value:String)

    fun onClickGetData()
    fun onDismissError()
    fun onChangeUrl(value: String)
    fun onChangeKeyPath(value: String)
    fun onChangeUsername(value: String)
    fun onChangePassword(value: String)
    fun onChangeBearer(value: String)
    fun onSelectOrder(order: Order)
}