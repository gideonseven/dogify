package com.gideon.dogify.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.gideon.dogify.db.DogifyDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

internal actual fun Scope.createDriver(databaseName: String): SqlDriver = AndroidSqliteDriver(
    DogifyDatabase.Schema, androidContext(), databaseName
)