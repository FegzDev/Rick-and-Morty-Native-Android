package com.lawpavilion.rick_and_morty.core.data.remote.error_handling

import com.lawpavilion.rick_and_morty.core.data.remote.models.RemoteData
import com.lawpavilion.rick_and_morty.core.domain.error_handling.Result

typealias RemoteResult<D> = Result<RemoteData<D>, RemoteError>