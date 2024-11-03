package com.example.businesscards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Bussiness Cards Creator",
                modifier = modifier
            )
            //BussinesCard
            BussinesCard(
                imageLogo = R.drawable.test_image,
                name = name,
                surname = surname,
                profession = profession,
                modifier = Modifier
                    .fillMaxSize()

            )

            //TextFielD Nombre
            TextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text("Ingrese su Nombre: ")
                },
                modifier = Modifier.fillMaxWidth()
            )

            //TextField Apellido
            TextField(
                value = surname,
                onValueChange = { surname = it },
                label = {
                    Text("Ingrese su Apellido: ")
                },
                modifier = Modifier.fillMaxWidth()
            )

            //TextField Profesión
            TextField(
                value = profession,
                onValueChange = { profession = it },
                label = {
                    Text("Ingrese su Profesión: ")
                },
                modifier = Modifier.fillMaxWidth()
            )

        }

    }
}

//Composable de la Bussiness Card
@Composable
fun BussinesCard(
    name: String,
    surname: String,
    profession: String,
    imageLogo: Int,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
            .padding(16.dp)
    )
    {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {

                    Image(
                        painter = painterResource(id = imageLogo),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )


                    Text(
                        text = name.ifEmpty { "Nombre" },
                    )
                    Text(
                        text = surname.ifEmpty { "Apellidos" }
                    )
                    Text(
                        text = profession.ifEmpty { "Profesión" }
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = "Telefono"
                    )
                    Text(
                        text = "Email"
                    )
                    Text(
                        text = "Web"
                    )
                    Text(
                        text = "GitHub"
                    )

                }

            }


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