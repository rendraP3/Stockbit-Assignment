package com.stockbit.hiring.di

import com.sandbox.domain.di.domainModule
import com.stockbit.assignment.di.assignmentModule
import com.stockbit.local.di.localModule
import com.stockbit.remote.di.remoteModule
import com.stockbit.repository.di.repositoryModule

val appComponent= listOf(
    remoteModule,
    repositoryModule,
    localModule,
    domainModule,
    assignmentModule
)