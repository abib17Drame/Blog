package com.example.simpleblogapp.adapteur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleblogapp.R;
import com.example.simpleblogapp.modele.Article;

import java.util.List;

/**
 * Adapteur pour afficher les articles dans un RecyclerView
 */
public class ArticleAdapteur extends RecyclerView.Adapter<ArticleAdapteur.ArticleViewHolder> {
    
    private List<Article> listeArticles;
    private OnArticleClickListener ecouteurClicArticle;

    /**
     * Interface pour gérer les clics sur les articles
     */
    public interface OnArticleClickListener {
        void onArticleClick(int position);
    }

    /**
     * Constructeur de l'adapteur
     * 
     * @param listeArticles Liste des articles à afficher
     * @param ecouteurClicArticle Écouteur pour les clics sur les articles
     */
    public ArticleAdapteur(List<Article> listeArticles, OnArticleClickListener ecouteurClicArticle) {
        this.listeArticles = listeArticles;
        this.ecouteurClicArticle = ecouteurClicArticle;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Création de la vue pour un élément de la liste
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view, ecouteurClicArticle);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        // Récupération de l'article à la position donnée
        Article articleCourant = listeArticles.get(position);
        
        // Mise à jour des vues avec les données de l'article
        holder.textTitreArticle.setText(articleCourant.getTitre());
        holder.textContenuArticle.setText(articleCourant.getContenu());
    }

    @Override
    public int getItemCount() {
        return listeArticles.size();
    }

    /**
     * ViewHolder pour représenter un élément de la liste des articles
     */
    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        
        public CardView cardViewArticle;
        public TextView textTitreArticle;
        public TextView textContenuArticle;

        public ArticleViewHolder(@NonNull View itemView, final OnArticleClickListener ecouteur) {
            super(itemView);
            
            // Initialisation des vues
            cardViewArticle = itemView.findViewById(R.id.cardViewArticle);
            textTitreArticle = itemView.findViewById(R.id.textTitreArticle);
            textContenuArticle = itemView.findViewById(R.id.textContenuArticle);
            
            // Configuration de l'écouteur de clic
            cardViewArticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ecouteur != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            ecouteur.onArticleClick(position);
                        }
                    }
                }
            });
        }
    }
}
