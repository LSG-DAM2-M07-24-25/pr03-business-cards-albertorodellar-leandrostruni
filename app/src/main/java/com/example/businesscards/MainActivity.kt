package com.example.businesscards

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.windowInsetsStartWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.businesscards.ui.theme.BusinessCardsTheme
import java.lang.Error
import java.util.regex.Pattern
import javax.xml.validation.Validator

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
    var telephone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var web by rememberSaveable { mutableStateOf("") }
    var github by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        //Progress bar implemetar

        //BussinesCard
        BussinesCard(
            imageLogo = R.drawable.test_image,
            name = name,
            surname = surname,
            profession = profession,
            telephone = telephone,
            email = email,
            web = web,
            github = github,
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
                .zIndex(0f)
        ) {

            //TextFielD Nombre
            item {
                InputField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Ingrese su Nombre: ",
                    errorMessage = "El nombre solo debe contener letras.",
                    validate = {it.matches(Regex("^[a-zA-Z\\s]+$"))}
                )
            }
            //TextField Apellido
            item {
                InputField(
                    value = surname,
                    onValueChange = { surname = it },
                    label = "Ingrese su Apellido: ",
                    errorMessage = "El Apellido solo puede contener letras.",
                    validate = {it.matches(Regex("^[a-zA-Z\\s]+$"))}
                )
            }


            //TextField Profesión
            item {
                InputField(
                    value = profession,
                    onValueChange = { profession = it },
                    label = "Ingrese su Profesión: ",
                    errorMessage = "La profesión solo puede contener letras.",
                    validate = {it.matches(Regex("^[a-zA-Z\\s]+$"))}
                )
            }


            //TextField Teléfono
            item {
                InputField(
                    value = telephone,
                    onValueChange = { telephone = it },
                    label = "Ingrese su Teléfono: ",
                    errorMessage = "El teléfono/móvil tiene que tener el formato +34 + 9 dígitos.",
                    validate = {it.matches(Regex("^\\+34\\d{9}\$"))}
                )
            }


            //TextField email
            item {
                InputField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Ingrese su Email: ",
                    errorMessage = "El Email no tiene el fórmato válido.",
                    validate = { Patterns.EMAIL_ADDRESS.matcher(it).matches()}
                )
            }


            //TextField web
            item {
                InputField(
                    value = web,
                    onValueChange = { web = it },
                    label = "Ingrese su Página Web: ",
                    errorMessage = "La dirección Web no tiene el formato válido",
                    validate = {it.matches(Regex("^(https?://)?(www\\\\.)?[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}(/.*)?\$\n"))}
                )
            }

            //TextField github
            item {
                InputField(
                    value = github,
                    onValueChange = { github = it },
                    label = "Ingrese su GitHub: ",
                    errorMessage = "Ingrese un perfil de usuairo de GitHub válido.",
                    validate = {it.matches(Regex("^github\\.com/[a-zA-Z0-9_-]{1,39}$"))}
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
    profession: String,
    telephone: String,
    email: String,
    web: String,
    github: String,
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
                    Text(
                        text = surname.ifEmpty { "Apellidos" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        textAlign = TextAlign.Center

                    )
                    Text(
                        text = profession.ifEmpty { "Profesión" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        textAlign = TextAlign.Center
                    )
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
                    Text(
                        text = telephone.ifEmpty { "Telefono" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = email.ifEmpty { "Email" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = web.ifEmpty { "Web" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = github.ifEmpty { "GitHub" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

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
    validate: (String) -> Boolean
) {
    var isError by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = value,
            onValueChange = { input ->
                if (validate(input)) {
                    onValueChange(input)
                    isError = false
                } else {
                    isError = true
                }

            },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            isError = isError
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BusinessCardsCreatorPreview() {
    BusinessCardsTheme {
        BusinessCardsCreator()
    }

}


//TODO
/*
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