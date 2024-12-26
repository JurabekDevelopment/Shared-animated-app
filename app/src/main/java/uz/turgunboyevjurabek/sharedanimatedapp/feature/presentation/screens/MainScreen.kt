@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalSharedTransitionApi::class)

package uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uz.turgunboyevjurabek.sharedanimatedapp.core.utils.ConstItems.FAB_EXPLODE_BOUNDS_KEY

@Composable
fun SharedTransitionScope.MainScreen(
    fabColor: Color,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = onFabClick,
                containerColor = fabColor,
                modifier = Modifier
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(
                            key = FAB_EXPLODE_BOUNDS_KEY
                        ),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            ) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = null,
                    modifier = modifier
                        .size(50.dp)
                )
            }
        }
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(40) {
                ItemCard(modifier = Modifier.padding(8.dp))
            }
        }
    }

}

@Composable
fun ItemCard(modifier: Modifier) {
    val randomHeight = (100..300).random()
    Card(
        modifier = modifier
            .height(randomHeight.dp)
    ) {
        Text("Item")
    }
}
