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
                    "The bridge of the devil",
                    "Joseph Mallord William Turner",
                    "1804",
                    "Turner captures this dramatic medieval bridge with his signature style of light and nature, " +
                            "symbolizing both the beauty and danger of natural landscapes."),
                Paint(R.drawable.guernica,
                    "Guernica",
                    "Pablo Picasso",
                    "1937",
                    "This monumental painting denounces the horrors of war, particularly the bombing of the Spanish city " +
                            "of Guernica during the civil war. A masterpiece of Cubism, full of symbolism."),
                Paint(R.drawable.la_jeune_fille_la_perle,
                    "The girl with the pearl earring",
                    "Johannes Vermeer",
                    "1665",
                    "Known as the \"Mona Lisa of the North\", this work captures the elegance and mystery of a young woman with a pearl, " +
                            "using delicate lighting and soft tones"),
                Paint(R.drawable.la_naissance_de_venus,
                    "the birth of venus",
                    "Sandro Botticelli",
                    "1485",
                    "This painting depicts the goddess Venus emerging from the sea on a shell, symbolizing beauty and love " +
                            "in Greco-Roman mythology."),
                Paint(R.drawable.la_nuit_etoile,
                    "Starry Night",
                    "Vincent van Gogh",
                    "1889",
                    "A swirling nightscape with shining stars, this work expresses tormented emotions " +
                            "of van Gogh through vibrant colors and intense brushstrokes."),
                Paint(R.drawable.le_baiser,
                    "The kiss",
                    "Gustav Klimt",
                    "1909",
                    "A symbol of love and passion, this ornate painting shows an embracing couple, surrounded by motifs " +
                            "gilded and decorative in Klimt's unique style."),
                Paint(R.drawable.le_cri,
                    "The cry",
                    "Edvard Munch",
                    "1893",
                    "Representing existential angst, this iconic painting shows a tormented figure, " +
                            "surrounded by a distorted and oppressive landscape, symbolizing human fear and anxiety.")
            )
        }
    }

}