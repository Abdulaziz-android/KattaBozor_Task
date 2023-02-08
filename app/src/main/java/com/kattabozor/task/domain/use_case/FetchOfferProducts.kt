package com.kattabozor.task.domain.use_case

import android.util.Log
import com.kattabozor.task.R
import com.kattabozor.task.common.Resource
import com.kattabozor.task.domain.model.Offer
import com.kattabozor.task.domain.repository.ProductRepository
import com.kattabozor.task.util.NetworkHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class FetchOfferProducts(
    private val repository: ProductRepository,
    private val networkHelper: NetworkHelper
) {

    private val TAG = "GetRemoteProducts"
    operator fun invoke() : Flow<Resource<List<Offer>>> = flow{
        try {
            emit(Resource.Loading())
            if (networkHelper.isNetworkConnected()){
                val offers = repository.getRemoteOfferProducts().offers
                emit(Resource.Success(offers))
            }else{
                emit(Resource.Error<List<Offer>>(R.string.internet_unavailable))
            }
        } catch (e: HttpException) {
            Log.d(TAG, "HttpException: ${e.localizedMessage}")
            when(e.code()){
                500 -> emit(Resource.Error<List<Offer>>(R.string.server_error))
                else -> emit(Resource.Error<List<Offer>>(R.string.an_error_occurred))
            }
        } catch (e: IOException) {
            emit(Resource.Error<List<Offer>>(R.string.could_not_connect_to_server))
        } catch (e: Exception){
            Log.d(TAG, "Exception: ${e.localizedMessage}")
            emit(Resource.Error<List<Offer>>(R.string.an_error_occurred))
        }
    }

}