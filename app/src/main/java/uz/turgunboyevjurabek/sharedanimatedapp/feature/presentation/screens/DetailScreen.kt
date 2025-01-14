package uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import uz.turgunboyevjurabek.sharedanimatedapp.R
import uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.navigation.DetailRout

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailScreen(
    detailRout: DetailRout,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val request = ImageRequest.Builder(context)
        .data(detailRout.imageUrl)
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
            .data(detailRout.imageUrl)
            .build(),
        imageLoader = imageLoader
    )
    Scaffold {innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = detailRout.id.toString()
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform ={initalBounds, finalBounds ->
                        spring(
                            dampingRatio = 0.8f,
                            stiffness = 380f
                        )
                    }
                )
                .fillMaxSize()
        ) {
            Spacer(modifier.height(30.dp))
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = modifier
//                    .clip(Shapes().large)
                    .fillMaxWidth()

            )
            Text(
                "${detailRout.title}",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Black,
                modifier = modifier.padding(10.dp)
            )
            HorizontalDivider(
                thickness = 5.dp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .padding(horizontal = 10.dp)
                    .clip(shape = Shapes().medium)
            )
            Text(
                "${detailRout.description}",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.padding(10.dp)
            )
            Spacer(modifier.height(30.dp))

        }

    }



}