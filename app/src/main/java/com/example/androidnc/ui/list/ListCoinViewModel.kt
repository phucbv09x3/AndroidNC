package com.example.androidnc.ui.list

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidnc.data.local.room.CoinDatabase
import com.example.androidnc.data.local.room.CoinModelRoom
import com.example.androidnc.data.model.CoinModel
import com.example.androidnc.ui.base.BaseViewModel
import com.example.androidnc.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Phuc on 20/1/2021
 */
class ListCoinViewModel(var db: CoinDatabase) : BaseViewModel() {
    val listCoin = MutableLiveData<MutableList<CoinModelRoom>>()
    val isRequest = ObservableBoolean(true)

    companion object {
        const val BASE_URL_COIN = "https://pro-api.coinmarketcap.com"
        const val VALUE_UP = "tăng"
        const val VALUE_DOWN = "giảm"
        const val VALUE_EMPTY = ""
    }

    fun getListCoin() {
        viewModelScope.launch(Dispatchers.Main) {
            processPattern()
        }
    }


    private suspend fun processPattern() {
        val listCoinLocal = db.coiDAO().getListCoinRoom() ?: mutableListOf()
        val listServer = Constants.apiCoinRepository(BASE_URL_COIN).getList().data
        val listCoinApi = listServer ?: mutableListOf()
        val listInsert = mutableListOf<CoinModel>()

        listCoinApi.forEach { server ->
            val findLocal = listCoinLocal.firstOrNull { local ->
                server.id == local.id
            }
            findLocal?.let { model ->
                isRequest.set(false)
                val priceServer = server.quote.USD.price
                val priceLocal = model.price
                val diff = priceLocal != priceServer
                if (diff) {
                    model.isCheckPrice = if (priceServer > priceLocal) VALUE_UP else VALUE_DOWN
                    model.price = priceServer
                    model.isNeedUpdate = true
                }
            } ?: kotlin.run {
                isRequest.set(true)
                listInsert.add(server)
            }
        }
        val listUpdate = listCoinLocal.filter {
            it.isNeedUpdate
        }
        if (listUpdate.isNotEmpty()) {
            db.coiDAO().updateListChange(listUpdate.toMutableList())
        }
        if (listInsert.isNotEmpty()) {
            insert(listInsert)
        }
        listCoin.value = db.coiDAO().getListCoinRoom()
    }

    private suspend fun insert(list: MutableList<CoinModel>) {
        val listInsert = list.map {
            CoinModelRoom(
                it.id, it.name, it.symbol, it.slug, it.quote.USD.price, VALUE_EMPTY
            )
        }.toMutableList()
        db.coiDAO().insertCoin(listInsert)
    }
}