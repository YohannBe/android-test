package com.evaneos.evaneostest.dummy

import com.evaneos.evaneostest.model.DidYouKnow

internal object  DidYouKnowFactory {

    fun createDidYouKnowList(): List<DidYouKnow> {
        return listOf(
            DidYouKnow(
                description = "Entre nature et tradition : 7 villes et villages à découvrir en Islande",
                imageUrl = "https://assets.evcdn.net/dam-images/83222/21-9/1440x617.jpeg",
                link = "https://www.evaneos.fr/magazine/inspirations-voyage/meilleurs-villes-et-villages-islande/",
            ),
            DidYouKnow(
                description = "10 expériences extraordinaires à vivre aux quatre coins du monde",
                imageUrl = "https://assets.evcdn.net/dam-images/84309/21-9/1440x617.jpeg",
                link = "https://www.evaneos.fr/magazine/inspirations-voyage/10-experiences-extraordinaires-autour-du-monde/",
            ),
            DidYouKnow(
                description = "S’évader sur une île en Europe : où prendre un bain de mer et de soleil ?",
                imageUrl = "https://assets.evcdn.net/dam-images/91861/21-9/1440x617.jpeg",
                link = "https://www.evaneos.fr/magazine/inspirations-voyage/sevader-ile-europe-bain-mer-et-soleil/",
            ),
            DidYouKnow(
                description = "10 circuits en France pour déconnecter et s’évader",
                imageUrl = "https://assets.evcdn.net/dam-images/92121/21-9/1440x617.jpeg",
                link = "https://www.evaneos.fr/magazine/inspirations-voyage/10-circuits-france-pour-deconnecter-au-printemps/",
            ),
            DidYouKnow(
                description = "Au fil des saisons : les plus belles randonnées en Europe",
                imageUrl = "https://assets.evcdn.net/dam-images/92063/21-9/1440x617.jpeg",
                link = "https://www.evaneos.fr/magazine/inspirations-voyage/les-plus-belles-randonnees-en-europe-au-fil-des-saisons/",
            ),
            DidYouKnow(
                description = "48h à Venise hors des sentiers battus",
                imageUrl = "https://assets.evcdn.net/dam-images/92410/21-9/1440x617.jpeg",
                link = "https://www.evaneos.fr/magazine/conseils-pratiques/48h-venise-hors-des-sentiers-battus/",
            ),
        )
    }
}