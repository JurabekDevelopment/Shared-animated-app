package uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.madels.Item
import uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.view_models.RoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernDropdownMenu(
    expanded: Boolean,
    item: Item,
    viewModel:RoomViewModel = koinViewModel(),
    onDismissRequest: () -> Unit = {},
    onExpandedChange: (Boolean) -> Unit = {}
) {
    var selectedItem by remember { mutableStateOf("") }
    val scope= rememberCoroutineScope ()

    // ExposedDropdownMenuBox yordamida menu joylashuvini boshqarish
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) {
        // Placeholder yoki boshqa tugmalarni qo'shishingiz mumkin
        Spacer(
            modifier = Modifier
                .menuAnchor() // Bu yerda menu joylashuvini boshqaradi
                .clickable {
                    onExpandedChange(!expanded) // Menyuni ochish yoki yopish
                }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            DropdownMenuItem(
                text = { Text("Edit item") },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Edit, contentDescription = null)
                },
                onClick = {
                    selectedItem = "edit"
                    onDismissRequest() // Menyuni yopish

                }
            )
            DropdownMenuItem(
                text = { Text("Delete item") },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Delete, contentDescription = null)
                },
                onClick = {
                    selectedItem = "delete"
                    onDismissRequest()
                    scope.launch {
                        viewModel.deleteItem(item)
                    }
                }
            )


        }
    }
}
