package com.simplified.qr_barcode_scanner.screens.create.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simplified.qr_barcode_scanner.screens.create.list_data.CreateItem
import kotlinx.coroutines.launch

@Composable
fun CreateMenu() {
    val items = CreateItem.entries.toTypedArray()
    val presetItems = items.take(11)
    val barcodeItems = items.drop(11)

    var selectedItem by remember { mutableStateOf<CreateItem?>(null) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {

        item(key = "header_presets") {
            ListHeader("Presets")
        }

        items(
            items = presetItems,
            key = { it.name }
        ) { item ->
            CreateMenuItem(
                item = item,
                isSelected = item == selectedItem,
                onClick = {
                    selectedItem = item

                    scope.launch {
                        item.action()
                    }
                }
            )
        }

        item(key = "header_barcodes") {
            ListHeader("Barcodes")
        }

        items(
            items = barcodeItems,
            key = { it.name }
        ) { item ->
            CreateMenuItem(
                item = item,
                isSelected = item == selectedItem,
                onClick = {
                    selectedItem = item

                    scope.launch {
                        item.action()
                    }
                }
            )
        }
    }
}


@Composable
private fun ListHeader(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            color = Color.Gray,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 20.dp, bottom = 20.dp)
        )
    }

}

@Composable
private fun CreateMenuItem(
    item: CreateItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val backgroundColor =
        if (isSelected) Color.DarkGray else Color.Transparent

    val textColor =
        if (isSelected) Color.White else Color.LightGray

    val borderColor =
        if (isSelected) Color.Transparent else Color.Gray.copy(alpha = 0.3f)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(50))
            .border(1.dp, borderColor, RoundedCornerShape(50))
            .background(backgroundColor)
            // 🔥 INSTANT CLICK — NO RIPPLE — NO DELAY
            .clickable(
                interactionSource = interactionSource,
                indication = null, // ← KILLS THE DELAY
                onClick = onClick
            )
            .padding(12.dp)
    ) {
        Image(
            painter = painterResource(item.icon),
            contentDescription = item.title,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = item.title,
            color = textColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
@Preview(showBackground = true, device = "id:Nexus S", backgroundColor = 0xFF1B1A19)
fun CreateMenuPreview() {
    CreateMenu()
}
