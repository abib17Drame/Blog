package com.example.simpleblogapp.base_de_donnees;

import android.provider.BaseColumns;

/**
 * Contrat de la base de données définissant la structure des tables
 */
public final class ContratBD {

    // Constructeur privé pour empêcher l'instanciation
    private ContratBD() {}

    /**
     * Définition de la table des articles
     */
    public static class ArticleEntree implements BaseColumns {
        public static final String NOM_TABLE = "articles";
        public static final String COLONNE_TITRE = "titre";
        public static final String COLONNE_CONTENU = "contenu";
        public static final String COLONNE_AUTEUR = "auteur";
        public static final String COLONNE_DATE = "date";
    }
}
