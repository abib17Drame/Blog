package com.example.simpleblogapp.base_de_donnees;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.simpleblogapp.modele.Article;

/**
 * Gestionnaire de la base de données SQLite
 */
public class GestionnaireBD extends SQLiteOpenHelper {

    // Nom et version de la base de données
    private static final String NOM_BASE_DONNEES = "simpleblog.db";
    private static final int VERSION_BASE_DONNEES = 1;
    private SQLiteDatabase baseDonnees;

    /**
     * Constructeur du gestionnaire de base de données
     * @param context Le contexte de l'application
     */
    public GestionnaireBD(Context context) {
        super(context, NOM_BASE_DONNEES, null, VERSION_BASE_DONNEES);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la table des articles
        final String SQL_CREATION_TABLE_ARTICLES = "CREATE TABLE " + 
                ContratBD.ArticleEntree.NOM_TABLE + " (" +
                ContratBD.ArticleEntree._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContratBD.ArticleEntree.COLONNE_TITRE + " TEXT NOT NULL, " +
                ContratBD.ArticleEntree.COLONNE_CONTENU + " TEXT NOT NULL, " +
                ContratBD.ArticleEntree.COLONNE_AUTEUR + " TEXT NOT NULL, " +
                ContratBD.ArticleEntree.COLONNE_DATE + " TEXT NOT NULL)";
        
        db.execSQL(SQL_CREATION_TABLE_ARTICLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // En cas de mise à jour, suppression et recréation de la table
        db.execSQL("DROP TABLE IF EXISTS " + ContratBD.ArticleEntree.NOM_TABLE);
        onCreate(db);
    }

    /**
     * Ouvre la base de données en mode écriture
     */
    public void ouvrir() {
        baseDonnees = getWritableDatabase();
    }

    /**
     * Ferme la base de données
     */
    public void fermer() {
        if (baseDonnees != null && baseDonnees.isOpen()) {
            baseDonnees.close();
        }
    }

    /**
     * Ajoute un article dans la base de données
     * @param article L'article à ajouter
     * @return L'identifiant du nouvel article, ou -1 en cas d'erreur
     */
    public long ajouterArticle(Article article) {
        ContentValues valeurs = new ContentValues();
        valeurs.put(ContratBD.ArticleEntree.COLONNE_TITRE, article.getTitre());
        valeurs.put(ContratBD.ArticleEntree.COLONNE_CONTENU, article.getContenu());
        valeurs.put(ContratBD.ArticleEntree.COLONNE_AUTEUR, article.getAuteur());
        valeurs.put(ContratBD.ArticleEntree.COLONNE_DATE, article.getDate());
        
        return baseDonnees.insert(ContratBD.ArticleEntree.NOM_TABLE, null, valeurs);
    }

    /**
     * Récupère tous les articles de la base de données
     * @return Un curseur contenant tous les articles
     */
    public Cursor obtenirTousLesArticles() {
        return baseDonnees.query(
                ContratBD.ArticleEntree.NOM_TABLE,
                null,
                null,
                null,
                null,
                null,
                ContratBD.ArticleEntree._ID + " DESC"  // Tri par ID décroissant (plus récent d'abord)
        );
    }

    /**
     * Récupère un article spécifique par son identifiant
     * @param id L'identifiant de l'article à récupérer
     * @return Un curseur contenant l'article demandé
     */
    public Cursor obtenirArticleParId(int id) {
        return baseDonnees.query(
                ContratBD.ArticleEntree.NOM_TABLE,
                null,
                ContratBD.ArticleEntree._ID + " = ?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null
        );
    }
}
