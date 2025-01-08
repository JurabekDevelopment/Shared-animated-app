@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalSharedTransitionApi::class)

package uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.screens

import android.content.Intent
import android.graphics.drawable.Icon
import android.graphics.fonts.Font
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.w3c.dom.Text
import uz.turgunboyevjurabek.sharedanimatedapp.R
import uz.turgunboyevjurabek.sharedanimatedapp.core.utils.ConstItems.FAB_EXPLODE_BOUNDS_KEY
import uz.turgunboyevjurabek.sharedanimatedapp.core.utils.Status
import uz.turgunboyevjurabek.sharedanimatedapp.core.utils.Status.*
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.madels.Item
import uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.view_models.RoomViewModel

@Composable
fun SharedTransitionScope.AddItemScreen(
    fabColor: Color,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: RoomViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val state by viewModel.addItem.collectAsStateWithLifecycle()
    val context= LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(fabColor)
            .fillMaxSize()
            .sharedBounds(
                sharedContentState = rememberSharedContentState(
                    key = FAB_EXPLODE_BOUNDS_KEY
                ),
                animatedVisibilityScope = animatedVisibilityScope,
                zIndexInOverlay = 10f,
                boundsTransform = { initialBounds, targetBounds ->
                    spring(
                        dampingRatio = 0.8f,
                        stiffness = 380f
                    )
                },
            )
    ) {
        DefaultAndSuccessInsertScreen(
            fabColor = fabColor,
            navController = navController,
        )
//        when(state.status){
//            DEFAULT -> {
//                DefaultAndSuccessInsertScreen(
//                    fabColor = fabColor,
//                    navController = navController,
//                )
//                Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show()
//            }
//            LOADING -> {
//                LoadingAddItemScreen()
//                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
//            }
//            ERROR -> {
//                Text("${state.message}")
//                Log.d("error text","${state.message}")
//                Toast.makeText(context, "Error ${state.message}", Toast.LENGTH_SHORT).show()
//            }
//            SUCCESS -> {
//                navController.popBackStack()
//                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}

@Composable
fun LoadingAddItemScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ){
        CircularProgressIndicator()
    }
}

@Composable
fun DefaultAndSuccessInsertScreen(
    fabColor: Color,
    viewModel: RoomViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? ->
                uri?.let {
                    context.contentResolver.takePersistableUriPermission(
                        it, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    selectedImageUri = it
                }
            })

    val scope= rememberCoroutineScope()
    var labelText by rememberSaveable { mutableStateOf("") }
    var descriptionText by rememberSaveable { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(fabColor)
            .fillMaxSize()

    ) {
        AsyncImage(
            model = selectedImageUri ?: R.drawable.img,
            contentDescription = null,
            modifier = modifier
                .statusBarsPadding()
                .clickable { launcher.launch("image/*") }
                .fillMaxWidth()
                .height(300.dp)
        )
        MyTextField(
            text = labelText,
            placeholder = "Item name",
            onValueChange = { labelText = it },
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        MyTextField(
            text = descriptionText,
            placeholder = "Item description",
            onValueChange = { descriptionText = it },
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = {
                if (selectedImageUri != null && labelText.isNotEmpty() && descriptionText.isNotEmpty()) {
                    val item = Item(
                        title = labelText,
                        description = descriptionText,
                        imageUrl = selectedImageUri.toString()
                    )
                    scope.launch {
                        viewModel.insertItem(item)
                    }

                    // Ma'lumotlarni tozalash
                    selectedImageUri = null
                    labelText = ""
                    descriptionText = ""


                } else {
                    Toast.makeText(context, "Ma'lumotlarni to'liq kiriting", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Qoshish", fontFamily = FontFamily.Serif)
        }
    }

}

@Composable
fun MyTextField(
    text: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(placeholder)
        },
        shape = Shapes().medium,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White.copy(0.5f),
        ),
        modifier = modifier
    )
}
