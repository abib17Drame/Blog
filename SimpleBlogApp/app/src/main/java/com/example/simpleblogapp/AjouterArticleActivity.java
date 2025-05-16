package com.example.simpleblogapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleblogapp.base_de_donnees.GestionnaireBD;
import com.example.simpleblogapp.modele.Article;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Activité permettant d'ajouter un nouvel article au blog
 */
public class AjouterArticleActivity extends AppCompatActivity {

    private TextView textTitrePrincipal;
    private TextView textTitrePage;
    private EditText editTitreArticle;
    private EditText editContenuArticle;
    private Button boutonAjouter;
    private ImageButton boutonFermer;
    private GestionnaireBD gestionnaireBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_article);

        // Initialisation des vues
        textTitrePrincipal = findViewById(R.id.textTitrePrincipal);
        textTitrePage = findViewById(R.id.textTitrePage);
        editTitreArticle = findViewById(R.id.editTitreArticle);
        editContenuArticle = findViewById(R.id.editContenuArticle);
        boutonAjouter = findViewById(R.id.boutonAjouter);
        boutonFermer = findViewById(R.id.boutonFermer);

        // Configuration des titres
        textTitrePrincipal.setText(R.string.app_name);
        textTitrePage.setText(R.string.ajouter_un_article);

        // Initialisation de la base de données
        gestionnaireBD = new GestionnaireBD(this);
        gestionnaireBD.ouvrir();

        // Configuration du bouton d'ajout
        boutonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterArticle();
            }
        });

        // Configuration du bouton de fermeture
        boutonFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Ajoute un nouvel article dans la base de données
     */
    private void ajouterArticle() {
        // Récupération des données saisies
        String titre = editTitreArticle.getText().toString().trim();
        String contenu = editContenuArticle.getText().toString().trim();

        // Validation des champs
        if (TextUtils.isEmpty(titre)) {
            editTitreArticle.setError(getString(R.string.erreur_titre_vide));
            return;
        }

        if (TextUtils.isEmpty(contenu)) {
            editContenuArticle.setError(getString(R.string.erreur_contenu_vide));
            return;
        }

        // Création de la date actuelle au format jj mois aaaa
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE);
        String date = formatDate.format(new Date());

        // Création de l'article (utilisation de "John Doe" comme auteur par défaut)
        String auteur = "John Doe";
        Article nouvelArticle = new Article(0, titre, contenu, auteur, date);

        // Insertion dans la base de données
        long resultat = gestionnaireBD.ajouterArticle(nouvelArticle);

        if (resultat != -1) {
            // Succès : affichage d'un message et fermeture de l'activité
            Toast.makeText(this, R.string.article_ajoute, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // Erreur
            Toast.makeText(this, R.string.erreur_ajout_article, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Fermeture de la base de données
        gestionnaireBD.fermer();
    }
}
