@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalSharedTransitionApi::class)

package uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import uz.turgunboyevjurabek.sharedanimatedapp.R
import uz.turgunboyevjurabek.sharedanimatedapp.core.utils.ConstItems.FAB_EXPLODE_BOUNDS_KEY
import uz.turgunboyevjurabek.sharedanimatedapp.core.utils.Status
import uz.turgunboyevjurabek.sharedanimatedapp.core.utils.Status.*
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.madels.Item
import uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.view_models.RoomViewModel

@Composable
fun SharedTransitionScope.MainScreen(
    fabColor: Color,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onFabClick: () -> Unit,
    viewModel: RoomViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {

    val roomViewModel by viewModel.getAllItems.collectAsStateWithLifecycle()

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
        when(roomViewModel.status){
            DEFAULT, LOADING -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            ERROR -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text("Error -> ${roomViewModel.message}")
                }
            }
            SUCCESS -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    roomViewModel.data?.let { items ->
                        items(items.size) { index ->
                            ItemCard(
                                item = items[index],
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    } ?: run {
                        // Agar data null bo'lsa, boshqa UI ko'rsatish
                        item {
                            Text(
                                text = "No data available",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

            }
        }


    }

}

@Composable
fun ItemCard(
    item: Item,
    modifier: Modifier
) {
    val context = LocalContext.current
//    val randomHeight = (100..300).random()
    val imageLoader = ImageLoader.Builder(context)
        .error(R.drawable.ic_launcher_foreground)
        .crossfade(500)
        .placeholder(R.drawable.img)
        .memoryCachePolicy(CachePolicy.ENABLED) //Keshlash
        .build()
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data(item.imageUrl)
            .build(),
        imageLoader = imageLoader
    )

    Surface(
        modifier = modifier
            .height(250.dp)
    ) {
        Column {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(150.dp)
            )
//            Spacer(modifier = modifier.height(12.dp))
            Text(item.title)
            Text(item.description)
        }
    }
}
