package com.abapps.hotel.domain.util

open class HotelException(override val message: String?) : Exception(message)

open class UnAuthException(override val message: String?) : HotelException(message)

class NetworkException(override val message: String?) : HotelException(message)

class PermissionDeniedException(override val message: String?) : UnAuthException(message)

class NotFoundException(override val message: String?) : HotelException(message)

class ValidationNetworkException(override val message: String?) : HotelException(message)

open class ValidationException(override val message: String?) : HotelException(message)

class EmptyDataException(override val message: String?) : HotelException(message)

class UnknownErrorException(override val message: String?) : HotelException(message)

class ServerErrorException(override val message: String?) : HotelException(message)