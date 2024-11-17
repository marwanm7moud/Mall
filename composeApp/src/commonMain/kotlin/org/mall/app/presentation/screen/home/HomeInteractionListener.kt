package org.mall.app.presentation.screen.home

import org.mall.app.data.model.Order

interface HomeInteractionListener {

    fun onClickGetData()
    fun onDismissError()
    fun onSelectOrder(order: Order)
}