@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalSharedTransitionApi::class)

package uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import uz.turgunboyevjurabek.sharedanimatedapp.R
import uz.turgunboyevjurabek.sharedanimatedapp.core.utils.ConstItems.FAB_EXPLODE_BOUNDS_KEY
import uz.turgunboyevjurabek.sharedanimatedapp.core.utils.Status
import uz.turgunboyevjurabek.sharedanimatedapp.core.utils.Status.*
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.madels.Item
import uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.view_models.RoomViewModel
import coil.imageLoader
import uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.components.ModernDropdownMenu
import uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.navigation.DetailRout

@Composable
fun SharedTransitionScope.MainScreen(
    fabColor: Color,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onFabClick: () -> Unit,
    viewModel: RoomViewModel = koinViewModel(),
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val roomViewModel by viewModel.getAllItems.collectAsStateWithLifecycle()


    val itemList = ArrayList<Item>()
    roomViewModel.data?.let { itemList.addAll(it.toMutableList()) }
    val lazyListState = rememberLazyStaggeredGridState()

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
                    modifier = modifier.size(50.dp)
                )
            }
        }
    ) { innerPadding ->
        when (roomViewModel.status) {
            DEFAULT, LOADING -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }

            ERROR -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Error -> ${roomViewModel.message}")
                }
            }
            SUCCESS -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    state = lazyListState,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    items(itemList, key = { item -> item.id}) { item ->
                            ItemCard(
                                item = item,
                                onItemClick = {
                                    navHostController.navigate(
                                        DetailRout(
                                            id = item.id,
                                            title = item.title,
                                            description = item.description,
                                            imageUrl = item.imageUrl
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .padding(10.dp)
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState(
                                            key = "${item.id}"
                                        ),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform ={initalBounds, finalBounds ->
                                            spring(
                                                dampingRatio = 0.8f,
                                                stiffness = 380f
                                            )
                                        }
                                    )
                            )
                        }
                    }
                }
            }
        }
    }

@Composable
fun ItemCard(
    item: Item,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var imageWidth by remember { mutableStateOf(0) }
    var imageHeight by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }


    val request = ImageRequest.Builder(context)
        .data(item.imageUrl)
        .crossfade(true)
        .build()

    val imageLoader = ImageLoader.Builder(context)
        .error(R.drawable.ic_launcher_foreground)
        .crossfade(500)
        .placeholder(R.drawable.img)
        .memoryCachePolicy(CachePolicy.ENABLED) // Xotira keshlashni yoqish
        .diskCachePolicy(CachePolicy.ENABLED) // Disk keshlashni yoqish
        .build()
    imageLoader.enqueue(request)

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data(item.imageUrl)
            .build(),
        imageLoader = imageLoader
    )

    if (expanded){
        ModernDropdownMenu(
            expanded = expanded,
            item=item,
            onDismissRequest = { expanded = false },
            onExpandedChange = { expanded = it }
        )
    }
    Surface(
        onClick = {
            onItemClick()
        },
        shape = Shapes().extraLarge,
        tonalElevation = 1.dp,
        modifier = modifier
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(
                        painter.intrinsicSize.width / painter.intrinsicSize.height
                    )
                    .clip(Shapes().large)
            )
            Spacer(modifier = Modifier.height(7.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Serif,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth(fraction = 0.72f)
                )
                IconButton(
                    onClick = {
                        expanded = !expanded
                    },
                    modifier = Modifier
                        .padding(end = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        modifier = Modifier.rotate(90f)
                    )
                }

            }
        }
    }
}
