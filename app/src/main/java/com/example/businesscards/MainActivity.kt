package com.example.businesscards

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.businesscards.ui.theme.ProgressRed
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween



/**
 * Método de entrada que se ejecuta cuando se crea la actividad.
 *
 * Configura el contenido de la actividad usando `Compose`. Establece una variable de estado para
 * el tema oscuro (`isDarkTheme`) y define `BusinessCardsTheme` como el tema principal de la aplicación.
 * Permite alternar entre el tema claro y oscuro.
 *
 */
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


/**
 * Composable principal de la actividad que muestra el BusinessCardsCreator.
 *
 * Este componente permite al usuario personalizar una tarjeta de presentación con diferentes opciones,
 * incluyendo el icono de perfil, nombre, apellidos, profesión, teléfono, email, usuario de LinkedIn y GitHub,
 * borde de la tarjeta y fondo de la tarjeta. También proporciona opciones para alternar entre el modo claro y oscuro,
 * y para mostrar u ocultar ciertos elementos de la tarjeta mediante casillas de verificación.
 *
 * @param modifier Modificador opcional para personalizar el diseño.
 * @param isDarkTheme Indica si el tema oscuro está activado.
 * @param useDarkTheme Callback que se invoca para cambiar entre el modo claro y oscuro.
 *
 * @author Alberto Rodellar
 * @author Leandro Struni
 * @since 7/11/2024
 */
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
            modifier = Modifier.fillMaxWidth()
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

            //Lazy Column para mostrar las opciones de personalización
            LazyColumn(
                contentPadding = PaddingValues(top = 24.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .zIndex(0f),
            ) {

                //Icono de la tarjeta
                item {
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


                //TextField Email
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

                //Sección fondo de la tarjeta
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


/**
 * Composable que representa una tarjeta de presentación con información personal y un icono de perfil.
 *
 * Permite personalizar diferentes campos, mostrar u ocultar secciones de información, establecer un borde y
 * un fondo personalizado, y agregar un icono de perfil.
 *
 * @param name Nombre del usuario que se mostrará en la tarjeta.
 * @param surname Apellido del usuario, que se muestra si showSurname es verdadero.
 * @param showSurname Indica si el apellido debe mostrarse en la tarjeta.
 * @param profession Profesión del usuario, que se muestra si showProfession es verdadero.
 * @param showProfession Indica si la profesión debe mostrarse en la tarjeta.
 * @param phone Teléfono del usuario, que se muestra si showPhone es verdadero.
 * @param showPhone Indica si el teléfono debe mostrarse en la tarjeta.
 * @param email Correo electrónico del usuario.
 * @param web Perfil de LinkedIn del usuario, que se muestra si showWeb es verdadero.
 * @param showWeb Indica si el perfil de LinkedIn debe mostrarse en la tarjeta.
 * @param github Perfil de GitHub del usuario, que se muestra si showGitHub es verdadero.
 * @param showGitHub Indica si el perfil de GitHub debe mostrarse en la tarjeta.
 * @param icon Icono de perfil que se muestra en la tarjeta.
 * @param borderStroke Estilo del borde para la tarjeta.
 * @param backgroundImage Imagen de fondo de la tarjeta; si es null, se usará un fondo predeterminado.
 * @param modifier Modificador opcional para personalizar el diseño de la tarjeta.
 */
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

    //Componente Card
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
                        .padding(start = 10.dp, top = 16.dp, end = 16.dp, bottom = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
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
                        .padding(start = 5.dp, top = 16.dp, end = 10.dp, bottom = 16.dp),
                    horizontalAlignment = Alignment.Start,
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


/**
 * Composable para un campo de entrada de texto con validación y límite de caracteres.
 *
 * Este campo permite al usuario ingresar texto con restricciones personalizables, como longitud máxima y
 * validación de formato. Muestra un mensaje de error cuando el contenido ingresado no cumple con la validación
 * especificada. El estilo, el color y las opciones del teclado también se pueden personalizar.
 *
 * @param value Texto actual del campo de entrada.
 * @param onValueChange Función de callback que se invoca cuando el texto cambia, pasando el nuevo valor.
 * @param label Etiqueta que se muestra dentro del campo de entrada cuando está vacío.
 * @param maxLength Número máximo de caracteres permitidos en el campo.
 * @param errorMessage Mensaje de error que se muestra cuando el texto no cumple con la validación.
 * @param validate Función de validación que se utiliza para comprobar si el texto ingresado es válido.
 * @param keyboardOptions Configuración del teclado, como tipo y acciones de entrada.
 * @param keyboardActions Acciones del teclado que se activan en ciertos eventos, como "Done".
 */
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

/**
 * Composable que muestra una lista de opciones con checkbox para configurar la visibilidad de distintos campos en una tarjeta de presentación.
 *
 * Cada checkbox representa un campo de datos (como Apellidos, Profesión, Teléfono, LinkedIn y GitHub)
 * y permite alternar su visibilidad en la tarjeta de presentación. Al marcar o desmarcar un checkbox,
 * se llama a la función de callback correspondiente para actualizar el estado externo.
 *
 * @param showSurname Estado actual de visibilidad del campo "Apellidos".
 * @param onShowSurnameChange Función de callback que se invoca al cambiar el estado de visibilidad de "Apellidos".
 * @param showProfession Estado actual de visibilidad del campo "Profesión".
 * @param onShowProfessionChange Función de callback que se invoca al cambiar el estado de visibilidad de "Profesión".
 * @param showPhone Estado actual de visibilidad del campo "Teléfono".
 * @param onShowPhoneChange Función de callback que se invoca al cambiar el estado de visibilidad de "Teléfono".
 * @param showWeb Estado actual de visibilidad del campo "LinkedIn".
 * @param onShowWebChange Función de callback que se invoca al cambiar el estado de visibilidad de "LinkedIn".
 * @param showGitHub Estado actual de visibilidad del campo "GitHub".
 * @param onShowGitHub Función de callback que se invoca al cambiar el estado de visibilidad de "GitHub".
 */
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
        //TODO Añadir checkbox Marcar y Desmarcar Todos
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


/**
 * Composable para un switch de tema que permite cambiar entre modo claro y modo oscuro.
 *
 * Muestra un texto que indica el tema actual ("Tema Claro" o "Tema Oscuro") y un switch que permite al usuario
 * alternar entre ambos temas. Cambia automáticamente el tema visual de la aplicación al activarse.
 *
 * @param isDarkTheme Estado actual del tema. true indica que el tema oscuro está activado.
 * @param useDarkTheme Función de callback que se invoca cuando se cambia el estado del switch,
 * actualizando el tema entre claro y oscuro.
 */
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

/**
 * Composable para un TriStateCheckbox que controla el estilo del borde de un elemento.
 *
 * Este checkbox permite alternar entre tres estados (`Off`, `On`, `Indeterminate`), cada uno de los cuales
 * aplica un estilo de borde diferente. Los estados de borde incluyen sin borde (`Off`), un borde fino
 * (`On`) y un borde más grueso (`Indeterminate`), todos en color dorado.
 *
 * @param onBorderChange Función de callback que se invoca cada vez que el estado del checkbox cambia,
 * pasando el estilo de borde actual como `BorderStroke?`.
 */
@Composable
fun TriStateBorder(
    onBorderChange: (BorderStroke?) -> Unit
) {
    var checkboxState by rememberSaveable { mutableStateOf(ToggleableState.Off) }

    val borderStroke = when (checkboxState) {
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


/**
 * Composable que muestra una fila de opciones de iconos mediante RadioButton.
 *
 * Cada opción representa un icono diferente, que se selecciona al hacer clic en el botón de radio correspondiente. La opción seleccionada
 * llama a una función `onOptionSelected` y envía el icono correspondiente como Painter.
 *
 * @param onOptionSelected Función de callback que se invoca al seleccionar una opción, proporcionando
 * el Painter del icono correspondiente a la opción elegida.
 */
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


/**
 * Composable para un menú desplegable que permite seleccionar una imagen de fondo.
 *
 * Muestra un texto que describe la imagen de fondo seleccionada y abre un menú desplegable con
 * opciones al hacer clic en el área de selección. Cada opción incluye una imagen de fondo con su
 * descripción, y al seleccionar una opción se invoca una función de callback que actualiza la selección.
 *
 * @param selectedImage Imagen de fondo seleccionada actualmente, representada por su recurso ID.
 * @param onSelectedImage Función de callback que se invoca cuando el usuario selecciona una imagen,
 *                        pasando el recurso ID de la imagen seleccionada o `null` para la opción predeterminada.
 * @param backgroundImages Lista de opciones de fondo, cada una representada por un par (recurso de imagen, descripción).
 */
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

/**
 * Composable que muestra una ProgressBar que refleja el estado de personalización de la tarjeta.
 *
 * La barra de progreso se ajusta automáticamente en función de varios factores, incluyendo la lista de valores
 * ingresados por el usuario, el estado de borde de la tarjeta y la imagen de fondo seleccionada.
 * A medida que el usuario completa o vacía estos valores, el progreso aumenta o disminuye.
 *
 * @param values Lista de valores de texto ingresados en los campos, cuyo contenido afecta el progreso.
 * @param borderStroke Estado del borde de la tarjeta, que contribuye al progreso al ser modificado.
 * @param selectedBackgroundImage ID de recurso de la imagen de fondo seleccionada, afectando el progreso cuando se elige una imagen.
 * @param progressStatus Estado actual del progreso, representado como un valor `Float` entre 0 y 1.
 * @param onProgressChanged Función de callback que se invoca cuando el progreso cambia, recibiendo el nuevo valor de progreso.
 */
@Composable
fun ProgressBar(
    values: List<String>,
    borderStroke: BorderStroke?,
    selectedBackgroundImage: Int?,
    progressStatus: Float,
    onProgressChanged: (Float) -> Unit
) {
    val previousValues = remember { mutableStateOf(values) }
    val previousBorderStroke = remember { mutableStateOf(borderStroke) }
    val previousBackgroundImage = remember { mutableStateOf(selectedBackgroundImage) }

    // Animacion
    val animatedProgress = animateFloatAsState(
        targetValue = progressStatus,
        animationSpec = tween(durationMillis = 500),
        label = ""
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

        // Actualiza valores
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
            progress = {
                animatedProgress.value
            },
            modifier = Modifier
                .padding(24.dp)
                .height(24.dp)
                .width(500.dp),
            color = ProgressRed,
            trackColor = Color.LightGray,
            strokeCap = StrokeCap.Butt,
        )
    }
}

/**
 * Composable para representar el preview
 */
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

