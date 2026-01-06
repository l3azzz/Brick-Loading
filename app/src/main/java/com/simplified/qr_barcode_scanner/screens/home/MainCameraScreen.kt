package com.simplified.qr_barcode_scanner.screens.home


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.simplified.qr_barcode_scanner.R
import com.simplified.qr_barcode_scanner.data.datastore.PublicViewModel
import com.simplified.qr_barcode_scanner.screens.home.components.AnimatedLoader
import com.simplified.qr_barcode_scanner.screens.home.components.BottomBar
import com.simplified.qr_barcode_scanner.screens.home.components.ScanAreaBox
import com.simplified.qr_barcode_scanner.screens.home.components.TopBar
import com.simplified.qr_barcode_scanner.utils.mlkit.QrContentClassifier
import com.simplified.qr_barcode_scanner.utils.mlkit.QrContentType
import com.simplified.qr_barcode_scanner.utils.mlkit.QrFunction
import com.simplified.qr_barcode_scanner.utils.mlkit.QrFunctionAnalyzer
import com.simplified.qr_barcode_scanner.utils.mlkit.QrFunctionExecutor
import com.simplified.qr_barcode_scanner.utils.mlkit.QrScanner
import com.simplified.qr_barcode_scanner.utils.mlkit.QrValueParser
import com.simplified.qr_barcode_scanner.utils.mlkit.getDrawable
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainCameraScreen(
    publicViewModel: PublicViewModel,
    navController: NavController? = null,
    onMenuClick: () -> Unit
) {


    // -CameraX Controller Start


    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or CameraController.IMAGE_ANALYSIS
            )
        }

    }

    // -CameraX Controller End


    val interactionSource = remember { MutableInteractionSource() }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {


        //Scanner
        var isConnected by remember { mutableStateOf(false) }
        var scanned by remember { mutableStateOf(false) }


        val executor = remember {
            Executors.newSingleThreadExecutor()
        }
//--------------------------------------------------------------------------------------------------------------------------------------------------

        var time by remember { mutableStateOf("") }
        var date by remember { mutableStateOf("") }

        var qrFormat by remember { mutableStateOf("") }
        var qrRawContent by remember { mutableStateOf("") }
        var qrContentTypes by remember { mutableStateOf<List<QrContentType?>>(emptyList()) }
        var parsedQrContent: String by remember { mutableStateOf("") }

        var imageReference by remember { mutableStateOf("") }

        var qrFunctions by remember { mutableStateOf<List<QrFunction?>>(emptyList()) }
        var qrContentTypeIcon: Int by remember { mutableStateOf(R.drawable.unknown) }


        val scanner = QrScanner(
            onQrScanned = { rawValue, format ->
                qrRawContent = rawValue
                qrFormat = format.name
                qrContentTypes = QrContentClassifier.classify(qrRawContent)
                qrFunctions = QrFunctionAnalyzer.analyze(qrContentTypes as List<QrContentType>)
                parsedQrContent = QrValueParser.parse(
                    qrRawContent,
                    qrContentTypes as List<QrContentType>
                )
                qrContentTypeIcon = getDrawable(qrContentTypes as List<QrContentType>)

                val (currentDate, currentTime) = captureTimeAndDate()
                date = currentDate
                time = currentTime



                scanned = true
                publicViewModel.setQrData(
                    time = time,
                    date = date,
                    qrRawContent = qrRawContent,
                    qrFormat = qrFormat,
                    qrContentTypes = qrContentTypes as List<QrContentType>,
                    parsedContent = parsedQrContent,
                    qrFunctions = qrFunctions as List<QrFunction>,
                    qrContentTypeIcon = qrContentTypeIcon,
                    imageReference = imageReference.ifEmpty { null }
                )

            },
            zoomCallback = { zoomRatio: Float ->
                controller.cameraControl?.setZoomRatio(zoomRatio)
                true
            }
//            isConnected = { isConnected }
        )

        controller.setImageAnalysisAnalyzer(executor, scanner)
        CameraPreview(
            controller,
            modifier = Modifier.fillMaxSize()
        )
        //Scanner


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            TopBar(
                navController = navController,
                onMenuClick,
                controller = controller,
                publicViewModel
            )
            ScanAreaBox()


            //-------------------------------------------------Bottom Capture Area
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                        .background(
                            Color.Black.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                        )
                ) {
                    if (!scanned) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.50f)
                        ) {
                            AnimatedLoader()
                        }
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.95f)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .drawBehind {
                                        val strokeWidth = 1.dp.toPx() // very thin line
                                        drawLine(
                                            color = Color(241, 243, 244),
                                            start = Offset(
                                                size.width - strokeWidth / 2,
                                                0f
                                            ), // right edge
                                            end = Offset(
                                                size.width - strokeWidth / 2,
                                                size.height
                                            ), // full height
                                            strokeWidth = strokeWidth
                                        )
                                    }
                                    .padding(end = 15.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(52.5.dp)
                                        .background(Color.Gray, shape = CircleShape)
                                        .border(2.dp, Color.White, CircleShape)
                                ) {
                                    Image(
                                        painter = painterResource(qrContentTypeIcon),
                                        contentDescription = null,
                                        Modifier
                                            .size(35.dp)
                                    )
                                }
                                Text(
                                    text = "(${qrContentTypes.firstOrNull() ?: "---"})",
                                    fontSize = 14.sp,
                                    color = Color.LightGray,
                                    fontWeight = FontWeight(600),
                                    modifier = Modifier.padding(top = 10.dp)
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxHeight()
                            ) {
                                Column(
                                    Modifier
                                        .fillMaxWidth(0.80f)
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = "Scanned Result",
                                        fontSize = 17.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight(700),
                                        modifier = Modifier.padding(top = 10.dp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.95f)
                                            .weight(1f) // take remaining vertical space
                                    ) {
                                        val scrollState = rememberScrollState()
                                        SelectionContainer() {
                                            Text(
                                                text = qrRawContent,
                                                fontSize = 14.sp,
                                                color = Color.White,
                                                fontWeight = FontWeight(400),
                                                modifier = Modifier
                                                    .verticalScroll(scrollState)
                                            )
                                        }
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceAround,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier
                                        ) {
                                            val scope = rememberCoroutineScope()
                                            val defaultBgColor = Color.Gray.copy(alpha = 0.5f)

                                            // --- Clipboard Icon ---
                                            val clipboardBgColor =
                                                remember { Animatable(defaultBgColor) }
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier
                                                    .padding(
                                                        horizontal = 5.dp,
                                                        vertical = 5.dp
                                                    )
                                                    .size(40.dp)
                                                    .clip(RoundedCornerShape(30.dp))
                                                    .background(clipboardBgColor.value)

                                                    .border(2.dp, Color.White, CircleShape)
                                                    .clickable(
                                                        interactionSource = remember { MutableInteractionSource() },
                                                        indication = null
                                                    ) {
                                                        scope.launch {
                                                            clipboardBgColor.snapTo(Color.White)
                                                            clipboardBgColor.animateTo(
                                                                targetValue = defaultBgColor,
                                                                animationSpec = tween(durationMillis = 500)
                                                            )
                                                        }
                                                        val clipboard =
                                                            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                                        val clip = ClipData.newPlainText(
                                                            "Scanned Text",
                                                            qrRawContent
                                                        )
                                                        clipboard.setPrimaryClip(clip)
                                                    }
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.clipboard),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(25.dp)
                                                )
                                            }

                                            // --- Share Icon ---
                                            val shareBgColor =
                                                remember { Animatable(defaultBgColor) }
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier


                                                    .padding(
                                                        horizontal = 5.dp,
                                                        vertical = 5.dp
                                                    )
                                                    .border(2.dp, Color.White, CircleShape)
                                                    .size(40.dp)
                                                    .clip(RoundedCornerShape(30.dp))
                                                    .background(shareBgColor.value)
                                                    .clickable(
                                                        interactionSource = remember { MutableInteractionSource() },
                                                        indication = null
                                                    ) {
                                                        scope.launch {
                                                            shareBgColor.snapTo(Color.White)
                                                            shareBgColor.animateTo(
                                                                targetValue = defaultBgColor,
                                                                animationSpec = tween(durationMillis = 500)
                                                            )
                                                        }
                                                        val formattedText =
                                                            "$parsedQrContent"
                                                        val sendIntent: Intent = Intent().apply {
                                                            action = Intent.ACTION_SEND
                                                            putExtra(
                                                                Intent.EXTRA_TEXT,
                                                                formattedText
                                                            )
                                                            type = "text/plain"
                                                        }
                                                        val shareIntent =
                                                            Intent.createChooser(sendIntent, null)
                                                        context.startActivity(shareIntent)
                                                    }
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.share),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(25.dp)
                                                )
                                            }

                                            // --- Visit/Main Function Icon ---
                                            val mainFunctionBgColor =
                                                remember { Animatable(defaultBgColor) }
                                            Box(

                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier

                                                    .padding(
                                                        horizontal = 5.dp,
                                                        vertical = 5.dp
                                                    )
                                                    .border(2.dp, Color.White, CircleShape)

                                                    .size(40.dp)
                                                    .clip(RoundedCornerShape(30.dp))
                                                    .background(mainFunctionBgColor.value)
                                                    .clickable(
                                                        interactionSource = remember { MutableInteractionSource() },
                                                        indication = null

                                                    ) {

                                                        scope.launch {
                                                            mainFunctionBgColor.snapTo(Color.White)
                                                            mainFunctionBgColor.animateTo(
                                                                targetValue = defaultBgColor,
                                                                animationSpec = tween(durationMillis = 500)
                                                            )
                                                            QrFunctionExecutor.execute(
                                                                context,
                                                                qrFunctions.first(),
                                                                qrRawContent
                                                            )
                                                        }


                                                    }
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.visit),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(25.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    BottomBar(navController, controller = controller, publicViewModel)
                }
            }
        }
    }
}

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun captureTimeAndDate(): Pair<String, String> {
    val now = LocalDateTime.now()
    val date = now.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    val time = now.format(DateTimeFormatter.ofPattern("hh:mm a"))
    return Pair(date, time)
}
//
//@Composable
//@Preview
//fun MainCameraScreenPreview() {
//    MainCameraScreen(null, {})
//}
