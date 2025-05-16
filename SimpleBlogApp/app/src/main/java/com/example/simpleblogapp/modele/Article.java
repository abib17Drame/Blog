package com.example.simpleblogapp.modele;

/**
 * Classe modèle représentant un article du blog
 */
public class Article {
    private int id;
    private String titre;
    private String contenu;
    private String auteur;
    private String date;

    /**
     * Constructeur de la classe Article
     * 
     * @param id Identifiant de l'article
     * @param titre Titre de l'article
     * @param contenu Contenu textuel de l'article
     * @param auteur Auteur de l'article
     * @param date Date de publication de l'article
     */
    public Article(int id, String titre, String contenu, String auteur, String date) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.date = date;
    }

    /**
     * Obtient l'identifiant de l'article
     * @return L'identifiant de l'article
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'article
     * @param id Le nouvel identifiant
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtient le titre de l'article
     * @return Le titre de l'article
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Définit le titre de l'article
     * @param titre Le nouveau titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Obtient le contenu de l'article
     * @return Le contenu de l'article
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * Définit le contenu de l'article
     * @param contenu Le nouveau contenu
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    /**
     * Obtient l'auteur de l'article
     * @return L'auteur de l'article
     */
    public String getAuteur() {
        return auteur;
    }

    /**
     * Définit l'auteur de l'article
     * @param auteur Le nouvel auteur
     */
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    /**
     * Obtient la date de publication de l'article
     * @return La date de publication
     */
    public String getDate() {
        return date;
    }

    /**
     * Définit la date de publication de l'article
     * @param date La nouvelle date
     */
    public void setDate(String date) {
        this.date = date;
    }
}
