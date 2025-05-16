package com.example.simpleblogapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleblogapp.adapteur.ArticleAdapteur;
import com.example.simpleblogapp.base_de_donnees.ContratBD;
import com.example.simpleblogapp.base_de_donnees.GestionnaireBD;
import com.example.simpleblogapp.modele.Article;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Activité principale qui affiche la liste des articles du blog
 */
public class MainActivity extends AppCompatActivity implements ArticleAdapteur.OnArticleClickListener {

    private RecyclerView recyclerViewArticles;
    private ArticleAdapteur adapteurArticle;
    private GestionnaireBD gestionnaireBD;
    private List<Article> listeArticles;
    private TextView textTitrePage;
    private FloatingActionButton boutonAjouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        recyclerViewArticles = findViewById(R.id.recyclerViewArticles);
        textTitrePage = findViewById(R.id.textTitrePage);
        boutonAjouter = findViewById(R.id.boutonAjouter);

        // Configuration du titre
        textTitrePage.setText(R.string.app_name);

        // Initialisation de la base de données
        gestionnaireBD = new GestionnaireBD(this);
        gestionnaireBD.ouvrir();

        // Configuration du RecyclerView
        recyclerViewArticles.setLayoutManager(new LinearLayoutManager(this));
        listeArticles = new ArrayList<>();
        adapteurArticle = new ArticleAdapteur(listeArticles, this);
        recyclerViewArticles.setAdapter(adapteurArticle);

        // Configuration du bouton d'ajout
        boutonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirection vers l'écran d'ajout d'article
                Intent intent = new Intent(MainActivity.this, AjouterArticleActivity.class);
                startActivity(intent);
            }
        });

        // Chargement des articles
        chargerArticles();
    }

    /**
     * Charge les articles depuis la base de données
     */
    private void chargerArticles() {
        listeArticles.clear();
        Cursor curseur = gestionnaireBD.obtenirTousLesArticles();
        
        if (curseur != null && curseur.moveToFirst()) {
            do {
                // Récupération des données à partir du curseur
                int id = curseur.getInt(curseur.getColumnIndexOrThrow(ContratBD.ArticleEntree._ID));
                String titre = curseur.getString(curseur.getColumnIndexOrThrow(ContratBD.ArticleEntree.COLONNE_TITRE));
                String contenu = curseur.getString(curseur.getColumnIndexOrThrow(ContratBD.ArticleEntree.COLONNE_CONTENU));
                String date = curseur.getString(curseur.getColumnIndexOrThrow(ContratBD.ArticleEntree.COLONNE_DATE));
                String auteur = curseur.getString(curseur.getColumnIndexOrThrow(ContratBD.ArticleEntree.COLONNE_AUTEUR));
                
                // Création de l'objet Article et ajout à la liste
                Article article = new Article(id, titre, contenu, auteur, date);
                listeArticles.add(article);
            } while (curseur.moveToNext());
            
            curseur.close();
        }
        
        // Notification à l'adapteur des modifications
        adapteurArticle.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualisation des articles lors du retour à l'activité
        chargerArticles();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Fermeture de la base de données
        gestionnaireBD.fermer();
    }

    @Override
    public void onArticleClick(int position) {
        // Redirection vers l'écran de détail d'un article
        Article articleSelectionne = listeArticles.get(position);
        Intent intent = new Intent(MainActivity.this, DetailArticleActivity.class);
        intent.putExtra("ARTICLE_ID", articleSelectionne.getId());
        startActivity(intent);
    }
}
