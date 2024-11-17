package org.mall.app.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> EventHandler(
    effects: Flow<T>,
    handleEffect: (T, NavHostController) -> Unit,
) {
    val navController = rememberNavController()
    LaunchedEffect(key1 = Unit) {
        effects.collectLatest { effect ->
            handleEffect(effect, navController)
        }
    }
}