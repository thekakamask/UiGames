package com.dcac.uiGames.model

import com.dcac.uiGames.R


data class Paint(
    val image: Int,
    val title: String,
    val artist: String,
    val year: String,
    val description: String) {
    companion object {
        fun getPaintings(): List<Paint> {
            return listOf(
                Paint(
                    R.drawable.pont_du_diable,
                    "Le pont du diable",
                    "Joseph Mallord William Turner",
                    "1804",
                    "Turner capture ce pont médiéval dramatique avec son style caractéristique de lumière et de nature, " +
                            "symbolisant à la fois la beauté et le danger des paysages naturels."),
                Paint(R.drawable.guernica,
                    "Guernica",
                    "Pablo Picasso",
                    "1937",
                    "Cette peinture monumentale dénonce les horreurs de la guerre, en particulier le bombardement de la ville espagnole " +
                            "de Guernica durant la guerre civile. Un chef-d'œuvre du cubisme, rempli de symbolisme."),
                Paint(R.drawable.la_jeune_fille_la_perle,
                    "La jeune fille à la perle",
                    "Johannes Vermeer",
                    "1665",
                    "Connue comme la \"Mona Lisa du Nord\", cette œuvre capture l'élégance et le mystère d'une jeune femme avec une perle, " +
                            "utilisant un éclairage délicat et des tons doux."),
                Paint(R.drawable.la_naissance_de_venus,
                    "la naissance de venus",
                    "Sandro Botticelli",
                    "1485",
                    "Cette peinture représente la déesse Vénus émergeant de la mer sur une coquille, symbolisant la beauté et l'amour " +
                            "dans la mythologie gréco-romaine."),
                Paint(R.drawable.la_nuit_etoile,
                    "La Nuit étoilée",
                    "Vincent van Gogh",
                    "1889",
                    "Un paysage nocturne tourbillonnant avec des étoiles brillantes, cette œuvre exprime les émotions tourmentées " +
                            "de van Gogh à travers des couleurs vibrantes et des coups de pinceau intenses."),
                Paint(R.drawable.le_baiser,
                    "Le baiser",
                    "Gustav Klimt",
                    "1909",
                    "Un symbole d'amour et de passion, cette peinture richement ornée montre un couple enlacé, entouré de motifs " +
                            "dorés et décoratifs dans le style unique de Klimt."),
                Paint(R.drawable.le_cri,
                    "Le cri",
                    "Edvard Munch",
                    "1893",
                    "Représentant l'angoisse existentielle, cette peinture iconique montre une figure tourmentée, " +
                            "entourée d'un paysage déformé et oppressant, symbolisant la peur et l'anxiété humaine.")
            )
        }
    }

}