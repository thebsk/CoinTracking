package com.bsk.cointracker.di.qualifiers

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CryptoAPI

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScopeIO
