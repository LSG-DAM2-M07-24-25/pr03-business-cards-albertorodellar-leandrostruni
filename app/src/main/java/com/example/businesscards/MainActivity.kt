package com.example.businesscards

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.businesscards.ui.theme.BusinessCardsTheme
import com.example.businesscards.ui.theme.LightBackgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlin.math.max
import com.example.businesscards.ui.theme.ProgressRed
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(false) }
            BusinessCardsTheme(darkTheme = isDarkTheme) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BusinessCardsCreator(
                        isDarkTheme = isDarkTheme,
                        useDarkTheme = { isDarkTheme = it },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

//Composable de la Activity Principal
@Composable
fun BusinessCardsCreator(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    useDarkTheme: (Boolean) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var surname by rememberSaveable { mutableStateOf("") }
    var profession by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var web by rememberSaveable { mutableStateOf("") }
    var github by rememberSaveable { mutableStateOf("") }

    //Variable para ocultar el teclado cuando se hace click en el LazyColumn (fuera del teclado)
    val keyboardController = LocalSoftwareKeyboardController.current

    //Estados para checkBox
    var showSurname by rememberSaveable { mutableStateOf(true) }
    var showProfession by rememberSaveable { mutableStateOf(true) }
    var showPhone by rememberSaveable { mutableStateOf(true) }
    var showWeb by rememberSaveable { mutableStateOf(true) }
    var showGitHub by rememberSaveable { mutableStateOf(true) }

    //Estado para el borde de la tarjeta
    var borderStroke by rememberSaveable { mutableStateOf<BorderStroke?>(null) }

    //Lista para las imagenes de backgroud
    val backgroundImages = listOf(
        null to "Fondo predeterminado",
        R.drawable.background_1 to "Turquesa",
        R.drawable.background_2 to "Verde Oliva",
        R.drawable.background_3 to "Azul Claro"
    )

    //Estado para la imagen de fondo
    var selectedBackgroundImage by rememberSaveable { mutableStateOf<Int?>(null) }

    //Estado para el icono de perfil
    var selectedIcon by remember { mutableStateOf<Painter?>(null) }
    if (selectedIcon == null) {
        selectedIcon = painterResource(id = R.drawable.user)
    }

    //Estado para la progress bar
    var progressStatus by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable { keyboardController?.hide() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {


            Text(
                text = "Bussines Card Creator",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 30.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            )

            //BussinesCard que se actualiza en tiempo real
            BussinesCard(
                icon = selectedIcon!!,
                name = name,
                surname = surname,
                showSurname = showSurname,
                profession = profession,
                showProfession = showProfession,
                phone = phone,
                showPhone = showPhone,
                email = email,
                web = web,
                showWeb = showWeb,
                github = github,
                showGitHub = showGitHub,
                borderStroke = borderStroke ?: BorderStroke(0.dp, Color.Transparent),
                backgroundImage = selectedBackgroundImage,
                modifier = Modifier
                    .padding(16.dp)
                    .zIndex(1f)

            )
            ProgressBar(
                values = listOf(name, email),
                borderStroke = borderStroke,
                selectedBackgroundImage = selectedBackgroundImage,
                progressStatus = progressStatus,
                onProgressChanged = { newProgress ->
                    progressStatus = newProgress
                }
            )

            Spacer(modifier = Modifier.height(6.dp))

            LazyColumn(
                contentPadding = PaddingValues(top = 24.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .zIndex(0f),
            ) {

                item{
                    Text(
                        text = "Cambiar Icono de la tarjeta",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.15.sp
                        )
                    )
                    RadioButtonRow { icon ->
                        selectedIcon = icon
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                //TextField Nombre
                item {
                    InputField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Nombre: ",
                        maxLength = 25,
                        errorMessage = "El nombre solo debe contener letras.",
                        validate = { it.matches(Regex("^[a-zA-Z\\s]+$")) },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        )
                    )
                }

                //TextField Apellido
                item {
                    InputField(
                        value = surname,
                        onValueChange = { surname = it },
                        label = "Apellidos: ",
                        maxLength = 25,
                        errorMessage = "El Apellido solo puede contener letras.",
                        validate = { it.matches(Regex("^[a-zA-Z\\s]+$")) },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        )
                    )
                }


                //TextField Profesión
                item {
                    InputField(
                        value = profession,
                        onValueChange = { profession = it },
                        label = "Profesión: ",
                        maxLength = 25,
                        errorMessage = "La profesión solo puede contener letras.",
                        validate = { it.trimEnd().matches(Regex("^[a-zA-Z\\s]+\$")) },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        )
                    )
                }


                //TextField Teléfono
                item {
                    InputField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = "Teléfono: ",
                        maxLength = 20,
                        errorMessage = "El teléfono tiene que empezar con el formato +34.",
                        validate = { it.matches(Regex("^\\+\\d{1,3} ?\\d{6,14}\$")) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        )
                    )
                }


                //TextField email
                item {
                    InputField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email: ",
                        maxLength = 50,
                        errorMessage = "El Email no tiene el fórmato válido.",
                        validate = { Patterns.EMAIL_ADDRESS.matcher(it).matches() },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        )
                    )
                }


                //TextField LinkdIn
                item {
                    InputField(
                        value = web,
                        onValueChange = { web = it },
                        label = "Usuario LinkedIn: ",
                        maxLength = 50,
                        errorMessage = "El nombre de usuario solo puede contener letras, números o guiones.",
                        validate = { it.matches(Regex("^[a-zA-Z0-9-]{3,47}\$")) },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        )
                    )
                }

                //TextField github
                item {
                    InputField(
                        value = github,
                        onValueChange = { github = it },
                        label = "Usuario GitHub: ",
                        maxLength = 39,
                        errorMessage = "Ingrese un perfil de usuario de GitHub válido.",
                        validate = { it.matches(Regex("^[a-zA-Z0-9_-]{1,39}\$")) },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        )
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                //Seccion checkbox
                item {
                    CheckBoxOption(
                        showSurname = showSurname,
                        onShowSurnameChange = { showSurname = it },
                        showProfession = showProfession,
                        onShowProfessionChange = { showProfession = it },
                        showPhone = showPhone,
                        onShowPhoneChange = { showPhone = it },
                        showWeb = showWeb,
                        onShowWebChange = { showWeb = it },
                        showGitHub = showGitHub,
                        onShowGitHub = { showGitHub = it }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                //Sección Switch
                item {
                    Text(
                        text = "Cambiar entre Modo Claro o Modo Oscuro",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.15.sp
                        )
                    )
                    SwitchTheme(
                        isDarkTheme = isDarkTheme,
                        useDarkTheme = useDarkTheme
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                //Sección del Tristate
                item {
                    Text(
                        text = "Cambiar el borde de la tarjeta",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.15.sp
                        )
                    )
                    TriStateBorder(onBorderChange = { newBorder ->
                        borderStroke = newBorder
                    }
                    )
                }

                item {
                    Text(
                        text = "Seleccionar el fondo de la tarjeta",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.15.sp
                        )
                    )
                    DropDownMenuBackground(
                        selectedImage = selectedBackgroundImage,
                        onSelectedImage = { selectedBackgroundImage = it },
                        backgroundImages = backgroundImages
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}


//Composable de la Bussiness Card
@Composable
fun BussinesCard(
    name: String,
    surname: String,
    showSurname: Boolean,
    profession: String,
    showProfession: Boolean,
    phone: String,
    showPhone: Boolean,
    email: String,
    web: String,
    showWeb: Boolean,
    github: String,
    showGitHub: Boolean,
    icon: Painter,
    borderStroke: BorderStroke,
    backgroundImage: Int?,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .height(250.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        border = borderStroke,
        shape = RoundedCornerShape(16.dp)
    )
    {
        Box(modifier = Modifier.fillMaxSize()) {
            //Fondo de imagen en el Box detrás del contenido
            if (backgroundImage != null) {
                Image(
                    painter = painterResource(id = backgroundImage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()

            ) {
                //Columna seccion izquierda
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f)
                    //.padding(end = 8.dp)
                    , horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        painter = icon,
                        contentDescription = "Business Card Icon",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(80.dp),
                        tint = Color(0xFF333333)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = name.ifEmpty { "Nombre" },
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        textAlign = TextAlign.Center
                    )
                    if (showSurname) {
                        Text(
                            text = surname.ifEmpty { "Apellidos" },
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                    if (showProfession) {
                        Text(
                            text = profession.ifEmpty { "Profesión" },
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                //Columna seccion derecha
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(start = 5.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
                    horizontalAlignment = Alignment.Start,

                    //.padding(start = 8.dp)
                ) {
                    if (showPhone) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Phone Icon",
                                modifier = Modifier.size(24.dp),
                                tint = Color(0xFF333333)
                            )
                            Text(
                                text = phone.ifEmpty { "Teléfono" },
                                color = Color.Black,
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email Icon",
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFF333333)
                        )
                        Text(
                            text = email.ifEmpty { "Email" },
                            color = Color.Black,
                        )
                    }

                    if (showWeb) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.linkedin_icon),
                                contentDescription = "Linkedin Icon",
                                modifier = Modifier.size(24.dp),
                                tint = Color(0xFF333333)
                            )
                            Text(
                                text = web.ifEmpty { "LinkedIn" },
                                color = Color.Black,
                            )
                        }
                    }

                    if (showGitHub) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.github_icon),
                                contentDescription = "Github Icon",
                                modifier = Modifier.size(24.dp),
                                tint = Color(0xFF333333)
                            )
                            Text(
                                text = github.ifEmpty { "GitHub" },
                                color = Color.Black,
                            )
                        }
                    }
                }
            }
        }
    }
}

//Composable de lo campos input
@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    maxLength: Int,
    errorMessage: String? = null,
    validate: (String) -> Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var tempValue by rememberSaveable { mutableStateOf(value) }
    var isError by rememberSaveable { mutableStateOf(false) }


    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = tempValue,
            onValueChange = { input ->
                if (input.length <= maxLength) {
                    tempValue = input
                    if (validate(input) || input.isEmpty()) {
                        onValueChange(input)
                        isError = false
                    } else {
                        isError = true
                    }
                }
            },
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            isError = isError,
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                cursorColor = if (isError) Color.Red else MaterialTheme.colorScheme.primary,
                errorCursorColor = Color.Red,
                errorIndicatorColor = Color.Red
            ),

            )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

    }
}

//Composable del CheckBoxOption
@Composable
fun CheckBoxOption(
    showSurname: Boolean,
    onShowSurnameChange: (Boolean) -> Unit,
    showProfession: Boolean,
    onShowProfessionChange: (Boolean) -> Unit,
    showPhone: Boolean,
    onShowPhoneChange: (Boolean) -> Unit,
    showWeb: Boolean,
    onShowWebChange: (Boolean) -> Unit,
    showGitHub: Boolean,
    onShowGitHub: (Boolean) -> Unit

) {
    Column {
        Text(
            text = "Mostrar Datos",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.15.sp
            )
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = showSurname,
                onCheckedChange = onShowSurnameChange
            )
            Text(text = "Mostrar Apellidos")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = showProfession,
                onCheckedChange = onShowProfessionChange
            )
            Text(text = "Mostrar Profesión")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = showPhone,
                onCheckedChange = onShowPhoneChange
            )
            Text(text = "Mostrar Teléfono")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = showWeb,
                onCheckedChange = onShowWebChange
            )
            Text(text = "Mostrar LinkedIn")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = showGitHub,
                onCheckedChange = onShowGitHub
            )
            Text(text = "Mostrar GitHub")
        }
    }
}

//Switch para cambiar entre tema claro y oscuro
@Composable
fun SwitchTheme(
    isDarkTheme: Boolean,
    useDarkTheme: (Boolean) -> Unit
) {

    BusinessCardsTheme(
        darkTheme = isDarkTheme,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = if (isDarkTheme) "Tema Oscuro" else "Tema Claro")
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { useDarkTheme(it) }
            )
        }
    }
}

//Componente triStateCheckbox para cambiar el borde de la tarjeta
@Composable
fun TriStateBorder(
    onBorderChange: (BorderStroke?) -> Unit
) {
    var checkboxState by rememberSaveable { mutableStateOf(ToggleableState.Off) }

    var borderStroke = when (checkboxState) {
        ToggleableState.Off -> null
        ToggleableState.On -> BorderStroke(2.dp, Color(0xFFB8860B))
        ToggleableState.Indeterminate -> BorderStroke(4.dp, Color(0xFFB8860B))
    }

    onBorderChange(borderStroke)

    TriStateCheckbox(
        state = checkboxState,
        onClick = {
            checkboxState = when (checkboxState) {
                ToggleableState.Off -> ToggleableState.On
                ToggleableState.On -> ToggleableState.Indeterminate
                ToggleableState.Indeterminate -> ToggleableState.Off
            }
        },
        colors = CheckboxDefaults.colors()
    )
}

//Composable radio button para cambiar icono de la tarjeta
@Composable
fun RadioButtonRow(onOptionSelected: (Painter) -> Unit) {
    val options = mapOf(
        "Default" to R.drawable.user,
        "Tie" to R.drawable.user_tie,
        "Doctor" to R.drawable.user_doctor,
        "Astronaut" to R.drawable.user_astronaut,
    )
    var selectedOption by remember { mutableStateOf("Default") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        options.forEach { (option, iconRes) ->

            //Carga aqui el painter porque sino peta
            val iconPainter = painterResource(id = iconRes)

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = {
                        selectedOption = option
                        // Llamamos a onOptionSelected pasándole el Painter cargado
                        onOptionSelected(iconPainter)
                    }
                )
                Icon(
                    painter = iconPainter,
                    contentDescription = option,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    }
}


//Composable del deplegable para seleccionar imagen de fondo de la BusinessCard
@Composable
fun DropDownMenuBackground(
    selectedImage: Int?,
    onSelectedImage: (Int?) -> Unit,
    backgroundImages: List<Pair<Int?, String>>
) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    val selectedImageDescription = remember(selectedImage) {
        backgroundImages.firstOrNull { it.first == selectedImage }?.second
            ?: "Selecciona una imagen de fondo"
    }


    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = selectedImageDescription,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF1F1F1),
                    shape = RoundedCornerShape(12.dp)

                )
                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .clickable { expanded = !expanded }
        )

        //Desplegable
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .padding(4.dp)
        ) {
            backgroundImages.forEach { (imageRes, description) ->
                DropdownMenuItem(
                    text = { Text(text = description) },
                    onClick = {
                        println(imageRes)
                        onSelectedImage(imageRes)
                        expanded = false
                    })
            }
        }

    }
}

@Composable
fun ProgressBar(
    values: List<String>,
    borderStroke: BorderStroke?,
    selectedBackgroundImage: Int?,
    progressStatus: Float,
    onProgressChanged: (Float) -> Unit
) {
    // Recordamos el estado anterior de cada valor
    val previousValues = remember { mutableStateOf(values) }
    val previousBorderStroke = remember { mutableStateOf(borderStroke) }
    val previousBackgroundImage = remember { mutableStateOf(selectedBackgroundImage) }

    // Usamos animateFloatAsState para animar el progreso
    val animatedProgress = animateFloatAsState(
        targetValue = progressStatus,
        animationSpec = tween(durationMillis = 500) // Ajusta la duración de la animación
    )

    LaunchedEffect(values, borderStroke, selectedBackgroundImage) {
        var newProgress = progressStatus

        // Revisa los valores de la lista 'values'
        values.forEachIndexed { index, value ->
            val previousValue = previousValues.value.getOrElse(index) { "" }
            if (value.isNotEmpty() && previousValue.isEmpty()) {
                newProgress = (newProgress + 0.25f).coerceAtMost(1f)
            } else if (value.isEmpty() && previousValue.isNotEmpty()) {
                newProgress = (newProgress - 0.25f).coerceAtLeast(0f)
            }
        }

        // Revisa el 'borderStroke'
        if (borderStroke != null && previousBorderStroke.value == null) {
            newProgress = (newProgress + 0.25f).coerceAtMost(1f)
        } else if (borderStroke == null && previousBorderStroke.value != null) {
            newProgress = (newProgress - 0.25f).coerceAtLeast(0f)
        }

        // Revisa el 'selectedBackgroundImage'
        if (selectedBackgroundImage != null && previousBackgroundImage.value == null) {
            newProgress = (newProgress + 0.25f).coerceAtMost(1f)
        } else if (selectedBackgroundImage == null && previousBackgroundImage.value != null) {
            newProgress = (newProgress - 0.25f).coerceAtLeast(0f)
        }

        // Actualizamos el progreso y los valores previos
        onProgressChanged(newProgress)
        previousValues.value = values
        previousBorderStroke.value = borderStroke
        previousBackgroundImage.value = selectedBackgroundImage
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Progreso: ${(animatedProgress.value * 100).toInt()}%",
            modifier = Modifier.padding(start = 16.dp)
        )
        LinearProgressIndicator(
            progress = animatedProgress.value, // Usa el valor animado
            modifier = Modifier
                .padding(24.dp)
                .height(24.dp)
                .width(500.dp),
            color = ProgressRed,
            trackColor = Color.LightGray,
            strokeCap = StrokeCap.Butt
        )
    }
}






@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BusinessCardsCreatorPreview() {
    BusinessCardsTheme(darkTheme = false) {
        BusinessCardsCreator(
            isDarkTheme = false,
            useDarkTheme = {}
        )
    }
}


//TODO
/*
Cambiar web por Linkedin
Creador de business cards
Crea una app que permeti confeccionar business cards (targetes de visita).
Aquesta app ha de disposar dels següents components dins d’una mateixa activitat:
Implementado
• Text (Per mostrar missatges a l’usuari)
   -Mostrar texto a usuario

Implementado
• TextField (Per tal de que l’usuari introdueixi les seves dades)
    -Ingresar datos del usuario.

Implementado
• CheckBox (Per sel·leccionar incloure o no alguna informació; per ex. cognoms,
càrrec, etc.)
    -Implementar checkbox para mostrar o ocultar campos de la Card ()

Implementado
• Switch (Per escollir colors i aspectes gràfics de la targeta)
    -Probar tema claro y tema oscuro o cambiar entre dos temas predefinidos

Implementado
• TriState (Per escollir entre tres opcions que decidiu vosaltres)
    Probar cambiar marco de la Card, por ejemplo: sin borde, borde fino, borde grueso.


Falta implementar
• RadioButton (Per escollir aspectes gràfics de la targeta).
    -Radio Button para seleccionar tamaño fuente, tipo, color??

Falta Implementar
• Icon (Per a què l’usuari pugui afegir icones a la seva targeta: estrelles, casetes, etc.)
    -Permitir añadir iconos, usar LAzyRow?

Implementado
• Image (Per tal de que l’usuari pugui afegir una imatge de fons a la targeta. Podem
tenir 4 imatges de fons predefinides que no interfereixin amb la lectura del contingut i
que l’usuari les esculli amb algun dels components anteriors)
    -Imagenes predefinidas como opciones de fondo o de logo, puede estar en un Row o LazyRow con
     RadioButton o Icon para selecionar las imagenes.

• Card (Per confeccionar la targeta a sota de les opcions anteriors)
    -Implemetar vista zoom?

 Falta Implementar
• Progress Indicator (per mostrar l’avenç en la creació de la targeta fins a acabar-la)
    -Implementar Progress Indicator en la parte superior de la BussinessCard.
 */