package com.example.businesscards

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.businesscards.ui.theme.BusinessCardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCardsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BusinessCardsCreator(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun BusinessCardsCreator(modifier: Modifier = Modifier) {
    var name by rememberSaveable { mutableStateOf("") }
    var surname by rememberSaveable { mutableStateOf("") }
    var profession by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var web by rememberSaveable { mutableStateOf("") }
    var github by rememberSaveable { mutableStateOf("") }

    //Variable para ocultar el teclado cuando se hace click en el LazyColumn (fuera del teclado)
    val keyboardController = LocalSoftwareKeyboardController.current


    //Variables para checkBox
    var showSurname by rememberSaveable { mutableStateOf(true) }
    var showProfession by rememberSaveable { mutableStateOf(true) }
    var showPhone by rememberSaveable { mutableStateOf(true) }
    var showWeb by rememberSaveable { mutableStateOf(true) }
    var showGitHub by rememberSaveable { mutableStateOf(true) }


    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable { keyboardController?.hide() }
    ) {

        //Progress bar implemetar

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
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
                .zIndex(1f)
        )


        LazyColumn(
            contentPadding = PaddingValues(top = 280.dp),
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
                    validate = { it.matches(Regex("^(https?://)?(www\\\\.)?[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}(/.*)?\$\n")) },
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
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .height(250.dp)
            .fillMaxWidth()
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.Gray, shape = RoundedCornerShape(8.dp))
    )
    {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()

            ) {
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        textAlign = TextAlign.Center
                    )
                    if (showSurname) {
                        Text(
                            text = surname.ifEmpty { "Apellidos" },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                    if (showProfession) {
                        Text(
                            text = profession.ifEmpty { "Profesión" },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

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
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(top = 20.dp)
                        )
                    }

                    Text(
                        text = email.ifEmpty { "Email" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 20.dp)
                    )

                    if (showWeb) {
                        Text(
                            text = web.ifEmpty { "Web" },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(top = 20.dp)
                        )
                    }

                    if (showGitHub) {
                        Text(
                            text = if (github.isNotEmpty()) "github.com/$github" else "GitHub",
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BusinessCardsCreatorPreview() {
    BusinessCardsTheme {
        BusinessCardsCreator()
    }

}


//TODO
/*
Cambiar web por Linkedin
Creador de business cards
Crea una app que permeti confeccionar business cards (targetes de visita).
Aquesta app ha de disposar dels següents components dins d’una mateixa activitat:
• Text (Per mostrar missatges a l’usuari)
   -Mostrar texto a usuario

• TextField (Per tal de que l’usuari introdueixi les seves dades)
    -Ingresar datos del usuario.

• CheckBox (Per sel·leccionar incloure o no alguna informació; per ex. cognoms,
càrrec, etc.)
    -Implementar checkbox para mostrar o ocultar campos de la Card ()

• Switch (Per escollir colors i aspectes gràfics de la targeta)
    -Probar tema claro y tema oscuro o cambiar entre dos temas predefinidos

• TriState (Per escollir entre tres opcions que decidiu vosaltres)
    Probar cambiar marco de la Card, por ejemplo: sin borde, borde fino, borde grueso.

• RadioButton (Per escollir aspectes gràfics de la targeta).
    -Radio Button para seleccionar tamaño fuente, tipo, color??

• Icon (Per a què l’usuari pugui afegir icones a la seva targeta: estrelles, casetes, etc.)
    -Permitir añadir iconos, usar LAzyRow?

• Image (Per tal de que l’usuari pugui afegir una imatge de fons a la targeta. Podem
tenir 4 imatges de fons predefinides que no interfereixin amb la lectura del contingut i
que l’usuari les esculli amb algun dels components anteriors)
    -Imagenes predefinidas como opciones de fondo o de logo, puede estar en un Row o LazyRow con
     RadioButton o Icon para selecionar las imagenes.

• Card (Per confeccionar la targeta a sota de les opcions anteriors)
    -Implemetar vista zoom?
• Progress Indicator (per mostrar l’avenç en la creació de la targeta fins a acabar-la)
    -Implementar Progress Indicator en la parte superior de la BussinessCard.
 */