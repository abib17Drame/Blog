package com.example.simpleblogapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleblogapp.base_de_donnees.ContratBD;
import com.example.simpleblogapp.base_de_donnees.GestionnaireBD;
import com.example.simpleblogapp.modele.Article;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Activité qui affiche les détails d'un article
 */
public class DetailArticleActivity extends AppCompatActivity {

    private TextView textTitre;
    private TextView textAuteur;
    private TextView textDate;
    private TextView textContenu;
    private ImageButton boutonRetour;
    private GestionnaireBD gestionnaireBD;
    private int articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        // Initialisation des vues
        textTitre = findViewById(R.id.textTitreArticle);
        textAuteur = findViewById(R.id.textAuteur);
        textDate = findViewById(R.id.textDate);
        textContenu = findViewById(R.id.textContenuArticle);
        boutonRetour = findViewById(R.id.boutonRetour);

        // Récupération de l'ID de l'article
        articleId = getIntent().getIntExtra("ARTICLE_ID", -1);

        // Initialisation de la base de données
        gestionnaireBD = new GestionnaireBD(this);
        gestionnaireBD.ouvrir();

        // Chargement des détails de l'article
        if (articleId != -1) {
            chargerDetailArticle(articleId);
        }

        // Configuration du bouton de retour
        boutonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Charge les détails d'un article depuis la base de données
     * @param id L'identifiant de l'article à charger
     */
    private void chargerDetailArticle(int id) {
        Cursor curseur = gestionnaireBD.obtenirArticleParId(id);
        
        if (curseur != null && curseur.moveToFirst()) {
            // Récupération des données
            String titre = curseur.getString(curseur.getColumnIndexOrThrow(ContratBD.ArticleEntree.COLONNE_TITRE));
            String contenu = curseur.getString(curseur.getColumnIndexOrThrow(ContratBD.ArticleEntree.COLONNE_CONTENU));
            String date = curseur.getString(curseur.getColumnIndexOrThrow(ContratBD.ArticleEntree.COLONNE_DATE));
            String auteur = curseur.getString(curseur.getColumnIndexOrThrow(ContratBD.ArticleEntree.COLONNE_AUTEUR));
            
            // Mise à jour de l'interface
            textTitre.setText(titre);
            textContenu.setText(contenu);
            textAuteur.setText(getString(R.string.ecrit_par, auteur));
            textDate.setText(date);
            
            curseur.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Fermeture de la base de données
        gestionnaireBD.fermer();
    }
}
