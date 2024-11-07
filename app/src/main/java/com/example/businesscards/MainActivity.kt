package com.example.businesscards

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
        R.drawable.background_1 to "Opción 1",
        R.drawable.background_2 to "Opción 2",
        R.drawable.background_3 to "Opción 3"
    )

    //Estado para la imagen de fondo
    var selectedBackgroundImage by rememberSaveable { mutableStateOf<Int?>(null) }


    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable { keyboardController?.hide() }
    ) {

        //Progress bar implemetar
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            //BussinesCard que se actualiza en tiempo real
            BussinesCard(
                imageLogo = R.drawable.test_image,
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

            Spacer(modifier = Modifier.height(6.dp))

            LazyColumn(
                contentPadding = PaddingValues(top = 24.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .zIndex(0f),
            ) {

                //TextFielD Nombre
                item {
                    InputField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Ingrese su Nombre: ",
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
                        label = "Ingrese su Apellido: ",
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
                        label = "Ingrese su Profesión: ",
                        errorMessage = "La profesión solo puede contener letras.",
                        validate = { it.matches(Regex("^[a-zA-Z\\s]+$")) },
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
                        label = "Ingrese su Teléfono: ",
                        errorMessage = "El teléfono/móvil tiene que tener el formato +XX seguido de 9 dígitos.",
                        validate = { it.matches(Regex("^\\+\\d{1,3}\\d{9}\$")) },
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
                        label = "Ingrese su Email: ",
                        errorMessage = "El Email no tiene el fórmato válido.",
                        validate = { Patterns.EMAIL_ADDRESS.matcher(it).matches() },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        )
                    )
                }


                //TextField web
                item {
                    InputField(
                        value = web,
                        onValueChange = { web = it },
                        label = "Ingrese su Página Web: ",
                        errorMessage = "La dirección Web no tiene el formato válido",
                        validate = { it.matches(Regex("^(https?://)?(www\\.)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(/.*)?\$\n"))},
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
                        label = "Ingrese su perfil de GitHub: ",
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
                        text = "Cambiar entre Modo Claro o Modo Oscuro"
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
                        text = "Cambiar el borde de la tarjeta"
                    )
                    TriStateBorder(onBorderChange = { newBorder ->
                        borderStroke = newBorder
                    }
                    )
                }

                item {
                    Text(
                        text = "Seleccionar el fondo de la tarjeta"
                    )
                    DropDownMenuBackground(
                        selectedImage = selectedBackgroundImage,
                        onSelectedImage = { selectedBackgroundImage = it },
                        backgroundImages = backgroundImages
                    )
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
    imageLogo: Int,
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
                //Culumna seccion izquierda
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f)
                    //.padding(end = 8.dp)
                ) {

                    Image(
                        painter = painterResource(id = imageLogo),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
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
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start,

                    //.padding(start = 8.dp)
                ) {
                    if (showPhone) {
                        Text(
                            text = phone.ifEmpty { "Teléfono" },
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(top = 20.dp)
                        )
                    }

                    Text(
                        text = email.ifEmpty { "Email" },
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 20.dp)
                    )

                    if (showWeb) {
                        Text(
                            text = web.ifEmpty { "Web" },
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(top = 20.dp)
                        )
                    }

                    if (showGitHub) {
                        Text(
                            text = if (github.isNotEmpty()) "github.com/$github" else "GitHub",
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(top = 20.dp)
                        )
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
    errorMessage: String? = null,
    validate: (String) -> Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var tempValue by rememberSaveable { mutableStateOf(value) }
    var isError by rememberSaveable { mutableStateOf(false) }


    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = tempValue,
            onValueChange = { input ->
                tempValue = input
                if (validate(input) || input.isEmpty()) {
                    onValueChange(input)
                    isError = false
                } else {
                    isError = true
                }
            },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions

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
        Text("Mostrar Datos")

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
            Text(text = "Mostrar Web")
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
        ToggleableState.On -> BorderStroke(2.dp, Color.Black)
        ToggleableState.Indeterminate -> BorderStroke(4.dp, Color.Black)
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

//Composable desl depleglabe para seleccionas imagen de fonde de la CardBussines
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
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp)
                .clickable { expanded = !expanded }
        )

        //Desplegable
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
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